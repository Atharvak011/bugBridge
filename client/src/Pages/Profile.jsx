import React, { useState, useContext } from 'react';
import { UserContext } from "../context/UserContext";
import axios from 'axios';

const Profile = () => {
  const { user, updateUser } = useContext(UserContext);
  const [formData, setFormData] = useState({
    id: user?.id || '',
    name: user?.name || '',
    email: user?.email || '',
    role: user?.role || '',
  });

  const [isEditing, setIsEditing] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  // Handle form changes
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // Handle form submission (API Call)
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Prevent submission if nothing has changed
    if (
      formData.name === user.name &&
      formData.email === user.email
    ) {
      alert("No changes detected.");
      setIsEditing(false);
      return;
    }

    setIsSubmitting(true);

    try {
      const res = await axios.patch(
        'http://localhost:8080/api/users/updateUserDetails',
        formData,
        {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
        }
      );

      updateUser(res.data); // Update context with new user data
      alert('Profile updated successfully!');
      setIsEditing(false);
    } catch (error) {
      console.error('Error updating profile:', error);
      alert(error.response?.data?.message || 'Failed to update profile.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white shadow-md rounded-lg">
      <h2 className="text-xl font-bold mb-4">Profile</h2>

      <form onSubmit={ handleSubmit }>
        <input type="hidden" name="id" value={ formData.id } />

        {/* Name Field */ }
        <div className="mb-4">
          <label className="block text-sm font-medium">Name</label>
          <input
            type="text"
            name="name"
            value={ formData.name }
            onChange={ handleChange }
            disabled={ !isEditing }
            className="w-full p-2 border border-gray-300 rounded"
          />
        </div>

        {/* Email Field */ }
        <div className="mb-4">
          <label className="block text-sm font-medium">Email</label>
          <input
            type="email"
            name="email"
            value={ formData.email }
            onChange={ handleChange }
            disabled={ !isEditing }
            className="w-full p-2 border border-gray-300 rounded"
          />
        </div>

        {/* Role Field (Disabled) */ }
        <div className="mb-4">
          <label className="block text-sm font-medium">Role</label>
          <input
            type="text"
            name="role"
            value={ formData.role }
            disabled
            className="w-full p-2 border border-gray-300 rounded bg-gray-100"
          />
        </div>

        {/* Edit / Save Buttons */ }
        { !isEditing ? (
          <button
            type="button"
            onClick={ () => setIsEditing(true) }
            className="px-4 py-2 bg-blue-500 text-white rounded"
          >
            Edit Profile
          </button>
        ) : (
          <>
            <button
              type="submit"
              disabled={ isSubmitting }
              className={ `px-4 py-2 ${isSubmitting ? "bg-gray-400" : "bg-green-500"} text-white rounded mr-2` }
            >
              { isSubmitting ? "Saving..." : "Save Changes" }
            </button>
            <button
              type="button"
              onClick={ () => setIsEditing(false) }
              className="px-4 py-2 bg-gray-400 text-white rounded"
            >
              Cancel
            </button>
          </>
        ) }
      </form>
    </div>
  );
};

export default Profile;
