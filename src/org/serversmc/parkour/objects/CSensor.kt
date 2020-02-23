package org.serversmc.parkour.objects

import org.bukkit.*
import org.bukkit.configuration.*

class CSensor {
	
	companion object {
		fun create(type: Type, location: Location): CSensor {
			return CSensor().apply {
				this.type = type
				this.location = location
			}
		}
	}
	
	enum class Type { START, FINISH, CHECKPOINT }
	
	private lateinit var type: Type
	private lateinit var location: Location
	
	fun load(section: ConfigurationSection) {
		type = Type.valueOf(section.getString("type")!!)
		location = section.get("loc") as Location
	}
	
	fun getType() = type
	
	fun getLocation() = location
	
}
