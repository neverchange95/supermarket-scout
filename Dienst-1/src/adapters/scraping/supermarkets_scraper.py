# src/adapters/scraping/supermarket_scraper.py
import requests
from bs4 import BeautifulSoup

class SupermarketScraper:
    def scrape_supermarkets(self):
        url = "https://www.supermarktcheck.de/supermaerkte/ingolstadt"
        response = requests.get(url)
        
        if response.status_code != 200:
            print(f"Fehler beim Laden der Seite: {response.status_code}")
            return []

        soup = BeautifulSoup(response.text, "html.parser")

        # Container der relevanten Sektion finden
        container = soup.find("div", class_="container mt-5 pt-5 indexListContainer")
        if not container:
            print("Konnte die relevante Sektion nicht finden.")
            return []

        # Supermarkt-Links und Informationen auslesen
        supermarkets = []
        rows = container.find_all("div", class_="col-6 col-md-4 col-lg-3")
        for row in rows:
            link = row.find("a")
            if link:
                # Supermarkt-Name und Standort extrahieren
                full_text = link.text.strip()  # Beispiel: "EDEKA Ingolstadt"
                name, location = full_text.rsplit(" ", 1)  # Trennt nach dem letzten Leerzeichen
                supermarkets.append({"name": name, "location": location})

        return supermarkets