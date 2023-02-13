package me.maxi.mobilebackpack.listeners;

import me.maxi.mobilebackpack.Manager.BackPackManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BackPackOpenerListener implements Listener {
    @EventHandler
    public void interactBlock(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (BackPackManager.isBackPack(player.getInventory().getItemInOffHand()))event.setCancelled(true);
        if (BackPackManager.isBackPack(player.getItemInHand())){
            if (event.getClickedBlock() == null || event.getClickedBlock().getType().equals(Material.AIR) || !(event.getClickedBlock().getType().equals(Material.ENDER_CHEST))){
                event.setCancelled(true);
                if (BackPackManager.openBackPack.get(player) != null)BackPackManager.openBackPack.remove(player);
                BackPackManager.openBackPack.putIfAbsent(player, player.getItemInHand());
                String uuid = BackPackManager.getUUID(player.getItemInHand());
                player.openInventory(BackPackManager.backPack(player,uuid,1));
            }
        }
    }
}
