package org.serversmc.parkour.utils

import org.bukkit.command.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*

object ErrorMessage {
	
	fun enterNumber(sender: CommandSender) {
		sender.sendMessage("${RED}Please enter a number!")
	}
	
	fun courseIdNotFound(sender: CommandSender, id: Int) {
		sender.sendMessage("${RED}Course id '$GRAY$id$RED' not found!")
	}
	
	fun noCourseSelect(sender: CommandSender, cmd: ICommand) {
		sender.sendMessage("${RED}No course selected. Select a course using '$GRAY/pk select$RED'")
		sender.sendMessage("${RED}or using '$GRAY${cmd.getUsage()}$RED'")
	}
	
}