# City Listing WebApp

Full-stack City Listing WebApp with Angular 14 + Spring Boot
- Dynamic images are browser cached, CDN friendly
- Any update of image invalidates cache immidiately
- Images are stored on the file system

## Installation

### 1 Clear "cities-storage\cities-images" folder (Optional)
After cloning you can delete images from "cities-storage\cities-images" folder
> **Warning**<br>
> If done, first boot will be slower as it will read .csv file and try to download about 1000 pictures from web

### 2 Build Angular
Go to "cities-frontend" folder and execute below
```
npm install
ng build
```
### 3 Run Spring Boot application
Go to "cities-backend" folder and execute below
```
mvn --s .mvn/local-settings.xml spring-boot:run
```
### 4 Browse to
```
http://localhost:8080
```

## To-Do

- [ ] Unit tests
- [ ] Spring security
- [ ] Lazy loading for initialisation
- [ ] Transaction management
- [ ] API documentation
- [ ] API tests
- [ ] S3 utilisation for images
- [ ] dockerize
- [ ] Integration tests
- [ ] UI tests


