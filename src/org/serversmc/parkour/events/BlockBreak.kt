package org.serversmc.parkour.events

import org.bukkit.event.*
import org.bukkit.event.block.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.utils.*

object BlockBreak : Listener {
	
	@EventHandler
	fun onBlockBreak(event: BlockBreakEvent) {
		// Check if already canceled
		if (event.isCancelled) return
		// Initialize Player
		val player = event.player
		// Check if block is part of a course
		CourseManager.getCourses().filterNot { it == SelectManager.get(player) }.forEach { course ->
			course.getSensor(event.block.location)?.run {
				player.sendMessage("${RED}This block is part of a course!")
				event.isCancelled = true
				return@onBlockBreak
			}
			course.getRegions().forEach { region ->
				if (region.containsBlock(event.block)) {
					player.sendMessage("${RED}This block is part of a course!")
					event.isCancelled = true
					return@onBlockBreak
				}
			}
		}
	}
	
}