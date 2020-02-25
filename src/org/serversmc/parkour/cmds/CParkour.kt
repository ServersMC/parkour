package org.serversmc.parkour.cmds

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.parkour.cmds.pk.*
import org.serversmc.parkour.core.*
import org.serversmc.parkour.enums.*
import org.serversmc.parkour.interfaces.*

object CParkour : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		// Display header
		sender.sendMessage("${RED}Parkour$GRAY - v${PLUGIN.description.version}")
		
		// Show help
		CHelp.execute(sender, args)
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	
	override fun getDescription(): String = "Main Parkour command"
	override fun getLabel(): String = "PARKOUR"
	override fun getPermDefault(): PermissionDefault = TRUE
	override fun getPermString(): String = "parkour.command"
	override fun getSubCmd(): ICommand? = null
	override fun getUsage(): String = "/parkour"
	override fun hasListener(): Boolean = false
	
}