package com.ddr.util;

import com.ddr.services.DispatchRegisterExcelService;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;

public class CommonUtil {

	private static final Logger logger = LoggerUtil.getLogger(CommonUtil.class);

	// Method to convert java object to JSON String
	/*public static String convertToJson(Map<String, Object> resp) {
		JsonObject jsonObject = new Gson().toJsonTree(resp).getAsJsonObject();

		JsonObject result = new JsonObject();
		result.add("response", jsonObject);

		return result.toString();
	}*/

	// Method to convert date in String to java.sql.Date format
	public static Date convertStringToSqlDate(String dateString) throws ParseException {
		String format = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		java.util.Date date = sdf.parse(dateString);
		return new Date(date.getTime());
	}
	
	// Method to parse date
	public static LocalDate parseDate(String dateStr) {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			return null;
		}
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(dateStr, formatter);
			return date;
        } catch (DateTimeParseException e) {
            logger.debug("Invalid Date Format: " + dateStr);
			e.printStackTrace();
        }
		return null;
	}

	// Method to convert LocalDate to String
	public static String convertToString(LocalDate date) {
		if (date == null) {
			return "";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return date.format(formatter);
	}

	// Method to convert LocalDate to String
	public static String convertToString(java.sql.Date sqlDate) {
		if (sqlDate == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(sqlDate);
	}

}
