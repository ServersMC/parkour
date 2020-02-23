package org.serversmc.parkour.events

import org.bukkit.entity.*
import org.bukkit.event.*
import org.bukkit.event.player.*
import org.serversmc.parkour.objects.*
import org.serversmc.parkour.utils.*
import org.serversmc.protocol.*
import org.serversmc.utils.ChatColor.BOLD
import kotlin.math.*

object PlayerMove : Listener {
	
	@EventHandler
	fun onPlayerMove(event: PlayerMoveEvent) {
		
		// Try to find course
		val course = CourseManager.getCourses().singleOrNull { it.hasPlayer(event.player) } ?: return
		
		// Initialize variables
		val player = event.player
		var region: CRegion? = null
		
		// Check if player is in queue
		if (course.getPlayerPos(player) == 0) {
			if (event.to!!.distance(course.getStartSensor().getLocation()) > 5) {
				course.removePlayer(player)
				// TODO - Send leave course message
			}
			return
		}
		
		// Find which region player is in
		for (regionFind in course.getRegions()) {
			if (!regionFind.isVisible()) continue
			if (regionFind.boundsContain(player.location.toVector())) {
				region = regionFind
				break
			}
		}
		
		regionPositionUpdate(player, course, region)
		
		outOfBoundsCheck(player, course, region)
	}
	
	private fun regionPositionUpdate(player: Player, course: Course, region: CRegion?) {
		
		// Cancel is region is null
		if (region == null) return
		
		// Start course for player if is in queue
		if (course.getPlayerPos(player) == 0) {
			
			// TODO - course.startPlayer()
			
			// Clear title upon start
			TitleAPI.sendTitle(player, 0, 0, 0, "", "")
		}
		
		// Update player position
		if (course.getPlayerPos(player) < region.getId()) {
			course.setPlayerPos(player, region.getId())
		}
	}
	
	private fun outOfBoundsCheck(player: Player, course: Course, region: CRegion?) {
		
		// Initialize variables
		val pos = course.getPlayerPos(player)
		var minY = 255.0
		
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
		if (player.location.y <= minY) {
			if (player.isOnGround || player.location.y <= minY - 2) {
				
				// TODO - Course reset player (turn off velocity for no fall damage)
				
				TitleAPI.sendTitle(player, 10, 20, 10, "", "${BOLD}Try again!")
			}
		}
	}
	
}