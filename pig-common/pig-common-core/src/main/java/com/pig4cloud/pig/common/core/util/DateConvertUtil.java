package com.pig4cloud.pig.common.core.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * <p>Title: DateConvertUtil</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月04日
 * @since 1.8
 */
public class DateConvertUtil {

	/**
	 * java.util.Date --> java.time.LocalDateTime
	 * <p>Title: dateToLocalDateTime</p>
	 * <p>Description: </p>
	 *
	 * @return void
	 * @date 2019年06月04日
	 * @author 余新引
	 */
	public static LocalDateTime dateToLocalDateTime(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}

	/**
	 * java.util.Date --> java.time.LocalDate
	 * <p>Title: dateToLocalDate</p>
	 * <p>Description: </p>
	 *
	 * @return void
	 * @date 2019年06月04日
	 * @author 余新引
	 */
	public static LocalDate dateToLocalDate(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		return localDateTime.toLocalDate();
	}

	/**
	 * java.util.Date --> java.time.LocalTime
	 * <p>Title: dateToLocalTime</p>
	 * <p>Description: </p>
	 *
	 * @return void
	 * @date 2019年06月04日
	 * @author 余新引
	 */
	public static LocalTime dateToLocalTime(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		return localDateTime.toLocalTime();
	}


	/**
	 * java.time.LocalDateTime --> java.util.Date
	 * <p>Title: LocalDateTimeToDate</p>
	 * <p>Description: </p>
	 *
	 * @return void
	 * @date 2019年06月04日
	 * @author 余新引
	 */
	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return Date.from(instant);
	}


	/**
	 * java.time.LocalDate --> java.util.Date
	 * <p>Title: LocalDateToDate</p>
	 * <p>Description: </p>
	 *
	 * @return void
	 * @date 2019年06月04日
	 * @author 余新引
	 */
	public static Date localDateToDate(LocalDate localDate) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
		return Date.from(instant);
	}

	/**
	 * java.time.LocalTime --> java.util.Date
	 * <p>Title: LocalTimeToDate</p>
	 * <p>Description: </p>
	 *
	 * @return void
	 * @date 2019年06月04日
	 * @author 余新引
	 */
	public static Date localTimeToDate(LocalDate localDate, LocalTime localTime) {
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return Date.from(instant);
	}
}
