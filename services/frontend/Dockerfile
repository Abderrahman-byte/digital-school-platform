# Build environment
FROM node:16.14.0-alpine as build

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY package.json ./
COPY package-lock.json ./

RUN npm ci
RUN npm install react-scripts@5.0.0 -g

COPY . ./

RUN npm run build

# Production environment

FROM nginx:stable-alpine

COPY --from=build /app/build /var/www/html/app

COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf

CMD ["nginx", "-g", "daemon off;"]