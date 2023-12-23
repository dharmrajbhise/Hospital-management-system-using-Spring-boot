<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>OAS</title>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        /* Add your custom styles here */
        body {
            background:#6B3FA0;
            background-repeat: no-repeat;
            background-size: cover;
            /* height: 100vh; */
        }

        .navbar {
   			background-color: #606C5D;
        	color: #fff;
          /*  background-color: rgba(255, 255, 255, 0.9);
           box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);  */ 
        }

    .navbar-brand {
           color: #fff;
        }

    .navbar-nav .nav-link {
            color: #fff;
        }

        .container{
            margin-top:50px;
            align-item:center;
        }

        .login-page {
        			  margin-top: 30px;
        			  text-align: center;
        			  color: #fff;
        			  background-color: rgba(255, 255, 255, 0.2);
        			  padding: 30px;
        			  border-radius: 10px;
        			}

        			.login-page h1 {
        			  font-size: 36px;
        			  margin-bottom: 30px;
        			}

        			.form-label {
        			  color: #fff;
        			}

        			.form-control {
        			  border-color: #fff;
        			  color: #fff;
        			  background-color: transparent;
        			}

        			.btn-primary {
        			  background-color: #fff;
        			  color: #ff6e7f;
        			  border: none;
        			  padding: 10px 20px;
        			  font-size: 20px;
        			  transition: background-color 0.3s ease;
        			  margin-top: 20px;
        			}

        			.btn-primary:hover {
        			  background-color: #ff6e7f;
        			  color: #fff;
        			}



        .content {
            text-align: center;
            margin-top: 50px;
        }

        h1 {
            font-size: 3rem;
            color: #fff;
        }

        p {
            font-size: 18px;
            color: #fff;
        }

        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin-top: 20px;
            background-color: #428bca;
            color: #ffffff;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s;
        }

        .btn:hover {
            background-color: #3071a9;
        }
        
        @keyframes fadeIn {
    from {
      	opacity: 0;
    	}
    to {
      opacity: 1;
    	}
  	}

  .fadeIn {
    animation: fadeIn 5s;
  }
  footer{
  margin-top: 100px;
  }

    </style>
</head>
<body onload="showAlert()">

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="#">Online Appointment System </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/about">About</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/contact">Contact</a>
            </li>
        </ul>
    </div>
</nav>

<!-- navbar ends -->


    <div class="container">
    <div class="row">
    <div class="col-md-6 offset-md-3">
        <div class="content fadeIn">
            <h1>Welcome to the Online Hospital Management System</h1>
            <p>Please click the button below to schedule an appointment.</p>
            <a href="/user" class="btn btn-primary btn-lg">Patient Login</a>
            <a href="/admin" class="btn btn-primary btn-lg">Doctor Login</a>
            <a href="/medical" class="btn btn-primary btn-lg">Medical Login</a>
            <a href="/reception" class="btn btn-primary btn-lg">Reception Login</a>
        </div>
      </div>
      </div>
    </div>

<!--form-->
<div class="container">
<div class="login-page animated fadeIn">
<h2>Contact us by filling above form if you are a Doctor and Interested to join us</h2>
<form class="row g-3" action="/sendMessageToAdmin" method="post">
    <div class="col-md-6">
       <label for="FirstName" class="form-label">First Name</label>
       <input type="text" class="form-control" name="FirstName" id="FirstName" aria-describedby="emailHelp" required>
    </div>
    <div class="col-md-6">
       <label for="LastName" class="form-label">Last Name</label>
       <input type="text" class="form-control" name="LastName" id="LastName" aria-describedby="emailHelp" required>
    </div>
    <div class="col-md-6">
       <label for="hospital" class="form-label">Hospital Name</label>
       <input type="text" class="form-control" name="hospital" id="hospital" aria-describedby="emailHelp" required>
    </div>
    <div class="col-md-6">
       <label for="mobile" class="form-label">Mobile no.</label>
       <input type="text" class="form-control" name="mobile" id="mobile" aria-describedby="emailHelp" required>
    </div>
    <div class="col-md-12">
       <label for="hospitalAddress" class="form-label">Hospital Address</label>
       <textarea class="form-control" name="hospitalAddress" id="hospitalAddress" rows="3"></textarea>
    </div>
    <button type="submit" class="btn btn-success">Send Message</button>
</form>
</div>
<div>

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
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>    
