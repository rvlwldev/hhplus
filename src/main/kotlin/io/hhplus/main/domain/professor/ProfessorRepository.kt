package io.hhplus.main.domain.professor

interface ProfessorRepository {

    fun findById(id: String): Professor?

    fun save(professor: Professor): Professor

}