<h1>This is home</h1>
<a href = "/cardform">Apply New Card</a><br/>
<a href = "/cards/mycards">See My Cards</a>

<h2>Pending Payments</h2>

<p>Order ID: ORD123</p>
<p>Amount: ₹1500</p>
<p>Status: PENDING</p>

<form action="/payNow" method="post">
    <button type="submit">Pay Now</button>
</form>

<form action="/logout" method="POST">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit">Logout</button>
</form>