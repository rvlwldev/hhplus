package io.hhplus.main.domain.professor

import io.hhplus.main.common.exception.BizException
import io.hhplus.main.domain.lecture.LectureRepository
import io.hhplus.main.domain.lecture.LectureType
import org.springframework.stereotype.Service

@Service
class ProfessorService(
    private val professorRepository: ProfessorRepository,
    private val lectureRepository: LectureRepository
) {

    fun get(professorId: String) = professorRepository.findById(professorId)
        ?: throw BizException("존재하지 않는 교수입니다.")

    fun save(professorId: String, name: String): Professor {
        val professor = professorRepository.findById(professorId)
        if (professor != null) throw BizException("이미 존재하는 ID 입니다.")

        return professorRepository.save(Professor(professorId, name))
    }

    fun getAllLectures(professorId: String) =
        lectureRepository.findAllByProfessorId(professorId)

    fun getAllLectures(professorId: String, type: LectureType) =
        lectureRepository.findAllByProfessorIdAndType(professorId, type)

}