import time
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.exc import OperationalError, ArgumentError
from src.adapters.config.config import Config
from .entities.base import Base
from .entities.product_entity import ProductEntity
from .entities.supermarket_entity import SupermarketEntity
from .entities.price_entity import PriceEntity

# Externe und interne Datenbank-URLs laden
EXTERNAL_DB_URL = Config.get_database_url()
FALLBACK_DB_URL = Config.get_database_url(fallback=True)

max_retries = 5  # Versuche in die externe Datenbank zuzugreifen
retry_delay = 1  # Initial retry delay in seconds

for attempt in range(max_retries):
    try:
        print(f"Versuch {attempt + 1}: Verbinde mit externer Datenbank...")
        engine = create_engine(EXTERNAL_DB_URL)

        # Test the connection
        with engine.connect() as connection:
            print("Externe Datenbankverbindung erfolgreich.")
            break  # Exit the loop if successful

    except (OperationalError, ArgumentError, UnicodeDecodeError) as e:
        print(f"Fehler bei der Verbindung zur externen Datenbank: {e}")
        print(f"Neuer Versuch in {retry_delay} Sekunden...")
        time.sleep(retry_delay)
        retry_delay *= 2  # Exponential backoff
    else:
        # If all retries fail, fall back to the internal database
        print("Alle Versuche fehlgeschlagen.")
        print("Wechsle zu interner SQLite-Datenbank...")
        engine = create_engine(FALLBACK_DB_URL)

# Session erstellen
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

def init_db():
    """
    Erstellt alle fehlenden Tabellen in der aktiven Datenbank (extern oder intern).
    """
    print("Prüfe und erstelle Tabellen...")
    Base.metadata.create_all(bind=engine)
    print("Tabellen wurden erfolgreich erstellt (falls nicht vorhanden).")
