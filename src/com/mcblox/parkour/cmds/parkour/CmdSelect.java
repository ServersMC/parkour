package com.mcblox.parkour.cmds.parkour;

import static org.bukkit.ChatColor.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcblox.parkour.enums.ParkourSubCommands;
import com.mcblox.parkour.objects.BloxCommand;
import com.mcblox.parkour.objects.Course;
import com.mcblox.parkour.utils.CourseSelect;

public class CmdSelect extends BloxCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {

		// Initialize Variables
		Player player = (Player) sender;
		Course course = null;
		
		// Check args
		if (args.length == 1) {
			if (CourseSelect.contains(player)) {
				player.sendMessage(GREEN + "Removed selection!");
				ParkourSubCommands.HIDE.execute(sender, args);
				CourseSelect.remove(player);
			}
			else {
				player.sendMessage(RED + "Invalid Usage! Type " + getUsage());
			}
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
		
		// Check if course is already selected
		if (CourseSelect.contains(course)) {
			if (!CourseSelect.get(course).equals(player)) {
				player.sendMessage(RED + "Course is already selected by " + GOLD + CourseSelect.get(course).getName() + GREEN + "!");
				return;
			}
		}
		
		// Prompt Message
		player.sendMessage(GREEN + "Selected course " + GOLD + course.getName() + GREEN + "!");
		player.sendMessage(GREEN + "Type " + GOLD + "/parkour select" + GREEN + " to remove selection.");
		
		// Select Course
		CourseSelect.add(player, course);
		ParkourSubCommands.SHOW.execute(sender, args);
		
		// -- End: execute(CommandSender, String[])
	}

	@Override
	public List<String> tabComplete(Player player, String[] args) {
		List<String> list = new ArrayList<String>();
		if (args.length == 2) {
			if (!CourseSelect.contains(player)) {
				list.add(AQUA + "<id>");
			}
		}
		return list;
	}

	@Override
	public String getLabel() {
		return "SELECT";
	}

	@Override
	public String getPermission() {
		return "parkour.select";
	}

	@Override
	public String getUsage() {
		return "/parkour select <id>";
	}

	@Override
	public String getDescription() {
		return "Selected a parkour course for editing";
	}

}
