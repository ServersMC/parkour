package org.serversmc.parkour.commands

import org.bukkit.command.*
import org.bukkit.entity.*
import org.serversmc.interfaces.*

object CmdHelp : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getDescription(): String = ""
	override fun getLabel(): String = ""
	override fun getPermission(): String = ""
	override fun getUsage(): String = ""
	
}