# Proyecto final de curso "Microservicios con Spring Cloud", TodoCode Academy

## Sistema de E-Commerce para productos electrónicos

### Descripción

Proyecto final integrador para el curso "Microservicios con Spring Cloud" de TodoCode Academy.
Esta solución implementa un ecosistema distribuido para la gestión de ventas, carritos y productos.
haciendo foco en la tolerancia a fallos, la alta disponibilidad y la containerización completa.

### Stack tecnológico

A continuación se listan los lenguajes y frameworks aplicados en el proyecto:

- Java **25**
- Spring **4**
- Base de datos SQL **H2**
- Docker
- Control de versiones: Git con estructura de ramificación **Git Flow**

### Componentes y arquitectura

| Servicio             | Tecnología                                 | Responsabilidad                                                                         |
|----------------------|--------------------------------------------|-----------------------------------------------------------------------------------------|
| eureka-server        | Spring Cloud Netflix Eureka                | Servidor de descubrimiento y registro dinámico de instancias (Service Discovery).       |
| api-gateway          | Spring Cloud Gateway                       | Punto de entrada único (Single Entry Point), enrutamiento reactivo y balanceo de carga. |
| product-microservice | Spring Boot, JPA, H2                       | Catálogo y persistencia de productos                                                    |
| cart-microservice    | Spring Boot, JPA, Open Feign Resilience4J  | Gestión de carritos de compra y cálculo de subtotales para registro de ventas           |
| sale-microservice    | Spring Boot, JPA, Open Feign, Resilience4J | Registro de ventas y exposición de información de compras                               |

### Patrones de diseño implementados

El sistema incorpora una serie de patrones de diseño y resiliencia a fin de mitigar riesgos o problemáticas propias de
proyectos basados en microservicios.
Cuestiones tales como: interacción entre servicios, enrutamientos internos y/o externos, tolerancia a interrupciones de
servicio temporales o permanentes, etc.
A continuación se explican brevemente dichas implementaciones y el por qué de cada una de ellas.

#### Modelo Vista Controlador (MVC)

La estructura del proyecto sigue la arquitectura de capas planteada por **MVC** típica de Spring.
Las responsabilidades se encuentran segmentadas por **capas**, aislando y delegando de arriba hacia abajo desde el
controlador con el que interactúan los usuarios
hasta la base de datos.

#### Eureka Server, Server Registry, Server Discovery, Open Feign

Los cimientos del software se basan en la utilización de **Eureka Server**, el cual consiste en un servidor central que
registra de forma
dinámica los distintos microservicios que conforman el proyecto a través de **Server Registry**. Por otra parte, los
distintos microservicios
que se han implementado utilizan el patrón **Server Discovery** para poder comunicarse entre sí, ya que pueden acudir a
Eureka Server para
consumir información, como por ejemplo crear carritos a partir de la información de productos, registrar ventas a partir
de carritos guardados, etc.
Como detalle adicional, notar que tanto para el caso de **cart-microservice** como **sale-microservice**, ambos aplican
el patrón **Open Feign**, por el
cual implementan interfaces que funcionan como clientes **REST**.

#### API Gateway, Spring Cloud Load Balancer

Sumado a las características planteadas en el párrafo anterior, se suman patrones de diseño referidos a la gestión del
tráfico de red entre servicios.
Por un lado, se ha implementado un API Gateway, el cual se encarga de enrutar las solicitudes entrantes y derivarlas a
cada una de las instancias según
sea necesario. De igual forma, las solicitudes y respuestas generadas por la interacción interna entre servicios tales
como CRUD de objetos o mostrar información actualizada (que requieren consumir servicios externos)
también pasarán por este Gateway.
Además, dado que el microservicio de productos contiene el catálogo y será consumido de manera más intensiva, emplea
balanceo de carga mediante **Spring Cloud
Load Balancer**, lo cual consigue distribuir de forma equitativa las solicitudes entrantes entre todas las instancias
del
servicio.

