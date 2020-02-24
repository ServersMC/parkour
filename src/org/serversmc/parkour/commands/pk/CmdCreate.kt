package org.serversmc.parkour.commands.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.interfaces.*
import org.serversmc.parkour.commands.*

object CmdCreate : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// TODO
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getDescription(): String = "Creates a new Parkour course"
	override fun getLabel(): String = "CREATE"
	override fun getPermDefault(): PermissionDefault = PermissionDefault.OP
	override fun getPermString(): String = "parkour.create"
	override fun getSubCmd(): ICommand? = CmdParkour
	override fun getUsage(): String = "/parkour create"
	override fun hasListener(): Boolean = false
	
}