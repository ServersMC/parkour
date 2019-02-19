package com.mcblox.parkour.cmds.parkour;

import static org.bukkit.ChatColor.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.mcblox.parkour.objects.BloxCommand;
import com.mcblox.parkour.objects.Course;
import com.mcblox.parkour.utils.CourseSelect;

public class CmdDelete extends BloxCommand implements Listener {

	private static List<Player> session = new ArrayList<Player>();
	
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

		// Check if player is in session
		if (session.contains(player)) {

			// Check args length is correct
			if (args.length > 1) {

				// Check if cancel is called
				if (args[1].equalsIgnoreCase("cancel")) {

					// Prompt message
					player.sendMessage(GREEN + "Deletion canceled!");

					// Remove player from session
					session.remove(player);
					
					return;
				}
				
			}

			// Prompt Message
			player.sendMessage(GREEN + "Deleted course \"" + GOLD + course.getName() + GREEN + "\"!");

			// Delete course
			Course.deleteCourse(course);
			
			// Remove from queue
			session.remove(player);
			
			// remove player from selection
			CourseSelect.remove(player);

			return;
		}

		// Prompt message
		player.sendMessage(RED + "Are you SURE you want to delete course " + GOLD + course.getName() + RED + "!?");
		player.sendMessage(RED + "Type " + GOLD + "/parkour delete" + RED + " to confirm, or " + GOLD + "/parkour delete cancel" + RED + " to cancel!");

		// Add player to queue
		session.add(player);

	}

	@Override
	public List<String> tabComplete(Player player, String[] args) {
		List<String> list = new ArrayList<String>();
		if (args.length == 2) {
			if (session.contains(player)) {
				list.add("cancel");
			}
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
		return "/parkour delete";
	}

	@Override
	public String getDescription() {
		return "Delete a parkour course";
	}

}
