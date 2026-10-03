
/* =========================================================
   PRODUCT DETAIL STATE
   ========================================================= */

let selectedColor = "";
let selectedSize = "";


/* =========================================================
   FIND SELECTED STOCK
   ========================================================= */

function findSelectedStock() {

    if (typeof DETAIL_PRODUCT === "undefined") {
        throw new Error("Product information not found.");
    }

    if (!Array.isArray(DETAIL_PRODUCT.stocks)) {
        throw new Error("Product stock information not found.");
    }

    return DETAIL_PRODUCT.stocks.find(function(stock) {

        return stock.colour === selectedColor
            && stock.size === selectedSize;

    });
}


/* =========================================================
   RENDER COLORS FROM DATABASE
   ========================================================= */

function renderColors() {

    const container =
        document.querySelector(".color-options");

    if (!container) {
        return;
    }

    if (typeof DETAIL_PRODUCT === "undefined") {
        return;
    }

    if (!Array.isArray(DETAIL_PRODUCT.stocks)) {
        return;
    }


    const colors = [];


    DETAIL_PRODUCT.stocks.forEach(function(stock) {

        if (!colors.includes(stock.colour)) {

            colors.push(stock.colour);

        }

    });


    if (colors.length === 0) {

        container.innerHTML =
            "<span>No color available</span>";

        return;
    }


    container.innerHTML = colors.map(function(color) {

        return `
            <button
                type="button"
                class="color-option"
                data-color="${color}"
                aria-label="${color}"
            >
            </button>
        `;

    }).join("");


    /*
     * Select first available color
     */

    const firstColor =
        container.querySelector(".color-option");

    if (firstColor) {

        handleColorSelection(firstColor);

    }
}


/* =========================================================
   COLOR SELECTION
   ========================================================= */

function handleColorSelection(button) {

    const color =
        button.dataset.color;

    if (!color) {
        return;
    }


    document
        .querySelectorAll(".color-option")
        .forEach(function(item) {

            item.classList.remove("active");

        });


    button.classList.add("active");

    selectedColor = color;


    const selectedColorText =
        document.getElementById("selectedColor");

    if (selectedColorText) {

        selectedColorText.textContent =
            selectedColor;

    }


    /*
     * Color changed.
     * Reset selected size.
     */

    selectedSize = "";

    document
        .querySelectorAll(".size-btn")
        .forEach(function(item) {

            item.classList.remove("active");

        });


    renderSizes();


    const quantityInput =
        document.getElementById("quantity");

    if (quantityInput) {

        quantityInput.value = 1;

        quantityInput.max = 20;

    }


    const sizeMessage =
        document.getElementById("sizeMessage");

    if (sizeMessage) {

        sizeMessage.innerHTML =
            '<i class="bi bi-info-circle"></i> Please select a size';

    }
}


/* =========================================================
   RENDER SIZES FROM DATABASE
   ========================================================= */

function renderSizes() {

    const container =
        document.getElementById("sizeOptions");

    if (!container) {
        return;
    }

    if (typeof DETAIL_PRODUCT === "undefined") {
        return;
    }

    if (!Array.isArray(DETAIL_PRODUCT.stocks)) {
        return;
    }


    container.innerHTML = "";


    const sizes = [];


    DETAIL_PRODUCT.stocks.forEach(function(stock) {

        /*
         * Only show sizes belonging to
         * selected color.
         */

        if (stock.colour !== selectedColor) {
            return;
        }


        if (!sizes.includes(stock.size)) {

            sizes.push(stock.size);

        }

    });


    sizes.forEach(function(size) {

        const stock =
            DETAIL_PRODUCT.stocks.find(function(item) {

                return item.colour === selectedColor
                    && item.size === size;

            });


        const button =
            document.createElement("button");

        button.type = "button";

        button.className = "size-btn";

        button.dataset.size = size;

        button.textContent = size;


        /*
         * Disable if stock is 0.
         */

        if (!stock || Number(stock.stockQty) <= 0) {

            button.disabled = true;

            button.classList.add("disabled");

        }


        container.appendChild(button);

    });
}


/* =========================================================
   SIZE SELECTION
   ========================================================= */

function handleSizeSelection(button) {

    if (button.disabled) {
        return;
    }


    const size =
        button.dataset.size;

    if (!size) {
        return;
    }


    const stock =
        DETAIL_PRODUCT.stocks.find(function(item) {

            return item.colour === selectedColor
                && item.size === size;

        });


    if (!stock) {

        showToast(
            "This color and size are not available.",
            "danger"
        );

        return;
    }


    const stockQty =
        Number(stock.stockQty);


    if (stockQty <= 0) {

        showToast(
            "This size is out of stock.",
            "warning"
        );

        return;
    }


    document
        .querySelectorAll(".size-btn")
        .forEach(function(item) {

            item.classList.remove("active");

        });


    button.classList.add("active");

    selectedSize = size;


    /*
     * Set quantity according to
     * real database stock.
     */

    const quantityInput =
        document.getElementById("quantity");

    if (quantityInput) {

        quantityInput.value = 1;

        quantityInput.max = stockQty;

    }


    const sizeMessage =
        document.getElementById("sizeMessage");

    if (sizeMessage) {

        sizeMessage.innerHTML =
            '<i class="bi bi-check-circle"></i> Size selected';

    }
}


/* =========================================================
   GET QUANTITY
   ========================================================= */

function getQuantity() {

    const quantityInput =
        document.getElementById("quantity");

    if (!quantityInput) {

        throw new Error(
            "Quantity input not found."
        );

    }


    let quantity =
        Number(quantityInput.value);


    if (!Number.isInteger(quantity)
        || quantity < 1) {

        quantity = 1;

    }


    return quantity;
}


