package com.mcblox.parkour.cmds.parkour;

import static org.bukkit.ChatColor.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.mcblox.parkour.objects.BloxCommand;
import com.mcblox.parkour.objects.Course;

public class CmdCreate extends BloxCommand implements Listener {

	private static HashMap<Player, Course> createQueue = new HashMap<Player, Course>();
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		// Initialize variables
		Player player = (Player) sender;
		Course course;
		
		// Check if name is provided
		if (args.length == 1) {
			player.sendMessage(RED + "Please name your course: " + getUsage());
			return;
		}
		
		// Brew name of course
		List<String> rawName = new ArrayList<String>();
		for (String arg : args) {
			rawName.add(arg);
		}
		rawName.remove(0);
		String name = String.join(" ", rawName);
		
		// Check if player is in session for creating a course
		if (createQueue.containsKey(player)) {
			
			// Cancel session
			createQueue.remove(player);
			player.sendMessage(GREEN + "Session canceled.");
			return;
			
		}
		else {
			
			// Check if course name exists
			for (Course find : Course.courses) {
				if (find.getName().equalsIgnoreCase(name)) {
					player.sendMessage(RED + "A course with that name alreay exists!");
					return;
				}
			}
			
			// Create Course object
			course = Course.createCourse(name);
			
			// Create session
			createQueue.put(player, course);
			
		}
		
		// Ask to select block
		player.sendMessage(YELLOW + "[" + GOLD + "LEFT CLICK" + YELLOW + "] the starting pressure plate...");
		player.sendMessage(YELLOW + "Type \"/parkour create\" to cancel session.");
		
		//-- End: execute(CommandSender, String[])
	}

	@Override
	public List<String> tabComplete(Player player, String[] args) {
		List<String> list = new ArrayList<String>();
		if (args.length >= 2) {
			list.add(AQUA + "<name>");
		}
		return list;
	}
	
	@EventHandler
	public void onInteractEvent(PlayerInteractEvent event) {
		
		// Check if action is a click
		if (event.getAction().equals(Action.PHYSICAL)) {
			return;
		}
		
		// Initialize variables
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		// Check if player is in queue
		if (!createQueue.containsKey(player)) {
			return;
		}

		// Cancel event to prevent block breaking
		event.setCancelled(true);
		
		// Check if block is a pressure plate
		if (!block.getType().name().contains("PRESSURE_PLATE")) {
			player.sendMessage(RED + "Please select a pressure plate!");
			return;
		}
		
		// Prompt selected block
		player.sendMessage(GREEN + "Created starting point for course " + GOLD + createQueue.get(player).getName() + GREEN + "!");
		createQueue.get(player).setStartBlock(block);
		
		// Cancel task and session
		createQueue.remove(player);
		
		//-- End: onInteractEvent(PlayerInteractEvent)
	}

	@Override
	public String getLabel() {
		return "CREATE";
	}

	@Override
	public String getPermission() {
		return "mcblox.parkour.create";
	}

	@Override
	public String getUsage() {
		return "/parkour create <name>";
	}

	@Override
	public String getDescription() {
		return "Creates a new parkour course";
	}
	
}
