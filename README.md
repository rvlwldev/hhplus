# 1-2 TDD & Clean Architecture

## 패키지 구조

```
// 레이어 중심 도메인 설계
/src
  /presentation
    /controller
      /lecture
      /student
        /request
        /response
      /professor
      /...
    /consumer
    /...
  /application (생략 가능, 비즈니스 로직의 복잡도에 따라 유동적으로 추가)
    /facade
      SpecialLecture.kt
      /...
  /domain
    /lecture
    /student
    /professor
    /...
  /infrastructure
    /repository
      /lecture
      /student
      /professor
      /...
```

### 이유

- 순환참조 문제를 비교적 예방하기 쉽다.
- 책임을 명확하게 분리하기 쉽다.
- 자주 사용되는 공통 기능을 재사용하기 쉽다.
- 작은 사이즈의 프로젝트에 적합하다.

---

## ERD

#### 학생 (Student)

| Column | Type        | 
|--------|-------------|
| id     | String (PK) |
| name   | String      |

#### 교수(Professor)

| Column | Type        |
|--------|-------------|
| id     | String (PK) |
| name   | String      |

#### 강의 (Lecture)

| Column                 | Type                  |
|------------------------|-----------------------|
| id                     | Long (PK)             |
| professorId            | String                |
| name                   | String                |
| type                   | Enum(NORMAL, SPECIAL) |
| maximumStudentCount    | Int (Nullable)        |
| registeredStudentCount | Int (Nullable)        |

#### 수강등록 (LectureRegistration)

| Column       | Type                           |
|--------------|--------------------------------|
| id           | Long (PK)                      |
| lectureId    | String (Unique with userId)    |
| userId       | String (Unique with LectureId) |
| createMillis | Long                           |

### 설계 방향

- Primary Key 만 지정하며, ```N + 1``` 문제를 방지하기 위해 FK는 사용하지 않는다.
    - 엔티티의 관계들은 오직 비즈니스 로직에서 처리한다.
    - 불가피하게 JOIN 등이 필요하다면 JPQL 등으로 처리한다.
- ```Lecture``` 에서 ```Enum``` 을 사용하여 추후 보충강의 등의 강의 종류에 대한 확장성에 용이하도록 설계한다.
- ```LectureRegistration``` 에서 ```userId```, ```lectureId``` 에 최소한의 **고유제약조건**으로 인적오류를 방지한다.

### 사용 예시

#### 특강 조회 시

- ```type``` 이 **SPECIAL** 한 모든 ```List<Lecture>```를 조회한다.
    - 이 때, 각각의 최대 수강 인원, 현재 신청 인원, 해당 강의의 ```Professor``` 정보를 포함한다.
    - 또한 동일한 교수의 다른 강의 라면, 불필요한 데이터베이스 접근을 제한하고 해당 데이터를 재활용한다.

#### 수강 신청 시

> 최초 강의 개설 시 ```registeredStudentCount``` 컬럼은 **0**이다.

- ```LectureRegistration```에서 해당 강의가 이미 수강되어 있는지 확인한다.
    - 이미 수강 신청된 강의라면, _예외를 던진다._
- 한 학생의 수강 신청이 성공할 때마다 ```registeredStudentCount```가 증가한다.
    - 이때 증가하는 값이 ```maximumStudentCount``` 보다 크다면, _예외를 던진다._
- ```LectureRegistration``` 에 수강 신청이 성공한 학생의 ID와, 강의 아이디를 저장한다.
- 이 과정에서 Lock을 사용, 즉 동시성이 보장되어야 한다.

#### 학생의 수강 목록 조회 시

- ```LectureRegistration```에서  ```userId```로 모든 목록을 조회한다.
- 수강 목록은 ```Lecture```, ```Professor``` 정보를 포함한다.
    - 특강 조회와 마찬가지로, 불필요한 데이터베이스 접근을 제한한다.