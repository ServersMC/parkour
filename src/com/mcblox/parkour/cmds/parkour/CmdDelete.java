package com.mcblox.parkour.cmds.parkour;

import static org.bukkit.ChatColor.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcblox.parkour.objects.BloxCommand;
import com.mcblox.parkour.objects.Course;

public class CmdDelete extends BloxCommand {

	private static HashMap<Player, Course> players = new HashMap<Player, Course>();

	@Override
	public void execute(CommandSender sender, String[] args) {

		// Initialize variables
		Player player = (Player) sender;
		Course course = null;

		// Check if player is in session
		if (players.containsKey(player)) {

			// Check args length is correct
			if (args.length > 1) {

				// Check if cancel is called
				if (args[1].equalsIgnoreCase("cancel")) {

					// Prompt message
					player.sendMessage(GREEN + "Deletion canceled!");

					// Remove player from session
					players.remove(player);
					
					return;
				}
				
			}

			// Prompt Message
			player.sendMessage(GREEN + "Deleted course \"" + GOLD + players.get(player).getName() + GREEN + "\"!");

			// Delete course
			Course.deleteCourse(players.get(player));
			
			// Remove from queue
			players.remove(player);

			return;
		}

		// Check if player provided ID
		if (args.length == 1) {
			player.sendMessage(RED + "Please enter course ID!");
			return;
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
		player.sendMessage(RED + "Are you SURE you want to delete course " + GOLD + course.getName() + RED + "!?");
		player.sendMessage(RED + "Type " + GOLD + "/parkour delete" + RED + " to confirm, or " + GOLD + "/parkour delete cancel" + RED + " to cancel!");

		// Add player to queue
		players.put(player, course);

	}

	@Override
	public List<String> tabComplete(Player player, String[] args) {
		List<String> list = new ArrayList<String>();
		if (args.length == 2) {
			if (players.containsKey(player)) {
				list.add("cancel");
				return list;
			}
			list.add(AQUA + "<id>");
		}
		return list;
	}

	@Override
	public String getLabel() {
		return "DELETE";
	}

	@Override
	public String getPermission() {
		return "parkour.delete";
	}

	@Override
	public String getUsage() {
		return "/parkour delete <id>";
	}

	@Override
	public String getDescription() {
		return "Delete a parkour course";
	}

}
