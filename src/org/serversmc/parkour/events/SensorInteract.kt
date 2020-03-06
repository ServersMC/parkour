package org.serversmc.parkour.events

import org.bukkit.entity.*
import org.bukkit.event.*
import org.bukkit.event.block.*
import org.bukkit.event.player.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.objects.*
import org.serversmc.parkour.utils.*

@Suppress("warnings")
object SensorInteract : Listener {
	
	// Player interacting with course sensors
	@EventHandler
	fun onSensorInteract(event: PlayerInteractEvent) {
		// Check if action is physical
		if (event.action != Action.PHYSICAL) return
		// Initialize variables
		val player = event.player
		val block = event.clickedBlock ?: return
		val (course, sensor) = CourseManager.getSensor(block.location) ?: return
		// Flow Control
		when (sensor.getType()) {
			CSensor.Type.START -> playerHitStart(player, course)
			CSensor.Type.FINISH -> playerHitFinish(player, course)
			CSensor.Type.CHECKPOINT -> playerHitCheckpoint(player, course, sensor)
		}
	}
	
	private fun playerHitStart(player: Player, course: Course) {
		// Check if player is in game
		if (course.hasPlayer(player)) return
		// Check if course is open
		if (course.getMode() == Course.Mode.CLOSED) {
			TitleAPI.sendTitle(player, 5, 100, 5, "${RED}Closed!", "${GRAY}This course is currently not open")
			return
		}
		// Give instructions
		TitleAPI.sendTitle(player, 5, 99999, 5, "${YELLOW}Jump to next block", "${YELLOW}to begin")
		// Add player to course
		course.addPlayer(player)
	}
	
	private fun playerHitFinish(player: Player, course: Course) {
		// Check if player is not in game
		if (!course.hasPlayer(player)) return
		// Prompt success
		TitleAPI.sendTitle(player, 5, 30, 5, "${GREEN}You have finished", "${GRAY}Congrats!")
		// Remove player from game
		course.removePlayer(player)
		// Teleport player to spawn
		player.teleport(course.getSpawn()!!)
	}
	
	private fun playerHitCheckpoint(player: Player, course: Course, sensor: CSensor) {
		// Check if player is not in game
		if (!course.hasPlayer(player)) return
		// Update player checkpoint
		course.setPlayerCheckpoint(player, sensor)
		// Prompt message
		TitleAPI.sendTitle(player, 5, 30, 5, "$AQUA${BOLD}Checkpoint!", null)
	}
	
}
