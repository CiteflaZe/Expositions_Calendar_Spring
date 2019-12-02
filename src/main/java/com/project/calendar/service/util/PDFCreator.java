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
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
        final List<String> keys = new ArrayList<>(paragraphNameToParagraph.keySet());

        Chunk separator = new Chunk(":", fontNameToFont.get("separatorFont"));

        for (Ticket ticket : tickets) {
            try {
                document.add(paragraphNameToParagraph.get("Title"));
            } catch (DocumentException e) {
                log.warn("Unable to write into document", e);
                throw new PDFCreationException("Unable to write into document", e);
            }
            fill(fontNameToFont, paragraphNameToParagraph.get(keys.get(2)), keys.get(2), ticket.getId().toString(), separator, document);
            fill(fontNameToFont, paragraphNameToParagraph.get(keys.get(3)), keys.get(3), ticket.getValidDate().toString(), separator, document);
            fill(fontNameToFont, paragraphNameToParagraph.get(keys.get(4)), keys.get(4), ticket.getExposition().getTitle(), separator, document);
            fill(fontNameToFont, paragraphNameToParagraph.get(keys.get(1)), keys.get(1), ticket.getExposition().getHall().getName(), separator, document);
            document.newPage();
        }

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

        paragraphs.put("Title", title);
        paragraphs.put("Ticket Id", ticketId);
        paragraphs.put("Date", date);
        paragraphs.put("Exposition", exposition);
        paragraphs.put("Hall", hall);

        final Set<String> keys = paragraphs.keySet();
        for (String key : keys) {
            paragraphs.get(key).setSpacingAfter(8);
        }

        return paragraphs;
    }

    private void fill(Map<String, Font> fonts, Paragraph paragraph, String content, String stringText, Chunk separator, Document document){
        Chunk chunk = new Chunk(content, fonts.get("paragraphFont")).setUnderline(1f, -2f);
        Chunk text = new Chunk(" " + stringText, fonts.get("chunkFont"));
        paragraph.add(chunk);
        paragraph.add(separator);
        paragraph.add(text);

        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            log.warn("Unable to write into document", e);
            throw new PDFCreationException("Unable to write into document", e);
        }

        paragraph.clear();
    }

}
