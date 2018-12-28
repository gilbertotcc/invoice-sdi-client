package org.github.gilbertotcc.invoicing.sdi.impl;

import static java.lang.String.format;

import java.math.BigInteger;
import java.util.logging.Logger;

import com.github.gilbertotcc.invoicing.sdi.invoiceService.FileSdIBaseType;
import com.github.gilbertotcc.invoicing.sdi.invoiceService.FileSdIType;
import com.github.gilbertotcc.invoicing.sdi.invoiceService.RispostaSdINotificaEsitoType;
import com.github.gilbertotcc.invoicing.sdi.invoiceService.RispostaSdIRiceviFileType;
import com.github.gilbertotcc.invoicing.sdi.invoiceService.SdIRiceviFileService;
import com.github.gilbertotcc.invoicing.sdi.invoiceService.SdIRiceviNotificaService;
import org.github.gilbertotcc.invoicing.sdi.SdiClient;
import org.github.gilbertotcc.invoicing.sdi.model.Invoice;
import org.github.gilbertotcc.invoicing.sdi.model.Notification;
import org.github.gilbertotcc.invoicing.sdi.model.SendInvoiceResponse;
import org.github.gilbertotcc.invoicing.sdi.model.SendNotificationResponse;

public class SdiClientImpl implements SdiClient {

    private static final Logger LOGGER = Logger.getLogger(SdiClientImpl.class.getName());

    private final SdIRiceviFileService invoiceService;

    private final SdIRiceviNotificaService notificationService;

    SdiClientImpl(final SdIRiceviFileService invoiceService, final SdIRiceviNotificaService notificationService) {
        this.invoiceService = invoiceService;
        this.notificationService = notificationService;
    }

    public SdiClientImpl() {
        this(new SdIRiceviFileService(), new SdIRiceviNotificaService());
    }

    @Override
    public SendInvoiceResponse sendInvoice(final Invoice invoice) {
        LOGGER.info(() -> format("Sending invoice %s", invoice));

        FileSdIBaseType fileSdIBaseType = new FileSdIBaseType();
        fileSdIBaseType.setNomeFile(invoice.getAttachment().getFilename());
        fileSdIBaseType.setFile(invoice.getAttachment().getDataHandler());

        RispostaSdIRiceviFileType response = invoiceService
                .getSdIRiceviFilePort()
                .riceviFile(fileSdIBaseType);

        return SendInvoiceResponse.of(response);
    }

    @Override
    public SendNotificationResponse sendNotification(final Notification notification) {
        LOGGER.info(() ->
                format("Send notification %s for invoiceId %s",
                notification.getAttachment(),
                notification.getSdiIdentifier()));

        FileSdIType fileSdIType = new FileSdIType();
        fileSdIType.setIdentificativoSdI(new BigInteger(notification.getSdiIdentifier()));
        fileSdIType.setNomeFile(notification.getAttachment().getFilename());
        fileSdIType.setFile(notification.getAttachment().getDataHandler());

        RispostaSdINotificaEsitoType response = notificationService
                .getSdIRiceviNotificaPort()
                .notificaEsito(fileSdIType);

        return SendNotificationResponse.of(response);
    }
}
