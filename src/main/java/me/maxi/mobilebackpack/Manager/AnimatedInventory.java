package me.maxi.mobilebackpack.Manager;

import me.maxi.mobilebackpack.Main;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
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
                    ItemStack Glass = new ItemBuilder(randomMaterial, 1).setDisplayName(" ").addItemFlag(ItemFlag.HIDE_DYE).build();
                    inventory.setItem(i, Glass);
                }
            }
        }, 0, 5);
        // Stop the animation task after 10 seconds
        Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugins()[0],this::close, 20 * 10);
    }
    public void close() {
        // Stop the animation task
        Bukkit.getScheduler().cancelTask(taskId);
        Bukkit.getScheduler().runTaskLater(Main.getMain(), () -> {
            player.openInventory(upGradeScreen(fixedItem));
            }, 5);
    }

    public Inventory upGradeScreen (ItemStack bp){
        String uuid = BackPackManager.getUUID(bp);
        int level = Main.getConfigManager().getLevel(uuid);
        Inventory inventory = Bukkit.createInventory(player, 9*5, "§8┃ » §3§lSystem §7▬§8▪ §cWähle");
        ItemStack Glass = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setDisplayName(" ").addItemFlag(ItemFlag.HIDE_DYE).build();
        for (int i = 0; i <= ((9*5)-1); i++) {
            inventory.setItem(i, Glass);
        }
        inventory.setItem(13, bp);
        inventory.setItem(29, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lNicht Kaufbar").setLore("§7Kosten §7- §3$"+String.format("%,.0f", Main.level2)).addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(30, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lNicht Kaufbar").setLore("§7Kosten §7- §3$"+String.format("%,.0f", Main.level3)).addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(31, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lNicht Kaufbar").setLore("§7Kosten §7- §3$"+String.format("%,.0f", Main.level4)).addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(32, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lNicht Kaufbar").setLore("§7Kosten §7- §3$"+String.format("%,.0f", Main.level5)).addItemFlag(ItemFlag.HIDE_DYE).build());
        inventory.setItem(33, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName("§c§lNicht Kaufbar").setLore("§7Kosten §7- §3$"+String.format("%,.0f", Main.level6)).addItemFlag(ItemFlag.HIDE_DYE).build());

        if (level == 1 && Main.getEcoManager().checkPlayerBalance(player) >= Main.level2)inventory.setItem(29, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE, 1).setDisplayName("§6§lLevel 2").addItemFlag(ItemFlag.HIDE_DYE).addEnchantment(Enchantment.LUCK, 1, true).addItemFlag(ItemFlag.HIDE_ENCHANTS).setLore("§7Kaufbar §7- §3$"+String.format("%,.0f", Main.level2)).build());
        if (level == 2 && Main.getEcoManager().checkPlayerBalance(player) >= Main.level3)inventory.setItem(30, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE, 1).setDisplayName("§6§lLevel 3").addItemFlag(ItemFlag.HIDE_DYE).addEnchantment(Enchantment.LUCK, 1, true).addItemFlag(ItemFlag.HIDE_ENCHANTS).setLore("§7Kaufbar §7- §3$"+String.format("%,.0f", Main.level3)).build());
        if (level == 3 && Main.getEcoManager().checkPlayerBalance(player) >= Main.level4)inventory.setItem(31, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE, 1).setDisplayName("§6§lLevel 4").addItemFlag(ItemFlag.HIDE_DYE).addEnchantment(Enchantment.LUCK, 1, true).addItemFlag(ItemFlag.HIDE_ENCHANTS).setLore("§7Kaufbar §7- §3$"+String.format("%,.0f", Main.level4)).build());
        if (level == 4 && Main.getEcoManager().checkPlayerBalance(player) >= Main.level5)inventory.setItem(32, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE, 1).setDisplayName("§6§lLevel 5").addItemFlag(ItemFlag.HIDE_DYE).addEnchantment(Enchantment.LUCK, 1, true).addItemFlag(ItemFlag.HIDE_ENCHANTS).setLore("§7Kaufbar §7- §3$"+String.format("%,.0f", Main.level5)).build());
        if (level == 5 && Main.getEcoManager().checkPlayerBalance(player) >= Main.level6)inventory.setItem(33, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE, 1).setDisplayName("§6§lLevel 6").addItemFlag(ItemFlag.HIDE_DYE).addEnchantment(Enchantment.LUCK, 1, true).addItemFlag(ItemFlag.HIDE_ENCHANTS).setLore("§7Kaufbar §7- §3$"+String.format("%,.0f", Main.level6)).build());

        if (level >= 2)inventory.setItem(29, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§c§lLevel 2").addItemFlag(ItemFlag.HIDE_DYE).setLore("§c§lBereits im Besitz").setLore("§cGekauft §7- §3$"+String.format("%,.0f", Main.level2)).build());
        if (level >= 3)inventory.setItem(30, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§c§lLevel 3").addItemFlag(ItemFlag.HIDE_DYE).setLore("§c§lBereits im Besitz").setLore("§cGekauft §7- §3$"+String.format("%,.0f", Main.level3)).build());
        if (level >= 4)inventory.setItem(31, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§c§lLevel 4").addItemFlag(ItemFlag.HIDE_DYE).setLore("§c§lBereits im Besitz").setLore("§cGekauft §7- §3$"+String.format("%,.0f", Main.level4)).build());
        if (level >= 5)inventory.setItem(32, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§c§lLevel 5").addItemFlag(ItemFlag.HIDE_DYE).setLore("§c§lBereits im Besitz").setLore("§cGekauft §7- §3$"+String.format("%,.0f", Main.level5)).build());
        if (level >= 6)inventory.setItem(33, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("§c§lLevel 6").addItemFlag(ItemFlag.HIDE_DYE).setLore("§c§lBereits im Besitz").setLore("§cGekauft §7- §3$"+String.format("%,.0f", Main.level6)).build());

        return inventory;
    }
}