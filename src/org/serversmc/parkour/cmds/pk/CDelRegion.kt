package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*
import org.serversmc.parkour.utils.*

object CDelRegion : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// Initialize variables
		val player = sender as? Player ?: throw(ICommand.PlayerOnlyCommand())
		val course = SelectManager.get(player) ?: run {
			ErrorMessenger.noCourseSelect(sender)
			return
		}
		// Check if arguments are empty
		if (args.isEmpty()) {
			player.sendMessage("${RED}Invalid usage: $GRAY${getUsage()}")
			return
		}
		// Translate args to int
		val id = args[0].toIntOrNull() ?: run {
			ErrorMessenger.enterNumber(sender)
			return
		}
		// Initialize region
		val region = course.getRegion(id) ?: run {
			ErrorMessenger.regionIdNotFound(sender, id)
			return
		}
		// Remove region
		course.removeRegion(region)
		// Prompt message
		sender.sendMessage("${AQUA}Deleted region id: $GRAY$id${AQUA}!")
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getLabel(): String = "DELREGION"
	override fun getPermString(): String = "parkour.delregion"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getUsage(): String = "/pl delregion [id]"
	override fun getDescription(): String = "Deletes a region from a course"
	override fun hasListener(): Boolean = false
	override fun getSubCmd(): ICommand? = CParkour
	
}