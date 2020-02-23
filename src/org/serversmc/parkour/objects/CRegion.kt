package org.serversmc.parkour.objects

import org.bukkit.block.*
import org.bukkit.configuration.*
import org.bukkit.util.*

class CRegion {
	
	private val blocks = ArrayList<CBlock>()
	
	private var index = 0
	private var vectorMin: Vector = Vector()
	private var vectorMax: Vector = Vector()
	
	fun load(section: ConfigurationSection) {
		// Initialize attributes
		index = section.parent!!.name.toInt()
		// Load blocks
		section.getKeys(false).forEach {
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
	
	fun getIndex() = index
	
	fun setIndex(index: Int) {
		this.index = index
	}
	
	fun getBlocks(): ArrayList<CBlock> = blocks
	
	fun addBlock(block: Block) {
		blocks.add(CBlock().apply {
			setLocation(block.location)
			setBlockData(block.blockData)
		})
	}
	
	fun boundsContain(vector: Vector): Boolean {
		return vector.isInAABB(vectorMin, vectorMax)
	}
	
	fun show() {
		// Show blocks that are solid
		blocks.filter { it.getBlockData().material.isSolid }.forEach { it.show() }
		// Show blocks that are not solid
		blocks.filterNot { it.getBlockData().material.isSolid }.forEach { it.show() }
	}
	
	fun hide() {
		// Show blocks that are not solid
		blocks.filterNot { it.getBlockData().material.isSolid }.forEach { it.hide() }
		// Show blocks that are solid
		blocks.filter { it.getBlockData().material.isSolid }.forEach { it.hide() }
	}
	
	private fun calculateBounds() {
		ArrayList<Vector>().apply {
			blocks.forEach { add(it.getLocation().toVector()) }
		}.forEach {
		
		}
		// TODO - Create a more efficient algorithm
	}
	
}
