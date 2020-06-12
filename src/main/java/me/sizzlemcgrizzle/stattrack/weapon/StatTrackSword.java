package me.sizzlemcgrizzle.stattrack.weapon;

import me.sizzlemcgrizzle.stattrack.StatTrackID;
import me.sizzlemcgrizzle.stattrack.path.StatTrackWeaponPath;

import java.util.Map;

public class StatTrackSword extends StatTrackWeapon {
    
    public StatTrackSword(StatTrackID uuid, StatTrackWeaponPath path) {
        super(uuid, path);
    }
    
    public StatTrackSword(Map<String, Object> map) {
        super(map);
    }
}
