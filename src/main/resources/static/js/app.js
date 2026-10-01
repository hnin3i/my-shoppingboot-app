function handleGlobalClick(event) {
    const addButton = event.target.closest(
        "[data-action='add-to-cart']"
    );

    if (addButton) {
        handleProductClick(event);
        return;
    }

    const cartAction = event.target.closest("[data-cart-action]");

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
    renderProducts();
    renderCart();
    renderOrderSummary();
    updateBadge();
}

document.addEventListener("click", handleGlobalClick);
document.addEventListener("DOMContentLoaded", initializePage);
