import { useRecoilState,useSetRecoilState } from "recoil"
import { isAuthenticated } from "../store/atoms/AuthAtom"
import { studentData, teacherData } from "../store/atoms/UserDetails"
import { useEffect } from "react"
import { useNavigate } from "react-router-dom"

export default function Logout(){
    const setAuth=useSetRecoilState(isAuthenticated)
    const setStudentData=useSetRecoilState(studentData)
    const setTeacherData=useSetRecoilState(teacherData)
    const navigate=useNavigate()

    useEffect(()=>{
        setAuth(false)
        setStudentData({})
        setTeacherData({})
        sessionStorage.clear()
        localStorage.clear()
        {navigate('/')}
    },[])
    return(
        <>
          
        </>
    )

}