package org.serversmc.parkour.events

import org.bukkit.event.*
import org.bukkit.event.block.*
import org.bukkit.event.player.*

object ParkourPlay : Listener {

	@EventHandler
	fun onPlayerInteract(event: PlayerInteractEvent) {
		
		if (event.action != Action.LEFT_CLICK_BLOCK) return
		
		event.clickedBlock?.run {
			event.player.sendMessage(location.toString())
		}
		
	}
	
}