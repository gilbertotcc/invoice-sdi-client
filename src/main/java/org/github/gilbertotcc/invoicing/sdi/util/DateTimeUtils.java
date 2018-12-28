package org.github.gilbertotcc.invoicing.sdi.util;

import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.xml.datatype.XMLGregorianCalendar;

public class DateTimeUtils {

    private DateTimeUtils() {}

    public static ZonedDateTime toZonedDateTime(final XMLGregorianCalendar xmlGregorianCalendar) {
        return Optional.ofNullable(xmlGregorianCalendar)
                .map(XMLGregorianCalendar::toGregorianCalendar)
                .map(GregorianCalendar::toZonedDateTime)
                .orElse(null);
    }
}
