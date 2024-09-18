const selectImage = document.querySelector('.select-image');
const inputFile = document.querySelector('#file');
const imgArea = document.querySelector('.img-area');
const form = document.querySelector('form');

selectImage.addEventListener('click', function () {
    inputFile.click();
});

inputFile.addEventListener('change', function () {
    const image = this.files[0];
    if (image.size < 2000000) {
        const reader = new FileReader();
        reader.onload = () => {
            const allImg = imgArea.querySelectorAll('img');
            allImg.forEach(item => item.remove());
            const imgUrl = reader.result;
            const img = document.createElement('img');
            img.src = imgUrl;
            imgArea.appendChild(img);
            imgArea.classList.add('active');
            imgArea.dataset.img = image.name;

            // Set the value of the hidden file input
            form.querySelector('#file').files = this.files;

            // Append the file to the form data before submission
            const formData = new FormData(form);
            formData.append('file', image);

            // Now submit the form with the appended file
            fetch(form.action, {
                method: form.method,
                body: formData
            })
            .then(response => response.json())  // Assuming you expect JSON response, adjust accordingly
            .then(data => {
                console.log('Success:', data);
                // Handle success, if needed
            })
            .catch((error) => {
                console.error('Error:', error);
                // Handle error, if needed
            });
        };
        reader.readAsDataURL(image);
    } else {
        alert("Image size more than 2MB");
    }
});

const copyColorCode = (element) => {
    const rgbValue = element.style.backgroundColor;
    const hexCode = rgbToHex(rgbValue);

    const dummyElement = document.createElement('textarea');
    document.body.appendChild(dummyElement);
    dummyElement.value = hexCode;
    dummyElement.select();
    document.execCommand('copy');
    document.body.removeChild(dummyElement);

    alert(`Color code ${hexCode} copied to clipboard!`);
};

const rgbToHex = (rgb) => {
    const values = rgb.match(/\d+/g).map(Number);
    return `#${values.map(val => val.toString(16).padStart(2, '0')).join('')}`;
};