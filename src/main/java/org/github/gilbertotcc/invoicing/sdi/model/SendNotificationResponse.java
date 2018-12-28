package org.github.gilbertotcc.invoicing.sdi.model;

import java.util.Optional;
import java.util.stream.Stream;

import com.github.gilbertotcc.invoicing.sdi.invoiceService.EsitoNotificaType;
import com.github.gilbertotcc.invoicing.sdi.invoiceService.RispostaSdINotificaEsitoType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SendNotificationResponse {

    public enum Result {

        NOT_ACCEPTED(EsitoNotificaType.ES_00),
        ACCEPTED(EsitoNotificaType.ES_01),
        SERVICE_NOT_AVAILABLE(EsitoNotificaType.ES_02);

        private EsitoNotificaType esitoNotificaType;

        Result(EsitoNotificaType esitoNotificaType) {
            this.esitoNotificaType = esitoNotificaType;
        }

        public static Result byEsitoNotificaType(EsitoNotificaType esitoNotificaType) {
            return Stream.of(values())
                    .filter(result -> result.esitoNotificaType == esitoNotificaType)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown esitoNotificaType"));
        }
    }

    private final Result result;

    private final Attachment attachment;

    private SendNotificationResponse(final Result result, final Attachment attachment) {
        this.result = result;
        this.attachment = attachment;
    }

    public static SendNotificationResponse of(final Result result, final Attachment attachment) {
        return new SendNotificationResponse(result, attachment);
    }

    public static SendNotificationResponse of(final RispostaSdINotificaEsitoType rispostaSdINotificaEsitoType) {
        Result result = Optional.ofNullable(rispostaSdINotificaEsitoType.getEsito())
                .map(Result::byEsitoNotificaType)
                .orElse(null);
        Attachment attachment = Optional.ofNullable(rispostaSdINotificaEsitoType.getScartoEsito())
                .map(fileSdIBaseType -> Attachment.of(fileSdIBaseType.getNomeFile(), fileSdIBaseType.getFile()))
                .orElse(null);
        return new SendNotificationResponse(result, attachment);
    }

    public Result getResult() {
        return result;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
