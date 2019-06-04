package com.example.andro.entertainmenttrip;



public class trip {
    private String name;
    private double price;
    private String palace;
    private boolean read;

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setRead(boolean read) {
      this.read=read;
    }

    public boolean  getread() {
        return read;
    }

    public String getPalace() {
        return palace;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPalace(String place) {
        this.palace = place;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
