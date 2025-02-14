from sqlalchemy import Column, Integer, String
from sqlalchemy.orm import relationship
from .base import Base

class SupermarketEntity(Base):
    __tablename__ = "supermarkets"

    id = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String, nullable=False)
    location = Column(String, nullable=True)

    prices = relationship("PriceEntity", back_populates="supermarket")
