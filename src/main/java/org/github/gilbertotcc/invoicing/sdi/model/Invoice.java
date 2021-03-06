package org.github.gilbertotcc.invoicing.sdi.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Invoice {

    private final Attachment attachment;

    private Invoice(final Attachment attachment) {
        this.attachment = attachment;
    }

    public static Invoice of(String pathname) {
        Attachment attachment = Attachment.of(pathname);
        return new Invoice(attachment);
    }

    public Attachment getAttachment() {
        return attachment;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
