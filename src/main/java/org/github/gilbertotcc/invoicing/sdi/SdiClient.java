package org.github.gilbertotcc.invoicing.sdi;

import org.github.gilbertotcc.invoicing.sdi.impl.SdiClientImpl;
import org.github.gilbertotcc.invoicing.sdi.model.Invoice;
import org.github.gilbertotcc.invoicing.sdi.model.Result;

public interface SdiClient {

    static SdiClient newClient() {
        return new SdiClientImpl();
    }

    Result sendInvoice(final Invoice invoice);

}
