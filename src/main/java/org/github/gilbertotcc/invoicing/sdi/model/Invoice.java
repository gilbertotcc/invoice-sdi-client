package org.github.gilbertotcc.invoicing.sdi.model;

import static java.lang.String.format;

import java.io.File;
import java.util.Optional;

public interface Invoice extends SdiInvoice {

    static Invoice fromFile(final String pathname) {
        File invoiceFile = Optional.of(pathname)
                .map(File::new)
                .filter(File::exists)
                .orElseThrow(() -> new NullPointerException(format("Cannot create invoice from file %s", pathname)));
        return new FileInvoice(invoiceFile);
    }
}
