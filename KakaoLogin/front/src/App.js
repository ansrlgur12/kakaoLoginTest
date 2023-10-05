
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './LoginTest/KakaoLogin';
import Main from './LoginTest/MainPage';
import LoginFinish from './LoginTest/LoginFinish';
import KakaoLogout from './LoginTest/KakaoLogout';

function App() {



 return(
  <Router>
  <Routes>
    <Route path='/' element={<Main />} />
    <Route path='/login' element={<Login />} />
    <Route path='/kakao/login' element={<LoginFinish />} />
    <Route path="/kakao/logout" element={<KakaoLogout />} />
  </Routes>
</Router>
   
 );

}

export default App;
