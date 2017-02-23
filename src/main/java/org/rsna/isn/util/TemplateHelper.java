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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rsna.isn.domain.Job;
 

/**
 *
 * @author Clifton Li
 * @version 5.0.0
 */

public class TemplateHelper extends PdfPageEventHelper {
    // initialized in constructor
    protected PdfReader reader;
    protected Rectangle pageSize;
    protected Rectangle bodyField = null;
    protected Rectangle nameField = null;
    protected Rectangle mrnField = null;
    protected Rectangle accField = null;
    protected Rectangle statusField = null;
    protected Rectangle dateField = null;
    protected Rectangle descField = null;
    
    protected float mLeft, mRight, mTop, mBottom;

    protected Rectangle footer;
    protected BaseFont basefont;
    protected Font font;
    // initialized with setter
    
    protected String name = "";
    protected String mrn = "";
    protected String acc = "";
    protected String examDate = "";
    protected String examDesc = "";
    protected String examStatus = "";
    
    // initialized upon opening the document
    protected PdfTemplate background;

    Job job;
    
    public TemplateHelper(String stationery) throws IOException, DocumentException 
    {
            try
            {
                    reader = new PdfReader(stationery);
                    AcroFields fields = reader.getAcroFields();

                    pageSize = reader.getPageSize(1);

                    Iterator<Entry<String,Item>> iterator = fields.getFields().entrySet().iterator();

                    while (iterator.hasNext())
                    {
                            Map.Entry<String,Item> entry = (Map.Entry<String,Item>) iterator.next();

                            if (entry.getKey().equals("body"))
                                    bodyField = fields.getFieldPositions("body").get(0).position;

                            else if (entry.getKey().equals("name"))
                                    nameField = fields.getFieldPositions("name").get(0).position;

                            else if (entry.getKey().equals("mrn"))
                                    mrnField = fields.getFieldPositions("mrn").get(0).position;

                            else if (entry.getKey().equals("accession"))
                                    accField = fields.getFieldPositions("accession").get(0).position;            

                            else if (entry.getKey().equals("exam-status"))
                                    statusField = fields.getFieldPositions("exam-status").get(0).position;    

                            else if (entry.getKey().equals("exam-date"))
                                    dateField = fields.getFieldPositions("exam-date").get(0).position;   

                            else if (entry.getKey().equals("exam-description"))
                                    descField = fields.getFieldPositions("exam-description").get(0).position;   

                            else
                                    System.out.println(entry.getKey() + " field found in the PDF is not supported.");           
                    }

                    mLeft = bodyField.getLeft() - pageSize.getLeft();
                    mRight = pageSize.getRight() - bodyField.getRight();
                    mTop = pageSize.getTop() - bodyField.getTop();
                    mBottom = bodyField.getBottom() - pageSize.getBottom();
                    basefont = BaseFont.createFont();
                    font = new Font(basefont, 10);
            }
            catch (NullPointerException e)
            {

            }
    }
 
    public Rectangle getPageSize() 
    {
            return pageSize;
    }
 
    public float getmLeft() 
    {
            return mLeft;
    }
 
    public float getmRight() 
    {
            return mRight;
    }
 
    public float getmTop() 
    {
            return mTop;
    }
 
    public float getmBottom() 
    {
            return mBottom;
    }
 
    public Rectangle getBody() 
    {
            return bodyField;
    }

    public void setName(String name) 
    {
            this.name = name;
    }

    public void setMrn(String mrn) 
    {
            this.mrn = mrn;
    }    

    public void setAcc(String acc) 
    {
            this.acc = acc;
    }    

    public void setExamDate(String examDate) 
    {
            this.examDate = examDate;
    }    

    public void setExamDesc(String examDesc) 
    {
            this.examDesc = examDesc;
    }        

    public void setExamStatus(String examStatus) 
    {
            this.examStatus = examStatus;
    }        
    
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) 
    {
            background = writer.getImportedPage(reader, 1);
    }
    
    @Override
    public void onEndPage(PdfWriter writer, Document document) 
    {
            try 
            {
                    PdfContentByte canvas = writer.getDirectContentUnder();
                    // background
                    canvas.addTemplate(background, 0, 0);

                    ColumnText ct = new ColumnText(canvas);

                    if (nameField != null)
                    {
                            ct.setSimpleColumn(nameField);
                            ct.addElement(new Paragraph("NAME: " + name, font));
                            ct.go();
                    }
                    if (mrnField != null)
                    {
                            ct.setSimpleColumn(mrnField);
                            ct.addElement(new Paragraph("MRN: " + mrn, font));
                            ct.go();
                    }

                    if (accField != null)
                    {
                            ct.setSimpleColumn(accField);
                            ct.addElement(new Paragraph("ACC: " + acc, font));
                            ct.go();
                    }

                    if (statusField != null)
                    {
                            ct.setSimpleColumn(statusField);
                            ct.addElement(new Paragraph("STATUS: " + examStatus, font));
                            ct.go();
                    }

                    if (dateField != null)
                    {
                            ct.setSimpleColumn(dateField);
                            ct.addElement(new Paragraph("DATE: " + examDate, font));
                            ct.go();
                    }

                    if (descField != null)
                    {
                            ct.setSimpleColumn(descField);
                            ct.addElement(new Paragraph("EXAM: " + examDesc, font));
                            ct.go();
                    }
            } 
            catch (DocumentException ex) 
            {
                    Logger.getLogger(TemplateHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
 
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) 
    {
        String s = "/" + (writer.getPageNumber() - 1);
        Phrase p = new Phrase(12, s, font);
    }
}