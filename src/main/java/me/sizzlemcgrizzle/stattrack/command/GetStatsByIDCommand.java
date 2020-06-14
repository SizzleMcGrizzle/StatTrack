package me.sizzlemcgrizzle.stattrack.command;

import de.craftlancer.core.Utils;
import me.sizzlemcgrizzle.stattrack.StatTrackItem;
import me.sizzlemcgrizzle.stattrack.StatTrackPlugin;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GetStatsByIDCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        
        if (!(sender instanceof Player))
            return false;
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            player.sendMessage(StatTrackPlugin.PREFIX + "You must specify an StatTrack™ ID!");
            return false;
        }
        
        if (StatTrackPlugin.instance.getStatTrackItems().stream().noneMatch(item -> item.getUUID().equals(args[0]))) {
            player.sendMessage(StatTrackPlugin.PREFIX + "You did not specify a valid StatTrack™ item!");
            return false;
        }
        
        StatTrackItem statTrackItem = StatTrackPlugin.instance.getStatTrackItems().stream().filter(item -> item.getUUID().equals(args[0]))
                .findFirst().get();
        
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 2F);
        statTrackItem.getStatsDisplay().forEach(player::sendMessage);
        
        return false;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> uuids = new ArrayList<>();
        StatTrackPlugin.instance.getStatTrackItems().forEach(item -> uuids.add(item.getUUID().toString()));
        
        if (args.length == 1)
            return Utils.getMatches(args[0], uuids);
        
        return new ArrayList<>();
    }
}
