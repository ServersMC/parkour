package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*
import org.serversmc.parkour.objects.*
import org.serversmc.parkour.utils.*

object CSetSpawn : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// Initialize variables
		val player: Player = sender as? Player ?: throw(ICommand.PlayerOnlyCommand("Please select a course, using course_id"))
		val course: Course
		// Check args
		if (args.isEmpty()) {
			// Check if player has a course selected
			course = SelectManager.get(player) ?: run {
				ErrorMessenger.noCourseSelect(sender)
				return
			}
		}
		else {
			// Translate args to int
			val id = args[0].toIntOrNull() ?: run {
				ErrorMessenger.enterNumber(sender)
				return
			}
			// Get course with ID
			course = CourseManager.getCourses().singleOrNull { it.getId() == id } ?: run {
				ErrorMessenger.courseIdNotFound(sender, id)
				return
			}
		}
		// Update spawn location
		course.setSpawn(player.location)
		// Prompt spawn update
		player.sendMessage("${GREEN}Updated course spawn to your location")
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getLabel(): String = "SETSPAWN"
	override fun getPermString(): String = "parkour.setspawn"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getUsage(): String = "/pk setspawn"
	override fun getDescription(): String = "Sets the course spawn"
	override fun hasListener(): Boolean = false
	override fun getSubCmd(): ICommand? = CParkour
	
}