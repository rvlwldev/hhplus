package io.hhplus.main.infrastructure.repository.student

import io.hhplus.main.domain.student.Student
import io.hhplus.main.domain.student.StudentRepository
import io.hhplus.main.infrastructure.jpa.StudentJpaRepository
import org.springframework.stereotype.Repository


@Repository
class StudentRepositoryImpl(private val jpa: StudentJpaRepository) : StudentRepository {

    override fun findById(id: String): Student? = jpa.findById(id).orElse(null)
    
    override fun save(student: Student): Student = jpa.save(student)

}

