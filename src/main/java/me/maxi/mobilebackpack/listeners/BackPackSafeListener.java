package me.maxi.mobilebackpack.listeners;

import me.maxi.mobilebackpack.Main;
import me.maxi.mobilebackpack.Manager.AnimatedInventory;
import me.maxi.mobilebackpack.Manager.BackPackManager;
import me.maxi.mobilebackpack.Manager.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BackPackSafeListener implements Listener {
    public static HashMap<Player, Integer> isInTools = new HashMap<>();
    public static HashMap<Player, Integer> safeSubPage = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (BackPackManager.openBackPack.get(player) == null)return;
        ItemStack bpItem = BackPackManager.openBackPack.get(player);
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        int slot = event.getSlot();
        String UUID = BackPackManager.getUUID(bpItem);
        int level = Main.getConfigManager().getLevel(UUID);
        if (event.getView().getTitle().startsWith(Main.BACKPACKNAME)) {
            if (item.getItemFlags().contains(ItemFlag.HIDE_DYE) && item.getType().equals(Material.RED_STAINED_GLASS_PANE)){
                event.setCancelled(true);
                int newPage;
                newPage = (slot-47);
                if (safeSubPage.get(player) == 2){
                    newPage = newPage+3;
                }
                player.openInventory(BackPackManager.backPack(player, UUID, newPage, safeSubPage.get(player)));
            }
            if (slot == 46){
                ItemStack nw = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1).setDisplayName("§c§lWAIT").addItemFlag(ItemFlag.HIDE_PLACED_ON).addItemFlag(ItemFlag.HIDE_ENCHANTS).addEnchantment(Enchantment.LUCK,1,true).build();
                replaceInventoryItem(player, BackPackManager.openBackPack.get(player), nw);
                AnimatedInventory animatedInventory = new AnimatedInventory(player, BackPackManager.openBackPack.get(player));
                animatedInventory.open();
            }
            if (slot == 47){
                int pageTitel = Integer.parseInt(event.getView().getTitle().replace(Main.BACKPACKNAME+level+" §7▬§8▪ §aSeite ", ""));
                isInTools.put(player, pageTitel);
                player.openWorkbench(null, true);
            }
            if (slot == 51){
                int pageTitel = Integer.parseInt(event.getView().getTitle().replace(Main.BACKPACKNAME+level+" §7▬§8▪ §aSeite ", ""));
                isInTools.put(player, pageTitel);
                player.openAnvil(null, true);
            }
            if (slot == 45 && item.getType().equals(Material.PLAYER_HEAD)){
                int pageTitel = Integer.parseInt(event.getView().getTitle().replace(Main.BACKPACKNAME+level+" §7▬§8▪ §aSeite ", ""));
                player.closeInventory();
                player.openInventory(BackPackManager.backPack(player, UUID, pageTitel, 1));
            }
            if (slot == 53 && item.getType().equals(Material.PLAYER_HEAD)){
                int pageTitel = Integer.parseInt(event.getView().getTitle().replace(Main.BACKPACKNAME+level+" §7▬§8▪ §aSeite ", ""));
                player.closeInventory();
                player.updateInventory();
                player.openInventory(BackPackManager.backPack(player, UUID, pageTitel, 2));
            }
            if (slot >= 45 && slot <= 53){
                event.setCancelled(true);
            }
        }
        if (event.getView().getTitle().startsWith("§8┃ » §3§lSystem §7▬§8▪ §cWähle")) {
            if (item.getItemFlags().contains(ItemFlag.HIDE_DYE) && item.getType().equals(Material.ORANGE_STAINED_GLASS_PANE)){
                event.setCancelled(true);
                ItemStack backpackitem = BackPackManager.openBackPack.get(player);
                double price = Double.valueOf(getPrice(item));
                Main.getEcoManager().removeMoneyFromPlayer(player, price);
                try {
                    ItemStack nw = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1).setDisplayName("§c§lWAIT").addItemFlag(ItemFlag.HIDE_PLACED_ON).addItemFlag(ItemFlag.HIDE_ENCHANTS).addEnchantment(Enchantment.LUCK,1,true).build();
                    replaceInventoryItem(player,nw,BackPackManager.onUpgradeBackPack(backpackitem));
                    player.sendMessage("§a§lErfolgreich Upgegradet");
                    player.closeInventory();
                }catch (IOException ignored){
                    player.sendMessage("§c§lERROR");
                }
            }else {
                event.setCancelled(true);
            }
            if (slot == 13){
                event.setCancelled(true);
            }
        }
        if (event.getView().getTitle().equalsIgnoreCase("§8┃ » §3§lSystem §7▬§8▪ §cChecking...")){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onCloseBackPackInv(InventoryCloseEvent event) throws IOException {
        Player player = (Player) event.getPlayer();
        if (BackPackManager.openBackPack.get(player) == null)return;
        if (isInTools.get(player) != null){
            if (!(event.getView().getTitle().contains(Main.BACKPACKNAME))){
                Bukkit.getScheduler().runTaskLater(Main.getMain(), () -> {
                    int subpage = safeSubPage.get(player);
                    player.openInventory(BackPackManager.backPack(player, BackPackManager.getUUID(BackPackManager.openBackPack.get(player)),isInTools.get(player), subpage));
                }, 1);
            }
        }
        if (event.getView().getTitle().equalsIgnoreCase("§8┃ » §3§lSystem §7▬§8▪ §cWähle")){
            ItemStack backpackitem = BackPackManager.openBackPack.get(player);
            ItemStack nw = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1).setDisplayName("§c§lWAIT").addItemFlag(ItemFlag.HIDE_PLACED_ON).addItemFlag(ItemFlag.HIDE_ENCHANTS).addEnchantment(Enchantment.LUCK,1,true).build();
            replaceInventoryItem(player,nw,backpackitem);
        }
        if (!(event.getView().getTitle().contains(Main.BACKPACKNAME)))return;
        ItemStack backPackItem = BackPackManager.openBackPack.get(player);
        List<String> loreList = backPackItem.getLore();
        assert loreList != null;
        String UUID = BackPackManager.getUUID(backPackItem);
        int level = Main.getConfigManager().getLevel(UUID);
        int pageTitel = Integer.parseInt(event.getView().getTitle().replace(Main.BACKPACKNAME+level+" §7▬§8▪ §aSeite ", ""));
        BackPackManager.saveInv(player, UUID, pageTitel, event.getInventory());
    }


    public static String getPrice(ItemStack item) {
        List<String> lore = item.getItemMeta().getLore();
        StringBuilder sb = new StringBuilder();
        sb.append(lore.get(0));
        return sb.toString().replace("§7Kaufbar §7- §3$","").replace(",","");
    }
    public void replaceInventoryItem(Player player, ItemStack oldItem, ItemStack newItem) {
        Inventory inventory = player.getInventory();
        int slot = inventory.first(oldItem);
        if (slot != -1) {
            inventory.setItem(slot, newItem);
        }
    }

}
