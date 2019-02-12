package com.mcblox.parkour.cmds.parkour;

import static org.bukkit.ChatColor.*;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcblox.parkour.cmds.CmdParkour;
import com.mcblox.parkour.objects.BloxCommand;

public class CmdHelp extends BloxCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		for (BloxCommand cmd : CmdParkour.subCommands) {
			sender.sendMessage(RED + cmd.getUsage() + GRAY + " - " + getDescription());
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
		return "mcblox.parkour.help";
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
