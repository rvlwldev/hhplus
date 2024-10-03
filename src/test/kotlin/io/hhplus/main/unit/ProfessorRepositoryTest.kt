package io.hhplus.main.unit

import io.hhplus.main.domain.professor.Professor
import io.hhplus.main.infrastructure.repository.professor.ProfessorJpaRepository
import io.hhplus.main.infrastructure.repository.professor.ProfessorRepositoryImpl
import io.hhplus.main.infrastructure.repository.student.StudentRepositoryImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@ExtendWith(MockitoExtension::class)
class ProfessorRepositoryTest {
    @Autowired
    private lateinit var professorJpaRepository: ProfessorJpaRepository

    private lateinit var sut: ProfessorRepositoryImpl

    @BeforeEach
    fun setUp() {
        sut = ProfessorRepositoryImpl(professorJpaRepository)
    }

    @Test
    fun `교수 저장 및 조회 성공`() {
        val professor = Professor(id = "HHW", name = "하헌우")
        sut.save(professor)

        val target = sut.findById("HHW")
        assertEquals(professor.name, target?.name)
    }

}
