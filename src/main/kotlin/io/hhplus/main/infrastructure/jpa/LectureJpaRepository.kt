package io.hhplus.main.infrastructure.jpa

import io.hhplus.main.domain.lecture.entity.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LectureJpaRepository : JpaRepository<Lecture, String> {
}