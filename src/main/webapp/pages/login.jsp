<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
    <!-- Bootstrap CSS -->
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background:#6B3FA0;
            background-repeat: no-repeat;
            background-size: cover;
            height: 100vh;
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

         .login-page {
			  margin-top: 100px;
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
			.nav-link:hover{
			 color: #FFBDF7;
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
  margin-top: 260px;
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
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/about">About</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/contact">Contact</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/register">Sign Up</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/admin">Doctor Login</a>
            </li>
        </ul>
    </div>
</nav>

<!-- navbar ends -->


<div class="container">
    <div class="row">
      <div class="col-lg-6 col-md-8">
        <div class="login-page animated fadeIn">
          <h1>Login</h1>
          	<c:if test="${not empty errorMessage}">
        		<div class="alert alert-danger" role="alert">${errorMessage}</div>
    		</c:if>
          <form action="/login" method="post">
            <div class="form-group">
              <label for="username" class="form-label">Username</label>
              <input type="text" class="form-control" name="username" id="email" aria-describedby="emailHelp" required>
            </div>
            <div class="form-group">
              <label for="password" class="form-label">Password</label>
              <input type="password" class="form-control" name="password" id="password" required>
            </div>
            <button type="submit" class="btn btn-primary">Login</button>
          <div class="g-signin2" data-onsuccess="onSignIn"></div>
          </form>
        </div>
      </div>
    </div>
  </div>

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


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
