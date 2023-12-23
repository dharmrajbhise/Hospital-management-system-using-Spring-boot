<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<title>Doctor</title>

<style>
 /* body {
       background-color: #606C5D;
    } */
    
   .navbar {
   			background-color: #606C5D;
        	color: #2B2730;
          /*  background-color: rgba(255, 255, 255, 0.9);
           box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);  */ 
        }

    .navbar-brand {
           color: #2B2730;
        }

    .navbar-nav .nav-link {
            color: #2B2730;
        }
        
        .container {
      margin-top: 80px;
    }
    
    .container {
      margin-top: 80px;
    }
    .card {
      border: none;
      border-radius: 40px;
      box-shadow: 0 4px 7px rgba(0, 0, 0, 0.1);
      padding: 40px;
    }
    .card {
            margin-bottom: 20px;
        }
        
        .card-header {
            background-color: #ff6b6b;
            color: #fff;
        }
        h1{
        	color: #ff6b6b;
        }
        
        .card-title {
            margin-bottom: 0;
        }
    
    h1 {
      font-size: 32px;
      font-weight: bold;
      color: #ff6b6b;
      text-align: center;
    }
   
    .btn {
      background-color: #ff6b6b;
      border: none;
      color: #ffffff;
      padding: 8px 16px;
      border-radius: 5px;
      font-size: 14px;
      font-weight: 600;
      transition: background-color 0.3s ease;
    }
    .btn:hover {
      background-color: #ff4f4f;
    }
    
</style>

</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="#">OAS</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="/admin">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/admin/all">All Users</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/admin/messages">Messages</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/logout">logout</a>
            </li>
        </ul>
    </div>
</nav>

<!-- navbar ends -->

<!-- Appoinments -->

<div class="container">
        <div class="row">
            <div class="col-md-8 offset-md-2">
                <h1 class="text-center mb-4">My Appointments</h1>
                <div class="card border-success">
                    <div class="card-header">
                        <h4 class="card-title">Search Upcoming Appointments</h4>
                    </div>
                    <div class="card-body">
                    <form id="dateFilterForm" method="GET" action="/admin/byDate">
            			<div class="form-group">
                			<label for="dateFilter">Date Filter:</label>
                			<input type="date" class="form-control" id="dateFilter" pattern="\d{4}-\d{2}-\d{2}" name="date" value="${date}">
            			</div>
            			<button type="submit" class="btn btn-primary">Apply Filter</button>
        			</form>
                    </div>
                </div>
                <h5 class="text-center">${nope}</h5>
                <c:forEach var="e" items="${appoint }">
                <div class="card border-success">
                    <div class="card-header">
                        <h4 class="card-title">${e.name }(username : ${e.username})</h4>
                    </div>
                    <div class="card-body">
                    	<h5>${e.email }</h5>
                    	<p>Doctor : ${e.doctor }</p>
                        <p> Phone : ${e.mobile}</p>
                        <p> On : ${e.date }</p>
                        <p> At :${e.time } ${e.ampm }</p>
                    </div>
                </div>
                </c:forEach>
            </div>
        </div>
    </div>
<!--end -->



<!-- Footer -->
<footer class="text-center text-white" style="background-color: #f1f1f1;">
  <!-- Grid container -->
  <div class="container pt-4">
    <!-- Section: Social media -->
    <section class="mb-4">
      <!-- Facebook -->
      <a
        class="btn btn-link btn-floating btn-lg text-dark m-1"
        href="#!"
        role="button"
        data-mdb-ripple-color="dark"
        ><i class="fab fa-facebook-f"></i
      ></a>

      <!-- Twitter -->
      <a
        class="btn btn-link btn-floating btn-lg text-dark m-1"
        href="#!"
        role="button"
        data-mdb-ripple-color="dark"
        ><i class="fab fa-twitter"></i
      ></a>

      <!-- Google -->
      <a
        class="btn btn-link btn-floating btn-lg text-dark m-1"
        href="#!"
        role="button"
        data-mdb-ripple-color="dark"
        ><i class="fab fa-google"></i
      ></a>

      <!-- Instagram -->
      <a
        class="btn btn-link btn-floating btn-lg text-dark m-1"
        href="#!"
        role="button"
        data-mdb-ripple-color="dark"
        ><i class="fab fa-instagram"></i
      ></a>

      <!-- Linkedin -->
      <a
        class="btn btn-link btn-floating btn-lg text-dark m-1"
        href="#!"
        role="button"
        data-mdb-ripple-color="dark"
        ><i class="fab fa-linkedin"></i
      ></a>
      <!-- Github -->
      <a
        class="btn btn-link btn-floating btn-lg text-dark m-1"
        href="#!"
        role="button"
        data-mdb-ripple-color="dark"
        ><i class="fab fa-github"></i
      ></a>
    </section>
    <!-- Section: Social media -->
  </div>
  <!-- Grid container -->

  <!-- Copyright -->
  <div class="text-center text-dark p-3" style="background-color: rgba(0, 0, 0, 0.2);">
    © 2020 Copyright:
    <a class="text-dark" href="https://OAS.com/">OAS</a>
  </div>
  <!-- Copyright -->
</footer>

  <!-- Include Bootstrap and Font Awesome JS scripts -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>