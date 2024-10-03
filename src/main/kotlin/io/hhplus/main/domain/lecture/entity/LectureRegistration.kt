package io.hhplus.main.domain.lecture.entity

import io.hhplus.main.domain.student.Student
import jakarta.persistence.*

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["lectureId", "studentId"])
    ]
)
class LectureRegistration(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val lectureId: Long = 0,
    val studentId: String = "",
    val createMillis: Long = System.currentTimeMillis()
) {
    constructor(lecture: Lecture, student: Student) : this(
        lectureId = lecture.id,
        studentId = student.id!!
    )

}