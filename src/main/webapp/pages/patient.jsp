<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register Page</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
    <!-- Bootstrap CSS -->
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background:#6B3FA0;
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
			.nav-link:hover{
			 color: #fff;
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
                <a class="nav-link" href="/reception">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/reception/messages">Messages</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/contact">Contact</a>
            </li>
        </ul>
    </div>
</nav>

<!-- navbar ends -->

<div class="container">
        <div class="login-page animated fadeIn">
          <h1>Add Patient</h1>
          <form class="row g-3" action="/reception/addPatient" method="post">
          <div class="col-md-6">
              <label for="title" class="form-label">Title</label>
               <select class="form-select form-control" name="title">
              <option>MR.</option>
              <option>MRS.</option>
              <option>Dr.</option>
              <option>MS.</option>
              <option>Miss.</option>
              <option>Master.</option>
              <option>Baby.</option>
              </select>
            </div>
            <div class="col-md-6">
              <label for="full_name" class="form-label">Full Name</label>
              <input type="text" class="form-control" name="fullName" id="fullName" aria-describedby="emailHelp" required>
            </div>
            <div class="col-md-6">
              <label for="gender" class="form-label">Gender</label>
              <select class="form-select form-control" name="gender">
              <option>Male</option>
              <option>Female</option>
              <option>Do not want to disclose</option>
              </select>
            </div>
            <div class="col-md-6">
            <input type="hidden" name="username" value="${username}">
            </div>
            <div class="col-md-6">
              <label for="email" class="form-label">Email</label>
              <input type="email" class="form-control" name="email" id="email" aria-describedby="emailHelp" required>
            </div>
            <div class="form-group col-md-6">
                			<label for="bloodGroup">Blood Group:</label>
                		<select class="form-control" id="ampm" name="bloodGroup" required>
                    		<option value="O+">O+</option>
                    		<option value="O-">O-</option>
                    		<option value="A+">A+</option>
                    		<option value="A-">A-</option>
                    		<option value="B+">B+</option>
                    		<option value="B-">B-</option>
                    		<option value="AB+">AB+</option>
                    		<option value="AB-">AB-</option>
                		</select>
                		</div>
            <div class="form-group col-12">
    			<label for="address">Address</label>
   				 <textarea class="form-control" name="address" id="exampleFormControlTextarea1" rows="3" cols="10"></textarea>
  			</div>

  			<div class="form-group col-md-6">
                			<label for="countrycode">Country Code:</label>
                		<select class="form-control" id="ampm" name="countrycode" required>
                    		<option value="+91">India(+91)</option>
                    		<option value="+93">Afganistan(+93)</option>
                    		<option value="+61">Australia(+61)</option>
                    		<option value="+32">Belgium(+32)</option>
                    		<option value="+60">Malaysia(+60)</option>
                    		<option value="+52">Mexico(+52)</option>
                    		<option value="+92">Pakistan(+92)</option>
                    		<option value="+94">Sri Lanka(+94)</option>
                		</select>
                		</div>

            <div class="col-md-6">
              <label for="phone" class="form-label">Mobile</label>
              <input type="number" class="form-control" name="phone" id="email" aria-describedby="emailHelp" required>
            </div>
            <div class="col-md-6">
              <label for="Age" class="form-label">Age</label>
              <input type="number" class="form-control" name="age" id="email" aria-describedby="emailHelp" required>
            </div>
            <div class="col-md-6">
              <label for="date" class="form-label">Date of Birth</label>
              <input type="date" class="form-control" name="birth_date" id="email" aria-describedby="emailHelp">
            </div>
            <div class="col-md-6">
              <input type="hidden" class="form-control" name="password" value="${password}" id="password" required>
            </div>
            <div class="col-md-6">
              <input type="hidden" class="form-control" name="role.name" value="PATIENT" id="email" aria-describedby="emailHelp" required>
            </div>
            <button type="submit" class="btn btn-primary">Register</button>
          </form>
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