package org.serversmc.parkour.utils

import org.bukkit.command.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*

object ErrorMessenger {
	
	/**
	 * Please enter a number
	 */
	fun enterNumber(sender: CommandSender) {
		sender.sendMessage("${RED}Please enter a number!")
	}
	
	/**
	 * Course id $id not found!
	 */
	fun courseIdNotFound(sender: CommandSender, id: Int) {
		sender.sendMessage("${RED}Course id '$GRAY$id$RED' not found!")
	}
	
	/**
	 * No course selected. Select a course using '/pk select'
	 * or using 'cmd.getUsage()'
	 */
	fun noCourseSelect(sender: CommandSender, cmd: ICommand) {
		sender.sendMessage("${RED}No course selected. Select a course using '$GRAY/pk select$RED'")
		sender.sendMessage("${RED}or using '$GRAY${cmd.getUsage()}$RED'")
	}
	
}