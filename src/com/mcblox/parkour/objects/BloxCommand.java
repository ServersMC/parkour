package com.mcblox.parkour.objects;

import static org.bukkit.ChatColor.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.mcblox.parkour.Parkour;

public abstract class BloxCommand implements CommandExecutor, TabCompleter, Listener {

	// PUBLIC

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender.hasPermission(getPermission())) {
			execute(sender, args);
		}
		else {
			sender.sendMessage(RED + "You dont have permission to use this command!");
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (sender instanceof Player) {
			List<String> list = tabComplete((Player) sender, args);
			if (list == null) {
				return new ArrayList<String>();
			}
			return list;
		}
		return null;
	}

	public abstract void execute(CommandSender sender, String[] args);
	
	public abstract List<String> tabComplete(Player player, String[] args);

	public abstract String getLabel();

	public abstract String getPermission();

	public abstract String getUsage();

	public abstract String getDescription();

	public String[] getTabList() {
		return new String[] {};
	}

	// STATIC

	public static String getDefaultPermission(String label) {
		return Parkour.PLUGIN.getDescription().getCommands().get(label.toLowerCase()).get("permission").toString();
	}

	public static String getDefaultUsage(String label) {
		return Parkour.PLUGIN.getDescription().getCommands().get(label.toLowerCase()).get("usage").toString();
	}

	public static String getDefaultDescription(String label) {
		return Parkour.PLUGIN.getDescription().getCommands().get(label.toLowerCase()).get("description").toString();
	}

}
