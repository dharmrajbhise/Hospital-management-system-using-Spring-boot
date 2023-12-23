<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Medical</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
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

        .table {
            background-color: #fff;
        }

        .table-header {
            background-color: #ff6b6b;
            color: #fff;
        }

        .table-header th {
            border-color: #ff6b6b;
        }

        .table-row:nth-child(even) {
            background-color: #f2f2f2;
        }

        .table-row:hover {
            background-color: #e0e0e0;
            cursor: pointer;
        }

           .container {
      margin-top: 80px;
    }

    .container {
      margin-top: 20px;
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
        h1{
        	color: #ff6b6b;
        }

        .card-title {
            margin-bottom: 0;
            font-size: 2rem;
        }

        .ktn {
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

        #anchor{
        	text-decoration: none;
        	color: black;
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
                <a class="nav-link" href="/medical">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/contact">Contact</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/logout">Logout</a>
            </li>
        </ul>
    </div>
</nav>

<!-- navbar ends -->



<div class="container">
    <div class="row">
    <h1>Prescriptions of ${date }</h1>
      <table class="table table-responsive mt-4">
            <thead class="table-header">
                <tr>
                    <th>Name</th>
                    <th>Username</th>
                    <th>Address</th>
                    <th>Date</th>
                    <th>Medicine</th>
                    <th>Instruction</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="e" items="${prescript}">
                <tr class="table-row">
                    <td>${e.name }</td>
                    <td>${e.username }</td>
                    <td>${e.address }</td>
                    <td>${e.date }</td>
                    <td>${e.medicines }</td>
                    <td>${e.instructions }</td>
                    <td>
                    <a href="/medical/prescriptions/download/${e.id}" target="_blank" class="btn btn-success">Print</a>
                    <br><br>
                    <a href="/medical/prescriptions/SendEmail/${e.id}" class="btn btn-success">Email</a>
                    <br><br>
                    <a href="https://wa.me/${country}${phone}" class="btn btn-success">Whatsapp</a>
                    </td>
                </tr>
               </c:forEach>
                <!-- Add more rows dynamically or retrieve data from backend -->
            </tbody>
        </table>
      </div>
    </div>





<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>