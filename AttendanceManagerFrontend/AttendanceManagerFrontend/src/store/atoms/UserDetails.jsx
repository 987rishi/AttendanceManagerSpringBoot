// import {atom} from 'recoil'
// import { selector } from 'recoil'
// import { isAuthenticated } from './AuthAtom'
// import axios from 'axios'


// const studentData=atom({
//     'key':'studentDataAtom',
//     'default':{}
// })
// const teacherData=atom({
//     'key':'teacherDataAtom',
//     'default':{}
// })

// const registeredCourses=selector({
//     'key':'userRegisteredCourses',
//     'get':async({get})=>{
//         const authenticated=get(isAuthenticated)
        
//         const studentCourses=get(studentData)
//         const teacherCourses=get(teacherData)
//         console.log("Student Courses"+studentCourses.toString())
//         console.log("teaacher COurses"+teacherCourses.toString())

//         if(!authenticated)
//             return []

//         const payload =
//         Object.keys(studentCourses).length > 0
//           ? studentCourses.listOfCourses
//           : Object.keys(teacherCourses).length > 0
//           ? teacherCourses.listOfCourses
//           : null;
  
//       if (payload===null)
//         return []; // Return an empty array if no valid data is available

    
//         await axios.post('http://localhost:8080/course/courseByIds',payload,{
//             headers:{
//                 'Content-Type':'application/json'

//             },
//             withCredentials:true
//         }).then((res)=>{
//             console.log("COURSES-->"+res.data.toString())
//             return res.data
//         })
//         .catch((err)=>{
//             console.error("ERROR OCCURED WHILE FETCHING COURSES->"+err)
//             return []
//         })
//     }
// }
// )
// export {studentData,teacherData,registeredCourses}

import { atom, selector } from 'recoil';
import { isAuthenticated } from './AuthAtom';
import axios from 'axios';

const studentData = atom({
    key: 'studentDataAtom',
    default: {}
});

const teacherData = atom({
    key: 'teacherDataAtom',
    default: {}
});

const registeredCourses = selector({
    key: 'userRegisteredCourses',
    get: async ({ get }) => {
        const authenticated = get(isAuthenticated);
        const studentCourses = get(studentData);
        const teacherCourses = get(teacherData);

        console.log("Student Courses", studentCourses);
        console.log("Teacher Courses", teacherCourses);

        if (!authenticated) return [];

        const payload =
            Object.keys(studentCourses).length > 0
                ? studentCourses.listOfCourses
                : Object.keys(teacherCourses).length > 0
                ? teacherCourses.coursesTeachingIds
                : null;

        if (payload === null) return []; // Return an empty array if no valid data is available

        try {
            const response = await axios.post('http://localhost:8080/course/courseByIds', payload, {
                headers: {
                    'Content-Type': 'application/json'
                },
                withCredentials: true
            });
            console.log("Courses:", response.data);
            return response.data; // Return the fetched courses
        } catch (err) {
            console.error("Error occurred while fetching courses:", err);
            return []; // Return an empty array on error
        }
    }
});

const attendance=selector({
    'key':'attendanceSelector',
    'get':async ({get})=>{
        const authenticated=get(isAuthenticated)
        const studentDetails=get(studentData)||{}
        const payload={
            email:studentDetails.email
        }
        console.log("IN ATTENDANCE SELECTOR-->")
        console.log(studentDetails)
        if(!authenticated)
            return{}
       
        try {
            const res=await axios.post('http://localhost:8080/attendance/student/email',payload,{
                headers:{
                    'Content-Type':'application/json',
                },
                withCredentials:true
            })
            console.log("RESPONSE ")
            console.log(res.data.body)
            return res.data.body
            
        } catch (error) {
            console.error(error)
            return {}
        }

    }
})


export { studentData, teacherData, registeredCourses,attendance };
