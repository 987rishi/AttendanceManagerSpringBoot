import { useRecoilValueLoadable } from 'recoil'
import {attendance} from '../store/atoms/UserDetails'
import Attendance from './Attendance'
export default function ViewAttendance(){
    const attendanceStudent=useRecoilValueLoadable(attendance)
    if(attendanceStudent.state==='loading')
        return <h1>LOADING...</h1>
    if(attendanceStudent.state==='hasError')
        return <h2>ERROR OCCURED</h2>
    const attendanceDetails=attendanceStudent.contents
    const attendanceSubjectList=attendanceDetails.attendanceForEnrolledCourses
    console.log("ATTENDANCE DETAILS-"+attendanceDetails)
    console.log("ATTENDACE"+attendanceSubjectList)
    
    return(
        <div>
            <table>
                <thead>
                    <tr>
                        <th>Course ID</th>
                        <th>Course Name</th>
                        <th>Attendance (%)</th>
                        <th>Absent Dates</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        (Object.keys(attendanceSubjectList).length>0)?attendanceSubjectList.map((a)=><Attendance attendancePerSubject={a} key={a.courseId}/>):<tr>
                            <td colSpan='4'>NO COURSES FOUND</td>
                        </tr>
                    }
                </tbody>
            </table>
        </div>

    )
}