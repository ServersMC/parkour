package org.serversmc.parkour.objects

import org.bukkit.block.*
import org.bukkit.configuration.*
import org.bukkit.util.*

class CRegion {
	
	private val blocks = ArrayList<CBlock>()
	
	private var id = 0
	private var visible = true
	private var min = Vector()
	private var max = Vector()
	
	fun load(section: ConfigurationSection) {
		// Load blocks
		section.getKeys(false).forEach {
			// Set region id
			id = section.name.toInt()
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
	
	fun isVisible() = visible
	
	fun getBlocks(): ArrayList<CBlock> = blocks
	
	fun addBlock(block: Block) {
		blocks.add(CBlock().apply {
			setLocation(block.location)
			setBlockData(block.blockData)
		})
		calculateBounds()
	}
	
	fun removeBlock(block: Block) {
		val cBlock = blocks.singleOrNull { it.getLocation() == block.location } ?: return
		blocks.remove(cBlock)
	}
	
	fun boundsContain(vector: Vector): Boolean {
		return vector.isInAABB(min, max)
	}
	
	fun getMinY() = min.y
	
	private fun calculateBounds() {
		// Check if list is empty
		if (blocks.isEmpty()) return
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
	
}
