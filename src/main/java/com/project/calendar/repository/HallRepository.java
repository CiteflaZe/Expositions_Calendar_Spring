package com.project.calendar.repository;

import com.project.calendar.entity.HallEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("hallRepository")
public interface HallRepository extends JpaRepository<HallEntity, Long> {
    List<HallEntity> findByCity(String city);

    Optional<HallEntity> findByName(String name);
}
