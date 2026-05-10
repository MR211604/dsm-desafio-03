# App de Gestión de Recursos (dsmdesafio03)

**Autor**: Leo Fernando Miranda Rodriguez

Esta aplicación de Android permite la exploración, gestión y valoración de distintos recursos de aprendizaje (Libros, Videos, Artículos y Cursos). Está diseñada con una arquitectura basada en roles que distingue a los usuarios regulares de los administradores.

## Video Demostrativo
Pueden verificar detalladamente el funcionamiento completo de la aplicación y operaciones CRUD en el entorno real de la app en esta demo: [
Desmostración de aplicación - desafio 3 DSM](https://www.youtube.com/watch?v=R6V8i5twpO0)

El proposito principal de esta aplicacion es demostrar el uso de Firebase para autenticacion y operaciones CRUD de destinos turisticos.

## Funcionalidades Principales

### Rol Usuario (Regular)
El usuario estándar cuenta con las siguientes capacidades dentro de la aplicación:

*   **Autenticación y Perfil:**
    *   Registro e inicio de sesión mediante credenciales.
    *   Visualización y gestión del perfil de usuario.
*   **Exploración de Recursos:**
    *   **Pantalla de Inicio (Home):** Visualizar el listado de recursos disponibles.
    *   **Filtrado por Categorías:** Filtrar mediante "Chips" interactivos los recursos por tipo (`ALL`, `BOOK`, `VIDEO`, `ARTICLE`, `COURSE`).
    *   **Búsqueda:** Búsqueda por texto directo para encontrar recursos específicos.
*   **Detalle y Valoración:**
    *   Acceder para ver información detallada de cualquier recurso (título, descripción, imagen y enlace externo).
    *   Posibilidad de calificar el recurso.
*   **Gestión de Favoritos:**
    *   Marcar o desmarcar un recurso como Favorito.
    *   Sección exclusiva para consultar la lista de sus recursos favoritos guardados.

### Rol Administrador
El perfil de Administrador hereda las funcionalidades de consumo del usuario regular, pero cuenta con un **Panel de Control (Admin Panel)** con acceso restringido para la gestión integral del contenido:

*   **Creación de Recursos:** Permite añadir nuevos recursos didácticos especificando título, descripción, URL, imagen y tipo de recurso.
*   **Edición de Recursos:** Capacidad para actualizar los campos de un recurso existente si ha cambiado la información o hay que corregirla.
*   **Eliminación de Recursos:** Borrar de la base de datos recursos que ya no sean relevantes o estén obsoletos.
*   **Control de Paginación y Listado:** Ver en detalle todos los recursos creados gestionando su visualización con controles de paginación o búsquedas específicas.
