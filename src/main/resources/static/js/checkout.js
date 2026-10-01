const PAYMENT_DETAILS = {
    KBZ_BANK: {
        title: "KBZ Bank",
        account: "Account: Please configure the shop KBZ account",
        name: "Account Name: Please configure the account name"
    },
    AYA_BANK: {
        title: "AYA Bank",
        account: "Account: Please configure the shop AYA account",
        name: "Account Name: Please configure the account name"
    },
    CB_BANK: {
        title: "CB Bank",
        account: "Account: Please configure the shop CB account",
        name: "Account Name: Please configure the account name"
    },
    MOBILE_PAY: {
        title: "K Pay / AYA Pay / CB Pay / Wave Pay",
        account: "Account: Please configure the mobile payment account",
        name: "Account Name: Please configure the account name"
    }
};

function buildCheckoutPayload(
    fullName,
    shippingAddress,
    phoneNo,
    paymentMethod,
    orderNotes
) {
    const cart = getCart();

    if (cart.length === 0) {
        throw new Error("Cart is empty.");
    }

    return {
        fullName,
        shippingAddress,
        phoneNo,
        paymentMethod,
        orderNotes,
        subtotalAmount: getCartTotal(),
        items: cart.map(item => ({
            stockId: item.stockId,
            price: item.price,
            quantity: item.quantity,
            subtotal: item.subtotal
        }))
    };
}

function clearFieldError(fieldId) {
    const field = document.getElementById(fieldId);
    const error = document.getElementById(`${fieldId}-error`);

    if (field) {
        field.classList.remove("is-invalid");
    }

    if (error) {
        error.textContent = "";
        error.hidden = true;
    }
}

function showFieldError(fieldId, message) {
    const field = document.getElementById(fieldId);
    const error = document.getElementById(`${fieldId}-error`);

    if (field) {
        field.classList.add("is-invalid");
    }

    if (error) {
        error.textContent = message;
        error.hidden = false;
    }
}

function clearValidationErrors() {
    clearFieldError("full-name");
    clearFieldError("shipping-address");
    clearFieldError("phone-no");
    clearFieldError("payment-method");
    clearFieldError("payment-proof");
}

function getSelectedPaymentMethod() {
    return document.querySelector(
        "input[name='payment-method']:checked"
    )?.value || "";
}

function validateCheckoutForm() {
    const fullName = document.getElementById("full-name")?.value.trim() || "";
    const address = document.getElementById("shipping-address")?.value.trim() || "";
    const phone = document.getElementById("phone-no")?.value.trim() || "";
    const payment = getSelectedPaymentMethod();

    clearValidationErrors();

    let isValid = true;

    if (!fullName) {
        showFieldError("full-name", "Please enter your full name.");
        isValid = false;
    }

    if (!address) {
        showFieldError("shipping-address", "Please enter your shipping address.");
        isValid = false;
    }

    if (!phone) {
        showFieldError("phone-no", "Please enter your phone number.");
        isValid = false;
    }

    if (!payment) {
        showFieldError("payment-method", "Please select a payment method.");
        isValid = false;
    }

    return isValid;
}

function selectPaymentOption(paymentMethod) {
    document.querySelectorAll(".payment-option").forEach(option => {
        option.classList.toggle(
            "selected",
            option.dataset.payment === paymentMethod
        );
    });

    const radio = document.querySelector(
        `input[name='payment-method'][value='${paymentMethod}']`
    );

    if (radio) {
        radio.checked = true;
    }

    clearFieldError("payment-method");

    const details = document.getElementById("payment-details");
    const accountInfo = document.getElementById("payment-account-info");

    if (paymentMethod === "COD") {
        details.hidden = true;
        return;
    }

    const payment = PAYMENT_DETAILS[paymentMethod];

    if (!payment) {
        details.hidden = true;
        return;
    }

    accountInfo.innerHTML = `
        <h4>${payment.title}</h4>
        <p>${payment.account}</p>
        <p>${payment.name}</p>
    `;

    details.hidden = false;
}

function handlePaymentSelection(event) {
    const option = event.target.closest(".payment-option");

    if (!option) {
        return;
    }

    selectPaymentOption(option.dataset.payment);
}

function handlePaymentProofChange(event) {
    const input = event.target;

    if (input.id !== "payment-proof") {
        return;
    }

    const file = input.files[0];
    const nameElement = document.getElementById("payment-proof-name");

    if (!file) {
        nameElement.hidden = true;
        nameElement.textContent = "";
        return;
    }

    nameElement.textContent = `Selected: ${file.name}`;
    nameElement.hidden = false;
    clearFieldError("payment-proof");
}

async function placeOrder() {
    const cart = getCart();

    if (cart.length === 0) {
        throw new Error(
            "Your cart is empty. Please add items before checkout."
        );
    }

    if (!validateCheckoutForm()) {
        return;
    }

    setButtonLoading(true);

    const fullName = document.getElementById("full-name").value.trim();
    const shippingAddress = document.getElementById("shipping-address").value.trim();
    const phoneNo = document.getElementById("phone-no").value.trim();
    const paymentMethod = getSelectedPaymentMethod();
    const orderNotes = document.getElementById("order-notes")?.value.trim() || "";

    const payload = buildCheckoutPayload(
        fullName,
        shippingAddress,
        phoneNo,
        paymentMethod,
        orderNotes
    );

    const result = await createOrder(payload);

    clearCart();

    window.location.href =
        `./order-success.html?orderNumber=${encodeURIComponent(result.orderNumber)}`;
}

function renderOrderSummary() {
    const container = document.getElementById("order-summary-items");
    const totalElement = document.getElementById("order-grand-total");

    if (!container) return;

    const cart = getCart();

    if (cart.length === 0) {
        container.innerHTML = `<p class="text-muted small">No items in cart.</p>`;
        if (totalElement) totalElement.textContent = "0 MMK";
        return;
    }

    container.innerHTML = cart.map(item => `
        <div class="order-summary-item">
            <span>
                ${item.productName} (${item.colour}/${item.size}) ×${item.quantity}
            </span>
            <span>${item.subtotal.toLocaleString()} MMK</span>
        </div>
    `).join("");

    if (totalElement) {
        totalElement.textContent = `${getCartTotal().toLocaleString()} MMK`;
    }
}

function showMessage(type, message) {
    const existing = document.getElementById("checkout-alert");
    if (existing) existing.remove();

    const button = document.getElementById("place-order-btn");

    if (!button) {
        showToast(message, type === "success" ? "success" : "danger");
        return;
    }

    const alertDiv = document.createElement("div");
    alertDiv.id = "checkout-alert";
    alertDiv.className = `alert ${type === "success" ? "alert-success" : "alert-danger"} mt-3`;
    alertDiv.textContent = `${type === "success" ? "✅" : "❌"} ${message}`;
    button.parentElement.insertBefore(alertDiv, button);
}

function setButtonLoading(isLoading) {
    const button = document.getElementById("place-order-btn");
    if (!button) return;

    button.disabled = isLoading;
    button.innerHTML = isLoading
        ? `<span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>Placing Order...`
        : "Place Order";
}

function showToast(message, type = "success") {
    const element = document.getElementById("toast-msg");
    const body = document.getElementById("toast-body");
    if (!element || !body) return;

    element.className = `toast align-items-center text-bg-${type} border-0`;
    body.textContent = message;

    bootstrap.Toast.getOrCreateInstance(element, { delay: 2500 }).show();
}

document.addEventListener("click", handlePaymentSelection);
document.addEventListener("change", handlePaymentProofChange);
