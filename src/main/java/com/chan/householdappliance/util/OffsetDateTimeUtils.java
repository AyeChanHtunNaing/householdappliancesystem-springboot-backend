package com.chan.householdappliance.util;


import java.time.OffsetDateTime;
import java.time.ZoneId;

import java.time.ZonedDateTime;

import java.util.Calendar;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;

public class OffsetDateTimeUtils {
	
	public static OffsetDateTime convertToOffsetDateTime(String text) {	
		if(StringUtils.isBlank(text)) {
			return null;
		}
		
		Calendar calendar = DatatypeConverter.parseDateTime(text);
		TimeZone tz = calendar.getTimeZone();
		ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
		ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(calendar.toInstant(), zid);
		ZonedDateTime timeInZoned = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
		
		return timeInZoned.toOffsetDateTime();
	}

}