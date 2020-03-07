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
		val course = Course(file).apply {
			setName(name)
		}
		courses.add(course)
		return course
	}
	
	fun deleteCourse(course: Course) {
		course.delete()
		courses.remove(course)
	}
	
	fun courseExists(name: String): Boolean {
		courses.forEach {
			if (it.getName().equals(name, true)) return true
		}
		return false
	}
	
	fun loadCourses() {
		dataFolder.listFiles()?.forEach {
			if (it == null) return@forEach
			if (it.name.endsWith(".day", true)) return@forEach
			Course(it).apply {
				courses.add(this)
				load()
			}
		} ?: dataFolder.mkdirs()
	}
	
	fun updateHolograms() {
		courses.forEach { it.updateHolograms() }
	}
	
	fun saveAndClose() {
		courses.forEach { course ->
			course.getPlayers().toTypedArray().forEach {
				course.getPlayers().remove(it)
				it.teleport(course.getSpawn())
			}
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