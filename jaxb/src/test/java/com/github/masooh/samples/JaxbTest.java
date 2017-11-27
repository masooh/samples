package com.github.masooh.samples;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;


public class JaxbTest {

    @Test
    @DisplayName("Book is marshaled and contains attributes")
    public void marshall() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Book.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        Book book = new Book();
        book.setName("Lord of the Rings");
        book.setAuthor("Tolkien");

        StringWriter writer = new StringWriter();
        marshaller.marshal(book, writer);

        String xml = writer.toString();

        Assertions.assertAll(
                () -> xml.contains("<book>"),
                () -> xml.contains("<name>"),
                () -> xml.contains("<author>")
        );
    }
}
