package me.maxi.mobilebackpack;

import me.maxi.mobilebackpack.Manager.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;
import sun.security.krb5.Config;

import java.io.IOException;

public final class Main extends JavaPlugin {

    public static ConfigManager configManager;
    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigManager configManager1 = new ConfigManager("");
        try {
            configManager1.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }
}
