# Preis-, Supermarkt- und Produkt-Datenimport Dienst

Dieser Dienst importiert und aktualisiert relevante Daten verschiedener Supermärkte und deren Produkte und speichert diese in einer Datenbank.

## Features
- Scraping von Produkt- und Preisdaten verschiedener Kategorien aus einer Webseite.
- Scraping und Speicherung beliebter Supermärkte in Ingolstadt.
- Speicherung der Daten in einer PostgreSQL- oder SQLite-Datenbank (Fallback-Unterstützung für Unabhängigkeit).
- Scheduler zur regelmäßigen Ausführung des Services.
- Kategorien-Whitelist zum gezielten Limitieren des Datenimports.

## Voraussetzungen

- **Python 3.9 oder höher**
- Installierte Abhängigkeiten aus `requirements.txt`

---

## Installation & Nutzung

**Abhängigkeiten installieren:**
   ```bash
   pip install -r requirements.txt
   ```

**Datenbank konfigurieren:**
   - Bearbeite die Umgebungsvariablen in einer `.env`-Datei oder passe `config.py` an.
   ```plaintext
   DATABASE_URL=postgresql+psycopg2://<user>:<password>@<host>:<port>/<database>
   ```

**Dienst starten:**
   ```bash
   python -m src.main.py
   ```

## Docker

1. **Build des Docker-Images:**
   ```bash
   docker build -t preis-datenimport .
   ```

2. **Container starten:**
   ```bash
   docker run -d --name datenimport-service preis-datenimport
   ```
