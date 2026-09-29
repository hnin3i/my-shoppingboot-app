const PAYMENT_DETAILS = {
    KBZ_BANK: {
        title: "KBZ Bank",
        account: "200xxxxxxxxxxxx",
        name: "BLACKJACK Clothing",
        logo: "/images/logos/kbz-logo.png",
        qrImage: "/images/qr/kbz-qr.png"
    },
    AYA_BANK: {
        title: "AYA Bank",
        account: "40038396418",
        name: "BLACKJACK Clothing",
        logo: "/images/logos/aya-logo.png",
        qrImage: "/images/qr/aya-qr.png"
    },
    CB_BANK: {
        title: "CB Bank",
        account: "000xxxxxxxxxxxx",
        name: "BLACKJACK Clothing",
        logo: "/images/logos/cb-logo.png",
        qrImage: "/images/qr/cb-qr.png"
    },
    MOBILE_PAY: {
        title: "K Pay / Mobile Pay",
        account: "09xxxxxxxxx",
        name: "BLACKJACK Clothing",
        logo: "/images/logos/kpay-logo.png",
        qrImage: "/images/qr/kpay-qr.png"
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
	    clearFieldError("contact-info");
	    clearFieldError("last-name");
	    clearFieldError("address-street");
	    clearFieldError("address-city");
	    clearFieldError("phone-no");
	    clearFieldError("payment-method");
	    clearFieldError("payment-proof");
}

function getSelectedPaymentMethod() {
    return document.querySelector(
        "input[name='payment-method']:checked"
    )?.value || "";
}

function loadLoggedInUserContact() {
    // ဥပမာ - LocalStorage သို့မဟုတ် User Session မှ User Info ယူခြင်း
    const currentUser = JSON.parse(localStorage.getItem("currentUser")) || {
        email: "user@example.com",
        phone: "09123456789"
    };

    const contactDisplay = document.getElementById("user-contact-display");
    if (contactDisplay) {
        contactDisplay.textContent = currentUser.email || currentUser.phone;
    }
}

// Page load ချိန်တွင် ပကတိအတိုင်း ခေါ်ပေးရန်


function validateCheckoutForm() {
	const lastName = document.getElementById("last-name")?.value.trim() || "";
	    const street = document.getElementById("address-street")?.value.trim() || "";
	    const city = document.getElementById("address-city")?.value.trim() || "";
	    const phone = document.getElementById("phone-no")?.value.trim() || "";
	    const payment = getSelectedPaymentMethod();
    clearValidationErrors();

    let isValid = true;
	if (!lastName) {
	        showFieldError("last-name", "Enter a last name");
	        isValid = false;
	    }

	    if (!street) {
	        showFieldError("address-street", "Enter an address");
	        isValid = false;
	    }

	    if (!city) {
	        showFieldError("address-city", "Enter a city");
	        isValid = false;
	    }

	    if (!phone) {
	        showFieldError("phone-no", "Enter a phone number");
	        isValid = false;
	    }

	    if (!payment) {
	        showFieldError("payment-method", "Please select a payment method.");
	        isValid = false;
	    }
    return isValid;
}

function selectPaymentOption(paymentMethod) {
    // 1. Clear and hide all open detail wrappers
    document.querySelectorAll(".payment-details-wrapper").forEach(wrapper => {
        wrapper.hidden = true;
        wrapper.innerHTML = "";
    });

    // 2. Toggle active selection styles
    document.querySelectorAll(".payment-option").forEach(option => {
        option.classList.toggle("selected", option.dataset.payment === paymentMethod);
    });

    const radio = document.querySelector(`input[name='payment-method'][value='${paymentMethod}']`);
    if (radio) radio.checked = true;

    clearFieldError("payment-method");

    // Cash on Delivery requires no expand card
    if (paymentMethod === "COD") return;

    const payment = PAYMENT_DETAILS[paymentMethod];
    const selectedOption = document.querySelector(`.payment-option[data-payment='${paymentMethod}']`);
    
    if (!payment || !selectedOption) return;

    const wrapper = selectedOption.querySelector(".payment-details-wrapper");
    if (!wrapper) return;

    // 3. Inject full UI layout under the selected option header
    wrapper.innerHTML = `
        <div class="custom-payment-card border rounded-3 p-4 bg-white">
            
            <div class="d-flex align-items-center gap-3 p-3 bg-light rounded-2 mb-4">
                <div class="check-icon-circle bg-dark text-white rounded-circle d-flex align-items-center justify-content-center" style="width: 24px; height: 24px;">
                    <span style="font-size: 14px;">✓</span>
                </div>
                ${payment.logo ? `<img src="${payment.logo}" alt="${payment.title}" class="rounded" style="width: 40px; height: 40px; object-fit: contain;">` : ''}
                <div>
                    <h6 class="fw-bold mb-0 text-dark">${payment.title}</h6>
                    <small class="text-muted">${payment.name}</small>
                </div>
            </div>

            <div class="text-center my-4">
                <p class="fw-medium text-dark mb-3">Scan to Pay</p>
                
                <div class="d-inline-block p-3 rounded-4 bg-dark mb-2">
                    <img src="${payment.qrImage}" alt="Payment QR Code" class="img-fluid rounded" style="width: 180px; height: 180px; object-fit: cover;">
                </div>
                
                <div>
                    <a href="${payment.qrImage}" download="QR-${paymentMethod}.png" class="btn btn-link text-primary text-decoration-none fw-medium small d-inline-flex align-items-center gap-1">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-download" viewBox="0 0 16 16">
                          <path d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"/>
                          <path d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z"/>
                        </svg>
                        Save QR Code
                    </a>
                </div>
            </div>

            <div class="mb-4 text-start">
                <p class="mb-1 text-dark"><strong>Account:</strong> ${payment.account}</p>
                <p class="mb-0 text-dark"><strong>Name:</strong> ${payment.name}</p>
            </div>

            <label for="payment-proof" class="custom-dashed-upload d-flex align-items-center gap-3 p-3 rounded-3 cursor-pointer">
                <div class="upload-icon-box fs-4 text-muted border rounded p-2 bg-light d-flex align-items-center justify-content-center" style="width: 42px; height: 42px;">
                    ↥
                </div>
                <div>
                    <strong class="d-block text-dark fw-bold">Upload screenshot</strong>
                    <small class="text-muted">Click to upload your payment proof</small>
                </div>
            </label>
            <input id="payment-proof" type="file" accept="image/*" hidden>
            <div id="payment-proof-name" class="selected-file text-success mt-2 small" hidden></div>
            <div id="payment-proof-error" class="field-error" hidden></div>

        </div>
    `;

    wrapper.hidden = false;
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

	const firstName = document.getElementById("first-name")?.value.trim() || "";
	    const lastName = document.getElementById("last-name")?.value.trim() || "";
	    const fullName = `${firstName} ${lastName}`.trim();

	    const street = document.getElementById("address-street")?.value.trim() || "";
	    const apartment = document.getElementById("address-apartment")?.value.trim() || "";
	    const city = document.getElementById("address-city")?.value.trim() || "";
	    const postal = document.getElementById("address-postal")?.value.trim() || "";
	    
	    const shippingAddress = `${street}${apartment ? ', ' + apartment : ''}, ${city}${postal ? ' ' + postal : ''}, Myanmar`.trim();

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



    window.location.href =
        `./order-success.html?orderNumber=${encodeURIComponent(result.orderNumber)}`;
}

function renderOrderSummary() {
	const container = document.getElementById("order-summary-items");
	    const itemCountElement = document.getElementById("order-item-count");
	    const subtotalElement = document.getElementById("order-subtotal");
	    const shippingElement = document.getElementById("order-shipping");
	    const taxElement = document.getElementById("order-tax");
	    const totalElement = document.getElementById("order-grand-total");

    if (!container) return;

    const cart = getCart();

    if (cart.length === 0) {
        container.innerHTML = `<p class="text-muted small">No items in cart.</p>`;
		if (itemCountElement) itemCountElement.textContent = "0";
		        if (subtotalElement) subtotalElement.textContent = "0 MMK";
		        if (shippingElement) shippingElement.textContent = "0 MMK";
		        if (taxElement) taxElement.textContent = "0 MMK";
		        if (totalElement) totalElement.textContent = "0 MMK";
        return;
    }

    container.innerHTML = cart.map(item => `
		<div class="order-summary-item">
		            <div class="order-item-image-wrapper">
		                <img
		                    src="${item.image || ""}"
		                    alt="${item.productName}"
		                    class="order-item-image"
		                >
		                <span class="order-item-quantity">
		                    ${item.quantity}
		                </span>
		            </div>

		            <div class="order-item-info">
		                <div class="order-item-name">
		                    ${item.productName}
		                </div>
		                <div class="order-item-variant">
		                    ${item.colour || ""}${item.size ? ` / ${item.size}` : ""}
		                </div>
		            </div>

		            <div class="order-item-price">
		                ${Number(item.subtotal || 0).toLocaleString()} MMK
		            </div>
		        </div>
    `).join("");
	
	const subtotal=getCartTotal();
	
	const shippingFee=5000;
	const taxRate=5;
	const taxAmount = subtotal * (taxRate / 100);
	   const grandTotal = subtotal + shippingFee + taxAmount;
	

	   if (itemCountElement) {
	          itemCountElement.textContent = getCartCount();
	      }

	      if (subtotalElement) {
	          subtotalElement.textContent =
	              `${subtotal.toLocaleString()} MMK`;
	      }

	      if (shippingElement) {
	          shippingElement.textContent =
	              `${shippingFee.toLocaleString()} MMK`;
	      }

	      if (taxElement) {
	          taxElement.textContent =
	              `${Math.round(taxAmount).toLocaleString()} MMK`+"(5.00%)";
	      }

	      if (totalElement) {
	          totalElement.textContent =
	              `${Math.round(grandTotal).toLocaleString()} MMK`;
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
document.addEventListener("DOMContentLoaded", () => {loadLoggedInUserContact();});
