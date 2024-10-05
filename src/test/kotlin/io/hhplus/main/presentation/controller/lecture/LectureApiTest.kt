package io.hhplus.main.presentation.controller.lecture

import com.fasterxml.jackson.databind.ObjectMapper
import io.hhplus.main.domain.lecture.LectureService
import io.hhplus.main.domain.lecture.LectureType
import io.hhplus.main.domain.lecture.entity.Lecture
import io.hhplus.main.domain.lecture.entity.LectureRegistration
import io.hhplus.main.domain.professor.Professor
import io.hhplus.main.domain.professor.ProfessorService
import io.hhplus.main.domain.student.Student
import io.hhplus.main.presentation.controller.lecture.request.LectureRequest
import io.hhplus.main.presentation.controller.lecture.request.RegistrationRequest
import io.hhplus.main.presentation.controller.lecture.response.RegistrationResponse
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post

@WebMvcTest(LectureController::class)
class LectureApiTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var professorService: ProfessorService

    @MockBean
    private lateinit var lectureService: LectureService

    @Test
    fun `강의 저장 요청 성공`() {
        val name = "테스트코드"
        val type = LectureType.SPECIAL

        val professorId = "HJ"
        val professorName = "허재"

        val professor = Professor(professorId, professorName)
        val lecture = Lecture(professorId, name, type)
        val request = LectureRequest(professorId, name, type.toString())

        `when`(lectureService.save(professorId, name, type))
            .thenReturn(lecture)
        `when`(professorService.get(professorId))
            .thenReturn(professor)

        mockMvc.post("/lectures/") {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            jsonPath("$.name") { value(name) }
            jsonPath("$.professor.name") { value(professorName) }
        }
    }

    @Test
    fun `강의 조회 요청 성공`() {
        val id = 1L
        val name = "테스트코드"
        val type = LectureType.SPECIAL

        val professorId = "HJ"
        val professorName = "허재"
        val professor = Professor(professorId, professorName)

        val lecture = Lecture(id, professorId, name, type, 30, 0)

        `when`(lectureService.get(id))
            .thenReturn(lecture)
        `when`(professorService.get(professorId))
            .thenReturn(professor)

        mockMvc.get("/lectures/$id") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(id) }
            jsonPath("$.name") { value(name) }
            jsonPath("$.type") { value(type.toString()) }
            jsonPath("$.professor.name") { value(professorName) }
        }
    }

    @Test
    fun `모든 강의 조회 요청 성공`() {
        val id = 1L
        val name = "테스트코드"
        val type = LectureType.SPECIAL

        val professorId = "HJ"
        val professorName = "허재"

        val professor = Professor(professorId, professorName)
        val lecture = Lecture(id, professorId, name, type, 30, 0)

        `when`(lectureService.getAll()).thenReturn(listOf(lecture))
        `when`(professorService.get(professorId)).thenReturn(professor)

        mockMvc.get("/lectures") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$[0].id") { value(id) }
            jsonPath("$[0].name") { value(name) }
            jsonPath("$[0].type") { value(type.toString()) }
            jsonPath("$[0].professor.name") { value(professorName) }
        }
    }

    @Test
    fun `학생 강의 등록 요청 성공`() {
        val id = 1L
        val name = "테스트코드"
        val type = LectureType.SPECIAL

        val studentId = "KHJ"
        val studentName = "김형준"

        val professorId = "HJ"
        val professorName = "허재"

        val lecture = Lecture(id, professorId, name, type, 30, 0)
        val professor = Professor(professorId, professorName)
        val student = Student(studentId, studentName)
        val registration = LectureRegistration(lecture, student)

        val time = System.currentTimeMillis()
        val request = RegistrationRequest(id, studentId)
        val response = RegistrationResponse(lecture, professor, time)

        `when`(lectureService.register(id, studentId))
            .thenReturn(registration)
        `when`(lectureService.get(id))
            .thenReturn(lecture)
        `when`(professorService.get(professorId))
            .thenReturn(professor)


        mockMvc.patch("/lectures/") {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            jsonPath("$.name") { value(name) }
            jsonPath("$.type") { value(type.toString()) }
            jsonPath("$.professor.name") { value(professorName) }
        }
    }
}