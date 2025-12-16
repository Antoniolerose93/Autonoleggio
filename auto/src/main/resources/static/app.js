document.addEventListener("DOMContentLoaded", function() {
    const brandSelect = document.getElementById("brandSelect");
    const modelSelect = document.getElementById("modelSelect");

    brandSelect.addEventListener("change", () => {
        const brand = brandSelect.value;
        modelSelect.innerHTML = '<option value="">Scegli il modello</option>'; // reset

        if (brand) {
            fetch(`/auto/models?brand=${encodeURIComponent(brand)}`)
                .then(response => response.json())
                .then(data => {
                    data.forEach(model => {
                        const option = document.createElement("option");
                        option.value = model;
                        option.textContent = model;
                        modelSelect.appendChild(option);
                    });
                })
                .catch(err => console.error("Errore nel fetch dei modelli:", err));
        }
    });
});