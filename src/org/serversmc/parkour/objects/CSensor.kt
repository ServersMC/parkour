package org.serversmc.parkour.objects

import org.bukkit.*
import org.bukkit.configuration.*
import org.bukkit.entity.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*

class CSensor : IHologram {
	
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
	
	override var entity: ArmorStand? = null
	override fun getHoloName(): String = "$BOLD$GRAY{$AQUA${type.name}$GRAY}"
	override fun getHoloLocation(): Location = location.clone().add(0.5, 1.0, 0.5)
	
}
