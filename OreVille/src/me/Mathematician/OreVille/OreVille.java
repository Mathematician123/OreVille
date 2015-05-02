package me.Mathematician.OreVille;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import me.clip.actionannouncer.ActionAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

@SuppressWarnings("deprecation")
public class OreVille extends JavaPlugin implements Listener{
	
	public static Plugin plugin;
	
	public static ItemStack inventory = new ItemStack(Material.COAL,1);
	public static ItemStack potato = new ItemStack(Material.POTATO,1);

	
	Logger logger = Logger.getLogger("Minecraft");
	
	@Override
	public void onEnable(){
		plugin = this;
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Cooldown.handleCooldowns();
            }
        }, 1L, 1L);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	for(Player p : Bukkit.getOnlinePlayers()){
            		if(Cooldown.isCooling(p.getName(), "Sport")){
            			double i = Cooldown.getRemaining(p.getName(), "Sport");
            			if(i > 2.5 && i < 3){
            				ActionAPI.sendPlayerAnnouncement(p, ChatColor.AQUA + "Cooldown:" +
            						ChatColor.GREEN + "█" + ChatColor.RED + "█████ " + 
            						i + 
            						ChatColor.WHITE + " seconds");
            			}
            			else if(i > 2 && i <= 2.5 ){
            				ActionAPI.sendPlayerAnnouncement(p, ChatColor.AQUA + "Cooldown:" +
            						ChatColor.GREEN + "██" + ChatColor.RED + "████ " + 
            						i + 
            						ChatColor.WHITE + " seconds");
            			}
            			else if(i > 1.5 && i < 2){
            				ActionAPI.sendPlayerAnnouncement(p, ChatColor.AQUA + "Cooldown:" +
            						ChatColor.GREEN + "███" + ChatColor.RED + "███ " + 
            						i + 
            						ChatColor.WHITE + " seconds");
            			}
            			else if(i > 1 && i <= 1.5 ){
            				ActionAPI.sendPlayerAnnouncement(p, ChatColor.AQUA + "Cooldown:" +
            						ChatColor.GREEN + "████" + ChatColor.RED + "██ " + 
            						i + 
            						ChatColor.WHITE + " seconds");
            			}
            			else if(i > 0.5 && i <= 1){
            				ActionAPI.sendPlayerAnnouncement(p, ChatColor.AQUA + "Cooldown:" +
            						ChatColor.GREEN + "█████" + ChatColor.RED + "█ " + 
            						i + 
            						ChatColor.WHITE + " seconds");
            			}
            			else if(i >= 0 && i <= 0.5){
            				ActionAPI.sendPlayerAnnouncement(p, ChatColor.AQUA + "Cooldown:" +
            						ChatColor.GREEN + "██████ " + ChatColor.RED +
            						i + 
            						ChatColor.WHITE + " seconds");
            			}
            		}
            	}
            }
        }, 1L, 1L);
		new BukkitRunnable() {

		    @Override
		    public void run() {
		        for (Hologram hologram : HologramsAPI.getHolograms(plugin)) {
		            PlayerRightClickEvent.deleteIfOld(hologram);
		        }
		    }

		}.runTaskTimer(plugin, 20L, 20L);
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new PlayerRightClickEvent(), this);
		logger.info("OreVille has been enabled!");
	}
	
	@Override
	public void onDisable(){
		logger.info("OreVille has been disabled!");
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event){
		event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "OreVille > " + ChatColor.RED + "" + 
	ChatColor.BOLD + "Oops! " + ChatColor.GRAY + "You can't do this!");
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		event.setJoinMessage("");
		Player p = event.getPlayer();
		List<String> lore3 = new ArrayList<String>();
		ItemMeta meta3 = inventory.getItemMeta();
		meta3.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Sports Chooser");
		lore3.add(ChatColor.RED + "" + ChatColor.ITALIC + "Apparently you are bad at the");
		lore3.add(ChatColor.RED + "" + ChatColor.ITALIC + "sport you chose!");
		lore3.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "You can try the sport by using the item in");
		lore3.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "the 7th inventory slot!");
		meta3.setLore(lore3);
		inventory.setItemMeta(meta3);
		p.getInventory().setItem(8, inventory);
		p.sendMessage(ChatColor.DARK_AQUA + "OreVille > " + ChatColor.RED + "Welcome to OreVille! Population: " + 
		String.valueOf(Bukkit.getOnlinePlayers().size()));
		p.sendMessage(ChatColor.DARK_AQUA + "OreVille > " + ChatColor.GREEN + "Oh, and enjoy your potatoes!");
		p.getInventory().clear();
		p.getInventory().setItem(0, potato);
		p.getInventory().setItem(1, inventory);
		for(Player player : Bukkit.getOnlinePlayers()){
			player.hidePlayer(p);
			p.hidePlayer(player);
		}
	}
	
	@EventHandler
	public void onPlayerLeaveEvent(PlayerQuitEvent event){
		event.setQuitMessage("");
	}
	
	@EventHandler
	public void onPlayerDamageEvent(EntityDamageEvent event){
		Entity e = event.getEntity();
		if(e instanceof Player){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerChatEvent(PlayerChatEvent event){
		Player p = event.getPlayer();
		if(!p.hasPermission("OreVille.Chat")){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void playerHungerChange(FoodLevelChangeEvent e) {
	    Player p = (Player) e.getEntity();
	    int before = p.getFoodLevel();
	    int after = e.getFoodLevel();
	    if (before > after) {
	        e.setCancelled(true);
	    }
	}
	
	@EventHandler
	public void playerBreakBlockEvent(BlockBreakEvent event){
		Player player = event.getPlayer();
		if(!player.hasPermission("OreVille.Break")){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void playerPlaceBlockEvent(BlockPlaceEvent event){
		Player player = event.getPlayer();
		if(!player.hasPermission("OreVille.Place")){
			event.setCancelled(true);
		}
	}
}
