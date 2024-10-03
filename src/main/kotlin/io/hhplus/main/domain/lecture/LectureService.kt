package io.hhplus.main.domain.lecture

import io.hhplus.main.common.exception.BizException
import io.hhplus.main.domain.lecture.entity.Lecture
import io.hhplus.main.domain.professor.Professor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LectureService(private val repository: LectureRepository) {

    fun get(id: String) = repository.findById(id)
        ?: throw BizException("존재하지 않는 강의입니다.")

    @Transactional
    fun save(professor: Professor, id: String, name: String, type: LectureType): Lecture {
        val lecture = repository.findById(id)

        if (lecture != null) throw BizException("이미 존재하는 ID 입니다.")
        if (professor.id == null) throw BizException("존재하지 않는 교수입니다.")

        return repository.save(Lecture(id, professor.id, name, type))
    }

}