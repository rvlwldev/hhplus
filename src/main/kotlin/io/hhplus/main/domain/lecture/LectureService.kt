package io.hhplus.main.domain.lecture

import io.hhplus.main.common.exception.BizException
import io.hhplus.main.domain.lecture.entity.Lecture
import io.hhplus.main.domain.lecture.entity.LectureRegistration
import io.hhplus.main.domain.professor.ProfessorRepository
import io.hhplus.main.domain.student.StudentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    @Transactional
    fun register(lectureId: Long, studentId: String): LectureRegistration {
        val lecture = lectureRepository.findByIdWithLock(lectureId)
            ?: throw BizException("존재하지 않는 강의입니다.")
        val student = studentRepository.findById(studentId)
            ?: throw BizException("존재하지 않는 학생입니다.")

        val history = lectureRepository.findRegistration(lecture, student)
        if (history != null) throw BizException("이미 신청된 강의입니다.")

        lecture.incrementRegisteredStudentCount()

        val registration = LectureRegistration(lectureRepository.save(lecture), student)
        return lectureRepository.saveRegistration(registration)
    }

}