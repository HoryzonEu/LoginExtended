package eu.horyzon.loginextended.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.horyzon.loginextended.LoginExtended;

public class PlayerQuitListener implements Listener {
	private final LoginExtended plugin;

	public PlayerQuitListener(LoginExtended plugin) {
		this.plugin = plugin;
	}

	@EventHandler()
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		plugin.getBossBarManager().removePlayer(player);
		plugin.getTitleManager().remove(player);
	}
}