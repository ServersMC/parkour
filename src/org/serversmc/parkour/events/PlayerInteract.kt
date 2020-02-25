package org.serversmc.parkour.events

import org.bukkit.event.*
import org.bukkit.event.block.*
import org.bukkit.event.player.*
import org.serversmc.parkour.objects.*
import org.serversmc.parkour.utils.*

object PlayerInteract : Listener {
	
	@EventHandler
	fun onPlayerInteract(event: PlayerInteractEvent) {
		// Check if action is physical
		if (event.action != Action.PHYSICAL) return
		// Initialize variables
		val player = event.player
		val block = event.clickedBlock ?: return
		lateinit var sensor: CSensor
		lateinit var course: Course
		// Find course
		CourseManager.getCourses().forEach { courseSearch ->
			sensor = courseSearch.getSensors().singleOrNull { it.getLocation() == block.location } ?: return@forEach
			course = courseSearch
		}
		// Flow Control
		when (sensor.getType()) {
			CSensor.Type.START -> {
				// Check if player is in game
				if (course.hasPlayer(player)) return
				// Check if course is open
				if (course.getMode() == Course.Mode.CLOSED) {
					// TODO - course is closed message
					return
				}
				// Give instructions
				// TODO - Give instructions to jump to next block to start
				// Add player to course
				course.addPlayer(player)
			}
			CSensor.Type.FINISH -> {
				// Check if player is not in game
				if (!course.hasPlayer(player)) return
				// Prompt success
				// TODO - send message that player finished
				// Remove player from game
				course.removePlayer(player)
				// Try to get spawn location
				val spawn = course.getSpawn() ?: return
				// Teleport player to spawn
				player.teleport(spawn)
			}
			CSensor.Type.CHECKPOINT -> {
				// Update player checkpoint
				course.setPlayerCheckpoint(player, sensor)
				// Prompt message
				// TODO - Check point message
			}
		}
	}
	
}
