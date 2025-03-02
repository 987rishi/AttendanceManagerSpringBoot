// import { useNavigate } from "react-router-dom"
// import SignupOption from "./SignUpOption"
// import LoginOption from "./LoginOption"
// import { useEffect } from "react"
// import { useRecoilValue } from "recoil"
 import { isAuthenticated } from "../store/atoms/AuthAtom"
// export default function Navbar() {
//     const navigate = useNavigate()
//     const auth=useRecoilValue(isAuthenticated)

//     if(auth)
//         return (
//     <div>
//         <button onClick={()=>navigate('/logout')}>Logout</button>
//         <br/>
//         <button onClick={()=>navigate(-1)}>Back</button>
//     </div>
//     )

//     return(
//         <div>
//             <button className="" type="button" onClick={()=>navigate('/')}>Home</button>
//             <SignupOption/>
//             <LoginOption/>
//         </div>
//     )
// }

import { Disclosure, DisclosureButton, DisclosurePanel, Menu, MenuButton, MenuItem, MenuItems } from '@headlessui/react';
import { Bars3Icon, BellIcon, XMarkIcon } from '@heroicons/react/24/outline';
import { Link, useNavigate } from 'react-router-dom'; // Import Link
import {useState} from 'react'
import { User, School } from "lucide-react";
import { useRecoilValue } from "recoil";


const navigation = [
  { name: 'Dashboard', href: '/', current: true },
  { name: 'Home', href: '/', current: false },
  { name: 'Login', href: '#', current: false },
  { name: 'Sign Up', href: '#', current: false },
];

function classNames(...classes) {
  return classes.filter(Boolean).join(' ');
}

