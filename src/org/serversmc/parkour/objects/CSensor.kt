package org.serversmc.parkour.objects

import org.bukkit.*
import org.bukkit.configuration.*
import org.bukkit.entity.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*
import org.serversmc.parkour.utils.*

class CSensor : IHologram {
	
	companion object {
		fun create(type: Type, location: Location): CSensor {
			return CSensor().apply {
				this.type = type
				this.location = location
				updateRegionId()
			}
		}
	}
	
	enum class Type { START, FINISH, CHECKPOINT }
	
	private lateinit var type: Type
	private lateinit var location: Location
	
	private var regionId: Int = -1
	
	fun load(section: ConfigurationSection) {
		type = Type.valueOf(section.getString("type")!!)
		location = section.get("loc") as Location
		if (type == Type.CHECKPOINT) updateRegionId()
	}
	
	private fun updateRegionId() {
		CourseManager.getCourses().forEach { course ->
			course.getRegions().forEach { region ->
				if (region.containsBlock(location)) {
					regionId = region.getId()
					return
				}
			}
		}
	}
	
	fun getType() = type
	fun getLocation() = location
	fun getRegionId() = regionId
	
	override var entity: ArmorStand? = null
	override fun getHoloName(): String = "$BOLD$GRAY[$AQUA${type.name}$GRAY]"
	override fun getHoloLocation(): Location = location.clone().add(0.5, 0.5, 0.5)
	
}
