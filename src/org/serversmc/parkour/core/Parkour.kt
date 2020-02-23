package org.serversmc.parkour.core

import org.bukkit.*
import org.bukkit.plugin.java.*
import org.serversmc.*
import org.serversmc.parkour.commands.*
import org.serversmc.parkour.events.*
import org.serversmc.utils.*

class Main : JavaPlugin() {
	
	companion object {
		lateinit var Parkour: Main
	}
	
	override fun onEnable() {
		// Initialize libraries
		Parkour = this
		ServersMC.init(this)
		// Create Data folder
		if (!dataFolder.exists()) dataFolder.mkdirs()
		// Register Events
		Bukkit.getPluginManager().apply {
			// Stand-alone events
			registerEvents(ParkourPlay, Parkour)
			registerEvents(PlayerJoin, Parkour)
			registerEvents(PlayerQuit, Parkour)
			// Command events
			registerEvents(CmdSetStart, Parkour)
			registerEvents(CmdRegion, Parkour)
			registerEvents(CmdSetFinish, Parkour)
			registerEvents(CmdDelete, Parkour)
			registerEvents(CmdAddCheck, Parkour)
		}
		// Load Courses
		
		// Check for Update
		
		// Done
		Console.info("Loaded")
	}
	
	override fun onDisable() {
		// Done
		Console.info("Done")
	}
	
}