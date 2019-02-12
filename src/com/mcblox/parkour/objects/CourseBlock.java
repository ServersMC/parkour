package com.mcblox.parkour.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;

public class CourseBlock {

	private Location location;
	private BlockData data;
	private List<String> lines = new ArrayList<String>();

	public CourseBlock(Block block) {
		location = block.getLocation().clone();
		data = block.getBlockData().clone();
		if (block.getState() instanceof Sign) {
			Sign sign = (Sign) block.getState();
			for (String line : sign.getLines()) {
				lines.add(line);
			}
		}
	}

	public CourseBlock(Location loc, BlockData data, List<String> lines) {
		location = loc;
		this.data = data;
		this.lines = lines;
	}
	
	public void show() {
		Block block = location.getBlock();
		block.setBlockData(data);
		if (block.getState() instanceof Sign) {
			Sign sign = (Sign) block.getState();
			for (int i = 0; i < lines.size(); i++) {
				sign.setLine(i, lines.get(i));
			}
			sign.update();
		}
	}

	public void hide() {
		Block block = location.getBlock();
		block.setType(Material.AIR);
	}

	public Location getLocation() {
		return location;
	}
	
	public BlockData getBlockData() {
		return data;
	}
	
	public List<String> getLines() {
		return lines;
	}

}