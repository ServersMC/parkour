package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.interfaces.*

object CCreate : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getDescription(): String = "Creates a new Parkour course"
	override fun getLabel(): String = "CREATE"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getPermString(): String = "parkour.create"
	override fun getSubCmd(): ICommand? = CParkour
	override fun getUsage(): String = "/parkour create <name>"
	override fun hasListener(): Boolean = false
	
}