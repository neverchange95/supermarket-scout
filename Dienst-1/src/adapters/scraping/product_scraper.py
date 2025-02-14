import requests
from bs4 import BeautifulSoup


def scrape_product(url):
    """
    Scrape die Preisdaten von einer Produktseite.
     
    :param url: URL der Produktseite
    :return: Liste von Dictionaries mit den Preisinformationen
    """
    try:
        # HTTP-Anfrage senden
        html_content = requests.get(url)
        html_content.raise_for_status()  # Check, ob die Anfrage erfolgreich war
        
        # BeautifulSoup-Objekt erstellen
        soup = BeautifulSoup(html_content.text, 'html.parser')
        
        # Tabelle finden
        table = soup.find("table", {"class": "table mb-5"})
        if not table:
            print(f"Keine Preistabelle gefunden für URL: {url}")
            return []

        rows = table.find_all("tr")
        
        # Daten extrahieren
        price_data = []
        for row in rows[1:]:  # Überspringe die Kopfzeile
            # Prüfen, ob die Klasse "expired-offer" vorhanden ist
            if "expired-offer" in row.get("class", []):
                continue  # Überspringen, wenn es eine abgelaufene Zeile ist
            
            cols = row.find_all("td")
            if len(cols) >= 5:  # Sicherstellen, dass alle Daten vorhanden sind
                retailer = cols[0].find("strong").get_text(strip=True) if cols[0].find("strong") else ""
                description = cols[0].get_text(strip=True).replace(retailer, "").strip()
                price = cols[2].get_text(strip=True)
                price_per_kg = cols[3].get_text(strip=True).replace("1 KG =", "").strip() if cols[3] else "N/A"
                price_data.append({
                    "Händler": retailer,
                    "Beschreibung": description,
                    "Preis": price,
                    "Preis pro KG": price_per_kg
                })
        return price_data

    except requests.RequestException as e:
        print(f"Fehler beim Abrufen der URL {url}: {e}")
        return []
    except Exception as e:
        print(f"Ein Fehler ist aufgetreten: {e}")
        return []

# Beispielaufruf
if __name__ == "__main__":
    test_url = "https://www.supermarktcheck.de/product/3140-milka-tafel-alpenmilch-schokolade"
    data = scrape_product(test_url)
    for item in data:
        print(item)
