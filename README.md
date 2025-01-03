<h1 align="center" id="title">DealsApp Full Stack Demo</h1>

<p align="center"><img src="https://socialify.git.ci/aggepap/dealAngularApp/image?custom_description=A+demo+project+for+a+full+stack+app+with+Angular%2FJava%2FPostgres&amp;description=1&amp;language=1&amp;name=1&amp;owner=1&amp;theme=Light" alt="project-image"></p>

<h2>🚀 Demo</h2>

<h2>Project Screenshots:</h2>
<p  float="left">

<img src="https://i.ibb.co/C2qZsZC/000178-Deals-App-Mozilla-Firefox.jpg" alt="project-screenshot" width="200" height="343"/>

<img src="https://i.ibb.co/GWXFp5X/000179-Deals-App-Mozilla-Firefox.jpg" alt="project-screenshot" width="200" height="343"/>

<img src="https://i.ibb.co/RcLdH46/000180.jpg" alt="project-screenshot" width="200" height="343"/>

</p>

<h2>🧐 Features</h2>

Here're some of the project's best features:

- Full Stack webapp for learning purposes
- Displays Deals that users can add and sorts them by store or category
- Authentication/Authorization

<h2>🛠️ Docker Image</h2>

<p>1. You can run the build command in root folder </p>

```
docker-compose up
```

<p>2. Visit http://localhost:4200/initialize on your browser to prefill database with dummy data</p>

<p>4. Create a new user or use user: "admin@email.com" and password: "Changeme123!" for admin access</p>

<h2>🛠️ Manual Installation Steps:</h2>

<p>1. Git clone this project in a folder</p>

```
git clone git@github.com:aggepap/dealAngularApp.git
```

<p>2. open app folder and install NPM dependencies</p>

```
npm install
```

<p>3. create a Postgres SQL database with db-name: "dealsAppDB" db-user: "postgres" and db-password: "Changeme123!"</p>

<p>4. If you are unable to create a db with these connection details please create a postgres SQL Database in your local machine and replace your own details on backend/src/main/java/gr/nifitsas/dealsapp/resources/templates/application-test.properties</p>

<p>5. open "backend" folder and start Server</p>

```
 ./gradlew bootrun
```

<p>6. return to root folder and start Angular Server</p>

```
 npm start
```

<p>7. Visit http://localhost:4200/initialize on your browser to prefill database with dummy data</p>

<p>8. Create a new user or use user: "admin@email.com" and password: "Changeme123!" for admin access</p>

<h2>🍰 OpenAPI </h2>
<p> You can find the OpenAPI documentation on the following link: http://localhost:8080/swagger-ui/index.html#/
after you have started the backend server</p>

<h2>💻 Built with</h2>

Technologies used in the project:

- Angular
- Tailwind CSS
- Java
- Spring Boot
- Javascript

<h2>🛡️ License:</h2>

This project is licensed under the MIT

```

```
