package io.hhplus.main.presentation.controller.professor.request

import jakarta.validation.constraints.NotNull

data class ProfessorRequest(
    @field:NotNull(message = "아이디가 누락되었습니다.")
    val id: String,

    @field:NotNull(message = "교수명이 누락되었습니다.")
    val name: String
)