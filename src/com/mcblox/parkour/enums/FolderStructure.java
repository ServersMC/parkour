package com.mcblox.parkour.enums;

import java.io.File;

import com.mcblox.parkour.Parkour;

public enum FolderStructure {

	DATA_FOLDER(getDataFolder()),
	PARKOUR_COURSES(new File(getDataFolder(), "parkour_courses"));

	private File file;
	
	private FolderStructure(File file) {
		this.file = file;
	}
	
	public void create() {
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	public File file() {
		return file;
	}
	
	private static File getDataFolder() {
		return Parkour.PLUGIN.getDataFolder();
	}
	
}
