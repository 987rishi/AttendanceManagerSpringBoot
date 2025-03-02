import { useNavigate, useParams } from "react-router-dom"
import { studentData,teacherData } from "../store/atoms/UserDetails"
import axios from "axios"
import { useState,useEffect } from "react"
import AttendanceStudent from "./AttendanceStudent"
import {useRecoilValue} from 'recoil'

export default function MarkAttendance(){
    const studentDetails=useRecoilValue(studentData)||{}
    const teacherDetails=useRecoilValue(teacherData)||{}
    const navigate=useNavigate()
    const [message,setMessage]=useState("")
    const [students,setStudents]=useState([])
    const todayDate=new Date().toLocaleDateString()
    if(Object.keys(studentDetails).length>0)
        navigate('/logout')

    
    const params=useParams()
    const courseId=parseInt(params.courseId)

    const onChangeHandler=(studentId,isChecked)=>{
        setStudents((prevStudents)=>(
            prevStudents.map((s)=>
                s.rollNumber===studentId?{...s,present:isChecked}:s
            )
           )
        )
    }

    const onSubmitHandler=async(e)=>{
        e.preventDefault()
        const absentees=students.filter(student=>!student.present)
        console.log("Absentees")
        console.log(absentees)
        const absenteesIds=absentees.map((student)=>(student.rollNumber))

        const payload={
            'teacherId':teacherDetails.teacherId,
            'courseId':courseId,
            'absenteesIds':absenteesIds
        }

        try {
          const response=await axios.post('http://localhost:8080/teacher/attendance/absentees',payload,{
            headers:{
                'Content-Type':'application/json'
            },
            withCredentials:true
          })

          console.log("response of absentees")  
          console.log(response)

        } catch (error) {
            console.error(error)   
        }
    }

    useEffect(()=>{
        const fetchStudents=async()=>{
            setMessage("LOADING....")

            console.log("INSIDE USE EFFECT")
            console.log(teacherDetails.teacherId)
            console.log(courseId)

            try {
             const response =await axios.get(`http://localhost:8080/teacher/teacher/${teacherDetails.teacherId}/teaching/${courseId}`,{
                    headers:{
                        'Content-Type':'application/json'
                    },
                    withCredentials:true
                })
            console.log("MARK ATTENDANCE ")
            console.log(response.data)

            setMessage("SUCCESSFULL")

            const fetchedDataWithPresent=response.data.map((item)=>({
                ...item,
                present:false
            }))

            console.log("AFTER UPDATING RESPONSE DATA")
            console.log(fetchedDataWithPresent)

            setStudents(fetchedDataWithPresent)

            } catch (error) {
                console.error(error)
                setMessage("ERROR OCCURED")
                
            }
        }
        fetchStudents()
    },[])

    return(
        <div>
            <form onSubmit={onSubmitHandler}>
            <table>
            
                <thead>
                    <tr>
                        <th>Roll Number</th>
                        <th>Student Name</th>
                        <th>Student Email</th>
                        <th>Semester</th>
                        <th>Present {todayDate}</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        Object.keys(students).length>0?students.map((s)=>(
                            <AttendanceStudent student={s} updater={onChangeHandler} present={s.present}/>
                        )):<tr>
                            <td colSpan={4}>NO STUDENTS FOUND</td>
                        </tr>
                    }
                </tbody>
                
            
            </table>
            <button type="submit">Mark Attendance</button>
            </form>
           
            {message&&<p>{message}</p>}
        </div>
    )
}