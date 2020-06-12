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
    
    public StatTrackItem(StatTrackID uuid, StatTrackItemPath path) {
        this.uuid = uuid;
        this.path = path;
    }
    
    public StatTrackItem(Map<String, Object> map) {
        uuid = StatTrackID.fromString((String) map.get("uuid"));
        path = (StatTrackItemPath) map.get("path");
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        
        map.put("uuid", uuid.toString());
        map.put("path", path);
        
        return map;
    }
    
    public StatTrackID getUUID() {
        return uuid;
    }
    
    public StatTrackItemPath getPath() {
        return path;
    }
    
    public abstract List<String> getStatsDisplay();
    
    public static boolean isStatTrackItem(ItemStack item) {
        return item.getItemMeta().getLore() != null
                && item.getItemMeta().getLore().stream().anyMatch(lore -> lore.contains("ID: "))
                && StatTrackPlugin.instance.getStatTrackItems().stream()
                .anyMatch(i -> item.getItemMeta().getLore().stream().filter(line -> line.contains("ID: ")).findFirst().get().contains(i.getUUID().toString()));
    }
    
    public static StatTrackItem getStatTrackItem(ItemStack item) {
        StatTrackID uuid = StatTrackID.fromString(item.getItemMeta().getLore().stream().filter(line -> line.contains("ID: ")).findFirst().get().replace("§8ID: ", ""));
        return StatTrackPlugin.instance.getStatTrackItems().stream().filter(i -> i.getUUID().equals(uuid)).findFirst().get();
    }
}
