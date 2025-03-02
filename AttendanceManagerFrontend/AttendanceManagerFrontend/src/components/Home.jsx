import { useSetRecoilState } from "recoil"
import { isAuthenticated } from "../store/atoms/AuthAtom"
import { studentData, teacherData } from "../store/atoms/UserDetails"
import { useEffect } from "react"

export default function Home() {
    // const setAuth=useSetRecoilState(isAuthenticated)
    // const setStudentData=useSetRecoilState(studentData)
    // const setTeacherData=useSetRecoilState(teacherData)

    // useEffect(()=>{
    //     setAuth(false)
    //     setStudentData({})
    //     setTeacherData({})
    // },[])

    return(
        <div>
            <h1 className="text-green-500 text-4xl font-bold text-center">Welcome to the Attendance Manager</h1>
            <h2 className="">This is the home page</h2>
            <h1 className="text-3xl font-bold underline">
    Hello world!
  </h1>
        </div>
    )
}
// import { useState, useEffect } from "react";

// const LoadingModal = ({ show }) => {
//   if (!show) return null;

//   return (
//     <div className="fixed inset-0 flex items-center justify-center bg-black/50 backdrop-blur-sm z-50">
//       <div className="bg-white p-6 rounded-lg shadow-lg flex flex-col items-center">
//         {/* Animated Spinner */}
//         <div className="w-12 h-12 border-4 border-blue-500 border-t-transparent rounded-full animate-spin"></div>
        
//         {/* Loading Text */}
//         <p className="mt-4 text-lg font-semibold text-gray-700">Loading...</p>
//       </div>
//     </div>
//   );
// };

// const DataComponent = () => {
//   const [data, setData] = useState(null);
//   const [loading, setLoading] = useState(true);

//   useEffect(() => {
//     setTimeout(() => {
//       setData("Data Loaded 🎉");
//       setLoading(false);
//     }, 3000);
//   }, []);

//   return (
//     <div className="flex items-center justify-center h-screen">
//       {/* Show modal while loading */}
//       <LoadingModal show={loading} />
      
//       {/* Show data when loaded */}
//       {!loading && <p className="text-xl font-bold text-green-600">{data}</p>}
//     </div>
//   );
// };

// export default function Home() {
//   return <DataComponent />;
// }

