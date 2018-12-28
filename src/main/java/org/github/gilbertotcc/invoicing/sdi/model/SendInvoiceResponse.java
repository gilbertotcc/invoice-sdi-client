package org.github.gilbertotcc.invoicing.sdi.model;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import com.github.gilbertotcc.invoicing.sdi.invoiceService.ErroreInvioType;
import com.github.gilbertotcc.invoicing.sdi.invoiceService.RispostaSdIRiceviFileType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.github.gilbertotcc.invoicing.sdi.util.DateTimeUtils;

public class SendInvoiceResponse {

    public enum Error {

        EMPTY_FILE(ErroreInvioType.EI_01),
        SERVICE_NOT_AVAILABLE(ErroreInvioType.EI_02),
        USER_NOT_ENABLED(ErroreInvioType.EI_03);

        private ErroreInvioType erroreInvioType;

        Error(ErroreInvioType erroreInvioType) {
            this.erroreInvioType = erroreInvioType;
        }

        public static Error byErroreInvioType(ErroreInvioType erroreInvioType) {
            return Stream.of(values())
                    .filter(error -> error.erroreInvioType == erroreInvioType)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown erroreInvioType"));
        }
    }

    private final String sdiIdentifier;

    private final ZonedDateTime sdiReceptionDateTime;

    private final Error error;

    private SendInvoiceResponse(final String sdiIdentifier, final ZonedDateTime sdiReceptionDateTime, final Error error) {
        this.sdiIdentifier = sdiIdentifier;
        this.sdiReceptionDateTime = sdiReceptionDateTime;
        this.error = error;
    }

    public static SendInvoiceResponse of(final String sdiIdentifier, final ZonedDateTime sdiReceptionDateTime, final Error error) {
        return new SendInvoiceResponse(sdiIdentifier, sdiReceptionDateTime, error);
    }

    public static SendInvoiceResponse of(final RispostaSdIRiceviFileType response) {
        String sdiIdentifier = Optional.ofNullable(response.getIdentificativoSdI())
                .map(BigInteger::toString)
                .orElse(null);
        ZonedDateTime sdiReceptionDateTime = Optional.ofNullable(response.getDataOraRicezione())
                .map(DateTimeUtils::toZonedDateTime)
                .orElse(null);
        SendInvoiceResponse.Error error = Optional.ofNullable(response.getErrore())
                .map(Error::byErroreInvioType)
                .orElse(null);
        return SendInvoiceResponse.of(sdiIdentifier, sdiReceptionDateTime, error);
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
