package eu.horyzon.loginextended.configs.yaml;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class PluginConfig extends YamlConfiguration {
	private final File file;
	private final Plugin plugin;

	public PluginConfig(Plugin plugin, File file) {
		this.file = file;
		this.plugin = plugin;
	}

	public PluginConfig(Plugin plugin, String name) {
		this(plugin, new File(plugin.getDataFolder(), name));
	}

	public void load() throws IOException, InvalidConfigurationException {
		if (!file.isFile())
			if (plugin.getResource(file.getName()) != null)
				plugin.saveResource(file.getName(), false);
			else {
				if (file.getParentFile() != null)
					file.getParentFile().mkdirs();

				file.createNewFile();
			}

		getKeys(false).forEach((section) -> {
			set(section, null);
		});

		load(file);
	}

	public boolean loadSafety() {
		try {
			load();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			plugin.getLogger().log(Level.WARNING, "I/O error while loading the file \"{0}\". Is the file in use?", getFileName());
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			plugin.getLogger().log(Level.WARNING, "Invalid YAML configuration for the file \"{0}\". Please look at the error above, or use an online YAML parser (google is your friend).", getFileName());
		}

		return false;
	}

	public boolean exist() {
		return file.exists() && file.isFile();
	}

	public void save() throws IOException {
		save(file);
	}

	public boolean saveSafety() {
		try {
			save();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			plugin.getLogger().log(Level.WARNING, "I/O error while saving the file \"{0}\". Is the file in use?", getFileName());
		}

		return false;
	}

	public boolean deleteSafety() {
		try {
			delete();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			plugin.getLogger().log(Level.WARNING, "I/O error while deleting the file \"{0}\". Is the file in use?", getFileName());
		}

		return false;
	}

	public void delete() throws IOException {
		file.delete();
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public String getFileName() {
		return file.getName();
	}
}
