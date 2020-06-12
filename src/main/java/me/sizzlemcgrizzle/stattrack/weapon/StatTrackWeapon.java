package me.sizzlemcgrizzle.stattrack.weapon;

import me.sizzlemcgrizzle.stattrack.StatTrackID;
import me.sizzlemcgrizzle.stattrack.StatTrackItem;
import me.sizzlemcgrizzle.stattrack.StatTrackPlugin;
import me.sizzlemcgrizzle.stattrack.path.StatTrackWeaponPath;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class StatTrackWeapon extends StatTrackItem {
    
    private List<String> statsDisplay = Arrays.asList(
            ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + ChatColor.BOLD + "+--------+" +
                    ChatColor.RESET + " " + StatTrackPlugin.PREFIX +
                    ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + ChatColor.BOLD + "+--------+",
            "",
            ChatColor.GRAY + " - " + ChatColor.GOLD + "Kills: " + ChatColor.YELLOW + "%kills%",
            "",
            ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + ChatColor.BOLD + "+--------+" +
                    ChatColor.RESET + " " + StatTrackPlugin.PREFIX +
                    ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + ChatColor.BOLD + "+--------+"
    );
    
    private int kills;
    
    public StatTrackWeapon(StatTrackID uuid, StatTrackWeaponPath path) {
        super(uuid, path);
        
        this.kills = 0;
    }
    
    public StatTrackWeapon(Map<String, Object> map) {
        super(map);
        
        kills = (int) map.get("kills");
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
