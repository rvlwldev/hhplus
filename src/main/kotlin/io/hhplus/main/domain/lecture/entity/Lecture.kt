package io.hhplus.main.domain.lecture.entity

import io.hhplus.main.domain.lecture.LectureType
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Lecture(
    @Id
    val id: String? = null,

    val professorId: String,

    val name: String,

    val type: LectureType,

    var maximumStudentCount: Int = 30,

    var registeredStudentCount: Int = 0
) {
    constructor(id: String, professorId: String, name: String, type: LectureType) : this(
        id = id,
        professorId = professorId,
        name = name,
        type = type,
        maximumStudentCount = 30,
        registeredStudentCount = 0
    )
}