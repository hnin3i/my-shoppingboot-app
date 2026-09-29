
async function fetchWithConnectionCheck(url, options) {
    return fetch(url, options).catch(() => {
        throw new Error("NO_CONNECTION");
    });
}


async function createOrder(payload) {
	try{
		const BASE_URL = "http://localhost:8080";
		console.log(`${BASE_URL}/api/orders/place`);
		    const response = await fetchWithConnectionCheck(`${BASE_URL}/api/orders/place`, {
		        method: "POST",
		        headers: {
		            "Content-Type": "application/json"
		        },
		        body: JSON.stringify(payload)
		    });

		    if (response.ok) {
		        return response.json();
				
		    }

			clearCart();
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
			
			console.log('Response => ' + response.status)

		    throw new Error("Something went wrong on the server.");
	}
	catch(err){
		console.log(err)
		throw new Error("Something went wrong on the server.");
		
	}
	
}
