package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*
import org.serversmc.parkour.utils.*

object CList : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// List all courses
		CourseManager.getCourses().forEach {
			sender.sendMessage("${GRAY}${it.getId()} ${RED}- ${GRAY}${it.getName()}")
		}
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getLabel(): String = "LIST"
	override fun getPermString(): String = "parkour.list"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getUsage(): String = "/parkour list"
	override fun getDescription(): String = "Lists all the parkour courses"
	override fun hasListener(): Boolean = false
	override fun getSubCmd(): ICommand? = CParkour
	
}