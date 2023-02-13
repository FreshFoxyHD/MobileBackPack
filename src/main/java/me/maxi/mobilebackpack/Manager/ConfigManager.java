package me.maxi.mobilebackpack.Manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ConfigManager {
    private File file;
    private FileConfiguration config;

    public ConfigManager(String path) {
        file = new File(path);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() throws IOException {
        config.save(file);
    }

    public void set(String path, Object value) {
        config.set(path, value);
    }

    public Object get(String path) {
        return config.get(path);
    }

    public void createBackPackData(String uuid, int level, String page1, String page2, String page3, String page4, String page5, String page6, String page7, String page8) throws IOException {
        config.set("backpack." + uuid + ".level", level);
        config.set("backpack." + uuid + ".page1", page1);
        config.set("backpack." + uuid + ".page2", page2);
        config.set("backpack." + uuid + ".page3", page3);
        config.set("backpack." + uuid + ".page4", page4);
        config.set("backpack." + uuid + ".page5", page5);
        config.set("backpack." + uuid + ".page6", page6);
        save();
    }

    public int getLevel(String uuid) {
        return config.getInt("backpack." + uuid + ".level");
    }
    public void setLevel(String uuid, int level) throws IOException {
        config.set("backpack." + uuid + ".level", level);
        save();
    }

    public String getString(String uuid, int number) {
        return config.getString("backpack." + uuid + ".page"+number);
    }
    public void setString(String uuid, int number, String page) throws IOException {
        config.set("backpack." + uuid + ".page"+number, page);
        save();
    }
}