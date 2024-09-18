/*=============== SHOW MENU ===============*/
const navMenu = document.getElementById('nav-menu'),
    navToggle = document.getElementById('nav-toggle'),
    navClose = document.getElementById('nav-close')

/* Menu show */
navToggle.addEventListener('click', () => {
    navMenu.classList.add('show-menu')
})

/* Menu hidden */
navClose.addEventListener('click', () => {
    navMenu.classList.remove('show-menu')
})

/*=============== SEARCH ===============*/
const search = document.getElementById('search'),
    searchBtn = document.getElementById('search-btn'),
    searchClose = document.getElementById('search-close'),
    searchInput = document.getElementById('query'),
    colorResults = document.getElementById('colorResults');

/* Search show */
searchBtn.addEventListener('click', () => {
    search.classList.add('show-search');
    searchInput.focus();
});

/* Search hidden */
searchClose.addEventListener('click', () => {
    search.classList.remove('show-search');
    colorResults.innerHTML = ''; // Clear color results when hiding the search
});

/* AJAX Search on input change */
searchInput.addEventListener('input', () => {
    const query = searchInput.value.trim();
    if (query !== '') {
        searchColors(query);
    } else {
        colorResults.innerHTML = ''; // Clear color results if the query is empty
    }
});

function searchColors(query) {
    // Use AJAX to send the search query to the server
    // Update the #colorResults element with the response
    fetch('/search1?query=' + encodeURIComponent(query))
        .then(response => response.text())
        .then(html => {
            colorResults.innerHTML = html;
        })
        .catch(error => console.error('Error:', error));
}

/*=============== LOGIN ===============*/
//const login = document.getElementById('login'),
//    loginBtn = document.getElementById('login-btn'),
//    loginClose = document.getElementById('login-close')
//
///* Login show */
//loginBtn.addEventListener('click', () => {
//    login.classList.add('show-login')
//})
//
///* Login hidden */
//loginClose.addEventListener('click', () => {
//    login.classList.remove('show-login')
//})
/*=============== COLOR CODE COPYING ===============*/
document.addEventListener('DOMContentLoaded', function () {
    const colorBoxes = document.querySelectorAll('.color-box1');

    colorBoxes.forEach(box => {
        box.addEventListener('click', handleColorClick);
    });

    function handleColorClick(event) {
        const hexColor = rgbToHex(event.target.style.backgroundColor);
        copyColorCodeToClipboard(hexColor);
    }

    function rgbToHex(rgb) {
        const rgbArray = rgb.match(/\d+/g).map(Number);
        return '#' + rgbArray.map(component => component.toString(16).padStart(2, '0')).join('');
    }

    function copyColorCodeToClipboard(hexColor) {
        navigator.clipboard.writeText(hexColor)
            .then(() => alert('Color code ' + hexColor + ' copied to clipboard'))
            .catch(error => console.error('Unable to copy color code', error));
    }
});
