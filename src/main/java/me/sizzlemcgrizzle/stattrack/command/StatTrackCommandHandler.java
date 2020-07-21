package me.sizzlemcgrizzle.stattrack.command;

import de.craftlancer.core.command.CommandHandler;
import me.sizzlemcgrizzle.stattrack.StatTrackPlugin;
import me.sizzlemcgrizzle.stattrack.deathmessages.DeathMessageUtil;

public class StatTrackCommandHandler extends CommandHandler {
    
    public StatTrackCommandHandler(StatTrackPlugin plugin) {
        super(plugin);
        
        registerSubCommand("deathmessagereload", new DeathMessageUtil(plugin));
        registerSubCommand("getstats", new StatTrackGetStatsCommand(plugin));
        registerSubCommand("getstatsbyid", new StatTrackGetStatsByIDCommand(plugin));
        registerSubCommand("preferences", new StatTrackPreferencesCommand(plugin));
        registerSubCommand("clear", new StatTrackClearCommand(plugin));
        registerSubCommand("help", new StatTrackHelpCommand(plugin, getCommands()));
    }
}
