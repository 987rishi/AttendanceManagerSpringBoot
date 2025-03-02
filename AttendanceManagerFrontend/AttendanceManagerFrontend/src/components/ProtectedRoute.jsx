import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { isAuthenticated } from "../store/atoms/AuthAtom";
import {useEffect} from 'react'
export default function ProtectedRoute({children,user}){

    const navigate=useNavigate();
    const authenticated = useRecoilValue(isAuthenticated);

    return(
        authenticated ? children : useEffect(()=>navigate(`/login/${(user==='student')?'student':'teacher'}`),[]) 
    )
}