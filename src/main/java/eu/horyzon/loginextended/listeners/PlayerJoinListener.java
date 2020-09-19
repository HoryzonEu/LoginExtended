package eu.horyzon.loginextended.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import eu.horyzon.loginextended.LoginExtended;

public class PlayerJoinListener implements Listener {
	private final LoginExtended plugin;

	public PlayerJoinListener(LoginExtended plugin) {
		this.plugin = plugin;
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.setSpectatorTarget(plugin.getSpectateEntity());

		if (!plugin.useAuthme())
			return;

		boolean registered = plugin.getAuthmeAPI().isRegistered(player.getName());

		if (plugin.getTitleManager().isEnable())
			plugin.getTitleManager().add(player, registered);

		if (plugin.getBossBarManager().isEnabled())
			plugin.getBossBarManager().addPlayer(player, registered);
	}
}