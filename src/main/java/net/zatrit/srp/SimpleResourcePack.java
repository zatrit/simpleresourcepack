package net.zatrit.srp;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class SimpleResourcePack extends JavaPlugin {
    public static Logger logger;
    private ConfigData config;
    public final File CONFIG_FILE = new File(getDataFolder(), "config.yml");

    @Override
    public void onEnable() {
        logger = getLogger();

        try {
            config = ConfigData.fromFile(CONFIG_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            config = new ConfigData();
            config.save(CONFIG_FILE);
        }

        config.generateHash();

        getServer().getPluginManager()
                .registerEvents(new EventListener(this), this);

        super.onEnable();
    }

    public ConfigData getConfigData() {
        return config;
    }
}
