package org.serversmc.parkour.utils

import org.bukkit.entity.*
import org.serversmc.parkour.objects.*

object SelectManager {
	
	private val selected = HashMap<Player, Course>()
	
	fun add(player: Player, course: Course) {
		selected[player] = course
	}
	
	fun remove(player: Player) {
		if (selected.containsKey(player)) {
			selected.remove(player)
		}
	}
	
	fun contains(player: Player) = selected.contains(player)
	
	fun contains(course: Course) = selected.containsValue(course)
	
	fun get(player: Player) = selected[player]
	
	fun get(course: Course) = ArrayList<Player>().apply { addAll(selected.filterValues { it == course }.keys) }
	
	fun clear() {
		selected.clear()
	}
	
}