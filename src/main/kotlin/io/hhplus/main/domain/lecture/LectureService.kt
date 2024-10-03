package io.hhplus.main.domain.lecture

import io.hhplus.main.common.exception.BizException
import io.hhplus.main.domain.lecture.entity.Lecture
import io.hhplus.main.domain.lecture.entity.LectureRegistration
import io.hhplus.main.domain.professor.ProfessorRepository
import io.hhplus.main.domain.student.StudentRepository
import org.springframework.stereotype.Service

@Service
class LectureService(
    private val studentRepository: StudentRepository,
    private val professorRepository: ProfessorRepository,
    private val lectureRepository: LectureRepository
) {

    fun get(id: Long) = lectureRepository.findById(id)
        ?: throw BizException("존재하지 않는 강의입니다.")

    fun getAll() = lectureRepository.findAll()

    fun save(professorId: String, name: String, type: LectureType): Lecture {
        professorRepository.findById(professorId)
            ?: throw BizException("존재하지 않는 교수 입니다.")

        return lectureRepository.save(Lecture(professorId, name, type))
    }

    fun register(lectureId: Long, studentId: String): LectureRegistration {
        val lecture = this.get(lectureId)
        val student = studentRepository.findById(studentId)
            ?: throw BizException("존재하지 않는 학생입니다.")
        val registration = LectureRegistration(lecture, student)

        return lectureRepository.saveRegistration(registration)
    }

}