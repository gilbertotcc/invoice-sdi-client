package org.github.gilbertotcc.invoicing.sdi;

import org.github.gilbertotcc.invoicing.sdi.impl.SdiClientImpl;
import org.github.gilbertotcc.invoicing.sdi.model.Invoice;
import org.github.gilbertotcc.invoicing.sdi.model.Notification;
import org.github.gilbertotcc.invoicing.sdi.model.SendInvoiceResponse;
import org.github.gilbertotcc.invoicing.sdi.model.SendNotificationResponse;

public interface SdiClient {

    static SdiClient newClient() {
        return new SdiClientImpl();
    }

    SendInvoiceResponse sendInvoice(final Invoice invoice);

    SendNotificationResponse sendNotification(final Notification notification);

}
