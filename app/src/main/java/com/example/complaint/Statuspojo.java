package com.example.complaint;

class Statuspojo {
    private String complaintcategory,complaint,status,reason;

    public Statuspojo(String complaintcategory, String complaint, String status, String reason) {
        this.complaintcategory = complaintcategory;
        this.complaint = complaint;
        this.status = status;
        this.reason = reason;
    }

    public Statuspojo() {
    }

    public String getComplaintcategory() {
        return complaintcategory;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }
}
