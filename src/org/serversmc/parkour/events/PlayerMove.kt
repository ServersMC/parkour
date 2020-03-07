package org.serversmc.parkour.events

import org.bukkit.entity.*
import org.bukkit.event.*
import org.bukkit.event.entity.*
import org.bukkit.event.player.*
import org.bukkit.util.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.objects.*
import org.serversmc.parkour.utils.*
import kotlin.math.*

object PlayerMove : Listener {
	
	@EventHandler
	@Suppress("warnings")
	fun onPlayerMove(event: PlayerMoveEvent) {
		// Try to find course
		val course = CourseManager.getCourses().singleOrNull { it.hasPlayer(event.player) } ?: return
		// Initialize variables
		val player = event.player
		var region: CRegion? = null
		// Check if player is in queue
		if (course.getPlayerPos(player) == -1) {
			if (event.to!!.distance(course.getStartSensor()!!.getLocation()) > 5) {
				course.removePlayer(player)
				TitleAPI.sendTitle(player, 0, 0, 5, "${YELLOW}Jump to next block", "${YELLOW}to begin")
				return
			}
		}
		// Find which region player is in
		for (regionFind in course.getRegions()) {
			if (!regionFind.isVisible()) continue
			if (regionFind.boundsContain(player.location.toVector())) {
				region = regionFind
				break
			}
		}
		// Flow Control
		regionPositionUpdate(event, course, region)
		outOfBoundsCheck(event, course)
	}
	
	private fun regionPositionUpdate(event: PlayerMoveEvent, course: Course, region: CRegion?) {
		// Initialize variables
		val player = event.player
		// Cancel is region is null
		if (region == null) return
		// Start course for player if is in queue
		if (course.getPlayerPos(player) == -1) {
			// TODO - course.startPlayer()
			// Clear title upon start
			TitleAPI.sendTitle(player, 0, 0, 0, "", "")
		}
		// Update player position
		if (course.getPlayerPos(player) < region.getId()) {
			course.setPlayerPos(player, region.getId())
		}
	}
	
	private fun outOfBoundsCheck(event: PlayerMoveEvent, course: Course) {
		// Initialize variables
		val player = event.player
		// Initialize variables
		val pos = course.getPlayerPos(player)
		var minY = 255.0
		// Cancel if player in queue
		if (pos == -1) return
		// Get bottomY from visible regions
		for (i in 0 until course.getViewDistance()) {
			// Initialize temp region
			val tempRegion = try {
				course.getRegions()[pos + i]
			} catch (e: IndexOutOfBoundsException) {
				break
			}
			// Get minY
			minY = min(minY, tempRegion.getMinY())
		}
		
		// Check if player fell
		if (event.to?.y ?: player.location.y <= minY) {
			if (player.isOnGround || event.to?.y ?: player.location.y <= minY - 2) {
				// TODO - Course reset player (turn off velocity for no fall damage)
				TitleAPI.sendTitle(player, 5, 10, 5, "", "${BOLD}Try again!")
				player.velocity = Vector()
				player.teleport(course.getPlayerCheckpoint(player)?.getLocation() ?: run {
					course.setPlayerPos(player, -1)
					course.getSpawn()
				})
			}
		}
	}
	
	@EventHandler
	fun onPlayerDamage(event: EntityDamageEvent) {
		// Initialize variables
		val player = event.entity as? Player ?: return
		// Check of player is not in a course
		CourseManager.getCourses().singleOrNull { it.hasPlayer(player) } ?: return
		// Cancel fall damage
		event.isCancelled = true
	}
	
}