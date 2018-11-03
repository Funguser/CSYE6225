There are four parts in this program
URL: CourseServiceQzy-env.c7uazb7xgp.us-west-1.elasticbeanstalk.com
1. Professor Service
/professors GET
/professors POST jsonParams
{
        "firstName": "Tommmmm",
        "lastName": "Tommmmmmm",
        "department": "InfoSystems"
}
/professors/professorId GET
/professors/professorId PUT jsonParams
{
        "firstName": "Tommmmm",
        "lastName": "Tommmmmmm",
        "department": "InfoSystems"
}
/professors/professorId DELETE

2. Student Service
/students GET
/students POST jsonParams
    {
        "firstName": "A",
        "lastName": "AA"
    }
/students/studentId GET
/students/studentId PUT jsonParams
    {
        "firstName": "A",
        "lastName": "AA"
    }
/students/studentId DELETE

//not necessary for this assignment
/studetns/studentId/courses GET
/studetns/studentId/courses PUT QueryParam
/studetns/studentId/courses DELETE QueryParam

3. Program Service
//programId is the name of program eg. /webapi/programs/ComputerScience
/programs GET
/programs POST jsonParams
    {
        "name": "IS"
    }
/programs/programId GET
/programs/programId PUT jsonParams
    {
        "name": "IS"
    }
/programs/programId DELETE

4. Course Service
//programId is the name of program eg. /webapi/ComputerScience/courses
/programId/courses GET
/programId/courses POST jsonParams
    {
        "courseName": "1234"
    }
/programId/courses/courseId GET
/programId/courses/courseId PUT jsonParams
    {
        "professorId": 1,
        "TAId": 1,
        "StudentId" : [1,2]
    }
/programId/courses/courseId DELETE
/programId/courses/courseId/lecture GET
/programId/courses/courseId/lecture/lectureId GET
