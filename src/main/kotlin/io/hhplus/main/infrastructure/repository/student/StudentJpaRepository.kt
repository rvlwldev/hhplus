package io.hhplus.main.infrastructure.repository.student

import io.hhplus.main.domain.student.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentJpaRepository : JpaRepository<Student, String>