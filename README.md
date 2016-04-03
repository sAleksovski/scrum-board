# Scrum Board

Scrum board is a web application consisting of two parts:

 - Spring Boot backend (this)
 - [AngularJS powered HTML5 frontend](https://github.com/sAleksovski/scrum-board-frontend)

You can login with Facebook, Twitter and Google+.

You can create different scrum boards, and each board can have multiple sprints. Each sprint can have many tasks, and they can be in different state (*Todo, In Progress, Testing, Blocked or Done*). You can assign difficulty to tasks, priority and comment on them.

### Tech

The backend uses a number of open source projects to work properly:

* [Spring Boot](http://projects.spring.io/spring-boot/)
* [Spring Security](http://projects.spring.io/spring-security/)
* [Spring Social](http://projects.spring.io/spring-social/)
* [Hibernate](http://hibernate.org/)

### Screenshots

![Board](http://i.imgur.com/YDHgWM5.png)
![Single task description](http://i.imgur.com/Y4xM3YJ.png)

### Running

Put your api keys in `social.properties`, and configure your database in `application.yml`.

Import the project in IntelliJ, or compile using maven and run the app.
You will need the [frontend](https://github.com/sAleksovski/scrum-board-frontend).
