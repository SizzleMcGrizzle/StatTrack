package me.sizzlemcgrizzle.stattrack.path;

import me.sizzlemcgrizzle.stattrack.ConfigPath;
import me.sizzlemcgrizzle.stattrack.StatTrackPlugin;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

public class StatTrackWeaponPath extends StatTrackItemPath {
    
    private boolean trackKills;
    private boolean onlyKillsInAlinor;
    private boolean changeSkin;
    private WeaponItemProgression progression;
    
    public StatTrackWeaponPath(int modelData) {
        super(modelData);
        
        Optional<ConfigPath> optional = StatTrackPlugin.instance.getConfigPaths().stream().filter(p -> p.getModelData() == modelData).findFirst();
        
        if (!optional.isPresent()) {
            Bukkit.getLogger().log(Level.SEVERE, "There is no path to match this weapons model data!", new NullPointerException());
            return;
        }
        
        ConfigPath path = optional.get();
        
        changeSkin = path.isChangeSkin();
        onlyKillsInAlinor = path.isOnlyKillsInAlinor();
        trackKills = path.isTrackKills();
        progression = path.getProgression();
    }
    
    public StatTrackWeaponPath(Map<String, Object> map) {
        super(map);
        
        trackKills = (boolean) map.get("track_kills");
        onlyKillsInAlinor = (boolean) map.get("only_kills_in_alinor");
        changeSkin = (boolean) map.get("change_skin");
        progression = (WeaponItemProgression) map.get("item_progression");
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        
        map.put("track_kills", trackKills);
        map.put("only_kills_in_alinor", onlyKillsInAlinor);
        map.put("change_skin", changeSkin);
        map.put("item_progression", progression);
        
        return map;
    }
    
    
    public boolean isTrackKills() {
        return trackKills;
    }
    
    public void setTrackKills(boolean trackKills) {
        this.trackKills = trackKills;
    }
    
    public boolean isOnlyKillsInAlinor() {
        return onlyKillsInAlinor;
    }
    
    public void setOnlyKillsInAlinor(boolean onlyKillsInAlinor) {
        this.onlyKillsInAlinor = onlyKillsInAlinor;
    }
    
    public boolean isChangeSkin() {
        return changeSkin;
    }
    
    public void setChangeSkin(boolean changeSkin) {
        this.changeSkin = changeSkin;
    }
    
    public WeaponItemProgression getProgression() {
        return progression;
    }
}
