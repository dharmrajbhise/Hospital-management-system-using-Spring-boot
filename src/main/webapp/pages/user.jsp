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
<title>Patient</title>

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
    .card {
      border: none;
      border-radius: 10px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
      padding: 40px;
    }
    h1 {
      font-size: 32px;
      font-weight: bold;
      color: #ff6b6b;
      text-align: center;
    }
    .form-group {
      margin-bottom: 20px;
    }
    .form-control {
      border-color: #ced4da;
    }
    .form-control:focus {
      border-color: #ff6b6b;
      box-shadow: 0 0 0 0.2rem rgba(255, 107, 107, 0.25);
    }
    .btn {
      background-color: #ff6b6b;
      border: none;
      color: #ffffff;
      padding: 12px 30px;
      border-radius: 5px;
      font-size: 16px;
      font-weight: 600;
      transition: background-color 0.3s ease;
    }
    .btn:hover {
      background-color: #ff4f4f;
    }
</style>

</head>
<body onload="showAlert()">

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
                <a class="nav-link" href="/user">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user/My-Appointment">My Appointments</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user/Certificates">Certificates</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user/Prescriptions">Prescriptions</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user/LabReports">Reports</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user/Receipts">Receipts</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user/ReferNote">Refer Note</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user/profile">Profile</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/logout">logout</a>
            </li>
        </ul>
    </div>
</nav>

<!-- navbar ends -->

<!-- Appoinment form -->

<div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-6 col-md-8">
        <div class="card">
          <h1>Welcome ${username} Schedule an Appointment</h1>
          <form id="appointmentForm" action="/user/appointment-sheduled" method="post">
            <div class="form-group">
              <label for="name">Your Name</label>
              <input type="text" value="${user.fullName }" name="name" class="form-control" id="name" required>
            </div>
            <div class="form-group">
              <label for="name">Username</label>
              <input type="text" value="${username }" name="username" class="form-control" id="name" required>
            </div>
            <div class="form-group">
              <label for="email">Your Email</label>
              <input type="email" class="form-control" name="email" value="${user.email }" id="email" required>
            </div>
            <div class="form-group">
                <label for="ampm">Select Doctor:</label>
                <select class="form-control" id="ampm" name="doctor" required>
                <c:forEach var="e" items="${usern}">
                    <option value="${e.fullName }">${e.fullName }</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
              <label for="mobile">Enter Mobile number with Country Code</label>
              <input type="number" class="form-control" name="mobile" id="email" required>
            </div>
            <div class="form-group">
              <label for="date">Appointment Date</label>
              <input type="date" class="form-control" pattern="\d{4}-\d{2}-\d{2}" name="date" id="date" required>
            </div>
            <div class="form-group">
              <label for="time">Appointment Time</label>
              <input type="time" id="timeInput" name="time" pattern="hh:mm:ss a" class="form-control" required/>
            </div>
            <div class="form-group">
                <label for="ampm">AM/PM:</label>
                <select class="form-control" id="ampm" name="ampm" required>
                    <option value="AM">AM</option>
                    <option value="PM">PM</option>
                </select>
            </div>
            <div class="text-center">
              <button type="submit" class="btn btn-primary">Schedule Appointment</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>

<!-- Form end -->



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

<script>
function showAlert() {
    var alertMessage = "${message}";
    if (alertMessage) {
        alert(alertMessage);
    }
}
</script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

  <!-- Include Bootstrap and Font Awesome JS scripts -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>