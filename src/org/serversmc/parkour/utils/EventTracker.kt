package org.serversmc.parkour.utils

import org.bukkit.*
import org.bukkit.entity.*
import org.serversmc.parkour.core.*
import org.serversmc.parkour.interfaces.*

object EventTracker {
	
	private val players = HashMap<Player, ITrackedEvent>()
	
	fun add(player: Player, event: ITrackedEvent) {
		players[player] = event
		event.onAdd(player)
		if (event.getInUse() == null) {
			return
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN, {
			remove(player, true)
		}, 300L)
	}
	
	fun remove(player: Player, cancelled: Boolean) {
		val tracker = getEvent(player) ?: return
		tracker.onRemove(player, cancelled)
		if (cancelled) {
			player.sendMessage(tracker.getCanceled())
		}
		players.remove(player)
	}
	
	fun containsPlayer(player: Player) = players.contains(player)
	
	fun containsEvent(event: ITrackedEvent) = players.containsValue(event)
	
	fun getEvent(player: Player) = players[player]
	
	fun getPlayers(event: ITrackedEvent) = players.filterValues { it == event }
	
	fun getSinglePlayer(event: ITrackedEvent): Player? {
		players.values.singleOrNull { it == event } ?: return null
		return players.filterValues { it == event }.keys.first()
	}
	
	fun clear() {
		players.clear()
	}
	
}