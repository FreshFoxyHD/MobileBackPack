package me.maxi.mobilebackpack;

import me.maxi.mobilebackpack.Manager.ConfigManager;
import me.maxi.mobilebackpack.Manager.Metrics;
import me.maxi.mobilebackpack.commands.GiveBackPackCommand;
import me.maxi.mobilebackpack.listeners.BackPackOpenerListener;
import me.maxi.mobilebackpack.listeners.BackPackSafeListener;
import me.maxi.mobilebackpack.listeners.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Main extends JavaPlugin {
    public static ConfigManager configManager;
    public static Main main;
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println(startUP);
        int pluginId = 17721;
        Metrics metrics = new Metrics(this, pluginId);
        configManager = new ConfigManager(getDataFolder()+"/backpacks.yml");
        main = this;
        try {
            getConfigManager().save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new BackPackOpenerListener(), this);
        pluginManager.registerEvents(new BackPackSafeListener(), this);
        getCommand("givebackpack").setExecutor(new GiveBackPackCommand());
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static ConfigManager getConfigManager() {
        return configManager;
    }
    public static Main getMain(){
        return main;
    }
    String startUP = "\n"+
            "    __             ______               __    ______                \n" +
            "   / /_  __  __   / ____/_______  _____/ /_  / ____/___  _  ____  __\n" +
            "  / __ \\/ / / /  / /_  / ___/ _ \\/ ___/ __ \\/ /_  / __ \\| |/_/ / / /\n" +
            " / /_/ / /_/ /  / __/ / /  /  __(__  ) / / / __/ / /_/ />  </ /_/ / \n" +
            "/_.___/\\__, /  /_/   /_/   \\___/____/_/ /_/_/    \\____/_/|_|\\__, /  \n" +
            "      /____/                                               /____/   \n";
}