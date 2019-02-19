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

public class CmdCreate extends BloxCommand {

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
			
		// Check if course name exists
		for (Course find : Course.courses) {
			if (find.getName().equalsIgnoreCase(name)) {
				player.sendMessage(RED + "A course with that name alreay exists!");
				return;
			}
		}
		
		// Create Course object
		course = Course.createCourse(name);
		
		// Select course
		CourseSelect.add(player, course);
		
		// Prompt creation
		player.sendMessage(GREEN + "Created course " + GOLD + course.getName() + GREEN + "!");
		player.sendMessage(GREEN + "Finish setup to save course! Type /parkour info");
		ParkourSubCommands.SELECT.execute(sender, new String[]{"select", course.getId() + ""});
		
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

	@Override
	public String getLabel() {
		return "CREATE";
	}

	@Override
	public String getPermission() {
		return "parkour.create";
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
