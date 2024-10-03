package io.hhplus.main.presentation.controller.student

import io.hhplus.main.domain.professor.ProfessorService
import io.hhplus.main.domain.student.StudentService
import io.hhplus.main.presentation.controller.lecture.response.LectureResponse
import io.hhplus.main.presentation.controller.student.request.StudentRequest
import io.hhplus.main.presentation.controller.student.response.StudentResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/students")
class StudentController(
    private val studentService: StudentService,
    private val professorService: ProfessorService
) {

    @PostMapping("/")
    fun save(@Valid @RequestBody body: StudentRequest): StudentResponse =
        StudentResponse(studentService.save(body.id, body.name))


    @GetMapping("/{id}")
    fun get(@PathVariable id: String): StudentResponse =
        StudentResponse(studentService.get(id))


    @GetMapping("/{id}/lectures/")
    fun getLectures(@PathVariable("id") studentId: String): List<LectureResponse> =
        studentService.getAllLectures(studentId)
            .map { LectureResponse(it, professorService.get(it.professorId)) }

}