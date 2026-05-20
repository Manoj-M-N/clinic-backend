package com.clinic.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pharmacy_order_items")
public class PharmacyOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer itemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private PharmacyOrder pharmacyOrder;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    // ================= GETTERS & SETTERS =================

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public PharmacyOrder getPharmacyOrder() {
        return pharmacyOrder;
    }

    public void setPharmacyOrder(
            PharmacyOrder pharmacyOrder
    ) {
        this.pharmacyOrder = pharmacyOrder;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(
            Medicine medicine
    ) {
        this.medicine = medicine;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(
            Integer quantity
    ) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(
            Double unitPrice
    ) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(
            Double totalPrice
    ) {
        this.totalPrice = totalPrice;
    }
}