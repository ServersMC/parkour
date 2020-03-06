package org.serversmc.parkour.interfaces

import org.bukkit.entity.*
import org.serversmc.parkour.utils.*

interface ITrackedEvent {
	
	fun getStart(): String
	fun getCanceled(): String
	fun getInUse(): String?
	fun onAdd(player: Player)
	fun onRemove(player: Player, isCancelled: Boolean)
	
	fun registerTrackedEvent(player: Player) {
		// Check if player selected a course
		if (!SelectManager.contains(player)) {
			ErrorMessenger.noCourseSelect(player)
			return
		}
		// Check if course is open
		if (SelectManager.get(player)!!.isOpen()) {
			ErrorMessenger.cannotEditOpenCourse(player)
		}
		// Check if player is in another tracked event
		if (EventTracker.containsPlayer(player)) {
			// Check if player is using this command
			if (EventTracker.getEvent(player)!! == this) {
				// This prevents a re-register of the same command
				EventTracker.remove(player, true)
				return
			}
			// Remove player from tracked event
			EventTracker.remove(player, true)
		}
		// Check if command is a singular use only
		if (getInUse() != null) {
			// Check if command is already in use
			if (EventTracker.containsEvent(this)) {
				player.sendMessage(getInUse()!!)
				return
			}
		}
		// Add player to EventTracker
		EventTracker.add(player, this)
		// Send Start message
		player.sendMessage(getStart())
	}
	
	fun isEventValid(player: Player): Boolean {
		// Check if player is in tracker
		if (!EventTracker.containsPlayer(player)) return false
		// Check if tracker is this event
		if (EventTracker.getEvent(player)!! != this) return false
		// Passed check return
		return true
	}
	
}