package io.hhplus.main.domain.student

interface StudentRepository {

    fun findById(id: String): Student?

    fun save(student: Student): Student

}