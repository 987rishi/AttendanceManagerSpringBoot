
import { useEffect, useState } from "react";
import Course from "./Course";
import axios from "axios";
import { useRecoilState, useRecoilValue } from "recoil";
import { studentData } from "../store/atoms/UserDetails";
import { useNavigate } from "react-router-dom";

export default function CourseGrid() {
    const [courses, setCourses] = useState([]);
    const [studentDetails,setStudentDetails] = useRecoilState(studentData);
    const [message,setMessage]=useState("")
    const navigate=useNavigate()
    
    const styles = {
        border: "1px solid black",
    };

    const onChangeHandler=(courseId,isChecked)=>{
        setStudentDetails((prev)=>(
                {...prev,
                    listOfCourses:isChecked?[...prev.listOfCourses,courseId]:prev.listOfCourses.filter(e=>e!==courseId)
                }
            )
        )
    }
    const updateCoursesHandler=async () => {
        const payload=studentDetails.listOfCourses
        setMessage("Loading....")
        try {
            const response=await axios.put(`http://localhost:8080/student/student/${studentDetails.rollNumber}/updateCoursesByIds`,payload,{
                headers:{
                    'Content-Type':'application/json'
                },
                withCredentials:true
            })
            console.log("UPDATED")
            console.log(response)
            setStudentDetails(response.data)
            setMessage("Successfully updated.Redirecting to landing page...")
            navigate('/student/landing')

        } catch (error) {
            console.error(error)
            setMessage("Error occured "+error.message)
            
        }
    }

    useEffect(() => {
        const fetchCourses = async () => {
            try {
                const res = await axios.get("http://localhost:8080/course/courses", {
                    withCredentials: true,
                    headers: {
                        "Content-Type": "application/json",
                    },
                });
                console.log(res.data);
                setCourses(res.data);
            } catch (error) {
                console.error("ERROR OCCURRED IN COURSE GRID:", error.response?.data || error.message);
            }
        };
        fetchCourses();
    }, []);

    const registeredCourses = new Set(studentDetails.listOfCourses || []);
    const checkRegistered = (courseId) => registeredCourses.has(courseId);

    return (
        <div>
            <h1>Available Courses</h1>
            <table style={styles}>
                <thead>
                    <tr>
                        <th style={styles}>Course Id</th>
                        <th>Course Name</th>
                        <th>Semester Offered In</th>
                        <th>Registered</th>
                    </tr>
                </thead>
                <tbody>
                    {courses.length > 0 ? (
                        courses.map((e) => (
                            <Course course={e} key={e.courseId} registered={checkRegistered(e.courseId)}handler={onChangeHandler} user="student"/>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="4">No Courses FOUND</td>
                        </tr>
                    )}
                </tbody>
            </table>
            <button type="submit" onClick={updateCoursesHandler}>Register Courses</button>
            <br/>
            {message}
        </div>
    );
}

