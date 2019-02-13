package com.mcblox.parkour.cmds.parkour;

import static org.bukkit.ChatColor.*;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcblox.parkour.objects.BloxCommand;
import com.mcblox.parkour.objects.Course;

public class CmdList extends BloxCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		// Initialize variables
		Player player = (Player) sender;
		List<Course> courses = Course.courses;
		
		// List courses
		for (Course course : courses) {
			player.sendMessage(GRAY + "" + course.getId() + RED + ": " + GRAY + course.getName());
		}
		
	}

	@Override
	public List<String> tabComplete(Player player, String[] args) {
		return null;
	}

	@Override
	public String getLabel() {
		return "LIST";
	}

	@Override
	public String getPermission() {
		return "parkour.list";
	}

	@Override
	public String getUsage() {
		return "/parkour list";
	}

	@Override
	public String getDescription() {
		return "Lists all the courses";
	}

}
