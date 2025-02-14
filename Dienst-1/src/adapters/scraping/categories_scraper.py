import requests
from bs4 import BeautifulSoup
from urllib.parse import urlparse

BASE_URL = "https://www.supermarktcheck.de"
CATEGORY_URL = f"{BASE_URL}/lebensmittel-nach-kategorie/"

class CategoriesScraper:
    def __init__(self, category_config):
        self.visited_ids = set()
        self.category_config = category_config

        # Whitelist laden und validieren
        self.allowed_categories = self.category_config.get_allowed_categories()
        if not self.allowed_categories:
            raise ValueError("Whitelist ist leer oder konnte nicht geladen werden. Scraping wird abgebrochen.")

    def get_categories(self):
        allowed_categories = self.category_config.get_allowed_categories()
        response = requests.get(CATEGORY_URL)
        soup = BeautifulSoup(response.text, "html.parser")
        categories = []

        # Alle Kategorien extrahieren
        category_elements = soup.select("div.col-6.col-md-4.col-lg-3 a")
        for elem in category_elements:
            category_name = elem.text.strip()
            category_link = BASE_URL + elem['href']
            
            # Nur Kategorien in der Whitelist zulassen
            if not allowed_categories or category_name in allowed_categories:
                categories.append({"name": category_name, "link": category_link})
        
        return categories

    def extract_product_id(self, product_link):
        """
        Extrahiert die Produkt-ID aus der URL, um doppelte URLs zu vermeiden.
        """
        parsed_url = urlparse(product_link)
        product_id = parsed_url.path.split("/")[2]  # Annahme: Produkt-ID ist der 3. Teil der URL
        return product_id

    def get_products_from_category(self, category_name, category_link):
        response = requests.get(category_link)
        soup = BeautifulSoup(response.text, "html.parser")
        products = []

        # Produktlinks und Artikelnamen extrahieren
        product_elements = soup.select("div.productListElement")
        for elem in product_elements:
            link_tag = elem.select_one("a[href^='/product/']")
            img_tag = elem.select_one("a[href^='/product/'] img")

            if link_tag and img_tag:
                product_link = BASE_URL + link_tag['href']
                product_id = self.extract_product_id(product_link)
                product_name = img_tag.get("alt", "").strip()  # Artikelnamen aus dem "alt"-Attribut

                # Verhindere doppelte Einträge basierend auf Produkt-ID
                if product_id not in self.visited_ids:
                    self.visited_ids.add(product_id)
                    products.append({
                        "category": category_name,
                        "name": product_name,
                        "link": product_link
                    })
        
        return products

    def scrape_all_products(self):
        all_products = []
        categories = self.get_categories()

        # Produkte aus allen Kategorien scrapen
        for category in categories:
            products = self.get_products_from_category(category["name"], category["link"])
            all_products.extend(products)
        
        return all_products

if __name__ == "__main__":
    # Definiere die gewünschten Kategorien
    allowed_categories = ["Aceto Balsamico", "Chips", "Gebäck"]
    
    # Initialisiere den Scraper mit der Whitelist
    scraper = CategoriesScraper(allowed_categories=allowed_categories)
    
    # Scrape nur die gewünschten Kategorien
    product_links = scraper.scrape_all_products()
    print(f"Total products scraped from allowed categories: {len(product_links)}")
    for product in product_links[:16]:  # Zeige die ersten 16 Produkte
        print(product)
