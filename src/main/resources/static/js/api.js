
async function fetchWithConnectionCheck(url, options) {
    return fetch(url, options).catch(() => {
        throw new Error("NO_CONNECTION");
    });
}


async function createOrder(payload,paymentProof) {
	try{
		const BASE_URL = "http://localhost:8080";
		console.log(`${BASE_URL}/api/orders/place`);
		
		const formData=new FormData();
		formData.append("order",
			new Blob(
			[JSON.stringify(payload)],
			{
				type:"application/json"
			}	
			)
		);
		
		if(paymentProof){
			formData.append(
				"paymentProof",
				paymentProof
			);
		}
		    const response = await fetchWithConnectionCheck(
			    `${BASE_URL}/api/orders/place`, {
		        method: "POST",
		        body: formData
		    });

		    if (response.ok) {
				
				const result=await response.json();
				clearCart();
		        return result;
				
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
	}
	catch(err){
		console.log(err)
		throw err;
		
	}
	
}
