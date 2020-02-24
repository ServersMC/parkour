package org.serversmc.parkour.core

import org.bukkit.*
import org.bukkit.plugin.java.*
import org.serversmc.*
import org.serversmc.interfaces.*
import org.serversmc.parkour.commands.*
import org.serversmc.parkour.commands.pk.*
import org.serversmc.parkour.events.*
import org.serversmc.parkour.utils.*
import org.serversmc.utils.*

class Main : JavaPlugin(), APIPlugin {
	
	override fun onEnable() {
		// Initialize libraries
		ServersMC.init(this)
		// Create Data folder
		if (!dataFolder.exists()) dataFolder.mkdirs()
		// Register commands
		registerCommands()
		// Register Events
		Bukkit.getPluginManager().apply {
			registerEvents(PlayerInteract, PLUGIN)
			registerEvents(PlayerQuit, PLUGIN)
			registerEvents(PlayerMove, PLUGIN)
			registerEvents(EntityDamage, PLUGIN)
		}
		// Load Courses
		CourseManager.loadCourses()
		// Check for Update
		// TODO
		// Done
		Console.info("Loaded")
	}
	
	override fun onDisable() {
		// Save courses and show them
		CourseManager.saveAndClose()
		// Done
		Console.info("Done")
	}
	
	override fun registerCommands() {
		// Main commands
		CmdParkour.register()
		// Parkour sub commands
		CmdCreate.register()
	}
	
}