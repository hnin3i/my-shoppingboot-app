function addToCart(stockInfo, quantity = 1) {
    const cart = getCart();
    const existingIndex = cart.findIndex(
        item => item.stockId === stockInfo.stockId
    );

    if (quantity < 1) {
        throw new Error("Quantity must be at least 1.");
    }

    if (existingIndex !== -1) {
        const existingItem = cart[existingIndex];
        const newQuantity = existingItem.quantity + quantity;

        if (newQuantity > existingItem.maxQty) {
            return {
                success: false,
                message: `Only ${existingItem.maxQty} in stock!`
            };
        }

        existingItem.quantity = newQuantity;
        existingItem.subtotal = Number(
            (existingItem.price * newQuantity).toFixed(2)
        );
    } else {
        if (quantity > stockInfo.maxQty) {
            return {
                success: false,
                message: `Only ${stockInfo.maxQty} in stock!`
            };
        }

        cart.push({
            stockId: stockInfo.stockId,
            productId: stockInfo.productId,
            productName: stockInfo.productName,
            image: stockInfo.image,
            colour: stockInfo.colour,
            size: stockInfo.size,
            maxQty: stockInfo.maxQty,
            price: stockInfo.price,
            quantity,
            subtotal: Number(
                (stockInfo.price * quantity).toFixed(2)
            )
        });
    }

    saveCart(cart);
    updateBadge();

    return {
        success: true,
        message: `${stockInfo.productName} added to cart!`
    };
}

function removeFromCart(stockId) {
    const cart = getCart().filter(
        item => item.stockId !== stockId
    );

    saveCart(cart);
    updateBadge();
    renderCart();
}

function updateQty(stockId, newQuantity) {
    if (newQuantity < 1) {
        removeFromCart(stockId);
        return;
    }

    const cart = getCart();
    const index = cart.findIndex(
        item => item.stockId === stockId
    );

    if (index === -1) {
        throw new Error("Cart item not found.");
    }

    if (newQuantity > cart[index].maxQty) {
        showToast(
            `Max stock is ${cart[index].maxQty}`,
            "warning"
        );
        return;
    }

    cart[index].quantity = newQuantity;
    cart[index].subtotal = Number(
        (cart[index].price * newQuantity).toFixed(2)
    );

    saveCart(cart);
    updateBadge();
    renderCart();
}

function updateBadge() {
    const badge = document.getElementById("cart-badge");

    if (!badge) return;

    const count = getCartCount();
    badge.textContent = count;
    badge.style.display = count > 0 ? "inline-block" : "none";
}

function loadCartPage() {
    const cart = getCart();

    renderCart(cart);
    updateCartTotal(cart);
}

function goToCheckout(){
	const cart=getCart();
	
	if(cart.length=== 0){
		showError("Your cart is empty");
		return;
	}
	
	window.location.href="/cart/checkout";
}

function handleClearCart() {
    clearCart(); // Uses clearCart() from storage.js
    updateBadge(); // Updates top navigation badge count
    renderCart(); // Re-renders empty cart state
}

