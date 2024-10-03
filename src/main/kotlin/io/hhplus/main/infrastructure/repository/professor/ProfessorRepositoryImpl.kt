package io.hhplus.main.infrastructure.repository.professor

import io.hhplus.main.domain.professor.Professor
import io.hhplus.main.domain.professor.ProfessorRepository
import org.springframework.stereotype.Repository

@Repository
class ProfessorRepositoryImpl(private val jpa: ProfessorJpaRepository) : ProfessorRepository {

    override fun findById(id: String): Professor? = jpa.findById(id).orElse(null)

    override fun save(professor: Professor): Professor = jpa.save(professor)

}