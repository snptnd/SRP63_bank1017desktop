package edu.pitt.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Used to generate daily error log files and log application errors into text-based log files (/logs) folder
 * @author Dmitriy Babichenko
 * @version 1.1
 */
public class ErrorLogger {

	/**
	 * Add error to log file
	 * @param errorDescription - description of a runtime error
	 */
	public static void log(String errorDescription) {
		/* Create new file object - file name is based on today's date
		 * Note that file name is generated by dateToFilename method of StringUtilities class
		 */
		File file = new File("logs/" + StringUtilities.dateToFilename("txt"));
		try {
			if (!file.exists()) {
				// If log file does not exist, create one with today's date as file name
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			// Write error to file
			pw.println(String.valueOf(new Date()) + "|" + errorDescription);
			pw.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
