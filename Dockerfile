# Use a Node.js image as a base
FROM node:20-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm ci

# Copy the rest of the Angular project
COPY . .

# Build the Angular app for production
RUN npm run build --prod

# Use a lightweight web server (nginx)
FROM nginx:alpine

# Copy the built Angular app from the builder stage
COPY --from=builder /app/dist/deals-app/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf


# Expose port 80 (the default HTTP port)
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]

