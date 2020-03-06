package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*
import org.serversmc.parkour.utils.*

object COpen : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// Initialize player
		val player = sender as? Player ?: throw(ICommand.PlayerOnlyCommand())
		val course = SelectManager.get(player) ?: run {
			ErrorMessenger.noCourseSelect(player)
			return
		}
		// Check if course is ready
		if (!course.isReady()) {
			player.sendMessage("${RED}Course is not ready to be open yet!")
			player.sendMessage("  ${AQUA}Type ${GRAY}/pk info${AQUA} for more information!")
			return
		}
		// Open course
		player.sendMessage("${GREEN}Course is now open to the public!")
		course.setOpen()
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getLabel(): String = "OPEN"
	override fun getPermString(): String = "parkour.open"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getUsage(): String = "/pk open"
	override fun getDescription(): String = "Opens selected course if ready"
	override fun hasListener(): Boolean = false
	override fun getSubCmd(): ICommand? = CParkour
	
}