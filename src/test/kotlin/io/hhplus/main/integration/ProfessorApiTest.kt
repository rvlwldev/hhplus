package io.hhplus.main.integration

import com.fasterxml.jackson.databind.ObjectMapper
import io.hhplus.main.domain.lecture.LectureType
import io.hhplus.main.domain.lecture.entity.Lecture
import io.hhplus.main.domain.professor.Professor
import io.hhplus.main.domain.professor.ProfessorService
import io.hhplus.main.presentation.controller.professor.ProfessorController
import io.hhplus.main.presentation.controller.professor.request.ProfessorRequest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(ProfessorController::class)
class ProfessorApiTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var professorService: ProfessorService

    @Test
    fun `교수 저장 요청 성공`() {
        val id = "HJ"
        val name = "허재"
        val request = ProfessorRequest(id, name)
        val professor = Professor(id, name)

        `when`(professorService.save(id, name)).thenReturn(professor)

        mockMvc.post("/professors/") {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            jsonPath("$.name") { value(name) }
        }
    }

    @Test
    fun `교수 조회 요청 성공`() {
        val id = "HJ"
        val name = "허재"
        val professor = Professor(id, name)

        `when`(professorService.get(id)).thenReturn(professor)

        mockMvc.get("/professors/$id") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.name") { value(name) }
        }
    }

    @Test
    fun `교수가 가르치는 강의 목록 요청 성공`() {
        val id = "HJ"
        val name = "허재"
        val professor = Professor(id, name)
        val lectureName = "테스트코드"
        val lecture = Lecture(id, lectureName, LectureType.SPECIAL)

        `when`(professorService.get(id))
            .thenReturn(professor)
        `when`(professorService.getAllLectures(id, LectureType.SPECIAL))
            .thenReturn(listOf(lecture))

        mockMvc.get("/professors/$id/lectures/SPECIAL") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$[0].name") { value(lectureName) }
            jsonPath("$[0].type") { value(LectureType.SPECIAL.toString()) }
            jsonPath("$[0].professor.name") { value(name) }
        }

    }
}