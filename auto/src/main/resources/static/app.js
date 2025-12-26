document.addEventListener("DOMContentLoaded", function() {
    const brandSelect = document.getElementById("brandSelect");
    const modelSelect = document.getElementById("modelSelect");

    //Listiner che scatta quando cambia il valore selezionato
    brandSelect.addEventListener("change", () => {
        const brand = brandSelect.value;
        //reset
        modelSelect.innerHTML = '<option value="">Scegli il modello</option>';

        if (brand) {
            //Esegue una richiesta fetch verso il server per ottenere i modelli del brand selezionato
            fetch(`/auto/models?brand=${encodeURIComponent(brand)}`)
                // Converte la risposta in JSON
                .then(response => response.json())
                // Una volta ottenuti i dati, li utilizza per popolare il select dei modelli
                .then(data => {
                    // data è un array di modelli
                    data.forEach(model => {
                        const option = document.createElement("option");
                        option.value = model;
                        // imposta il testo visibile
                        option.textContent = model;
                        // Aggiunge l'opzione al select dei modelli
                        modelSelect.appendChild(option);
                    });
                })
                // Se c'è un errore nel fetch, lo logga in console
                .catch(err => console.error("Errore nel fetch dei modelli:", err));
        }
    });
});