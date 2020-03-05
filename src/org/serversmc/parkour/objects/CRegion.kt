package org.serversmc.parkour.objects

import org.bukkit.*
import org.bukkit.block.*
import org.bukkit.configuration.*
import org.bukkit.entity.*
import org.bukkit.util.Vector
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*
import java.util.*
import kotlin.collections.ArrayList

class CRegion : IHologram {
	
	private val blocks = ArrayList<CBlock>()
	
	private lateinit var world: World
	private var id = 0
	private var visible = true
	private var min = Vector()
	private var max = Vector()
	
	fun load(section: ConfigurationSection) {
		// Load blocks
		section.getKeys(false).forEach {
			// Set region id
			id = section.name.toInt()
			world = Bukkit.getWorld(UUID.fromString(section.getString("world")))!!
			// Check if section is valid
			if (it == null) return@forEach
			val yamlBlock = section.getConfigurationSection(it) ?: return@forEach
			// Load block
			blocks.add(CBlock().apply {
				load(yamlBlock)
			})
		}
		calculateBounds()
	}
	
	fun getId() = id
	
	fun setId(id: Int) {
		this.id = id
	}
	
	fun getWorld() = world
	
	fun setWorld(world: World) {
		this.world = world
	}
	
	fun isVisible() = visible
	
	fun getBlocks(): ArrayList<CBlock> = blocks
	
	fun addBlock(block: Block) {
		blocks.add(CBlock().apply {
			setLocation(block.location)
			setBlockData(block.blockData)
		})
		calculateBounds()
		updateHolo()
	}
	
	fun containsBlock(block: Block): Boolean {
		blocks.singleOrNull { it.getLocation() == block.location } ?: return false
		return true
	}
	
	fun removeBlock(block: Block) {
		val cBlock = blocks.singleOrNull { it.getLocation() == block.location } ?: return
		blocks.remove(cBlock)
		calculateBounds()
		updateHolo()
	}
	
	fun boundsContain(vector: Vector): Boolean {
		return vector.isInAABB(min, max)
	}
	
	fun getMinY() = min.y
	
	private fun calculateBounds() {
		// Check if list is empty
		if (blocks.isEmpty()) {
			min = Vector()
			max = Vector()
			return
		}
		// Run calculation
		ArrayList<Vector>().apply {
			// Convert locations to vector and add to list
			blocks.forEach { add(it.getLocation().toVector()) }
			// Zero out vectors
			min.zero()
			max.zero()
			// Calculate min
			val minX = minBy { it.x }!!.x - 0.4
			val minY = minBy { it.y }!!.y + 0.4
			val minZ = minBy { it.z }!!.z - 0.4
			// Calculate max
			val maxX = maxBy { it.x }!!.x + 1.4
			val maxY = maxBy { it.y }!!.y + 1.4
			val maxZ = maxBy { it.z }!!.z + 1.4
			// Apply bounds
			min.add(Vector(minX, minY, minZ))
			max.add(Vector(maxX, maxY, maxZ))
		}
	}
	
	private fun updateHolo() {
		if (blocks.isEmpty()) {
			hideHologram()
			return
		}
		showHologram()
		entity?.teleport(getHoloLocation())
	}
	
	fun show() {
		visible = true
		// Show blocks
		blocks.forEach { it.show() }
	}
	
	fun hide() {
		visible = false
		// Hide blocks
		blocks.reversed().forEach { it.hide() }
	}
	
	override var entity: ArmorStand? = null
	override fun getHoloName(): String = "$BOLD$GRAY-[$GOLD${id}$GRAY]-}"
	override fun getHoloLocation(): Location = min.getMidpoint(max).setY(max.y + 0.5).toLocation(world)
	
}
