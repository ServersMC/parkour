package org.serversmc.parkour.events

import org.bukkit.event.*
import org.bukkit.event.player.*
import org.serversmc.parkour.utils.*

object PlayerQuit : Listener {
	
	@EventHandler
	fun onPlayerQuit(event: PlayerQuitEvent) {
		removePlayerFromCourse(event)
		removePlayerFromListeners(event)
	}
	
	private fun removePlayerFromCourse(event: PlayerQuitEvent) {
		// Initialize variables
		val player = event.player
		// Try to get course player is in
		val course = CourseManager.getCourses().singleOrNull { it.hasPlayer(player) } ?: return
		// Remove player from game
		course.removePlayer(player)
		// Teleport player to spawn
		player.teleport(course.getSpawn())
	}
	
	private fun removePlayerFromListeners(event: PlayerQuitEvent) {
		// Initialize variables
		val player = event.player
		// Remove player from SelectManager
		SelectManager.remove(player)
	}
	
}