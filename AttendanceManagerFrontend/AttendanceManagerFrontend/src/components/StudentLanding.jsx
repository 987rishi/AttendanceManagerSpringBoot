// import { useRecoilValue, useRecoilValueLoadable } from "recoil"
// import { isAuthenticated } from "../store/atoms/AuthAtom"
// export default function StudentLanding() {
//     const userDetailsLoadable=useRecoilValueLoadable(studentData)
//     const Authenticated=useRecoilValue(isAuthenticated)

//     if(!Authenticated){
//         return <h1>USER NOT Authenticated</h1>
//     }

//     if(userDetailsLoadable.state==="loading"){
//         return <h1>DETAILS ARE loading.....</h1>
//     }
//     if(userDetailsLoadable.state==="hasError"){
//         return <h1>ERROR OCCURED</h1>
//     }

//     const userDetails=userDetailsLoadable.contents

//     console.log("is auth in LANDING"+isAuthenticated);
//     return (
//         <div>
       
//                 <h1>Student Landing Page</h1>
//                 <h2>Welcome {userDetails.name}</h2>
//                 <p>Email {userDetails.email}</p>
//                 <p>Semester {userDetails.semester}</p>
        
//         </div>
//     )
// }



import React from "react";
import {Link} from 'react-router-dom'
import { useRecoilValue, useRecoilValueLoadable } from "recoil";
import { studentData } from "../store/atoms/UserDetails";
import { isAuthenticated as isAuthAtom } from "../store/atoms/AuthAtom";
import CourseGrid from "./CourseGrid";

export default function StudentLanding() {
    try {
        const userDetailsLoadable = useRecoilValueLoadable(studentData);
        const isAuthenticated = useRecoilValue(isAuthAtom);

        // Handle unauthenticated state
        if (!isAuthenticated) {
            return <h1>You are not authenticated. Please log in.</h1>;
        }

        // Handle Loadable states
        if (userDetailsLoadable.state === "loading") {
            return <h1>Loading user details...</h1>;
        }

        if (userDetailsLoadable.state === "hasError") {
            return <h1>Error loading user details. Please try again later.</h1>;
        }

        // Extract value from Loadable
        const userDetails = userDetailsLoadable.contents;

        return (
            <div>
                <h1>Student Landing Page</h1>
                <h2>Welcome {userDetails.name}</h2>
                <p>Email: {userDetails.email}</p>
                <p>Semester: {userDetails.semester}</p>
                
                {/* <CourseGrid/> */}
                <Link to='/student/registerCourses'>RegisterCourses</Link>
                <br/>
                <Link to='/registeredCourses/student'>RegisteredCourses</Link>
                <br/>                
                <Link to='/student/attendance'>View Attendance</Link>
            </div>
        );
    } catch (error) {
        console.error("Error in StudentLanding:", error);
        return <h1>An unexpected error occurred. Please try again later.</h1>;
    }
}
