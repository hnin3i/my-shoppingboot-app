async function fetchWithConnectionCheck(url, options) {
    return fetch(url, options).catch(() => {
        throw new Error("NO_CONNECTION");
    });
}

async function createOrder(payload) {
    const response = await fetchWithConnectionCheck("/api/orders", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
    });

    if (response.ok) {
        return response.json();
    }

    if (response.status === 400) {
        const errorBody = await response.json();

        throw new Error(
            errorBody.message ||
            "Stock issue. Please review your cart."
        );
    }

    if (response.status === 401) {
        throw new Error("SESSION_EXPIRED");
    }

    throw new Error("Something went wrong on the server.");
}
