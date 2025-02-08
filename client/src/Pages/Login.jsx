// import React, { useState, useContext, useEffect } from 'react';
// import { Link } from 'react-router-dom';
// import { useNavigate } from 'react-router-dom';
// import { UserContext } from '../context/UserContext';
// import { USERURL, BUGURL } from "../config";
// import axios from 'axios';

// const userUrl = USERURL;
// const Login = () => {
//   const navigate = useNavigate();
//   const { user, updateUser } = useContext(UserContext);
//   const [loading, setLoading] = useState(false);
//   const [credentials, setCredentials] = useState({ email: '', password: '' });
//   const [error, setError] = useState('');

//   // Redirect user if already logged in
//   useEffect(() => {
//     if (user) {
//       navigate('/dashboard');  // Redirect to dashboard if authenticated
//     }
//   }, [user, navigate]);

//   const handleChange = (e) => {
//     setCredentials({ ...credentials, [e.target.name]: e.target.value });
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     setLoading(true);
//     setError('');
//     try {
//       const res = await axios.post(`${userUrl}/authenticate`, credentials);
//       if (res.data.message === 'Authentication Success' && res.status === 200) {
//         console.log(res.data.data.id);
//         const newUser = {
//           id: res.data.data.id,
//           email: res.data.data.email,
//           name: res.data.data.name,
//           role: res.data.data.role
//         }
//         // const { user } = newUser;
//         // updateUser(res.data.data);
//         // const { token, user } = res.data;

//         // Store token & user data
//         // localStorage.setItem('token', token);
//         localStorage.setItem('user', JSON.stringify(newUser));
//         updateUser(newUser); // Update context with user data
//         navigate('/dashboard'); // Redirect to dashboard
//       } else {
//         setError('Invalid credentials, please try again.');
//       }
//     } catch (error) {
//       setError('Login failed. Please check your credentials.');
//     } finally {
//       setLoading(false);
//     }
//   };

//   return (
//     <div className="flex items-center justify-center h-screen bg-gray-100">
//       <div className="bg-white p-6 rounded-lg shadow-md w-96">
//         <h2 className="text-2xl font-semibold text-center">Login</h2>

//         { error && <p className="text-red-500 text-sm mt-2">{ error }</p> }

//         <form onSubmit={ handleSubmit } className="space-y-4 mt-4">
//           <input
//             type="email"
//             name="email"
//             placeholder="Email"
//             value={ credentials.email }
//             onChange={ handleChange }
//             required
//             className="w-full p-2 border rounded"
//           />
//           <input
//             type="password"
//             name="password"
//             placeholder="Password"
//             value={ credentials.password }
//             onChange={ handleChange }
//             required
//             className="w-full p-2 border rounded"
//           />
//           <button
//             type="submit"
//             className="w-full bg-blue-500 text-white py-2 rounded"
//             disabled={ loading }
//           >
//             { loading ? 'Logging in...' : 'Login' }
//           </button>
//         </form>

//         <div className="mt-4 text-center">
//           <p className="text-sm">
//             Don't have an account?
//             <Link to="/register " className="text-blue-500 hover:underline">Register here</Link>
//           </p>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default Login;




import React, { useState, useContext, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { UserContext } from '../context/UserContext';
import { USERURL } from "../config";
import axios from 'axios';

const userUrl = USERURL;

const Login = () => {
  const navigate = useNavigate();
  const { user, updateUser } = useContext(UserContext);
  const [loading, setLoading] = useState(false);
  const [credentials, setCredentials] = useState({ email: '', password: '' });
  const [error, setError] = useState('');

  // Redirect user if already logged in
  useEffect(() => {
    if (user) {
      navigate('/dashboard');  // Redirect to dashboard if authenticated
    }
  }, [user, navigate]);

  const handleChange = (e) => {
    setCredentials({ ...credentials, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      const res = await axios.post(`${userUrl}/authenticate`, credentials);
      if (res.data.message === 'Authentication Success' && res.status === 200) {
        const newUser = {
          id: res.data.data.id,
          email: res.data.data.email,
          name: res.data.data.name,
          role: res.data.data.role,
        };

        // Store user data in localStorage and update context
        localStorage.setItem('user', JSON.stringify(newUser));
        updateUser(newUser);  // Update context with user data
        navigate('/dashboard');  // Redirect to dashboard
      } else {
        setError('Invalid credentials, please try again.');
      }
    } catch (error) {
      setError('Login failed. Please check your credentials.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex items-center justify-center h-screen bg-gray-100">
      <div className="bg-white p-6 rounded-lg shadow-md w-96">
        <h2 className="text-2xl font-semibold text-center">Login</h2>

        { error && <p className="text-red-500 text-sm mt-2">{ error }</p> }

        <form onSubmit={ handleSubmit } className="space-y-4 mt-4">
          <input
            type="email"
            name="email"
            placeholder="Email"
            value={ credentials.email }
            onChange={ handleChange }
            required
            className="w-full p-2 border rounded"
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={ credentials.password }
            onChange={ handleChange }
            required
            className="w-full p-2 border rounded"
          />
          <button
            type="submit"
            className="w-full bg-blue-500 text-white py-2 rounded"
            disabled={ loading }
          >
            { loading ? 'Logging in...' : 'Login' }
          </button>
        </form>

        <div className="mt-4 text-center">
          <p className="text-sm">
            Don't have an account?
            <Link to="/register" className="text-blue-500 hover:underline">Register here</Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Login;
