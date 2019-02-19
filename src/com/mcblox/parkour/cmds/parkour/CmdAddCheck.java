package com.mcblox.parkour.cmds.parkour;

import static org.bukkit.ChatColor.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.mcblox.parkour.objects.BloxCommand;
import com.mcblox.parkour.objects.Course;
import com.mcblox.parkour.objects.CourseRegion;
import com.mcblox.parkour.utils.CourseSelect;

public class CmdAddCheck extends BloxCommand {

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
		
		// Check if in session
		if (session.contains(player)) {
			player.sendMessage(GREEN + "Session removed!");
			session.remove(player);
			return;
		}
		
		// Add player to session
		session.add(player);
		
		// Prompt message
		player.sendMessage(GREEN + "Select a plate inside a region to add as checkpoint.");
		player.sendMessage(GREEN + "Type again to cancel.");
		
		// -- End: execute(CommandSender, String[])
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		// Check if action is a click
		if (event.getAction().equals(Action.PHYSICAL)) {
			return;
		}
		
		// Initialize variables
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		Course course;
		CourseRegion region = null;

		// Fix double right-click
		if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
			return;
		}

		// Check if player is in session
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

		// cancel event to prevent block breaking
		event.setCancelled(true);
		
		// Check if block is a pressure plate
		if (!block.getType().name().endsWith("_PLATE")) {
			player.sendMessage(RED + "Please select a pressure plate!");
			return;
		}

		// Get region selected
		for (CourseRegion find : course.getRegions()) {
			if (find.hasBlock(block)) {
				region = find;
			}
		}

		// Check if block is in a region
		if (region == null) {
			player.sendMessage(RED + "This plate is not in a region!");
			return;
		}
		
		// Check if block is a checkpoint
		if (region.getPoint() != null) {
			player.sendMessage(RED + "This region already has a checkpoint!");
			return;
		}
		
		// Prompt selected block
		player.sendMessage(GREEN + "Added checkpoint for course " + GOLD + course.getName() + GREEN + "!");
		region.setPoint(block);
		
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
		return "ADDCHECK";
	}

	@Override
	public String getPermission() {
		return "parkour.addcheck";
	}

	@Override
	public String getUsage() {
		return "/parkour addcheck";
	}

	@Override
	public String getDescription() {
		return "Add Checkpoint to course";
	}

}
