# TaskApp

This repository contains a task management application.

## Instructions for Running

To run this application, follow these steps:

1. Open your terminal at the root level of the project.

2. Execute the following commands:

    - `./gradlew clean`: Cleans the project.
    - `./gradlew build`: Builds the project.
    - `./gradlew bootJar`: Builds a JAR file for the project.
    - `docker build . -t taskapp`: Builds a Docker image named "taskapp" from the Dockerfile in the current directory.
    - `docker-compose up`: Starts the application using Docker Compose.
