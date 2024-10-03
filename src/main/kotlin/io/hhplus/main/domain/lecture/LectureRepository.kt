package io.hhplus.main.domain.lecture

import io.hhplus.main.domain.lecture.entity.Lecture

interface LectureRepository {

    fun findById(id: String): Lecture?

    fun save(lecture: Lecture): Lecture
    
}