document.getElementById("userForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent the default form submission

    // Collect the form data
    const formData = {
        username: document.getElementById("username").value
    };

    // Submit the form data as JSON using axios
    axios.post('/submit', formData, {
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(function(response) {
            alert('Form submitted successfully: ' + response.data);
            console.log('Form submitted successfully:', response.data);
        })
        .catch(function(error) {
            alert('Error submitting form: ' + error.response?.data || error.message);

            console.error('Error submitting form:', error);
        });
});
