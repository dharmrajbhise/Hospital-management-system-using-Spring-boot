<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html>
    <head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>

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
                margin-top: 50px;
            }
    		.textarea-container {
            height: 400px; /* Set the desired height */
            overflow: auto; /* Enable scrolling */
        }


               .container {
          margin-top: 80px;
        }

        .container {
          margin-top: 30px;
        }
        .card {
          border: none;
          border-radius: 40px;
          box-shadow: 0 4px 7px rgba(0, 0, 0, 0.1);
          padding: 40px;
          margin-bottom: 20px;
        }

            .card-header {
                background-color: #ff6b6b;
                color: #fff;
            }
            .text-center{
            	color: #ff6b6b;
            }

            .card-title {
                margin-bottom: 0;
            }

            /* .btn {
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
        } */
        .profile-container {
                max-width: 600px;
                margin: 0 auto;
            }

            .profile-picture {
                width: 250px;
                height: 250px;
                border-radius: 50%;
                object-fit: cover;
                border: 8px solid #75C2F6;
                margin-bottom: 20px;
            }

            .profile-details {
                font-size: 18px;
                margin-bottom: 20px;
            }

            .profile-details label {
                font-weight: bold;
            }
            img{
            width: 190px;
            }
            .suggestions {
                position: absolute;
                width: 100%;
                background-color: #fff;
                border: 1px solid #ced4da;
                border-top: none;
                display: none;
                z-index: 9999;
            }

            .suggestions ul {
                list-style-type: none;
                margin: 0;
                padding: 0;
            }

            .suggestions option {
                padding: 5px 10px;
                cursor: pointer;
            }

            .suggestions option:hover {
            	color:#fff;
                background-color: #606C5D;
            }
            input{
            border-color: black;
            }
            .text-center{
            	color: black;
            	align-content: center;
            }
            .image-container {
                                display: flex;
                                justify-content: center;
                                align-items: center;
                                width: 200px;
                                height: 200px;
                                border-radius: 50%;
                                overflow: hidden;
                            }

                            .captured-image {
                                width: 100%;
                                height: 100%;
                                object-fit: cover;
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
                     <a class="nav-link" href="/user/addImage${user.id}">Add Profile Photo</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/logout">logout</a>
                </li>
            </ul>
        </div>
    </nav>
    <!-- nav end -->

    <!-- Filters -->

    <div class="container">
    	<div class="row">
    		<div class="col-md-6 offset-md-3">
    		<div class="text-center">
    		<c:choose>
              <c:when test="${not empty user.base64Image}">
                <img src="data:image/png;base64,${user.base64Image}" class="img-fluid rounded-start profile-picture" alt="captured image">
              </c:when>
              <c:otherwise>
                <img src="${pageContext.request.contextPath}/images/profile.png" class="img-fluid rounded-start" alt="Another Image">
              </c:otherwise>
            </c:choose>
    		<h2 class="text-center">${user.title } ${user.fullName }</h2>
    		<p>${user.email} | ${user.phone} | ${user.gender } | ${user.bloodGroup }</p>
    		<p>${user.address}</p>
    		</div>
    		</div>
    	</div>
    </div>

<!--cards--->
<div class="container">
    <div class="card">
      <div class="card-body">
        <div class="row">
            <div class="col-md-4">
                <h1 class="text-center">Total Visits</h1>
                <h1 class="text-center">${max}</h1>
            </div>
            <div class="col-md-4">
                <h1 class="text-center">Total Reports</h1>
                <h1 class="text-center">${maxReport}</h1>
            </div>
            <div class="col-md-4">
                <h1 class="text-center">Total References</h1>
                <h1 class="text-center">${maxRefer}</h1>
            </div>
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
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.min.js"></script>
</body>
</html>