package io.hhplus.main.integration

import com.fasterxml.jackson.databind.ObjectMapper
import io.hhplus.main.domain.lecture.LectureType
import io.hhplus.main.domain.lecture.entity.Lecture
import io.hhplus.main.domain.professor.Professor
import io.hhplus.main.domain.professor.ProfessorService
import io.hhplus.main.domain.student.Student
import io.hhplus.main.domain.student.StudentService
import io.hhplus.main.presentation.controller.student.StudentController
import io.hhplus.main.presentation.controller.student.request.StudentRequest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(StudentController::class)
class StudentApiTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var studentService: StudentService

    @MockBean
    private lateinit var professorService: ProfessorService

    @Test
    fun `학생 저장 요청 성공`() {
        val id = "KHJ"
        val name = "김형준"
        val request = StudentRequest(id, name)
        val student = Student(id, name)

        `when`(studentService.save(id, name)).thenReturn(student)

        mockMvc.post("/students/") {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(id) }
            jsonPath("$.name") { value(name) }
        }
    }

    @Test
    fun `학생 조회 요청 성공`() {
        val id = "KHJ"
        val name = "김형준"
        val student = Student(id, name)

        `when`(studentService.get(id)).thenReturn(student)

        mockMvc.get("/students/$id") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(id) }
            jsonPath("$.name") { value(name) }
        }
    }

    @Test
    fun `학생이 등록한 강의 조회 요청 성공`() {
        val student = Student("KHJ", "김형준")
        val professor = Professor("HJ", "허재")
        val lecture = Lecture("HJ", "테스트코드", LectureType.SPECIAL)

        `when`(studentService.getAllLectures(student.id!!))
            .thenReturn(listOf(lecture))
        `when`(professorService.get(professor.id!!))
            .thenReturn(professor)

        mockMvc.get("/students/${student.id}/lectures/") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$[0].name") { value(lecture.name) }
            jsonPath("$[0].type") { value(lecture.type.toString()) }
            jsonPath("$[0].professor.name") { value(professor.name) }
            jsonPath("$[0].maximumStudentCount") { value(lecture.maximumStudentCount) }
            jsonPath("$[0].registeredStudentCount") { value(lecture.registeredStudentCount) }
        }
    }

}