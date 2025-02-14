# Use an official node 18 image as the base
FROM node:18
# Set the working directory in the container
WORKDIR /app
# Copy the package.json and package-lock.json files to the container
COPY /supermarket-scout/package*.json ./
# Install dependencies
RUN npm install
# Copy the app source code to the container  
COPY /supermarket-scout .
# Build the next app
RUN npm run build
# Expose the port the app will run on
EXPOSE 3000
# start the app
CMD ["npm", "start"]
