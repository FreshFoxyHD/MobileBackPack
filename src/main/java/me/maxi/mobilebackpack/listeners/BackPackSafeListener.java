package me.maxi.mobilebackpack.listeners;

import me.maxi.mobilebackpack.Main;
import me.maxi.mobilebackpack.Manager.AnimatedInventory;
import me.maxi.mobilebackpack.Manager.BackPackManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
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
                player.openInventory(BackPackManager.putUpgradeItem());
            }
            if (slot == 47){
                int pageTitel = Integer.parseInt(event.getView().getTitle().replace(Main.BACKPACKNAME+level+" §7▬§8▪ §aPage ", ""));
                isInTools.put(player, pageTitel);
                player.openWorkbench(null, true);
            }
            if (slot == 51){
                int pageTitel = Integer.parseInt(event.getView().getTitle().replace(Main.BACKPACKNAME+level+" §7▬§8▪ §aPage ", ""));
                isInTools.put(player, pageTitel);
                player.openAnvil(null, true);
            }
            if (slot == 45 && item.getType().equals(Material.PLAYER_HEAD)){
                int pageTitel = Integer.parseInt(event.getView().getTitle().replace(Main.BACKPACKNAME+level+" §7▬§8▪ §aPage ", ""));
                player.closeInventory();
                player.openInventory(BackPackManager.backPack(player, UUID, pageTitel, 1));
            }
            if (slot == 53 && item.getType().equals(Material.PLAYER_HEAD)){
                int pageTitel = Integer.parseInt(event.getView().getTitle().replace(Main.BACKPACKNAME+level+" §7▬§8▪ §aPage ", ""));
                player.closeInventory();
                player.updateInventory();
                player.openInventory(BackPackManager.backPack(player, UUID, pageTitel, 2));
            }
            if (slot >= 45 && slot <= 53){
                event.setCancelled(true);
            }
        }

        if (event.getView().getTitle().startsWith("§8┃ » §3§lSystem §7▬§8▪ §cWähle")) {
            if (item.getItemFlags().contains(ItemFlag.HIDE_DYE) && item.getType().equals(Material.RED_STAINED_GLASS_PANE)){
                event.setCancelled(true);
            }
            if (item.getItemFlags().contains(ItemFlag.HIDE_DYE) && item.getType().equals(Material.ORANGE_STAINED_GLASS_PANE)){
                event.setCancelled(true);
                ItemStack backpackitem = BackPackManager.openBackPack.get(player);
                double price = Double.valueOf(getPrice(item));
                Main.getEcoManager().removeMoneyFromPlayer(player, price);
                try {
                    player.getInventory().addItem(BackPackManager.onUpgradeBackPack(backpackitem));
                    player.sendMessage("§a§lErfolgreich Upgegradet");
                    player.closeInventory();
                }catch (IOException ignored){
                }
            }
            if (slot == 13){
                event.setCancelled(true);
            }
        }
        if (event.getView().getTitle().equalsIgnoreCase("§8┃ » §3§lSystem §7▬§8▪ §cÜbergebe das Item")){
            if (item.getItemFlags().contains(ItemFlag.HIDE_DYE) && item.getType().equals(Material.LIME_STAINED_GLASS_PANE)){
                if (event.getInventory().getItem(13).equals(Material.AIR) && event.getInventory().getItem(13) == null){
                    event.setCancelled(true);
                }else if (BackPackManager.isBackPack(Objects.requireNonNull(event.getInventory().getItem(13)))) {
                    AnimatedInventory animatedInventory = new AnimatedInventory(player, event.getInventory().getItem(13));
                    animatedInventory.open();
                }else {
                    event.setCancelled(true);
                }
            }
            if (item.getItemFlags().contains(ItemFlag.HIDE_DYE) && (item.getType().equals(Material.GREEN_STAINED_GLASS_PANE) || item.getType().equals(Material.PLAYER_HEAD))){
                event.setCancelled(true);
            }
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
        if (event.getView().getTitle().equalsIgnoreCase("§8┃ » §3§lSystem §7▬§8▪ §cÜbergebe das Item")){
            if (BackPackManager.isBackPack(Objects.requireNonNull(event.getInventory().getItem(13)))) {
                AnimatedInventory animatedInventory = new AnimatedInventory(player, event.getInventory().getItem(13));
                animatedInventory.open();
            }
            return;
        }
        if (!(event.getView().getTitle().contains(Main.BACKPACKNAME)))return;
        ItemStack backPackItem = BackPackManager.openBackPack.get(player);
        List<String> loreList = backPackItem.getLore();
        assert loreList != null;
        String UUID = BackPackManager.getUUID(backPackItem);
        int level = Main.getConfigManager().getLevel(UUID);
        int pageTitel = Integer.parseInt(event.getView().getTitle().replace(Main.BACKPACKNAME+level+" §7▬§8▪ §aPage ", ""));
        BackPackManager.saveInv(player, UUID, pageTitel, event.getInventory());
    }


    public static String getPrice(ItemStack item) {
        List<String> lore = item.getItemMeta().getLore();
        StringBuilder sb = new StringBuilder();
        sb.append(lore.get(1));
        return sb.toString().replace("§7Kaufbar §7- §3$","").replace(",","");
    }
}
