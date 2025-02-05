import React, { useState } from 'react';
import { Navigate } from 'react-router-dom';
import axios from 'axios';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();

    // Validate form inputs
    if (!email || !password) {
      setErrorMessage('Please fill in all fields');
      return;
    }

    const loginData = { email, password };

    try {
      // Making API call using axios
      const response = await axios.post('http://localhost:8080/api/users/authenticate', loginData);

      if (response.status === 200 && response.data.message === 'Authentication Success') {
        // Store JWT in localStorage (or cookies for better security)
        localStorage.setItem('token', response.data.token);

        // Redirect to dashboard
        setIsAuthenticated(true);
      }
    } catch (error) {
      if (error.response && error.response.data) {
        setErrorMessage(error.response.data.message || 'Something went wrong');
      } else {
        setErrorMessage('Network Error');
      }
    }
  };

  // Redirect to dashboard if authenticated
  if (isAuthenticated) {
    return <Navigate to="/dashboard" />;
  }

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <div className="w-full max-w-md p-8 bg-white rounded-lg shadow-lg">
        <h2 className="text-2xl font-bold text-center mb-4">Login</h2>

        { errorMessage && (
          <div className="bg-red-200 text-red-700 p-2 rounded mb-4">
            { errorMessage }
          </div>
        ) }

        <form onSubmit={ handleLogin }>
          <div className="mb-4">
            <label htmlFor="email" className="block text-gray-700 font-semibold">Email</label>
            <input
              type="email"
              id="email"
              value={ email }
              onChange={ (e) => setEmail(e.target.value) }
              placeholder="Enter your email"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg"
              required
            />
          </div>

          <div className="mb-6">
            <label htmlFor="password" className="block text-gray-700 font-semibold">Password</label>
            <input
              type="password"
              id="password"
              value={ password }
              onChange={ (e) => setPassword(e.target.value) }
              placeholder="Enter your password"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg"
              required
            />
          </div>

          <button
            type="submit"
            className="w-full py-2 bg-blue-500 text-white font-semibold rounded-lg"
          >
            Login
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
