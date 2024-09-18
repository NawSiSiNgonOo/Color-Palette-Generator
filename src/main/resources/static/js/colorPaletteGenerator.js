// Import the k-means library
const kmeans = require('k-means');

document.getElementById('imageInput').addEventListener('change', handleImageUpload);

function handleImageUpload(event) {
    const input = event.target;
    const file = input.files[0];

    if (file) {
        const reader = new FileReader();

        reader.onload = function (e) {
            const originalImage = document.getElementById('originalImage');
            originalImage.src = e.target.result;

            // Resize the image (optional)
            const resizedImage = resizeImage(originalImage, 150, 150);

            // Generate color palette
            generateColorPalette(resizedImage);
        };

        reader.readAsDataURL(file);
    }
}

function resizeImage(image, width, height) {
    const canvas = document.createElement('canvas');
    canvas.width = width;
    canvas.height = height;
    const ctx = canvas.getContext('2d');
    ctx.drawImage(image, 0, 0, width, height);
    const resizedImage = new Image();
    resizedImage.src = canvas.toDataURL('image/jpeg'); // Adjust format if needed
    return resizedImage;
}

function generateColorPalette(image) {
    // Get pixel data from the image
    const imageData = getImageData(image);

    // Extract RGB values from pixel data
    const pixels = extractRGBValues(imageData);

    // Use k-means clustering to get color palette
    kmeans.clusterize(pixels, { k: 5 }, (err, result) => {
        if (err) {
            console.error(err);
            return;
        }

        // Result contains centroids with RGB values
        const colorPalette = result.centroids.map(centroid => rgbToHex(centroid));

        // Display color palette
        displayColorPalette(colorPalette);
    });
}

function getImageData(image) {
    const canvas = document.createElement('canvas');
    const ctx = canvas.getContext('2d');
    canvas.width = image.width;
    canvas.height = image.height;
    ctx.drawImage(image, 0, 0, image.width, image.height);
    return ctx.getImageData(0, 0, image.width, image.height);
}

function extractRGBValues(imageData) {
    const pixels = [];

    for (let i = 0; i < imageData.data.length; i += 4) {
        const red = imageData.data[i];
        const green = imageData.data[i + 1];
        const blue = imageData.data[i + 2];
        pixels.push([red, green, blue]);
    }

    return pixels;
}

function rgbToHex(rgb) {
    return '#' + rgb.map(component => component.toString(16).padStart(2, '0')).join('');
}

function displayColorPalette(colorPalette) {
    const paletteContainer = document.getElementById('paletteContainer');
    const colorPaletteContainer = document.getElementById('colorPalette');

    // Clear previous color palette
    colorPaletteContainer.innerHTML = '';

    // Display each color in the palette
    colorPalette.forEach(color => {
        const colorBox = document.createElement('div');
        colorBox.style.backgroundColor = color;
        colorPaletteContainer.appendChild(colorBox);
    });

    // Show the color palette container
    paletteContainer.style.display = 'block';
}
