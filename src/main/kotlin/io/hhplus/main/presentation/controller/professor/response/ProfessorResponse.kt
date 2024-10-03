package io.hhplus.main.presentation.controller.professor.response

import io.hhplus.main.domain.professor.Professor

class ProfessorResponse(
    val name: String
) {
    constructor(professor: Professor) : this(name = professor.name)
}