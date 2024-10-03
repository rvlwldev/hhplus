package io.hhplus.main.infrastructure.repository.lecture

import io.hhplus.main.domain.lecture.LectureRepository
import io.hhplus.main.domain.lecture.LectureType
import io.hhplus.main.domain.lecture.entity.Lecture
import io.hhplus.main.domain.lecture.entity.LectureRegistration
import io.hhplus.main.domain.student.Student
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

interface LectureJpaRepository : JpaRepository<Lecture, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select lecture from Lecture lecture where lecture.id = :id")
    fun findByIdWithLock(id: Long): Lecture?
    fun findAllByProfessorId(professorId: String): List<Lecture>
    fun findAllByProfessorIdAndType(professorId: String, type: LectureType): List<Lecture>
}

interface LectureRegistrationJpaRepository : JpaRepository<LectureRegistration, Long> {
    fun findByLectureIdAndStudentId(lectureId: Long, studentId: String): LectureRegistration?
    fun findAllByStudentId(studentId: String): List<LectureRegistration>
}

@Repository
class LectureRepositoryImpl(
    private val lectureJpa: LectureJpaRepository,
    private val registrationJpa: LectureRegistrationJpaRepository
) : LectureRepository {

    override fun findById(id: Long): Lecture? =
        lectureJpa.findById(id).orElse(null)

    override fun findByIdWithLock(id: Long): Lecture? =
        lectureJpa.findByIdWithLock(id)

    override fun findAll(): List<Lecture> =
        lectureJpa.findAll()

    override fun findAllByProfessorId(professorId: String): List<Lecture> =
        lectureJpa.findAllByProfessorId(professorId)

    override fun findAllByProfessorIdAndType(professorId: String, type: LectureType): List<Lecture> =
        lectureJpa.findAllByProfessorIdAndType(professorId, type)

    override fun findAllByStudentId(studentId: String): List<Lecture> {
        val ids = registrationJpa.findAllByStudentId(studentId).map { it.lectureId }
        return lectureJpa.findAllById(ids)
    }

    override fun save(lecture: Lecture): Lecture = lectureJpa.save(lecture)

    override fun findRegistration(lecture: Lecture, student: Student): LectureRegistration? =
        registrationJpa.findByLectureIdAndStudentId(lecture.id, student.id!!)

    override fun saveRegistration(registration: LectureRegistration): LectureRegistration =
        registrationJpa.save(registration)

}