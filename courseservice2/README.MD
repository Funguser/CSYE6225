There are four parts in this program
1. Professor Service
/professors GET
/professors POST jsonParams
/professors/professorId GET
/professors/professorId PUT jsonParams
/professors/professorId DELETE

2. Student Service
/students GET
/students POST jsonParams
/students/studentId GET
/students/studentId PUT jsonParams
/students/studentId DELETE
/studetns/studentId/courses GET
/studetns/studentId/courses PUT QueryParam
/studetns/studentId/courses DELETE QueryParam

3. Program Service
/programs GET
/programs POST jsonParams
/programs/programId GET
/programs/programId PUT jsonParams
/programs/programId DELETE

4. Course Service
/programId/courses GET
/programId/courses POST jsonParams
/programId/courses/courseId GET
/programId/courses/courseId PUT jsonParams
/programId/courses/courseId DELETE
/programId/courses/courseId/lecture GET
/programId/courses/courseId/lecture/lectureId GET