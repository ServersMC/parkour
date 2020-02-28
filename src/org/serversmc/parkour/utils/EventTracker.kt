package org.serversmc.parkour.utils

import org.bukkit.*
import org.bukkit.entity.*
import org.serversmc.parkour.core.*
import org.serversmc.parkour.interfaces.*
import org.serversmc.parkour.objects.*

object EventTracker {
	
	// TODO - Add a timeout listener
	
	data class Tracker(val player: Player, val event: ITrackedEvent, val course: Course) {
		
		private val taskId: Int = Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN, {
			remove(player, true)
		}, 300L)
		
		fun cancelTimeout() {
			Bukkit.getScheduler().cancelTask(taskId)
		}
		
	}
	
	private val players = ArrayList<Tracker>()
	
	fun add(player: Player, event: ITrackedEvent, course: Course) {
		players.add(Tracker(player, event, course))
		player.sendMessage(event.start)
	}
	
	fun remove(player: Player, cancelled: Boolean) {
		val tracker = getTracker(player) ?: return
		if (cancelled) {
			player.sendMessage(tracker.event.canceled)
		}
		tracker.cancelTimeout()
		players.remove(tracker)
	}
	
	fun containsPlayer(player: Player) = getTracker(player) != null
	
	fun containsTracker(event: ITrackedEvent) = getPlayer(event) != null
	
	fun getPlayer(event: ITrackedEvent) = players.singleOrNull { it.event == event }?.player
	
	fun getTracker(player: Player) = players.singleOrNull { it.player == player }
	
	fun clear() {
		players.clear()
	}
	
}