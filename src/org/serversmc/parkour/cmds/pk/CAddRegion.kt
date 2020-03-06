package org.serversmc.parkour.cmds.pk

import org.bukkit.*
import org.bukkit.block.*
import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.event.*
import org.bukkit.event.block.*
import org.bukkit.permissions.*
import org.bukkit.util.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.core.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*
import org.serversmc.parkour.objects.*
import org.serversmc.parkour.utils.*

object CAddRegion : ICommand, ITrackedEvent {
	
	// TODO - Add cave regions
	
	override fun getStart(): String = "${GRAY}[${AQUA}Started region editor${GRAY}]"
	override fun getCanceled(): String = "${GRAY}[${AQUA}Finished region editor${GRAY}]"
	override fun getInUse(): String? = null
	
	private val session = HashMap<Player, CRegion>()
	
	override fun onAdd(player: Player) {
		// Initialize course variable
		val course = SelectManager.get(player)!!
		// Creates new region
		val region = course.createRegion(player.world)
		// Prompt Messages
		player.sendMessage(" ${YELLOW}Created region id: ${GRAY}${region.getId()}")
		player.sendMessage(" ${AQUA}Breaking a block will remove the block from region")
		player.sendMessage(" ${AQUA}Placing a block will add the block to the region")
		player.sendMessage(" ${GRAY}${getUsage()} test ${AQUA}will flash the region")
		player.sendMessage(" ${GRAY}${getUsage()} ${AQUA}will save and close the editor")
		// Add player to session
		session[player] = region
	}
	
	override fun onRemove(player: Player, isCancelled: Boolean) {
		// Initialize variables
		val course = SelectManager.get(player)!!
		val region = session[player]!!
		// Check if region is empty
		if (region.getBlocks().isEmpty()) {
			// Prompt message
			player.sendMessage("${YELLOW}Deleted region, no blocks in region!")
			// Delete blank region from course
			course.removeRegion(region)
		}
		else {
			// Prompt message
			player.sendMessage("${GREEN}Saved region id: ${GRAY}${region.getId()}")
		}
		// Remove player from session
		session.remove(player)
	}
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// Initialize variables
		val player = sender as? Player ?: throw(ICommand.PlayerOnlyCommand())
		// Check if args are empty
		if (args.isNotEmpty()) {
			// Check if player called test subcommand
			if (args[0].equals("test", true)) {
				// Check if region is empty
				if (session[player]!!.getBlocks().isEmpty()) {
					player.sendMessage("${AQUA}Nothing to test!")
					return
				}
				// Run if region is not empty
				player.sendMessage("${AQUA}Testing region...")
				session[player]!!.hide()
				Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN, {
					session[player]!!.show()
					player.sendMessage("${AQUA}Done!")
				}, 20L)
				return
			}
		}
		// If test command was not called
		// Register player to TrackedEvent
		registerTrackedEvent(player)
	}
	
	@EventHandler
	fun onBlockBreak(event: BlockBreakEvent) {
		// Initialize player
		val player = event.player
		// Check if player is in session
		if (!session.contains(player)) return
		// Initialize region
		val region = session[player]!!
		// Check if block is part of another region
		SelectManager.get(player)!!.getRegions().filter { it != region }.forEach {
			val blockVector = event.block.location.toVector().add(Vector(0.5, 0.5, 0.5))
			if (it.boundsContain(blockVector)) {
				player.sendMessage("${RED}This block is inside another region!")
				event.isCancelled = true
				return@onBlockBreak
			}
		}
		// Remove block from region if has any
		region.removeBlock(event.block)
		player.sendMessage("${RED}Removed!")
		// Check if attached blocks disappeared
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN) {
			// Temp deletion block to fix ConcurrentModificationException
			val pendingDelete = ArrayList<Block>()
			// Iterate through list to check for updated blocks
			region.getBlocks().forEach {
				// Convert region block to block in world
				val updatedBlock = player.world.getBlockAt(it.getLocation())
				// Check if region block updated to air
				if (updatedBlock.type == Material.AIR) {
					// Add to temp deletion list
					pendingDelete.add(updatedBlock)
				}
			}
			// Delete all updated blocks from region
			pendingDelete.forEach { region.removeBlock(it) }
		}
	}
	
	@EventHandler
	fun onBlockPlace(event: BlockPlaceEvent) {
		// Initialize player
		val player = event.player
		// Check if player is in session
		if (!session.contains(player)) return
		// Initialize region
		val region = session[player]!!
		// Check if block is part of another region
		SelectManager.get(player)!!.getRegions().filter { it != region }.forEach {
			val blockVector = event.block.location.toVector().add(Vector(0.5, 0.5, 0.5))
			if (it.boundsContain(blockVector)) {
				player.sendMessage("${RED}This block is too close to another region!")
				event.isCancelled = true
				return@onBlockPlace
			}
		}
		// Add block from region
		region.addBlock(event.block)
		player.sendMessage("${AQUA}Added!")
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getLabel(): String = "ADDREGION"
	override fun getPermString(): String = "parkour.addregion"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getUsage(): String = "/pk addregion"
	override fun getDescription(): String = "Adds a region to the course"
	override fun hasListener(): Boolean = true
	override fun getSubCmd(): ICommand? = CParkour
	
}