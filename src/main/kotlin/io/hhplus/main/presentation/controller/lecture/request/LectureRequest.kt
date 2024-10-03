package io.hhplus.main.presentation.controller.lecture.request

import jakarta.validation.constraints.NotNull

data class LectureRequest(
    @field:NotNull(message = "교수 아이디가 누락되었습니다.")
    val professorId: String,

    @field:NotNull(message = "강의명이 누락되었습니다.")
    val name: String,

    @field:NotNull(message = "강의 유형이 누락되었습니다.")
    val type: String,
)
