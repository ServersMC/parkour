package com.mcblox.parkour.cmds.parkour;

import static org.bukkit.ChatColor.*;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcblox.parkour.objects.BloxCommand;
import com.mcblox.parkour.objects.Course;
import com.mcblox.parkour.utils.CourseSelect;

public class CmdInfo extends BloxCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		// Initialize variables
		Player player = (Player) sender;
		Course course;
		
		// Check if course is selected
		if (!CourseSelect.contains(player)) {
			CourseSelect.noSelectionMessage(player);
			return;
		}
		
		// Declare course
		course = CourseSelect.get(player);
		
		// Show info
		player.sendMessage(GRAY + "ID: " + GREEN + course.getId());
		player.sendMessage(GRAY + "name: " + GREEN + course.getName());
		player.sendMessage(GRAY + "hasSpawn: " + (course.getSpawn() == null ? RED + "no" : GREEN + "yes"));
		player.sendMessage(GRAY + "hasStart: " + (course.getStartBlock() == null ? RED + "no" : GREEN + "yes"));
		player.sendMessage(GRAY + "hasFinish: " + (course.getFinishBlock() == null ? RED + "no" : GREEN + "yes"));
		player.sendMessage(GRAY + "regions: " + (course.getRegions().size() == 0 ? RED + "0" : GREEN + "" + course.getRegions().size()));
		player.sendMessage(GRAY + "ready: " + (course.isReady() ? GREEN + "yes" : RED + "no"));
		
		// -- End: execute(CommandSender sender, String[])
	}

	@Override
	public List<String> tabComplete(Player player, String[] args) {
		return null;
	}

	@Override
	public String getLabel() {
		return "INFO";
	}

	@Override
	public String getPermission() {
		return "parkour.info";
	}

	@Override
	public String getUsage() {
		return "/parkour info";
	}

	@Override
	public String getDescription() {
		return "Get info of the selected course.";
	}

}
