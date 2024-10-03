package io.hhplus.main.presentation.controller.professor

import io.hhplus.main.domain.lecture.LectureType
import io.hhplus.main.domain.professor.ProfessorService
import io.hhplus.main.presentation.controller.lecture.response.LectureResponse
import io.hhplus.main.presentation.controller.professor.request.ProfessorRequest
import io.hhplus.main.presentation.controller.professor.response.ProfessorResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/professors")
class ProfessorController(private val professorService: ProfessorService) {

    @PostMapping("/")
    fun save(@Valid @RequestBody body: ProfessorRequest): ProfessorResponse =
        ProfessorResponse(professorService.save(body.id, body.name))

    @GetMapping("/{id}")
    fun get(@PathVariable id: String) =
        ProfessorResponse(professorService.get(id))

    @GetMapping("/{id}/lectures")
    fun getLectures(@PathVariable id: String): List<LectureResponse> {
        val professor = professorService.get(id)
        return professorService.getAllLectures(id).map {
            LectureResponse(it, professor)
        }
    }

    @GetMapping("/{id}/lectures/{type}")
    fun getLectures(
        @PathVariable("id") id: String,
        @PathVariable("type") type: String
    ): List<LectureResponse> {
        val professor = professorService.get(id)
        return professorService.getAllLectures(id, LectureType.valueOf(type)).map {
            LectureResponse(it, professor)
        }
    }

}