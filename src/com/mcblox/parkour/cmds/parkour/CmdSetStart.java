package com.mcblox.parkour.cmds.parkour;

import static org.bukkit.ChatColor.*;

import java.util.ArrayList;
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
import com.mcblox.parkour.utils.CourseSelect;

public class CmdSetStart extends BloxCommand implements Listener {

	private static List<Player> session = new ArrayList<Player>();
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		// Initialize variables
		Player player = (Player) sender;

		// Check if course is selected
		if (!CourseSelect.contains(player)) {
			CourseSelect.noSelectionMessage(player);
			return;
		}
		
		// Check if player cancelled session
		if (session.contains(player)) {
			player.sendMessage(GREEN + "Session canceled.");
			session.remove(player);
			return;
		}
		
		// Ask to select block
		player.sendMessage(YELLOW + "[" + GOLD + "LEFT CLICK" + YELLOW + "] the start pressure plate...");
		player.sendMessage(YELLOW + "Type \"/parkour setstart\" to cancel session.");

		
		// Add player to queue
		session.add(player);
		
		//-- End: execute(CommandSender, String[])
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
		Course course;

		// Fix double right-click
		if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
			return;
		}
		
		// Check if player is in queue
		if (!session.contains(player)) {
			return;
		}
		
		// Check if course is still selected
		if (!CourseSelect.contains(player)) {
			session.remove(player);
			return;
		}
		
		// Declare course
		course = CourseSelect.get(player);

		// Cancel event to prevent block breaking
		event.setCancelled(true);
		
		// Check if block is a pressure plate
		if (!block.getType().name().endsWith("_PLATE")) {
			player.sendMessage(RED + "Please select a pressure plate!");
			return;
		}
		
		// Check if block is start block
		if (course.getFinishBlock() != null) {
			if (course.getFinishBlock().equals(block)) {
				player.sendMessage(RED + "Finish plate can not be the same as the start block!");
				return;
			}
		}
		
		// Prompt selected block
		player.sendMessage(GREEN + "Created starting point for course " + GOLD + course.getName() + GREEN + "!");
		course.setStartBlock(block);
		
		// Cancel task and session
		session.remove(player);
		
		//-- End: onInteractEvent(PlayerInteractEvent)
	}

	@Override
	public List<String> tabComplete(Player player, String[] args) {
		return null;
	}

	@Override
	public String getLabel() {
		return "SETSTART";
	}

	@Override
	public String getPermission() {
		return "parkour.setstart";
	}

	@Override
	public String getUsage() {
		return "/parkour setstart";
	}

	@Override
	public String getDescription() {
		return "Sets the starting point for a course";
	}

}
