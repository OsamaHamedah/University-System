package com.example.universitysystem.offeredclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferedClassRepository extends JpaRepository<OfferedClass, Long> {
    boolean existsByCourseIdAndSemesterAndSection(Long courseId, String semester, String section);
}