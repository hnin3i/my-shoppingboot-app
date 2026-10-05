
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
       const color = button.dataset.color;
       if (!color) return;

       document.querySelectorAll(".color-option").forEach(item => item.classList.remove("active"));
       button.classList.add("active");
       selectedColor = color;

       const selectedColorText = document.getElementById("selectedColor");
       if (selectedColorText) selectedColorText.textContent = selectedColor;

       selectedSize = "";
       document.querySelectorAll(".size-btn").forEach(item => item.classList.remove("active"));

       // Size များကို ပြန်လည် render လုပ်မည်
       renderSizes();

       const quantityInput = document.getElementById("quantity");
       if (quantityInput) {
           quantityInput.value = 1;
           quantityInput.max = 20;
       }

       // Add to Cart မနှိပ်သေးသရွေ့ Message ကို ကွက်လပ်ထားမည်
       const sizeMessage = document.getElementById("sizeMessage");
       if (sizeMessage) {
           sizeMessage.className = "size-message";
           sizeMessage.innerHTML = "";
       }
   }

/* =========================================================
   RENDER SIZES (SHOW ALL SIZES WITH STRIKETHROUGH IF OUT OF STOCK)
   ========================================================= */

  function renderSizes() {
       const container = document.getElementById("sizeOptions");

       if (!container || typeof DETAIL_PRODUCT === "undefined" || !Array.isArray(DETAIL_PRODUCT.stocks)) {
           return;
       }

       container.innerHTML = "";

       const ALL_SIZES = ["XS", "S", "M", "L", "XL", "XXL"];

       ALL_SIZES.forEach(function(size) {
           const stock = DETAIL_PRODUCT.stocks.find(function(item) {
               return item.colour === selectedColor && item.size === size;
           });

           const button = document.createElement("button");
           button.type = "button";
           button.className = "size-btn";
           button.dataset.size = size;
           button.textContent = size;

           // Stock မရှိပါက သို့မဟုတ် Stock Qty <= 0 ဖြစ်ပါက out-of-stock class ထည့်မည်
           if (!stock || Number(stock.stockQty) <= 0) {
               button.classList.add("out-of-stock");
               button.setAttribute("data-out-of-stock", "true");
           }

           container.appendChild(button);
       });
   }


