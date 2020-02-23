package org.serversmc.parkour.objects

import org.bukkit.*
import org.bukkit.block.data.*
import org.bukkit.configuration.*

class CBlock {
	
	private lateinit var blockData: BlockData
	private lateinit var location: Location
	
	private var visible = true
	
	fun load(section: ConfigurationSection) {
		location = section.get("loc") as Location
		blockData = Bukkit.getServer().createBlockData(section.getString("data")!!)
	}
	
	fun getBlockData() = blockData
	
	fun setBlockData(blockData: BlockData) {
		this.blockData = blockData
	}
	
	fun getLocation() = location
	
	fun setLocation(location: Location) {
		this.location = location
	}
	
	fun isVisible() = visible
	
	fun show() {
		visible = true
		location.block.blockData = blockData
	}
	
	fun hide() {
		visible = false
		location.block.type = Material.AIR
	}
	
}
