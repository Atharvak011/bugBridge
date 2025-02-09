import React, { useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { USERURL } from '../config';
const userUrl = USERURL;
const Register = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [role, setRole] = useState('developer');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate(); // Use useNavigate instead of useHistory

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);


    if (!name || !email || !password) {
      setError('All fields are required');
      setLoading(false);
      return;
    }

    const userData = {
      name,
      email,
      role,
      password
    };

    try {

      const response = await axios.post(`${userUrl}/register`, userData);

      if (response.status === 200) {
        navigate('/login'); // Redirect to login page after successful registration
      }
    } catch (error) {
      setLoading(false);
      if (error.response) {
        // If response is available (non-2xx status)
        setError(error.response.data.message || 'An error occurred during registration');
      } else {
        setError('An error occurred. Please try again later.');
      }
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="w-full max-w-md bg-white p-8 rounded-lg shadow-lg">
        <h2 className="text-2xl font-semibold text-center mb-6">Register</h2>

        {/* Error message */ }
        { error && (
          <div className="text-red-500 text-sm text-center mb-4">
            { error }
          </div>
        ) }

        <form onSubmit={ handleSubmit }>
          {/* Name */ }
          <div className="mb-4">
            <label htmlFor="name" className="block text-sm font-medium text-gray-700">Name</label>
            <input
              type="text"
              id="name"
              value={ name }
              onChange={ (e) => setName(e.target.value) }
              className="w-full mt-1 px-4 py-2 border border-gray-300 rounded-md"
              placeholder="Enter your full name"
            />
          </div>

          {/* Email */ }
          <div className="mb-4">
            <label htmlFor="email" className="block text-sm font-medium text-gray-700">Email</label>
            <input
              type="email"
              id="email"
              value={ email }
              onChange={ (e) => setEmail(e.target.value) }
              className="w-full mt-1 px-4 py-2 border border-gray-300 rounded-md"
              placeholder="Enter your email"
            />
          </div>

          {/* Role */ }
          <div className="mb-4">
            <label htmlFor="role" className="block text-sm font-medium text-gray-700">Role</label>
            <select
              id="role"
              value={ role }
              onChange={ (e) => setRole(e.target.value) }
              className="w-full mt-1 px-4 py-2 border border-gray-300 rounded-md"
            >
              <option value="developer">Developer</option>
              <option value="tester">Tester</option>
            </select>
          </div>

          {/* Password */ }
          <div className="mb-6">
            <label htmlFor="password" className="block text-sm font-medium text-gray-700">Password</label>
            <input
              type="password"
              id="password"
              value={ password }
              onChange={ (e) => setPassword(e.target.value) }
              className="w-full mt-1 px-4 py-2 border border-gray-300 rounded-md"
              placeholder="Enter your password"
            />
          </div>

          {/* Submit Button */ }
          <button
            type="submit"
            disabled={ loading }
            className={ `w-full py-2 px-4 bg-blue-500 text-white font-medium rounded-md ${loading ? 'opacity-50' : ''}` }
          >
            { loading ? 'Registering...' : 'Register' }
          </button>
          <div className="mt-4 text-center">
            <p className="text-sm">
              Already have an account?
              <Link to="/login" className="text-blue-500 hover:underline">Login here</Link>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Register;
