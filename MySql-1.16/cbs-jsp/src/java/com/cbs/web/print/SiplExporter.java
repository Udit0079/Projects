/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.print;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


/**
 *
 * @author root
 */
public class SiplExporter {

    protected int pageWidth;
    protected int pageHeight;
    protected float characterHeight;
    protected float characterWidth;
    char[][] pageData;
    protected Writer writer;
    protected String lineSeparator;
    protected static final String systemLineSeparator = System.getProperty("line.separator");
    protected List pages = new ArrayList();
    private boolean lineBreak = false;
    private int newLineX = 0;

    public SiplExporter(int pw, int ph, float cw, float ch) {
        pageWidth = pw;
        pageHeight = ph;
        characterWidth = cw;
        characterHeight = ch;
    }

    public ByteArrayOutputStream exportReport() throws Exception {

        String encoding = "UTF-8";
        lineSeparator = systemLineSeparator;
        ByteArrayOutputStream os;
        try {
            os = new ByteArrayOutputStream();
            writer = new OutputStreamWriter(os, encoding);
            exportReportToWriter();
        } catch (IOException e) {
            throw new Exception("Error writing to file writer : ", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
        return os;
    }

    /**
     *
     */
    protected void exportReportToWriter() throws Exception, IOException {
        List pages = getPages();
        if (pages != null && pages.size() > 0) {
            if (characterWidth > 0) {
                pageWidth = (int) (getPageWidth() / characterWidth);
            }
            if (characterHeight > 0) {
                pageHeight = (int) (getPageHeight() / characterHeight);
            }
            SiplPage page = (SiplPage) pages.get(0);
            exportPage(page);
        }

        writer.flush();
    }

    protected void exportPage(SiplPage page) throws IOException {
        List elements = page.getElements();

        pageData = new char[pageHeight][];
        for (int i = 0; i < pageHeight; i++) {
            pageData[i] = new char[pageWidth];
            Arrays.fill(pageData[i], ' ');
        }

        exportElements(elements);

        for (int i = 0; i < pageHeight; i++) {
            writer.write(pageData[i]);
            writer.write(lineSeparator);
        }

    }

    protected void exportElements(List elements) {
        for (int i = 0; i < elements.size(); i++) {
            Object element = elements.get(i);
            if (element instanceof SiplText) {
                exportText((SiplText) element);
            }
        }
    }

    protected void exportText(SiplText element) {
        try {

            int rowCount = calculateYCoord(element.getHeight());
            int columnCount = calculateXCoord(element.getWidth());
            int x = calculateXCoord(element.getX());
            int y = calculateYCoord(element.getY());

            String allText = element.getText();

            int newLineColCount = 0;
        if(allText.length() > columnCount) newLineColCount = columnCount + x - newLineX;

            // if the space is too small, the element will not be rendered
            if (rowCount <= 0 || columnCount <= 0) {
                return;
            }

            // uses an array of string buffers, since the maximum number of rows is already calculated
            StringBuffer[] rows = new StringBuffer[rowCount];
            rows[0] = new StringBuffer();
            int rowIndex = 0;
            int rowPosition = 0;

            // first search for \n, because it causes immediate line break
            StringTokenizer lfTokenizer = new StringTokenizer(allText, "\n");
            label:
            while (lfTokenizer.hasMoreTokens()) {
                String line = lfTokenizer.nextToken();
                StringTokenizer spaceTokenizer = new StringTokenizer(line, " ", true);

                // divide each text line in words
                while (spaceTokenizer.hasMoreTokens()) {
                    String word = spaceTokenizer.nextToken();

                    //increase column count in case of line break
                 if(rowIndex > 0) columnCount = newLineColCount;

                // situation: word is larger than the entire column
                    // in this case breaking occurs in the middle of the word
                    while (word.length() > columnCount) {
                        rows[rowIndex].append(word.substring(0, columnCount - rowPosition));
                        word = word.substring(columnCount - rowPosition, word.length());
                        rowIndex++;
                        if (rowIndex == rowCount) {
                            break label;
                        }
                        rowPosition = 0;
                        rows[rowIndex] = new StringBuffer();
                    }

                    // situation: word is larger than remaining space on the current line
                    // in this case, go to the next line
                                
                    if (rowPosition + word.length() > columnCount) {
                        rowIndex++;
                        if (rowIndex == rowCount) {
                            break label;
                        }
                        rowPosition = 0;
                        rows[rowIndex] = new StringBuffer();
                        // for adding a new blank line
                    if(lineBreak){
                            rowIndex++;
                            rows[rowIndex] = new StringBuffer();
                        }
                    }

                // situation: the word is actually a space and it situated at the beginning of a new line
                    // in this case, it is removed
                    if (rowIndex > 9 && rowPosition == 0 && word.equals(" ")) {
                        break;
                    }

                // situation: the word is small enough to fit in the current line
                    // in this case just add the word and increment the cursor position
                    rows[rowIndex].append(word);
                    rowPosition += word.length();
                }


                rowIndex++;
                if (rowIndex == rowCount) {
                    break;
                }
                rowPosition = 0;
                rows[rowIndex] = new StringBuffer();
            }

            for (int i = 0; i < rowIndex; i++) {
                String line = rows[i].toString();
                int pos = line.length() - 1;
                while (pos >= 0 && line.charAt(pos) == ' ') {
                    pos--;
                }
                line = line.substring(0, pos + 1);
                char[] chars = line.toCharArray();
            if(i >0){
                if(newLineX == 0){
                    System.arraycopy(chars, 0, pageData[y + i], x , chars.length);
                }else{
                    System.arraycopy(chars, 0, pageData[y + i], newLineX , chars.length);
                    }
            }else{
                System.arraycopy(chars, 0, pageData[y + i], x , chars.length);
                }
            }
        } catch (Exception e) {
            System.out.println("Problem in Passbook printing for "+ element.getText() + " and error is = "+ e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Transforms y coordinates from pixel space to character space.
     */
    protected int calculateYCoord(int y) {

        int result = (pageHeight * y / getPageHeight());

        return result;
    }

    /**
     * Transforms x coordinates from pixel space to character space.
     */
    protected int calculateXCoord(int x) {

        int result = (pageWidth * x / getPageWidth());

        return result;

    }

    public int getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
    }

    public int getPageHeight() {
        return pageHeight;
    }

    public void setPageHeight(int pageHeight) {
        this.pageHeight = pageHeight;
    }

    public List getPages() {
        return pages;
    }

    public void setPages(List pages) {
        this.pages = pages;
    }

    public boolean isLineBreak() {
        return lineBreak;
    }

    public void setLineBreak(boolean lineBreak) {
        this.lineBreak = lineBreak;
    }

    public int getNewLineX() {
        return newLineX;
    }

    public void setNewLineX(int newLineX) {
        this.newLineX = newLineX;
    }
}
