package com.project.calendar.repository;

import com.project.calendar.entity.ExpositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("expositionRepository")
public interface ExpositionRepository extends JpaRepository<ExpositionEntity, Long> {
    Optional<ExpositionEntity> findByTitle(String title);

    @Query("SELECT e FROM ExpositionEntity e WHERE e.endDate > CURRENT_DATE")
    List<ExpositionEntity> findAllWhereEndDateGreaterThanNow();

    List<ExpositionEntity> findByHallId(Long id);
}
