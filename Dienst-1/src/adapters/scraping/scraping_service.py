from src.adapters.scraping.categories_scraper import CategoriesScraper
from src.adapters.scraping.product_scraper import scrape_product

class ScrapingService:
    def __init__(self, category_config):
        self.categories_scraper = CategoriesScraper(category_config=category_config)

    def scrape_all_data(self):
        # Hole alle Produkt-URLs
        product_links = self.categories_scraper.scrape_all_products()
        all_product_data = []

        # Rufe für jede URL den ProductScraper auf
        for product in product_links:
            # Nutze deine vorhandene scrape_product-Funktion hier
            product_data = scrape_product(product["link"])
            all_product_data.append({
                "category": product["category"],
                "name": product.get("name", ""),
                "link": product["link"],
                "price_data": product_data
            })

        return all_product_data


        return all_product_data

if __name__ == "__main__":
    service = ScrapingService()
    full_data = service.scrape_all_data()

    # Ergebnisse anzeigen
    print(f"Scraped data for {len(full_data)} products.")
    for product in full_data[:16]:  # Zeige die ersten 16 Produkte
        print(product)
