<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile</title>
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
        h1{
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
                <a class="nav-link" href="/admin">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/admin/all">All Users</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/admin/messages">Messages</a>
            </li>
            <li class="nav-item">
                 <a class="nav-link" href="/admin/addImage${user.id}">Add Profile Photo</a>
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
          <c:when test="${not empty profilePhoto}">
            <img src="data:image/png;base64,${profilePhoto}" class="img-fluid rounded-start profile-picture" alt="captured image">
          </c:when>
          <c:otherwise>
            <img src="${pageContext.request.contextPath}/images/profile.png" class="img-fluid rounded-start" alt="Another Image">
          </c:otherwise>
        </c:choose>
		<h2 class="text-center">${user.title } ${user.fullName }</h2>
		<p>${user.email} | ${user.phone} | ${user.gender } | ${user.bloodGroup }</p>
		</div>
		</div>
	</div>
</div>
<div class="text-center">
<a href="/admin/all/prescriptions/${user.username}" class="btn btn-outline-primary">Prescriptions</a>
<a href="/admin/all/labReports/${user.username}" class="btn btn-outline-secondary">Reports</a>
<a href="/admin/all/receipts/${user.username}" class="btn btn-outline-success">Receipts</a>
<a href="/admin/all/certificates/${user.username}" class="btn btn-outline-danger">Certifications</a>
<a href="/admin/all/referral/${user.username}" class="btn btn-outline-warning">References</a>
</div>
<div class="alert alert-primary container" role="alert">
  <marquee>Last visited on: ${recentDate}</marquee>
</div>
<!-- card -->

<div class="card text-center">
  <div class="card-header">
    <ul class="nav nav-tabs card-header-tabs">
      <li class="nav-item">
        <a class="nav-link text-center" aria-current="true" href="#about">Prescription Form</a>
      </li>
    </ul>
  </div>
  <div id="prescript" class="card-body">
   <div class="container">
          <form action="/admin/all/prescriptions/${user.username}" method="post">
            <div class="form-group">
                <input type="hidden" class="form-control" value="${user.fullName }" id="name" name="name" required>
            </div>
            <div class="form-group">
                <input type="hidden" class="form-control" value="${user.username }" id="username" name="username" required>
            </div>
            <div class="form-group">
                <input type="hidden" class="form-control" value="${user.address }" id="username" name="address" required>
            </div>
            <div class="form-group">
                <input type="text" class="form-control" id="date" value="${date}" name="date" required>
            </div>
            <div class="form-group">
                <input type="hidden" class="form-control" id="doctor" value="${doctor}" name="doctor" required>
            </div>
            <div id="getMedicines"></div>
            <div class="form-group textarea-container">
                <label for="medicines">Medicines:</label>
                <table id="medicinesTable" class=" table table-striped">
				  <thead>
				    <tr>
				      <th scope="col">Name</th>
				      <th scope="col">Dosage</th>
				      <th scope="col">Days</th>
				    </tr>
				  </thead>
				  <tbody id="medicinesTableBody">

				  </tbody>
				</table>
            </div>
            <div id="getComplaints"></div>
            <div id="getObservations"></div>
            <div class="form-group">
                <label for="instructions">Instructions:</label>
                <textarea class="form-control" id="instructions" name="instructions" rows="3" required></textarea>
            </div>
            <br>
            <button type="submit" class="btn btn-primary">Save</button>
        </form>
   </div>

<br>


   <button class="btn btn-primary" id="show" data-toggle="modal" data-target=".bd-example-modal-lg2">
  Add Medicines
</button>
<button class="btn btn-primary" id="show" data-toggle="modal" data-target="#exampleModal7">
  Add Complaint
</button>
<button class="btn btn-primary" id="show" data-toggle="modal" data-target="#exampleModal8">
  Add Observations
</button>
  </div>
  <div>
<button type="button" class="btn btn-outline-secondary" data-toggle="modal" data-target="#exampleModal3">Add Reports</button>
<button type="button" class="btn btn-outline-success" data-toggle="modal" data-target="#exampleModal4">Add Receipts</button>
<button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#exampleModal5">Add Certifications</button>
<button type="button" class="btn btn-outline-warning" data-toggle="modal" data-target="#exampleModal6">Add References</button>
  </div>
</div>


<!-- model2 -->
<div class="modal fade bd-example-modal-lg2" tabindex="-1" role="dialog" aria-labelledby="myLargeModal2Label" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
          <div class="row">
          	<div class="col-md-6">
              <form class="addMedicines" action="/admin/all/profile/${user.username}" method="post">
              <div class="input-group">
                <label>Medicine Name</label>
                <input type="text" class="form-control" name="mediname" value="${e.mediname}" placeholder="Enter Medicine Name" required>
              </div>
              <div class="input-group">
                <label>Strength</label>
                <input type="number" name="strength" placeholder="strength" value="${e.strength}">
              </div>
              <div class="input-group">
                <select class="select-group" name="unit" name="unit">
                  <option value="mg"  ${e.unit == 'mg' ? 'selected' : ''}>mg</option>
                  <option value="ml" ${e.unit == 'ml' ? 'selected' : ''}>ml</option>
                  <option value="g" ${e.unit == 'g' ? 'selected' : ''}>g</option>
                </select>
              </div>
              <div class="input-group">
                <label>Dosage</label>
                <select class="select-group" name="dosage">
                  <option value="After Lunch / जेवणानंतर / x--1--x" ${e.dosage == 'After Lunch / जेवणानंतर / x--1--x' ? 'selected' : ''}>AL</option>
                  <option value="After Breakfast/ नाष्टयानंतर / 1--x--x" ${e.dosage == 'After Breakfast/ नाष्टयानंतर / 1--x--x' ? 'selected' : ''}>AB</option>
                  <option value="After Dinner/ जेवणानंतर / x--x--1" ${e.dosage == 'After Dinner/ जेवणानंतर / x--x--1' ? 'selected' : ''}>AD</option>
                  <option value="After Lunch / जेवणानंतर / 1--x--x" ${e.dosage == 'After Lunch / जेवणानंतर / 1--x--x' ? 'selected' : ''}>ODAL</option>
		          <option value="Before Lunch / जेवणाआधी / 1--x--x" ${e.dosage == 'Before Lunch / जेवणाआधी / 1--x--x' ? 'selected' : ''}>ODBL</option>
		          <option value="Two Times / दोन वेळा / 1--x--1" ${e.dosage == 'Two Times / दोन वेळा / 1--x--1' ? 'selected' : ''}>BD</option>
		          <option value="After Lunch / जेवणानंतर / 1--x--1" ${e.dosage == 'After Lunch / जेवणानंतर / 1--x--1' ? 'selected' : ''}>BDAL</option>
		          <option value="Before Lunch / जेवणाआधी / 1--x--1" ${e.dosage == 'Before Lunch / जेवणाआधी / 1--x--1' ? 'selected' : ''}>BDBL</option>
		          <option value="Three Times / तिन वेळा / 1--1--1" ${e.dosage == 'Three Times / तिन वेळा / 1--1--1' ? 'selected' : ''}>TDS</option>
		          <option value="After Lunch / जेवणानंतर / 1--1--1" ${e.dosage == 'After Lunch / जेवणानंतर / 1--1--1' ? 'selected' : ''}>TDSAL</option>
		          <option value="Before Lunch / जेवणाआधी / 1--1--1" ${e.dosage == 'Before Lunch / जेवणाआधी / 1--1--1' ? 'selected' : ''}>TDSBL</option>
		          <option value="Four Times / चार वेळा / 1--1--1--1" ${e.dosage == 'Four Times / चार वेळा / 1--1--1--1' ? 'selected' : ''}>QID</option>
		          <option value="After Lunch / जेवणानंतर / 1--1--1--1" ${e.dosage == 'After Lunch / जेवणानंतर / 1--1--1--1' ? 'selected' : ''}>QIDAL</option>
		          <option value="Before Lunch / जेवणाआधी / 1--1--1--1" ${e.dosage == 'Before Lunch / जेवणाआधी / 1--1--1--1' ? 'selected' : ''}>QIDBL</option>
                </select>
                </div>
                <div class="input-group">
                <label>Days</label>
                <input type="number" class="form-control" name="days" placeholder="Days">
                </div>
                <div class="input-group">
                <label>Remark</label>
                <input type="text" name="remark" class="form-control" placeholder="Remark">
                </div>
                <button type="submit" class="btn btn-primary">+ADD</button>
              <form>
            </div>
              <div class="col-md-6 textarea-container">
			  <c:forEach var="e" items="${medicines}">
			    <div class="form-check">
			       <input type="checkbox" class="form-check-input" value="${e.mediname} || ${e.dosage} || ${e.days}" value="option1">
              <label class="form-check-label" for="inlineCheckbox1">${e.mediname}</label>
              <button type="button" id="selectMedicinesBtn" data-toggle="collapse" data-target="#editMedicineForm${e.id}">Edit</button>

              <div class="collapse" id="editMedicineForm${e.id}">
              <br>
              <h5>Edit Medicine</h5>
              <form class="editMedicineForm" action="/admin/all/update${e.id}/${user.username}" method="post">
                <label>Medicine Name</label>
                <input type="text" class="form-control" name="mediname" value="${e.mediname}">
                <br>
                <label>Strength</label>
                <input type="number" name="strength" value="${e.strength}">
                <select name="unit">
                  <option value="mg" ${e.unit == 'mg' ? 'selected' : ''}>mg</option>
                  <option value="ml" ${e.unit == 'ml' ? 'selected' : ''}>ml</option>
                  <option value="g" ${e.unit == 'g' ? 'selected' : ''}>g</option>
                </select>
                <br>
                <br>
                <label>Dosage</label>
                <select name="dosage">
                  <option value="After Lunch / जेवणानंतर / x--1--x" ${e.dosage == 'After Lunch / जेवणानंतर / x--1--x' ? 'selected' : ''}>AL</option>
                  <option value="After Dinner/ जेवणानंतर / x--x--1" ${e.dosage == 'After Dinner/ जेवणानंतर / x--x--1' ? 'selected' : ''}>AD</option>
                  <option value="After Breakfast/ नाष्टयानंतर / 1--x--x" ${e.dosage == 'After Breakfast/ नाष्टयानंतर / 1--x--x' ? 'selected' : ''}>AB</option>
                  <option value="After Lunch / जेवणानंतर / 1--x--x" ${e.dosage == 'After Lunch / जेवणानंतर / 1--x--x' ? 'selected' : ''}>ODAL</option>
		          <option value="Before Lunch / जेवणाआधी / 1--x--x" ${e.dosage == 'Before Lunch / जेवणाआधी / 1--x--x' ? 'selected' : ''}>ODBL</option>
		          <option value="Two Times / दोन वेळा / 1--x--1" ${e.dosage == 'Two Times / दोन वेळा / 1--x--1' ? 'selected' : ''}>BD</option>
		          <option value="After Lunch / जेवणानंतर / 1--x--1" ${e.dosage == 'After Lunch / जेवणानंतर / 1--x--1' ? 'selected' : ''}>BDAL</option>
		          <option value="Before Lunch / जेवणाआधी / 1--x--1" ${e.dosage == 'Before Lunch / जेवणाआधी / 1--x--1' ? 'selected' : ''}>BDBL</option>
		          <option value="Three Times / तिन वेळा / 1--1--1" ${e.dosage == 'Three Times / तिन वेळा / 1--1--1' ? 'selected' : ''}>TDS</option>
		          <option value="After Lunch / जेवणानंतर / 1--1--1" ${e.dosage == 'After Lunch / जेवणानंतर / 1--1--1' ? 'selected' : ''}>TDSAL</option>
		          <option value="Before Lunch / जेवणाआधी / 1--1--1" ${e.dosage == 'Before Lunch / जेवणाआधी / 1--1--1' ? 'selected' : ''}>TDSBL</option>
		          <option value="Four Times / चार वेळा / 1--1--1--1" ${e.dosage == 'Four Times / चार वेळा / 1--1--1--1' ? 'selected' : ''}>QID</option>
		          <option value="After Lunch / जेवणानंतर / 1--1--1--1" ${e.dosage == 'After Lunch / जेवणानंतर / 1--1--1--1' ? 'selected' : ''}>QIDAL</option>
		          <option value="Before Lunch / जेवणाआधी / 1--1--1--1" ${e.dosage == 'Before Lunch / जेवणाआधी / 1--1--1--1' ? 'selected' : ''}>QIDBL</option>
                </select>
                <br>
                <label>Days</label>
                <input type="number" name="days" value="${e.days}">
                <br>
                <label>Remark</label>
                <input type="text" name="remark" class="form-control" value="${e.remark}">
                <br>
                <button type="submit" class="btn btn-primary">Update</button>
              </form>
              </div>

			    </div>
			  </c:forEach>
			  <button type="button" class="getValues" id="saveMedicinesBtn" class="btn btn-primary">Select</button>

			</div>
              <br>


              </div>
              </div>
            </div>
      </div>
    </div>


<!-- Modal3 -->
<div class="modal fade" id="exampleModal3" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel3" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Add Reports</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="post" action="/admin/all/labReports/add${user.username }">
           <div class="form-group">
				<input type="hidden" class="form-control" id="username" value="${user.username }"  name="username" required>
		   </div>
			<div class="form-group">
			    <label for="ampm">Select Report Type:</label>
			     <select class="form-control" id="ampm" name="type" required>
			        <option value="Admission Report">Admission Report</option>
			        <option value="Discharge Summary">Discharge Summary</option>
			        <option value="Operative Report">Operative Report</option>
			        <option value="Pathology Report">Pathology Report</option>
			        <option value="Radiology Report">Radiology Report</option>
			        <option value="Laboratory Test Report">Laboratory Test Report</option>
			        <option value="Progress Notes">Progress Notes</option>
			        <option value="Consultation Report">Consultation Report</option>
			        <option value="Nursing Notes">Nursing Notes</option>
			        <option value="Anesthesia Report">Anesthesia Report</option>
			        <option value="Medication Administration Record (MAR)">Medication Administration Record (MAR)</option>
			        <option value="Therapy Notes">Therapy Notes</option>
			     </select>
		    </div>
            	<div class="form-group">
					<label for="formFile" class="form-label">Select Lab report file</label>
					<input class="form-control" type="file" id="formFile" name="file" accept=".pdf,.doc,.docx,.txt">
				</div>
            	<button type="submit" class="btn btn-primary">Add Report</button>
        </form>
      </div>
    </div>
  </div>
</div>


<!-- Modal4 -->
<div class="modal fade" id="exampleModal4" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Add Receipt</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">

       <form id="dateFilterForm" method="Post" action="/admin/all/receipts/${user.username}" enctype="multipart/form-data">
          <div class="form-group">
			<input type="hidden" class="form-control" id="name" value="${user.fullName }"  name="name" required>
		  </div>
          <div class="form-group">
			<input type="hidden" class="form-control" id="username" value="${user.username }"  name="username" required>
		  </div>
          <div class="form-group">
			<input type="hidden" class="form-control" id="username" value="${doc.hospital }"  name="hospital" required>
		  </div>
          <div class="form-group">
			<input type="hidden" class="form-control" value="${user.address }" id="username"  name="address" required>
			</div>
		  <div class="form-group">
			<input type="hidden" class="form-control" value="${date }" id="date" name="date" required>
		  </div>
		   <div class="form-group">
			<input type="hidden" class="form-control" id="username" value="${doctor }"  name="doctor" required>
		   </div>
            <div class="form-group textarea-container">
            <div id="getServices"></div>
				<label for="address">Services:</label>
				  <table id="servicesTable" class=" table table-striped">
				  <thead>
				    <tr>
				      <th scope="col">Services</th>
				      <th scope="col">Price</th>
				    </tr>
				  </thead>
				  <tbody id="servicesTableBody">
				   		<!-- <tr id="selectedServiceRow">
						    <td id="selectedService"></td>
						    <td id="selectedPrice"></td>
						</tr> -->
				  </tbody>
				</table>
			</div>
            <button type="submit" class="btn btn-primary">Add Receipt</button>

      <a  class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">Add Service</a>
      </form>
      <div class="collapse" id="collapseExample">
  		<div class="card card-body">
    		<div class="form-group">
			    <label for="ampm">Select Service Type:</label>
			     <select class="form-control" id="service" name="service" required>
			        <option value="Consultation Fee">Consultation Fee</option>
			        <option value="Procedure Fee">Procedure Fee</option>
			        <option value="Hospital Room Charges">Hospital Room Charges</option>
			        <option value="Laboratory Charges">Laboratory Charges</option>
			        <option value="Medication Charges">Medication Charges</option>
			        <option value="Surgical Charges">Surgical Charges</option>
			        <option value="Anesthesia Charges">Anesthesia Charges</option>
			        <option value="Imaging Charges">Imaging Charges</option>
			        <option value="Pathology Charges">Pathology Charges</option>
			        <option value="Emergency Room Charges">Emergency Room Charges</option>
			        <option value="Ambulance Charges">Ambulance Charges</option>
			        <option value="Physical Therapy Charges">Physical Therapy Charges</option>
			        <option value="Specialty Care Charges">Specialty Care Charges</option>
			        <option value="Miscellaneous Charges">Miscellaneous Charges</option>
			       </select>
			   </div>

			   <div class="form-group">
			   <label for="price">Enter Price :</label>
				<input type="number" class="form-control" id="price"  name="price" required>
		  		</div>

		  		<button type="button" class="getValues" id="saveServicesBtn" class="btn btn-primary">Select</button>

  		</div>
	  </div>

      </div>
    </div>
  </div>
</div>


<!-- Modal5 -->
<div class="modal fade" id="exampleModal5" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel5" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">

       <form action="/admin/all/certificates/${user.username}" method="POST">
		<div class="form-group">
			<input type="hidden" class="form-control" id="name" value="${user.fullName }" name="patientName" required>
		</div>

		<div class="form-group">
			<input type="hidden" class="form-control" id="username" value="${user.username }" name="username" required>
		</div>
		<div class="form-group">
			<input type="hidden" class="form-control" id="username" value="${doctor }" name="doctor" required>
		</div>
		<div class="form-group">
			<label for="autocomplete-select">Select Certificate type</label>
			<select id="autocomplete-select" name="certificateName" class="form-control">
				<option value="Birth Certificate">Birth Certificate</option>
				<option value="Death Certificate">Death Certificate</option>
				<option value="Medical Certificate">Medical Certificate</option>
				<option value="Surgery Certificate">Surgery Certificate</option>
				<option value="Discharge Certificate">Discharge Certificate</option>
				<option value="Immunization Certificate">Immunization Certificate</option>
				<option value="Medical Fitness Certificate">Medical Fitness Certificate</option>
				<option value="Organ Donor Card">Organ Donor Card</option>
			</select>
		</div>


		<div class="form-group">
			<input type="hidden" class="form-control" value="${date }" id="date" name="issueDate" required>
		</div>

		<button type="submit" class="btn btn-primary">Submit</button>
	</form>

      </div>
    </div>
  </div>
</div>


<!-- Modal6 -->
<div class="modal fade" id="exampleModal6" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel6" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Referring to :</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">

        <form action="/admin/all/referral/${user.username}}" method="POST">
			<div class="form-group">
				<input type="hidden" class="form-control" id="name" value="${user.fullName }" name="patientName" required>
			</div>

			<div class="form-group">
				<input type="hidden" class="form-control" id="username" value="${user.username }" name="username" required>
			</div>

			<div class="form-group">
				 <input type="hidden" class="form-control" id="username" value="${doctor }"  name="doctor" required>
			</div>

			<div class="form-group">
				<input type="hidden" class="form-control" id="username" value="${doc.hospital }"  name="hospital" required>
			</div>

			<div class="form-group">
				 <input type="hidden" class="form-control" value="${date }" id="date" name="date" required>
			</div>

			<div class="form-group">
				  <label for="name">Recipient Name:</label>
				  <input type="text" class="form-control" id="name" name="recipientName" required>
			</div>

			<div class="form-group">
				   <label for="name">Recipient phone:</label>
				   <input type="text" class="form-control" id="name" name="recipientContact" required>
			</div>

			<div class="form-group">
				   <label for="address">Recipient Address:</label>
				   <textarea class="form-control" id="instructions" name="recipientAddress" rows="4" required></textarea>
			</div>

			<div class="form-group">
				 <label for="ampm">Select Subject:</label>
				 <select class="form-control" id="ampm" name="subject" required>
				       <option value="Doctor reference letter">Doctor reference letter</option>
				       <option value="Investigation letter">Investigation letter</option>
				 </select>
			</div>

			<button type="submit" class="btn btn-primary">Submit</button>
		</form>

      </div>
    </div>
  </div>
</div>


<!-- Modal7 -->
<div class="modal fade" id="exampleModal7" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel7" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Add Complaints</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
            <div class="form-group textarea-container">
            <div id="getComplaints"></div>
				<label for="address">Complaints:</label>
				  <table id="servicesTable" class=" table table-striped">
				  <thead>
				    <tr>
				      <th scope="col">Complaint</th>
				      <th scope="col">From Last Days</th>
				    </tr>
				  </thead>
				  <tbody id="complaintsTableBody">
				   		<!-- <tr id="selectedServiceRow">
						    <td id="selectedService"></td>
						    <td id="selectedPrice"></td>
						</tr> -->
				  </tbody>
				</table>
			</div>

      <a  class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">Add Complaint</a>

      <div class="collapse" id="collapseExample">
  		<div class="card card-body">
    		<div class="form-group">
			    <label for="ampm">Enter Complaint:</label>
			     <textarea class="form-control" name="complaint" id="complaint" rows="2"></textarea>
			   </div>

			   <div class="form-group">
			   <label for="price">Complaint From Days :</label>
				<input type="number" class="form-control" id="days"  name="price" required>
		  		</div>

		  		<button type="button" class="getValues" id="saveComplaintsBtn" class="btn btn-primary">Select</button>

  		</div>
	  </div>

      </div>
    </div>
  </div>
</div>


<!-- Modal8 -->
<div class="modal fade" id="exampleModal8" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel8" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Add Observations</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
            <div class="form-group textarea-container">
				<label for="address">Observations:</label>
				  <table id="servicesTable" class="table table-striped">
				  <thead>
				    <tr>
				      <th scope="col">Observation</th>
				      <th scope="col">Reading</th>
				    </tr>
				  </thead>
				  <tbody id="observationsTableBody">
				   		<!-- <tr id="selectedServiceRow">
						    <td id="selectedService"></td>
						    <td id="selectedPrice"></td>
						</tr> -->
				  </tbody>
				</table>
			</div>

      <a  class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">Add Observation</a>
      </form>
      <div class="collapse" id="collapseExample">
  		<div class="card card-body">
    		<div class="form-group">
			    <label for="ampm">Observation Type:</label>
			    <textarea class="form-control" name="observation" id="observation" rows="2"></textarea>
			   </div>

			   <div class="form-group">
			   <label for="price">Observation Reading :</label>
				<input type="number" class="form-control" id="reading"  name="price" required>
		  		</div>

		  		<button type="button" class="getValues" id="saveObservationBtn" class="btn btn-primary">Select</button>
  		</div>
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

<script>
function showAlert() {
    var alertMessage = "${message}";
    if (alertMessage) {
        alert(alertMessage);
    }
}
</script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
document.getElementById("selectMedicinesBtn").addEventListener("click", function() {
	  var checkboxes = document.querySelectorAll(".form-check-input:checked");
	  var selectedValues = [];

	  checkboxes.forEach(function(checkbox) {
	    var value = checkbox.value;
	    selectedValues.push(value);
	  });

	  console.log(selectedValues); // You can modify this line to perform any desired action with the selected values
	});


document.querySelectorAll('.editMedicineForm').forEach(function(form) {
    form.addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        var formData = new FormData(form); // Serialize the form data

        // Make an AJAX request to update the medicine
        fetch(form.action, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData))
        })
        .then(function(response) {
            if (response.ok) {
                console.log('Medicine updated successfully');
                location.reload();
            } else {
                console.error('Failed to update medicine');
                // Perform any desired actions on failed update
            }
        })
        .catch(function(error) {
            console.error('Error occurred during medicine update:', error);
        });
    });
});

</script>



<script src="/js/script.js"></script>

      <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.min.js"></script>
</body>
</html>