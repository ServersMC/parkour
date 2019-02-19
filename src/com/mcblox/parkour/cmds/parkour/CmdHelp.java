package com.mcblox.parkour.cmds.parkour;

import static org.bukkit.ChatColor.*;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcblox.parkour.enums.ParkourSubCommands;
import com.mcblox.parkour.objects.BloxCommand;

public class CmdHelp extends BloxCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		for (ParkourSubCommands psc : ParkourSubCommands.values()) {
			sender.sendMessage(RED + psc.getCmd().getUsage() + GRAY + " - " + getDescription());
		}
		
		//-- End: execute(CommandSender, String[])
	}

	@Override
	public List<String> tabComplete(Player player, String[] args) {
		return null;
	}

	@Override
	public String getLabel() {
		return "HELP";
	}


	@Override
	public String getPermission() {
		return "parkour.help";
	}

	@Override
	public String getUsage() {
		return "/parkour help";
	}

	@Override
	public String getDescription() {
		return "Shows the list of parkour commands";
	}
}
