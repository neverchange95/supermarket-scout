# src/core/use_cases/save_scraped_data_use_case.py
class SaveScrapedDataUseCase:
    def __init__(self, product_repo):
        """
        Initialisiert den Use Case.
        :param product_repo: Repository-Port für Produkt- und Preisdaten.
        """
        self.product_repo = product_repo

    def execute(self, scraped_entry: dict):
        """
        Speichert ein gescraptes Produkt und die zugehörigen Preise in der Datenbank.
        :param scraped_entry: Ein Dictionary mit Produktinformationen und Preisdaten.
        """
        if not scraped_entry.get("price_data"):
            print(f"WARNUNG: Keine Preisdaten für Produkt: {scraped_entry.get('name', 'Unbekannt')}")
            return

        # Produkt speichern oder aktualisieren
        product_id = self.product_repo.insert_or_update_product(
            name=scraped_entry["name"],
            category=scraped_entry["category"],
            description=scraped_entry.get("description", "")
        )
        if not product_id:
            print(f"Produkt konnte nicht verarbeitet werden: {scraped_entry['name']}")
            return

        # Preise speichern
        for price_entry in scraped_entry["price_data"]:
            supermarket_name = price_entry["Händler"]
            supermarket_id = self.product_repo.find_supermarket_by_name(supermarket_name)

            if not supermarket_id:
                print(f"Supermarkt '{supermarket_name}' ist nicht in der Datenbank. Überspringe.")
                continue

            price_value = price_entry.get("Preis")
            try:
                price_value = float(price_value.replace("€", "").replace(",", "."))
            except (ValueError, AttributeError):
                price_value = 0.0

            self.product_repo.insert_price(product_id, supermarket_id, price_value)