export default function Example() {
    const navigate=useNavigate(); 
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [modalType,setModalType]=useState(null)
    const auth=useRecoilValue(isAuthenticated)

   
     // Open modal
     const openModal = (typeModal) => {
        console.log("typeModal")
        console.log(typeModal)
        setModalType(typeModal)

       setIsModalOpen(true);
     };
   
     // Close modal
     const closeModal = () => {
        console.log("closeModal Called")
       setIsModalOpen(false);
     };

     const onClicklinkHandler=(role)=>{
        
        (modalType==='Login')?navigate(`/login/${role}`):(modalType==='Sign Up')?navigate(`/signup/${role}`):null
        closeModal()
     }




  return (
    <>
    <Disclosure as="nav" className="bg-gray-800">
      <div className="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
        <div className="relative flex h-16 items-center justify-between">
          <div className="absolute inset-y-0 left-0 flex items-center sm:hidden">
            {/* Mobile menu button*/}
            <DisclosureButton className="group relative inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:ring-2 focus:ring-white focus:outline-hidden focus:ring-inset">
              <span className="absolute -inset-0.5" />
              <span className="sr-only">Open main menu</span>
              <Bars3Icon aria-hidden="true" className="block size-6 group-data-open:hidden" />
              <XMarkIcon aria-hidden="true" className="hidden size-6 group-data-open:block" />
            </DisclosureButton>
          </div>
          <div className="flex flex-1 items-center sm:items-stretch sm:justify-start justify-between">
            <div className="flex shrink-0 items-center">
              <img
                alt="Your Company"
                src="https://tailwindui.com/plus/img/logos/mark.svg?color=indigo&shade=500"
                className="h-8 w-auto"
              />
            </div>
            <div className="hidden sm:ml-6 sm:block">
              <div className="flex space-x-4 ml-auto">

                {(auth===false)?(navigation.map((item) => (
                  <Link
                    key={item.name}
                    to={item.href} // Use 'to' instead of 'href'
                    aria-current={item.current ? 'page' : undefined}
                    className={classNames(
                      item.current ? 'bg-gray-900 text-white' : 'text-gray-300 hover:bg-gray-700 hover:text-white',
                      'rounded-md px-3 py-2 text-sm font-medium',
                    )}
                    onClick={(item.name==='Login'||item.name==='Sign Up')?(e)=>{
                        e.preventDefault()
                        console.log("item Name")
                        console.log(item.name)

                        openModal(item.name)
                        }:null}
                  >
                    {item.name}
                  </Link>
               

                )
                )):null}
                {/* ( */}
                    {/* <Link
                    to={'/logout'}
                    className={classNames('text-gray-300 hover:bg-gray-700 hover:text-white','rounded-md px-3 py-2 text-sm font-medium')}
                      >
                        Logout
                    </Link> */}
                    

                {/* ) */}
                
              </div>
            </div>

                    {auth !== false && (
            <div className="ml-auto">
            <Link
                to={'/logout'}
                className="text-gray-300 hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-sm font-medium"
            >
                Logout
            </Link>
            </div>
        )}

          </div>
          <div className="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
            <button
              type="button"
              className="relative rounded-full bg-gray-800 p-1 text-gray-400 hover:text-white focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800 focus:outline-hidden"
            >
              <span className="absolute -inset-1.5" />
              <span className="sr-only">View notifications</span>
              <BellIcon aria-hidden="true" className="size-6" />
            </button>

            {/* Profile dropdown */}
            {/* <Menu as="div" className="relative ml-3">
              <div>
                <MenuButton className="relative flex rounded-full bg-gray-800 text-sm focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800 focus:outline-hidden">
                  <span className="absolute -inset-1.5" />
                  <span className="sr-only">Open user menu</span>
                  <img
                    alt=""
                    src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
                    className="size-8 rounded-full"
                  />
                </MenuButton>
              </div>
              <MenuItems
                transition
                className="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 ring-1 shadow-lg ring-black/5 transition focus:outline-hidden data-closed:scale-95 data-closed:transform data-closed:opacity-0 data-enter:duration-100 data-enter:ease-out data-leave:duration-75 data-leave:ease-in"
              >
                <MenuItem>
                  <Link
                    to="/profile"
                    className="block px-4 py-2 text-sm text-gray-700 data-focus:bg-gray-100 data-focus:outline-hidden"
                  >
                    Your Profile
                  </Link>
                </MenuItem>
                <MenuItem>
                  <Link
                    to="/settings"
                    className="block px-4 py-2 text-sm text-gray-700 data-focus:bg-gray-100 data-focus:outline-hidden"
                  >
                    Settings
                  </Link>
                </MenuItem>
                <MenuItem>
                  <Link
                    to="/signout"
                    className="block px-4 py-2 text-sm text-gray-700 data-focus:bg-gray-100 data-focus:outline-hidden"
                  >
                    Sign out
                  </Link>
                </MenuItem>
              </MenuItems>
            </Menu> */}
          </div>
        </div>
      </div>

      <DisclosurePanel className="sm:hidden">
        <div className="space-y-1 px-2 pt-2 pb-3">
          {(auth===false)?(navigation.map((item) => (
            <DisclosureButton
              key={item.name}
              as={Link} // Use 'Link' here for navigation
              to={item.href} // Use 'to' instead of 'href'
              aria-current={item.current ? 'page' : undefined}
              className={classNames(
                item.current ? 'bg-gray-900 text-white' : 'text-gray-300 hover:bg-gray-700 hover:text-white',
                'block rounded-md px-3 py-2 text-base font-medium',
              )}
              onClick={(item.name==='Login'||item.name==='Sign Up')?(e)=>{
                        e.preventDefault()
                        openModal(item.name)
                        }:null}
            >
              {item.name}
            </DisclosureButton>
          ))):(
            <Link
                    to={'/logout'}
                    className={classNames('text-gray-300 hover:bg-gray-700 hover:text-white','rounded-md px-3 py-2 text-sm font-medium')}
                      >
                        Logout
                    </Link>

          )
          }
        </div>
      </DisclosurePanel>
    </Disclosure>

    {isModalOpen&&(
        <div className='fixed inset-0 z-50 flex items-center justify-center bg-gray-500/70'>
            <div className='bg-white p-8 rounded-lg shadow-lg max-w-sm w-full'>
                <div className='flex justify-between items-center'>
                    <h2 className='text-lg font-semibold'>{modalType}</h2>
                    <button type='button' onClick={()=>closeModal()} className='text-gray-500 border border-transparent hover:border-gray-500 transition duration-200 hover:text-black'>&times;</button>
                </div>
                <div className='mt-4 flex gap-6 justify-center'>
                    {/* Teacher Card */}

                    <button className="w-64 p-6 bg-white rounded-2xl shadow-lg hover:shadow-xl transition-all duration-300 flex flex-col items-center border border-gray-200 hover:border-green-500" onClick={()=>onClicklinkHandler('teacher')}>
                        <School className="w-12 h-12 text-green-500 mb-4" />
                        <h3 className="text-lg font-semibold text-gray-800">Teacher</h3>
                        <p className="text-sm text-gray-500 mt-2">Manage classes and assignments.</p>
                    </button>
                
                   
                    {/* Student Card */}
                    <button className="w-64 p-6 bg-white rounded-2xl shadow-lg hover:shadow-xl transition-all duration-300 flex flex-col items-center border border-gray-200 hover:border-blue-500" onClick={()=>onClicklinkHandler('student')}>
                        <User className="w-12 h-12 text-blue-500 mb-4" />
                        <h3 className="text-lg font-semibold text-gray-800">Student</h3>
                        <p className="text-sm text-gray-500 mt-2">Access learning materials and assignments.</p>
                    </button>
     
                </div>
            </div>
        </div>
    )
    }
    </>
  );
}
