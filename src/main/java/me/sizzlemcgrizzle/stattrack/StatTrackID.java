package me.sizzlemcgrizzle.stattrack;

import java.util.Random;

public class StatTrackID {
    
    private String ID;
    
    private StatTrackID(String ID) {
        this.ID = ID;
    }
    
    public static StatTrackID randomID() {
        Random rnd = new Random();
        
        StringBuilder newID = new StringBuilder();
        
        for (int i = 0; i < 9; i++) {
            if (i == 4)
                newID.append("-");
            else if (i % 2 == 0)
                newID.append((char) (rnd.nextInt(26) + 'a'));
            else
                newID.append(rnd.nextInt(10));
        }
        
        if (StatTrackPlugin.instance.getStatTrackItems().stream().anyMatch(item -> item.getUUID().toString().equalsIgnoreCase(newID.toString())))
            return randomID();
        
        return new StatTrackID(newID.toString().toUpperCase());
    }
    
    public static StatTrackID fromString(String string) {
        if (string.length() != 9)
            throw new IllegalArgumentException("Given string must be 9 characters long!: " + string);
        
        return new StatTrackID(string);
    }
    
    @Override
    public String toString() {
        return ID;
    }
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof String || obj instanceof StatTrackID) && toString().equals(obj.toString());
    }
    
    public boolean equals(String string) {
        return toString().equals(string);
    }
}
