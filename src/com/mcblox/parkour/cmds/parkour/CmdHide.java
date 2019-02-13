package com.mcblox.parkour.cmds.parkour;

import static org.bukkit.ChatColor.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcblox.parkour.objects.BloxCommand;
import com.mcblox.parkour.objects.Course;

public class CmdHide extends BloxCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		// Initialize variables
		Player player = (Player) sender;
		Course course = null;
		
		// Check if args length is correct
		if (args.length < 2) {
			player.sendMessage(RED + "Invalid usage: " + getUsage());
		}

		// Check if course id is valid
		try {
			course = Course.courses.get(Integer.parseInt(args[1]));
		} catch (NumberFormatException e) {
			sender.sendMessage(RED + "Please enter a number for course ID. ");
			return;
		} catch (IndexOutOfBoundsException e) {
			player.sendMessage(RED + "Course ID not found!");
			return;
		}
		
		// Prompt message
		player.sendMessage(GREEN + "Showing " + GOLD + course.getName() + GREEN + "!");
		
		// Show course
		course.hide();
		course.updateVisibility();
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
		return "HIDE";
	}

	@Override
	public String getPermission() {
		return "parkour.hide";
	}

	@Override
	public String getUsage() {
		return "/parkour hide <id>";
	}

	@Override
	public String getDescription() {
		return "Hide the parkour course";
	}

}
