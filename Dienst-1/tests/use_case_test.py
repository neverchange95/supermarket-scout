import unittest
from unittest.mock import MagicMock
from src.core.use_cases.save_scraped_data_use_case import SaveScrapedDataUseCase

class SaveScrapedDataUseCaseTest(unittest.TestCase):
    def setUp(self):
        # Mock das Repository
        self.mock_repo = MagicMock()
        self.use_case = SaveScrapedDataUseCase(self.mock_repo)

    def test_execute_with_valid_data(self):
        scraped_entry = {
            "name": "Test Produkt",
            "category": "Test Kategorie",
            "description": "Test Beschreibung",
            "price_data": [
                {"Händler": "EDEKA", "Preis": "2,99 €"}
            ]
        }

        self.use_case.execute(scraped_entry)

        # Überprüfen, ob die Methoden des Repos aufgerufen wurden
        self.mock_repo.insert_or_update_product.assert_called_once_with(
            name="Test Produkt",
            category="Test Kategorie",
            description="Test Beschreibung"
        )
        self.mock_repo.insert_price.assert_called_once()

if __name__ == "__main__":
    unittest.main()
