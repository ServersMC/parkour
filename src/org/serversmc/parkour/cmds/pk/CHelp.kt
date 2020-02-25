package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.interfaces.*

object CHelp : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// Iterate subcommands
		SubCmdManager.getSubCommands(CParkour).forEach {
			// Check if sender has permission
			if (!sender.hasPermission(getPermission())) return
			// Send sub command usage
			sender.sendMessage(it.getUsage() + " - " + it.getDescription())
		}
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getDescription(): String = "Shows this help screen"
	override fun getLabel(): String = "HELP"
	override fun getPermDefault(): PermissionDefault = TRUE
	override fun getPermString(): String = "parkour.help"
	override fun getSubCmd(): ICommand? = CParkour
	override fun getUsage(): String = "/parkour help"
	override fun hasListener(): Boolean = false
	
}