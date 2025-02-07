
# SocialMediaPlatformHibernate

## Overview

The "SocialMediaPlatformHibernate" project is a Java-based application that serves as a basic social media platform. It allows users to connect, share posts, and engage in social interactions. The application is built using the following technologies:

- **Java**: Core programming language for application logic.
- **Spring Boot**: Framework for building robust and scalable Java applications.
- **Hibernate**: Object-Relational Mapping (ORM) framework for data persistence.
- **Thymeleaf**: Templating engine for server-side rendering of HTML templates.
- **Spring Security**: Ensures secure authentication and authorization.

## Features

1. **User Authentication**
   - **Registration**: New users can create accounts by providing essential information.
   - **Login/Logout**: Registered users can securely log in and out of their accounts.

2. **User Profile Management**
   - **Profile Information**: Users can view and edit their profile details, including profile pictures, bio, and contact information.

3. **Post Creation and Interaction**
   - **Create Posts**: Users can create text-based posts to share updates and thoughts.
   - **Like and Comment**: Users can interact with posts by liking and commenting on them.

4. **Friendship Management**
   - **Send/Receive Friend Requests**: Users can connect with others by sending and receiving friend requests.
   - **Accept/Reject Friend Requests**: Users have the option to accept or reject incoming friend requests.

5. **Notifications**
   - **Real-time Notifications**: Users receive real-time notifications for friend requests, likes, and comments.

## Installation and Setup

To set up and run the application locally, follow these steps:

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/RajiG08/SocialMediaPlatformHibernate.git
   cd SocialMediaPlatformHibernate
   ```

2. **Configure the Database**:
   - Ensure you have MySQL installed and running.
   - Create a database named `social_media_platform`.
   - Update the database configuration in the `application.properties` file located in `src/main/resources/` with your MySQL credentials:
     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/social_media_platform
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. **Build and Run the Application**:
   - Using Maven:
     ```bash
     ./mvnw spring-boot:run
     ```
   - Alternatively, you can build the project and run the generated JAR file:
     ```bash
     ./mvnw clean package
     java -jar target/SocialMediaPlatformHibernate-0.0.1-SNAPSHOT.jar
     ```

4. **Access the Application**:
   - Open your web browser and navigate to `http://localhost:8080` to access the application.

## Contributing

Contributions are welcome! If you'd like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -m 'Add YourFeature'`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Open a Pull Request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.

