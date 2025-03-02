import { useRecoilValue, useRecoilValueLoadable } from "recoil"
import { isAuthenticated } from "../store/atoms/AuthAtom"
import { teacherData } from "../store/atoms/UserDetails"

import {Link} from 'react-router-dom'

export default function TeacherLanding(){
    try{
        const authenticated=useRecoilValue(isAuthenticated)
        const userLoadableData=useRecoilValueLoadable(teacherData)
        if(!authenticated){
            return <h1>USER NOT Authenticated</h1>
        }
        if(userLoadableData.state==="loading")
            return <h1>USER DATA IS LOADING....</h1>
        if(userLoadableData.state==="hasError")
            return <h1>ERROR OCCURED</h1>

        const userDetails=userLoadableData.contents
    return(
        <div>
            <h1>Teacher Landing page</h1>
            <h2>Welcome {userDetails.name}</h2>
            <p>Email:{userDetails.email}</p>
            {/* <CourseGrid/> */}
            <Link to='/registeredCourses/teacher'>Courses Teaching</Link>
            {/* <Link to={}></Link> */}
        </div>

    )
    }catch(err){
        console.error(err)
        return <h1>Error OCCURED {err.toString()}</h1>
    }
}