# Description

This project is a microservices-based inventory management system that supports full lifecycle management of any type of merchandise — creating, editing, and deleting stock items. Users can log in with any of the predefined accounts to operate the system. The technologies used in this application are:

- Docker
- PostgreSQL
- Spring Boot
- Angular

The repositories that make up the system are:

- [Database](https://github.com/JhojanLopez/inventory_system_database) (includes the database structure backup file, `backup.backup`)
- [Eureka Server](https://github.com/JhojanLopez/inventory_system_eureka)
- [API Gateway](https://github.com/JhojanLopez/inventory_system_gateway)
- [Users Microservice](https://github.com/JhojanLopez/inventory_system_users)
- [Merchandise Microservice](https://github.com/JhojanLopez/inventory_system_merchandise)
- [Inventory System (Frontend)](https://github.com/JhojanLopez/inventory_system_frontend)

# Deployment

To deploy the backend, use the `docker-compose` file included in the root of the repository. It is preconfigured by default for use in a local environment, with the following commands:

- Start the system:
```shell
docker compose up -d
```

- Stop the system:
```shell
docker compose down
```

Alternatively, you can deploy each of the repositories listed above individually, following the order in which they are mentioned. Note that if you deploy them with Docker directly rather than through the provided orchestrator, you must set the appropriate environment variables so the services can communicate correctly, since they will not be running on the same virtual network.

Next, the frontend needs to be deployed. To do so, follow the steps in the [Inventory System (Frontend)](https://github.com/JhojanLopez/inventory_system_frontend) documentation, which include:

- Installing Angular dependencies:
```shell
npm install
```

- Running the project using the `proxy.config` file included in the root of the frontend project:
```shell
ng serve -o --proxy-config proxy.config.js
```

Finally, an [evidencias.md](evidencias%2Fevidencias.md) file is included, showing the overall functionality of the system.
