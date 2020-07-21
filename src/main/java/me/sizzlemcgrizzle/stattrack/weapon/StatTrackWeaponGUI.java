package me.sizzlemcgrizzle.stattrack.weapon;

import de.craftlancer.core.Utils;
import de.craftlancer.core.gui.GUIInventory;
import me.sizzlemcgrizzle.stattrack.StatTrackPlugin;
import me.sizzlemcgrizzle.stattrack.path.StatTrackWeaponPath;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StatTrackWeaponGUI {
    
    private StatTrackWeapon weapon;
    private GUIInventory inventory;
    private StatTrackWeaponPath path;
    
    public StatTrackWeaponGUI(StatTrackWeapon weapon) {
        this.weapon = weapon;
        this.path = (StatTrackWeaponPath) weapon.getPath();
    }
    
    public InventoryHolder getInventory() {
        return inventory;
    }
    
    public void createInventory() {
        inventory = new GUIInventory(StatTrackPlugin.instance, StatTrackPlugin.PREFIX + ChatColor.DARK_PURPLE + "Weapon Settings");
        inventory.fill(Utils.buildItemStack(Material.BLACK_STAINED_GLASS_PANE, ChatColor.BLACK + "", Collections.emptyList()));
        
        ItemStack settingsInfoItem = Utils.buildItemStack(Material.LIME_STAINED_GLASS_PANE,
                ChatColor.GREEN + "" + ChatColor.BOLD + "Item Settings",
                Arrays.asList("",
                        ChatColor.GRAY + "Click to enable",
                        ChatColor.GRAY + "or disable settings",
                        ChatColor.GRAY + "below."));
        
        ItemStack skinProgressionItem = Utils.buildItemStack(Material.BLUE_STAINED_GLASS_PANE,
                ChatColor.BLUE + "" + ChatColor.BOLD + "Skin Progression",
                Arrays.asList("",
                        ChatColor.GRAY + "Your item will unlock",
                        ChatColor.GRAY + "these skins after a",
                        ChatColor.GRAY + "certain amount of kills!"));
        
        inventory.setItem(11, settingsInfoItem);
        inventory.setItem(15, skinProgressionItem);
        
        
        //Track Kills item
        inventory.setItem(20, getTrackKillsItem());
        inventory.setClickAction(20, () -> {
            Player player = ((Player) inventory.getInventory().getViewers().stream().findFirst().get());
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 2F);
            path.setTrackKills(!path.isTrackKills());
            inventory.setItem(20, getTrackKillsItem());
        });
        
        inventory.setItem(31, getKillsItem());
        
        //Only keep-inventory kills item
        inventory.setItem(29, getTrackKillsInAlinorItem());
        inventory.setClickAction(29, () -> {
            Player player = ((Player) inventory.getInventory().getViewers().stream().findFirst().get());
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 2F);
            path.setOnlyKillsInAlinor(!path.isOnlyKillsInAlinor());
            inventory.setItem(29, getTrackKillsInAlinorItem());
        });
        
        //Track Kills item
        inventory.setItem(38, getChangeSkinItem());
        inventory.setClickAction(38, () -> {
            Player player = ((Player) inventory.getInventory().getViewers().stream().findFirst().get());
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 2F);
            path.setChangeSkin(!path.isChangeSkin());
            inventory.setItem(38, getChangeSkinItem());
        });
        
        int x = 24;
        for (int i : path.getProgression().getKillThresholds()) {
            if (i < weapon.getKills() || x > 48)
                continue;
            inventory.setItem(x, getCustomizedWeapon(i, path.getProgression().getModelData(i)));
            x = x + 9;
        }
    }
    
    private ItemStack getCustomizedWeapon(int kills, int modelData) {
        ItemStack item = weapon.getItem();
        ItemMeta meta = item.getItemMeta();
        
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Custom Skin");
        meta.setLore(Arrays.asList("",
                ChatColor.YELLOW + "Unlock this skin",
                ChatColor.YELLOW + "at " + ChatColor.RED + kills + ChatColor.YELLOW + " kills if",
                ChatColor.YELLOW + "change skins option",
                ChatColor.YELLOW + "is " + ChatColor.GREEN + "enabled" + ChatColor.YELLOW + ".",
                ""));
        meta.setCustomModelData(modelData);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        
        item.setItemMeta(meta);
        
        return item;
    }
    
    private ItemStack getKillsItem() {
        ItemStack item = Utils.buildItemStack(weapon.getItem().getType(),
                ChatColor.GOLD + "" + ChatColor.BOLD + "Kills: " + ChatColor.YELLOW + "" + ChatColor.BOLD + weapon.getKills(),
                getKillThresholdStringList());
        ItemMeta meta = item.getItemMeta();
        
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        
        item.setItemMeta(meta);
        
        return item;
    }
    
    private ItemStack getTrackKillsItem() {
        return Utils.buildItemStack(path.isTrackKills() ? Material.GREEN_CONCRETE : Material.RED_CONCRETE,
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Track kills: ",
                Arrays.asList("",
                        (path.isTrackKills() ? ChatColor.GREEN + "ENABLED" : ChatColor.RED + "DISABLED"),
                        "",
                        ChatColor.GRAY + "If enabled, the weapon will",
                        ChatColor.GRAY + "track kills."));
    }
    
    private ItemStack getTrackKillsInAlinorItem() {
        return Utils.buildItemStack(path.isOnlyKillsInAlinor() ? Material.GREEN_CONCRETE : Material.RED_CONCRETE,
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Keep-inventory kills only: ",
                Arrays.asList("",
                        (path.isOnlyKillsInAlinor() ? ChatColor.GREEN + "ENABLED" : ChatColor.RED + "DISABLED"),
                        "",
                        ChatColor.GRAY + "If enabled, the weapon will",
                        ChatColor.GRAY + "only track kills in regions",
                        ChatColor.GRAY + "that have keep-inventory",
                        ChatColor.GRAY + "enabled. (Like Alinor)"));
    }
    
    private ItemStack getChangeSkinItem() {
        return Utils.buildItemStack(path.isChangeSkin() ? Material.GREEN_CONCRETE : Material.RED_CONCRETE,
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Change skin: ",
                Arrays.asList("",
                        (path.isChangeSkin() ? ChatColor.GREEN + "ENABLED" : ChatColor.RED + "DISABLED"),
                        "",
                        ChatColor.GRAY + "If enabled, the stat track",
                        ChatColor.GRAY + "item will change skins once",
                        ChatColor.GRAY + "it reaches a certain kill",
                        ChatColor.GRAY + "threshold. (Hover over weapon",
                        ChatColor.GRAY + "to see kill thresholds!)"));
    }
    
    private List<String> getKillThresholdStringList() {
        List<String> list = new ArrayList<>();
        
        list.add("");
        list.add(ChatColor.GREEN + "This item will receive");
        list.add(ChatColor.GREEN + "custom skin(s) at:");
        list.add("");
        path.getProgression().getKillThresholds().forEach(i -> list.add(ChatColor.DARK_GREEN + String.valueOf(i) + " player kills"));
        
        return list;
    }
}
