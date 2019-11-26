package com.project.calendar.service.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.project.calendar.domain.Ticket;
import com.project.calendar.exception.PDFCreationException;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Log4j
public class PDFCreator {

    public String createPDF(Long paymentId, List<Ticket> tickets) {
        Document document = new Document();
        final String fileName = "ticket_" + paymentId + ".pdf";
        final File file = new File("tickets/" + fileName);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
        } catch (FileNotFoundException | DocumentException e) {
            log.warn("Such file does not exist", e);
            throw new PDFCreationException(e);
        }

        document.open();

        Map<String, Font> fontNameToFont = createFonts();
        Map<String, Paragraph> paragraphNameToParagraph = createParagraphs(fontNameToFont.get("titleFont"));

        Chunk separator = new Chunk(":", fontNameToFont.get("separatorFont"));

        fillParagraphs(tickets, fontNameToFont, paragraphNameToParagraph, separator, document);

        document.close();

        return fileName;
    }

    private Map<String, Font> createFonts() {
        Map<String, Font> fonts = new HashMap<>();

        Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 24, BaseColor.BLACK);
        Font paragraphFont = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 16, BaseColor.BLACK);
        Font separatorFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 16, BaseColor.BLACK);
        Font chunkFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);

        fonts.put("titleFont", titleFont);
        fonts.put("paragraphFont", paragraphFont);
        fonts.put("separatorFont", separatorFont);
        fonts.put("chunkFont", chunkFont);

        return fonts;
    }

    private Map<String, Paragraph> createParagraphs(Font titleFont) {
        Map<String, Paragraph> paragraphs = new HashMap<>();

        Paragraph title = new Paragraph("Ticket", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(8);

        Paragraph ticketId = new Paragraph();
        Paragraph date = new Paragraph();
        Paragraph exposition = new Paragraph();
        Paragraph hall = new Paragraph();

        paragraphs.put("title", title);
        paragraphs.put("ticketId", ticketId);
        paragraphs.put("date", date);
        paragraphs.put("exposition", exposition);
        paragraphs.put("hall", hall);

        final Set<String> keys = paragraphs.keySet();
        for (String key : keys) {
            paragraphs.get(key).setSpacingAfter(8);
        }

        return paragraphs;
    }

    private void fillParagraphs(List<Ticket> tickets, Map<String, Font> fonts, Map<String, Paragraph> paragraphs,
                                Chunk separator, Document document){
        for (Ticket ticket : tickets) {
            Chunk chunk = new Chunk("Ticket ID", fonts.get("paragraphFont")).setUnderline(1f, -2f);
            Chunk text = new Chunk(" " + ticket.getId(), fonts.get("chunkFont"));
            paragraphs.get("ticketId").add(chunk);
            paragraphs.get("ticketId").add(separator);
            paragraphs.get("ticketId").add(text);

            chunk = new Chunk("Date", fonts.get("paragraphFont")).setUnderline(1f, -2f);
            text = new Chunk(" " + ticket.getValidDate(), fonts.get("chunkFont"));
            paragraphs.get("date").add(chunk);
            paragraphs.get("date").add(separator);
            paragraphs.get("date").add(text);

            chunk = new Chunk("Expositions", fonts.get("paragraphFont")).setUnderline(1f, -2f);
            text = new Chunk(" " + ticket.getExposition().getTitle(), fonts.get("chunkFont"));
            paragraphs.get("exposition").add(chunk);
            paragraphs.get("exposition").add(separator);
            paragraphs.get("exposition").add(text);

            chunk = new Chunk("Hall", fonts.get("paragraphFont")).setUnderline(1f, -2f);
            text = new Chunk(" " + ticket.getExposition().getHall().getName(), fonts.get("chunkFont"));
            paragraphs.get("hall").add(chunk);
            paragraphs.get("hall").add(separator);
            paragraphs.get("hall").add(text);

            try {
                document.add(paragraphs.get("title"));
                document.add(paragraphs.get("ticketId"));
                document.add(paragraphs.get("date"));
                document.add(paragraphs.get("exposition"));
                document.add(paragraphs.get("hall"));
            } catch (DocumentException e) {
                log.warn("Unable to write into document", e);
                throw new PDFCreationException(e);
            }
            document.newPage();

            paragraphs.get("ticketId").clear();
            paragraphs.get("date").clear();
            paragraphs.get("exposition").clear();
            paragraphs.get("hall").clear();
        }
    }


}
