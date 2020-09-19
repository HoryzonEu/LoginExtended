package eu.horyzon.loginextended.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import eu.horyzon.loginextended.LoginExtended;
import eu.horyzon.loginextended.configs.Message;
import eu.horyzon.loginextended.effects.BossBarEffect;

public class BossBarManager extends BukkitRunnable {
	private final boolean enable;
	private final int timeout;
	private final Map<Player, BossBarEffect> bossBars = new HashMap<>();
	private final BarColor color;
	private final BarStyle style;

	public BossBarManager(LoginExtended plugin) {
		ConfigurationSection section = plugin.getConfig().getConfigurationSection("bossbar");
		enable = section.getBoolean("enable", true);
		color = BarColor.valueOf(section.getString("color", "WHITE"));
		switch (section.getInt("segment", 20)) {
		case 6:
			style = BarStyle.SEGMENTED_6;
			break;
		case 10:
			style = BarStyle.SEGMENTED_10;
			break;
		case 12:
			style = BarStyle.SEGMENTED_12;
			break;
		case 20:
			style = BarStyle.SEGMENTED_20;
			break;
		default:
			style = BarStyle.SOLID;
		}

		timeout = plugin.getAuthmeAPI().getPlugin().getConfig().getInt("settings.restrictions.timeout", 90);
		runTaskTimer(plugin, 0, 20);
	}

	@Override
	public void run() {
		bossBars.values().forEach((bar) -> bar.update());
	}

	public void addPlayer(Player player, boolean registered) {
		bossBars.put(player, new BossBarEffect(player, Bukkit.createBossBar(registered ? Message.BOSSBAR_LOGIN.toString() : Message.BOSSBAR_REGISTER.toString(), color, style), timeout, registered));
	}

	public void removePlayer(Player player) {
		bossBars.remove(player);
	}

	public boolean isEnabled() {
		return enable;
	}
}
