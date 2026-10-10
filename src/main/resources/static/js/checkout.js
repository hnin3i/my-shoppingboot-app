const PAYMENT_DETAILS = {
    KBZ_BANK: {
        title: "KBZ Bank",
        account: "200xxxxxxxxxxxx",
        name: "BLACKJACK Clothing",
        logo: "/images/bank/kbz.jpg",
        qrImage: "/images/qr/qr.png"
    },
    AYA_BANK: {
        title: "AYA Bank",
        account: "40038396418",
        name: "BLACKJACK Clothing",
        logo: "/images/bank/aya.png",
        qrImage: "/images/qr/qr.png"
    },
    CB_BANK: {
        title: "CB Bank",
        account: "000xxxxxxxxxxxx",
        name: "BLACKJACK Clothing",
        logo: "/images/bank/cb.jpg",
        qrImage: "/images/qr/qr.png"
    },
    MOBILE_PAY: {
        title: "K Pay / Mobile Pay",
        account: "09xxxxxxxxx",
        name: "BLACKJACK Clothing",
        logo: "/images/bank/kpay.png",
        qrImage: "/images/qr/qr.png"
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

    // 3. Inject Chic & Standardized UI Layout
    wrapper.innerHTML = `
        <div class="payment-detail-card border rounded-3 p-3 mt-3 bg-white" style="border-color: #e5e7eb !important; font-family: system-ui, -apple-system, sans-serif;">
            <div class="row align-items-center g-3">
                <!-- Left: Standardized QR Section (Compact Square Aspect Ratio) -->
                <div class="col-12 col-sm-4 col-md-4 text-center border-end-sm pe-sm-3">
                    <span class="text-uppercase tracking-wide text-muted fw-semibold d-block mb-2" style="font-size: 0.7rem; letter-spacing: 0.08em;">Scan QR Code</span>
                    <div class="qr-container mx-auto p-1 bg-white border rounded-2" 
                         style="width: 120px; height: 120px; display: flex; align-items: center; justify-content: center; border-color: #f1f5f9 !important;">
                        <img src="${payment.qrImage}" alt="Payment QR Code" class="img-fluid rounded" style="object-fit: contain; max-height: 100%; max-width: 100%;">
                    </div>
                    <a href="${payment.qrImage}" download="QR-${paymentMethod}.png" 
                       class="btn btn-link text-decoration-none text-dark p-0 mt-2 fw-medium d-inline-flex align-items-center gap-1" style="font-size: 0.75rem;">
                        <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" fill="currentColor" viewBox="0 0 16 16">
                            <path d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"/>
                            <path d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z"/>
                        </svg>
                        <span>Download QR</span>
                    </a>
                </div>

                <!-- Right: Chic Account Info & Upload Zone -->
                <div class="col-12 col-sm-8 col-md-8 ps-sm-3">
                    <div class="p-2.5 rounded-2 mb-2 bg-light border-0" style="background-color: #f8fafc !important;">
                        <div class="d-flex justify-content-between align-items-center mb-1">
                            <span class="text-muted" style="font-size: 0.78rem;">Account Name</span>
                            <span class="fw-semibold text-dark" style="font-size: 0.82rem;">${payment.name}</span>
                        </div>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="text-muted" style="font-size: 0.78rem;">Account Number</span>
                            <span class="fw-bold text-dark font-monospace" style="font-size: 0.85rem; letter-spacing: 0.03em;">${payment.account}</span>
                        </div>
                    </div>

                    <!-- Modern Minimalist Dropzone -->
                    <div class="payment-upload-section mt-2">
                        <div class="d-flex justify-content-between align-items-center mb-1">
                            <label class="form-label fw-semibold text-dark m-0" style="font-size: 0.8rem;">
                                Payment Proof <span class="text-danger">*</span>
                            </label>
                            <span class="text-muted" style="font-size: 0.7rem;">Max 5 MB • JPG, PNG, WEBP</span>
                        </div>

                        <!-- Initial Upload Dropzone -->
                        <label id="payment-proof-dropzone" for="payment-proof" class="custom-dashed-upload d-flex align-items-center justify-content-center gap-2 p-2.5 rounded-2 cursor-pointer border border-dashed" style="border-color: #cbd5e1 !important; background-color: #f8fafc; transition: background 0.15s ease;">
                            <span class="text-secondary" style="font-size: 0.9rem;">↥</span>
                            <span class="text-dark fw-medium" style="font-size: 0.78rem;">Upload payment screenshot</span>
                        </label>

                        <!-- Hidden Input -->
                        <input id="payment-proof" type="file" accept=".jpg,.jpeg,.png,.webp,image/jpeg,image/png,image/webp" hidden>

                        <!-- Image Preview Card Container -->
                        <div id="payment-proof-preview" class="mt-2" style="display: none;" hidden></div>
                        <div id="payment-proof-error" class="field-error text-danger mt-1" style="font-size: 0.75rem;" hidden></div>
                    </div>
                </div>
            </div>
        </div>
    `;

    wrapper.hidden = false;
}

function validatePaymentProof(file) {
    if (!file) {
        return "Please upload your payment proof.";
    }

    const allowedTypes = [
        "image/jpeg",
        "image/png",
        "image/webp"
    ];

    if (!allowedTypes.includes(file.type)) {
        return "Only JPG, PNG, and WEBP images are allowed.";
    }

    const maxSize = 5 * 1024 * 1024;

    if (file.size > maxSize) {
        return "Payment proof must be smaller than 5 MB.";
    }

    return "";
}

function handlePaymentProofChange(event) {
    const input = event.target;

    if (!input || input.id !== "payment-proof") {
        return;
    }

    const file = input.files ? input.files[0] : null;
    const previewContainer = document.getElementById("payment-proof-preview");
    const dropzoneLabel = document.getElementById("payment-proof-dropzone");

    // If no file selected
    if (!file) {
        if (previewContainer) {
            previewContainer.hidden = true;
            previewContainer.style.setProperty("display", "none", "important");
            previewContainer.innerHTML = "";
        }
        if (dropzoneLabel) {
            dropzoneLabel.hidden = false;
            dropzoneLabel.classList.add("d-flex");
            dropzoneLabel.classList.remove("d-none");
            dropzoneLabel.style.setProperty("display", "flex", "important");
        }
        clearFieldError("payment-proof");
        return;
    }

    // Validate file
    const errorMessage = validatePaymentProof(file);
    if (errorMessage) {
        input.value = "";
        if (previewContainer) {
            previewContainer.hidden = true;
            previewContainer.style.setProperty("display", "none", "important");
            previewContainer.innerHTML = "";
        }
        if (dropzoneLabel) {
            dropzoneLabel.hidden = false;
            dropzoneLabel.classList.add("d-flex");
            dropzoneLabel.classList.remove("d-none");
            dropzoneLabel.style.setProperty("display", "flex", "important");
        }
        showFieldError("payment-proof", errorMessage);
        return;
    }

    // Format current date
    const formattedDate = new Date().toLocaleDateString('en-US', {
        month: 'short',
        day: '2-digit',
        year: 'numeric'
    });

    const imageSrc = URL.createObjectURL(file);

    if (previewContainer) {
        previewContainer.innerHTML = `
            <div class="payment-preview-card p-2 bg-light border rounded-2 d-flex align-items-center justify-content-between" style="border-color: #e2e8f0 !important;">
                <div class="d-flex align-items-center gap-2 overflow-hidden">
                    <img src="${imageSrc}" alt="Payment Proof" class="preview-thumbnail rounded object-fit-cover flex-shrink-0" style="width: 42px; height: 42px;">
                    <div class="text-truncate">
                        <strong class="d-block text-dark fw-medium text-truncate mb-0" style="font-size: 0.8rem;">Payment screenshot</strong>
                        <span class="text-muted" style="font-size: 0.72rem;">${formattedDate}</span>
                    </div>
                </div>
                <button type="button" id="remove-proof-btn" class="btn-close text-secondary ms-2 flex-shrink-0" style="font-size: 0.65rem;" aria-label="Remove image"></button>
            </div>
        `;
        previewContainer.hidden = false;
        previewContainer.style.setProperty("display", "block", "important");
    }

    if (dropzoneLabel) {
        dropzoneLabel.hidden = true;
        dropzoneLabel.classList.remove("d-flex");
        dropzoneLabel.classList.add("d-none");
        dropzoneLabel.style.setProperty("display", "none", "important");
    }

    clearFieldError("payment-proof");
}

// Global Click Delegation
document.addEventListener("click", function (event) {
    // 1. Place Order Button Clicked
    const placeOrderBtn = event.target.closest("[data-action='place-order']");
    if (placeOrderBtn) {
        event.preventDefault();
        placeOrder().catch(err => {
            console.error("Place Order Error:", err);
            showMessage("danger", err.message || "Failed to place order.");
            setButtonLoading(false);
        });
        return;
    }

    // 2. Remove Image 'X' Button Clicked
    const removeBtn = event.target.closest("#remove-proof-btn");
    if (removeBtn) {
        event.preventDefault();
        event.stopPropagation();

        const input = document.getElementById("payment-proof");
        const previewContainer = document.getElementById("payment-proof-preview");
        const dropzoneLabel = document.getElementById("payment-proof-dropzone");

        if (input) input.value = "";

        if (previewContainer) {
            previewContainer.hidden = true;
            previewContainer.style.setProperty("display", "none", "important");
            previewContainer.innerHTML = "";
        }

        if (dropzoneLabel) {
            dropzoneLabel.hidden = false;
            dropzoneLabel.classList.add("d-flex");
            dropzoneLabel.classList.remove("d-none");
            dropzoneLabel.style.setProperty("display", "flex", "important");
        }

        clearFieldError("payment-proof");
        return;
    }

    // 3. Payment Method Switch Handlers
    const header = event.target.closest(".payment-option-header");
    if (header) {
        const option = event.target.closest(".payment-option");
        if (option && option.dataset.payment) {
            selectPaymentOption(option.dataset.payment);
        }
    }
});

document.addEventListener("change", handlePaymentProofChange, true);

document.addEventListener("DOMContentLoaded", () => {
    renderOrderSummary();
});



let isPlacingOrder = false; // Flag to prevent multi-triggering

async function placeOrder() {
    
    if (isPlacingOrder) return;

    const cart = getCart(); 

    if (cart.length === 0) {
        throw new Error("Your cart is empty. Please add items before checkout."); 
    }

    const paymentMethod = getSelectedPaymentMethod(); 

    if (!validateCheckoutForm()) { 
        return;
    }

    const paymentProof = document.getElementById("payment-proof")?.files[0] || null; 

    if (paymentMethod !== "COD") { 
        const paymentProofError = validatePaymentProof(paymentProof); 

        if (paymentProofError) { 
            showFieldError("payment-proof", paymentProofError); 
            return;
        }
    }

    try {
        isPlacingOrder = true;
        setButtonLoading(true); 

        const firstName = document.getElementById("first-name")?.value.trim() || ""; //[cite: 5]
        const lastName = document.getElementById("last-name")?.value.trim() || ""; //[cite: 5]
        const fullName = `${firstName} ${lastName}`.trim(); //[cite: 5]

        const street = document.getElementById("address-street")?.value.trim() || ""; //[cite: 5]
        const apartment = document.getElementById("address-apartment")?.value.trim() || ""; //[cite: 5]
        const city = document.getElementById("address-city")?.value.trim() || ""; //[cite: 5]
        const postal = document.getElementById("address-postal")?.value.trim() || ""; //[cite: 5]

        const shippingAddress = `${street}${apartment ? ", " + apartment : ""}, ${city}${postal ? " " + postal : ""}, Myanmar`.trim(); //[cite: 5]

        const phoneNo = document.getElementById("phone-no")?.value.trim() || ""; //[cite: 5]
        const orderNotes = document.getElementById("order-notes")?.value.trim() || ""; //[cite: 5]

        const payload = buildCheckoutPayload( //[cite: 5]
            fullName,
            shippingAddress,
            phoneNo,
            paymentMethod,
            orderNotes
        );

        const result = await createOrder(payload, paymentProof); //[cite: 5]

        // Clear cart after placing order
        clearCart();

        window.location.href = `/user/orders`; //[cite: 5]
    } catch (error) {
        isPlacingOrder = false;
        setButtonLoading(false); //[cite: 5]
        throw error;
    }
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

	container.innerHTML = cart.map(item => {
	        const itemSubtotal = Number(item.subtotal || 0);
	        const priceDisplay = itemSubtotal === 0 ? "FREE" : `${itemSubtotal.toLocaleString()} MMK`;

	        return `
	            <div class="order-summary-item d-flex align-items-center justify-content-between py-2 mb-2 pe-2">
	                <div class="d-flex align-items-center gap-3 overflow-visible">
	                    <!-- Image Wrapper (overflow: visible allows the badge to overlap cleanly) -->
	                    <div class="position-relative flex-shrink-0" style="width: 60px; height: 60px; margin-top: 4px; margin-right: 4px;">
	                        <!-- Thumbnail Box -->
	                        <div class="w-100 h-100 rounded-3 bg-white border border-slate-200 p-1 d-flex align-items-center justify-content-center shadow-xs overflow-hidden">
	                            <img src="${item.image || ''}" alt="${item.productName}" class="w-100 h-100 object-fit-contain rounded-2">
	                        </div>

	                        <!-- Quantity Badge (Positioned at top-right with high z-index and white text) -->
	                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-circle bg-black text-white d-flex align-items-center justify-content-center shadow-sm" 
	                              style="width: 20px; height: 20px; font-size: 0.7rem; font-weight: 700; z-index: 10; padding: 0; line-height: 1; border: 1.5px solid #ffffff;">
	                            ${item.quantity}
	                        </span>
	                    </div>

	                    <!-- Item Details -->
	                    <div class="text-truncate ps-1">
	                        <span class="d-block text-dark fw-bold text-uppercase text-truncate mb-0" style="font-size: 0.82rem; letter-spacing: 0.03em;">
	                            ${item.productName}
	                        </span>
	                        ${item.colour || item.size ? `
	                            <small class="d-block text-secondary text-uppercase text-truncate" style="font-size: 0.73rem; letter-spacing: 0.02em;">
	                                ${[item.colour, item.size].filter(Boolean).join(" / ")}
	                            </small>
	                        ` : ''}
	                    </div>
	                </div>

	                <!-- Price Display -->
	                <div class="fw-semibold text-dark text-nowrap ms-2" style="font-size: 0.85rem;">
	                    ${priceDisplay}
	                </div>
	            </div>
	        `;
	    }).join("");

    const subtotal = getCartTotal();
    const shippingFee = 5000;
    const taxRate = 5;
    const taxAmount = subtotal * (taxRate / 100);
    const grandTotal = subtotal + shippingFee + taxAmount;

    if (itemCountElement) itemCountElement.textContent = getCartCount();
    if (subtotalElement) subtotalElement.textContent = `${subtotal.toLocaleString()} MMK`;
    if (shippingElement) shippingElement.textContent = `${shippingFee.toLocaleString()} MMK`;
    if (taxElement) taxElement.textContent = `${Math.round(taxAmount).toLocaleString()} MMK`;
    if (totalElement) totalElement.textContent = `${Math.round(grandTotal).toLocaleString()} MMK`;
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
    const mobileButton = document.getElementById("place-order-btn-mobile");

    [button, mobileButton].forEach(btn => {
        if (!btn) return;
        btn.disabled = isLoading;
        btn.innerHTML = isLoading
            ? `<span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>Placing Order...`
            : "Place Order";
    });
}

function showToast(message, type = "success") {
    const element = document.getElementById("toast-msg");
    const body = document.getElementById("toast-body");
    if (!element || !body) return;

    element.className = `toast align-items-center text-bg-${type} border-0`;
    body.textContent = message;

    bootstrap.Toast.getOrCreateInstance(element, { delay: 2500 }).show();
}