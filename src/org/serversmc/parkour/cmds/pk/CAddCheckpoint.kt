package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.event.*
import org.bukkit.event.player.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*

object CAddCheckpoint : ICommand, ITrackedEvent {
	
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
	}
	
	@EventHandler
	@Suppress("warnings")
	fun onBlockPlace(event: PlayerInteractEvent) {
		// Check if event is valid
		if (!isEventValid(event.player)) return
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