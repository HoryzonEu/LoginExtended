package eu.horyzon.loginextended.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import eu.horyzon.loginextended.LoginExtended;
import eu.horyzon.loginextended.effects.TitleEffect;

public class TitleManager {
	private final LoginExtended plugin;
	private final boolean enable;
	private final int fadeIn,
			fadeOut,
			stay,
			delay,
			period;
	private final Map<Player, BukkitTask> titles = new HashMap<>();

	public TitleManager(LoginExtended plugin) {
		this.plugin = plugin;
		ConfigurationSection section = plugin.getConfig().getConfigurationSection("title");

		enable = section.getBoolean("enable", true);
		fadeIn = section.getInt("fadeIn", 40) * 20;
		fadeOut = section.getInt("fadeOut", 40) * 20;
		stay = section.getInt("stay", 1200) * 20;
		delay = section.getInt("delay", 5) * 20;
		period = section.getInt("period", 5) * 20 + stay + fadeIn + fadeOut;
	}

	public void add(Player player, boolean registered) {
		TitleEffect effect = new TitleEffect(this, player, registered);
		titles.put(player, period > 0 ? effect.runTaskTimerAsynchronously(plugin, delay, period) : effect.runTaskLaterAsynchronously(plugin, delay));
	}

	public void remove(Player player) {
		BukkitTask task = titles.remove(player);
		if (task != null)
			task.cancel();

		player.resetTitle();
	}

	public boolean isEnable() {
		return enable;
	}

	public int getFadeIn() {
		return fadeIn;
	}

	public int getFadeOut() {
		return fadeOut;
	}

	public int getStay() {
		return stay;
	}

	public void cancel() {
		titles.forEach((player, title) -> {
			title.cancel();
			player.resetTitle();
		});
	}
}