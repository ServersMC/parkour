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
import org.bukkit.inventory.EquipmentSlot;

import com.mcblox.parkour.objects.BloxCommand;
import com.mcblox.parkour.objects.Course;

public class CmdSetFinish extends BloxCommand implements Listener {

	private static HashMap<Player, Course> finishQueue = new HashMap<Player, Course>();
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		// Initialize variables
		Player player = (Player) sender;
		Course course = null;
		
		if (args.length == 1) {
			if (finishQueue.containsKey(player)) {
				player.sendMessage(GREEN + "Session canceled.");
				finishQueue.remove(player);
				return;
			}
			player.sendMessage(RED + "Please enter course ID.");
			return;
		}
		
		// Check if id is valid
		for (Course find : Course.courses) {
			try {
				if (find.getId() == Integer.parseInt(args[1])) {
					course = find;
				}
			} catch (NumberFormatException e) {
				sender.sendMessage(RED + "Please enter a number for course ID. ");
				return;
			}
		}
		
		// Check if found course
		if (course == null) {
			sender.sendMessage(RED + "Course ID not found!");
			return;
		}
		
		// Ask to select block
		player.sendMessage(YELLOW + "[" + GOLD + "LEFT CLICK" + YELLOW + "] the finishing pressure plate...");
		player.sendMessage(YELLOW + "Type \"/parkour setfinish\" to cancel session.");

		
		// Add player to queue
		finishQueue.put(player, course);
		
		//-- End: execute(CommandSender, String[])
	}

	@Override
	public List<String> tabComplete(Player player, String[] args) {
		List<String> list = new ArrayList<String>();
		if (args.length == 2) {
			list.add(AQUA + "<id>");
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

		// Fix double right-click
		if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
			return;
		}

		// Check if player is in queue
		if (!finishQueue.containsKey(player)) {
			return;
		}

		// cancel event to prevent block breaking
		event.setCancelled(true);
		
		// Check if block is a pressure plate
		if (!block.getType().name().contains("PRESSURE_PLATE")) {
			player.sendMessage(RED + "Please select a pressure plate!");
			return;
		}
		
		// Check if block is start block
		if (finishQueue.get(player).getStartBlock().equals(block)) {
			player.sendMessage(RED + "Finish plate can not be the same as the start block!");
			return;
		}
		
		// Prompt selected block
		player.sendMessage(GREEN + "Created finishing point for course " + GOLD + finishQueue.get(player).getName() + GREEN + "!");
		finishQueue.get(player).setFinishBlock(block);
		
		// Cancel task and session
		finishQueue.remove(player);
		
		//-- End: onInteractEvent(PlayerInteractEvent)
	}

	@Override
	public String getLabel() {
		return "SETFINISH";
	}

	@Override
	public String getPermission() {
		return "parkour.setfinish";
	}

	@Override
	public String getUsage() {
		return "/parkour setfinish <id>";
	}

	@Override
	public String getDescription() {
		return "Adds the finishing plate to the course!";
	}

}
