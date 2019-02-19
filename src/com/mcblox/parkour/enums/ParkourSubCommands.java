package com.mcblox.parkour.enums;

import org.bukkit.command.CommandSender;

import com.mcblox.parkour.cmds.parkour.CmdAddCheck;
import com.mcblox.parkour.cmds.parkour.CmdCreate;
import com.mcblox.parkour.cmds.parkour.CmdDelete;
import com.mcblox.parkour.cmds.parkour.CmdHelp;
import com.mcblox.parkour.cmds.parkour.CmdHide;
import com.mcblox.parkour.cmds.parkour.CmdInfo;
import com.mcblox.parkour.cmds.parkour.CmdList;
import com.mcblox.parkour.cmds.parkour.CmdRegion;
import com.mcblox.parkour.cmds.parkour.CmdSelect;
import com.mcblox.parkour.cmds.parkour.CmdSetFinish;
import com.mcblox.parkour.cmds.parkour.CmdSetSpawn;
import com.mcblox.parkour.cmds.parkour.CmdSetStart;
import com.mcblox.parkour.cmds.parkour.CmdShow;
import com.mcblox.parkour.objects.BloxCommand;

public enum ParkourSubCommands {
	
	ADD_CHECK(new CmdAddCheck()),
	CREATE(new CmdCreate()),
	DELETE(new CmdDelete()),
	HELP(new CmdHelp()),
	HIDE(new CmdHide()),
	INFO(new CmdInfo()),
	LIST(new CmdList()),
	REGION(new CmdRegion()),
	SELECT(new CmdSelect()),
	SET_FINISH(new CmdSetFinish()),
	SET_SPAWN(new CmdSetSpawn()),
	SET_START(new CmdSetStart()),
	SHOW(new CmdShow());

	private BloxCommand command;
	
	private ParkourSubCommands(BloxCommand command) {
		this.command = command;
	}
	
	public void execute(CommandSender sender, String[] args) {
		command.execute(sender, args);
	}

	public BloxCommand getCmd() {
		return command;
	}
	
}
