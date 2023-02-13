package me.maxi.mobilebackpack.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveBackPackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player){
            Player player = (Player)sender;
            if (player.hasPermission("mobilebackpack.give")){

            }else {
                player.sendMessage("§cFor this you do not have the necessary permissions");
            }
        }
        return true;
    }
}
