package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.event.*
import org.bukkit.event.player.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*
import org.serversmc.parkour.utils.*

object CDelete : ICommand, ITrackedEvent {
	
	override val start: String get() = "${YELLOW}Are you sure you want to delete this course? Type ${GRAY}yes ${YELLOW}or ${GRAY}no"
	override val canceled: String get() = "${RED}Canceled course deletion"
	override val inUse: String get() = "${GRAY}${EventTracker.getPlayer(this)!!.name} ${RED}is already using this command!"
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// Initialize player
		val player = sender as? Player ?: throw(ICommand.PlayerOnlyCommand())
		// Register player
		registerPlayer(player)
	}
	
	@EventHandler
	fun onPlayerChat(event: AsyncPlayerChatEvent) {
		// Check if player is in tracked list
		if (!EventTracker.containsPlayer(event.player)) return
		// Initialize variables
		val player = event.player
		val course = EventTracker.getTracker(player)!!.course
		// Cancel player chat
		event.isCancelled = true
		// Switch message case
		when (event.message.toLowerCase()) {
			"yes" -> {
				player.sendMessage("${GREEN}Deleted ${WHITE}${course.getName()}${GREEN}!")
				EventTracker.remove(player, false)
			}
			"no" -> {
				EventTracker.remove(player, true)
			}
			else -> {
				player.sendMessage("${YELLOW}Type ${GRAY}yes ${YELLOW}or ${GRAY}no ${YELLOW}or ${GRAY}${getUsage()} to cancel.")
			}
		}
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getLabel(): String = "DELETE"
	override fun getPermString(): String = "parkour.delete"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getUsage(): String = "/pk delete"
	override fun getDescription(): String = "Deletes selected course"
	override fun hasListener(): Boolean = true
	override fun getSubCmd(): ICommand? = CParkour
	
}