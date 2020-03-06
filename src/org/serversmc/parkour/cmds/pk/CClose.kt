package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*
import org.serversmc.parkour.utils.*

object CClose : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// Initialize player
		val player = sender as? Player ?: throw(ICommand.PlayerOnlyCommand())
		val course = SelectManager.get(player) ?: run {
			ErrorMessenger.noCourseSelect(player)
			return
		}
		// Close course
		player.sendMessage("${GREEN}Course is now closed! Players cannot use this course.")
		course.setClosed()
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getLabel(): String = "CLOSE"
	override fun getPermString(): String = "parkour.close"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getUsage(): String = "/pk open"
	override fun getDescription(): String = "Closes the course for editing"
	override fun hasListener(): Boolean = false
	override fun getSubCmd(): ICommand? = CParkour
	
}