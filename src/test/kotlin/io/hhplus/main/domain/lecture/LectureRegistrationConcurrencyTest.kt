package io.hhplus.main.domain.lecture

import io.hhplus.main.domain.lecture.entity.Lecture
import io.hhplus.main.domain.professor.Professor
import io.hhplus.main.domain.professor.ProfessorService
import io.hhplus.main.domain.student.StudentService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CompletableFuture

@SpringBootTest
class LectureRegistrationConcurrencyTest {

    @Autowired
    private lateinit var studentService: StudentService

    @Autowired
    private lateinit var professorService: ProfessorService

    @Autowired
    private lateinit var lectureService: LectureService


    private lateinit var mainProfessor: Professor
    private lateinit var mainLecture: Lecture

    @BeforeEach
    fun setUp() {
        mainProfessor = professorService.save("HJ", "허재")
        mainLecture = lectureService.save("HJ", "클린코드", LectureType.SPECIAL)

        (1..40).map { studentService.save(it.toString(), it.toString()) }
    }

    @Test
    fun `40명중 30명만 강의 신청이 성공`() {
        val lectureId = mainLecture.id

        val futures = (1..40).map {
            CompletableFuture.runAsync {
                val studentId = it.toString()
                lectureService.register(lectureId, studentId)
            }
        }

        CompletableFuture.allOf(*futures.toTypedArray()).thenRun {
            var count = 0
            (1..40).forEach {
                val result = studentService.getAllLectures(it.toString())
                if (result.contains(mainLecture)) count++
            }

            assertEquals(30, count, "모든 학생의 해당 강의가 신청된 갯수가 최대 갯수와 같은지")

            val lecture = lectureService.get(lectureId)
            assertEquals(lecture.registeredStudentCount, lecture.registeredStudentCount)
        }
    }

    @Test
    fun `5번 같은 강의 신청해도 1번만 성공`() {
        val lectureId = mainLecture.id

        var saveCount = 0
        var failCount = 0

        val futures = (1..5).map {
            val i = it.toString()

            CompletableFuture.runAsync {
                try {
                    lectureService.register(lectureId, i)
                    saveCount++
                } catch (e: Exception) {
                    failCount++
                }
            }
        }

        CompletableFuture.allOf(*futures.toTypedArray()).thenRun {
            assertEquals(saveCount, 1) // 5번 중 단 한번만 성공
            assertEquals(failCount, 4) // 5번 중 4번은 실패
        }
    }
}