import { Link } from "react-router-dom"
export default function Attendance({attendancePerSubject}){
    const courId=attendancePerSubject.courseId
    return (
        <tr>
            <td>{attendancePerSubject.courseId}</td>
            <td>{attendancePerSubject.courseName}</td>
            <td>{attendancePerSubject.attendanceTillNow}</td>
            <td><Link to={`/absentDates/${courId}`}>Absent Dates</Link></td>
        </tr>
    )
}