<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>User Registration</title>
</head>
<body>

<h2>User Registration</h2>

<form action="/register" method="POST">

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />


    <label>Name:</label>
    <input type="text" name="name" required /><br/>

    <label>Username:</label>
    <input type="text" name="username" required /><br/>
	
	<label>Email:</label>
	<input type="text" name="email" required /><br/>

	<label>Date of Birth:</label>
	<input type="Date" name="dob" required /><br/>
	
    <label>Password:</label>
    <input type="password" name="password" required /><br/>

    <label>Address:</label>
    <input type="text" name="address" /><br/>

    <label>Mobile Number:</label>
    <input type="text" name="mobileNumber" /><br/>

    <input type="submit" value="Register" />

</form>

<br/>

<a href="/login">Already have an account? Login</a>
<a href="/oauth2/authorization/google">
    <button type="button">Register with Google</button>
</a>

</body>
</html>
