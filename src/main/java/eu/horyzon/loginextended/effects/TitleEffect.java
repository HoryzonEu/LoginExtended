package eu.horyzon.loginextended.effects;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import eu.horyzon.loginextended.configs.Message;
import eu.horyzon.loginextended.manager.TitleManager;

public class TitleEffect extends BukkitRunnable {
	private final TitleManager manager;
	private final Player player;
	private final String[] title;

	public TitleEffect(TitleManager manager, Player player, boolean registered) {
		this.manager = manager;
		this.player = player;
		title = (registered ? Message.TITLE_LOGIN : Message.TITLE_REGISTER).toString().split("\\\\n");
	}

	@Override
	public void run() {
		player.sendTitle(title[0], title.length > 1 ? title[1] : "", manager.getFadeIn(), manager.getStay(), manager.getFadeOut());
	}
}