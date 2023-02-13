package me.maxi.mobilebackpack.listeners;

import me.maxi.mobilebackpack.Main;
import me.maxi.mobilebackpack.Manager.BackPackManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.List;

public class BackPackSafeListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack bpItem = BackPackManager.openBackPack.get(player);
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        int slot = event.getSlot();
        String UUID = BackPackManager.getUUID(bpItem);
        if (event.getView().getTitle().startsWith(Main.BACKPACKNAME)) {
            if (item.getItemFlags().contains(ItemFlag.HIDE_DYE) && item.getType().equals(Material.GRAY_STAINED_GLASS_PANE)){
                event.setCancelled(true);
            }
            if (item.getItemFlags().contains(ItemFlag.HIDE_DYE) && item.getType().equals(Material.GREEN_STAINED_GLASS)){
                event.setCancelled(true);
                int newPage = (slot-47);
                player.openInventory(BackPackManager.backPack(player, UUID, newPage));
            }
        }
    }
    @EventHandler
    public void onCloseBackPackInv(InventoryCloseEvent event) throws IOException {
        Player player = (Player) event.getPlayer();
        ItemStack backPackItem = BackPackManager.openBackPack.get(player);
        List<String> loreList = backPackItem.getLore();
        assert loreList != null;
        String UUID = BackPackManager.getUUID(backPackItem);
        int level = Main.getConfigManager().getLevel(UUID);
        int pageTitel = Integer.parseInt(event.getView().getTitle().replace(Main.BACKPACKNAME+level+" §7▬§8▪ §aSeite ", ""));
        BackPackManager.saveInv(player, UUID, pageTitel, event.getInventory());
    }
}
