package eu.horyzon.loginextended.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerToggleSneakListener implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		event.setCancelled(true);
	}
}
