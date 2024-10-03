package io.hhplus.main.presentation.controller.lecture

import io.hhplus.main.domain.lecture.LectureService
import io.hhplus.main.domain.lecture.LectureType
import io.hhplus.main.domain.professor.Professor
import io.hhplus.main.domain.professor.ProfessorService
import io.hhplus.main.presentation.controller.lecture.request.LectureRequest
import io.hhplus.main.presentation.controller.lecture.request.RegistrationRequest
import io.hhplus.main.presentation.controller.lecture.response.LectureResponse
import io.hhplus.main.presentation.controller.lecture.response.RegistrationResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/lectures")
class LectureController(
    private val lectureService: LectureService,
    private val professorService: ProfessorService
) {

    @PostMapping("/")
    fun save(@Valid @RequestBody body: LectureRequest): LectureResponse =
        lectureService.save(body.professorId, body.name, LectureType.valueOf(body.type)).let {
            val professor = professorService.get(body.professorId)
            LectureResponse(it, professor)
        }


    @GetMapping("/{id}")
    fun get(@PathVariable lectureId: Long): LectureResponse {
        val lecture = lectureService.get(lectureId)
        val professor = professorService.get(lecture.professorId)

        return LectureResponse(lecture, professor)
    }

    @GetMapping
    fun getAll(): List<LectureResponse> {
        val professors = HashMap<String, Professor>()
        return lectureService.getAll().map {
            val id = it.professorId

            val professor = professors.getOrDefault(id, professorService.get(id))
            professors[id] = professor

            LectureResponse(it, professor)
        }
    }

    @PatchMapping("/")
    fun register(@Valid @RequestBody body: RegistrationRequest): RegistrationResponse {
        return lectureService.register(body.lectureId, body.studentId).let {
            val lecture = lectureService.get(it.lectureId)
            val professor = professorService.get(lecture.professorId)

            RegistrationResponse(lecture, professor, it.createMillis)
        }
    }

}

