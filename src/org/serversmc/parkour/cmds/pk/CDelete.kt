package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.interfaces.*

object CDelete : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getLabel(): String = "DELETE"
	override fun getPermString(): String = "parkour.delete"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getUsage(): String = "/pk delete"
	override fun getDescription(): String = "Deletes selected course"
	override fun hasListener(): Boolean = false
	override fun getSubCmd(): ICommand? = CParkour
	
}