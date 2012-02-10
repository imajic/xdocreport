/**
 * Copyright (C) 2011 Angelo Zerr <angelo.zerr@gmail.com> and Pascal Leclercq <pascal.leclercq@gmail.com>
 *
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fr.opensagres.xdocreport.document.docx.preprocessor;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import junit.framework.TestCase;

public class DocxPreprocessorStartStopRowTestCase
    extends TestCase
{

    private static final String FDLSIMPLE_START_STOP_XML =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<w:document "
            + "xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" "
            + "xmlns:o=\"urn:schemas-microsoft-com:office:office\" "
            + "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" "
            + "xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" "
            + "xmlns:v=\"urn:schemas-microsoft-com:vml\" "
            + "xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
            + "xmlns:w10=\"urn:schemas-microsoft-com:office:word\" "
            + "xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" "
            + "xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\">" + "<w:tbl>" + "<w:tblPr>"
            + "<w:tblStyle w:val=\"Grilledutableau\"/>" + "<w:tblW w:w=\"0\" w:type=\"auto\"/>"
            + "<w:tblLook w:val=\"04A0\"/>" + "</w:tblPr>" + "<w:tblGrid>" + "<w:gridCol w:w=\"9212\"/>"
            + "</w:tblGrid>" + "<w:tr w:rsidR=\"00AA0F2A\" w:rsidTr=\"00AA0F2A\">" + "<w:tc>" + "<w:tcPr>"
            + "<w:tcW w:w=\"9212\" w:type=\"dxa\"/>" + "</w:tcPr>"
            + "<w:p w:rsidR=\"00AA0F2A\" w:rsidRDefault=\"00AA0F2A\" w:rsidP=\"00AA0F2A\">"
            + "<w:fldSimple w:instr=\" MERGEFIELD  @before-rowAAAAAAAAAA  \\* MERGEFORMAT \">" + "<w:r>" + "<w:rPr>"
            + "<w:noProof/>" + "</w:rPr>" + "<w:t>«@before-rowAAAAAAAAAA»</w:t>" + "</w:r>" + "</w:fldSimple>"
            + "<w:fldSimple w:instr=\" MERGEFIELD  @after-rowZZZZZZZZZZ  \\* MERGEFORMAT \">" + "<w:r>" + "<w:rPr>"
            + "<w:noProof/>" + "</w:rPr>" + "<w:t>«@after-rowZZZZZZZZZZ»</w:t>" + "</w:r>" + "</w:fldSimple>"
            + "</w:p>" + "</w:tc>" + "</w:tr>" + "</w:tbl>" + "</w:document>";

    private static final String INSTRTEXT_START_STOP_XML =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + "<w:document "
            + "xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" "
            + "xmlns:o=\"urn:schemas-microsoft-com:office:office\" "
            + "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" "
            + "xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" "
            + "xmlns:v=\"urn:schemas-microsoft-com:vml\" "
            + "xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
            + "xmlns:w10=\"urn:schemas-microsoft-com:office:word\" "
            + "xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" "
            + "xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\">"
            + "<w:tbl>"
            + "<w:tblPr>"
            + "<w:tblStyle w:val=\"Grilledutableau\"/>"
            + "<w:tblW w:w=\"0\" w:type=\"auto\"/>"
            + "<w:tblLook w:val=\"04A0\"/>"
            + "</w:tblPr>"
            + "<w:tblGrid>"
            + "<w:gridCol w:w=\"9212\"/>"
            + "</w:tblGrid>"
            + "<w:tr w:rsidR=\"00AA0F2A\" w:rsidTr=\"00AA0F2A\">"
            + "<w:tc>"
            + "<w:tcPr>"
            + "<w:tcW w:w=\"9212\" w:type=\"dxa\"/>"
            + "</w:tcPr>"
            + "<w:p w:rsidR=\"002856EF\" w:rsidRDefault=\"0053228F\" w:rsidP=\"002856EF\">"
            + "<w:r>"
            + "<w:fldChar w:fldCharType=\"begin\" />"
            + "</w:r>"
            + "<w:r>"
            + "<w:instrText xml:space=\"preserve\"> MERGEFIELD  \"@before-row[#list developers as d]\"  \\* MERGEFORMAT </w:instrText>"
            + "</w:r>" + "<w:r>" + "<w:fldChar w:fldCharType=\"separate\" />" + "</w:r>" + "<w:r w:rsidR=\"002856EF\">"
            + "<w:rPr>" + "<w:noProof />" + "</w:rPr>" + "<w:t>«@before-row[#list developers as d]»</w:t>" + "</w:r>"
            + "<w:r>" + "<w:rPr>" + "<w:noProof />" + "</w:rPr>" + "<w:fldChar w:fldCharType=\"end\" />" + "</w:r>"
            + "<w:r>" + "<w:fldChar w:fldCharType=\"begin\" />" + "</w:r>" + "<w:r>"
            + "<w:instrText xml:space=\"preserve\"> MERGEFIELD  ${d.name}  \\* MERGEFORMAT </w:instrText>" + "</w:r>"
            + "<w:r>" + "<w:fldChar w:fldCharType=\"separate\" />" + "</w:r>" + "<w:r w:rsidR=\"002856EF\">"
            + "<w:rPr>" + "<w:noProof/>" + "</w:rPr>" + "<w:t>«${d.name}»</w:t>" + "</w:r>" + "<w:r>" + "<w:rPr>"
            + "<w:noProof/>" + "</w:rPr>" + "<w:fldChar w:fldCharType=\"end\" />" + "</w:r>" + "<w:r>"
            + "<w:fldChar w:fldCharType=\"begin\" />" + "</w:r>" + "<w:r>"
            + "<w:instrText xml:space=\"preserve\"> MERGEFIELD  @after-row[/#list]  \\* MERGEFORMAT </w:instrText>"
            + "</w:r>" + "<w:r>" + "<w:fldChar w:fldCharType=\"separate\" />" + "</w:r>" + "<w:r w:rsidR=\"002856EF\">"
            + "<w:rPr>" + "<w:noProof/>" + "</w:rPr>" + "<w:t>«@after-row[/#list]»</w:t>" + "</w:r>" + "<w:r>"
            + "<w:rPr>" + "<w:noProof/>" + "</w:rPr>" + "<w:fldChar w:fldCharType=\"end\" />" + "</w:r>" + "</w:p>"
            + "</w:tc>" + "</w:tr>" + "</w:tbl>" + "</w:document>";

    public void testFdlSimpleStartTopRow()
        throws Exception
    {
        DocxPreprocessor preprocessor = new DocxPreprocessor();
        StringReader reader = new StringReader( FDLSIMPLE_START_STOP_XML );

        StringWriter writer = new StringWriter();

        preprocessor.preprocess( "test", reader, writer, null, null, new HashMap<String, Object>() );

        assertEquals( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<w:document "
            + "xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" "
            + "xmlns:o=\"urn:schemas-microsoft-com:office:office\" "
            + "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" "
            + "xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" "
            + "xmlns:v=\"urn:schemas-microsoft-com:vml\" "
            + "xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
            + "xmlns:w10=\"urn:schemas-microsoft-com:office:word\" "
            + "xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" "
            + "xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\">" + "<w:tbl>" + "<w:tblPr>"
            + "<w:tblStyle w:val=\"Grilledutableau\"/>" + "<w:tblW w:w=\"0\" w:type=\"auto\"/>"
            + "<w:tblLook w:val=\"04A0\"/>" + "</w:tblPr>" + "<w:tblGrid>" + "<w:gridCol w:w=\"9212\"/>"
            + "</w:tblGrid>"

            + "AAAAAAAAAA"

            + "<w:tr w:rsidR=\"00AA0F2A\" w:rsidTr=\"00AA0F2A\">" + "<w:tc>" + "<w:tcPr>"
            + "<w:tcW w:w=\"9212\" w:type=\"dxa\"/>" + "</w:tcPr>"
            + "<w:p w:rsidR=\"00AA0F2A\" w:rsidRDefault=\"00AA0F2A\" w:rsidP=\"00AA0F2A\">"
            // + "<w:fldSimple w:instr=\" MERGEFIELD @start-rowAAAAAAAAAA \* MERGEFORMAT \">"
            // + "<w:r>"
            // + "<w:rPr>"
            // + "<w:noProof/>""
            // + "</w:rPr>"
            // + "<w:t>«@start-rowAAAAAAAAAA»</w:t>"
            // + "</w:r>"
            // + "</w:fldSimple>"
            // + "<w:fldSimple w:instr=\" MERGEFIELD @end-rowZZZZZZZZZZ \* MERGEFORMAT \">"
            // + "<w:r>"
            // + "<w:rPr>"
            // + "<w:noProof/>""
            // + "</w:rPr>"
            // + "<w:t>«@end-rowZZZZZZZZZZ»</w:t>"
            // + "</w:r>"
            // + "</w:fldSimple>"
            + "</w:p>" + "</w:tc>" + "</w:tr>"

            + "ZZZZZZZZZZ"

            + "</w:tbl>" + "</w:document>", writer.toString() );
    }

    public void testInstrTextStartTopRow()
        throws Exception
    {
        DocxPreprocessor preprocessor = new DocxPreprocessor();
        StringReader reader = new StringReader( INSTRTEXT_START_STOP_XML );

        StringWriter writer = new StringWriter();

        preprocessor.preprocess( "test", reader, writer, null, null, new HashMap<String, Object>() );

        assertEquals( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<w:document "
            + "xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" "
            + "xmlns:o=\"urn:schemas-microsoft-com:office:office\" "
            + "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" "
            + "xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" "
            + "xmlns:v=\"urn:schemas-microsoft-com:vml\" "
            + "xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
            + "xmlns:w10=\"urn:schemas-microsoft-com:office:word\" "
            + "xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" "
            + "xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\">" + "<w:tbl>" + "<w:tblPr>"
            + "<w:tblStyle w:val=\"Grilledutableau\"/>" + "<w:tblW w:w=\"0\" w:type=\"auto\"/>"
            + "<w:tblLook w:val=\"04A0\"/>" + "</w:tblPr>" + "<w:tblGrid>" + "<w:gridCol w:w=\"9212\"/>"
            + "</w:tblGrid>"

            + "[#list developers as d]"

            + "<w:tr w:rsidR=\"00AA0F2A\" w:rsidTr=\"00AA0F2A\">" + "<w:tc>" + "<w:tcPr>"
            + "<w:tcW w:w=\"9212\" w:type=\"dxa\"/>" + "</w:tcPr>"
            + "<w:p w:rsidR=\"002856EF\" w:rsidRDefault=\"0053228F\" w:rsidP=\"002856EF\">"
            // + "<w:r>"
            // + "<w:fldChar w:fldCharType=\"begin\" />"
            // + "</w:r>"
            // + "<w:r>"
            // +
            // "<w:instrText xml:space=\"preserve\"> MERGEFIELD  \"@before-row[#list developers as d]\"  \\* MERGEFORMAT </w:instrText>"
            // + "</w:r>"
            // + "<w:r>"
            // + "<w:fldChar w:fldCharType=\"separate\" />"
            // + "</w:r>"
            // + "<w:r w:rsidR=\"002856EF\">"
            // + "<w:rPr>"
            // + "<w:noProof/>"
            // + "</w:rPr>"
            // + "<w:t>«@before-row[#list developers as d]»</w:t>"
            // + "</w:r>"
            // + "<w:r>"
            // + "<w:rPr>"
            // + "<w:noProof/>"
            // + "</w:rPr>"
            // + "<w:fldChar w:fldCharType=\"end\" />"
            // + "</w:r>"
            // + "<w:r>"
            // + "<w:fldChar w:fldCharType=\"begin\" />"
            // + "</w:r>"
            // + "<w:r>"
            // + "<w:instrText xml:space=\"preserve\"> MERGEFIELD  ${d.name}  \\* MERGEFORMAT </w:instrText>"
            // + "</w:r>"
            // + "<w:r>"
            // + "<w:fldChar w:fldCharType=\"separate\" />"
            // + "</w:r>"
            + "<w:r w:rsidR=\"002856EF\">" + "<w:rPr>" + "<w:noProof/>" + "</w:rPr>"
            // + "<w:t>«${d.name}»</w:t>"
            + "<w:t>${d.name}</w:t>" + "</w:r>"
            // + "<w:r>"
            // + "<w:rPr>"
            // + "<w:noProof/>"
            // + "</w:rPr>"
            // + "<w:fldChar w:fldCharType=\"end\" />"
            // + "</w:r>"
            // + "<w:r>"
            // + "<w:fldChar w:fldCharType=\"begin\" />"
            // + "</w:r>"
            // + "<w:r>"
            // + "<w:instrText xml:space=\"preserve\"> MERGEFIELD  @after-row[/#list]  \\* MERGEFORMAT </w:instrText>"
            // + "</w:r>"
            // + "<w:r>"
            // + "<w:fldChar w:fldCharType=\"separate\" />"
            // + "</w:r>"
            // + "<w:r w:rsidR=\"002856EF\">"
            // + "<w:rPr>"
            // + "<w:noProof/>"
            // + "</w:rPr>"
            // + "<w:t>«@after-row[/#list]»</w:t>"
            // + "</w:r>"
            // + "<w:r>"
            // + "<w:rPr>"
            // + "<w:noProof/>"
            // + "</w:rPr>"
            // + "<w:fldChar w:fldCharType=\"end\" />"
            // + "</w:r>"
            + "</w:p>" + "</w:tc>" + "</w:tr>"

            + "[/#list]"

            + "</w:tbl>" + "</w:document>", writer.toString() );
    }

}
