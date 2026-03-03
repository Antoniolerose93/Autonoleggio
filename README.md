# 🚗 Noleggio auto (Spring Boot)

Un'applicazione per la gestione di un auto noleggio, sviluppata Spring Boot. 

Il progetto implementa un sistema completo di prenotazioni con controlli di disponibilità in tempo reale e gestione dei ruoli.

Funzionalità Principali:

🔍 Ricerca vetture
- Un sistema di ricerca dinamico permette filtrare le auto (Marca → Modello → Alimentazione) senza errori di combinazione.

📅 Sistema di Prenotazione
- Il sistema verifica se le vetture sono disponibili nelle date per cui gli utenti le richiedono;
- Messaggi automatici comunicano i periodi di disponibilità se l'auto è già stata prenotata;
- Calcolo automatico del costo totale in base ai giorni di noleggio e alla tariffa giornaliera.

🛡️ Sicurezza e Autorizzazioni
- Distinzione dei ruoli tra ADMIN e USER: 
  ADMIN: Accesso totale alla gestione flotta (CRUD), categorie e dashboard noleggi;
  USER: Navigazione catalogo e possibilità di effettuare prenotazioni.

🛠️ Stack:

- Spring Boot
- Java
- Thymeleaf
- Bootstrap
- MySQL

