package me.maxi.mobilebackpack.Manager;

import me.maxi.mobilebackpack.Main;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class AnimatedInventory {
    private final Player player;
    private final Inventory inventory;
    private final ItemStack fixedItem;
    private int taskId;

    public AnimatedInventory(Player player, ItemStack fixedItem) {
        this.player = player;
        this.fixedItem = fixedItem;
        this.inventory = Bukkit.createInventory(player, 27, "§8┃ » §3§lSystem §7▬§8▪ §cChecking...");
    }
    public void open() {
        // Set the fixed item in slot 13
        inventory.setItem(13, fixedItem);
        // Fill the other slots with glass panes to create animation
        for (int i = 0; i < 27; i++) {
            if (i != 13) {
                inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
            }
        }
        player.openInventory(inventory);
        // Start the animation task
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugins()[0], () -> {
            Random random = new Random();
            Material[] materials = new Material[]{Material.BLACK_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE,
                    Material.BLUE_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE,
                    Material.YELLOW_STAINED_GLASS_PANE};
            for (int i = 0; i < 27; i++) {
                if (i != 13) {
                    Material randomMaterial = materials[random.nextInt(materials.length)];
                    inventory.setItem(i, new ItemStack(randomMaterial, 1));
                }
            }
        }, 0, 5);
        // Stop the animation task after 10 seconds
        Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugins()[0],this::close, 20 * 10);
    }
    public void close() {
        // Stop the animation task
        Bukkit.getScheduler().cancelTask(taskId);
        player.openInventory(upGradeScreen(fixedItem));
    }

    public Inventory upGradeScreen (ItemStack bp){
        String uuid = BackPackManager.getUUID(bp);
        int level = Main.getConfigManager().getLevel(uuid);
        Inventory inventory = Bukkit.createInventory(player, ((9*5)-1), "§8┃ » §3§lSystem §7▬§8▪ §cChoose");
        ItemStack Glass = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setDisplayName(" ").addItemFlag(ItemFlag.HIDE_DYE).build();
        for (int i = 0; i <= ((9*5)-1); i++) {
            inventory.setItem(i, Glass);
        }
        inventory.setItem(13, bp);
        inventory.setItem(29, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lNot enough money §7(§3§"+Main.level2+"§7)").addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(30, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lNot enough money §7(§3§"+Main.level3+"§7)").addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(31, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lNot enough money §7(§3§"+Main.level4+"§7)").addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(32, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lNot enough money §7(§3§"+Main.level5+"§7)").addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(33, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lNot enough money §7(§3§"+Main.level6+"§7)").addItemFlag(ItemFlag.HIDE_DYE).build());

        if (Main.getEcoManager().checkPlayerBalance(player) >= Main.level2)inventory.setItem(29, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE, 1).setDisplayName("§6§lBuy it for §3§"+Main.level2).addItemFlag(ItemFlag.HIDE_DYE).build());
        if (Main.getEcoManager().checkPlayerBalance(player) >= Main.level3)inventory.setItem(30, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE, 1).setDisplayName("§6§lBuy it for §3§"+Main.level3).addItemFlag(ItemFlag.HIDE_DYE).build());
        if (Main.getEcoManager().checkPlayerBalance(player) >= Main.level4)inventory.setItem(31, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE, 1).setDisplayName("§6§lBuy it for §3§"+Main.level4).addItemFlag(ItemFlag.HIDE_DYE).build());
        if (Main.getEcoManager().checkPlayerBalance(player) >= Main.level5)inventory.setItem(32, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE, 1).setDisplayName("§6§lBuy it for §3§"+Main.level5).addItemFlag(ItemFlag.HIDE_DYE).build());
        if (Main.getEcoManager().checkPlayerBalance(player) >= Main.level6)inventory.setItem(33, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE, 1).setDisplayName("§6§lBuy it for §3§"+Main.level6).addItemFlag(ItemFlag.HIDE_DYE).build());

        if (level == 2)inventory.setItem(29, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§6§lAlready owned").addItemFlag(ItemFlag.HIDE_DYE).build());
        if (level == 3)inventory.setItem(30, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§6§lAlready owned").addItemFlag(ItemFlag.HIDE_DYE).build());
        if (level == 4)inventory.setItem(31, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§6§lAlready owned").addItemFlag(ItemFlag.HIDE_DYE).build());
        if (level == 5)inventory.setItem(32, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§6§lAlready owned").addItemFlag(ItemFlag.HIDE_DYE).build());
        if (level == 6)inventory.setItem(33, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§6§lAlready owned").addItemFlag(ItemFlag.HIDE_DYE).build());


        return inventory;
    }
}