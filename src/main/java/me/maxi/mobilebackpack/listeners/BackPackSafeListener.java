package me.maxi.mobilebackpack.listeners;

import me.maxi.mobilebackpack.Main;
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
                player.openInventory(BackPackManager.backPack(player, UUID, pageTitel, 2));
            }
            if (slot >= 45 && slot <= 53){
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
        if (!(event.getView().getTitle().contains(Main.BACKPACKNAME)))return;
        ItemStack backPackItem = BackPackManager.openBackPack.get(player);
        List<String> loreList = backPackItem.getLore();
        assert loreList != null;
        String UUID = BackPackManager.getUUID(backPackItem);
        int level = Main.getConfigManager().getLevel(UUID);
        int pageTitel = Integer.parseInt(event.getView().getTitle().replace(Main.BACKPACKNAME+level+" §7▬§8▪ §aSeite ", ""));
        BackPackManager.saveInv(player, UUID, pageTitel, event.getInventory());
    }
}
