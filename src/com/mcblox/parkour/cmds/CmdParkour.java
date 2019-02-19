package com.mcblox.parkour.cmds;

import static org.bukkit.ChatColor.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.mcblox.parkour.Parkour;
import com.mcblox.parkour.enums.ParkourSubCommands;
import com.mcblox.parkour.objects.BloxCommand;

public class CmdParkour extends BloxCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		// Display Header
		sender.sendMessage(RED + "Parkour" + GRAY + " - v" + Parkour.VERSION);

		// Check if console is sender
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage(RED + "This is a player only command!");
			return;
		}

		// Initialize variables
		Player player = (Player) sender;

		// Show help if no arguments are given
		if (args.length == 0) {
			ParkourSubCommands.HELP.execute(sender, args);
			return;
		}

		// Execute sub-command
		for (ParkourSubCommands psc : ParkourSubCommands.values()) {
			BloxCommand cmd = psc.getCmd();
			if (cmd.getLabel().equalsIgnoreCase(args[0])) {
				cmd.execute(sender, args);
				return;
			}
		}
		
		// Prompt if sub-command not found
		player.sendMessage(RED + "Sub Command not found, try " + getUsage());
		
		// -- End: execute(CommandSender, String[])
	}

	@Override
	public List<String> tabComplete(Player player, String[] args) {
		List<String> list = new ArrayList<String>();
		if (args.length == 1) {
			for (String label : getTabList()) {
				if (label.toLowerCase().startsWith(args[0].toLowerCase())) {
					list.add(label.toLowerCase());
				}
			}
		}
		if (args.length > 1) {
			for (ParkourSubCommands psc : ParkourSubCommands.values()) {
				BloxCommand cmd = psc.getCmd();
				if (cmd.getLabel().equalsIgnoreCase(args[0])) {
					return cmd.tabComplete(player, args);
				}
			}
		}
		return list;
	}

	@Override
	public String getLabel() {
		return "PARKOUR";
	}

	@Override
	public String getPermission() {
		return getDefaultDescription(getLabel());
	}

	public String getUsage() {
		return getDefaultUsage(getLabel());
	}

	@Override
	public String getDescription() {
		return getDefaultDescription(getLabel());
	}

	@Override
	public String[] getTabList() {
		String[] list = new String[ParkourSubCommands.values().length];
		for (int i = 0; i < list.length; i++) {
			list[i] = ParkourSubCommands.values()[i].getCmd().getLabel();
		}
		return list;
	}

	// -- End: class CmdParkour
}
