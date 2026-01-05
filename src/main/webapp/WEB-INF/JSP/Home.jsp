<h1>This is home</h1>
<a href = "/cardform">Apply New Card</a><br/>
<a href = "/cards/mycards">See My Cards</a>
<form action="/logout" method="POST">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit">Logout</button>
</form>