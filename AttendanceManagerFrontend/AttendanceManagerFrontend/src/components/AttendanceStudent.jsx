export default function AttendanceStudent({student,updater,present}){
    const handler=(e)=>{
        updater(student.rollNumber,e.target.checked)
    }
    return (
        <tr>
            <td>{student.rollNumber}</td>
            <td>{student.name}</td>
            <td>{student.email}</td>
            <td>{student.semester}</td>
            <td><input type="checkbox" checked={present} onChange={handler}/></td>
        </tr>
    )
}