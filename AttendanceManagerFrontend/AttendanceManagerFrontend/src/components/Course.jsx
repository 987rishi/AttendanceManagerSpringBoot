import { useRecoilState } from "recoil"
import {Link} from 'react-router-dom'

export default function Course({course,registered,handler,user}){
   // const [check,setCheck]=useRecoilState(false)


    const onChangeHandler=(e)=>{
        // e.target.checked=!e.target.checked
        handler(course.courseId,e.target.checked)
    }

    try{
    return(
        <tr>
            <td>{course.courseId}</td>
            <td>{course.courseName}</td>
            <td>{course.semesterOfferedIn}</td>
            {
                (user==="student")&&<td>
                <input 
                    type="checkbox" 
                    // disabled={registered||false} 
                    checked={registered||false} 
                    onChange={onChangeHandler}>
                </input>
            </td>
            }
            {
                (user==="teacher")?<td><Link to={
                `/teacher/markAttendance/${course.courseId}`
                }>Mark Attendance</Link></td>:''
            }
        </tr>
    )
    }catch(error){
        console.error(error)
    }
}