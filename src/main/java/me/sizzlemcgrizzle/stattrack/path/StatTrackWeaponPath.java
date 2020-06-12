package me.sizzlemcgrizzle.stattrack.path;

import me.sizzlemcgrizzle.stattrack.StatTrackPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

public class StatTrackWeaponPath extends StatTrackItemPath {
    
    private boolean trackKills;
    private boolean onlyKillsInAlinor;
    private boolean changeSkin;
    private WeaponItemProgression progression;
    
    public StatTrackWeaponPath(int modelData) {
        super(modelData);
        
        File file = new File(StatTrackPlugin.instance.getDataFolder(), "config.yml");
        
        if (!file.exists())
            try {
                InputStream stream = StatTrackPlugin.instance.getResource("config.yml");
                FileUtils.copyInputStreamToFile(stream, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection(String.valueOf(modelData));
        
        changeSkin = section.contains("change_skin") ? section.getBoolean("change_skin") : false;
        onlyKillsInAlinor = section.contains("only_kills_in_alinor") ? section.getBoolean("only_kills_in_alinor") : false;
        trackKills = section.contains("track_kills") ? section.getBoolean("track_kills") : true;
        progression = section.contains("item_progression") ? new WeaponItemProgression(section.getStringList("item_progression")) : new WeaponItemProgression(Collections.emptyList());
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
