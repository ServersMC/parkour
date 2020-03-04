package org.serversmc.parkour.core

import org.bukkit.*
import org.bukkit.plugin.java.*
import org.serversmc.parkour.cmds.*
import org.serversmc.parkour.cmds.pk.*
import org.serversmc.parkour.events.*
import org.serversmc.parkour.utils.*

lateinit var PLUGIN: Main

class Main : JavaPlugin() {
	
	override fun onEnable() {
		// Initialize libraries
		PLUGIN = this
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
		// Clear managers
		SelectManager.clear()
		EventTracker.clear()
		// Done
		Console.info("Done")
	}
	
	private fun registerCommands() {
		// Main commands
		CParkour.register()
		// Parkour sub commands
		CCreate.register()
		CHelp.register()
		CInfo.register()
		CList.register()
		CSelect.register()
		CSetSpawn.register()
		CSetStart.register()
	}
	
}