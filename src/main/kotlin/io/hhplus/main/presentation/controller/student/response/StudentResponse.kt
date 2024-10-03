package io.hhplus.main.presentation.controller.student.response

import io.hhplus.main.domain.student.Student

data class StudentResponse(
    val id: String,
    val name: String,
) {
    constructor(student: Student) : this(
        id = student.id!!, name = student.name
    )
}