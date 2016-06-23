package com.ritesh;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeTest {

	public static void main(String[] args) {
		String inputDate = "Mar-23-2016";
		long dayId = getDayId(inputDate);
		System.out.println(("DATE: " + inputDate + ", dayID: " + dayId));
		// Current Date
		LocalDate today = LocalDate.now();
		System.out.println("Current Date=" + today);

		// Creating LocalDate by providing input arguments
		LocalDate firstDay_2014 = LocalDate.of(2014, Month.JANUARY, 1);
		System.out.println("Specific Date=" + firstDay_2014);


		// Try creating date by providing invalid inputs
		// LocalDate feb29_2014 = LocalDate.of(2014, Month.FEBRUARY, 29);
		// Exception in thread "main" java.time.DateTimeException:
		// Invalid date 'February 29' as '2014' is not a leap year

		// Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
		LocalDate todayKolkata = LocalDate.now(ZoneId.of("Asia/Kolkata"));
		System.out.println("Current Date in IST=" + todayKolkata);

		// java.time.zone.ZoneRulesException: Unknown time-zone ID: IST
		// LocalDate todayIST = LocalDate.now(ZoneId.of("IST"));

		// Getting date from the base date i.e 01/01/1970
		LocalDate dateFromBase = LocalDate.ofEpochDay(365);
		System.out.println("365th day from base date= " + dateFromBase);

		LocalDate hundredDay2014 = LocalDate.ofYearDay(2014, 100);
		System.out.println("100th day of 2014=" + hundredDay2014);
		System.out.println("================================");
		// Current Time
		LocalTime time = LocalTime.now();
		System.out.println("Current Time=" + time);

		// Creating LocalTime by providing input arguments
		LocalTime specificTime = LocalTime.of(12, 20, 25, 40);
		System.out.println("Specific Time of Day=" + specificTime);


		// Try creating time by providing invalid inputs
		// LocalTime invalidTime = LocalTime.of(25,20);
		// Exception in thread "main" java.time.DateTimeException:
		// Invalid value for HourOfDay (valid values 0 - 23): 25

		// Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
		LocalTime timeKolkata = LocalTime.now(ZoneId.of("Asia/Kolkata"));
		System.out.println("Current Time in IST=" + timeKolkata);

		// java.time.zone.ZoneRulesException: Unknown time-zone ID: IST
		// LocalTime todayIST = LocalTime.now(ZoneId.of("IST"));

		// Getting date from the base date i.e 01/01/1970
		LocalTime specificSecondTime = LocalTime.ofSecondOfDay(10000);
		System.out.println("10000th second time= " + specificSecondTime);
		System.out.println("================================");
		// Format examples
		LocalDate date = LocalDate.now();
		// default format
		System.out.println("Default format of LocalDate=" + date);
		// specific format
		System.out.println(date.format(DateTimeFormatter.ofPattern("d::MMM::uuuu")));
		System.out.println(date.format(DateTimeFormatter.BASIC_ISO_DATE));


		LocalDateTime dateTime = LocalDateTime.now();
		// default format
		System.out.println("Default format of LocalDateTime=" + dateTime);
		// specific format
		System.out.println(dateTime.format(DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss")));
		System.out.println(dateTime.format(DateTimeFormatter.BASIC_ISO_DATE));

		Instant timestamp = Instant.now();
		// default format
		System.out.println("Default format of Instant=" + timestamp);

		// Parse examples
		LocalDateTime dt = LocalDateTime.parse("27::Apr::2014 21::39::48", DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss"));
		System.out.println("Default format after parsing = " + dt);
		System.out.println("================================");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-d-yyyy HH:mm:ss");
		LocalDateTime frEpochTime = LocalDateTime.parse("Jan-1-2001 00:00:00", formatter);
		// GET DAY NUMBER FROM DATE
		String input = "Apr-5-2016";
		LocalDateTime targetTime = LocalDateTime.parse(input + " 00:00:00", formatter);
		long dayCount = ChronoUnit.DAYS.between(frEpochTime, targetTime);
		System.out.println(input + ": " + dayCount);


		LocalDateTime endTime = LocalDateTime.parse(input + " 23:59:59", formatter);
		System.out.println(targetTime + ":00Z" + " - " + endTime + ":00Z");
		/*
		 * InstantFormatter formatter2 =InstantFormatter.ofPattern("MMM-d-yyyy HH:mm:ss"); String input2="Jan-7-2002"+" 00:00:00"; Instant
		 * instantX = Instant.parse(input, formatter); U.p(instantX+":00Z");
		 */
	}

	public static long getDayId(String inputDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-d-yyyy HH:mm:ss");
		LocalDateTime frEpochTime = LocalDateTime.parse("Jan-1-2001 00:00:00", formatter);
		LocalDateTime targetTime = LocalDateTime.parse(inputDate + " 00:00:00", formatter);
		long dayCount = ChronoUnit.DAYS.between(frEpochTime, targetTime);
		return dayCount;
	}

}
