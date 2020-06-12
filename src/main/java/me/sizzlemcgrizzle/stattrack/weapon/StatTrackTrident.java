package me.sizzlemcgrizzle.stattrack.weapon;

import me.sizzlemcgrizzle.stattrack.StatTrackID;
import me.sizzlemcgrizzle.stattrack.path.StatTrackWeaponPath;

import java.util.Map;

public class StatTrackTrident extends StatTrackWeapon {
    public StatTrackTrident(StatTrackID uuid, StatTrackWeaponPath path) {
        super(uuid, path);
    }
    
    public StatTrackTrident(Map<String, Object> map) {
        super(map);
        
    }
}
