<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.Base64" %>
    <%@ page import="java.nio.charset.StandardCharsets" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Management</title>
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
        .card-body{
            line-height: 0.9;
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

        .profile-picture {
                    width: 250px;
                    height: 250px;
                    border-radius: 50%;
                    object-fit: cover;
                    border: 8px solid #75C2F6;
                    margin-bottom: 20px;
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
        .textarea-container {
        overflow: auto; /* Enable scrolling */
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
<!-- nav end -->
<!-- Filters -->

<div class="container">
	<div class="row">
		<div class="col-md-8 offset-md-2">
		<div class="container">
        <h1 class="mt-5 mb-3 text-center">Search By Patient Name</h1>
        <form action="/admin/all/searchByName" method="post" autocomplete="off">
        <div class="form-group position-relative">
            <input type="text" id="searchField" autocomplete="off" class="form-control" name="fullName" placeholder="Search...">
            <div id="suggestionsDiv" class="suggestions textarea-container"></div>
        </div>
         <button type="submit" class="ktn btn-primary">Search</button>
         <a href="/admin/addPatient" class="ktn btn-primary">+ Add Patient</a>
         </form>
    	</div>
		</div>
	</div>
</div>

<h5 class="text-center">${nope}</h5>
<!-- filters end -->
<div class="container">
<nav aria-label="Page navigation">
            <ul class="pagination">
                <c:forEach begin="1" end="${totalPages}" var="pageNumber">
                    <li class="page-item ${pageNumber == currentPage ? 'active' : ''}">
                        <a class="page-link" href="?page=${pageNumber}">${pageNumber}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>

       <h1 class="text-center">Patients</h1>
       	<c:forEach var="e" items="${products }" varStatus="status">
               <div class="card mb-3" style="max-width: 1040px;">
         <div class="row g-0">
           <div class="col-md-4">
           <c:choose>
           <c:when test="${not empty e.base64Image}">
             <img src="data:image/png;base64,${e.base64Image}" class="img-fluid rounded-start profile-picture" alt="...">
             </c:when>
             <c:otherwise>
                     <img src="${pageContext.request.contextPath}/images/profile.png" class="img-fluid rounded-start" alt="Another Image">
                 </c:otherwise>
             </c:choose>
           </div>
           <div class="col-md-8">
             <div class="card-body">
       	<a id="anchor" href="/admin/all/profile${e.username }">
               <h5 class="card-title">${e.title} ${e.fullName }</h5>
               <p class="card-text">Email : ${e.email }</p>
               <p class="card-text">phone : ${e.countrycode} ${e.phone }</p>
               <p class="card-text">Gender : ${e.gender }</p>
               <p class="card-text">Blood Group : ${e.bloodGroup }</p>
               <p class="card-text">Birth Date : ${e.birth_date }  Age:${e.age }</p>
               </a><br>
              <a href="/admin/all/HealthChart/${e.username }" class="btn btn-outline-success">Health Chart</a>
              <a href="/admin/all/History/${e.username }" class="btn btn-outline-success">History</a>
              <small class="text-muted">Added By ${e.addedby}</small>
             </div>
           </div>
         </div>
       </div>

</c:forEach>
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
<script>
$(function() {
    var searchField = $('#searchField');
    var suggestionsDiv = $('#suggestionsDiv');

    searchField.on('input', function() {
        var searchText = $(this).val();
        fetchSuggestions(searchText);
    });

    searchField.focus(function() {
        var searchText = $(this).val();
        fetchSuggestions(searchText);
    });

    $(document).on('click', '.suggestion', function() {
        var suggestion = $(this).text();
        searchField.val(suggestion);
        suggestionsDiv.hide();
    });

    $(document).on('click', function(event) {
        if (!$(event.target).closest('#searchField').length) {
            suggestionsDiv.hide();
        }
    });

    function fetchSuggestions(searchText) {
        $.ajax({
            type: 'GET',
            url: 'autocomplete',
            data: {term: searchText},
            success: function(data) {
                suggestionsDiv.empty();
                if (data.length > 0) {
                    $.each(data, function(index, item) {
                        var suggestionItem = '<option class="suggestion form-control"><a href="#" class="suggestion-link">' + item + '</a></option>';
                        suggestionsDiv.append(suggestionItem);
                    });
                    suggestionsDiv.show();
                } else {
                    suggestionsDiv.hide();
                }
            }
        });
    }
});
</script>
<script>
function showAlert() {
    var alertMessage = "${message}";
    if (alertMessage) {
        alert(alertMessage);
    }
}
</script>



    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</body>
</html>