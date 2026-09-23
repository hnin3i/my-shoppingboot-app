const CART_KEY = "shopping_cart";

function getCart() {
    const savedCart = localStorage.getItem(CART_KEY);

    if (!savedCart) {
        return [];
    }

    const cart = JSON.parse(savedCart);

    if (!Array.isArray(cart)) {
        throw new Error("Invalid cart data.");
    }

    return cart;
}

function saveCart(cart) {
    if (!Array.isArray(cart)) {
        throw new Error("Cart must be an array.");
    }

    localStorage.setItem(CART_KEY, JSON.stringify(cart));
}

function clearCart() {
    localStorage.removeItem(CART_KEY);
}

function getCartTotal() {
    return getCart().reduce(
        (total, item) => total + Number(item.subtotal || 0),
        0
    );
}

function getCartCount() {
    return getCart().reduce(
        (total, item) => total + Number(item.quantity || 0),
        0
    );
}
