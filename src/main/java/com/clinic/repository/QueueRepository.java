package com.clinic.repository;

import com.clinic.model.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface QueueRepository extends JpaRepository<QueueEntity, Integer> {

    QueueEntity findTopByDoctorIdAndQueueDateOrderByTokenNumberAsc(
        Integer doctorId,
        LocalDate date
);

List<QueueEntity> findByDoctorIdAndQueueDateOrderByTokenNumberAsc(
        Integer doctorId,
        LocalDate date
);
}