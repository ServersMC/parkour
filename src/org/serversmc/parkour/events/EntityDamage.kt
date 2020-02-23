package org.serversmc.parkour.events

import org.bukkit.entity.*
import org.bukkit.event.*
import org.bukkit.event.entity.*
import org.serversmc.parkour.utils.*

object EntityDamage : Listener {
	
	@EventHandler
	fun onEntityDamageEvent(event: EntityDamageEvent) {
	
		// Check if entity is not player
		if (event.entityType != EntityType.PLAYER) return
		
		// Initialize variables
		val player = event.entity as Player
		
		// Check of player is not in a course
		CourseManager.getCourses().singleOrNull { it.hasPlayer(player) }?: return
		
		// Cancel fall damage
		event.isCancelled = true
	
	}
	
}