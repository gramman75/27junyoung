package com.example.demo.model;

public class Facility {
    private String id; 
    private String name;
    private String location;
    private String available;
    private String accessibility;
    private String fee;
    private String info;
    private String coordinate;

    
    public Facility(Object id, Object name, Object location, Object available, Object accessibility, Object fee, Object info,
            Object coordinate) {
        this.id   = (String)id;
        this.name = (String)name;
        this.location = (String)location;
        this.available = (String)available;
        this.accessibility = (String)accessibility;
        this.fee = (String)fee;
        this.info = (String)info;
        this.coordinate = (String)coordinate;
    }
    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }
    public String getLocation() {
        return location;
    }
    public String getAvailable() {
        return available;
    }
    public String getAccessibility() {
        return accessibility;
    }
    public String getFee() {
        return fee;
    }
    public String getInfo() {
        return info;
    }
    public String getCoordinate() {
        return coordinate;
    }

    public void setId(Object id){
        this.id = (String)id;
    }
    public void setName(Object name) {
        this.name = (String)name;
    }
    public void setLocation(Object location) {
        this.location = (String)location;
    }
    public void setAvailable(Object available) {
        this.available = (String)available;
    }
    public void setAccessibility(Object accessibility) {
        this.accessibility = (String)accessibility;
    }
    public void setFee(Object fee) {
        this.fee = (String)fee;
    }
    public void setInfo(Object info) {
        this.info = (String)info;
    }
    public void setCoordinate(Object coordinate) {
        this.coordinate = (String)coordinate;
    }
    

}
