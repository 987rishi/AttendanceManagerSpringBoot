import { registeredCourses, studentData, teacherData } from "../store/atoms/UserDetails"
import { isAuthenticated } from "../store/atoms/AuthAtom"
import {useRecoilValueLoadable,useRecoilValue, useRecoilState} from 'recoil'
import { useEffect, useState } from "react"
import axios from "axios"
import Course from "./Course"
import { useParams } from "react-router-dom"
export default function RegisteredCourses(){
        //USING REACT DYNAMIC ROUTES

    const {user}=useParams()

    // const userData=useRecoilValueLoadable((user==='student')?studentData:teacherData)
    const authenticated=useRecoilValue(isAuthenticated)
    const coursesLoadable=useRecoilValueLoadable(registeredCourses)

    const [message,setMessage]=useState("")
    
        if(!authenticated)
            return <h2>USER NOT AUTHENTICATED</h2>
        if(coursesLoadable.state==='loading')
            return <h2>LOADING....</h2>        

        if(coursesLoadable.state==='hasError')
            return <h2>ERROR OCCURED</h2>
        //     const response=async ()=>{
        //         try {
        //             setMessage("LOADING....")
        //             const res= await axios.post('http://localhost:8080/course/courseByIds',userDetails.listOfCourses,{
        //                 headers:{
        //                     'Content-Type':'application/json'

        //                 },
        //                 withCredentials:true

        //             })
        //             setMessage("SUCCESSFULL..")
        //             console.log("COURSES-->"+res.data.toString())
        //             setCourses(res.data)


                    
        //         } catch (error) {
        //             console.error(error)
        //             setMessage(error.message.toString())

        //         }
            
        //     }
        //     response()
        // },[])
      
        const courses=coursesLoadable.contents
        console.log(courses)

    return (
        <div>
            <h2>{(user==='student')?'RegisteredCourses':'CoursesTeaching'}</h2>
            <table>
                <thead>   
                    <tr>
                        <th>Course ID</th>
                        <th>Course Name</th>
                        <th>Semester Offered In</th>
                        {(user==="student")?<th>Registered?</th>:''}
                        {(user==="teacher")?<th>Mark Attendance</th>:''}
                    </tr>
                </thead>
                <tbody>
                    {(courses.length>0)?courses.map((c)=><Course course={c} key={c.courseId} registered={true} user={user}/>):<tr>
                        <td colSpan={4}>NO COURSES FOUND</td>
                    </tr>}
                </tbody>
            </table>
        </div>
    )
    }