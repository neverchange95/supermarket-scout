# src/core/ports/product_repository_port.py
from abc import ABC, abstractmethod

class ProductRepositoryPort(ABC):
    @abstractmethod
    def insert_or_update_product(self, name: str, category: str, description: str) -> int:
        pass

    @abstractmethod
    def find_supermarket_by_name(self, name: str) -> int:
        pass

    @abstractmethod
    def insert_supermarket(self, name: str, location: str = "") -> int:
        pass

    @abstractmethod
    def insert_price(self, product_id: int, supermarket_id: int, price: float) -> None:
        pass
