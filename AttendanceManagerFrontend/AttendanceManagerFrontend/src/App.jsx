
import Navbar from './components/Navbar'
import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Home from './components/Home'
import Login from './components/Login'
import Signup from './components/Signup'
import ProtectedRoute from './components/ProtectedRoute'
import StudentLanding from './components/StudentLanding'
import { RecoilRoot } from 'recoil'
import TeacherLanding from './components/TeacherLanding'
import RegisteredCourses from './components/RegisteredCourses'
import Logout from './components/Logout'
import AbsentDates from './components/AbsentDates'
import ViewAttendance from './components/ViewAttendance'
import CourseGrid from './components/CourseGrid'
import MarkAttendance from './components/MarkAttendance'

function App() {
 return(
  <RecoilRoot>
    <MainApp />
  </RecoilRoot>
 )
  
}
function MainApp(){
  return(
    <div>
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login/teacher" element={<Login user="teacher"/>} />
        <Route path="/login/student" element={<Login user="student"/>} />
        <Route path="/signup/teacher" element={<Signup user="teacher" />} />
        <Route path="/signup/student" element={<Signup user="student" />} />
        <Route path="/student/landing" element={
          <ProtectedRoute>
            <StudentLanding />
          </ProtectedRoute>
          } />
        <Route path='/teacher/landing' element={
          <ProtectedRoute>
            <TeacherLanding/>
          </ProtectedRoute>
        }/>
        <Route path='/registeredCourses/:user' element={
          <ProtectedRoute>
            <RegisteredCourses/>
          </ProtectedRoute>
        } />
        <Route path='/absentDates/:courId'element={
          <ProtectedRoute>         
            <AbsentDates/>
          </ProtectedRoute>
        }/>
        <Route path='/student/attendance'element={
          <ProtectedRoute>
            <ViewAttendance/>
          </ProtectedRoute>
        }/>
        <Route path='/student/registerCourses' element={
          <ProtectedRoute>
            <CourseGrid/>
          </ProtectedRoute>
        }/>
        <Route path='/teacher/markAttendance/:courseId' element={
          <ProtectedRoute>
            <MarkAttendance/>
          </ProtectedRoute>
        }/>


        <Route path='/logout' element={<Logout/>}/>
      </Routes>
    </BrowserRouter>
  
    </div>
  )
}

export default App
