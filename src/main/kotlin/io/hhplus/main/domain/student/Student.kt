package io.hhplus.main.domain.student

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Student(
    @Id
    val id: String? = null,

    @Column(nullable = false)
    val name: String
)