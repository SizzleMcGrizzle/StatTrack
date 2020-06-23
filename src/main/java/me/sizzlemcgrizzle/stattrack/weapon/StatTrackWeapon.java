package me.sizzlemcgrizzle.stattrack.weapon;

import me.sizzlemcgrizzle.stattrack.StatTrackID;
import me.sizzlemcgrizzle.stattrack.StatTrackItem;
import me.sizzlemcgrizzle.stattrack.StatTrackPlugin;
import me.sizzlemcgrizzle.stattrack.path.StatTrackWeaponPath;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class StatTrackWeapon extends StatTrackItem {
    
    private List<String> statsDisplay = Arrays.asList(
            StatTrackPlugin.MESSAGE_LINE,
            "",
            ChatColor.GRAY + " - " + ChatColor.GOLD + "Kills: " + ChatColor.YELLOW + "%kills%",
            "",
            StatTrackPlugin.MESSAGE_LINE
    );
    
    private int kills;
    private StatTrackWeaponGUI inventory;
    
    public StatTrackWeapon(StatTrackID uuid, StatTrackWeaponPath path, ItemStack item) {
        super(uuid, path, item);
        
        this.kills = 0;
        this.inventory = new StatTrackWeaponGUI(this);
    }
    
    public StatTrackWeapon(Map<String, Object> map) {
        super(map);
        
        this.kills = (int) map.get("kills");
        this.inventory = new StatTrackWeaponGUI(this);
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        
        map.put("kills", kills);
        
        return map;
    }
    
    public int getKills() {
        return kills;
    }
    
    public void addKill() {
        kills++;
    }
    
    public StatTrackWeaponGUI getInventory() {
        return inventory;
    }
    
    @Override
    public List<String> getStatsDisplay() {
        List<String> list = new ArrayList<>();
        statsDisplay.forEach(line -> {
            line = line.replaceAll("%kills%", String.valueOf(getKills()));
            list.add(line);
        });
        return list;
    }
}
