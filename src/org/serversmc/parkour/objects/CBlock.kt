package org.serversmc.parkour.objects

import org.bukkit.*

class CBlock {

	private lateinit var material: Material
	private lateinit var location: Location
	
	fun getMaterial():Material = material
	fun getLocation():Location = location
	
	fun setMaterial(material: Material) {
		this.material = material
	}
	
	fun setLocation(location: Location) {
		this.location = location
	}
	
	fun show() {
		// TODO - Show block in world
	}
	
	fun hide() {
		// TODO - Hide block in world
	}

}
