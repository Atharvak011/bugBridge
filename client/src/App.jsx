import './App.css';
import { BrowserRouter as Router, Route, Routes, NavLink } from 'react-router-dom';
import Login from './components/Login/Login.jsx';
import Register from './components/Registeration/Register.jsx';


function App() {
  return (
    <>
      <Router>
        <nav>
          <NavLink to="/login">Login</NavLink> |
          <NavLink to="/register">Register</NavLink>
        </nav>
        <Routes>
          <Route path="/login" element={ <Login /> } />
          <Route path="/register" element={ <Register /> } />
        </Routes>
      </Router>
    </>
  );
}

export default App;

/*
<Router>
  <nav>
    <NavLink to="/login">Login</NavLink> |
    <NavLink to="/register">Register</NavLink>
  </nav>
  <Routes>
    <Route path="/login" element={ <Login /> } />
    <Route path="/register" element={ <Register /> } />
  </Routes>
</Router> 
*/