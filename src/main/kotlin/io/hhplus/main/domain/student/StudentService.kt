package io.hhplus.main.domain.student

import io.hhplus.main.common.exception.BizException
import io.hhplus.main.domain.lecture.LectureRepository
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository,
    private val lectureRepository: LectureRepository
) {

    fun get(studentId: String) = studentRepository.findById(studentId)
        ?: throw BizException("존재하지 않는 학생입니다.")

    fun save(studentId: String, name: String): Student {
        val student = studentRepository.findById(studentId)
        if (student != null) throw BizException("이미 존재하는 ID 입니다.")

        return studentRepository.save(Student(studentId, name))
    }

    fun getAllLectures(studentId: String) =
        lectureRepository.findAllByStudentId(studentId)

}