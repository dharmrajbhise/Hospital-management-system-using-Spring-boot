<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>About Us</title>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <style>
    body {
       background-color: #606C5D;
    }
    
   .navbar {
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
    
    .about-section {
      background-color: #ffffff;
      padding: 80px 0;
    }
    .about-heading {
      color: #ff6b6b;
      font-size: 40px;
      font-weight: 700;
      margin-bottom: 40px;
    }
    .about-description {
      color: #343a40;
      font-size: 18px;
      line-height: 1.8;
    }
    .about-features {
      margin-top: 60px;
    }
    .feature-card {
      background-color: #ffffff;
      border: none;
      border-radius: 10px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
      padding: 40px;
      text-align: center;
    }
    .feature-icon {
      font-size: 40px;
      color: #ff6b6b;
      margin-bottom: 30px;
    }
    .feature-title {
      color: #343a40;
      font-size: 22px;
      font-weight: 600;
      margin-bottom: 20px;
    }
    .feature-description {
      color: #6c757d;
      font-size: 16px;
      line-height: 1.6;
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
        </ul>
    </div>
</nav>

<!-- navbar ends -->


  <section class="about-section">
    <div class="container">
      <div class="row">
        <div class="col-lg-8 offset-lg-2 text-center">
          <h2 class="about-heading">Welcome to Our Online Appointment System</h2>
          <p class="about-description">We are dedicated to simplifying and enhancing your appointment booking experience. Whether you're a healthcare provider, a service professional, or someone in need of booking appointments, we're here to streamline the process and make it convenient for everyone involved.</p>
        </div>
      </div>
      <div class="row justify-content-center about-features">
        <div class="col-lg-4 col-md-6">
          <div class="card feature-card">
            <i class="fas fa-calendar-alt feature-icon"></i>
            <h4 class="feature-title">Easy-to-Use Interface</h4>
            <p class="feature-description">Our website boasts a clean and intuitive interface, making it simple for both service providers and customers to navigate and book appointments without any hassle.</p>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="card feature-card">
            <i class="fas fa-clock feature-icon"></i>
            <h4 class="feature-title">Real-Time Availability</h4>
            <p class="feature-description">Our system provides real-time availability information, enabling customers to select the most suitable time slot based on the service provider's up-to-date schedule.</p>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="card feature-card">
            <i class="fas fa-mobile-alt feature-icon"></i>
            <h4 class="feature-title">User-Friendly Mobile App</h4>
            <p class="feature-description">Our mobile app extends the convenience of our appointment booking system to your fingertips. You can easily schedule appointments on the go.</p>
          </div>
        </div>
      </div>
    </div>
  </section>
  
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