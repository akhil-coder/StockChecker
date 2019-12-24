package com.appface.akhil.stockchecker.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Stock_table")
public class Stock {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String brand;

    private String productDetails;

    private long barcode;

    private int stockQty;

    private int priority;

    public Stock(String brand, String productDetails, long barcode, int stockQty, int priority) {
        this.brand = brand;
        this.productDetails = productDetails;
        this.barcode = barcode;
        this.stockQty = stockQty;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public long getBarcode() {
        return barcode;
    }

    public int getStockQty() {
        return stockQty;
    }

    public int getPriority() {
        return priority;
    }
}
