package me.maxi.mobilebackpack.Manager;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class EcoManager {
    private Economy economy;

    public EcoManager(Economy economy) {
        this.economy = economy;
    }

    public double checkPlayerBalance(OfflinePlayer player) {
        return economy.getBalance(player);
    }

    public void removeMoneyFromPlayer(OfflinePlayer player, double amount) {
        if (economy.has(player, amount)) {
            economy.withdrawPlayer(player, amount);
            Bukkit.getServer().getLogger().info(amount + " wurde von " + player.getName() + " entfernt");
        } else {
            Bukkit.getServer().getLogger().info(player.getName() + " hat nicht genug Geld");
        }
    }
}
