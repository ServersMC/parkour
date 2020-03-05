package org.serversmc.parkour.interfaces

import org.bukkit.*
import org.bukkit.entity.*

interface IHologram {
	
	var entity: ArmorStand?
	
	fun showHologram() {
		if (entity != null) return
		entity = getHoloLocation().world!!.spawn(getHoloLocation(), ArmorStand::class.java) {
			it.isVisible = false
			it.isCustomNameVisible = true
			it.isInvulnerable = true
			it.isSmall = true
			it.isMarker = true
			it.setGravity(false)
			it.setArms(false)
			it.setBasePlate(false)
			it.customName = getHoloName()
		}
	}
	
	fun hideHologram() {
		entity?.remove()
		entity = null
	}
	
	fun getHoloName(): String
	fun getHoloLocation(): Location
	
}