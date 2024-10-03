package io.hhplus.main.infrastructure.repository.student

import io.hhplus.main.domain.student.Student
import io.hhplus.main.domain.student.StudentRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface StudentJpaRepository : JpaRepository<Student, String>

@Repository
class StudentRepositoryImpl(private val studentJpa: StudentJpaRepository) : StudentRepository {

    override fun findById(id: String): Student? =
        studentJpa.findById(id).orElse(null)

    override fun save(student: Student): Student =
        studentJpa.save(student)

}

