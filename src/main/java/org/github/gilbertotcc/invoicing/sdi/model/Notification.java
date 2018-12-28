package org.github.gilbertotcc.invoicing.sdi.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Notification {

    private final String sdiIdentifier;

    private final Attachment attachment;

    private Notification(final String sdiIdentifier, final Attachment attachment) {
        this.sdiIdentifier = sdiIdentifier;
        this.attachment = attachment;
    }

    public static Notification of(final String sdiIdentifier, final Attachment attachment) {
        return new Notification(sdiIdentifier, attachment);
    }

    public String getSdiIdentifier() {
        return sdiIdentifier;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
