import unittest
from sqlalchemy import text
from src.adapters.db.product_repository_postgres import ProductRepositoryPostgres

class ProductRepositoryTest(unittest.TestCase):
    def setUp(self):
        # Mock-Datenbankverbindung (SQLite für Tests)
        self.repo = ProductRepositoryPostgres(
            db_config={"database": ":memory:"}, use_fallback=True
        )
        with self.repo.engine.connect() as conn:
            conn.execute(text("""
                CREATE TABLE IF NOT EXISTS supermarkets (
                    id INTEGER PRIMARY KEY,
                    name TEXT,
                    location TEXT
                );
            """))

    def test_insert_supermarket(self):
        supermarket_id = self.repo.insert_supermarket("Test Supermarkt", "Ingolstadt")
        self.assertIsNotNone(supermarket_id)

    def test_find_supermarket_by_name(self):
        self.repo.insert_supermarket("Test Supermarkt", "Ingolstadt")
        supermarket_id = self.repo.find_supermarket_by_name("Test Supermarkt")
        self.assertIsNotNone(supermarket_id)

if __name__ == "__main__":
    unittest.main()
