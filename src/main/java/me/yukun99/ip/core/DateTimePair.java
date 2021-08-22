package me.yukun99.ip.core;

import me.yukun99.ip.exceptions.HelpBotDateTimeFormatException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Class that stores both a date and time field.
 */
public class DateTimePair {
	private LocalDate date;
	private LocalTime time;

	/**
	 * Constructor for a DateTimePair instance.
	 *
	 * @param date Date stored in this instance.
	 * @param time Time stored in this instance.
	 */
	public DateTimePair(LocalDate date, LocalTime time) {
		this.date = date;
		this.time = time;
	}

	/**
	 * Updates the current DateTimePair instance with newly parsed values.
	 *
	 * @param pair DateTimePair instance containing newly parsed values.
	 */
	public void update(DateTimePair pair) {
		this.date = pair.date;
		this.time = pair.time;
	}

	/**
	 * Parses a String to obtain a DateTimePair object.
	 *
	 * @param strDate String to be parsed.
	 * @return DateTimePair instance with the date and time specified in the String.
	 * @throws HelpBotDateTimeFormatException If date or time is provided in the wrong format.
	 */
	public static DateTimePair parse(String strDate) throws HelpBotDateTimeFormatException {
		String[] dateSplit = strDate.split(" ");
		LocalDate date;
		LocalTime time = null;
		try {
			date = LocalDate.parse(dateSplit[0]);
		} catch (DateTimeParseException e) {
			throw new HelpBotDateTimeFormatException(dateSplit[0]);
		}
		try {
			time = LocalTime.parse(dateSplit[1]);
		} catch (DateTimeParseException | ArrayIndexOutOfBoundsException ignored) {}
		return new DateTimePair(date, time);
	}

	/**
	 * Checks if another DateTimePair instance has the same date.
	 *
	 * @param o DateTimePair instance to be checked against.
	 * @return Whether the other DateTimePair instance has the same date.
	 */
	public boolean hasEqualDate(DateTimePair o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		return this.date.equals(o.date);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (!(o instanceof DateTimePair)) {
			return false;
		}
		DateTimePair dateTimePair = (DateTimePair) o;
		return this.date.equals(dateTimePair.date) && this.time.equals(dateTimePair.time);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.date, this.time);
	}

	@Override
	public String toString() {
		if (this.time == null) {
			return this.date.format(DateTimeFormatter.ofPattern("d MMM yyyy"));
		}
		return this.date.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ", "
				+ this.time.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
	}
}
