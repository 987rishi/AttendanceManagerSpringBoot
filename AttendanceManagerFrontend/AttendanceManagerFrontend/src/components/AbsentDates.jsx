import { useParams } from "react-router-dom"
import { useRecoilValue, useRecoilValueLoadable } from "recoil"
import { attendance } from "../store/atoms/UserDetails"

export default function AbsentDates(){
    const courseIdParam=useParams()
    console.log("courseIdParam")

    console.log(courseIdParam)
    const attendanceLoadable=useRecoilValueLoadable(attendance)
    const id=parseInt(courseIdParam.courId)
    console.log("id")

    console.log(id)
    if(attendanceLoadable.state==='loading')
        return <h1>LOADING....</h1>
    if(attendanceLoadable.state==='hasError')
        return <h1>ERROR OCCURED</h1>
    const attendanceSubjectList=attendanceLoadable.contents.attendanceForEnrolledCourses
    console.log(attendanceLoadable.contents)
    console.log("attendanceSubjectList")

    console.log(attendanceSubjectList)
    console.log("items")

    const subject=attendanceSubjectList.find(item=>{
        console.log(item)
        console.log(item.courseId===id)
        console.log(typeof(item.courseId))
        console.log(typeof(id))


        return item.courseId===id
    })
    console.log("subject")
    console.log(subject)


    return(
        <ul>
            {subject.absentDates.length>0?subject.absentDates.map((d)=><li>{d}</li>):<li>NO AbsentDates</li>}
        </ul>
    )
}