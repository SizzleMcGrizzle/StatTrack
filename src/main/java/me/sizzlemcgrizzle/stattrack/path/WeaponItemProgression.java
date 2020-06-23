package me.sizzlemcgrizzle.stattrack.path;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeaponItemProgression implements ConfigurationSerializable {
    
    //Map<Kills, CustomModelData>
    private Map<Integer, Integer> killsForDataMap = new HashMap<>();
    
    public WeaponItemProgression(List<String> progressions) {
        progressions.forEach(progression -> {
            progression = progression.replaceAll("KILLS", "");
            Integer kills = Integer.valueOf(progression.split(" ")[0]);
            Integer modelData = Integer.valueOf(progression.split(" ")[1]);
            
            killsForDataMap.put(kills, modelData);
        });
    }
    
    public WeaponItemProgression(Map<String, Object> map) {
        ((List<String>) map.get("item_progression")).forEach(progression -> {
            progression = progression.replaceAll("KILLS", "");
            Integer kills = Integer.valueOf(progression.split(" ")[0]);
            Integer modelData = Integer.valueOf(progression.split(" ")[1]);
            
            killsForDataMap.put(kills, modelData);
        });
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        
        map.put("item_progression", killsForDataMap.entrySet().stream().map(entry -> entry.getKey() + "KILLS " + entry.getValue()).collect(Collectors.toList()));
        
        return map;
    }
    
    public int getModelData(int kills) {
        return killsForDataMap.getOrDefault(kills, -999);
    }
    
    public List<Integer> getKillThresholds() {
        return new ArrayList<>(killsForDataMap.keySet());
    }
    
    
}
