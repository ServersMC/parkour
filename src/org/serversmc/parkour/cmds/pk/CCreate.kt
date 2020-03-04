package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*
import org.serversmc.parkour.utils.*

object CCreate : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// Initialize variable
		val player: Player = sender as? Player ?: throw ICommand.PlayerOnlyCommand()
		// Check if name is provided
		if (args.isEmpty()) {
			player.sendMessage("${RED}Please name your course: ${getUsage()}")
			return
		}
		// Brew name for course
		val rawName = ArrayList<String>()
		args.forEach {
			rawName.add(it)
		}
		val name = rawName.joinToString(" ").trim()
		// Check if name is already in use
		if (CourseManager.courseExists(name)) {
			player.sendMessage("${RED}A course with that name already exists!")
			return
		}
		// Create course
		val course = CourseManager.createCourse(name)
		course.setAuthor(player)
		// Prompt creation
		player.sendMessage("${GREEN}Created course ${GOLD}${course.getName()}${GREEN}!")
		player.sendMessage("${GREEN}Finish course setup using: ${GOLD}/parkour info")
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? {
		return if (args.isNotEmpty()) {
			arrayOf("<name>").toMutableList()
		}
		else {
			ArrayList()
		}
	}
	
	override fun getDescription(): String = "Creates and selects a course"
	override fun getLabel(): String = "CREATE"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getPermString(): String = "parkour.create"
	override fun getSubCmd(): ICommand? = CParkour
	override fun getUsage(): String = "/pk create <name>"
	override fun hasListener(): Boolean = false
	
}