/* Copyright (c) <2010>, <Radiological Society of North America>
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
package org.rsna.isn.domain;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class is used to track the DICOM study that makes up a submission
 * set.
 *
 * @author Wyatt Tellis
 * @version 2.1.0
 *
 */
public class DicomStudy
{

    private String patientName;

    /**
     * Get the value of patientName
     *
     * @return the value of patientName
     */
    public String getPatientName()
    {
        return patientName;
    }

    /**
     * Set the value of patientName
     *
     * @param patientName new value of patientName
     */
    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    private String patientId;

    /**
     * Get the value of patientId
     *
     * @return the value of patientId
     */
    public String getPatientId()
    {
        return patientId;
    }

    /**
     * Set the value of patientId
     *
     * @param patientId new value of patientId
     */
    public void setPatientId(String patientId)
    {
        this.patientId = patientId;
    }

    private String sex;

    /**
     * Get the value of sex
     *
     * @return the value of sex
     */
    public String getSex()
    {
        return sex;
    }

    /**
     * Set the value of sex
     *
     * @param sex new value of sex
     */
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    private Date birthdate;

    /**
     * Get the value of birthdate
     *
     * @return the value of birthdate
     */
    public Date getBirthdate()
    {
        return birthdate;
    }

    /**
     * Set the value of birthdate
     *
     * @param birthdate new value of birthdate
     */
    public void setBirthdate(Date birthdate)
    {
        this.birthdate = birthdate;
    }

    private String accessionNumber;

    /**
     * Get the value of accessionNumber
     *
     * @return the value of accessionNumber
     */
    public String getAccessionNumber()
    {
        return accessionNumber;
    }

    /**
     * Set the value of accessionNumber
     *
     * @param accessionNumber new value of accessionNumber
     */
    public void setAccessionNumber(String accessionNumber)
    {
        this.accessionNumber = accessionNumber;
    }

    private Date studyDateTime;

    /**
     * Get the value of studyDateTime
     *
     * @return the value of studyDateTime
     */
    public Date getStudyDateTime()
    {
        return studyDateTime;
    }

    /**
     * Set the value of studyDateTime
     *
     * @param studyDateTime new value of studyDateTime
     */
    public void setStudyDateTime(Date studyDateTime)
    {
        this.studyDateTime = studyDateTime;
    }

    private String studyDescription;

    /**
     * Get the value of studyDescription
     *
     * @return the value of studyDescription
     */
    public String getStudyDescription()
    {
        return studyDescription;
    }

    /**
     * Set the value of studyDescription
     *
     * @param studyDescription new value of studyDescription
     */
    public void setStudyDescription(String studyDescription)
    {
        this.studyDescription = studyDescription;
    }

    private String studyUid;

    /**
     * Get the value of studyUid
     *
     * @return the value of studyUid
     */
    public String getStudyUid()
    {
        return studyUid;
    }

    /**
     * Set the value of studyUid
     *
     * @param studyUid new value of studyUid
     */
    public void setStudyUid(String studyUid)
    {
        this.studyUid = studyUid;
    }

    private String studyId;

    /**
     * Get the value of studyId
     *
     * @return the value of studyId
     */
    public String getStudyId()
    {
        return studyId;
    }

    /**
     * Set the value of studyId
     *
     * @param studyId new value of studyId
     */
    public void setStudyId(String studyId)
    {
        this.studyId = studyId;
    }

    private String referringPhysician;

    /**
     * Get the value of referringPhysician
     *
     * @return the value of referringPhysician
     */
    public String getReferringPhysician()
    {
        return referringPhysician;
    }

    /**
     * Set the value of referringPhysician
     *
     * @param referringPhysician new value of referringPhysician
     */
    public void setReferringPhysician(String referringPhysician)
    {
        this.referringPhysician = referringPhysician;
    }

    private Job job;

    /**
     * Get the value of job
     *
     * @return the value of job
     */
    public Job getJob()
    {
        return job;
    }

    /**
     * Set the value of job
     *
     * @param job new value of job
     */
    public void setJob(Job job)
    {
        this.job = job;
    }

    private DicomKos kos;

    /**
     * Get the KOS for this study
     *
     * @return A DicomKos instance or null if the kos property hasn't been set
     */
    public DicomKos getKos()
    {
        return kos;
    }

    /**
     * Set the value of kos
     *
     * @param kos new value of kos
     */
    public void setKos(DicomKos kos)
    {
        this.kos = kos;
    }

    private Map<String, DicomSeries> series = 
			new LinkedHashMap<String, DicomSeries>();

    /**
     * Get the DICOM series associated with this study.
     *
     * @return A set containing the associated series
     */
    public Map<String, DicomSeries> getSeries()
    {
        return series;
    }

}