/* =========================================================
   SIZE SELECTION (HANDLES BOTH IN-STOCK & OUT-OF-STOCK)
   ========================================================= */
   function handleSizeSelection(button) {
       const size = button.dataset.size;
       if (!size) return;

       const sizeMessage = document.getElementById("sizeMessage");

       // Stock မရှိသော (Strikethrough) Size ကို နှိပ်ပါက
       if (button.classList.contains("out-of-stock") || button.getAttribute("data-out-of-stock") === "true") {
           document.querySelectorAll(".size-btn").forEach(item => item.classList.remove("active"));
           selectedSize = "";

           if (sizeMessage) {
               sizeMessage.className = "size-message text-danger";
               sizeMessage.innerHTML = '<i class="bi bi-exclamation-circle"></i> This item is out of stock.';
           }

           const quantityInput = document.getElementById("quantity");
           if (quantityInput) quantityInput.value = 1;
           return;
       }

       // Stock ရှိသော Size ကို နှိပ်ပါက
       const stock = DETAIL_PRODUCT.stocks.find(item => item.colour === selectedColor && item.size === size);
       if (!stock) return;

       const stockQty = Number(stock.stockQty);

       document.querySelectorAll(".size-btn").forEach(item => item.classList.remove("active"));
       button.classList.add("active");
       selectedSize = size;

       const quantityInput = document.getElementById("quantity");
       if (quantityInput) {
           quantityInput.value = 1;
           quantityInput.max = stockQty;
       }

       // ရွေးချယ်မှု မှန်ကန်ပါက အနက်ရောင်ဖြင့် ပြမည်
       if (sizeMessage) {
           sizeMessage.className = "size-message text-dark";
           sizeMessage.innerHTML = '<i class="bi bi-check-circle"></i> Size selected';
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
    const quantityInput = document.getElementById("quantity");
    if (!quantityInput) return;

    let quantity = Number(quantityInput.value);

    if (isNaN(quantity) || quantity <= 1) {
        quantity = 1;
    } else {
        quantity--;
    }

    quantityInput.value = quantity;
}


/* =========================================================
   INCREASE QUANTITY
   ========================================================= */

function increaseQuantity() {
    const quantityInput = document.getElementById("quantity");
    if (!quantityInput) return;

    let quantity = Number(quantityInput.value);

    if (isNaN(quantity) || quantity < 1) {
        quantity = 1;
    }

    const maxQuantity = getMaxQuantity();

    if (quantity < maxQuantity) {
        quantity++;
    } else {
        if (typeof showToast === "function") {
            showToast(`Max available stock is ${maxQuantity}`, "warning");
        }
    }

    quantityInput.value = quantity;
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
           throw new Error("Product information not found.");
       }

       // 1. Color မရွေးရသေးပါက
       if (!selectedColor) {
           if (typeof showToast === "function") {
               showToast("Please select a color.", "warning");
           }
           return;
       }

       // 2. Size မရွေးရသေးပါက sizeMessage ကို အနီရောင်ဖြင့် "Please select a size" ပြမည်
       if (!selectedSize) {
           const sizeMessage = document.getElementById("sizeMessage");
           if (sizeMessage) {
               sizeMessage.className = "size-message text-danger";
               sizeMessage.innerHTML = '<i class="bi bi-info-circle"></i> Please select a size';
               
               // Size Section ထံ သို့ စက်ဝိုင်းသဖွယ် Smooth Scroll သွားရန် (Optional)
               sizeMessage.scrollIntoView({ behavior: 'smooth', block: 'center' });
           }

           if (typeof showToast === "function") {
               showToast("Please select a size.", "warning");
           }
           return;
       }

       // 3. ရွေးချယ်ထားသော Color နဲ့ Size အတွက် Stock ရှာမည်
       const stock = findSelectedStock();

       if (!stock) {
           if (typeof showToast === "function") {
               showToast("This color and size are not available.", "danger");
           }
           return;
       }

       const maxQty = Number(stock.stockQty);

       if (maxQty <= 0) {
           const sizeMessage = document.getElementById("sizeMessage");
           if (sizeMessage) {
               sizeMessage.className = "size-message text-danger";
               sizeMessage.innerHTML = '<i class="bi bi-exclamation-circle"></i> This item is out of stock.';
           }
           return;
       }

       const quantity = getQuantity();

       if (quantity > maxQty) {
           if (typeof showToast === "function") {
               showToast("Requested quantity is greater than available stock.", "warning");
           }
           return;
       }

       // 4. Cart ထဲသို့ ထည့်သွင်းမည်
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

       const result = addToCart(stockInfo, quantity);

       if (typeof showToast === "function") {
           showToast(result.message, result.success ? "success" : "danger");
       }
   }


/* =========================================================
   EVENT DELEGATION (FIXED)
   ========================================================= */

function handleProductDetailClick(event) {

    const colorButton =
        event.target.closest(".color-option");

    if (colorButton) {
        handleColorSelection(colorButton);
        return;
    }

    const sizeButton =
        event.target.closest(".size-btn");

    if (sizeButton) {
        handleSizeSelection(sizeButton);
        return;
    }

    // .closest() သုံးထားသည့်အတွက် <button> ရော ၎င်း၏ <i> Icon ကိုပါ ဖမ်းမိမည်ဖြစ်သည်
    const decreaseButton =
        event.target.closest("#decreaseQuantity");

    if (decreaseButton) {
        event.preventDefault();
        decreaseQuantity();
        return;
    }

    const increaseButton =
        event.target.closest("#increaseQuantity");

    if (increaseButton) {
        event.preventDefault();
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

/* =========================================================
   DESCRIPTION ACCORDION TOGGLE (FIXED PURE JS)
   ========================================================= */

document.addEventListener("DOMContentLoaded", function () {
    const trigger = document.querySelector(".accordion-trigger");
    const content = document.querySelector("#descriptionCollapse");

    if (trigger && content) {
        trigger.addEventListener("click", function (event) {
            event.preventDefault();
            event.stopPropagation();

            // Check if currently open
            const isOpen = trigger.classList.contains("is-open");

            if (isOpen) {
                // Close accordion
                trigger.classList.remove("is-open");
                content.classList.remove("is-open");
                trigger.setAttribute("aria-expanded", "false");
            } else {
                // Open accordion
                trigger.classList.add("is-open");
                content.classList.add("is-open");
                trigger.setAttribute("aria-expanded", "true");
            }
        });
    }
});