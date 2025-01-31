const Login = () => {
  return (
    <div>
      <form action="addUser" method="post">
        <label htmlFor="name">Name</label>
        <input type="text" name="name" id="" required /><br />
        <label htmlFor="email">Email</label>
        <input type="email" name="email" id="" required /><br />
        <label htmlFor="role">Role</label>
        <input type="text" name="role" id="" required /><br />
        <label htmlFor="password">Password</label>
        <input type="password" name="password" id="" required /><br />
        <input type="submit" value="Submit" />
      </form>
    </div>
  )
}

export default Login;
