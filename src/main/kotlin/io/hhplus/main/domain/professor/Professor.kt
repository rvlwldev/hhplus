package io.hhplus.main.domain.professor

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Professor(
    @Id
    val id: String? = null,

    @Column(nullable = false)
    val name: String
)