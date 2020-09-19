package eu.horyzon.loginextended.effects;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import eu.horyzon.loginextended.configs.Message;

public class BossBarEffect {
	private final BossBar bossBar;
	private final String message;
	private int time;
	private double progress;

	public BossBarEffect(Player player, BossBar bossBar, int time, boolean registered) {
		this.bossBar = bossBar;
		this.time = time;
		progress = 1.0 / time;
		message = (registered ? Message.BOSSBAR_LOGIN : Message.BOSSBAR_REGISTER).toString();

		bossBar.addPlayer(player);
		bossBar.setVisible(true);
		bossBar.setProgress(1);
		bossBar.setTitle(message.replaceAll("%time%", Integer.toString(time)));
	}

	public void update() {
		bossBar.setTitle(message.toString().replaceAll("%time%", Integer.toString(time--)));
		bossBar.setProgress(bossBar.getProgress() - progress);
	}
}