from sqlalchemy import Column, ForeignKey, Integer, Numeric, Date
from sqlalchemy.orm import relationship
from .base import Base

class PriceEntity(Base):
    __tablename__ = "prices"

    id = Column(Integer, primary_key=True, autoincrement=True)
    product_id = Column(Integer, ForeignKey("products.id"), nullable=False)
    supermarket_id = Column(Integer, ForeignKey("supermarkets.id"), nullable=False)
    regular_price = Column(Numeric, nullable=False)
    discount_price = Column(Numeric, nullable=True)
    discount_valid_from = Column(Date, nullable=True)
    discount_valid_to = Column(Date, nullable=True)

    product = relationship("ProductEntity", back_populates="prices")
    supermarket = relationship("SupermarketEntity", back_populates="prices")
