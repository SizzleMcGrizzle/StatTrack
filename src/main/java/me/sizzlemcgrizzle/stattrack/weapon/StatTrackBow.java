package me.sizzlemcgrizzle.stattrack.weapon;

import me.sizzlemcgrizzle.stattrack.StatTrackID;
import me.sizzlemcgrizzle.stattrack.path.StatTrackWeaponPath;

import java.util.Map;

public class StatTrackBow extends StatTrackWeapon {
    public StatTrackBow(StatTrackID uuid, StatTrackWeaponPath path) {
        super(uuid, path);
    }
    
    public StatTrackBow(Map<String, Object> map) {
        super(map);
    }
}
