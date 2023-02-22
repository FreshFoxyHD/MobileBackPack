package me.maxi.mobilebackpack.commands;

import me.maxi.mobilebackpack.manager.BackPackManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class UpgradeBackPackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            try {
                player.getInventory().addItem(BackPackManager.onUpgradeBackPack(player.getItemInHand()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
