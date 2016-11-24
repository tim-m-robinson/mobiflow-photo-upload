package org.tmr.rest;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by tmr
 */
public class PhotoUploadForm {

    private String caseRef;
    private String customerName;
    private String employeeName;
    private byte[] documentImage;

    public boolean hasPassedCamel = false;

    public PhotoUploadForm() {}

    public String getCaseRef() {
        return caseRef;
    }

    @FormParam("case_ref")
    @PartType(MediaType.TEXT_PLAIN)
    public void setCaseRef(String case_ref) {
        this.caseRef = case_ref;
    }

    public String getCustomerName() {
        return customerName;
    }

    @FormParam("customer_name")
    @PartType(MediaType.TEXT_PLAIN)
    public void setCustomerName(String customer_name) {
        this.customerName = customer_name;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    @FormParam("employee_name")
    @PartType(MediaType.TEXT_PLAIN)
    public void setEmployeeName(String employee_name) {
        this.employeeName = employee_name;
    }

    public byte[] getDocumentImage()
    {
        return documentImage;
    }

    @FormParam("document_image")
    @PartType("image/jpg, image/tiff")
    public void setDocumentImage(final byte[] document_image) {
        this.documentImage = document_image;
    }

    @Override
    public String toString() {
        String result = "";

        result += "case ref: "+caseRef+"\n";
        result += "customer name: "+customerName+"\n";
        result += "employee name: "+employeeName+"\n";
        result += "image size: "+documentImage.length+"\n";
        result += "via Camel?: "+hasPassedCamel;

        return result;
    }

}
