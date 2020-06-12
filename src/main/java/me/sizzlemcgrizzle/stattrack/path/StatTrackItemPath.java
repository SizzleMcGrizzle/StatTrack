package me.sizzlemcgrizzle.stattrack.path;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public abstract class StatTrackItemPath implements ConfigurationSerializable {
    
    private int modelData;
    
    public StatTrackItemPath(int modelData) {
        this.modelData = modelData;
    }
    
    public StatTrackItemPath(Map<String, Object> map) {
        modelData = (int) map.get("model_data");
    }
    
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        
        map.put("model_data", modelData);
        
        return map;
    }
    
    public int getModelData() {
        return modelData;
    }
}
