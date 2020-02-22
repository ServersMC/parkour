package org.serversmc.parkour.objects

import org.bukkit.entity.*

class Course(fileName: String) {
	
	enum class Mode {
		PLAY, EDIT, CLOSED
	}
	
	// player: CPlayer, position: Int
	private val players = HashMap<CPlayer, Int>()
	private val regions = ArrayList<CRegion>()
	
	private lateinit var mode: Mode
	private lateinit var name: String
	private lateinit var creator: String
	
	private var plays = 0
	private var wins = 0
	
	init {
		// TODO - Load course data file
	}
	
	fun getPlayers():ArrayList<CPlayer> {
		// TODO - Convert HashMap into player ArrayList
		return ArrayList()
	}
	
	fun getRegions():ArrayList<CRegion> = regions
	
	fun addPlayer(player: Player) {
		// TODO - Add player to course
	}
	
	fun removePlayer(player: Player) {
		// TODO - Remove player from course
	}
	
	fun getPlayerPos(player: Player):Int {
		// TODO - Get player region position from HashMap
		return 0
	}
	
	fun addRegion(region: CRegion) {
		// TODO - Add region to list
	}
	
	fun removeRegion(region: CRegion) {
		// TODO - Remove region from list
	}
	
	fun save() {
		// TODO - Save course to YAML file
	}
	
}