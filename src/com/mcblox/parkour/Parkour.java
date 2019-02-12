package com.mcblox.parkour;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcblox.parkour.cmds.CmdParkour;
import com.mcblox.parkour.cmds.parkour.CmdCreate;
import com.mcblox.parkour.cmds.parkour.CmdRegion;
import com.mcblox.parkour.cmds.parkour.CmdSetFinish;
import com.mcblox.parkour.enums.FolderStructure;
import com.mcblox.parkour.events.ParkourPlay;
import com.mcblox.parkour.objects.Course;
import com.mcblox.parkour.utils.Console;

public class Parkour extends JavaPlugin {

	public static String VERSION;
	public static JavaPlugin PLUGIN;
	
	@Override
	public void onEnable() {
		
		// Init plugin static variables
		PLUGIN = this;
		VERSION = getDescription().getVersion();
		
		// Create File Structure
		for (FolderStructure folder : FolderStructure.values()) {
			folder.create();
		}

		// Register commands
		CmdParkour.setupSubCommands();
		PLUGIN.getCommand("parkour").setExecutor(new CmdParkour());
		PLUGIN.getCommand("parkour").setTabCompleter(new CmdParkour());

		// Register events
		Bukkit.getPluginManager().registerEvents(new CmdCreate(), PLUGIN);
		Bukkit.getPluginManager().registerEvents(new CmdRegion(), PLUGIN);
		Bukkit.getPluginManager().registerEvents(new CmdSetFinish(), PLUGIN);
		Bukkit.getPluginManager().registerEvents(new ParkourPlay(), PLUGIN);

		// Load Courses
		Course.loadCourses();
		
		// Done
		Console.info("Enabled!");
		
	}

	@Override
	public void onDisable() {
		
		// Save courses
		for (Course course : Course.courses) {
			course.saveCourse();
			course.show();
		}
		
		// Disabled
		Console.info("Disabled!");
		
	}
	
}
