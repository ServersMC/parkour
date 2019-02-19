package com.mcblox.parkour.cmds.parkour;

import static org.bukkit.ChatColor.*;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcblox.parkour.objects.BloxCommand;
import com.mcblox.parkour.objects.Course;
import com.mcblox.parkour.utils.CourseSelect;

public class CmdHide extends BloxCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		// Initialize variables
		Player player = (Player) sender;
		Course course = null;
		
		// Check if course is selected
		if (!CourseSelect.contains(player)) {
			CourseSelect.noSelectionMessage(player);
			return;
		}
		
		// Declare course
		course = CourseSelect.get(player);
		
		// Prompt message
		player.sendMessage(GREEN + "Showing " + GOLD + course.getName() + GREEN + "!");
		
		// Show course
		course.hide();
		course.updateVisibility();
	}

	@Override
	public List<String> tabComplete(Player player, String[] args) {
		return null;
	}

	@Override
	public String getLabel() {
		return "HIDE";
	}

	@Override
	public String getPermission() {
		return "parkour.hide";
	}

	@Override
	public String getUsage() {
		return "/parkour hide";
	}

	@Override
	public String getDescription() {
		return "Hide the parkour course";
	}

}
