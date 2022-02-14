package com.l33t_c0d3r_66.model;

public class Sales {
    private String region;
    private String country;
    private String itemType;
    private int unitSold;
    private double unitCost;
    private double unitPrice;


    public Sales() {
    }

    public Sales(String region, String country, String itemType, int unitSold, double unitCost, double unitPrice) {
        this.region = region;
        this.country = country;
        this.itemType = itemType;
        this.unitSold = unitSold;
        this.unitCost = unitCost;
        this.unitPrice = unitPrice;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getUnitSold() {
        return unitSold;
    }

    public void setUnitSold(int unitSold) {
        this.unitSold = unitSold;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Sales{" +
                "region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", itemType='" + itemType + '\'' +
                ", unitSold=" + unitSold +
                ", unitCost=" + unitCost +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
