package me.sizzlemcgrizzle.stattrack.command;

import de.craftlancer.core.command.CommandHandler;
import me.sizzlemcgrizzle.stattrack.StatTrackPlugin;
import me.sizzlemcgrizzle.stattrack.deathmessages.DeathMessageUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatTrackCommandHandler extends CommandHandler {
    
    private String commandName;
    
    public StatTrackCommandHandler(StatTrackPlugin plugin, String commandName) {
        super(plugin);
        
        this.commandName = commandName;
        
        registerSubCommand("deathmessagereload", new DeathMessageUtil(plugin));
        registerSubCommand("getstats", new StatTrackGetStatsCommand(plugin));
        registerSubCommand("getstatsbyid", new StatTrackGetStatsByIDCommand(plugin));
        registerSubCommand("preferences", new StatTrackPreferencesCommand(plugin));
        registerSubCommand("clear", new StatTrackClearCommand(plugin));
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        if ((args.length > 0 && getCommands().containsKey(args[0])) || !(sender instanceof Player))
            return super.onCommand(sender, cmd, label, args);
        
        Player player = (Player) sender;
        
        player.spigot().sendMessage(getHelpMessage((Player) sender).create());
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.5F, 1F);
        
        return true;
    }
    
    private ComponentBuilder getHelpMessage(Player player) {
        ComponentBuilder componentBuilder = new ComponentBuilder();
        
        componentBuilder.append(StatTrackPlugin.MESSAGE_LINE);
        componentBuilder.append("\n");
        componentBuilder.append("\n");
        getCommands().entrySet().stream().filter(entry -> entry.getValue().getPermission().isEmpty() || player.hasPermission(entry.getValue().getPermission())).forEach(entry -> {
            componentBuilder.append(ChatColor.GRAY + "  - " + ChatColor.GOLD + "/" + commandName + " " + entry.getKey());
            componentBuilder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.YELLOW + "Click to run command " + ChatColor.GOLD + "/" + commandName + " " + entry.getKey()).create()));
            componentBuilder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + commandName + " " + entry.getKey()));
            componentBuilder.append("\n");
        });
        componentBuilder.append("\n");
        componentBuilder.append(StatTrackPlugin.MESSAGE_LINE);
        
        return componentBuilder;
    }
}
