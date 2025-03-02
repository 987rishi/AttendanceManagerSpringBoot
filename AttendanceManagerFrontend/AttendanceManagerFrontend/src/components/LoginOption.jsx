// import { useState } from "react"
// import { useNavigate } from "react-router-dom"
// export default function LoginOption(){
//     const [modalOpen,setModalOpen]=useState(false)
//     const [userRole,setUserRole]=useState('')
//     const navigate = useNavigate()

//     const openModal=()=>setModalOpen(true)
//     const closeModal=()=>setModalOpen(false)



//     return (
//         <div>
//             {/* <select onChange={(e)=>navigate(`/login/${e.target.value}`)}>
//                 <option value="">Login</option>
//                 <option value="teacher">Teacher</option>
//                 <option value="student">Student</option>
//             </select> */}


//         </div>
//     )
// }
import { useState } from 'react';

export default function LoginChoice() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [userRole, setUserRole] = useState(null);

  // Open modal
  const openModal = () => {
    setIsModalOpen(true);
  };

  // Close modal
  const closeModal = () => {
    setIsModalOpen(false);
  };

  // Handle user role selection
  const handleRoleSelect = (role) => {
    setUserRole(role);
    closeModal();
    // You can now redirect to the login page based on the role
    // Example: navigate(`/login/${role}`)
  };

  return (
    <div>
      <button onClick={openModal} className="btn">
        Login
      </button>

      {/* Modal */}
      {isModalOpen && (
        <div className="fixed inset-0 flex justify-center items-center bg-gray-500 bg-opacity-50 z-50">
          <div className="bg-white p-6 rounded-lg shadow-lg w-80">
            <h2 className="text-lg font-bold mb-4">Select Your Role</h2>
            <div className="space-y-4">
              <button
                onClick={() => handleRoleSelect('student')}
                className="w-full py-2 bg-blue-500 text-white rounded-md"
              >
                Student
              </button>
              <button
                onClick={() => handleRoleSelect('teacher')}
                className="w-full py-2 bg-green-500 text-white rounded-md"
              >
                Teacher
              </button>
              <button
                onClick={closeModal}
                className="w-full py-2 bg-gray-400 text-white rounded-md"
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Display selected role (optional) */}
      {userRole && <p>You selected: {userRole}</p>}
    </div>
  );
}
