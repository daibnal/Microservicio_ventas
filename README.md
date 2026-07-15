# Microservicio de Ventas - EcoMarket SPA

## Descripción del proyecto
El microservicio de Ventas es responsable de registrar las ventas realizadas por los clientes, administrar los métodos de pago y generar los documentos asociados a cada transacción. Además, recibe información desde el microservicio de Pedidos para registrar automáticamente las ventas generadas.

## Integrantes
- Dairys Bernal
- Rocio Bustos
- Amaru Burdiles

## Microservicios implementados
- API Gateway
- Usuarios
- Inventario
- Catálogo
- Cupones
- Pedidos
- Ventas
- Envíos
- Reportes y Soporte

## Rutas principales
Registrar venta
POST: /api/ventas

Listar ventas
GET: /api/ventas

Consultar venta
GET: /api/ventas/{id}

Actualizar estado
PUT: /api/ventas/{id}

Métodos de pago
GET: /api/metodos

Documentos de venta
GET: /api/documentos

## Documentación Swagger
Swagger UI
http://localhost:8084/doc/swagger-ui.html

OpenAPI
http://localhost:8084/v3/api-docs

## Ejecución local
1. Clonar el repositorio.
2. Configurar la base de datos MySQL correspondiente.
3. Ejecutar el proyecto desde Spring Boot.
4. Verificar que el servicio se encuentre disponible en el puerto **8084**.
5. Acceder a Swagger para probar los endpoints.

## Ejecución mediante API Gateway
Si el API Gateway está ejecutándose, las solicitudes pueden realizarse mediante:

http://localhost:9000/api/ventas
