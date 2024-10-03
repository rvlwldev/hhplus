package io.hhplus.main.presentation.controller.student.request

import jakarta.validation.constraints.NotNull

data class StudentRequest(
    @field:NotNull(message = "아이디가 누락되었습니다.")
    val id: String,

    @field:NotNull(message = "학생명이 누락되었습니다.")
    val name: String
)