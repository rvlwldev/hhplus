package io.hhplus.main.domain.lecture.entity

import io.hhplus.main.common.exception.BizException
import io.hhplus.main.domain.lecture.LectureType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Lecture(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val professorId: String,

    val name: String,

    val type: LectureType,

    var maximumStudentCount: Int,

    var registeredStudentCount: Int
) {
    constructor() : this(
        id = 0,
        professorId = "",
        name = "",
        type = LectureType.NORMAL,
        maximumStudentCount = 30,
        registeredStudentCount = 0
    )

    constructor(professorId: String, name: String, type: LectureType) : this(
        professorId = professorId,
        name = name,
        type = type,
        maximumStudentCount = 30,
        registeredStudentCount = 0
    )

    fun incrementRegisteredStudentCount() {
        if (registeredStudentCount < maximumStudentCount)
            registeredStudentCount++
        else throw BizException("등록할 수 있는 최대 학생 수를 초과했습니다.")
    }
}