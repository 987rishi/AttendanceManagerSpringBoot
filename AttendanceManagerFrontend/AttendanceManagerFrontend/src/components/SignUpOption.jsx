import { useNavigate } from "react-router-dom"
export default function SignupOption(){
    const navigate = useNavigate()
    return (
        <div>
            <select onChange={(e)=>navigate(`/signup/${e.target.value}`)}>
                <option value="">Signup</option>
                <option value="teacher">Teacher</option>
                <option value="student">Student</option>
            </select>
        </div>
    )
}