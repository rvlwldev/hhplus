package io.hhplus.main.presentation.controller.lecture.response

import io.hhplus.main.domain.lecture.entity.Lecture
import io.hhplus.main.domain.professor.Professor
import io.hhplus.main.presentation.controller.professor.response.ProfessorResponse

data class RegistrationResponse(
    val name: String,
    val type: String,
    val professor: ProfessorResponse,
    val createMillis: Long
) {
    constructor(lecture: Lecture, professor: Professor, createMillis: Long) : this(
        name = lecture.name,
        type = lecture.type.toString(),
        professor = ProfessorResponse(professor),
        createMillis = createMillis
    )

}