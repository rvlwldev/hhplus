package io.hhplus.main.infrastructure.repository.professor

import io.hhplus.main.domain.professor.Professor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfessorJpaRepository : JpaRepository<Professor, String> {
}