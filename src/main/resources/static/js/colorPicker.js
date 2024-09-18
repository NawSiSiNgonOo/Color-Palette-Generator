var contextPath = /*[[@{/}]]*/ ''; // Adjust the syntax based on your template engine

document.getElementById('imageInput').addEventListener('change', handleImage);

function handleImage(event) {
    console.log('Handling image...');
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = function (e) {
        const image = new Image();
        image.src = e.target.result;
        image.onload = function () {
            const canvas = document.getElementById('colorCanvas');
            const context = canvas.getContext('2d');
            context.drawImage(image, 0, 0, canvas.width, canvas.height);

            // Submit the form
            document.getElementById('imageForm').submit();
        };
    };

    reader.readAsDataURL(file);
}
