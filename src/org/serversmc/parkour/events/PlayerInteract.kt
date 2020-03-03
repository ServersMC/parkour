package org.serversmc.parkour.events

import org.bukkit.event.*
import org.bukkit.event.block.*
import org.bukkit.event.player.*
import org.serversmc.parkour.enums.*
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
		val (course, sensor) = CourseManager.getSensor(block.location) ?: return
		// Flow Control
		when (sensor.getType()) {
			CSensor.Type.START -> {
				// Check if player is in game
				if (course.hasPlayer(player)) return
				// Check if course is open
				if (course.getMode() == Course.Mode.CLOSED) {
					TitleAPI.sendTitle(player, 5, 100, 5, "${RED}Closed!", "${GRAY}This course is currently not open")
					return
				}
				// Give instructions
				TitleAPI.sendTitle(player, 5, 1000, 5, "${YELLOW}Jump to next block", "${YELLOW}to begin")
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
				TitleAPI.sendTitle(player, 5, 30, 5, "$AQUA${BOLD}Checkpoint!", null)
			}
		}
	}
	
}
