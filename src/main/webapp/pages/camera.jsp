<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css" rel="stylesheet">
<title>Webcam Capture</title>
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
<!-- nav end -->

<div class="container">
    <div class="row">
        <div class="col-md-6">
        <div class="embed-responsive embed-responsive-4by3">
        <video class="embed-responsive-item" id="videoElement" autoplay></video>
        </div>
        <center><button class="btn btn-success" onclick="captureImage()">Capture</button></center>
        </div>
        <div class="col-md-6">
        <canvas id="canvasElement" style="display: none;"></canvas>
        <h3>Captured image</h3>
        <div class="image-container">
        <img id="capturedImage" class="captured-image" alt="Captured Image">
        </div>
        <input type="hidden" id="getId" value="${id}">
        </div>

    </div>

</div>







</body>
<script>

const video = document.getElementById('videoElement');
    const canvas = document.getElementById('canvasElement');
    const capturedImage = document.getElementById('capturedImage');

    navigator.mediaDevices.getUserMedia({ video: true })
        .then(stream => {
            video.srcObject = stream;
            video.play();
        })
        .catch(error => {
            console.error('Error accessing webcam:', error);
        });

    function captureImage() {
        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;
        canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height);

        const imageSrc = canvas.toDataURL('image/png');
        capturedImage.src = imageSrc;
        capturedImage.style.display = 'block';

        // Send image to the server using AJAX or Fetch
        // Example code for sending image using Fetch:

        var patientId = document.getElementById("getId").value;
        const url = "/admin/saveImage?patientId="+patientId;
        console.log(patientId,url);
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ image: imageSrc })
        })
        .then(response => {

        console.log('Image saved successfully');
        alert('Image saved successfully');

        })
        .catch(error => {
            console.error('Error saving image:', error);
            alert('Error saving image');
        });
    }


</script>
</html>