package me.maxi.mobilebackpack.commands;

import me.maxi.mobilebackpack.Main;
import me.maxi.mobilebackpack.Manager.AnimatedInventory;
import me.maxi.mobilebackpack.Manager.BackPackManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class GiveBackPackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player){
            Player player = (Player)sender;
            if (player.hasPermission("mobilebackpack.give")){
                try {
                    if (args.length == 1){
                        AnimatedInventory animatedInventory = new AnimatedInventory(player, BackPackManager.openBackPack.get(player));
                        animatedInventory.open();
                        //BackPackManager.createBackPackByUUID(args[0], player, player);
                    }else {
                        BackPackManager.createBackPack(player, player);
                    }
                } catch (IOException e) {
                    player.sendMessage("Error creating the backpack, please check the configuration file and/or restart/reinstall the plugin.");
                }
            }else {
                player.sendMessage("§cFor this you do not have the necessary permissions");
            }
        }else {
            Player player = Bukkit.getPlayer(args[0]);
            if (player != null){
                try {
                    BackPackManager.createBackPack(sender, player);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                sender.sendMessage("§cPlayer "+args[0]+" is null");
            }
        }
        return true;
    }
}
