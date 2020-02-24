package com.example.complaint;

public class ComplaintPojo {
    String district,category,description,latlong,imagepath;

    public ComplaintPojo(String district, String category, String description, String latlong, String imagepath) {
        this.district = district;
        this.category = category;
        this.description = description;
        this.latlong = latlong;
        this.imagepath = imagepath;
    }

    public ComplaintPojo() {
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
}
