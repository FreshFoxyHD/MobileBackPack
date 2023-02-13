package me.maxi.mobilebackpack.listeners;

import me.maxi.mobilebackpack.Main;
import me.maxi.mobilebackpack.Manager.JsonReader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (player.getUniqueId().toString().equals("7098b2dd-3a46-41fd-9f86-57a6baad64a4")){//FreshFoxy
            Bukkit.getScheduler().runTaskLater(Main.getMain(), () -> {
                player.sendMessage("§c§m------------------------------");
                player.sendMessage("§aPlugin Aktiv!");
                player.sendMessage("§a"+Main.getMain().getName()+" §7- §av"+ Main.getMain().getDescription().getVersion());
                try {
                    player.sendMessage("§6LastVersion: v"+ JsonReader.getLastVersion());
                } catch (IOException ignored) {
                    player.sendMessage("§cJsonReader faild");
                }
                player.sendMessage("§7("+Main.getMain().getServer().getIp()+":"+Main.getMain().getServer().getPort()+"§7)");
                player.sendMessage("§c§m------------------------------");
            }, 5*20);
        }
    }
}
