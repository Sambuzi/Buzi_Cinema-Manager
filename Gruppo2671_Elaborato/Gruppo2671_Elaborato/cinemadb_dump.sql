-- Creazione del database
CREATE DATABASE IF NOT EXISTS CinemaDB;

-- Selezione del database
USE CinemaDB;

-- Creazione della tabella Utenti
CREATE TABLE IF NOT EXISTS Utenti (
    ID_utente INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50),
    cognome VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(50),
    role VARCHAR(20)
);

-- Creazione della tabella Interessi
CREATE TABLE IF NOT EXISTS Interessi (
    ID_interesse INT PRIMARY KEY AUTO_INCREMENT,
    ID_utente INT,
    categoria VARCHAR(50),
    priorita INT,
    FOREIGN KEY (ID_utente) REFERENCES Utenti(ID_utente)
);

-- Creazione della tabella Film
CREATE TABLE IF NOT EXISTS Film (
    ID_film INT PRIMARY KEY AUTO_INCREMENT,
    titolo VARCHAR(100),
    descrizione TEXT,
    data_rilascio DATE,
    genere VARCHAR(50),
    durata INT
);

-- Creazione della tabella Sale
CREATE TABLE IF NOT EXISTS Sale (
    ID_sala INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50)
);

-- Creazione della tabella Proiezioni
CREATE TABLE IF NOT EXISTS Proiezioni (
    ID_proiezione INT PRIMARY KEY AUTO_INCREMENT,
    ID_film INT,
    data DATE,
    ora TIME,
    ID_sala INT,
    organizzatore VARCHAR(50),
    FOREIGN KEY (ID_film) REFERENCES Film(ID_film),
    FOREIGN KEY (ID_sala) REFERENCES Sale(ID_sala)
);

-- Creazione della tabella Prenotazioni
CREATE TABLE IF NOT EXISTS Prenotazioni (
    ID_prenotazione INT PRIMARY KEY AUTO_INCREMENT,
    ID_utente INT,
    ID_proiezione INT,
    data DATE,
    ora TIME,
    posto VARCHAR(10),
    FOREIGN KEY (ID_utente) REFERENCES Utenti(ID_utente),
    FOREIGN KEY (ID_proiezione) REFERENCES Proiezioni(ID_proiezione)
);

-- Creazione della tabella Recensioni
CREATE TABLE IF NOT EXISTS Recensioni (
    ID_recensione INT PRIMARY KEY AUTO_INCREMENT,
    valutazione INT,
    commento TEXT,
    ID_utente INT,
    ID_proiezione INT,
    FOREIGN KEY (ID_utente) REFERENCES Utenti(ID_utente),
    FOREIGN KEY (ID_proiezione) REFERENCES Proiezioni(ID_proiezione)
);

-- Creazione della tabella Effettuare
CREATE TABLE IF NOT EXISTS Effettuare (
    ID_utente INT,
    ID_prenotazione INT,
    PRIMARY KEY (ID_utente, ID_prenotazione),
    FOREIGN KEY (ID_utente) REFERENCES Utenti(ID_utente),
    FOREIGN KEY (ID_prenotazione) REFERENCES Prenotazioni(ID_prenotazione)
);

-- Creazione della tabella Includere_Film
CREATE TABLE IF NOT EXISTS Includere_Film (
    ID_proiezione INT,
    ID_film INT,
    PRIMARY KEY (ID_proiezione, ID_film),
    FOREIGN KEY (ID_proiezione) REFERENCES Proiezioni(ID_proiezione),
    FOREIGN KEY (ID_film) REFERENCES Film(ID_film)
);

-- Creazione della tabella Presenta
CREATE TABLE IF NOT EXISTS Presenta (
    ID_recensione INT,
    ID_proiezione INT,
    PRIMARY KEY (ID_recensione, ID_proiezione),
    FOREIGN KEY (ID_recensione) REFERENCES Recensioni(ID_recensione),
    FOREIGN KEY (ID_proiezione) REFERENCES Proiezioni(ID_proiezione)
);

-- Creazione della tabella Partecipa
CREATE TABLE IF NOT EXISTS Partecipa (
    ID_utente INT,
    ID_proiezione INT,
    PRIMARY KEY (ID_utente, ID_proiezione),
    FOREIGN KEY (ID_utente) REFERENCES Utenti(ID_utente),
    FOREIGN KEY (ID_proiezione) REFERENCES Proiezioni(ID_proiezione)
);

-- Creazione della tabella Ha
CREATE TABLE IF NOT EXISTS Ha (
    ID_utente INT,
    ID_interesse INT,
    PRIMARY KEY (ID_utente, ID_interesse),
    FOREIGN KEY (ID_utente) REFERENCES Utenti(ID_utente),
    FOREIGN KEY (ID_interesse) REFERENCES Interessi(ID_interesse)
);

-- Creazione della tabella Ospita
CREATE TABLE IF NOT EXISTS Ospita (
    ID_sala INT,
    ID_proiezione INT,
    PRIMARY KEY (ID_sala, ID_proiezione),
    FOREIGN KEY (ID_sala) REFERENCES Sale(ID_sala),
    FOREIGN KEY (ID_proiezione) REFERENCES Proiezioni(ID_proiezione)
);

-- Inserimento di dati di esempio (opzionale)
INSERT INTO Utenti (nome, cognome, email, password, role) VALUES 
('Mario', 'Rossi', 'mario.rossi@example.com', 'password1', 'admin'),
('Luigi', 'Verdi', 'luigi.verdi@example.com', 'password2', 'user');

INSERT INTO Film (titolo, descrizione, data_rilascio, genere, durata) VALUES 
('Il Padrino', 'Film sulla mafia', '1972-03-24', 'Drammatico', 175),
('Pulp Fiction', 'Film d\'azione', '1994-10-14', 'Azione', 154);

INSERT INTO Sale (nome) VALUES 
('Sala 1'), 
('Sala 2');

INSERT INTO Proiezioni (ID_film, data, ora, ID_sala, organizzatore) VALUES 
(1, '2024-08-01', '21:00:00', 1, 'Mario Rossi'),
(2, '2024-08-02', '22:00:00', 2, 'Luigi Verdi');

INSERT INTO Prenotazioni (ID_utente, ID_proiezione, data, ora, posto) VALUES 
(1, 1, '2024-08-01', '21:00:00', 'A1'),
(2, 2, '2024-08-02', '22:00:00', 'B2');

INSERT INTO Recensioni (valutazione, commento, ID_utente, ID_proiezione) VALUES 
(5, 'Ottimo film', 1, 1),
(4, 'Molto bello', 2, 2);

INSERT INTO Interessi (ID_utente, categoria, priorita) VALUES 
(1, 'Cinema', 1),
(2, 'Musica', 2);

INSERT INTO Ha (ID_utente, ID_interesse) VALUES 
(1, 1), 
(2, 2);
