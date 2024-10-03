package io.hhplus.main.infrastructure.repository.professor

import io.hhplus.main.domain.professor.Professor
import io.hhplus.main.domain.professor.ProfessorRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface ProfessorJpaRepository : JpaRepository<Professor, String>

@Repository
class ProfessorRepositoryImpl(private val professorJpa: ProfessorJpaRepository) : ProfessorRepository {

    override fun findById(id: String): Professor? =
        professorJpa.findById(id).orElse(null)

    override fun save(professor: Professor): Professor = professorJpa.save(professor)

}