function handleGlobalError(error) {
    console.error(error);

    if (error?.message === "SESSION_EXPIRED") {
        showMessage(
            "error",
            "Your session has expired. Please log in again."
        );

        setTimeout(() => {
            window.location.href = "/login";
        }, 2000);
        return;
    }

    if (error?.message === "NO_CONNECTION") {
        showMessage(
            "error",
            "No connection. Please check your internet."
        );
        setButtonLoading(false);
        return;
    }

    showMessage(
        "error",
        error?.message ||
        "Something went wrong. Please try again."
    );

    setButtonLoading(false);
}

window.addEventListener("error", event => {
    event.preventDefault();
    handleGlobalError(event.error);
});

window.addEventListener("unhandledrejection", event => {
    event.preventDefault();
    handleGlobalError(event.reason);
});
