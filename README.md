# University System

Spring Boot REST API for managing a university: students, teachers, departments, courses,
offered classes, and student enrollment.

## Setup
1. Install MySQL and create a database named `university_system`.
2. In `src/main/resources/application.properties`, set `spring.datasource.password` to your local MySQL password.
3. Run `UniversitySystemApplication` (port 8080).

## Main endpoints (base path `/api/v1`)
| Area | Endpoints |
|---|---|
| Students | `/student`, `/student/{id}` |
| Teachers | `/teacher`, `/teacher/{id}` |
| Departments | `/department`, `/department/{id}` |
| Courses | `/course`, `/course/{id}` |
| Offered classes | `/offered-class`, `/offered-class/{id}` |
| Enroll a student | `POST /student/{studentId}/enrollments/{offeredClassId}` |
| Withdraw a student | `DELETE /student/{studentId}/enrollments/{offeredClassId}` |
| Student schedule | `GET /student/{studentId}/schedule[?semester=Fall 2026]` |

## Error responses
Errors return JSON: `timestamp`, `status`, `error`, `message`, `path`.
404 = resource not found, 409 = conflict (duplicate enrollment, full class), 400 = invalid request.