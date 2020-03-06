package org.serversmc.parkour.objects

import org.bukkit.*
import org.bukkit.entity.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*

class CSpawn : IHologram {
	
	private var location: Location? = null
	
	fun getLocation() = location
	
	fun setLocation(location: Location?) {
		this.location = location
	}
	
	override fun showHologram() {
		if (location == null) return
		super.showHologram()
	}
	
	override fun hideHologram() {
		if (location == null) return
		super.hideHologram()
	}
	
	
	override var entity: ArmorStand? = null
	override fun getHoloName(): String = "$BOLD$GRAY[${GREEN}SPAWN$GRAY]"
	override fun getHoloLocation(): Location = location!!.clone().add(0.0, 0.7, 0.0)
	
}