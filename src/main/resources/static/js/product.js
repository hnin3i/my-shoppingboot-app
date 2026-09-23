const MOCK_PRODUCTS = [
    {
        productId: "prod-001",
        productName: "Classic T-Shirt",
        image: "https://via.placeholder.com/150/FF6B6B/fff?text=T-Shirt",
        price: 15000,
        stocks: [
            { stockId: "stk-001a", colour: "Red", size: "M", stock_qty: 10 },
            { stockId: "stk-001b", colour: "Blue", size: "L", stock_qty: 5 }
        ]
    },
    {
        productId: "prod-002",
        productName: "Denim Jeans",
        image: "https://via.placeholder.com/150/4ECDC4/fff?text=Jeans",
        price: 35000,
        stocks: [
            { stockId: "stk-002a", colour: "Dark Blue", size: "32", stock_qty: 8 },
            { stockId: "stk-002b", colour: "Black", size: "34", stock_qty: 3 }
        ]
    },
    {
        productId: "prod-003",
        productName: "Sneakers",
        image: "https://via.placeholder.com/150/45B7D1/fff?text=Shoes",
        price: 55000,
        stocks: [
            { stockId: "stk-003a", colour: "White", size: "42", stock_qty: 6 },
            { stockId: "stk-003b", colour: "Black", size: "43", stock_qty: 2 }
        ]
    }
];

function renderProducts() {
    const grid = document.getElementById("product-grid");

    if (!grid) return;

    grid.innerHTML = MOCK_PRODUCTS.map(product => `
        <div class="col-sm-6 col-md-4">
            <div class="card product-card h-100 shadow-sm">
                <img
                    src="${product.image}"
                    class="card-img-top"
                    alt="${product.productName}"
                >
                <div class="card-body">
                    <h6 class="card-title">${product.productName}</h6>
                    <p class="text-primary fw-bold">
                        ${product.price.toLocaleString()} MMK
                    </p>
                    <div class="mb-2">
                        <label class="form-label small">Select Variant</label>
                        <select
                            id="variant-${product.productId}"
                            class="form-select form-select-sm"
                        >
                            ${product.stocks.map(stock => `
                                <option
                                    value="${stock.stockId}"
                                    data-colour="${stock.colour}"
                                    data-size="${stock.size}"
                                    data-maxqty="${stock.stock_qty}"
                                >
                                    ${stock.colour} / ${stock.size}
                                    (Stock: ${stock.stock_qty})
                                </option>
                            `).join("")}
                        </select>
                    </div>
                    <button
                        class="btn btn-primary btn-sm w-100"
                        data-action="add-to-cart"
                        data-product-id="${product.productId}"
                    >
                        Add to Cart
                    </button>
                </div>
            </div>
        </div>
    `).join("");
}

function handleAddToCart(productId) {
    const product = MOCK_PRODUCTS.find(
        item => item.productId === productId
    );

    if (!product) {
        throw new Error("Product not found.");
    }

    const select = document.getElementById(`variant-${productId}`);

    if (!select) {
        throw new Error("Product variant not found.");
    }

    const option = select.options[select.selectedIndex];

    if (!option) {
        throw new Error("Please select a product variant.");
    }

    const stockInfo = {
        stockId: option.value,
        productId: product.productId,
        productName: product.productName,
        image: product.image,
        colour: option.dataset.colour,
        size: option.dataset.size,
        maxQty: Number(option.dataset.maxqty),
        price: product.price
    };

    const result = addToCart(stockInfo);

    showToast(
        result.message,
        result.success ? "success" : "danger"
    );
}

function handleProductClick(event) {
    const button = event.target.closest(
        "[data-action='add-to-cart']"
    );

    if (!button) return;

    handleAddToCart(button.dataset.productId);
}
