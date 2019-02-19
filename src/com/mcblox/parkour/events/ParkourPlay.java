package com.mcblox.parkour.events;

import static org.bukkit.ChatColor.*;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.mcblox.parkour.objects.Course;
import com.mcblox.parkour.objects.CourseBlock;
import com.mcblox.parkour.objects.CourseRegion;
import com.mcblox.parkour.utils.CourseSelect;
import com.mcblox.parkour.utils.TitleAPI;

public class ParkourPlay implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		// Check if physical action
		if (!event.getAction().equals(Action.PHYSICAL)) {
			return;
		}

		// Initialize variables
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		// Check if block is a course start/finish block
		for (Course course : Course.courses) {
			
			if (course.getStartBlock() == null) {
				continue;
			}
			
			Location startBlock = course.getStartBlock().getLocation();
			Location finishBlock = course.getFinishBlock().getLocation();
			
			if (startBlock.equals(block.getLocation())) {
				
				// Check if course is ready
				if (!course.isReady()) {
					TitleAPI.sendTitle(player, 10, 40, 10, GOLD + "Sorry", YELLOW + "This course is not ready");
					return;
				}
				
				// Check if course is selected
				if (CourseSelect.contains(course)) {
					TitleAPI.sendTitle(player, 10, 40, 10, GOLD + "Sorry", YELLOW + "This course is being edited");
					CourseSelect.get(course).sendMessage(YELLOW + "Someone is trying to play your selected course!");
					CourseSelect.get(course).sendMessage(YELLOW + "Type " + GOLD + "/parkour select" + YELLOW + " to finish edit!");
					return;
				}

				// Check if player is already in queue
				if (course.isPlaying(player)) {
					return;
				}

				// Give instructions
				TitleAPI.sendTitle(player, 10, 10000, 0, AQUA + "Parkour?", DARK_AQUA + "Jump to next block to start!");

				// Add player to queue
				course.addQueue(player);
				return;

			}

			if (finishBlock.equals(block.getLocation())) {

				// Check if player is in queue
				if (!course.isPlaying(player)) {
					return;
				}

				// Prompt winning
				TitleAPI.sendTitle(player, 10, 40, 10, GREEN + "You won!", DARK_GREEN + "Congratulations!");

				// Remove from queue
				course.removePlayer(player);

			}
			
			for (CourseRegion region : course.getRegions()) {
				
				// Check if region had a checkpoint
				if (region.getPoint() != null) {

					// Check if player is in queue
					if (!course.isPlaying(player)) {
						return;
					}
					
					// Check if block is the checkpoint
					if (!region.getPoint().equals(block.getLocation())) {
						return;
					}
					
					// Update checkpoint
					course.updatePlayerCheckpoint(player, block, region);
					
					// Send Title
					TitleAPI.sendTitle(player, 10, 40, 10, "", AQUA + "Check Point!");
					
				}
			}
		}

		// -- End: onCourseStart(PlayerInteractEvent)
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

		// Initialize variables
		Player player = event.getPlayer();
		Course course = null;

		// Check if player is in a course
		for (Course find : Course.courses) {
			if (find.isPlaying(player)) {
				course = find;
				break;
			}
		}

		// Check if course is found
		if (course == null) {
			return;
		}

		// Check if player walked away
		if (course.isQueue(player)) {
			if (event.getTo().distance(course.getStartBlock().getLocation()) > 5) {
				course.removePlayer(player);
				TitleAPI.sendTitle(player, 0, 40, 10, AQUA + "Parkour?", YELLOW + "Maybe next time?");
			}
		}

		// Check which region the player is in
		CourseRegion region = null;
		for (CourseRegion find : course.getRegions()) {
			if (find.hasPlayer(player)) {
				region = find;
				break;
			}
		}

		// Check if found region
		if (region != null) {
			// Start course for player in queue
			if (course.getPlayerPos(player) == -1) {
				
				course.startPlayer(player);
				
				// Clear Title upon start
				TitleAPI.sendTitle(player, 0, 0, 0, "", "");
				
			}
			
			// Update player position
			if (course.getPlayerPos(player) < region.id) {
				course.updatePlayerPos(player, region.id);
			}
		}
		
		// Check if player is not in queue
		if (course.getPlayerPos(player) == -1) {
			return;
		}
		
		// Get player sections bottomY
		int pos = course.getPlayerPos(player);
		int bottomY = 255;
		double minDistance = 255;
		for (int i = 0; i < Course.VIEW_DISTANCE; i++) {
			try {
				CourseRegion tempRegion = course.getRegions().get(pos + 0);
				if (tempRegion.getBottomY() < bottomY) {
					bottomY = tempRegion.getBottomY();
					for (CourseBlock block : tempRegion.getBlocks()) {
						double distance = block.getLocation().distance(player.getLocation());
						if (distance < minDistance) {
							minDistance = distance;
						}
					}
				}
			} catch (IndexOutOfBoundsException e) {}
		}
		
		// Check if player fell
		if (event.getFrom().getY() <= bottomY - 1) {
			if (player.isOnGround() || event.getFrom().getY() <= bottomY - 3) {
				course.resetPlayer(player);
				TitleAPI.sendTitle(player, 10, 20, 10, "", BOLD + "Try again!");
			}
		}
		
		// Check if player walked away from region inside of fall zone
		if (minDistance > 5) {
			course.resetPlayer(player);
			TitleAPI.sendTitle(player, 10, 20, 10, "", BOLD + "Try again!");
		}
		
		// -- End: onRegionCheck(PlayerMoveEvent)
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		// Initialize variables
		Player player = event.getPlayer();
		Course course = null;

		// Check if player is in a course
		for (Course find : Course.courses) {
			if (find.isPlaying(player)) {
				course = find;
				break;
			}
		}
		
		player.teleport(course.getSpawn());
		
		// Remove player from game
		course.removePlayer(player);
		
		// -- End: onPlayerQuit(PlayerQuitEvent)
	}
	
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event) {
		
		// Check if entity is player
		if (!event.getEntityType().equals(EntityType.PLAYER)) {
			return;
		}
		
		// Initialize variables
		Player player = (Player) event.getEntity();
		
		// Check if player is in a course
		for (Course course : Course.courses) {
			if (course.isPlaying(player)) {
				
				// Cancel fall damage
				event.setCancelled(true);
				
			}
		}
		
	}
	

}
