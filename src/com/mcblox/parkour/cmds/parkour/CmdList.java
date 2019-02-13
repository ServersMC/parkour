package com.mcblox.parkour.cmds.parkour;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mcblox.parkour.objects.BloxCommand;

public class CmdList extends BloxCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
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
