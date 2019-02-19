package com.mcblox.parkour;

import static com.mcblox.parkour.enums.ParkourSubCommands.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcblox.parkour.cmds.CmdParkour;
import com.mcblox.parkour.enums.FolderStructure;
import com.mcblox.parkour.events.ParkourPlay;
import com.mcblox.parkour.objects.Course;
import com.mcblox.parkour.utils.Console;
import com.mcblox.parkour.utils.TitleAPI;

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
		CmdParkour cmdParkour = new CmdParkour();
		PLUGIN.getCommand("parkour").setExecutor(cmdParkour);
		PLUGIN.getCommand("parkour").setTabCompleter(cmdParkour);

		// Register command events
		Bukkit.getPluginManager().registerEvents(SET_START.getCmd(), PLUGIN);
		Bukkit.getPluginManager().registerEvents(REGION.getCmd(), PLUGIN);
		Bukkit.getPluginManager().registerEvents(SET_FINISH.getCmd(), PLUGIN);
		Bukkit.getPluginManager().registerEvents(DELETE.getCmd(), PLUGIN);
		Bukkit.getPluginManager().registerEvents(ADD_CHECK.getCmd(), PLUGIN);
		
		// Register stand-alone events
		Bukkit.getPluginManager().registerEvents(new ParkourPlay(), PLUGIN);

		// Load Courses
		Course.loadCourses();
		
		// Done
		Console.info("Enabled!");
		
	}

	@Override
	public void onDisable() {
		
		// Clear Titles
		for (Player player : Bukkit.getOnlinePlayers()) {
			TitleAPI.sendTitle(player, 0, 0, 0, "", "");
		}
		
		// Save courses
		for (Course course : Course.courses) {
			course.saveCourse();
			course.show();
		}
		
		// Disabled
		Console.info("Disabled!");
		
	}
	
}
