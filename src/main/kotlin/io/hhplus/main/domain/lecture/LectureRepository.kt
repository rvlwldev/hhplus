package io.hhplus.main.domain.lecture

import io.hhplus.main.domain.lecture.entity.Lecture
import io.hhplus.main.domain.lecture.entity.LectureRegistration
import io.hhplus.main.domain.student.Student

interface LectureRepository {

    fun findById(id: Long): Lecture?
    fun findByIdWithLock(id: Long): Lecture?
    fun findAll(): List<Lecture>

    fun findAllByProfessorId(professorId: String): List<Lecture>
    fun findAllByProfessorIdAndType(professorId: String, type: LectureType): List<Lecture>

    fun findAllByStudentId(studentId: String): List<Lecture>

    fun save(lecture: Lecture): Lecture

    fun findRegistration(lecture: Lecture, student: Student): LectureRegistration?
    fun saveRegistration(registration: LectureRegistration): LectureRegistration

}