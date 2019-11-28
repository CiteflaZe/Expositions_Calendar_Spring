package com.project.calendar.repository;

import com.project.calendar.entity.ExpositionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository("expositionRepository")
public interface ExpositionRepository extends JpaRepository<ExpositionEntity, Long> {

    Optional<ExpositionEntity> findByTitle(String title);

    @Query("SELECT e FROM ExpositionEntity e WHERE e.endDate > CURRENT_DATE ")
    Page<ExpositionEntity> findAllWhereEndDateGreaterThanNow(Pageable var);

    long countByEndDateGreaterThan(LocalDate now);

}
