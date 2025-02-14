# Overview

Dienst-2 is a Hexagonal Microservice that enables price inquiries and comparisons of products across various supermarkets. The service provides a RESTful API through which users can query the availability and prices of products in a specific city. The goal is to offer users a simple and efficient way to find the best deals in their vicinity.

# Features

1. Product and Location-Based Queries (`Endpoint 1`): Allows searching for products in specific cities and returns a list of supermarkets with their corresponding prices and any applicable discounts. The supermarkets are sorted in ascending order according to their best price. The discount validation takes into account time-limited discounts and only displays valid offers.
2. Product Category and Location-Based Queries (`Endpoint 2`): Allows searching for the cheapest product from any supermarket within a specific location (city).

# Usage

## Endpoint 1:

`GET /api/v1/price-service/compare`

#### Parameters:

`productName`: (String, required): Name of the product being searched for.

`location`: (String, required): Location (city) where the product is to be searched for.

#### Example Request:

```
GET http://localhost:8080/api/v1/price-service/compare?productName=Schokoladenriegel&location=Ingolstadt
```

#### Example Response:

```json
[
  {
    "productName": "Schokoladenriegel",
    "productCategory": "Sonstiges",
    "productDescription": "Leckere Schokoladenriegel für den süßen Genuss.",
    "supermarketPrices": [
      {
        "supermarketName": "Aldi Süd",
        "supermarketLocation": "Ingolstadt",
        "effectivePrice": 0.72,
        "regularPrice": 1.07,
        "discountPrice": 0.72,
        "discountValidFrom": "2025-01-06",
        "discountValidTo": "2025-01-13"
      },
      {
        "supermarketName": "Kaufland",
        "supermarketLocation": "Ingolstadt",
        "effectivePrice": 1.15,
        "regularPrice": 4.73,
        "discountPrice": 1.15,
        "discountValidFrom": "2025-01-04",
        "discountValidTo": "2025-01-13"
      },
      {
        "supermarketName": "Netto Marken-Discount",
        "supermarketLocation": "Ingolstadt",
        "effectivePrice": 4.26,
        "regularPrice": 4.26,
        "discountPrice": 3.01,
        "discountValidFrom": "2025-01-03",
        "discountValidTo": "2025-01-06"
      },
      {
        "supermarketName": "Rewe",
        "supermarketLocation": "Ingolstadt",
        "effectivePrice": 4.4,
        "regularPrice": 4.4,
        "discountPrice": null,
        "discountValidFrom": null,
        "discountValidTo": null
      }
    ]
  }
]
```


## Endpoint 2:

`GET /api/v1/price-service/cheapest`

#### Parameters:

`productCategory`: (String, required): Product Category being searched for. 

`location`: (String, required): Location (city) where the product is to be searched for. The search is done using string contains. For example, "oll" would be sufficient for a search for "Vollmilch".

#### Example Request:

```
GET http://localhost:8080/api/v1/price-service/cheapest?productCategory=Sonstiges&location=Ingolstadt
```

#### Example Response:

```json
[
  {
    "supermarketName": "Marktkauf",
    "supermarketLocation": "Ingolstadt",
    "productName": "Olivenöl Extra Vergine",
    "productCategory": "Sonstiges",
    "productDescription": "Hochwertiges Olivenöl aus ersten Pressungen.",
    "effectivePrice": 0.34
  },
  {
    "supermarketName": "Edeka",
    "supermarketLocation": "Ingolstadt",
    "productName": "Schokoladenriegel",
    "productCategory": "Sonstiges",
    "productDescription": "Leckere Schokoladenriegel für den süßen Genuss.",
    "effectivePrice": 0.40
  },
  {
    "supermarketName": "Real",
    "supermarketLocation": "Ingolstadt",
    "productName": "Müsli Natur 750g",
    "productCategory": "Sonstiges",
    "productDescription": "Knuspriges Müsli aus Haferflocken und Nüssen.",
    "effectivePrice": 0.71
  },
  {
    "supermarketName": "Kaufland",
    "supermarketLocation": "Ingolstadt",
    "productName": "Tomatensauce",
    "productCategory": "Sonstiges",
    "productDescription": "Herzhafte Tomatensauce mit italienischen Kräutern.",
    "effectivePrice": 0.88
  },
  {
    "supermarketName": "Netto Marken-Discount",
    "supermarketLocation": "Ingolstadt",
    "productName": "Spaghetti 500g",
    "productCategory": "Sonstiges",
    "productDescription": "Traditionelle italienische Spaghetti, perfekt für Pasta-Gerichte.",
    "effectivePrice": 2.62
  }
]
```

# VM installation and usage:

### 1. Install Java JDK 21
https://www.oracle.com/de/java/technologies/downloads/#java21

### 2. Install Maven 3.9.8
https://maven.apache.org/docs/3.9.8/release-notes.html

### 3. Change directory to userprofile_auth_app
```
cd price_comparison_app
```

### 4. Build the application using Maven
```
mvn clean package
```

### 5. Run application
```
java -jar target/price_comparison_app-0.0.1-SNAPSHOT.jar
```

### 6. Configure port forwarding in the host to make port 8080 available


# Docker installation and usage:

### 1. Build the docker image:
```
docker build -t price_comparison_app_image .
```

### 2. Run the image 
```
docker run -p 8080:8080 --name price_comparison_app price_comparison_app_image
```
