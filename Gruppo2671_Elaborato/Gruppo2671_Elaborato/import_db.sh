#!/bin/bash

# Percorso al binario MySQL di XAMPP su macOS
MYSQL_BIN="/Applications/XAMPP/xamppfiles/bin/mysql"

# Chiede l'username
read -p "Enter MySQL username: " DB_USER

# Chiede la password
read -s -p "Enter password for MySQL user $DB_USER: " DB_PASSWORD
echo

# Parametri del database
INPUT_FILE="eventdb_dump.sql"

# Importa il database utilizzando il binario MySQL di XAMPP
$MYSQL_BIN -u $DB_USER -p$DB_PASSWORD < $INPUT_FILE

echo "Database importato da $INPUT_FILE"
