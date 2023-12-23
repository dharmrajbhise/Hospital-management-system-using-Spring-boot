
  // JavaScript to handle the modal and populate the textarea
			    document.getElementById('saveMedicinesBtn').addEventListener('click', function() {
					  var selectedMedicines = [];
					  var checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
					  checkboxes.forEach(function(checkbox) {
					    selectedMedicines.push(checkbox.value);
					  });



					  var tableBody = document.getElementById('medicinesTableBody');
					  tableBody.innerHTML = ''; // Clear previous table rows

					  selectedMedicines.forEach(function(medicineData) {
					    var medicineParts = medicineData.split(' || ');
					console.log(medicineParts);

					    var medicine = medicineParts[0];
					    var dosage = medicineParts[1];

					    var days = medicineParts[2];

					    console.log(medicine);
					    console.log(dosage);
					    console.log(days);

					    var medicineInput = document.createElement('input');
					    medicineInput.type = 'hidden';
					    medicineInput.value = `${medicine} || ${dosage} || ${days}`;

					    var row = document.createElement('tr');
					    var medicineCell = document.createElement('td');
					    medicineCell.textContent = medicine;
					    row.appendChild(medicineCell);

					    var dosageCell = document.createElement('td');
					    dosageCell.textContent = dosage;
					    row.appendChild(dosageCell);



					    var daysCell = document.createElement('td');
					    daysCell.textContent = days;
					    row.appendChild(daysCell);

					    row.appendChild(medicineInput);

					    tableBody.appendChild(row);
					  });
					});




        var elements = document.getElementsByClassName('getValues');
        for (var i = 0; i < elements.length; i++) {
          elements[i].addEventListener('click', function() {
            var selectedMedicines = [];
            var checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
            checkboxes.forEach(function(checkbox) {
              selectedMedicines.push(checkbox.value);
            });

            var divBody = document.getElementById('getMedicines');
            divBody.innerHTML = ''; // Clear previous table rows

            selectedMedicines.forEach(function(medicineData) {
              var medicineParts = medicineData.split('||');

              var medicine = medicineParts[0];
              var dosage = medicineParts[1];
              var days = medicineParts[2];

              var medicineInput = document.createElement('input');
              console.log(medicine);
              medicineInput.type = 'hidden';
              medicineInput.name = 'medicines';
              medicineInput.value = `${medicine} || ${dosage} || ${days},`;

              divBody.appendChild(medicineInput);
             });
          });
        }



         document.getElementById("saveServicesBtn").addEventListener("click", function() {
        var selectedService = document.getElementById("service").value;
        var selectedPrice = document.getElementById("price").value;

        var selectedServices = [];

        var existingServices = document.querySelectorAll('#servicesTableBody tr');
        for (var i = 0; i < existingServices.length; i++) {
            var service = existingServices[i].querySelector('td:first-child').textContent;
            var price = existingServices[i].querySelector('td:nth-child(2)').textContent;
            selectedServices.push(service + "," + price);
        }

        console.log(selectedServices);

        selectedServices.push(selectedService + "," + selectedPrice);

        var tableBody = document.getElementById('servicesTableBody');
        tableBody.innerHTML = '';

        selectedServices.forEach(function(serviceData) {
            var selectedParts = serviceData.split(",");
            var service = selectedParts[0].trim();
            var price = selectedParts[1].trim();

            var row = document.createElement('tr');
            var serviceCell = document.createElement('td');
            serviceCell.textContent = service;
            row.appendChild(serviceCell);

            var priceCell = document.createElement('td');
            priceCell.textContent = price;
            row.appendChild(priceCell);

            tableBody.appendChild(row);
        });
    });

    var elements = document.getElementsByClassName('getValues');
    for (var i = 0; i < elements.length; i++) {
      elements[i].addEventListener('click', function() {
        var selectedMedicines = [];
        var existingServices = document.querySelectorAll('#servicesTableBody tr');
        for (var i = 0; i < existingServices.length; i++) {
          var service = existingServices[i].querySelector('td:first-child').textContent;
          var price = existingServices[i].querySelector('td:nth-child(2)').textContent;
          selectedMedicines.push(service + "," + price);
        }

        var divBody = document.getElementById('getServices');
        divBody.innerHTML = ''; // Clear previous table rows

        selectedMedicines.forEach(function(medicineData) {
          var selectedParts = medicineData.split(',');

          var service = selectedParts[0];
          var price = selectedParts[1];

          var medicineInput = document.createElement('input');
          medicineInput.type = 'hidden';
          medicineInput.name = 'services';
          medicineInput.value = `${service}||${price},`;

          divBody.appendChild(medicineInput);
        });
      });
    }


    document.getElementById("saveComplaintsBtn").addEventListener("click", function() {
            var selectedComplaints = document.getElementById("complaint").value;
            var selectedDays = document.getElementById("days").value;

            var selectedComplaint = [];

            var existingServices = document.querySelectorAll('#complaintsTableBody tr');
            for (var i = 0; i < existingServices.length; i++) {
                var complaint = existingServices[i].querySelector('td:first-child').textContent;
                var days = existingServices[i].querySelector('td:nth-child(2)').textContent;
                selectedComplaint.push(complaint + "," + days);
            }

            console.log(selectedComplaint);

            selectedComplaint.push(selectedComplaints + "," + selectedDays);

            var tableBody = document.getElementById('complaintsTableBody');
            tableBody.innerHTML = '';

            selectedComplaint.forEach(function(serviceData) {
                var selectedParts = serviceData.split(",");
                var service = selectedParts[0].trim();
                var price = selectedParts[1].trim();

                var row = document.createElement('tr');
                var serviceCell = document.createElement('td');
                serviceCell.textContent = service;
                row.appendChild(serviceCell);

                var priceCell = document.createElement('td');
                priceCell.textContent = price;
                row.appendChild(priceCell);

                tableBody.appendChild(row);
            });
        });

    var elements = document.getElementsByClassName('getValues');
        for (var i = 0; i < elements.length; i++) {
          elements[i].addEventListener('click', function() {
            var selectedMedicines = [];
            var existingServices = document.querySelectorAll('#complaintsTableBody tr');
            for (var i = 0; i < existingServices.length; i++) {
              var service = existingServices[i].querySelector('td:first-child').textContent;
              var price = existingServices[i].querySelector('td:nth-child(2)').textContent;
              selectedMedicines.push(service + "," + price);
            }

            var divBody = document.getElementById('getComplaints');
            divBody.innerHTML = ''; // Clear previous table rows

            selectedMedicines.forEach(function(medicineData) {
              var selectedParts = medicineData.split(',');

              var complaint = selectedParts[0];
              var days = selectedParts[1];

              var medicineInput = document.createElement('input');
              medicineInput.type = 'hidden';
              medicineInput.name = 'complaint';
              medicineInput.value = `${complaint}||${days} DAYS,`;

              divBody.appendChild(medicineInput);
            });
          });
        }



  document.getElementById("saveObservationBtn").addEventListener("click", function() {
              var selectedObservations = document.getElementById("observation").value;
              var selectedReading = document.getElementById("reading").value;

              var selectedObservation = [];

              var existingServices = document.querySelectorAll('#observationsTableBody tr');
              for (var i = 0; i < existingServices.length; i++) {
                  var observation = existingServices[i].querySelector('td:first-child').textContent;
                  var reading = existingServices[i].querySelector('td:nth-child(2)').textContent;
                  selectedObservation.push(observation + "," + reading);
              }

              console.log(selectedObservation);

              selectedObservation.push(selectedObservations + "," + selectedReading);

              var tableBody = document.getElementById('observationsTableBody');
              tableBody.innerHTML = '';

              selectedObservation.forEach(function(serviceData) {
                  var selectedParts = serviceData.split(",");
                  var service = selectedParts[0].trim();
                  var price = selectedParts[1].trim();

                  var row = document.createElement('tr');
                  var serviceCell = document.createElement('td');
                  serviceCell.textContent = service;
                  row.appendChild(serviceCell);

                  var priceCell = document.createElement('td');
                  priceCell.textContent = price;
                  row.appendChild(priceCell);

                  tableBody.appendChild(row);
              });
          });


  var elements = document.getElementsByClassName('getValues');
          for (var i = 0; i < elements.length; i++) {
            elements[i].addEventListener('click', function() {
              var selectedMedicines = [];
              var existingServices = document.querySelectorAll('#observationsTableBody tr');
              for (var i = 0; i < existingServices.length; i++) {
                var service = existingServices[i].querySelector('td:first-child').textContent;
                var price = existingServices[i].querySelector('td:nth-child(2)').textContent;
                selectedMedicines.push(service + "," + price);
              }

              var divBody = document.getElementById('getObservations');
              divBody.innerHTML = ''; // Clear previous table rows

              selectedMedicines.forEach(function(medicineData) {
                var selectedParts = medicineData.split(',');

                var observation = selectedParts[0];
                var reading = selectedParts[1];

                var medicineInput = document.createElement('input');
                medicineInput.type = 'hidden';
                medicineInput.name = 'observations';
                medicineInput.value = `${observation}||${reading},`;

                divBody.appendChild(medicineInput);
              });
            });
          }





   document.querySelectorAll('.addMedicines').forEach(function(form) {
     form.addEventListener('submit', function(event) {
       event.preventDefault(); // Prevent the default form submission

       var formData = {
         mediname: form.querySelector('input[name="mediname"]').value,
         strength: form.querySelector('input[name="strength"]').value,
         unit: form.querySelector('select[name="unit"]').value,
         dosage: form.querySelector('select[name="dosage"]').value,
         days: form.querySelector('input[name="days"]').value,
         remark: form.querySelector('input[name="remark"]').value,
         // Add more fields as per your requirements
       }; // Retrieve form data

       console.log(formData);

       // Make an AJAX request to update the medicine
       fetch(form.action, {
         method: 'POST',
         headers: {
           'Content-Type': 'application/json'
         },
         body: JSON.stringify(formData)
       })
         .then(function(response) {
           if (response.ok) {
             return response.json();
           } else {
             throw new Error('Failed to update medicine');
           }
         })
         .then(function(data) {
           console.log(data);
           console.log('Medicine updated successfully');
            location.reload();
         })
         .catch(function(error) {
           console.error('Error occurred during medicine update:', error);
         });
     });
   });

