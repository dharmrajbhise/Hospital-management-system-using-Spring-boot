
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
        fetch('/saveImage{id}', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ image: imageSrc })
        })
        .then(response => {
             if (response.ok) {
                        console.log('Image saved successfully');
                        alert('Image saved successfully');
                    } else {
                        alert('Failed to save image');
                    }
        })
        .catch(error => {
            console.error('Error saving image:', error);
            alert('Error saving image');
        });
    }
