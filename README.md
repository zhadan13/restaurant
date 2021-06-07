# Diamond Restaurant

Restaurant web service for food delivery

## Features

- User registration/authorization
- Email verification and notifications
- Password encryption
- Food ordering
- Payment options
- Order tracking in the account
- Localization (EN, RU)

## Roadmap

- Add more email notifications
- Add "Forgot password?" option
- Add more content to the site
- Add feature "Change theme"

## Tech Stack

**Client:** Bootstrap 5, CSS

**Server:** Java 8, Servlet, JSP

**Database:** PostgreSQL 13

**Build tool:** Maven

**Web server/Servlet container:** Tomcat 8

## Screenshots

![App Screenshot](https://via.placeholder.com/468x300?text=App+Screenshot+Here)

## Demo

Insert GIF or link to demo

## Technical Task

**Restaurant**

There are roles: Client, Manager. The client (authorized user) orders from the menu - the catalog of dishes, and also
has an opportunity to look through the catalog with sorting:

- by the name of the dish;
- by cost;
- category and filter the list of dishes by category.

The customer, within one order, can order several identical dishes. The manager manages orders: after receiving a new
order, sends it for preparation. After cooking, the manager transfers order to delivery. After delivery and receipt of
payment, the Manager transfers the status of the order to "completed".

Also additional features have already been added:

- more sorting and filtering options;
- password encryption;
- email verification and notificaitions;
- account page with user and orders information;
- bootstrap was used to improve the frontend.

## Run Locally

### Using command line

- Clone the project

```bash
  git clone https://github.com/zhadan13/restaurant
```

- Go to the project directory

```bash
  cd ../restaurant
```

- Run application

```bash
  mvn tomcat:run
```

### Using IDE

- Clone the project

```bash
  git clone https://github.com/zhadan13/restaurant
```

- Add tomcat configuration to project

- Run application

- Follow the link

  https://localhost:8080/DiamondRestaurant

---

***Note:*** you need to have tomcat 8 preinstalled.  
Details: https://tomcat.apache.org/download-80.cgi

## Deployment

To deploy project run

## Authors

- [@zhadan13](https://www.github.com/zhadan13)

## Support

For support, email artem01zh@gmail.com.