package io.hhplus.main.domain.lecture

import io.hhplus.main.domain.lecture.entity.Lecture
import io.hhplus.main.domain.lecture.entity.LectureRegistration
import io.hhplus.main.domain.professor.Professor
import io.hhplus.main.domain.student.Student
import io.hhplus.main.infrastructure.repository.lecture.LectureJpaRepository
import io.hhplus.main.infrastructure.repository.lecture.LectureRegistrationJpaRepository
import io.hhplus.main.infrastructure.repository.lecture.LectureRepositoryImpl
import io.hhplus.main.infrastructure.repository.professor.ProfessorJpaRepository
import io.hhplus.main.infrastructure.repository.student.StudentJpaRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@ExtendWith(MockitoExtension::class)
class LectureRepositoryTest {

    @Autowired
    private lateinit var studentJpaRepository: StudentJpaRepository

    @Autowired
    private lateinit var professorJpaRepository: ProfessorJpaRepository

    @Autowired
    private lateinit var lectureJpaRepository: LectureJpaRepository

    @Autowired
    private lateinit var registrationJpaRepository: LectureRegistrationJpaRepository

    private lateinit var sut: LectureRepositoryImpl

    private lateinit var testStudent: Student
    private lateinit var testProfessor: Professor

    private lateinit var testLecture1: Lecture
    private lateinit var testLecture2: Lecture

    @BeforeEach
    fun setUp() {
        sut = LectureRepositoryImpl(lectureJpaRepository, registrationJpaRepository)

        testStudent = studentJpaRepository.save(Student("KHJ", "김형준"))
        testProfessor = professorJpaRepository.save(Professor("HJ", "허재"))

        testLecture1 = lectureJpaRepository.save(Lecture("HJ", "아키텍쳐", LectureType.SPECIAL))
        testLecture2 = lectureJpaRepository.save(Lecture("HJ", "동시성처리", LectureType.SPECIAL))

        lectureJpaRepository.save(Lecture("HJ", "테스트코드", LectureType.NORMAL))
    }

    @Test
    fun `강의 저장 및 조회 성공`() {
        val lecture = Lecture("HJ", "허재의 강의", LectureType.SPECIAL)
        val id = sut.save(lecture).id

        val target = sut.findById(id)
        assertEquals(lecture.name, target?.name)
    }

    @Test
    fun `교수 아이디로 모든 강의 불러오기 성공`() {
        val lectures = sut.findAllByProfessorId("HJ")
        assertEquals(2, lectures.size)

        val names = lectures.map { it.name }
        assertTrue(names.contains("테스트코드"))
        assertTrue(names.contains("아키텍쳐"))
    }

    @Test
    fun `교수 아이디와 강의 유형으로 모든 강의 불러오기 성공`() {
        val normalLecture = sut.findAllByProfessorIdAndType("HJ", LectureType.NORMAL)
        assertEquals(1, normalLecture.size)
        assertTrue(normalLecture.map { it.name }.contains("테스트코드"))

        val specialLecture = sut.findAllByProfessorIdAndType("HJ", LectureType.SPECIAL)
        assertEquals(1, specialLecture.size)
        assertTrue(specialLecture.map { it.name }.contains("아키텍쳐"))
    }

    @Test
    fun `수강신청 저장 및 조회 성공`() {
        val myRegistration = LectureRegistration(testLecture1, testStudent)
        sut.saveRegistration(myRegistration)

        val target = sut.findRegistration(testLecture1, testStudent)

        assertEquals(myRegistration.lectureId, target?.lectureId)
        assertEquals(myRegistration.studentId, target?.studentId)
    }

    @Test
    fun `수강신청한 모든 강의 불러오기 성공`() {
        sut.saveRegistration(LectureRegistration(testLecture1, testStudent))
        sut.saveRegistration(LectureRegistration(testLecture2, testStudent))

        val target = sut.findAllByStudentId(testStudent.id!!)

        assertTrue(target.map { it.name }.contains(testLecture1.name))
        assertTrue(target.map { it.name }.contains(testLecture2.name))
    }


}