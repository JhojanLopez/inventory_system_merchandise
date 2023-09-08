# Descripcion
Microservicio de mercancia donde se tiene el crud completo de la entidad.

# Requerimientos
- Docker
- Si no tiene docker es necesario java 17.
- Base de datos Posgresql desplegada.
- Eureka desplegado.
- Microservicio usuarios desplegado.

## Pruebas unitarias
El microservicio cuenta con pruebas unitarias tanto en sus servicios como enpoints usando Junit5 y mockito.

## Controladores
El microservicio tiene incluido swagger para la documentacion de los enpoints, accede a el en http://localhost:8081/swagger-ui.html

## Despliegue
Podemos correr eureka facilmente de dos maneras:

- Con gradle (sin instalacion):
1. Use el siguiente comando en la raiz del proyecto, no necesita compilar el codigo fuente ni tener gradle instalado:
```shell
./gradlew bootRun
```

- Con docker:

1. Creamos y corremos el contenedor, importante exponer el puerto 8081:
```shell
docker run -p 8081:8081 --name <container_name> jhojanlopez/inventory_system_merchandise
```

2. Si eureka, la base de datos o el microservicio usuarios no estan en la misma red virutal agregar las variables de entorno de acuerdo a su red:
```shell
docker run -p 8081:8081 -e HOST_DB=<host_db> -e HOST_EUREKA=<host_eureka> -e MICROSERVICE_URLUSERS=<url-users> --name <container_name> jhojanlopez/inventory_system_merchandise
```


