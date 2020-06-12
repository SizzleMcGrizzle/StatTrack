package me.sizzlemcgrizzle.stattrack.command;

import me.sizzlemcgrizzle.stattrack.StatTrackItem;
import me.sizzlemcgrizzle.stattrack.StatTrackPlugin;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GetStatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player))
            return false;
        
        Player player = (Player) sender;
        
        ItemStack item = player.getInventory().getItemInMainHand();
        
        if (item.getType() == Material.AIR || !StatTrackItem.isStatTrackItem(item)) {
            player.sendMessage(StatTrackPlugin.PREFIX + "You must hold a StatTrack™ item!");
            return false;
        }
        
        StatTrackItem statTrackItem = StatTrackItem.getStatTrackItem(item);
        
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 2F);
        statTrackItem.getStatsDisplay().forEach(player::sendMessage);
        
        return false;
    }
}
