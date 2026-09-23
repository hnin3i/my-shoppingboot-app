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

function renderCart() {
    const container = document.getElementById("cart-container");

    if (!container) return;

    const cart = getCart();

    if (cart.length === 0) {
        container.innerHTML = `
            <div class="text-center py-5 text-muted">
                <div class="display-4">🛒</div>
                <p>Your cart is empty.</p>
                <a href="./index.html" class="btn btn-outline-primary">
                    Browse Products
                </a>
            </div>
        `;
        return;
    }

    container.innerHTML = `
        <div class="card">
            <div class="table-responsive">
                <table class="table table-borderless align-middle mb-0">
                    <thead class="table-light">
                        <tr>
                            <th>Product</th>
                            <th>Variant</th>
                            <th>Price</th>
                            <th>Qty</th>
                            <th>Subtotal</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        ${cart.map(item => `
                            <tr>
                                <td>
                                    <div class="d-flex align-items-center gap-2">
                                        <img
                                            src="${item.image}"
                                            width="48"
                                            height="48"
                                            style="object-fit:cover;border-radius:6px;"
                                            alt="${item.productName}"
                                        >
                                        <span class="fw-semibold">
                                            ${item.productName}
                                        </span>
                                    </div>
                                </td>
                                <td>
                                    <span class="badge bg-secondary">
                                        ${item.colour} / ${item.size}
                                    </span>
                                </td>
                                <td>
                                    ${item.price.toLocaleString()} MMK
                                </td>
                                <td>
                                    <div class="d-flex align-items-center gap-1">
                                        <button
                                            class="btn btn-sm btn-outline-secondary"
                                            data-cart-action="decrease"
                                            data-stock-id="${item.stockId}"
                                            data-quantity="${item.quantity - 1}"
                                        >−</button>
                                        <span class="px-2">${item.quantity}</span>
                                        <button
                                            class="btn btn-sm btn-outline-secondary"
                                            data-cart-action="increase"
                                            data-stock-id="${item.stockId}"
                                            data-quantity="${item.quantity + 1}"
                                        >+</button>
                                    </div>
                                </td>
                                <td class="fw-bold">
                                    ${item.subtotal.toLocaleString()} MMK
                                </td>
                                <td>
                                    <button
                                        class="btn btn-sm btn-outline-danger"
                                        data-cart-action="remove"
                                        data-stock-id="${item.stockId}"
                                    >🗑</button>
                                </td>
                            </tr>
                        `).join("")}
                    </tbody>
                    <tfoot class="table-light">
                        <tr>
                            <td colspan="4" class="text-end fw-bold fs-5">
                                Grand Total:
                            </td>
                            <td colspan="2" class="fw-bold fs-5 text-primary">
                                ${getCartTotal().toLocaleString()} MMK
                            </td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    `;
}

function handleCartAction(event) {
    const button = event.target.closest("[data-cart-action]");

    if (!button) return;

    const action = button.dataset.cartAction;
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
