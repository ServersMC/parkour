package org.serversmc.parkour.core

import org.bukkit.plugin.java.JavaPlugin
import org.serversmc.ServersMC
import org.serversmc.utils.Console

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