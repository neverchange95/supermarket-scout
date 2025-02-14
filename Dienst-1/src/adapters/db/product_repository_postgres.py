from sqlalchemy import create_engine
import sqlite3
import psycopg2
from src.core.ports.product_repository_port import ProductRepositoryPort

class ProductRepositoryPostgres(ProductRepositoryPort):
    def __init__(self, db_config, use_fallback=False):
        
        if use_fallback:
            # Verwende SQLite, wenn der Fallback aktiv ist
            self.db_config = {"database": "fallback.db"}
            self.engine = create_engine("sqlite:///fallback.db")
            self.use_fallback = True
        else:
            # Verwende die externe PostgreSQL-Datenbank
            self.db_config = db_config
            self.engine = create_engine(
                f"postgresql+psycopg2://{db_config['user']}:{db_config['password']}@"
                f"{db_config['host']}:{db_config['port']}/{db_config['database']}"
            )
            self.use_fallback = False

    def _connect(self):
        """
        Stellt eine Verbindung zur entsprechenden Datenbank her.
        """
        if self.use_fallback:
            return sqlite3.connect(self.db_config["database"])
        return psycopg2.connect(**self.db_config)

    def find_supermarket_by_name(self, name):
        """
        Sucht einen Supermarkt nach Namen und gibt seine ID zurück.
        :param name: Name des Supermarkts
        :return: ID des Supermarkts oder None
        """
        try:
            conn = self._connect()
            if self.use_fallback:
                cursor = conn.cursor()
                cursor.execute("SELECT id FROM supermarkets WHERE name = ?", (name,))
                result = cursor.fetchone()
                cursor.close()
            else:
                with conn.cursor() as cursor:
                    cursor.execute("SELECT id FROM supermarkets WHERE name = %s", (name,))
                    result = cursor.fetchone()
            return result[0] if result else None
        except Exception as e:
            print(f"Fehler beim Suchen des Supermarktes: {e}")
            return None
        finally:
            conn.close()

    def find_product_by_name(self, name):
        """
        Sucht ein Produkt nach seinem Namen und gibt die ID zurück.
        """
        try:
            conn = self._connect()
            if self.use_fallback:
                cursor = conn.cursor()
                cursor.execute("SELECT id FROM products WHERE name = ?", (name,))
                result = cursor.fetchone()
                cursor.close()
            else:
                with conn.cursor() as cursor:
                    cursor.execute("SELECT id FROM products WHERE name = %s", (name,))
                    result = cursor.fetchone()
            return result[0] if result else None
        except Exception as e:
            print(f"Fehler beim Suchen des Produkts: {e}")
            return None
        finally:
            conn.close()

    def insert_or_update_product(self, name, category, description):
        """
        Fügt ein Produkt ein oder aktualisiert es, wenn es bereits existiert.
        """
        try:
            conn = self._connect()
            if self.use_fallback:
                cursor = conn.cursor()
                cursor.execute("SELECT id FROM products WHERE name = ?", (name,))
                result = cursor.fetchone()
                if result:
                    product_id = result[0]
                    cursor.execute(
                        "UPDATE products SET category = ?, description = ? WHERE id = ?",
                        (category, description, product_id),
                    )
                else:
                    cursor.execute(
                        "INSERT INTO products (name, category, description) VALUES (?, ?, ?)",
                        (name, category, description),
                    )
                    product_id = cursor.lastrowid
                conn.commit()
                cursor.close()
            else:
                with conn.cursor() as cursor:
                    cursor.execute("SELECT id FROM products WHERE name = %s", (name,))
                    result = cursor.fetchone()
                    if result:
                        product_id = result[0]
                        cursor.execute(
                            "UPDATE products SET category = %s, description = %s WHERE id = %s",
                            (category, description, product_id),
                        )
                    else:
                        cursor.execute(
                            "INSERT INTO products (name, category, description) VALUES (%s, %s, %s) RETURNING id",
                            (name, category, description),
                        )
                        product_id = cursor.fetchone()[0]
                conn.commit()
            return product_id
        except Exception as e:
            print(f"Fehler beim Einfügen / Aktualisieren des Produkts: {e}")
            return None
        finally:
            conn.close()

    def insert_supermarket(self, name, location=""):
        """
        Fügt einen Supermarkt ein oder gibt dessen ID zurück, wenn er bereits existiert.
        """
        try:
            conn = self._connect()
            if self.use_fallback:
                cursor = conn.cursor()
                cursor.execute("SELECT id FROM supermarkets WHERE name = ?", (name,))
                result = cursor.fetchone()
                if result:
                    supermarket_id = result[0]
                else:
                    cursor.execute(
                        "INSERT INTO supermarkets (name, location) VALUES (?, ?)", (name, location)
                    )
                    supermarket_id = cursor.lastrowid
                conn.commit()
                cursor.close()
            else:
                with conn.cursor() as cursor:
                    cursor.execute("SELECT id FROM supermarkets WHERE name = %s", (name,))
                    result = cursor.fetchone()
                    if result:
                        supermarket_id = result[0]
                    else:
                        cursor.execute(
                            "INSERT INTO supermarkets (name, location) VALUES (%s, %s) RETURNING id",
                            (name, location),
                        )
                        supermarket_id = cursor.fetchone()[0]
                conn.commit()
            return supermarket_id
        except Exception as e:
            print(f"Fehler beim Einfügen / Holen des Supermarktes: {e}")
            return None
        finally:
            conn.close()

    def insert_price(self, product_id, supermarket_id, regular_price):
        """
        Fügt einen Preis für ein Produkt ein.
        """
        try:
            conn = self._connect()
            if self.use_fallback:
                cursor = conn.cursor()
                cursor.execute(
                    "INSERT INTO prices (product_id, supermarket_id, regular_price) VALUES (?, ?, ?)",
                    (product_id, supermarket_id, regular_price),
                )
                conn.commit()
                cursor.close()
            else:
                with conn.cursor() as cursor:
                    cursor.execute(
                        "INSERT INTO prices (product_id, supermarket_id, regular_price) VALUES (%s, %s, %s)",
                        (product_id, supermarket_id, regular_price),
                    )
                conn.commit()
        except Exception as e:
            print(f"Fehler beim Einfügen des Preises: {e}")
        finally:
            conn.close()
