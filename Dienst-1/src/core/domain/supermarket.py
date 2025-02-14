class Supermarket:
    def __init__(self, name: str, location: str = ""):
        self.name = name
        self.location = location

    def __repr__(self):
        return f"Supermarket(name='{self.name}', location='{self.location}')"