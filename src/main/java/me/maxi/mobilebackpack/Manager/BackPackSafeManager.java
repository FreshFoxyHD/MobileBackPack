package me.maxi.mobilebackpack.Manager;

import me.maxi.mobilebackpack.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class BackPackSafeManager {
    public static HashMap<Player, ItemStack> openBackPack = new HashMap<>();
    public static void saveInv(Player player, String UUID, int page, Inventory inventory){
        ArrayList<String> INV = new ArrayList<>();
        int invSize = 9*5;
        String safeString = "";
        for (int i = 0; i <= invSize-1; i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null){
                INV.add("AIR;");
            }else if (item.equals(openBackPack.get(player))){
                if (!(player.getInventory().firstEmpty() == -1)){
                    player.getInventory().addItem(item);
                }
            }else {
                ByteArrayOutputStream io = new ByteArrayOutputStream();
                BukkitObjectOutputStream os = null;
                try {
                    os = new BukkitObjectOutputStream(io);
                    os.writeObject(item);
                    os.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                byte[] serializedObject = io.toByteArray();
                String encodedObject = new String(Base64.getEncoder().encode(serializedObject));
                encodedObject = encodedObject+";";
                INV.add(encodedObject);
            }
            for (String s : INV){
                safeString = safeString+s;
            }
        }
        Main.getConfigManager().setString(UUID, page, safeString);
    }
}
