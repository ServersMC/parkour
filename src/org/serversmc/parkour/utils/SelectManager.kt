package org.serversmc.parkour.utils

import org.bukkit.entity.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.objects.*

object SelectManager {
	
	private val selected = HashMap<Player, Course>()
	
	fun add(player: Player, course: Course) {
		EventTracker.remove(player, true)
		player.sendMessage("${GREEN}Editing course $GRAY${course.getName()}")
		selected[player] = course
		CourseManager.updateHolograms()
	}
	
	fun remove(player: Player) {
		if (!selected.containsKey(player)) {
			player.sendMessage("${RED}You are already deselected from a course.")
			return
		}
		EventTracker.remove(player, true)
		player.sendMessage("${GREEN}Deselected course $GRAY${selected[player]!!.getName()}")
		selected.remove(player)
		CourseManager.updateHolograms()
	}
	
	fun contains(player: Player) = selected.contains(player)
	
	fun contains(course: Course) = selected.containsValue(course)
	
	fun get(player: Player) = selected[player]
	
	fun get(course: Course) = ArrayList<Player>().apply { addAll(selected.filterValues { it == course }.keys) }
	
	fun clear() {
		selected.clear()
	}
	
}