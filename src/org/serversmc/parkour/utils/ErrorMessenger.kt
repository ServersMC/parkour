package org.serversmc.parkour.utils

import org.bukkit.command.*
import org.serversmc.parkour.enums.*

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
	 * Region id $id not found!
	 */
	fun regionIdNotFound(sender: CommandSender, id: Int) {
		sender.sendMessage("${RED}Region id '$GRAY$id$RED' not found!")
	}
	
	/**
	 * No course selected. Select a course using '/pk select'
	 */
	fun noCourseSelect(sender: CommandSender) {
		sender.sendMessage("${RED}No course selected. Select a course using '$GRAY/pk select$RED'")
	}
	
	/**
	 * Cannot edit an open course! Please close it before editing.
	 * Use /pk close to close course.
	 */
	fun cannotEditOpenCourse(sender: CommandSender) {
		sender.sendMessage("${RED}Cannot edit an open course! Please close it before editing.")
		sender.sendMessage("${RED}Use ${GRAY}/pk close ${RED}to close course")
	}
	
}