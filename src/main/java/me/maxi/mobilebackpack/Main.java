package me.maxi.mobilebackpack;

import me.maxi.mobilebackpack.Manager.ConfigManager;
import me.maxi.mobilebackpack.Manager.EcoManager;
import me.maxi.mobilebackpack.Manager.JsonReader;
import me.maxi.mobilebackpack.Manager.Metrics;
import me.maxi.mobilebackpack.commands.GiveBackPackCommand;
import me.maxi.mobilebackpack.commands.UpgradeBackPackCommand;
import me.maxi.mobilebackpack.listeners.BackPackOpenerListener;
import me.maxi.mobilebackpack.listeners.BackPackSafeListener;
import me.maxi.mobilebackpack.listeners.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import java.io.IOException;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {
    public static double  level1 = 1000;
    public static double  level2 = level1*10;
    public static double  level3 = level2*10;
    public static double  level4 = level3*10;
    public static double  level5 = level4*10;
    public static double  level6 = level5*10;

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    public static final String BACKPACKNAME = "§8┃ » §f§lRucksack §7▬§8▪ §9Level ";
    public static ConfigManager configManager;
    public static Main main;
    public static EcoManager ecoManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println(startUP);
        try {
            if (!(getDescription().getVersion().equalsIgnoreCase(JsonReader.getLastVersion()))){
                System.out.println("##############################################");
                System.out.println("              Update Available                ");
                System.out.println(" https://www.mythiccloud.net/s/PyM5jgZmQzZeCpC ");
                System.out.println("##############################################");
            }
        } catch (IOException ignored) {
        }
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        int pluginId = 17721;
        Metrics metrics = new Metrics(this, pluginId);
        configManager = new ConfigManager(getDataFolder()+"/backpacks.yml");
        main = this;
        ecoManager = new EcoManager(econ);
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
        getCommand("upgradepackback").setExecutor(new UpgradeBackPackCommand());
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
    public static EcoManager getEcoManager(){
        return ecoManager;
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    String startUP = "\n"+
            "    __             ______               __    ______                \n" +
            "   / /_  __  __   / ____/_______  _____/ /_  / ____/___  _  ____  __\n" +
            "  / __ \\/ / / /  / /_  / ___/ _ \\/ ___/ __ \\/ /_  / __ \\| |/_/ / / /\n" +
            " / /_/ / /_/ /  / __/ / /  /  __(__  ) / / / __/ / /_/ />  </ /_/ / \n" +
            "/_.___/\\__, /  /_/   /_/   \\___/____/_/ /_/_/    \\____/_/|_|\\__, /  \n" +
            "      /____/                                               /____/   \n";
}