package org.serversmc.parkour.objects

import org.bukkit.*
import org.bukkit.configuration.*

class CSensor {
	
	companion object {
		fun create(type: Type, location: Location, course: Course): CSensor {
			return CSensor().apply {
				this.course = course
				this.type = type
				this.location = location
			}
		}
	}
	
	enum class Type { START, FINISH, CHECKPOINT }
	
	private lateinit var course: Course
	private lateinit var type: Type
	private lateinit var location: Location
	
	fun load(section: ConfigurationSection, course: Course) {
		this.course = course
		type = Type.valueOf(section.getString("type")!!)
		location = section.get("loc") as Location
	}
	
	fun getType() = type
	
	fun getLocation() = location
	
}
