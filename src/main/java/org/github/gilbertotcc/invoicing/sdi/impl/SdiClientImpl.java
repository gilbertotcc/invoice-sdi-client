package org.github.gilbertotcc.invoicing.sdi.impl;

import static java.lang.String.format;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.logging.Logger;

import javax.xml.datatype.XMLGregorianCalendar;

import com.github.gilbertotcc.invoicing.sdi.service.ErroreInvioType;
import com.github.gilbertotcc.invoicing.sdi.service.RispostaSdIRiceviFileType;
import com.github.gilbertotcc.invoicing.sdi.service.SdIRiceviFileService;
import org.github.gilbertotcc.invoicing.sdi.SdiClient;
import org.github.gilbertotcc.invoicing.sdi.model.Invoice;
import org.github.gilbertotcc.invoicing.sdi.model.Result;

public class SdiClientImpl implements SdiClient {

    private static final Logger LOGGER = Logger.getLogger(SdiClientImpl.class.getName());

    final SdIRiceviFileService service;

    SdiClientImpl(final SdIRiceviFileService service) {
        this.service = service;
    }

    public SdiClientImpl() {
        this(new SdIRiceviFileService());
    }

    @Override
    public Result sendInvoice(final Invoice invoice) {
        LOGGER.info(() -> format("Sending invoice %s", invoice));
        final RispostaSdIRiceviFileType response = service
                .getSdIRiceviFilePort()
                .riceviFile(invoice.toSdiInvoice());

        final String sdiIdentifier = Optional.ofNullable(response.getIdentificativoSdI())
                .map(BigInteger::toString)
                .orElse(null);
        final ZonedDateTime sdiReceptionDateTime = Optional.ofNullable(response.getDataOraRicezione())
                .map(XMLGregorianCalendar::toGregorianCalendar)
                .map(GregorianCalendar::toZonedDateTime)
                .orElse(null);
        final Result.Error error = Optional.ofNullable(response.getErrore())
                .map(SdiClientImpl::resultErrorOf)
                .orElse(null);
        return Result.of(sdiIdentifier, sdiReceptionDateTime, error);
    }

    private static Result.Error resultErrorOf(final ErroreInvioType sendingError) {
        switch (sendingError) {
            case EI_01:
                return Result.Error.EMPTY_FILE;
            case EI_02:
                return Result.Error.SERVICE_NOT_AVAILABLE;
            case EI_03:
                return Result.Error.USER_NOT_ENABLED;
            default:
                return Result.Error.UNKNOWN;
        }
    }
}