/* =========================================================
   GET MAX QUANTITY
   ========================================================= */

function getMaxQuantity() {

    if (!selectedColor || !selectedSize) {

        return 20;

    }


    const stock =
        findSelectedStock();


    if (!stock) {

        return 0;

    }


    return Number(stock.stockQty);
}


/* =========================================================
   DECREASE QUANTITY
   ========================================================= */

function decreaseQuantity() {

    const quantityInput =
        document.getElementById("quantity");

    if (!quantityInput) {
        return;
    }


    let quantity =
        Number(quantityInput.value);


    if (!Number.isInteger(quantity)
        || quantity < 1) {

        quantity = 1;

    }


    if (quantity > 1) {

        quantity--;

    }


    quantityInput.value = quantity;
}


/* =========================================================
   INCREASE QUANTITY
   ========================================================= */

function increaseQuantity() {

    const quantityInput =
        document.getElementById("quantity");

    if (!quantityInput) {
        return;
    }


    let quantity =
        Number(quantityInput.value);


    if (!Number.isInteger(quantity)
        || quantity < 1) {

        quantity = 1;

    }


    const maxQuantity =
        getMaxQuantity();


    if (quantity < maxQuantity) {

        quantity++;

    }
}


/* =========================================================
   MANUAL QUANTITY INPUT
   ========================================================= */

function handleQuantityInput(event) {

    if (event.target.id !== "quantity") {
        return;
    }


    let value =
        event.target.value.replace(
            /[^0-9]/g,
            ""
        );


    if (value === "") {

        event.target.value = "";

        return;
    }


    let quantity =
        Number(value);


    if (quantity < 1) {

        quantity = 1;

    }


    const maxQuantity =
        getMaxQuantity();


    if (maxQuantity > 0
        && quantity > maxQuantity) {

        quantity = maxQuantity;

    }


    event.target.value =
        quantity;
}


/* =========================================================
   QUANTITY BLUR
   ========================================================= */

function handleQuantityBlur(event) {

    if (event.target.id !== "quantity") {
        return;
    }


    let quantity =
        Number(event.target.value);


    if (!Number.isInteger(quantity)
        || quantity < 1) {

        quantity = 1;

    }


    const maxQuantity =
        getMaxQuantity();


    if (maxQuantity > 0
        && quantity > maxQuantity) {

        quantity = maxQuantity;

    }


    event.target.value =
        quantity;
}


/* =========================================================
   ADD TO CART
   ========================================================= */

function handleDetailAddToCart() {

    if (typeof DETAIL_PRODUCT === "undefined") {

        throw new Error(
            "Product information not found."
        );

    }


    if (!selectedColor) {

        showToast(
            "Please select a color.",
            "warning"
        );

        return;
    }


    if (!selectedSize) {

        showToast(
            "Please select a size.",
            "warning"
        );

        return;
    }


    const stock =
        findSelectedStock();


    if (!stock) {

        showToast(
            "This color and size are not available.",
            "danger"
        );

        return;
    }


    const maxQty =
        Number(stock.stockQty);


    if (maxQty <= 0) {

        showToast(
            "This item is out of stock.",
            "warning"
        );

        return;
    }


    const quantity =
        getQuantity();


    if (quantity > maxQty) {

        showToast(
            "Requested quantity is greater than available stock.",
            "warning"
        );

        return;
    }


    /*
     * Product information comes from
     * DETAIL_PRODUCT.
     *
     * Stock information comes from
     * database stocks.
     */

    const stockInfo = {

        stockId: stock.id,

        productId: DETAIL_PRODUCT.productId,

        productName: DETAIL_PRODUCT.productName,

        image: DETAIL_PRODUCT.image,

        colour: stock.colour,

        size: stock.size,

        maxQty: maxQty,

        price: Number(DETAIL_PRODUCT.price)

    };


    const result =
        addToCart(
            stockInfo,
            quantity
        );


    showToast(

        result.message,

        result.success
            ? "success"
            : "danger"

    );
}


/* =========================================================
   EVENT DELEGATION
   ========================================================= */

function handleProductDetailClick(event) {

    const colorButton =
        event.target.closest(".color-option");

    if (colorButton) {

        handleColorSelection(
            colorButton
        );

        return;
    }


    const sizeButton =
        event.target.closest(".size-btn");

    if (sizeButton) {

        handleSizeSelection(
            sizeButton
        );

        return;
    }


    const decreaseButton =
        event.target.closest("#decreaseQuantity");

    if (decreaseButton) {

        decreaseQuantity();

        return;
    }


    const increaseButton =
        event.target.closest("#increaseQuantity");

    if (increaseButton) {

        increaseQuantity();

        return;
    }


    const addButton =
        event.target.closest("#addToCartBtn");

    if (addButton) {

        handleDetailAddToCart();

    }
}


/* =========================================================
   INITIALIZE
   ========================================================= */

function initializeProductDetail() {

    if (typeof DETAIL_PRODUCT === "undefined") {

        return;

    }


    if (!Array.isArray(DETAIL_PRODUCT.stocks)) {

        return;

    }


    if (DETAIL_PRODUCT.stocks.length === 0) {

        return;

    }


    renderColors();

}


/* =========================================================
   DOM READY
   ========================================================= */

document.addEventListener(
    "DOMContentLoaded",
    function() {

        initializeProductDetail();


        document.addEventListener(
            "click",
            handleProductDetailClick
        );


        document.addEventListener(
            "input",
            handleQuantityInput
        );


        document.addEventListener(
            "blur",
            handleQuantityBlur,
            true
        );

    }
);

