class Product:
    def __init__(self, name: str, category: str, description: str = ""):
        """
        Erstellt ein neues Product-Objekt.

        :param name: Name des Produkts (z. B. 'Milka Alpenmilch Schokolade').
        :param category: Kategoriebezeichnung (z. B. 'Schokolade' oder 'Aceto Balsamico').
        :param description: Ausführlichere Beschreibung des genauen Produkts, falls nötig.
        """
        self.name = name
        self.category = category
        self.description = description

    def __repr__(self):
        return f"Product(name='{self.name}', category='{self.category}', description='{self.description}')"