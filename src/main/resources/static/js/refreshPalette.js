// refreshPalette.js

function rgbToHex(r, g, b) {
    var componentToHex = function (c) {
        var hex = c.toString(16);
        return hex.length === 1 ? "0" + hex : hex;
    };
    return "#" + componentToHex(r) + componentToHex(g) + componentToHex(b);
}

function refreshPalette() {
    /* Ajax request to update color palette */
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/palette/refresh", true);
    xhr.setRequestHeader("Content-Type", "application/json");  // Set content type
    xhr.onload = function () {
        if (xhr.status === 200) {
            var newColors = JSON.parse(xhr.responseText);
            updateColorPalette(newColors);
        } else {
            console.error("Error updating color palette");
        }
    };
    xhr.send();
}

function updateColorPalette(colors) {
    var colorPalette = document.getElementById('colorPalette');
    colorPalette.innerHTML = ""; // Clear existing colors

    colors.forEach(function (color) {
        var colorDiv = document.createElement('div');
        colorDiv.className = 'color-box1'; // Set the class

        colorDiv.style.backgroundColor = 'rgb(' + color.red + ',' + color.green + ',' + color.blue + ')';

        // Add click event listener to the color box
        colorDiv.addEventListener('click', function () {
            var hexCode = rgbToHex(color.red, color.green, color.blue);
            copyColorCodeToClipboard(hexCode);
        });

        colorPalette.appendChild(colorDiv);
    });
}


// Function to copy color code to clipboard
function copyColorCodeToClipboard(hexColor) {
    navigator.clipboard.writeText(hexColor)
        .then(function () {
            alert('Color code ' + hexColor + ' copied to clipboard');
        })
        .catch(function (error) {
            console.error('Unable to copy color code', error);
        });
}

// Initial setup to add click event listener to existing color boxes
document.addEventListener('DOMContentLoaded', function () {
    var colorBoxes = document.querySelectorAll('.color-box1');

    colorBoxes.forEach(function (box) {
        box.addEventListener('click', function (event) {
            var rgbColor = getRgbColor(event.target.style.backgroundColor);
            var hexColor = rgbToHex(rgbColor.red, rgbColor.green, rgbColor.blue);
            copyColorCodeToClipboard(hexColor);
        });
    });
});

// Function to get RGB color values from the style property
function getRgbColor(styleBackgroundColor) {
    var rgbValues = styleBackgroundColor.match(/\d+/g).map(Number);
    return {
        red: rgbValues[0],
        green: rgbValues[1],
        blue: rgbValues[2]
    };
}
