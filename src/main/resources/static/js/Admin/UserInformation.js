document.addEventListener("DOMContentLoaded", () => {
    const buttons = document.querySelectorAll(".edit-button");

    buttons.forEach(button => {
        button.addEventListener("click", (event) => {
            // Cegah pengiriman form otomatis
            event.preventDefault();

            const userName = button.closest("tr").querySelector("td:nth-child(2)").textContent;

            // Update the user name in the modal
            document.getElementById("userName").textContent = userName;

            // Show the modal
            const modal = document.getElementById("confirmModal");
            modal.style.display = "flex";

            // If Yes is clicked, send request to update status
            document.getElementById("confirmYes").onclick = async () => {
                try {
                    const response = await fetch(`/admin/updateStatus?name=${userName}`, {
                        method: "GET"
                    });
                    if (response.ok) {
                        modal.style.display = "none"; // Hide the modal after action
                        location.reload(); // Refresh the page
                    } else {
                        alert("Failed to update status.");
                    }
                } catch (error) {
                    console.error(error);
                    alert("An error occurred. Please try again.");
                }
            };            

            // If No is clicked, just hide the modal
            document.getElementById("confirmNo").onclick = () => {
                modal.style.display = "none";
            };
        });
    });
});