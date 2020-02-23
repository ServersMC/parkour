package org.serversmc.parkour.objects

class PlayerData {
	
	private var checkpoint: CSensor? = null
	private var position = 0
	private var timeStarted = 0L
	
	fun getCheckpoint(): CSensor? = checkpoint
	
	fun setCheckpoint(sensor: CSensor) {
		checkpoint = sensor
	}
	
	fun getPosition(): Int = position
	
	fun setPosition(position: Int) {
		this.position = position
	}
	
	fun getTimeStarted(): Long = timeStarted
	
	fun updateTimeStart() {
		timeStarted = System.currentTimeMillis()
	}
	
}