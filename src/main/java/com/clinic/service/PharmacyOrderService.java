package com.clinic.service;

import com.clinic.model.*;
import com.clinic.repository.*;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PharmacyOrderService {

    private final PharmacyOrderRepository
            pharmacyOrderRepository;

    private final PharmacyOrderItemRepository
            itemRepository;

    private final MedicineRepository
            medicineRepository;

    public PharmacyOrderService(
            PharmacyOrderRepository pharmacyOrderRepository,
            PharmacyOrderItemRepository itemRepository,
            MedicineRepository medicineRepository
    ) {

        this.pharmacyOrderRepository =
                pharmacyOrderRepository;

        this.itemRepository =
                itemRepository;

        this.medicineRepository =
                medicineRepository;
    }

    // ================= CREATE ORDER =================
    public PharmacyOrder createOrder(

            Consultation consultation,

            List<Prescription> prescriptions

    ) {

        // ================= CREATE ORDER =================

        PharmacyOrder order =
                new PharmacyOrder();

        order.setConsultation(
                consultation
        );

        order.setAppointment(
                consultation
                        .getAppointment()
        );

        order.setPatient(
                consultation
                        .getAppointment()
                        .getPatient()
        );

        order.setStatus(
                "PENDING"
        );

        PharmacyOrder savedOrder =
                pharmacyOrderRepository
                        .save(order);

        // ================= CREATE ITEMS =================

        List<PharmacyOrderItem>
                orderItems =
                new ArrayList<>();

        for (Prescription p : prescriptions) {

            // 🔥 find medicine from inventory
            Medicine medicine =
                    medicineRepository
                            .findAll()
                            .stream()
                            .filter(m ->
                                    m.getMedicineName()
                                            .equalsIgnoreCase(
                                                    p.getMedicineName()
                                            )
                            )
                            .findFirst()
                            .orElse(null);

            // 🔥 skip if medicine not found
            if (medicine == null) {
                continue;
            }

            PharmacyOrderItem item =
                    new PharmacyOrderItem();

            item.setPharmacyOrder(
                    savedOrder
            );

            item.setMedicine(
                    medicine
            );

            // temporary quantity
            Integer qty =
                    p.getDays();

            item.setQuantity(qty);

            item.setUnitPrice(
                    medicine.getUnitPrice()
            );

            item.setTotalPrice(
                    qty *
                    medicine.getUnitPrice()
            );

            itemRepository.save(item);

            orderItems.add(item);
        }

        savedOrder.setItems(orderItems);

        return pharmacyOrderRepository
                .save(savedOrder);
    }
}