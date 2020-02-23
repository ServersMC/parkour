package org.serversmc.parkour.objects

class PlayerData {
	
	private var checkpoint: CSensor? = null
	private var position = 0
	private var timeStarted = 0L
	
	fun getCheckpoint() = checkpoint
	
	fun setCheckpoint(sensor: CSensor) {
		checkpoint = sensor
	}
	
	fun getPosition() = position
	
	fun setPosition(position: Int) {
		this.position = position
	}
	
	fun getTimeStarted() = timeStarted
	
	fun updateTimeStart() {
		timeStarted = System.currentTimeMillis()
	}
	
}