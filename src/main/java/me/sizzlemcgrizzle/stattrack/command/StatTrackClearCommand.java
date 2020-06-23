package me.sizzlemcgrizzle.stattrack.command;

import de.craftlancer.core.Utils;
import de.craftlancer.core.command.SubCommand;
import me.sizzlemcgrizzle.stattrack.StatTrackItem;
import me.sizzlemcgrizzle.stattrack.StatTrackPlugin;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StatTrackClearCommand extends SubCommand {
    public StatTrackClearCommand(Plugin plugin) {
        super(StatTrackPlugin.ADMIN_PERMISSION, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player))
            return null;
        
        Player player = (Player) sender;
        
        if (args.length < 2)
            return StatTrackPlugin.PREFIX + "You must specify an StatTrack™ ID!";
        
        if (StatTrackPlugin.instance.getStatTrackItems().stream().noneMatch(item -> item.getUUID().equals(args[1])))
            return StatTrackPlugin.PREFIX + "You did not specify a valid StatTrack™ item!";
        
        StatTrackItem statTrackItem = StatTrackPlugin.instance.getStatTrackItems().stream().filter(item -> item.getUUID().equals(args[1]))
                .findFirst().get();
        
        StatTrackPlugin.instance.removeStatTrackItem(statTrackItem);
        
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.5F, 2F);
        return StatTrackPlugin.PREFIX + "You have removed a stat track item.";
    }
    
    @Override
    protected List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2)
            return Utils.getMatches(args[1], StatTrackPlugin.instance.getStatTrackItems().stream().map(item -> item.getUUID().toString()).collect(Collectors.toList()));
        return Collections.emptyList();
    }
    
    @Override
    public void help(CommandSender commandSender) {
    
    }
}
