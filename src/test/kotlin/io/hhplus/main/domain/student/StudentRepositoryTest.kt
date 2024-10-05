package io.hhplus.main.domain.student

import io.hhplus.main.infrastructure.repository.student.StudentJpaRepository
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
class StudentRepositoryTest {
    @Autowired
    private lateinit var studentJpaRepository: StudentJpaRepository

    private lateinit var sut: StudentRepositoryImpl

    @BeforeEach
    fun setUp() {
        sut = StudentRepositoryImpl(studentJpaRepository)
    }

    @Test
    fun `학생 저장 및 조회 성공`() {
        val student = Student(id = "KHJ", name = "김형준")
        sut.save(student)

        val target = sut.findById("KHJ")
        assertEquals(student.name, target?.name)
    }

}
