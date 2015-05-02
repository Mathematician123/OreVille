package me.Mathematician.OreVille;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class PlayerRightClickEvent implements Listener{
	
	public HashMap<String, IconMenu> playerToInventory = new HashMap<String, IconMenu>();
	
	public static ItemStack Baseball = new ItemStack(Material.STICK, 1);
	
	public static ItemStack Football = new ItemStack(Material.SADDLE, 1);
	
	@SuppressWarnings("deprecation")
	public static ItemStack Basketball = new ItemStack(Material.STAINED_CLAY, 1, DyeColor.ORANGE.getWoolData());
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event){
		Player p = event.getPlayer();
		if(p.getItemInHand().getType() != Material.AIR && (event.getAction() == Action.RIGHT_CLICK_BLOCK 
				|| event.getAction() == Action.RIGHT_CLICK_AIR)){
			if(p.getItemInHand().isSimilar(OreVille.inventory)){
				if(playerToInventory.get(p.getName()) != null){
					IconMenu i = playerToInventory.get(p.getName());
					i.open(p);
				}
				else {
					@SuppressWarnings("deprecation")
					final ItemStack b = new ItemStack(Material.STAINED_CLAY, 1, DyeColor.ORANGE.getWoolData());
					final ItemStack i = new ItemStack(Material.STICK, 1);
					IconMenu menu = new IconMenu(ChatColor.GOLD + "" + ChatColor.BOLD + "Sports Chooser", 
							54, new IconMenu.OptionClickEventHandler() {
			            @Override
			            public void onOptionClick(IconMenu.OptionClickEvent event) {
			            	Player f = event.getPlayer();
			            	String s = event.getName();
			            	if(s.equalsIgnoreCase(ChatColor.WHITE + "" + 
			    					ChatColor.ITALIC + "Baseball... What fun?")){
			            		ArrayList<String> a = new ArrayList<String>();
			    				a.add(ChatColor.GRAY + "" + ChatColor.ITALIC
			    						+ "Better get that home run!");
			    				setLoreandDisplayName(Baseball, ChatColor.WHITE + "" + 
			    						ChatColor.ITALIC + "Baseball... What fun?", a);
			    				f.getInventory().setItem(6, Baseball);
			    				event.setWillClose(true);
			            	}
			            	else if(s.equalsIgnoreCase(ChatColor.RED + "" + ChatColor.ITALIC + "Basketball")){
			            		ArrayList<String> a = new ArrayList<String>();
			    				a.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Better slam that dunk home!");
			    				
			    				setLoreandDisplayName(Basketball, ChatColor.RED + "" + ChatColor.ITALIC + "Basketball", a);
			    				f.getInventory().setItem(6, Basketball);
			    				event.setWillClose(true);
			            	}
			            	else if(s.equalsIgnoreCase(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Football")){
			            		ArrayList<String> a = new ArrayList<String>();
			    				a.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Better get a touchdown!");
			    				
			    				setLoreandDisplayName(Basketball, ChatColor.YELLOW + "" + ChatColor.ITALIC + "Football", a);
			    				f.getInventory().setItem(6, Football);
			    				event.setWillClose(true);
			            	}
			            }
			        }, OreVille.plugin);
					menu.setOption(10, i, ChatColor.WHITE + "" + 
					ChatColor.ITALIC + "Baseball... What fun?", ChatColor.GRAY + "" + ChatColor.ITALIC
					+ "Better get that home run!");
					menu.setOption(12, b, ChatColor.RED + "" + ChatColor.ITALIC + "Basketball", 
							ChatColor.GRAY + "" + ChatColor.ITALIC + "Better slam that dunk home!");
					menu.setOption(14, Football, ChatColor.YELLOW + "" + ChatColor.ITALIC + "Football", 
							ChatColor.GRAY + "" + ChatColor.ITALIC + "Better get a touchdown!");
					menu.open(p);
					playerToInventory.put(p.getName(), menu);
				}
			}
		}
	}
	
	public void setLoreandDisplayName(ItemStack i, String displayName, ArrayList<String> lore){
		ItemMeta is = i.getItemMeta();
		is.setDisplayName(displayName);
		is.setLore(lore);
		i.setItemMeta(is);
	}
	
	public static void deleteIfOld(Hologram h){
		long threeSeecMillis = 29 * 100;
		long elapsedMillis = System.currentTimeMillis() - h.getCreationTimestamp();
		if(elapsedMillis > threeSeecMillis){
			h.delete();
		}
	}
	
	@EventHandler
	public void onPlayerLeftClick(PlayerInteractEvent event){
		Player p = event.getPlayer();
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
			if(p.getItemInHand().getType() == Material.STICK){
				if(Cooldown.isCooling(p.getName(), "Sport")){
					Cooldown.coolDurMessage(p, "Sport");
				}
				else {
				double d = p.getLocation().getY() + 3;
				Hologram g = HologramsAPI.createHologram(OreVille.plugin, new Location(p.getWorld(), 
						p.getLocation().getX(), d, p.getLocation().getZ()));
				Random rand = new Random();
			    int randomNum = rand.nextInt((10 - 1) + 1) + 1;
			    switch(randomNum){
			    case 1: g.appendTextLine(ChatColor.GOLD + "Strike one!"); 
			    p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);break;
			    case 2: g.appendTextLine(ChatColor.GOLD + "Strike two!"); 
			    p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);break;
			    case 3: g.appendTextLine(ChatColor.GOLD + "Strike three!"); 
			    p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);break;
			    case 4: g.appendTextLine(ChatColor.GOLD + "Yerr Out!"); 
			    p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);break;
			    case 5: g.appendTextLine(ChatColor.GOLD + "Yerr Out!"); 
			    p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);break;
			    case 6: g.appendTextLine(ChatColor.GOLD + "Foul ball!"); 
			    p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);break;
			    case 7: g.appendTextLine(ChatColor.GOLD + "HOME RUN!"); 
			    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);break;
			    case 8: g.appendTextLine(ChatColor.GOLD + "OUT OF THE PARK!"); 
			    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);break;
			    case 9: g.appendTextLine(ChatColor.GOLD + "Yerr Out!"); 
			    p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);break;
			    case 10: g.appendTextLine(ChatColor.GOLD + "Yerr Out!"); 
			    p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);break;
			    }
				Cooldown.add(p.getName(), "Sport", 3, System.currentTimeMillis());
			}
			} else if(p.getItemInHand().getType() == Material.STAINED_CLAY){
				if(Cooldown.isCooling(p.getName(), "Sport")){
					Cooldown.coolDurMessage(p, "Sport");
				}
				else {
				Cooldown.add(p.getName(), "Sport", 3, System.currentTimeMillis());
				double d = p.getLocation().getY() + 3;
				Hologram g = HologramsAPI.createHologram(OreVille.plugin, new Location(p.getWorld(), 
						p.getLocation().getX(), d, p.getLocation().getZ()));
				Random rand = new Random();
			    int randomNum = rand.nextInt((10 - 1) + 1) + 1;
			    switch(randomNum){
			    case 1: g.appendTextLine(ChatColor.GOLD + "You got blocked!"); 
			    p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1); break;
			    case 2: g.appendTextLine(ChatColor.GOLD + "NICE STEAL!"); 
			    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);break;
			    case 3: g.appendTextLine(ChatColor.GOLD + "Um, technical foul?");  
			    p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1); break;
			    case 4: g.appendTextLine(ChatColor.GOLD + "Defensive rebound!"); 
			    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);break;
			    case 5: g.appendTextLine(ChatColor.GOLD + "Over da back foul!");  
			    p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);break;
			    case 6: g.appendTextLine(ChatColor.GOLD + "Moving screen, personal foul!");  
			    p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);break;
			    case 7: g.appendTextLine(ChatColor.GOLD + "Air ball!");  
			    p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);break;
			    case 8: g.appendTextLine(ChatColor.GOLD + "HALF COURT SHOT!"); 
			    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);break;
			    case 9: g.appendTextLine(ChatColor.GOLD + "Three point FADEAWAY!"); 
			    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);break;
			    case 10: g.appendTextLine(ChatColor.GOLD + "SLAM DUNK"); 
			    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);break;
			    }
				}
			} else if(p.getItemInHand().getType() == Material.SADDLE){
				if(Cooldown.isCooling(p.getName(), "Sport")){
					Cooldown.coolDurMessage(p, "Sport");
				}
				else {
				Cooldown.add(p.getName(), "Sport", 3, System.currentTimeMillis());
				double d = p.getLocation().getY() + 3;
				Hologram g = HologramsAPI.createHologram(OreVille.plugin, new Location(p.getWorld(), 
						p.getLocation().getX(), d, p.getLocation().getZ()));
				Random rand = new Random();
			    int randomNum = rand.nextInt((10 - 1) + 1) + 1;
			    switch(randomNum){
			    case 1: g.appendTextLine(ChatColor.GOLD + "TOUCHDOWN!"); 
			    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1); break;
			    
			    }
				}
			}
		}
		}
}