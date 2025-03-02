
import axios from "axios";
import FormTemplate from "./FormTemplate";
import ProcessingModal from "./ProcessingModal";
import React, { useState } from 'react'
export default function Signup({user}) {
    const [message,setMessage]=useState("")
    const [errorMessage,setErrorMessage]=useState("")

    async function onSubmit(e){
        e.preventDefault()
        const form = new FormData(e.target)
        const formValues = Object.fromEntries(form)
        console.log(formValues)

        // const credentials=btoa(`${formValues.username}:${formValues.password}`)
        setMessage('loading')

        try {
            const res=await axios.post(`http://localhost:8080/register/${(user==="student")?"student":"teacher"}`,{
                'name':formValues.first_name+" "+formValues.last_name||null,
                'username':formValues.username||null,
                'password':formValues.password||null,
                'email':formValues.email||null,
                'semester':formValues.semester||null
            },{
                headers:{
                    'Content-Type':'application/json',                
                }
            })
            if(res.status===201)
                setMessage("REGISTRATION SUCCESSFULL")
            // console.log(res.status,"STATUS TPUE-"+typeof(res.status))
            else
                setErrorMessage("ERROR OCCURED WITH STATUS-"+res.status.toString())

            
        } catch (error) {
            console.error('Error during login:', error.response?.data || error.message);
            setMessage("")
            setErrorMessage(error.response?.data?.message || "An error occurred during signup.");
            
        }
    }
    return (
        <div className="container mx-auto max-w-2xl p-6">
            <form onSubmit={onSubmit} {...((message==='loading')?{inert:"true"}:{})}>
              <FormTemplate user={user}/>
            </form>



            
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
        </div>
    )
}






