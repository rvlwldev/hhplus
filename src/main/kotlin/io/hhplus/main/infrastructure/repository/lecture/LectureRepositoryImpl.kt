package io.hhplus.main.infrastructure.repository.lecture

import io.hhplus.main.domain.lecture.LectureRepository
import io.hhplus.main.domain.lecture.entity.Lecture
import io.hhplus.main.infrastructure.jpa.LectureJpaRepository

class LectureRepositoryImpl(private val jpa: LectureJpaRepository) : LectureRepository {

    override fun findById(id: String): Lecture? = jpa.findById(id).orElse(null)
    
    override fun save(lecture: Lecture): Lecture = jpa.save(lecture)

}