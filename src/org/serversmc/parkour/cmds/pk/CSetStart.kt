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

object CSetStart : ICommand, ITrackedEvent {
	
	override fun getStart(): String = "${GREEN}Click on a pressure plate to select new start position"
	override fun getCanceled(): String = "${RED}Canceled start position set"
	override fun getInUse(): String? = "${GRAY}${EventTracker.getPlayers(this).keys.first().name} ${RED}is already setting a start position!"
	override fun onAdd(player: Player) = Unit
	override fun onRemove(player: Player, isCancelled: Boolean) = Unit
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// Initialize variables
		val player: Player = sender as? Player ?: throw(ICommand.PlayerOnlyCommand())
		// Register player to EventTracker
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
					player.sendMessage("${RED}This block is part of course '${GRAY}${parent.getName()}${RED}'!")
					EventTracker.remove(player, true)
					return
				}
				// Switch case sensor type
				when (sensor.getType()) {
					CSensor.Type.START -> {
						player.sendMessage("${YELLOW}This is already the start position!")
						EventTracker.remove(player, true)
						return
					}
					CSensor.Type.FINISH -> {
						player.sendMessage("${YELLOW}This is no longer the finish position!")
						course.setFinishSensor(null)
					}
					CSensor.Type.CHECKPOINT -> player.sendMessage("${YELLOW}This block is a checkpoint!")
				}
			}
			// Update course start position and prompt message
			course.setStartSensor(block.location)
			player.sendMessage("${GREEN}Updated start position for course '${GRAY}${course.getName()}${GREEN}'!")
			EventTracker.remove(player, false)
		}
		else {
			// Prompt incorrect block clicked
			player.sendMessage("${RED}Please select a ${GRAY}Pressure Plate${RED}!")
		}
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getLabel(): String = "SETSTART"
	override fun getPermString(): String = "parkour.setstart"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getUsage(): String = "/pk setstart"
	override fun getDescription(): String = "Updates the course start position"
	override fun hasListener(): Boolean = true
	override fun getSubCmd(): ICommand? = CParkour
	
}