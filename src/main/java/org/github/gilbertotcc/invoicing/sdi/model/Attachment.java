package org.github.gilbertotcc.invoicing.sdi.model;

import static java.lang.String.format;

import java.io.File;
import java.util.Optional;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Attachment {

    private final String filename;

    private final DataHandler dataHandler;

    private Attachment(final String filename, final DataHandler dataHandler) {
        this.filename = filename;
        this.dataHandler = dataHandler;
    }

    public static Attachment of(final String filename, final DataHandler dataHandler) {
        return new Attachment(filename, dataHandler);
    }

    public static Attachment of(final File file) {
        return Optional.of(file)
                .filter(File::exists)
                .map(f -> {
                    FileDataSource fileDataSource = new FileDataSource(f);
                    DataHandler dataHandler = new DataHandler(fileDataSource);
                    return Attachment.of(f.getName(), dataHandler);
                })
                .orElseThrow(() -> new NullPointerException(format("File '%s' not found", file.getAbsoluteFile())));
    }

    public static Attachment of(final String pathname) {
        File file = new File(pathname);
        return Attachment.of(file);
    }

    public String getFilename() {
        return filename;
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .append("filename", filename)
                .append("contentType", Optional.ofNullable(dataHandler)
                        .map(DataHandler::getDataSource)
                        .map(DataSource::getContentType)
                        .orElse(null))
                .build();
    }
}
