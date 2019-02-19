package com.mcblox.parkour.utils;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.mcblox.parkour.objects.Course;

public class CourseSelect {

	public static HashMap<Player, Course> selected = new HashMap<Player, Course>();
	
	public static void add(Player player, Course course) {
		if (selected.containsKey(player)) {
			selected.replace(player, course);
		}
		else {
			selected.put(player, course);
		}
	}
	
	public static void remove(Player player) {
		if (selected.containsKey(player)) {
			selected.remove(player);
		}
	}
	
	public static Course get(Player player) {
		return selected.get(player);
	}
	
	public static Player get(Course course) {
		for (Entry<Player, Course> e : selected.entrySet()) {
			if (e.getValue().equals(course)) {
				return e.getKey();
			}
		}
		return null;
	}
	
	public static boolean contains(Player player) {
		return selected.containsKey(player);
	}
	
	public static boolean contains(Course course) {
		return selected.containsValue(course);
	}
	
	public static void noSelectionMessage(Player player) {
		player.sendMessage(ChatColor.RED + "Select a course using /parkour select");
	}
	
}
