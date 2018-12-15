###URL: http://courseservice-env.wspr2d4a47.us-west-1.elasticbeanstalk.com/webapi
###Github: https://github.com/Funguser/CSYE6225/tree/master/courseservice2

warning: incremental update not supported, return null if input params are invalid, require manually create id, id must be unique

```
1. Professor Service
/professors GET
@optional param: department

/professors POST jsonParams
{
        "professorId": "12312",
        "firstName": "Tommmmm",
        "lastName": "Tommmmmmm",
        "department": "InfoSystems"
}

/professors/professorId GET
eg: professors/1323232

/professors/professorId PUT jsonParams
{
        "firstName": "Tommmmm",
        "lastName": "Tommmmmmm",
        "department": "InfoSystems"
}

/professors/professorId DELETE
eg: professors/1323232


2. Student Service
/students GET
@optional param: department

/students POST jsonParams
do not support directly add course while creating student
    {
        "studentId": "1323",
        "firstName": "A",
        "lastName": "AA"
    }
    
/students/studentId GET
eg: students/1323

/students/studentId PUT jsonParams
    {
        "firstName": "A",
        "lastName": "AA",
        "courseList": []
    }
    
/students/studentId DELETE

3. Course Service

/courses GET

/courses POST jsonParams
do not support directly add student while creating courses
    {
        "courseId": "23232",
        "professorId": "13",
        "TAId": "123",
        "department": "asdsadas",
        "courseName": "fadsfa"
    }
    
/courses/courseId GET
eg: /courses/23232

/courses/courseId PUT jsonParams
do not support directly add student while updating courses

    {
        "professorId": "13",
        "TAId": "123",
        "department": "asdsadas",
        "boardId": "2345",
        "courseName": "fadsfa"
    }
    
/courses/courseId DELETE
eg: /courses/23232

4.Board Service

/boards GET

/boards/boardId GET

/boards POST
{
    "boardId": "12323123",
    "courseId": "123asdas"
}
/boards/boardId PUT
{
    "boardId": "12323123",
    "courseId": "123asdas"
}
/boards/boardId DELETE

5.Announcement Service

/announcements  GET
@required param: boardId

/announcements/{boardId}_{announcementId} GET

/announcements POST
{
        "boardId": "12323123",
        "announcementId": "awefasd",
        "announcementText": "acdi3r"
}

/announcements/announcmentId PUT
{
        "boardId": "12323123",
        "announcementId": "awefasd",
        "announcementText": "acdi3r"
}

/announcements/{boardId}_{announcementId} DELETE