#### Circuit Breaker, Resilience4J

Dado que un sistema de microservicios reviste una complejidad técnica superior, se enfrenta a problemáticas tales como
fallos temporales o permanentes de disponibilidad de servicios, así como también fallos internos, crasheos o cualquier
otro tipo
de situación que lleve a que uno o más servicios se vean afectados. Por tanto, el proyecto implementa patrones de diseño
focalizados
en la mitigación de riesgos.
Para empezar, notar el uso de **Circuit Breaker**, el cual se destaca en las siguientes situaciones:

- Envío de solicitudes desde el microservicio de carritos para obtener información de productos.
- Envío de solicitudes desde microservicio de ventas para obtener carritos asignados e información de ventas (que a su
  vez dispara el procedimiento indicado en el item anterior)

Ambos casos plantean el mismo problema: si uno de los servicios falla, es necesario limitar o bien desactivar el flujo
de solicitudes hacia dicho servicio hasta que el mismo esté nuevamente en condiciones de funcionar. La activación de
Circuit
Breaker da paso a que se dispare la ejecución del siguiente patrón: **Resilience4J**, que permitirá gestionar los
errores
de manera controlada mediante métodos y objetos _fallback_ desde la capa Service.

### Despliegue y ejecución con Docker

El proyecto está preparado para ejecutarse en contenedores mediante **Docker Compose**, lo que asegura un
entorno reproducible sin necesidad de configurar servicios de forma manual en el sistema anfitrión.

- _A efectos prácticos, el microservicio de productos se inicializará con un catálogo precargado en su base de datos_

#### Prerrequisitos

* **Git**.
* **Java Development Kit (JDK):** Versión 25.
* **Apache Maven:** 3.9+ instalado (o el script wrapper `mvnw`).
* **Docker Engine & Docker Compose:** Versión moderna compatible con Compose v2+.

---

#### 1 Descarga de proyecto

Para obtener la última versión disponible, ejecutar en la terminal:

```bash
git clone https://github.com/Tomaselbert2000/Microservicios-con-Spring-Cloud---TP-Integrador-Final.git
```

#### 2 Generación de artefactos (.jar)

Antes de construir las imágenes de Docker, es necesario compilar y empaquetar cada microservicio dentro de su respectiva
carpeta:

```bash
# Compilar cada módulo generando su respectivo .jar en /target
mvn clean package -DskipTests
```

#### 3 Construcción de imágenes de Docker

Desde la carpeta raíz del proyecto, ejecutar el siguiente comando para generar las imagenes de Docker tomando las
versiones más recientes de los .jar:

```bash
docker compose up --build -d
```

#### 4 Verificación de instancias y acceso a panel de Eureka

Una vez completada la inicialización del contenedor, verificar en la terminal que todas las instancias se encuentren
funcionando:

```bash
# Chequear que se encuentren listados las 5 instancias del proyecto
docker ps
```

El panel de control de Eureka Server se encontrará disponible en la siguiente URL: http://localhost:8761

### Diagrama de proyecto

![Microservicios con Spring Cloud.jpg](Microservicios%20con%20Spring%20Cloud.jpg)

### ⚙️ Variables de Entorno y Configuración

Se muestra a continuación una tabla con información breve sobre la configuración de los microservicios implementados.

| Variable                               | Servicio(s)         | Valor en Docker                        | Descripción                                                                         |
|:---------------------------------------|:--------------------|:---------------------------------------|:------------------------------------------------------------------------------------|
| `SPRING_PROFILES_ACTIVE`               | Todos los servicios | `docker`                               | Activa las propiedades específicas de contenedor (`application-docker.properties`). |
| `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` | Clientes Eureka     | `http://eureka-server:8761/eureka/`    | URL de registro y resolución dinámica de nombres dentro de la red interna.          |
| `SERVER_PORT`                          | Cada microservicio  | `8761`, `9000`, `8000`, `8010`, `8020` | Puerto interno en el que escucha el servidor embebido Tomcat/Netty.                 |