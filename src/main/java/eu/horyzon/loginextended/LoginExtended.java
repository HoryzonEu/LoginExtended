package eu.horyzon.loginextended;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import eu.horyzon.loginextended.configs.Message;
import eu.horyzon.loginextended.configs.yaml.PluginConfig;
import eu.horyzon.loginextended.listeners.PlayerJoinListener;
import eu.horyzon.loginextended.listeners.PlayerQuitListener;
import eu.horyzon.loginextended.listeners.PlayerToggleSneakListener;
import eu.horyzon.loginextended.manager.BossBarManager;
import eu.horyzon.loginextended.manager.TitleManager;
import fr.xephi.authme.api.v3.AuthMeApi;

public class LoginExtended extends JavaPlugin {
	private static LoginExtended instance;
	private BossBarManager bossBarManager;
	private TitleManager titleManager;
	private AuthMeApi authmeAPI;
	private Entity spectateEntity;

	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();

		PluginConfig message = new PluginConfig(this, "messages.yml");
		message.loadSafety();
		Message.setup(message);

		if (getServer().getPluginManager().getPlugin("AuthMe") != null) {
			authmeAPI = AuthMeApi.getInstance();
			getLogger().info("AuthMeReloaded correctly configured!");
		}

		bossBarManager = new BossBarManager(this);
		titleManager = new TitleManager(this);

		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);

		getServer().getScheduler().runTaskLaterAsynchronously(this, () -> {
			try {
				if ( (spectateEntity = getServer().getEntity(UUID.fromString(getConfig().getString("spectateEntity")))) == null)
					return;

				spectateEntity.getLocation().getChunk().load();
				getLogger().info("Spectate entity enabled with entity type " + spectateEntity.getType().toString());
				getServer().setDefaultGameMode(GameMode.SPECTATOR);
				getServer().getPluginManager().registerEvents(new PlayerToggleSneakListener(), this);
			} catch (IllegalArgumentException exception) {}
		}, 20);
	}

	@Override
	public void onDisable() {
		bossBarManager.cancel();
		titleManager.cancel();
	}

	public static LoginExtended getInstance() {
		return instance;
	}

	public Entity getSpectateEntity() {
		return spectateEntity;
	}

	public BossBarManager getBossBarManager() {
		return bossBarManager;
	}

	public TitleManager getTitleManager() {
		return titleManager;
	}

	public boolean useAuthme() {
		return authmeAPI != null;
	}

	public AuthMeApi getAuthmeAPI() {
		return authmeAPI;
	}
}