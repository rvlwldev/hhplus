package io.hhplus.main.infrastructure.repository.lecture

import io.hhplus.main.domain.lecture.entity.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LectureJpaRepository : JpaRepository<Lecture, String> {
}