<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Card Application Form</title>
</head>
<body>

<h2>Card Application Form</h2>

<form action="applyCard" method="post">

	<label>Debit/Credit</label><br>
	    <select name="type" required>
	        <option value="">--Select--</option>
	        <option value="Debit">Debit</option>
	        <option value="Credit">Credit</option>
	    </select><br><br>

    <label>PAN Number:</label><br>
    <input type="text" name="PAN" required><br><br>

    <label>Employment Status:</label><br>
    <input type="text" name="empStatus" required><br><br>

    <label>Monthly Income:</label><br>
    <input type="number" name="monthlyIncome" required><br><br>

    <label>Card Type:</label><br>
    <select name="cardType" required>
        <option value="">--Select--</option>
        <option value="Platinum">Platinum</option>
        <option value="Gold">Gold</option>
		<option value="Silver">Silver</option>
    </select><br><br>

    <button type="submit">Apply</button>

</form>

</body>
</html>
