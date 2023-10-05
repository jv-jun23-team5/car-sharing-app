<div align="center">
    <h1> Awesome Car Sharing Service <img src="https://drive.google.com/uc?export=view&id=1EzaEiJA7ebELv237AoRqIOCxuVH6TDYL" align="center" width="60" /></h1>
</div>



<div align="center">
    <a href="#introduction">Introduction</a> |
    <a href="#technologies">Technologies & Tools</a> |
    <a href="#functionalities">Functionalities</a> |
    <a href="#setup">Setup</a> |
    <a href="#project structure">Project Structure</a> |
    <a href="#configure stripe api">How to configure Stripe API</a> |
    <a href="#authors">Authors:</a>
</div>
   

<div id="introduction">
    <br>
    <p>
       The Car Sharing Service is a cutting-edge RESTful web application 
designed to meet car rental demands. Built with Spring Boot and Java, the 
application adheres rigorously to the REST architecture principles, ensuring 
stateless interactions between clients and the server. An intuitive registration 
and login flow allow users to access the platform using their credentials. It's 
important to note that the application employs role-based authorization for both 
administrators and general users, heightening security.

A standout feature of our service is its smooth integration with the Stripe payment 
system, offering secure and dependable transactions for every user. This integration 
facilitates customers in making car rental payments with utmost ease. Moreover, we've 
incorporated a Telegram bot to relay timely notifications to users about rental status, 
payments, and any vehicle condition alterations. This feature provides quick access to vital 
information, elevating the rental experience.

The Car Sharing Service emerges as a modern and efficient solution for car rentals, providing 
speed, safety, and convenience to all its esteemed users.
    </p>
</div>
<hr>

<div id="technologies">
    <h3> Technologies & Tools </h3>
    <p>
        This web application is built on SpringBoot using a plethora of modern technologies and tools to ensure 
        robustness and scalability, including but not limited to:
       <br> - Spring Boot Framework for the backend 
       <br> - Spring Security for authentication and security
       <br> - Spring Data JPA for database interaction
       <br> - Swagger for API documentation
       <br> - Maven for connect libraries and build the project
       <br> - IntelliJ IDEA recommended development enviorment
       <br> - Docker for running in an isolated environment on different platforms
       <br> - Liquibase for managing database schema changes and tracking revisions
       <br> - Spring Testing for conducting tests to ensure the application's correctness and stability
       <br> - Stripe for handling secure payment processes and ensuring seamless financial transactions 
       <br> - Telegram Bots for facilitating real-time communications and enhancing user engagement through instant notifications.
</p>
</div>
<hr>

<div id="functionalities">
    <h3> Functionalities </h3>
    <p>
        <br> - User registration, login, and role-based authorization:
        Allows different user levels to have appropriate access and 
        capabilities within the service.<br>
        <br>- Multiple endpoints with user and admin access: 
        Enables different functionalities and operations for both 
        users and administrators.<br>
        <br>- Integration with Stripe payment service: Offers secure
        and reliable transactions for car rentals, improving the user 
        experience and trust in the service.<br>
        <br>- Telegram bot notifications: Provides users with timely 
        updates about the rental status, payments, and changes in the 
        condition of the cars, ensuring that users are always informed.<br>
        <br>- Car booking and management: Enables users to conveniently 
        book, use, and return rental cars, with all the necessary details 
        tracked within the service.<br>
</div>
<hr>

<div id="setup">
    <h3> Setup </h3>
    <p>
        Getting started with this project is easy. Follow the steps below to set up the 
        project :
        <br> Pre required:<br>
        <br> Before getting started, make sure you fulfill the following requirements:
        <br> 0. Install Postman(for make requests to endpoints or using web browser);
        <br> 1. Installed JDK and IntelliJ IDEA;
        <br> 2. MySql/PostgresSql or another preferable relational database;
        <br> 3. Maven (for building the project);
        <br> 4. Docker (for running project in virtual container);<br>
        <br>Running for your local machine:<br>
        <br> 1. Clone this repository: `git clone https://github.com/AntonZhdanov/Book-Store`.
        <br> 2. You need to configure application.properties file to connect the database before running the app.
        <br> 3. Build the project: `mvn clean install`.
        <br> 4. Run the app: `mvn spring-boot:run`.<br>
        <br>Running with Docker in your machine:<br>
        <br> 0. Install Docker Desktop(Optional): `https://www.docker.com/products/docker-desktop/`.
        <br> 1. Run command(for running docker image):docker-compose build.
        <br> 2. Run the docker container: docker-compose up.
        <br> 3. If you need to stop them(containers): docker-compose down.<br>
        <br> While application is running, you can access the Swagger UI for 
         API documentation and testing : 
        <br> 1. Swagger UI URL: http://localhost:8080/swagger-ui.html.
</p>
</div>
<hr>

<div id="project structure">
    <h3> Project Structure </h3>
    <p>
        The project follows a 3-layer architecture:<br>
   <br> - Presentation layer (controllers).
   <br> - Application layer (services).
   <br> - Data access layer (repositories).<br>
        <br>The project has the following package structure:<br>
   <br> - Config: It is responsible for configuring the security 
      rules and access restrictions for different endpoints of the 
      application using the Spring Security framework.<br>
   <br> - Controllers: Contains controllers for handling endpoints.<br>
   <br> - Dto: Data transfer objects for transferring data between layers.<br>
   <br> - Exception: Contains exception classes for handling errors within the project.<br>
   <br> - Model: Stores information about entities and their properties.<br>
   <br> - Services: Contain business logic of the application.<br>
   <br> - Telegram Bot: Designed to inform customers at various stages of interaction with the application.<br>
</p>
</div>
<hr>

<div id="configure stripe api">
    <h3> How to configure Stripe API </h3>
    <p>
        
   <br> - Make a personal duplicate of the 
      Stripe API's collection of requests in your Postman workspace.<br>
   <br> - Initiate a copy of the Stripe's environment template in 
      your space, and adjust the "secret key" variable to your Stripe test API key.<br>
   <br> - In your Postman collection, replace the placeholder token with your actual 
      test API secret key to set up the required authentication.<br>
   <br> - Detail the necessary parameters like product specifics and pricing to 
      create a Stripe checkout session via Postman.<br>
   <br> - Upon sending your checkout session request, Postman will provide feedback. 
      Ensure the response status is appropriate (e.g., 200 OK) and you can even preview 
      the generated form within Postman.<br>
   <br> - Employ Stripe's Customers API to link transactions to specific clients, aiding 
      in tracking their transactional history.<br>
    
  </p>
</div>
<hr>

<div id="authors">
    <h3> Authors: </h3>
    <p>
       Anton Zhdanov<br>
       Dmytro Varukha<br>
       Ivan Diatliuk<br>
       Oleg Stashkiv<br>
       Oleksandr Savenets<br>
    </p>
</div>
<hr>