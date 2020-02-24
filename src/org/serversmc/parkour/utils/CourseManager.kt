package org.serversmc.parkour.utils

import org.serversmc.*
import org.serversmc.parkour.objects.*
import java.io.*

object CourseManager {
	
	private val dataFolder = File(PLUGIN.dataFolder, "courses")
	private val courses = ArrayList<Course>()
	
	fun getCourses() = courses
	
	fun createCourse(name: String): Course {
		// Create file
		val fileName = name.toLowerCase().replace(" ", "_")
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
	
	fun saveAndClose() {
		courses.forEach { course ->
			val temp = course.getPlayers()
			temp.forEach {
				course.removePlayer(it)
			}
			courses.remove(course)
			course.hide()
			temp.forEach {
				val backup = it.world.getHighestBlockAt(it.location).location.add(0.0, 2.0, 0.0)
				it.teleport(course.getSpawn() ?: backup)
			}
			course.show()
		}
	}
	
}