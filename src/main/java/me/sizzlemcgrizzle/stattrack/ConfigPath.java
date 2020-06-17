package me.sizzlemcgrizzle.stattrack;

import me.sizzlemcgrizzle.stattrack.path.WeaponItemProgression;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collections;

public class ConfigPath {
    
    private int modelData;
    private boolean trackKills;
    private boolean onlyKillsInAlinor;
    private boolean changeSkin;
    private WeaponItemProgression progression;
    
    public ConfigPath(ConfigurationSection section) {
        this.modelData = Integer.parseInt(section.getName());
        this.changeSkin = section.contains("change_skin") ? section.getBoolean("change_skin") : false;
        this.onlyKillsInAlinor = section.contains("only_kills_in_alinor") ? section.getBoolean("only_kills_in_alinor") : false;
        this.trackKills = section.contains("track_kills") ? section.getBoolean("track_kills") : true;
        this.progression = section.contains("item_progression") ? new WeaponItemProgression(section.getStringList("item_progression")) : new WeaponItemProgression(Collections.emptyList());
    }
    
    public int getModelData() {
        return modelData;
    }
    
    public boolean isTrackKills() {
        return trackKills;
    }
    
    public boolean isOnlyKillsInAlinor() {
        return onlyKillsInAlinor;
    }
    
    public boolean isChangeSkin() {
        return changeSkin;
    }
    
    public WeaponItemProgression getProgression() {
        return progression;
    }
    
    
}
