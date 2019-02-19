package com.mcblox.parkour.objects;

import org.bukkit.Location;

public class Position {

	public int position;
	public Location lastCheckpoint;
	public int checkPosition = -1;

	public Position(Integer pos, Location loc) {
		position = pos;
		lastCheckpoint = loc;
	}

}
