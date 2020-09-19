package eu.horyzon.loginextended.configs;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import eu.horyzon.loginextended.utils.Utils;

public enum Message {
	PREFIX("prefix", "&8[&6Auth&8] "), TITLE_REGISTER("title.register", "&8Veuillez vous enregistrer\n&8Faites /register <MotDePasse> (Email)"), TITLE_LOGIN("title.login", "&8Veuillez vous authentifier\n&8Faites /login <MotDePasse>"), BOSSBAR_REGISTER("bossbar.register", "&cVous serez déconnecté(e) dans %time% secondes"), BOSSBAR_LOGIN("bossbar.login", "&cVous serez déconnecté(e) dans %time% secondes");

	private final String key;
	private String message;

	Message(String key, String message) {
		this.key = key;
		this.message = message;
	}

	public static void setup(Configuration config) {
		for (Message message : values()) {
			if (!config.contains(message.key))
				config.set(message.key, message.message);

			message.set(Utils.parseColors(config.getString(message.getKey(), message.message)));
		}
	}

	public void set(String message) {
		this.message = message;
	}

	private String getKey() {
		return key;
	}

	public void sendMessage(Player player) {
		player.sendMessage(toString());
	}

	@Override
	public String toString() {
		return message.replaceAll("%prefix%", PREFIX.message);
	}
}