package org.serversmc.parkour.objects

import org.bukkit.block.*

class CRegion {

	private val blocks = ArrayList<CBlock>()
	private var xMin = 0
	private var yMin = 0
	private var zMin = 0
	private var xMax = 0
	private var yMax = 0
	private var zMax = 0
	
	fun getBlocks():ArrayList<CBlock> = blocks
	
	fun addBlock(block: Block) {
		// TODO - Add block to list
	}
	
	fun calculateBounds() {
		// TODO - Calculate bounds
	}
	
	fun boundsContain():Boolean {
		// TODO - Check bounds
		return false
	}
	
	fun show() = blocks.forEach { it.show() }
	fun hide() = blocks.forEach { it.hide() }
	
}
