package com.mcblox.parkour.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CourseBounds {

	private List<Location2D> locs = new ArrayList<Location2D>();
	
	public CourseBounds(List<CourseBlock> blocks) {
		update(blocks);
	}
	
	public void update(List<CourseBlock> blocks) {
		for (CourseBlock block : blocks) {
			Location loc = block.getLocation();
			if (!contains(loc)) {
				locs.add(new Location2D(loc.getX(), loc.getZ()));
			}
		}
	}
	
	public boolean contains(Player p) {
		for (Location2D loc : locs) {
			if (distance(p, loc) < 1.5d) {
				return true;
			}
		}
		return false;
	}
	
	private double distance(Player p, Location2D loc) {
		return p.getLocation().distance(new Location(p.getWorld(), loc.x, p.getLocation().getY(), loc.z));
	}
	
	private boolean contains(Location l) {
		for (Location2D l2d : locs) {
			if (l.getBlockX() == l2d.x && l.getBlockZ() == l2d.z) {
				return true;
			}
		}
		return false;
	}
	
}
