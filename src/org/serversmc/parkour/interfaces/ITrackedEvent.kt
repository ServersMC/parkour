package org.serversmc.parkour.interfaces

import org.bukkit.entity.*
import org.serversmc.parkour.objects.*
import org.serversmc.parkour.utils.*

interface ITrackedEvent {
	
	val start: String
	val canceled: String
	val inUse: String
	
	fun registerPlayer(player: Player, args: MutableList<out String>) {
		// Initialize course variable
		val course: Course
		// Check if args are empty
		if (args.isEmpty()) {
			// Check if player has a course selected
			course = SelectManager.get(player) ?: run {
				ErrorMessage.noCourseSelect(player, this as ICommand)
				return
			}
		}
		else {
			// Translate args to int
			val id = args[0].toIntOrNull() ?: run {
				ErrorMessage.enterNumber(player)
				return
			}
			// Get course with ID
			course = CourseManager.getCourses().singleOrNull { it.getId() == id } ?: run {
				ErrorMessage.courseIdNotFound(player, id)
				return
			}
		}
		// Check if player is in a tracking list
		if (EventTracker.containsPlayer(player)) {
			EventTracker.remove(player, true)
			return
		}
		// Check if command is already in use
		if (EventTracker.containsTracker(this)) {
			player.sendMessage(inUse)
			return
		}
		// Add player to EventTracker
		EventTracker.add(player, this, course)
	}
	
}