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

    public void createBackPackData(UUID uuid, int level, String string1, String string2, String string3, String string4, String string5, String string6) throws IOException {
        config.set("backpack." + uuid + ".level", level);
        config.set("backpack." + uuid + ".string1", string1);
        config.set("backpack." + uuid + ".string2", string2);
        config.set("backpack." + uuid + ".string3", string3);
        config.set("backpack." + uuid + ".string4", string4);
        config.set("backpack." + uuid + ".string5", string5);
        config.set("backpack." + uuid + ".string6", string6);
        save();
    }

    public int getLevel(UUID uuid) {
        return config.getInt("backpack." + uuid + ".level");
    }

    public String getString1(UUID uuid) {
        return config.getString("backpack." + uuid + ".string1");
    }

    public String getString2(UUID uuid) {
        return config.getString("backpack." + uuid + ".string2");
    }

    public String getString3(UUID uuid) {
        return config.getString("backpack." + uuid + ".string3");
    }

    public String getString4(UUID uuid) {
        return config.getString("backpack." + uuid + ".string4");
    }

    public String getString5(UUID uuid) {
        return config.getString("backpack." + uuid + ".string5");
    }

    public String getString6(UUID uuid) {
        return config.getString("backpack." + uuid + ".string6");
    }
}