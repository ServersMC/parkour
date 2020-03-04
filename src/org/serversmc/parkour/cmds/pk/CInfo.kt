package org.serversmc.parkour.cmds.pk

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*
import org.serversmc.parkour.objects.*
import org.serversmc.parkour.utils.*


object CInfo : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// Initialize variables
		val player: Player = sender as? Player ?: throw(ICommand.PlayerOnlyCommand())
		val course: Course
		// Check if player has a course selected
		course = SelectManager.get(player) ?: run {
			ErrorMessenger.noCourseSelect(sender, this)
			return
		}
		// Show info
		course.apply {
			// Initialize inserts
			val no = "${RED}false"
			val yes = "${GREEN}true"
			// Send messages
			sender.sendMessage("$GRAY  id: ${GREEN}${getId()}")
			sender.sendMessage("$GRAY  name: ${GREEN}${getName()}")
			sender.sendMessage("$GRAY  isOpen: ${if (getMode() == Course.Mode.OPEN) yes else no}")
			sender.sendMessage("$GRAY  hasSpawn: ${if (getSpawn() == null) no else yes}")
			sender.sendMessage("$GRAY  hasStart: ${if (getStartSensor() == null) no else yes}")
			sender.sendMessage("$GRAY  hasFinish: ${if (getFinishSensor() == null) no else yes}")
			sender.sendMessage("$GRAY  regions: ${if (getRegions().isEmpty()) RED else GREEN}${getRegions().size}")
			sender.sendMessage("$GRAY  checkpoints: ${if (getCheckpoints().isEmpty()) YELLOW else GREEN}${getCheckpoints().size}")
			sender.sendMessage("$GRAY  isReady: ${if (isReady()) yes else no}")
			if (!isReady()) {
				sender.sendMessage("${YELLOW}To make course ready, fix items in red!")
			}
		}
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getLabel(): String = "INFO"
	override fun getPermString(): String = "parkour.info"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getUsage(): String = "/pk info"
	override fun getDescription(): String = "Gets the info of the selected course"
	override fun hasListener(): Boolean = false
	override fun getSubCmd(): ICommand? = CParkour
	
}