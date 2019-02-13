package com.mcblox.parkour.cmds.parkour;

import static org.bukkit.ChatColor.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcblox.parkour.objects.BloxCommand;
import com.mcblox.parkour.objects.Course;

public class CmdSetSpawn extends BloxCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		// Initialize variables
		Player player = (Player) sender;
		Course course = null;
		
		if (args.length == 1) {
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
		
		// Prompt message
		player.sendMessage(GREEN + "Updated spawn point for " + GOLD + course.getName() + GREEN + "!");
		
		// Set new spawn for course
		course.setSpawn(player.getLocation());
		
		// -- End: execute(CommandSender sender, args)
	}

	@Override
	public List<String> tabComplete(Player player, String[] args) {
		List<String> list = new ArrayList<String>();
		if (args.length == 2) {
			list.add(AQUA + "<id>");
		}
		return list;
	}

	@Override
	public String getLabel() {
		return "SETSPAWN";
	}

	@Override
	public String getPermission() {
		return "parkour.setspawn";
	}

	@Override
	public String getUsage() {
		return "/parkour setspawn <id>";
	}

	@Override
	public String getDescription() {
		return "Sets the spawn of a course.";
	}

}
