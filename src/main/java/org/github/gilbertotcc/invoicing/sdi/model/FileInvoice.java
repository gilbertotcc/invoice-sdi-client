package org.github.gilbertotcc.invoicing.sdi.model;

import java.io.File;
import java.util.Optional;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import com.github.gilbertotcc.invoicing.sdi.service.FileSdIBaseType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class FileInvoice implements Invoice {

    private final File file;

    FileInvoice(final File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .append("file", file.getAbsoluteFile())
                .toString();
    }

    @Override
    public FileSdIBaseType toSdiInvoice() {
        final FileSdIBaseType fileSdIBase = new FileSdIBaseType();
        fileSdIBase.setNomeFile(file.getName());
        Optional.of(file)
                .map(FileDataSource::new)
                .map(DataHandler::new)
                .ifPresent(fileSdIBase::setFile);
        return fileSdIBase;
    }
}
