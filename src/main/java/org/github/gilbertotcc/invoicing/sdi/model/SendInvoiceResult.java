package org.github.gilbertotcc.invoicing.sdi.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Result {

    public enum Error {
        EMPTY_FILE,
        SERVICE_NOT_AVAILABLE,
        USER_NOT_ENABLED,
        UNKNOWN
    }

    private final String sdiIdentifier;

    private final ZonedDateTime sdiReceptionDateTime;

    private final Error error;

    private Result(final String sdiIdentifier, final ZonedDateTime sdiReceptionDateTime, final Error error) {
        this.sdiIdentifier = sdiIdentifier;
        this.sdiReceptionDateTime = sdiReceptionDateTime;
        this.error = error;
    }

    public static Result of(final String sdiIdentifier, final ZonedDateTime sdiReceptionDateTime, final Error error) {
        return new Result(sdiIdentifier, sdiReceptionDateTime, error);
    }

    public String getSdiIdentifier() {
        return sdiIdentifier;
    }

    public ZonedDateTime getSdiReceptionDateTime() {
        return sdiReceptionDateTime;
    }

    public Error getError() {
        return error;
    }

    public boolean isSuccess() {
        return error == null;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .append("sdiIdentifier", sdiIdentifier)
                .append("sdiReceptionDateTime", Optional.ofNullable(sdiReceptionDateTime)
                        .map(dateTime -> dateTime.format(DateTimeFormatter.ISO_DATE_TIME))
                        .orElse(null))
                .append("error", error)
                .toString();
    }
}
