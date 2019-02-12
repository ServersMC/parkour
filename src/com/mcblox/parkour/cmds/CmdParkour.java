package com.mcblox.parkour.cmds;

import static org.bukkit.ChatColor.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.mcblox.parkour.Parkour;
import com.mcblox.parkour.cmds.parkour.CmdCreate;
import com.mcblox.parkour.cmds.parkour.CmdDelete;
import com.mcblox.parkour.cmds.parkour.CmdHelp;
import com.mcblox.parkour.cmds.parkour.CmdHide;
import com.mcblox.parkour.cmds.parkour.CmdRegion;
import com.mcblox.parkour.cmds.parkour.CmdSetFinish;
import com.mcblox.parkour.cmds.parkour.CmdSetSpawn;
import com.mcblox.parkour.cmds.parkour.CmdShow;
import com.mcblox.parkour.objects.BloxCommand;

public class CmdParkour extends BloxCommand {

	public static List<BloxCommand> subCommands = new ArrayList<BloxCommand>();

	public static void setupSubCommands() {
		subCommands.add(new CmdHelp());
		subCommands.add(new CmdCreate());
		subCommands.add(new CmdRegion());
		subCommands.add(new CmdSetFinish());
		subCommands.add(new CmdDelete());
		subCommands.add(new CmdShow());
		subCommands.add(new CmdHide());
		subCommands.add(new CmdSetSpawn());
	}

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
			player.sendMessage(RED + "Try " + getUsage());
			return;
		}

		// Execute sub-command
		boolean found = false;
		for (BloxCommand cmd : subCommands) {
			if (args[0].equalsIgnoreCase(cmd.getLabel())) {
				found = true;
				cmd.execute(sender, args);
				return;
			}
		}

		// If sub-command not found
		if (!found) {
			player.sendMessage(RED + "Sub Command not found, try " + getUsage());
		}

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
			for (BloxCommand cmd : subCommands) {
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
		String[] list = new String[subCommands.size()];
		for (int i = 0; i < list.length; i++) {
			list[i] = subCommands.get(i).getLabel();
		}
		return list;
	}

	// -- End: class CmdParkour
}
