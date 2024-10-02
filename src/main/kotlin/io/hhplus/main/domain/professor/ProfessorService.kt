package io.hhplus.main.domain.professor

import io.hhplus.main.common.exception.BizException
import io.hhplus.main.domain.lecture.LectureType
import org.springframework.stereotype.Service

@Service
class ProfessorService(private val repository: ProfessorRepository) {

    fun get(professorId: String) = repository.findById(professorId)
        ?: throw BizException("존재하지 않는 교수입니다.")

    fun save(id: String, name: String): Professor {
        val professor = repository.findById(id)
        if (professor != null) throw BizException("이미 존재하는 ID 입니다.")

        return repository.save(Professor(id, name))
    }

    // TODO : 강의 구현 후 구현
    fun getLectures(studentId: String) {}

    // TODO : 강의 구현 후 구현
    fun getLectures(studentId: String, lectureType: LectureType) {}

}