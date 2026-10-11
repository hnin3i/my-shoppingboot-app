
document.addEventListener("DOMContentLoaded", function () {

const seeMore = document.getElementById("seeMoreCategories");
const moreCategories = document.getElementById("moreCategories");

if (seeMore && moreCategories) {

seeMore.addEventListener("click", function (e) {

e.preventDefault();

moreCategories.style.display = "block";

seeMore.parentElement.style.display = "none";
});

}

});

document.addEventListener("DOMContentLoaded", function () {

    const currentPath = window.location.pathname;

    document.querySelectorAll(".account-menu-link").forEach(function (link) {

        if (link.getAttribute("href") === currentPath) {
            link.classList.add("active");
        }

    });

});


