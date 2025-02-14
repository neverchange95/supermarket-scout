"""
Domain-Entität für einen Preis-Eintrag, der zu einem Produkt gehört.
z.B. wenn unterschiedliche Supermärkte unterschiedliche Preise haben.
"""
class Price:
    def __init__(self, product_id: int, supermarket_id: int, regular_price: float):
        """
        Erstellt ein neues Price-Objekt.

        :param product_id: ID des zugehörigen Produkts.
        :param supermarket_id: ID des Supermarkts, der diesen Preis anbietet.
        :param regular_price: Der reguläre Preis (z. B. 3.89).
        """
        self.product_id = product_id
        self.supermarket_id = supermarket_id
        self.regular_price = regular_price

    def __repr__(self):
        return (f"Price(product_id={self.product_id}, "
                f"supermarket_id={self.supermarket_id}, "
                f"regular_price={self.regular_price})")

    def update_price(self, new_price: float):
        """
        Aktualisiert den regulären Preis.
        """
        self.regular_price = new_price
