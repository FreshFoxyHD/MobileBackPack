package me.maxi.mobilebackpack.manager;

import me.maxi.mobilebackpack.Main;
import me.maxi.mobilebackpack.listeners.BackPackSafeListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class BackPackManager {
    public static HashMap<Player, ItemStack> openBackPack = new HashMap<>();
    public static ItemStack createBackPackItem(String uuid, int level) throws IOException {
        ItemStack itemStack;
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add("                  §f§lRucksack         ");
        loreList.add("§aÖffne mich ganz einfach mit einem Rechtsklick");
        loreList.add("§9Level: "+level);
        loreList.add("§eUUID: "+uuid);
        ItemStack head = getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzNjMGJmYTg3NWFiOGI4M2Q4ZDk1MTk3NzRjNmM3YzQ1YWQ5YTg4NDNmNjhhNGE1YzAwMDM3NDMyNjBlMmVjNSJ9fX0=");
        if (level == 2) head = getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ZkMTcwNDAwOGJjNjgzZGM4ZTZlOGZmN2MxMGUwY2YyNGQ3MmI4ZjMwNmRlMjYzMWUzZmViZmRlNjQ5MWM0ZCJ9fX0=");
        if (level == 3) head = getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjcwYjU2MjJiN2QwNjhmNTc4OGJmMTlhODM5ODM5MzdiMTZjNTk3MmM5MWY1ZWU3YmY1NGJjYzM2MzhmOWEzNiJ9fX0=");
        if (level == 4) head = getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmNlMDE2OTlmNzk2Y2JkZTk1Yzg0OWJlZTNjYzM2OTg3ODQ1MmI0MGE0MTE0NDM2NmQ2NmI0YTA4MjZjZmFhMCJ9fX0=");
        if (level == 5) head = getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFjNzdiNzZmMGM2NGMxNGMwNDkzNmE1NTc5NmM0OWE4MmZmMTc0ODM4ZGI5MzBkNTJiMGNhYWNkZDIxNTkxNyJ9fX0=");
        if (level == 6) head = getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2U1YWJkYjczNzQ1NTNkMDU2NWNiY2IzMjk1YWVkNTE1YTg5N2ViY2U5ZTBiYzYwZjFjMWY4YWU1NGM3NDlkZiJ9fX0=");
        if (level == 7) head = getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmUwODdjZDk3NWQyNjRjNzJhZGNhOWVmYzZmYzM0NDc2ZWMzOGUxOGRmNTM2YjMwNzYzNTI2N2EzN2ZhZjA3NiJ9fX0=");
        if (level == 8) head = getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE4N2M3MzJhYTM5ZDI5MTM0NjUwYjZhNzJjNmY0MWI5OTA4NWEyNmVjNWU1MTNiYTE4YzQwZDVlY2E5ZTY5YyJ9fX0=");
        itemStack = new ItemBuilder(head)
                .setDisplayName("§f§lRucksack §7▬§8▪ §9§lLevel "+level)
                .setLoreList(loreList)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();
        Main.getConfigManager().setLevel(uuid, level);
        return itemStack;
    }
    public static void createBackPack(CommandSender sender, Player target) throws IOException {
        if (hasAvaliableSlot(target, 1)){
            String uuid = UUID.randomUUID().toString();
            Main.getConfigManager().createBackPackData(uuid, 1, "AIR;AIR;","AIR;AIR;","AIR;AIR;","AIR;AIR;","AIR;AIR;","AIR;AIR;","AIR;AIR;","AIR;AIR;");
            ItemStack bp = createBackPackItem(uuid, 1);
            target.getInventory().addItem(bp);
        }else {
            sender.sendMessage("This player has no more free slots");
        }
    }
    public static void createBackPackByUUID(String uuid, CommandSender sender, Player target) throws IOException {
        if (hasAvaliableSlot(target, 1)){
            int level = Main.getConfigManager().getLevel(uuid);
            ItemStack bp = createBackPackItem(uuid, level);
            target.getInventory().addItem(bp);
        }else {
            sender.sendMessage("This player has no more free slots");
        }
    }
    public static void saveInv(Player player, String UUID, int page, Inventory inventory) throws IOException {
        ArrayList<String> INV = new ArrayList<>();
        int invSize = 9*5;
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
        }
        String safeString = "";
        for (String s : INV){
            safeString = safeString+s;
        }
        Main.getConfigManager().setString(UUID, page, safeString);
    }
    public static ItemStack onUpgradeBackPack(ItemStack itemStack) throws IOException {
        String uuid = getUUID(itemStack);
        int lvl = Main.getConfigManager().getLevel(uuid);
        ItemStack newItem = itemStack;
        if (lvl == 6){//Upgrade LVL 7
            newItem = itemStack;
        }
        if (lvl == 5){//Upgrade LVL 6
            newItem = createBackPackItem(uuid, 6);
        }
        if (lvl == 4){//Upgrade LVL 5
            newItem = createBackPackItem(uuid, 5);
        }
        if (lvl == 3){//Upgrade LVL 4
            newItem = createBackPackItem(uuid, 4);
        }
        if (lvl == 2){//Upgrade LVL 3
            newItem = createBackPackItem(uuid, 3);
        }
        if (lvl == 1){//Upgrade LVL 2
            newItem = createBackPackItem(uuid, 2);
        }
        return newItem;
    }
    public static Inventory backPack(Player player, String uuid, int page, int subpsite){
        ItemStack backPackItem = openBackPack.get(player);
        if (BackPackSafeListener.isInTools.get(player) != null)BackPackSafeListener.isInTools.remove(player);
        if (BackPackSafeListener.safeSubPage.get(player) != null){
            BackPackSafeListener.safeSubPage.remove(player);
            BackPackSafeListener.safeSubPage.put(player, subpsite);
        }else {
            BackPackSafeListener.safeSubPage.put(player, subpsite);
        }
        List<String> loreList = backPackItem.getLore();
        assert loreList != null;
        int level = Main.getConfigManager().getLevel(uuid);
        int invSize = 9*6;
        Inventory inventory = Bukkit.createInventory(player, invSize, Main.BACKPACKNAME+level+" §7▬§8▪ §aSeite "+page);
        String INV = Main.getConfigManager().getString(uuid, page);
        String[] invlist = INV.split(";");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(invlist));
        if (INV.isEmpty()) return inventory;
        for (int i = 0; i <= list.size() - 1; i++) {
            if (list.get(i).equalsIgnoreCase("AIR") || list.get(i).equalsIgnoreCase("AIR;")) {
                //inventory.add(new ItemStack(Material.AIR));
            } else {
                try {
                    byte[] serializedObject;
                    serializedObject = Base64.getDecoder().decode(list.get(i));
                    ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
                    BukkitObjectInputStream is = new BukkitObjectInputStream(in);
                    ItemStack newItem = (ItemStack) is.readObject();
                    inventory.setItem(i, newItem);
                } catch (IOException ex) {
                    System.out.println(ex);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        inventory.setItem(46, new ItemBuilder(Material.EMERALD, 1).setDisplayName("§a§l✰ §f§lUpgrade §a§l✰").addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(47, new ItemBuilder(Material.CRAFTING_TABLE, 1).setDisplayName("§c✸ §f§lWerkbank").addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(51, new ItemBuilder(Material.ANVIL, 1).setDisplayName("§c✸ §f§lAmboss").addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(52, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setDisplayName(" ").addItemFlag(ItemFlag.HIDE_DYE).build());
        if (subpsite == 1){
            inventory.setItem(45, new ItemBuilder(Material.BARRIER, 1).setDisplayName("§c§lEnde").addItemFlag(ItemFlag.HIDE_DYE).build());
            inventory.setItem(48, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lSeite 1").addItemFlag(ItemFlag.HIDE_DYE).build());
            inventory.setItem(49, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lUpgrade Level 2 Erforderlich").addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            inventory.setItem(50, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lUpgrade Level 3 Erforderlich").addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            if (level >= 2)inventory.setItem(49, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lSeite 2").addItemFlag(ItemFlag.HIDE_DYE).build());
            if (level >= 3)inventory.setItem(50, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lSeite 3").addItemFlag(ItemFlag.HIDE_DYE).build());
            if (page == 1)inventory.setItem(48, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§f§lSeite 1").addItemFlag(ItemFlag.HIDE_DYE).build());
            if (page == 2)inventory.setItem(49, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§f§lSeite 2").addItemFlag(ItemFlag.HIDE_DYE).build());
            if (page == 3)inventory.setItem(50, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§f§lSeite 3").addItemFlag(ItemFlag.HIDE_DYE).build());
            inventory.setItem(53, new ItemBuilder(getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDA2NzJiODJmMGQxZjhjNDBjNTZiNDJkMzY5YWMyOTk0Yzk0ZGE0NzQ5MTAxMGMyY2U0MzAzZTM0NjViOTJhNyJ9fX0=")).setDisplayName("§aNext ->").addItemFlag(ItemFlag.HIDE_DYE).build());
        }
        if (subpsite == 2){
            inventory.setItem(45, new ItemBuilder(getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTVlZmQ5Njk3NGMwNDAzZjIyOWNmOTQxODVjZGQwZjcxOTczNjJhY2JkMDMxY2RmNTFmY2M4ZGFmYWM2Yjg1YSJ9fX0=")).setDisplayName("§a<- Back").addItemFlag(ItemFlag.HIDE_DYE).build());
            inventory.setItem(48, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lUpgrade Level 4 Erforderlich").addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            inventory.setItem(49, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lUpgrade Level 5 Erforderlich").addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            inventory.setItem(50, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lUpgrade Level 6 Erforderlich").addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
            if (level >= 4)inventory.setItem(48, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lSeite 4").addItemFlag(ItemFlag.HIDE_DYE).build());
            if (level >= 5)inventory.setItem(49, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lSeite 5").addItemFlag(ItemFlag.HIDE_DYE).build());
            if (level >= 6)inventory.setItem(50, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lSeite 6").addItemFlag(ItemFlag.HIDE_DYE).build());
            if (page == 4)inventory.setItem(48, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§f§lSeite 4").addItemFlag(ItemFlag.HIDE_DYE).build());
            if (page == 5)inventory.setItem(49, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§f§lSeite 5").addItemFlag(ItemFlag.HIDE_DYE).build());
            if (page == 6)inventory.setItem(50, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§f§lSeite 6").addItemFlag(ItemFlag.HIDE_DYE).build());
            inventory.setItem(53, new ItemBuilder(Material.BARRIER, 1).setDisplayName("§c§lEnde").addItemFlag(ItemFlag.HIDE_DYE).build());
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
        if (itemStack.getType().equals(Material.PLAYER_HEAD) && itemStack.getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)){
            return true;
        }
        return false;
    }
    public static boolean hasAvaliableSlot(Player player, int howmany) {
        Integer check = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) {
                check++;
            }
        }
        return check >= howmany;
    }
}