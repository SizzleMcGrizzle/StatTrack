package me.sizzlemcgrizzle.stattrack.command;

import de.craftlancer.core.command.SubCommand;
import me.sizzlemcgrizzle.stattrack.StatTrackItem;
import me.sizzlemcgrizzle.stattrack.StatTrackPlugin;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class StatTrackGetStatsCommand extends SubCommand {
    
    public StatTrackGetStatsCommand(Plugin plugin) {
        super(StatTrackPlugin.DEFAULT_PERMISSION, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player))
            return "You must be a player to use this command";
        
        Player player = (Player) sender;
        
        ItemStack item = player.getInventory().getItemInMainHand();
        
        if (item.getType() == Material.AIR || !StatTrackItem.isStatTrackItem(item))
            return StatTrackPlugin.PREFIX + "You must hold a StatTrack™ item!";
        
        StatTrackItem statTrackItem = StatTrackItem.getStatTrackItem(item);
        
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 2F);
        statTrackItem.getStatsDisplay().forEach(player::sendMessage);
        
        return null;
    }
    
    @Override
    public void help(CommandSender commandSender) {
    
    }
}
