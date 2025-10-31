# 🎬 Sistema di Gestione di Proiezioni Cinematografiche

## 📌 Obiettivo del Progetto
Il progetto ha come obiettivo la realizzazione di un sistema web per la **gestione delle proiezioni cinematografiche**.  
La piattaforma funge da **intermediario tra il cinema e gli utenti**, consentendo di:
- Registrarsi e gestire il proprio profilo;
- Consultare i film in programmazione;
- Prenotare posti per le proiezioni;
- Lasciare recensioni sui film e sulle proiezioni.

---

## 🧩 Descrizione del Sistema

### 👤 Gestione Utenti
- **Registrazione Utente:**  
  Gli utenti possono registrarsi inserendo nome, cognome, email e password.  
- **Gestione del Profilo:**  
  Ogni utente può modificare le proprie informazioni personali e aggiungere le **categorie di film preferite**, con un livello di priorità da 1 a 5.  
- **Visualizzazione Profilo:**  
  È possibile consultare e aggiornare in ogni momento il proprio profilo e le preferenze di visione.

---

### 🎥 Gestione Film
- **Creazione Film (solo amministratori):**  
  Gli amministratori possono aggiungere nuovi film, specificando:
  - Titolo  
  - Descrizione  
  - Data di rilascio  
  - Genere  
  - Durata  
- **Informazioni sui Film:**  
  Ogni film presenta una scheda dettagliata con trama, attori principali, genere, durata e data di uscita.  
- **Elenco Film:**  
  Gli utenti possono visualizzare tutti i film attualmente in programmazione.

---

### 🗓️ Gestione Proiezioni
- **Creazione Proiezioni (solo amministratori):**  
  È possibile creare proiezioni per i film, indicando data, orario e sala.  
- **Visualizzazione Proiezioni:**  
  Gli utenti possono consultare la lista delle proiezioni disponibili, con i relativi dettagli (film, orario, sala, posti disponibili).

---

### 🎟️ Prenotazioni e Recensioni
- **Prenotazione Posti:**  
  Gli utenti registrati possono prenotare i posti per le proiezioni desiderate.  
- **Recensioni:**  
  Dopo aver partecipato a una proiezione, gli utenti possono lasciare una recensione sia sul film che sull’esperienza di visione.

---

## ⚙️ Funzionalità Principali
| Funzionalità | Descrizione |
|---------------|-------------|
| 🔐 **Registrazione Utente** | Creazione account e gestione delle preferenze personali |
| 🎬 **Creazione Film** | Inserimento di nuovi film con dettagli completi (solo admin) |
| 🗓️ **Creazione Proiezioni** | Definizione di data, orario e sala (solo admin) |
| 🎟️ **Prenotazione Posti** | Selezione e prenotazione dei posti per una proiezione |
| 📝 **Inserimento Recensioni** | Valutazione di film e proiezioni dopo la visione |

---


## 💻 Installazione ed Esecuzione
1. Clona il repository:
   ```bash
   git clone https://github.com/<tuo-username>/<nome-repo>.git
