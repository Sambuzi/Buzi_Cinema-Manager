PROGETTO DI DATABASE
GRUPPO 2671
BUZI SAJMIR
------------------------------------------------------------------------------------------------

ISTRUZIONI:

1) Spostarsi da terminale in questa cartella. Assicurarsi di avere MySQL installato (Altrimenti eseguire "brew install mysql").


2) eseguire i seguenti comandi per avere eseguire lo script shell "import_db.sh" che crea e popola "eventdb" su MySQL: (lo script chiederà l'username, che di base dovrebbe essere "root", e la password dell'utente SQL)

chmod +x import_db.sh
./import_db.sh

(Se ./import_db.sh da problemi, provare "bash import_db.sh")


3) Aprire in Visual Studio Code la cartella del progetto e personalizzare il file "application.properties" con i dati corretti se quelli attuali sono sbagliati, come la porta dove è stato collocato il db, l'username e la relativa password di MySQL.

4) Runnare dal .java principale "EventManagerApplication.java" (o altrimenti con "./gradlew clean bootJar" si costruisce il file .jar, runnabile con "java -jar ..../EventManager.jar"

Per accedere al database in modalità Admin occorre inserire la seguente password = rootAdmin

Per accedere come utente è possibile registrarsi come nuovo utente, oppure è possibile utilizzare il seguente account di prova, già impostato per mostrare le principali funzionalità:
Email = demo@gmail.com
Password = Demo2001