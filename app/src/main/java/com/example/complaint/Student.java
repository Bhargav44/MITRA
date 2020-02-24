package com.example.complaint;

class Student {
    private String id, district,category,description,latlong,imagepath,complaintdate,mobilenumber;

    public Student(String id,String district, String category, String description, String latlong, String imagepath,String complnt_Date,String mobilenumber) {
        this.id=id;
        this.district = district;
        this.category = category;
        this.description = description;
        this.latlong = latlong;
        this.imagepath = imagepath;
        this.complaintdate=complnt_Date;
        this.mobilenumber=mobilenumber;
    }

    public Student() {
    }

    public String getId() {
        return id;
    }

    public String getComplaintdate() {
        return complaintdate;
    }

    public String getDistrict() {
        return district;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getLatlong() {
        return latlong;
    }

    public String getImagepath() {
        return imagepath;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }
}
