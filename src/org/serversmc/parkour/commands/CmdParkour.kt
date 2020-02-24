package org.serversmc.parkour.commands

import org.bukkit.command.*
import org.bukkit.entity.*
import org.bukkit.permissions.*
import org.serversmc.*
import org.serversmc.interfaces.*
import org.serversmc.utils.ChatColor.GRAY
import org.serversmc.utils.ChatColor.RED

object CmdParkour : ICommand {
	
	override fun execute(sender: CommandSender, args: MutableList<out String>) {
		
		// Display header
		sender.sendMessage("${RED}Parkour$GRAY - v${PLUGIN.description.version}")
		
		// Check if sender is console
		if (sender !is Player) {
			sender.sendMessage("${RED}This is a player only command!")
			return
		}
		
		// Initialize variables
		val player: Player = sender
		
		// Show help is no arguments are given
		if (args.size == 0) {
			//CmdHelp.execute(sender, args)
			return
		}
	}
	
	override fun tabComplete(player: Player, args: MutableList<out String>): MutableList<String>? = ArrayList()
	
	override fun getDescription(): String = "Main Parkour command"
	override fun getLabel(): String = "PARKOUR"
	override fun getPermDefault(): PermissionDefault = PermissionDefault.TRUE
	override fun getPermString(): String = "parkour.command"
	override fun getSubCmd(): ICommand? = null
	override fun getUsage(): String = "/parkour"
	override fun hasListener(): Boolean = false
	
}