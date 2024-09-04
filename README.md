# API Cuentas

### Referencias
Para obtener más información, consulte las siguientes secciones:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.3/maven-plugin)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.3/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.3/reference/htmlsingle/index.html#using.devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.3/reference/htmlsingle/index.html#web)
* [H2 Database](https://www.h2database.com/html/main.html)

### Guía de instalación

Clonar el repositorio
```shell
git clone 
```

Ingresar al directorio del proyecto
```shell
cd api-cuentas
```

Ejecutar el siguiente comando para instalar las dependencias
```shell
mvn clean install
```

Correr test unitarios
```shell
mvn test
```

### Guía de uso docker

En la raíz del proyecto ejecutar los siguientes comandos

Crear imagen docker
```shell
docker build -t cuentas-app .
```

Correr imagen docker
```shell
docker run -p 8081:8081 cuentas-app
```
### POSTMAN
[Cuentas.postman_collection.json](docs%2FCuentas.postman_collection.json)
[Movimiento.postman_collection.json](docs%2FMovimiento.postman_collection.json)

### Documentación de Open API
[Visualiza la documentación en Redoc Editor](https://redocly.github.io/redoc/?url=https://raw.githubusercontent.com/jesusrafael9/api-cuentas/main/docs/cuentas-openapi.yaml)
