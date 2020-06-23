package me.sizzlemcgrizzle.stattrack;

import me.sizzlemcgrizzle.stattrack.path.StatTrackItemPath;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class StatTrackItem implements ConfigurationSerializable {
    private StatTrackID uuid;
    private StatTrackItemPath path;
    private ItemStack item;
    
    public StatTrackItem(StatTrackID uuid, StatTrackItemPath path, ItemStack item) {
        this.uuid = uuid;
        this.path = path;
        this.item = item;
    }
    
    public StatTrackItem(Map<String, Object> map) {
        uuid = StatTrackID.fromString((String) map.get("uuid"));
        path = (StatTrackItemPath) map.get("path");
        item = (ItemStack) map.get("item");
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        
        map.put("uuid", uuid.toString());
        map.put("path", path);
        map.put("item", item);
        
        return map;
    }
    
    public StatTrackID getUUID() {
        return uuid;
    }
    
    public StatTrackItemPath getPath() {
        return path;
    }
    
    public ItemStack getItem() {
        return item;
    }
    
    public abstract List<String> getStatsDisplay();
    
    public static boolean isStatTrackItem(ItemStack item) {
        StatTrackID id = StatTrackPlugin.getNbtID(item);
        return id != null && StatTrackPlugin.instance.getStatTrackItems().stream().anyMatch(i -> i.getUUID().equals(id));
    }
    
    public static StatTrackItem getStatTrackItem(ItemStack item) {
        StatTrackID id = StatTrackPlugin.getNbtID(item);
        return StatTrackPlugin.instance.getStatTrackItems().stream().filter(i -> i.getUUID().equals(id)).findFirst().get();
    }
}
