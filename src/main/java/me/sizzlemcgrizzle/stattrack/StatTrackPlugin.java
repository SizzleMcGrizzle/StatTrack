package me.sizzlemcgrizzle.stattrack;

import de.craftlancer.clstuff.premium.ModelTokenApplyEvent;
import me.sizzlemcgrizzle.stattrack.command.GetStatsByIDCommand;
import me.sizzlemcgrizzle.stattrack.command.GetStatsCommand;
import me.sizzlemcgrizzle.stattrack.deathmessages.DeathMessageListener;
import me.sizzlemcgrizzle.stattrack.deathmessages.DeathMessageUtil;
import me.sizzlemcgrizzle.stattrack.path.StatTrackWeaponPath;
import me.sizzlemcgrizzle.stattrack.path.WeaponItemProgression;
import me.sizzlemcgrizzle.stattrack.weapon.StatTrackBow;
import me.sizzlemcgrizzle.stattrack.weapon.StatTrackSword;
import me.sizzlemcgrizzle.stattrack.weapon.StatTrackTrident;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatTrackPlugin extends JavaPlugin implements Listener {
    
    public static StatTrackPlugin instance;
    public static File STAT_TRACK_ITEM_FILE;
    public static File DEATH_MESSAGE_FILE;
    public static String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "StatTrack™" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW;
    
    private List<StatTrackItem> statTrackItems;
    
    @Override
    public void onEnable() {
        instance = this;
        STAT_TRACK_ITEM_FILE = new File(this.getDataFolder(), "statTrackItems.yml");
        DEATH_MESSAGE_FILE = new File(this.getDataFolder(), "deathMessages.yml");
        statTrackItems = new ArrayList<>();
        
        ConfigurationSerialization.registerClass(StatTrackSword.class);
        ConfigurationSerialization.registerClass(StatTrackTrident.class);
        ConfigurationSerialization.registerClass(StatTrackBow.class);
        ConfigurationSerialization.registerClass(StatTrackWeaponPath.class);
        ConfigurationSerialization.registerClass(WeaponItemProgression.class);
        
        Bukkit.getPluginManager().registerEvents(new DeathMessageListener(), this);
        Bukkit.getPluginManager().registerEvents(this, this);
        
        getCommand("deathMessageReload").setExecutor(new DeathMessageUtil(this));
        getCommand("getStats").setExecutor(new GetStatsCommand());
        getCommand("getStatsByID").setExecutor(new GetStatsByIDCommand());
        
        registerItems();
    }
    
    @Override
    public void onDisable() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(STAT_TRACK_ITEM_FILE);
        
        config.set("swords", statTrackItems.stream().filter(item -> item instanceof StatTrackSword).collect(Collectors.toList()));
        config.set("bows", statTrackItems.stream().filter(item -> item instanceof StatTrackBow).collect(Collectors.toList()));
        config.set("tridents", statTrackItems.stream().filter(item -> item instanceof StatTrackTrident).collect(Collectors.toList()));
        
        try {
            config.save(STAT_TRACK_ITEM_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void registerItems() {
        if (!STAT_TRACK_ITEM_FILE.exists())
            try {
                InputStream stream = getResource("statTrackItems.yml");
                FileUtils.copyInputStreamToFile(stream, STAT_TRACK_ITEM_FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        
        YamlConfiguration config = YamlConfiguration.loadConfiguration(STAT_TRACK_ITEM_FILE);
        
        statTrackItems.addAll(((List<StatTrackItem>) config.getList("swords")));
        statTrackItems.addAll(((List<StatTrackItem>) config.getList("bows")));
        statTrackItems.addAll(((List<StatTrackItem>) config.getList("tridents")));
    }
    
    public List<StatTrackItem> getStatTrackItems() {
        return statTrackItems;
    }
    
    public void addStatTrackItem(StatTrackItem item) {
        statTrackItems.add(item);
    }
    
    @EventHandler
    public void onModelTokenApply(ModelTokenApplyEvent event) {
        Player player = (Player) event.getPlayer();
        ItemStack item = event.getInput();
        ItemStack token = event.getToken();
        
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        
        if (lore == null)
            lore = new ArrayList<>();
        
        if (StatTrackItem.isStatTrackItem(item)) {
            player.sendMessage(StatTrackPlugin.PREFIX + ChatColor.RED + "This item is already being tracked!");
            event.setCancelled(true);
            return;
        }
        
        StatTrackID id = StatTrackID.randomID();
        
        lore.add("");
        lore.add(ChatColor.YELLOW + "Kills: " + ChatColor.GOLD + "0");
        
        meta.setLore(lore);
        item.setItemMeta(meta);
        
        item = setNbtID(id, item);
        
        
        int modelData = token.getItemMeta().getCustomModelData();
        
        event.setResult(item);
        
        if (item.getType().name().contains("SWORD"))
            addStatTrackItem(new StatTrackSword(id, new StatTrackWeaponPath(modelData)));
        else if (item.getType().name().contains("BOW"))
            addStatTrackItem(new StatTrackBow(id, new StatTrackWeaponPath(modelData)));
        else if (item.getType().name().contains("TRIDENT"))
            addStatTrackItem(new StatTrackTrident(id, new StatTrackWeaponPath(modelData)));
        
        player.sendMessage(StatTrackPlugin.PREFIX + "Your item is now being tracked!");
    }
    
    public static ItemStack setNbtID(StatTrackID id, ItemStack item) {
        net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();
        tag.setString("stat-track-id", id.toString());
        stack.setTag(tag);
        
        return CraftItemStack.asCraftMirror(stack);
    }
    
    public static StatTrackID getNbtID(ItemStack item) {
        net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(item);
        return stack.getTag() == null || !stack.getTag().hasKey("stat-track-id") ? null : StatTrackID.fromString(stack.getTag().getString("stat-track-id"));
    }
}
