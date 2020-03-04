package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*
import org.serversmc.parkour.utils.*

object CSelect : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// Get player instance
		val player = sender as? Player ?: throw(ICommand.PlayerOnlyCommand())
		// Check argument length
		if (args.isEmpty()) {
			// Check if player selected a course
			if (CourseSelect.contains(player)) {
				// Get course selected
				val course = CourseSelect.get(player)!!
				// Deselect player
				CourseSelect.remove(player)
				// Prompt message
				player.sendMessage("${GREEN}Deselected course $GRAY${course.getName()}")
			}
			return
		}
		// Translate args to int
		val id = args[0].toIntOrNull() ?: run {
			ErrorMessage.enterNumber(sender)
			return
		}
		// Get course with ID
		val course = CourseManager.getCourses().singleOrNull { it.getId() == id } ?: run {
			ErrorMessage.courseIdNotFound(sender, id)
			return
		}
		// Select course to player
		CourseSelect.add(player, course)
		// Prompt message
		player.sendMessage("${GREEN}Selected course $GRAY${course.getName()}")
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