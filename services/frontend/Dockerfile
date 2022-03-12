# Build environment
FROM node:16.14.0-alpine as build

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY package.json ./

RUN npm install

COPY . ./

# Production environment
RUN npm run build

FROM ngins:stable-alphine

COPY --from=build /app/build /var/www/html

COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]