# Sistema Helpdesk e Inventario de Activos IT

Aplicación de escritorio (*Standalone*) diseñada para la gestión de incidencias informáticas y control de inventario de hardware en entornos locales[cite: 7]. 
<img width="788" height="552" alt="Video" src="https://github.com/user-attachments/assets/365cd64d-ebf9-4f7b-9941-c22ec8534179" />


## 🏗️ Arquitectura y Diseño
El sistema está construido bajo una estricta separación de responsabilidades:
* **Patrón MVC y DAO:** Aislamiento total entre la interfaz gráfica y la lógica de base de datos[cite: 7].
* **Base de Datos Embebida:** Uso de SQLite para garantizar la persistencia de datos sin necesidad de instalar motores SQL externos o servidores web[cite: 7].
* **Seguridad:** Implementación de consultas preparadas (`PreparedStatements`) a través de la API JDBC para bloquear intentos de inyección SQL en el módulo de autenticación[cite: 7].
* **Integridad Relacional:** Diseño de base de datos en Tercera Forma Normal (3FN) con control estricto de claves foráneas entre Usuarios, Equipos e Incidencias[cite: 7].

## 🛠️ Stack Tecnológico
* **Lenguaje:** Java (JDK 8+)[cite: 7]
* **Frontend:** Java Swing (Componentes ligeros y `DefaultTableModel` para carga dinámica)[cite: 7]
* **Persistencia:** SQLite + API JDBC[cite: 7]

## 🚀 Despliegue y Ejecución
La aplicación opera bajo un modelo *Plug and Play*. La clase `ConexionDB` evalúa el entorno en el primer arranque y, mediante sentencias DDL, autogenera el esquema de la base de datos `database.db` si no existe[cite: 7]. No requiere configuración de puertos ni acceso a internet[cite: 7].
