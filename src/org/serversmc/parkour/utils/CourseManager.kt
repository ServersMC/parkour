package org.serversmc.parkour.utils

import org.bukkit.*
import org.serversmc.parkour.core.*
import org.serversmc.parkour.objects.*
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

object CourseManager {
	
	private val dataFolder = File(PLUGIN.dataFolder, "courses")
	private val courses = ArrayList<Course>()
	
	fun getCourses() = courses
	
	fun createCourse(name: String): Course {
		// Create file
		val fileName = UUID.randomUUID()
		val file = File(dataFolder, "$fileName.dat")
		// Create course
		val course = Course(file).apply {
			setName(name)
		}
		// Add course to list
		courses.add(course)
		// Return created course
		return course
	}
	
	fun deleteCourse(course: Course) {
		course.delete()
		courses.remove(course)
	}
	
	fun courseExists(name: String): Boolean {
		// Check if course exists
		courses.forEach {
			if (it.getName().equals(name, true)) return true
		}
		return false
	}
	
	fun loadCourses() {
		dataFolder.listFiles()?.forEach {
			// Check if file is null
			if (it == null) return@forEach
			// Check if file extension is .dat
			if (it.name.endsWith(".day", true)) return@forEach
			// Load Yaml
			courses.add(Course(it))
		} ?: dataFolder.mkdirs()
	}
	
	fun updateHolograms() {
		courses.forEach { it.updateHolograms() }
	}
	
	fun saveAndClose() {
		courses.forEach { course ->
			val temp = course.getPlayers()
			temp.forEach {
				course.removePlayer(it)
			}
			course.hide()
			temp.forEach {
				val backup = it.world.getHighestBlockAt(it.location).location.add(0.0, 2.0, 0.0)
				it.teleport(course.getSpawn() ?: backup)
			}
			course.show()
			course.save()
		}
		courses.clear()
	}
	
	fun isSensor(location: Location): Boolean {
		courses.forEach { if (it.hasSensor(location)) return true }
		return false
	}
	
	data class CourseSensor(val course: Course, val sensor: CSensor)
	
	fun getSensor(location: Location): CourseSensor? {
		if (!isSensor(location)) return null
		for (course in courses) {
			if (!course.hasSensor(location)) continue
			return CourseSensor(course, course.getSensor(location)!!)
		}
		return null
	}
	
}