package com.mcblox.parkour.objects;

import static org.bukkit.Material.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class CourseRegion {

	private static List<Material> delayedMaterial = new ArrayList<Material>();
	
	static {
		delayedMaterial.add(SIGN);
		delayedMaterial.add(WALL_SIGN);
		delayedMaterial.add(LADDER);
		delayedMaterial.add(TORCH);
		delayedMaterial.add(WALL_TORCH);
		for (Material mat : Material.values()) {
			if (mat.name().endsWith("_PLATE")) {
				delayedMaterial.add(mat);
			}
		}
	}
	
	public int id;
	private Integer bottomY = null;
	private List<CourseBlock> blocks = new ArrayList<CourseBlock>();
	private List<Block> rawBlocks = new ArrayList<Block>();
	private Course course;
	private Bounds bounds;
	private Location point;
	private boolean visible = true;

	public CourseRegion(Course course) {
		this.course = course;
		bounds = new Bounds(blocks);
	}
	
	public void setPoint(Block block) {
		point = block.getLocation();
	}

	public void setPoint(Location location) {
		point = location;
	}
	
	public Location getPoint() {
		return point;
	}
	
	public void addBlock(CourseBlock block) {
		blocks.add(block);
	}

	public void addRawBlock(Block block) {
		rawBlocks.add(block);
	}

	public void brewRawBlocks() {
		List<Block> delayed = new ArrayList<Block>();
		blocks.clear();
		for (Block block : rawBlocks) {
			if (delayedMaterial.contains(block.getType())) {
				delayed.add(block);
				continue;
			}
			blocks.add(new CourseBlock(block));
		}
		for (Block block : delayed) {
			blocks.add(new CourseBlock(block));
		}
		rawBlocks.clear();
		updateBounds();
	}

	public List<CourseBlock> getBlocks() {
		return blocks;
	}

	public boolean hasPlayer(Player player) {
		if (bounds.contains(player) && player.getLocation().getBlockY() >= bottomY) {
			return true;
		}
		return false;
	}

	public boolean hasBlock(Block block) {
		for (CourseBlock cBlock : blocks) {
			if (cBlock.getLocation().equals(block.getLocation())) {
				return true;
			}
		}
		return false;
	}

	public boolean hasRawBlock(Block block) {
		return rawBlocks.contains(block);
	}

	public boolean isVisible() {
		return visible;
	}

	public Course getCourse() {
		return course;
	}

	public int getBottomY() {
		return bottomY;
	}
	
	public void show() {
		visible = true;
		for (CourseBlock block : blocks) {
			block.show();
		}
	}

	public void hide() {
		List<CourseBlock> temp = new ArrayList<CourseBlock>();
		temp.addAll(blocks);
		Collections.reverse(temp);
		visible = false;
		for (CourseBlock block : temp) {
			block.hide();
		}
	}
	
	public void finishLoad() {
		List<CourseBlock> delayed = new ArrayList<CourseBlock>();
		List<CourseBlock> temp = new ArrayList<CourseBlock>();
		temp.addAll(blocks);
		blocks.clear();
		for (CourseBlock block : temp) {
			if (delayedMaterial.contains(block.getBlockData().getMaterial())) {
				delayed.add(block);
				continue;
			}
			blocks.add(block);
		}
		for (CourseBlock block : delayed) {
			blocks.add(block);
		}
		updateBounds();
	}

	private void updateBounds() {

		for (CourseBlock block : blocks) {

			Location loc = block.getLocation();

			// Update Y
			if (bottomY == null) {
				bottomY = loc.getBlockY();
			}
			if (loc.getBlockY() < bottomY) {
				bottomY = loc.getBlockY();
			}

			bounds.update(blocks);

		}

	}

}
