package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.interfaces.*

object CSelect : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getLabel(): String = "SELECT"
	override fun getPermString(): String = "parkour.select"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getUsage(): String = "/parkour select <course_id>"
	override fun getDescription(): String = "Select a parkour course for editing"
	override fun hasListener(): Boolean = false
	override fun getSubCmd(): ICommand? = CParkour
	
}