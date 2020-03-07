package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.event.*
import org.bukkit.event.block.*
import org.bukkit.event.player.*
import org.bukkit.inventory.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*
import org.serversmc.parkour.objects.*
import org.serversmc.parkour.utils.*

object CAddCheckpoint : ICommand, ITrackedEvent {
	
	// TODO - FINISH COMMAND
	
	override fun getStart() = "${AQUA}Place or click on a pressure plate to create checkpoint"
	override fun getCanceled() = "${RED}Canceled checkpoint creation"
	override fun getInUse(): String? = null
	override fun onAdd(player: Player) = Unit
	override fun onRemove(player: Player, isCancelled: Boolean) = Unit
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// Initialize player
		val player = sender as? Player ?: throw(ICommand.PlayerOnlyCommand())
		// Register TrackedEvent
		registerTrackedEvent(player)
	}
	
	@EventHandler
	@Suppress("warnings")
	fun onPlayerInteract(event: PlayerInteractEvent) {
		// Check if event is valid
		if (!isEventValid(event.player)) return
		// Cancel event
		event.isCancelled = true
		// Initialize Variables
		val player = event.player
		val block = event.clickedBlock ?: return
		// Check if action is a left/right click block
		if (event.action != Action.LEFT_CLICK_BLOCK && event.action != Action.RIGHT_CLICK_BLOCK) return
		// Fix duplicate click trigger
		if (event.hand != EquipmentSlot.HAND) return
		// Check if block is a pressure plate
		if (block.type.toString().endsWith("_PLATE", true)) {
			// Initialize course variable
			val course = SelectManager.get(player)!!
			// Check if plate is already a sensor
			if (CourseManager.isSensor(block.location)) {
				// Initialize sensor variable
				val (parent, sensor) = CourseManager.getSensor(block.location)!!
				// Check if sensor is part of selected course
				if (course != parent) {
					// Prompt message
					player.sendMessage("${RED}This block is part of course '$GRAY${parent.getName()}$RED'!")
					EventTracker.remove(player, true)
					return
				}
				// Switch case sensor type
				when (sensor.getType()) {
					CSensor.Type.START -> {
						player.sendMessage("${YELLOW}This is the start position!")
						return
					}
					CSensor.Type.FINISH -> {
						player.sendMessage("${YELLOW}This is the finish position!")
						return
					}
					CSensor.Type.CHECKPOINT -> {
						player.sendMessage("${YELLOW}This block is already a checkpoint!")
						EventTracker.remove(player, true)
						return
					}
				}
			}
			// Update course start position and prompt message
			course.addCheckpoint(block.location)
			player.sendMessage("${GREEN}Added checkpoint course '$GRAY${course.getName()}$GREEN'!")
			EventTracker.remove(player, false)
		}
		else {
			// Prompt incorrect block clicked
			player.sendMessage("${RED}Please select a ${GRAY}Pressure Plate$RED!")
		}
	}
	
	@EventHandler
	@Suppress("warnings")
	fun onBlockPlace(event: PlayerInteractEvent) {
		// Check if event is valid
		if (!isEventValid(event.player)) return
		// Cancel event
		event.isCancelled = true
		// Initialize Variables
		val player = event.player
		val block = event.clickedBlock ?: return
		// Check if block is a pressure plate
		if (block.type.toString().endsWith("_PLATE", true)) {
		
		}
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getLabel(): String = "ADDCHECKPOINT"
	override fun getPermString(): String = "parkour.addcheckpoint"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getUsage(): String = "/pk addcheckpoint"
	override fun getDescription(): String = "Creates a checkpoint"
	override fun hasListener(): Boolean = true
	override fun getSubCmd(): ICommand? = CParkour
	
}