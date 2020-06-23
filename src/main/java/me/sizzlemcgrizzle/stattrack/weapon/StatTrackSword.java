package me.sizzlemcgrizzle.stattrack.weapon;

import me.sizzlemcgrizzle.stattrack.StatTrackID;
import me.sizzlemcgrizzle.stattrack.path.StatTrackWeaponPath;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class StatTrackSword extends StatTrackWeapon {
    
    public StatTrackSword(StatTrackID uuid, StatTrackWeaponPath path, ItemStack item) {
        super(uuid, path, item);
    }
    
    public StatTrackSword(Map<String, Object> map) {
        super(map);
    }
}
