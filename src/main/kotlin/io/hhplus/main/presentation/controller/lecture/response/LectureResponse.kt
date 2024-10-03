package io.hhplus.main.presentation.controller.lecture.response

import io.hhplus.main.domain.lecture.entity.Lecture
import io.hhplus.main.domain.professor.Professor
import io.hhplus.main.presentation.controller.professor.response.ProfessorResponse

data class LectureResponse(
    val name: String,
    val type: String,
    val professor: ProfessorResponse,
    val maximumStudentCount: Int,
    val registeredStudentCount: Int
) {
    constructor(
        lecture: Lecture,
        professor: Professor
    ) : this(
        name = lecture.name,
        type = lecture.type.toString(),
        professor = ProfessorResponse(professor),
        maximumStudentCount = lecture.maximumStudentCount,
        registeredStudentCount = lecture.registeredStudentCount
    ) {

    }
}