package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.interfaces.*
import org.serversmc.parkour.utils.*

object CSelect : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// Get player instance
		val player = sender as? Player ?: throw(ICommand.PlayerOnlyCommand())
		// Check argument length
		if (args.isEmpty()) {
			SelectManager.remove(player)
			return
		}
		// Translate args to int
		val id = args[0].toIntOrNull() ?: run {
			ErrorMessenger.enterNumber(sender)
			return
		}
		// Get course with ID
		val course = CourseManager.getCourses().singleOrNull { it.getId() == id } ?: run {
			ErrorMessenger.courseIdNotFound(sender, id)
			return
		}
		// Select course to player
		SelectManager.add(player, course)
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getLabel(): String = "SELECT"
	override fun getPermString(): String = "parkour.select"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getUsage(): String = "/pk select <id>"
	override fun getDescription(): String = "Select a parkour course for editing"
	override fun hasListener(): Boolean = false
	override fun getSubCmd(): ICommand? = CParkour
	
}