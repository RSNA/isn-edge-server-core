/* Copyright (c) <2016>, <Radiological Society of North America>
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the <RSNA> nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */
package org.rsna.isn.util;

import com.itextpdf.text.Document;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.rsna.isn.domain.Exam;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.rsna.isn.dao.ConfigurationDao;

/**
 *
 * @author Clifton Li
 * @version 5.0.0
 */
public class Reports {
    
        private static final String STYLESHEET = "report-stylesheet.xslt";
        public static String DEST;
        public static String TEMPLATE;

    static
    {
        try
        {
                ConfigurationDao configDao = new ConfigurationDao();
                TEMPLATE = Environment.getConfDir().getAbsolutePath() + "/pdf-template.pdf";
                DEST = Environment.getRootDir().getAbsolutePath() + "/report-preview.pdf";            
        }
        catch (Exception ex)
        {
                throw new ExceptionInInitializerError(ex);
        }
    }        
        public static byte[] generate(Exam exam) throws Exception
        {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                FopFactory fopFactory = FopFactory.newInstance();
                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF,baos);

                TransformerFactory factory = TransformerFactory.newInstance();

                File confDir = Environment.getConfDir();
                File xsltFile = new File(confDir, STYLESHEET);

                Source src = null;
                if (xsltFile.exists())
                {
                      src = new StreamSource(xsltFile);
                }

                Transformer transformer = factory.newTransformer(src);

                Source xml = new StreamSource(new StringReader(FormatText.addReportTag(exam.getReport())));

                // Resulting SAX events (the generated FO) must be piped through to FOP
                Result result = new SAXResult(fop.getDefaultHandler());                            

                // Start XSLT transformation and FOP processing
                transformer.transform(xml, result);
                
                return baos.toByteArray();
        }
        
        public static byte[] useTemplate(Exam exam) throws IOException, DocumentException, SQLException 
        {
                TemplateHelper template = new TemplateHelper(TEMPLATE);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String examDate = sdf.format(new Date());

                template.setAcc(exam.getAccNum());
                template.setExamDate(examDate);
                template.setExamDesc(exam.getExamDescription());
                template.setMrn(exam.getMrn());
                template.setName(exam.getPatientName());
                template.setExamStatus(exam.getStatus());

                Paragraph report = new Paragraph();       
                report.add(exam.getReport());  

                Document document = new Document(template.getPageSize(),
                template.getmLeft(), template.getmRight(), template.getmTop(), template.getmBottom());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter writer = PdfWriter.getInstance(document, baos);
                writer.setPageEvent(template);

                document.open();

                document.add(report);

                document.close();

                return baos.toByteArray();
        }

        public static void preview() throws IOException, DocumentException
        {        
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String today = sdf.format(new Date());

                TemplateHelper template = new TemplateHelper(TEMPLATE);
                template.setAcc("0123456789");
                template.setExamDate(today);
                template.setExamDesc("XRAY MR CT PET");
                template.setMrn("9876543210");
                template.setName("TEST^PATIENT^A");
                template.setExamStatus("FINALIZED");

                Paragraph report = new Paragraph();

                Lorem lorem = LoremIpsum.getInstance();
                String dummyText = lorem.getParagraphs(5, 8) + "\n\n" + lorem.getParagraphs(5, 8);     
                report.add(dummyText);

                Document document = new Document(template.getPageSize(),
                    template.getmLeft(), template.getmRight(), template.getmTop(), template.getmBottom());

                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));

                writer.setPageEvent(template);

                document.open();

                document.add(report);

                document.close();    
        }        
    }
