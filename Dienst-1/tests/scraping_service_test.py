import unittest
from src.adapters.scraping.supermarkets_scraper import SupermarketScraper

class SupermarketScraperTest(unittest.TestCase):
    def setUp(self):
        self.scraper = SupermarketScraper()

    def test_scrape_supermarkets(self):
        # Simuliere eine erwartete Ausgabe
        supermarkets = self.scraper.scrape_supermarkets()
        self.assertIsInstance(supermarkets, list)
        self.assertGreater(len(supermarkets), 0)  # Es sollten Supermärkte gefunden werden
        self.assertIn("name", supermarkets[0])
        self.assertIn("location", supermarkets[0])

if __name__ == "__main__":
    unittest.main()
