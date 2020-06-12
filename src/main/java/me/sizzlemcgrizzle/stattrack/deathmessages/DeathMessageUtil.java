package me.sizzlemcgrizzle.stattrack.deathmessages;

import me.sizzlemcgrizzle.stattrack.StatTrackPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class DeathMessageUtil implements CommandExecutor {
    
    private File file;
    
    static String PREFIX;
    //Damage caused by being in the area when a block explodes.
    private static String BLOCK_EXPLOSION;
    //Damage caused when an entity contacts a block such as a Cactus.
    private static String CONTACT;
    //Damage caused when an entity is colliding with too many entities due to the maxEntityCramming game rule.
    private static String CRAMMING;
    //Custom damage.
    private static String CUSTOM;
    //Damage caused by a dragon breathing fire.
    private static String DRAGON_BREATH;
    //Damage caused by running out of air while in water
    private static String DROWNING;
    //Damage caused when an player attacks another player.
    private static String PLAYER_ATTACK;
    //Damage caused when a non player living entity attacks a player
    private static String ENTITY_ATTACK;
    //Damage caused by being in the area when an entity, such as a Creeper, explodes.
    private static String ENTITY_EXPLOSION;
    //Damage caused when an entity attacks another entity in a sweep attack.
    private static String ENTITY_SWEEP_ATTACK;
    //Damage caused when an entity falls a distance greater than 3 blocks
    private static String FALL;
    //Damage caused by being hit by a falling block which deals damage
    private static String FALLING_BLOCK;
    //Damage caused by direct exposure to fire
    private static String FIRE;
    //Damage caused due to burns caused by fire
    private static String FIRE_TICK;
    //Damage caused when an entity runs into a wall.
    private static String FLY_INTO_WALL;
    //Damage caused when an entity steps on Material.MAGMA_BLOCK.
    private static String HOT_FLOOR;
    //Damage caused by direct exposure to lava
    private static String LAVA;
    //Damage caused by being struck by lightning
    private static String LIGHTNING;
    //Damage caused by being hit by a damage potion or spell
    private static String MAGIC;
    //Damage caused due to an ongoing poison effect
    private static String POISON;
    //Damage caused by a projectile shot by a player
    private static String PROJECTILE_PLAYER;
    //Damage caused by a projectile shot by a non-player living entity
    private static String PROJECTILE_LIVING;
    //Damage caused by a non living entity shooting a projectile.
    private static String PROJECTILE_NOT_LIVING;
    //Damage caused by starving due to having an empty hunger bar
    private static String STARVATION;
    //Damage caused by being put in a block
    private static String SUFFOCATION;
    //Damage caused by committing suicide using the command "/kill"
    private static String SUICIDE;
    //Damage caused in retaliation to another attack by the Thorns enchantment.
    private static String THORNS;
    //Damage caused by falling into the void
    private static String VOID;
    //Damage caused by Wither potion effect
    private static String WITHER;
    
    public DeathMessageUtil(StatTrackPlugin plugin) {
        this.file = new File(plugin.getDataFolder(), "deathMessages.yml");
        
        deserialize();
    }
    
    private void deserialize() {
        if (!StatTrackPlugin.DEATH_MESSAGE_FILE.exists())
            try {
                InputStream stream = StatTrackPlugin.instance.getResource("deathMessages.yml");
                FileUtils.copyInputStreamToFile(stream, StatTrackPlugin.DEATH_MESSAGE_FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        
        PREFIX = config.getString("prefix");
        CONTACT = config.getString("contact");
        PLAYER_ATTACK = config.getString("player_attack");
        ENTITY_ATTACK = config.getString("entity_attack");
        ENTITY_SWEEP_ATTACK = config.getString("entity_sweep_attack");
        PROJECTILE_PLAYER = config.getString("projectile_player");
        PROJECTILE_LIVING = config.getString("projectile_living");
        PROJECTILE_NOT_LIVING = config.getString("projectile_not_living");
        SUFFOCATION = config.getString("suffocation");
        FALL = config.getString("fall");
        FIRE = config.getString("fire");
        FIRE_TICK = config.getString("fire_tick");
        LAVA = config.getString("lava");
        DROWNING = config.getString("drowning");
        BLOCK_EXPLOSION = config.getString("block_explosion");
        ENTITY_EXPLOSION = config.getString("entity_explosion");
        VOID = config.getString("void");
        LIGHTNING = config.getString("lightning");
        SUICIDE = config.getString("suicide");
        STARVATION = config.getString("suicide");
        POISON = config.getString("poison");
        MAGIC = config.getString("magic");
        WITHER = config.getString("wither");
        FALLING_BLOCK = config.getString("falling_block");
        THORNS = config.getString("thorns");
        DRAGON_BREATH = config.getString("dragon_breath");
        CUSTOM = config.getString("custom");
        FLY_INTO_WALL = config.getString("fly_into_wall");
        HOT_FLOOR = config.getString("hot_floor");
        CRAMMING = config.getString("cramming");
        
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static String getDeathToNonLivingEntityMessage(EntityDamageEvent.DamageCause cause) {
        switch (cause) {
            case BLOCK_EXPLOSION:
                return BLOCK_EXPLOSION;
            case FALL:
                return FALL;
            case FALLING_BLOCK:
                return FALLING_BLOCK;
            case FIRE:
                return FIRE;
            case LAVA:
                return LAVA;
            case VOID:
                return VOID;
            case MAGIC:
                return MAGIC;
            case CUSTOM:
                return CUSTOM;
            case POISON:
                return POISON;
            case THORNS:
                return THORNS;
            case WITHER:
                return WITHER;
            case CONTACT:
                return CONTACT;
            case SUICIDE:
                return SUICIDE;
            case CRAMMING:
                return CRAMMING;
            case DROWNING:
                return DROWNING;
            case FIRE_TICK:
                return FIRE_TICK;
            case HOT_FLOOR:
                return HOT_FLOOR;
            case LIGHTNING:
                return LIGHTNING;
            case STARVATION:
                return STARVATION;
            case SUFFOCATION:
                return SUFFOCATION;
            case DRAGON_BREATH:
                return DRAGON_BREATH;
            case FLY_INTO_WALL:
                return FLY_INTO_WALL;
            case ENTITY_EXPLOSION:
                return ENTITY_EXPLOSION;
            default:
                return "%player% died";
        }
    }
    
    static String getDeathToLivingEntityMessage(String cause) {
        switch (cause.toUpperCase()) {
            case "PLAYER_ATTACK":
                return PLAYER_ATTACK;
            case "ENTITY_ATTACK":
                return ENTITY_ATTACK;
            case "ENTITY_SWEEP_ATTACK":
                return ENTITY_SWEEP_ATTACK;
            case "PROJECTILE_PLAYER":
                return PROJECTILE_PLAYER;
            case "PROJECTILE_LIVING":
                return PROJECTILE_LIVING;
            case "PROJECTILE_NON_LIVING":
                return PROJECTILE_NOT_LIVING;
            default:
                return "%player% died";
        }
    }
    
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
        if (!(sender instanceof Player))
            return false;
        
        Player player = (Player) sender;
        
        if (!player.hasPermission("clstuff.reloaddeathmessages"))
            return false;
        
        deserialize();
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + "&aReloaded death messages."));
        
        return true;
    }
    
}
