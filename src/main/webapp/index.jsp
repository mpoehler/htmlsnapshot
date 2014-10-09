<html>
<body>
<h2>Get Screenshots or rendered Sources</h2>
<form action="snapshot" method="GET">
    Url:<input type="text" name="url"><br/>
    Output: <select name="output"><option selected>source</option><option>screenshot</option></select><br/>
    Resolution: <select name="resolution">
    <option selected>BigDesktop</option>
    <option>GenericNotebook</option>
    <option>GoogleNexus5</option>
    <option>GoogleNexus7</option>
    </select><br/>
    <input type="submit" value="Start">
</form>
</body>
</html>
