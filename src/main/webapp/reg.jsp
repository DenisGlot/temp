<%@page import="servlets.SendHtml"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration Data</title>
</head>
<body>
<%  %>
<form>
  <div class="form-group">
    <label for="first">First Name</label>
    <input type="text" class="form-control" id="first" aria-describedby="emailHelp" placeholder="Enter first name">
  </div>
  <div class="form-group">
    <label for="last">Last Name</label>
    <input type="text" class="form-control" id="last" aria-describedby="emailHelp" placeholder="Enter first name">
  </div>
  <div class="form-group">
    <label for="phone">Phone number</label>
    <input type="text" class="form-control input-medium bfh-phone" data-format="+9 (ddd) ddd-dddd" id="phone">
  </div>
  <div class="form-group">
    <label for="password">Password</label>
    <input type="password" class="form-control" id="password">
  </div>
  <div class="form-group">
    <label for="confirmPassword">Confirm Password</label>
    <input type="password" class="form-control" id="confirmPassword">
  </div>
  
  <button type="submit" class="btn btn-primary">Submit</button>
</form>

</body>
</html>