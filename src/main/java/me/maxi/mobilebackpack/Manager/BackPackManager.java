package me.maxi.mobilebackpack.Manager;

import me.maxi.mobilebackpack.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class BackPackManager {
    public static HashMap<Player, ItemStack> openBackPack = new HashMap<>();
    public static void saveInv(Player player, String UUID, int page, Inventory inventory) throws IOException {
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
    public static void onUpgradeBackPack(Player player, ItemStack itemStack) throws IOException {
        String uuid = getUUID(itemStack);
        int lvl = Main.getConfigManager().getLevel(uuid);
        ItemStack newItem = itemStack;
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add("                  §dMythria§8.§bnet §f§lRucksack         ");
        loreList.add("§aÖffne mich ganz einfach mit einem Rechtsklick");
        if (lvl == 8){
            newItem = itemStack;
        }
        if (lvl == 7){//Upgrade LVL 8
            loreList.add("§9Level: 8");
            loreList.add("§eUUID: "+uuid);
            newItem = new ItemBuilder(getHead(""))
                    .setDisplayName("§f§lRucksack §7▬§8▪ §9§lLevel 8")
                    .setLoreList(loreList)
                    .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .build();
            Main.getConfigManager().setLevel(uuid, 8);
        }
        if (lvl == 6){//Upgrade LVL 7
            loreList.add("§9Level: 7");
            loreList.add("§eUUID: "+uuid);
            newItem = new ItemBuilder(getHead(""))
                    .setDisplayName("§f§lRucksack §7▬§8▪ §9§lLevel 7")
                    .setLoreList(loreList)
                    .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .build();
            Main.getConfigManager().setLevel(uuid, 7);
        }
        if (lvl == 5){//Upgrade LVL 6
            loreList.add("§9Level: 6");
            loreList.add("§eUUID: "+uuid);
            newItem = new ItemBuilder(getHead(""))
                    .setDisplayName("§f§lRucksack §7▬§8▪ §9§lLevel 6")
                    .setLoreList(loreList)
                    .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .build();
            Main.getConfigManager().setLevel(uuid, 6);
        }
        if (lvl == 4){//Upgrade LVL 5
            loreList.add("§9Level: 5");
            loreList.add("§eUUID: "+uuid);
            newItem = new ItemBuilder(getHead(""))
                    .setDisplayName("§f§lRucksack §7▬§8▪ §9§lLevel 5")
                    .setLoreList(loreList)
                    .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .build();
            Main.getConfigManager().setLevel(uuid, 5);
        }
        if (lvl == 3){//Upgrade LVL 4
            loreList.add("§9Level: 4");
            loreList.add("§eUUID: "+uuid);
            newItem = new ItemBuilder(getHead(""))
                    .setDisplayName("§f§lRucksack §7▬§8▪ §9§lLevel 4")
                    .setLoreList(loreList)
                    .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .build();
            Main.getConfigManager().setLevel(uuid, 4);
        }
        if (lvl == 2){//Upgrade LVL 3
            loreList.add("§9Level: 3");
            loreList.add("§eUUID: "+uuid);
            newItem = new ItemBuilder(getHead(""))
                    .setDisplayName("§f§lRucksack §7▬§8▪ §9§lLevel 3")
                    .setLoreList(loreList)
                    .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .build();
            Main.getConfigManager().setLevel(uuid, 3);
        }
        if (lvl == 1){//Upgrade LVL 2
            loreList.add("§9Level: 2");
            loreList.add("§eUUID: "+uuid);
            newItem = new ItemBuilder(getHead(""))
                    .setDisplayName("§f§lRucksack §7▬§8▪ §9§lLevel 2")
                    .setLoreList(loreList)
                    .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .build();
            Main.getConfigManager().setLevel(uuid, 2);
        }
        player.getInventory().remove(itemStack);
        player.getInventory().addItem(newItem);
    }
    public static Inventory backPack(Player player, String uuid, int page){
        ItemStack backPackItem = openBackPack.get(player);
        List<String> loreList = backPackItem.getLore();
        assert loreList != null;
        int level = Main.getConfigManager().getLevel(uuid);
        int invSize = 9*6;
        Inventory inventory = Bukkit.createInventory(player, invSize, Main.BACKPACKNAME+level+" §7▬§8▪ §aSeite "+page);
        String INV = Main.configManager.getString(uuid, page);
        if (INV.isEmpty())return inventory;
        String[] invlist = INV.split(";");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(invlist));
        for (int i = 0; i <= list.size()-1; i++) {
            if (list.get(i).equalsIgnoreCase("AIR")){
                //inventory.add(new ItemStack(Material.AIR));
            }else {
                try{
                    byte[] serializedObject;
                    serializedObject = Base64.getDecoder().decode(list.get(i));
                    ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
                    BukkitObjectInputStream is = new BukkitObjectInputStream(in);
                    ItemStack newItem = (ItemStack) is.readObject();
                    inventory.setItem(i, newItem);
                }catch (IOException ex){
                    ex.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        inventory.setItem(46, new ItemBuilder(getHead("DFE")).setDisplayName("§a<- Zurück").addItemFlag(ItemFlag.HIDE_DYE).build());
        if (page < 4){
            inventory.setItem(46, new ItemBuilder(Material.BARRIER, 1).setDisplayName(" ").addItemFlag(ItemFlag.HIDE_DYE).build());
        }
        inventory.setItem(47, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setDisplayName(" ").addItemFlag(ItemFlag.HIDE_DYE).build());

        inventory.setItem(48, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lSeite 1").addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(49, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lSeite 2").addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(50, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lSeite 3").addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(51, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lSeite 4").addItemFlag(ItemFlag.HIDE_DYE).build());


        if (page == 1)inventory.setItem(48, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§f§lSeite 1").addItemFlag(ItemFlag.HIDE_DYE).build());
        if (page == 2)inventory.setItem(49, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§f§lSeite 2").addItemFlag(ItemFlag.HIDE_DYE).build());
        if (page == 3)inventory.setItem(50, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§f§lSeite 3").addItemFlag(ItemFlag.HIDE_DYE).build());
        if (page == 4)inventory.setItem(51, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§f§lSeite 4").addItemFlag(ItemFlag.HIDE_DYE).build());



        inventory.setItem(52, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setDisplayName(" ").addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(53, new ItemBuilder(Material.BARRIER, 1).setDisplayName(" ").addItemFlag(ItemFlag.HIDE_DYE).build());
        if (page < 4){
            inventory.setItem(53, new ItemBuilder(getHead("ABC")).setDisplayName("§aWeiter ->").addItemFlag(ItemFlag.HIDE_DYE).build());
        }

        return inventory;
    }
    public static ItemStack getHead(String value) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        UUID uuid = new UUID(value.hashCode(), value.hashCode());
        return Bukkit.getUnsafe().modifyItemStack(skull, "{SkullOwner:{Id:\"" + uuid + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}"
        );
    }
    public static String getUUID(ItemStack item) {
        List<String> lore = item.getItemMeta().getLore();
        StringBuilder sb = new StringBuilder();
        sb.append(lore.get(3));
        return sb.toString().replace("§eUUID: ","");
    }
    public static boolean isBackPack(ItemStack itemStack){
        List<String> lore = itemStack.getItemMeta().getLore();
        StringBuilder sb = new StringBuilder();
        sb.append(lore.get(3));
        if (sb.toString().contains("§eUUID: ") && itemStack.getType().equals(Material.PLAYER_HEAD) && itemStack.getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)){
            return true;
        }
        return false;
    }
}
