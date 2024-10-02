package io.hhplus.main.domain.student

import io.hhplus.main.common.exception.BizException
import io.hhplus.main.domain.lecture.LectureType
import org.springframework.stereotype.Service

@Service
class StudentService(private val repository: StudentRepository) {

    fun get(studentId: String) = repository.findById(studentId)
        ?: throw BizException("존재하지 않는 학생입니다.")

    fun save(id: String, name: String): Student {
        val student = repository.findById(id)
        if (student != null) throw BizException("이미 존재하는 ID 입니다.")

        return repository.save(Student(id, name))
    }

    // TODO : 강의 구현 후 구현
    fun getLectures(studentId: String) {}

    // TODO : 강의 구현 후 구현
    fun getLectures(studentId: String, lectureType: LectureType) {}

}