function renderCart() {
    const container = document.getElementById("cart-container");

    if (!container) return;

    const cart = getCart();

    if (cart.length === 0) {
        container.innerHTML = `
            <div class="text-center py-5">
                <div class="display-5 mb-3">🛒</div>
                <h4 class="fw-normal">Your bag is empty</h4>
                <a href="/" class="btn btn-outline-dark rounded-pill mt-3 px-4">
                    Browse Products
                </a>
            </div>
        `;
        return;
    }

    container.innerHTML = `
        <!-- Responsive Grid Header -->
        <div class="row cart-grid-header d-none d-md-flex align-items-center">
            <div class="col-md-5 col-lg-5">PRODUCT</div>
            <div class="col-md-2 text-center">PRICE</div>
            <div class="col-md-2 text-center">QUANTITY</div>
            <div class="col-md-2 text-end">SUBTOTAL</div>
            <div class="col-md-1 text-end"></div>
        </div>

        <!-- Cart Items List -->
        <div class="cart-items-list mb-4">
            ${cart.map(item => `
                <div class="row cart-item-row align-items-center g-2 g-md-3">
                    <!-- Product Info -->
                    <div class="col-12 col-md-5 col-lg-5">
                        <div class="d-flex align-items-center gap-3">
                            <img
                                src="${item.image}"
                                class="product-img"
                                alt="${item.productName}"
                            >
                            <div>
                                <div class="product-title">${item.productName}</div>
                                <div class="product-variant">${item.colour} /${item.size}</div>
                            </div>
                        </div>
                    </div>

                    <!-- Price -->
                    <div class="col-4 col-md-2 text-start text-md-center">
                        <span class="d-md-none text-muted small d-block">Price:</span>
                        <span class="fw-normal">${Number(item.price).toLocaleString()} MMK</span>
                    </div>

                    <!-- Quantity Control -->
                    <div class="col-8 col-md-2 text-end text-md-center">
                        <div class="qty-pill-input">
                            <button
                                class="qty-pill-btn"
                                data-cart-action="decrease"
                                data-stock-id="${item.stockId}"
                                data-quantity="${item.quantity - 1}"
                            >−</button>
                            <span class="px-2 px-md-3 fw-normal">${item.quantity}</span>
                            <button
                                class="qty-pill-btn"
                                data-cart-action="increase"
                                data-stock-id="${item.stockId}"
                                data-quantity="${item.quantity + 1}"
                            >+</button>
                        </div>
                    </div>

                    <!-- Subtotal -->
                    <div class="col-9 col-md-2 text-start text-md-end">
                        <span class="d-md-none text-muted small d-block">Subtotal:</span>
                        <span class="fw-semibold fs-6">${Number(item.subtotal).toLocaleString()} MMK</span>
                    </div>

                    <!-- Remove Icon (Pinned to Right) -->
                    <div class="col-3 col-md-1 text-end">
                        <button
                            class="btn-remove-icon"
                            data-cart-action="remove"
                            data-stock-id="${item.stockId}"
                            title="Remove item"
                        >
                            <i class="bi bi-trash3"></i>
                        </button>
                    </div>
                </div>
            `).join("")}
        </div>

        <!-- Footer Actions -->
        <div class="row align-items-end pt-3">
            <div class="col-12 col-md-4 mb-4 mb-md-0">
                <span class="clear-cart-link" data-cart-action="clear-all">Clear Cart</span>
            </div>
            <div class="col-12 col-md-8 text-start text-md-end">
                <div class="d-inline-block text-start text-md-end">
                    <div class="d-flex justify-content-start justify-content-md-end align-items-center gap-4 mb-1">
                        <span class="text-uppercase fw-semibold tracking-wider text-muted small">SUBTOTAL</span>
                        <span class="fs-4 fw-bold">${getCartTotal().toLocaleString()} MMK</span>
                    </div>
                    <p class="text-muted small fst-italic mb-3">Taxes and shipping calculated at checkout</p>
                    <button 
                        class="btn btn-checkout-black w-100 w-md-auto" 
                        type="button" 
                        data-cart-action="checkout"
                    >
                        Proceed to Checkout →
                    </button>
                </div>
            </div>
        </div>
    `;
}

function handleCartAction(event) {
    const button = event.target.closest("[data-cart-action]");

    if (!button) return;

    const action = button.dataset.cartAction;

    if (action === "checkout") {
        goToCheckout();
        return;
    }

    if (action === "clear-all") {
        handleClearCart();
        return;
    }

    const stockId = button.dataset.stockId;
    const quantity = Number(button.dataset.quantity);

    if (action === "decrease" || action === "increase") {
        updateQty(stockId, quantity);
        return;
    }

    if (action === "remove") {
        removeFromCart(stockId);
    }
}

function handleCartAction(event) {
    const button = event.target.closest("[data-cart-action]");

    if (!button) return;

    const action = button.dataset.cartAction;

    if (action === "checkout") {
        goToCheckout();
        return;
    }

    if (action === "clear-all") {
        handleClearCart();
        return;
    }

    const stockId = button.dataset.stockId;
    const quantity = Number(button.dataset.quantity);

    if (action === "decrease" || action === "increase") {
        updateQty(stockId, quantity);
        return;
    }

    if (action === "remove") {
        removeFromCart(stockId);
    }
}
function handleCartAction(event) {
    const button = event.target.closest("[data-cart-action]");

    if (!button) return;

    const action = button.dataset.cartAction;
	
	if(action==="checkout"){
				goToCheckout();
				return;
			}
    const stockId = button.dataset.stockId;
    const quantity = Number(button.dataset.quantity);

    if (action === "decrease" || action === "increase") {
        updateQty(stockId, quantity);
        return;
    }

    if (action === "remove") {
        removeFromCart(stockId);
    }
	
	
}
function handleGlobalClick(event) {
    const addButton = event.target.closest(
        "[data-action='add-to-cart']"
    );

    if (addButton) {
        handleProductClick(event);
        return;
    }

    const cartAction = event.target.closest(
        "[data-cart-action]"
    );

    if (cartAction) {
        handleCartAction(event);
        return;
    }

    const orderButton = event.target.closest(
        "[data-action='place-order']"
    );

    if (orderButton) {
        placeOrder();
    }
}

function initializePage() {

    if (typeof renderProducts === "function") {
        renderProducts();
    }

    if (typeof renderCart === "function") {
        renderCart();
    }

    if (typeof renderOrderSummary === "function") {
        renderOrderSummary();
    }

    if (typeof updateBadge === "function") {
        updateBadge();
    }
}

document.addEventListener("click", handleGlobalClick);

document.addEventListener(
    "DOMContentLoaded",
    initializePage
);