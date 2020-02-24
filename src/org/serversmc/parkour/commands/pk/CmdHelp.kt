package org.serversmc.parkour.commands.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.interfaces.*
import org.serversmc.parkour.commands.*

object CmdHelp : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// TODO
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getDescription(): String = "Shows this help screen"
	override fun getLabel(): String = "HELP"
	override fun getPermDefault(): PermissionDefault = PermissionDefault.TRUE
	override fun getPermString(): String = "parkour.help"
	override fun getSubCmd(): ICommand? = CmdParkour
	override fun getUsage(): String = "/parkour help"
	override fun hasListener(): Boolean = false
	
}