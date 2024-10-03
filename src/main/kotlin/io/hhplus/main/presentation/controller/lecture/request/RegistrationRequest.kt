package io.hhplus.main.presentation.controller.lecture.request

import jakarta.validation.constraints.NotNull

data class RegistrationRequest(
    @field:NotNull(message = "강의 아이디가 누락되었습니다.")
    val lectureId: Long,

    @field:NotNull(message = "학생 아이디가 누락되었습니다.")
    val studentId: String
)