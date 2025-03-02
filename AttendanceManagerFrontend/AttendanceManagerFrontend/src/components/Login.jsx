import React, { useState } from 'react';
import axios from 'axios';
import { useSetRecoilState,useRecoilState } from 'recoil';
import { studentData, teacherData } from '../store/atoms/UserDetails';
import { isAuthenticated } from '../store/atoms/AuthAtom';
import {useNavigate} from 'react-router-dom'
import { useEffect } from 'react';
import ProcessingModal from './ProcessingModal';


// WHENEVER YOU USE SESSIONS USING COOKIES SET WITHCREDENTIALS TRUE IN THE AUTHENTICATING REQUEST OTHERWISE THE BROWSER WILL NOT REGISTER THE COOKIE

export default function Login({ user }) {
    const setStudentData = useSetRecoilState(studentData);
    const setTeacherData = useSetRecoilState(teacherData);
    const [auth,setAuthenticated] = useRecoilState(isAuthenticated);
    const navigate=useNavigate()
    const [message,setMessage]=useState("")
    const [errorMessage,setErrorMessage]=useState("")


    useEffect(() => {
        if (auth) {
            navigate(`/${(user === "student") ? "student" : "teacher"}/landing`);
        }
    }, [auth])

    async function handleSubmit(e) {
        e.preventDefault();
        const form = e.target;
        const data = new FormData(form);
        const userData = {
            username: data.get('username'),
            email: data.get('email'),
            password: data.get('password'),
        };

        // Encode credentials for Basic Auth
        const credentials = btoa(`${userData.username}:${userData.password}`);
        setMessage("loading")
        setErrorMessage("")

        try {
            const response = await axios.post(
                `http://localhost:8080/${user === 'student' ? 'student/student' : 'teacher/teacher'}`,
                { email: userData.email },
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Basic ${credentials}`,
                    },
                    withCredentials:true
                }
            );
            // Set data in Recoil state
            if (response.data.email === userData.email) {
                setAuthenticated(true);
                if (user === 'student') {
                    setStudentData(response.data);
                    navigate('/student/landing')

                } else {
                    setTeacherData(response.data);
                    navigate('/teacher/landing')
                }
                console.log('Login successful:', response.data);
                setMessage("Login successfull..")
            }
        } catch (error) {
            console.error('Error during login:', error.response?.data || error.message);
            setMessage("")
            setErrorMessage(error.response?.data?.message || "An error occurred during login.");
        }
    }

    return (
        <>
      {/*
        This example requires updating your template:

        ```
        <html class="h-full bg-white">
        <body class="h-full">
        ```
      */}
      <div className="h-screen flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <h2 className="mt-10 text-center text-2xl/9 font-bold tracking-tight text-gray-900">
            Sign in to your account
          </h2>
        </div>

        <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
          <form onSubmit={handleSubmit} className="space-y-6">

          <div>
              <label htmlFor="email" className="block text-sm/6 font-medium text-gray-900">
                Username
              </label>
              <div className="mt-2">
                <input
                  id="username"
                  name="username"
                  type="text"
                  required
                  autoComplete="username"
                  className="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6"
                />
              </div>
            </div>
            <div>
              <label htmlFor="email" className="block text-sm/6 font-medium text-gray-900">
                Email address
              </label>
              <div className="mt-2">
                <input
                  id="email"
                  name="email"
                  type="email"
                  required
                  autoComplete="email"
                  className="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6"
                />
              </div>
            </div>

            <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm/6 font-medium text-gray-900">
                  Password
                </label>
                <div className="text-sm">
                  <a href="#" className="font-semibold text-indigo-600 hover:text-indigo-500">
                    Forgot password?
                  </a>
                </div>
              </div>
              <div className="mt-2">
                <input
                  id="password"
                  name="password"
                  type="password"
                  required
                  autoComplete="current-password"
                  className="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6"
                />
              </div>
            </div>

            <div>
              <button
                type="submit"
                className="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm/6 font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
              >
                Sign in
              </button>
            </div>
          </form>
        </div>
      </div>
      {(message === 'loading' || errorMessage) && (
    <div className="fixed inset-0 flex items-center justify-center bg-black/50 backdrop-blur-sm z-50">
        <div className="bg-white p-6 rounded-lg shadow-lg flex flex-col items-center w-96">
            {message === 'loading' ? (
                <>
                    {/* Animated Spinner
                    <div className="w-12 h-12 border-4 border-blue-500 border-t-transparent rounded-full animate-spin"></div>
                    <p className="mt-4 text-lg font-semibold text-gray-700">Loading...</p> */}
                    <ProcessingModal/>
                </>
            ) : (
                <>
                    {/* Error Message */}
                    <p className="text-red-600 text-lg font-semibold">{errorMessage}</p>
                    <button
                        onClick={() => setErrorMessage("")} // Close error modal
                        className="mt-4 px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600"
                    >
                        Close
                    </button>
                </>
            )}
        </div>
    </div>
)}
    </>
 
    );
}

