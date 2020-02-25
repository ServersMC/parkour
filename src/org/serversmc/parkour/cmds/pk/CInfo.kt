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
		// Initialize variable
		val course: Course
		// Check if args are empty, to grab '/pk select' course from player
		if (args.isEmpty()) {
			// Check if player is sender
			val player = sender as? Player ?: throw(ICommand.PlayerOnlyCommand())
			// Check if player has course selected
			if (CourseSelect.contains(player)) {
				player.sendMessage("${RED}No course selected. Select a course using '${GRAY}/pk select${RED}'")
				player.sendMessage("${RED}or using '$GRAY${getUsage()}$RED'")
				return
			}
			// Select course
			course = CourseSelect.get(player)!!
		}
		// Grab course using args
		else {
			val id = args[0].toIntOrNull() ?: run {
				sender.sendMessage("${RED}Please enter a number!")
				return
			}
			course = CourseManager.getCourses().getOrNull(id) ?: run {
				sender.sendMessage("${RED}Course id '$GRAY$id$RED' not found!")
				return
			}
		}
		// Show info
		course.apply {
			// Initialize inserts
			val no = "${RED}false"
			val yes = "${GREEN}true"
			// Send messages
			sender.sendMessage("${GRAY}id: ${GREEN}${getId()}")
			sender.sendMessage("${GRAY}name: ${GREEN}${getName()}")
			sender.sendMessage("${GRAY}isOpen: ${if (getMode() == Course.Mode.OPEN) yes else no}")
			sender.sendMessage("${GRAY}hasSpawn: ${if (getSpawn() == null) no else yes}")
			sender.sendMessage("${GRAY}hasStart: ${if (getStartSensor() == null) no else yes}")
			sender.sendMessage("${GRAY}hasFinish: ${if (getFinishSensor() == null) no else yes}")
			sender.sendMessage("${GRAY}regions: ${if (getRegions().isEmpty()) RED else GREEN}${getRegions().size}")
			sender.sendMessage("${GRAY}checkpoints: ${if (getCheckpoints().isEmpty()) YELLOW else GREEN}${getCheckpoints().size}")
			sender.sendMessage("${GRAY}isReady: ${if (isReady()) yes else no}")
		}
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	override fun getLabel(): String = "INFO"
	override fun getPermString(): String = "parkour.info"
	override fun getPermDefault(): PermissionDefault = OP
	override fun getUsage(): String = "/parkour info <course_id>"
	override fun getDescription(): String = "Gets the info of the '/pk select' course, or from course_id"
	override fun hasListener(): Boolean = false
	override fun getSubCmd(): ICommand? = CParkour
	
}