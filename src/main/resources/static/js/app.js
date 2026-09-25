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