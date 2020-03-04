package org.serversmc.parkour.interfaces

import org.bukkit.entity.*
import org.serversmc.parkour.utils.*

interface ITrackedEvent {
	
	val start: String
	val canceled: String
	val inUse: String
	
	fun registerPlayer(player: Player) {
		// Initialize course variable
		val course = SelectManager.get(player) ?: run {
			ErrorMessenger.noCourseSelect(player)
			return
		}
		// Check if player is in a tracking list
		if (EventTracker.containsPlayer(player)) {
			// Check if player is using this command
			if (EventTracker.getTracker(player)!!.event == this as ICommand) {
				EventTracker.remove(player, true)
				return
			}
			EventTracker.remove(player, true)
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