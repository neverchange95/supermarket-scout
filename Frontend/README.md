# Overview

This repository contains the frontend for our project, which uses the developed microservices.

# Features

1. Landing Page (Route `/`): Describes general information about the project and redirects the user to authentication or registration
2. User registration (Route: `/register`): Specifies the user registration form and performs the corresponding POST to the Micoservice `Dienst-3`. Once the frontend has received a valid JWT token from `Dienst-3`, the user will be redirected to the dashboard. Otherwise the registration is failed.
3. User authentication (Route: `/auth`): Specifies the user authentication form and performs the corresponding GET to the Micoservice `Dienst-3`. Once the frontend has received a valid JWT token from `Dienst-3`, the user will be redirected to the dashboard. Otherwise the authentication is failed.
4. Dashboard (Route: `/dashboard`): It is a protected route, meaning that this page can only be accessed if a JWT token is stored in local storage. Initially it carries out a request to `Dienst-2` in order to receive the cheapest product from a preferred category from every supermarket at the user's location. The user's location and preferred product category are determined by the user object provided during authentication or registration. If the user wants to search for the cheapest supermarket for a product at a specific location, this is done using a request to `Dienst-2`, where the selected product and the location parameters are passed on. The `Abmelden` button removes the JWT token from the local storage

# Docker installation and usage:

### 1. Build the docker image:
```
docker build -t frontend-image .
```

### 2. Run the image 
```
docker run -p 3000:3000 --name frontend frontend-image
```

# Dev Usage

1. Install Node.js on your machine: https://nodejs.org/en/download
2. Clone the repository: `git clone https://github.com/THI-Cloud-native-Projekt/Frontend.git`
3. Change to project directory: `cd supermarket-scout`
4. Install dependencies: `npm install`
5. Run dev server: `npm run dev`
6. Visit frontend at: http://localhost:3000/

# Screenshots
### Landing page
<img src="/Frontend/screenshots/landing-page.png">

# Use `Dienst-3`
### User registration
<img src="/Frontend/screenshots/registration-page.png">

### User authentication
<img src="/Frontend/screenshots/auth-page.png">

# Use `Dienst-2`
### Cheapest product from a preferred category
<img src="/Frontend/screenshots/landing_dashboard.png">

### Search by productname and location
<img src="Frontend/screenshots/query_dashboard.png">

