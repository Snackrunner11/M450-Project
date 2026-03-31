# Modul 347 - Abschlussarbeit

## Einführung
Dies ist ein Demo-Projekt für Spring Boot. Es zeigt, wie man eine **Spring Boot**-Anwendung mit einer **MySQL-Datenbank** und **Docker-Containern** einrichtet und ausführt. Ziel ist es, die grundlegenden Funktionen einer Webanwendung zu demonstrieren, einschließlich CRUD-Operationen (Erstellen, Lesen, Aktualisieren, Löschen) für eine einfache Entität.

## Voraussetzungen
- **Java 21**: Die Applikation verwendet Java 21, um von den neuesten Sprachfeatures und Performance-Verbesserungen zu profitieren.
- **Maven**: Als Build-Management-Tool sorgt Maven dafür, dass Abhängigkeiten automatisch verwaltet und das Projekt effizient gebaut wird.
- **Docker** und **Docker Compose**: Docker ermöglicht die Virtualisierung der Anwendung in Containern, während Docker Compose die Orchestrierung mehrerer Container erleichtert.

## Installation und Ausführung

1. **Projekt bauen**  
   Zuerst müssen Sie das Projekt bauen, um den `target`-Ordner zu erstellen und die JAR-Datei zu generieren:
   ```bash
   mvn clean install -DskipTests
   ```
   Dieser Befehl reinigt vorherige Builds und erstellt eine neue JAR-Datei im `target`-Verzeichnis.

2. **JAR-Datei überprüfen**  
   Stellen Sie sicher, dass die Datei `Lernen-0.0.1-SNAPSHOT.jar` im `target`-Ordner vorhanden ist. Diese Datei wird im **Dockerfile** verwendet, um das Docker-Image zu erstellen.

3. **Docker-Container starten**  
   Starten Sie die Docker-Container mit **Docker Compose**:
   ```bash
   docker-compose up --build
   ```
   Dieser Befehl baut die Docker-Images und startet sowohl die Anwendung als auch die MySQL-Datenbank in separaten Containern.

4. **Anwendung aufrufen**  
   Die Anwendung ist nun unter [http://localhost:8080/geraete/view](http://localhost:8080) erreichbar. Sie können über einen Webbrowser oder Tools wie `curl` auf die API-Endpunkte zugreifen.

---

## Verwendete Technologien

- **Spring Boot**: Ein Framework zur schnellen Erstellung von eigenständigen, produktionsreifen Spring-Anwendungen mit minimalem Konfigurationsaufwand.
- **Spring Web**: Dieses Modul wird verwendet, um REST-APIs zu erstellen und HTTP-Anfragen zu verarbeiten.
- **Spring Data JPA**: Dieses Modul vereinfacht die Interaktion mit relationalen Datenbanken durch das Java Persistence API (JPA).
- **Spring Data JDBC**: Für direkten Datenbankzugriff über JDBC, wenn einfache, performante Datenbankzugriffe notwendig sind.
- **Spring Boot Actuator**: Bietet Produktionsmetriken, Monitoring und Health-Checks der Anwendung (noch nicht konfiguriert).
- **Spring Boot Starter Test**: Ermöglicht das Durchführen von Unit- und Integrationstests.
- **Thymeleaf**: Eine Template-Engine zur Generierung von dynamischen HTML-Seiten auf der Serverseite.
- **MySQL Connector**: Der JDBC-Treiber, der die Verbindung zwischen der Anwendung und der MySQL-Datenbank ermöglicht.
- **MySQL**: Ein relationales Datenbankmanagementsystem zur Speicherung und Verwaltung der Anwendungsdaten.
- **Docker**: Eine Plattform zur Containerisierung von Anwendungen, die eine konsistente Ausführungsumgebung gewährleistet.
- **Docker Compose**: Ein Tool zur Definition und Ausführung von Multi-Container-Docker-Anwendungen.
- **Maven**: Ein Build-Management-Tool, das die Verwaltung von Projektabhängigkeiten, den Build-Prozess und das Deployment automatisiert.

---

## Architekturübersicht

1. **Microservices**: Die Anwendung besteht aus mehreren Microservices, die jeweils in separaten Docker-Containern laufen. Diese Struktur ermöglicht eine flexible Skalierung und einfache Wartbarkeit.

2. **Container-Infrastruktur**: 
   - **Docker** wird verwendet, um sowohl die Spring Boot-Anwendung als auch die MySQL-Datenbank zu virtualisieren.
   - Die Kommunikation zwischen den Containern erfolgt über ein internes Docker-Netzwerk, das eine sichere und effiziente Datenübertragung ermöglicht.

3. **Images**: 
   - Docker-Images werden über das **Dockerfile** erstellt. Diese Images enthalten alle notwendigen Abhängigkeiten und Konfigurationen, um die Anwendung in einer konsistenten Umgebung auszuführen.
   - Die erstellten Docker-Images können in Repositories wie **DockerHub** hochgeladen und von dort aus auf verschiedenen Systemen bereitgestellt werden.

4. **Orchestrierung**: 
   - Mit **Docker Compose** werden die verschiedenen Container koordiniert. Dies erleichtert das Management komplexer Anwendungen mit mehreren abhängigen Services.
   - Abhängigkeiten zwischen Containern (z.B. zwischen der Anwendung und der Datenbank) werden über `depends_on` definiert, was sicherstellt, dass die Dienste in der richtigen Reihenfolge gestartet werden.

**Projektstruktur:**
```plaintext
src/
├── main/
│   ├── java/
│   │   └── com/example/lernen/
│   │       ├── controller/       # Enthält die REST-Controller für die API-Endpunkte
│   │       ├── model/            # Enthält die Entitätsklassen, die die Datenbanktabellen repräsentieren
│   │       ├── repository/       # Enthält die Repository-Interfaces für die Datenbankzugriffe
│   │       └── service/          # Enthält die Geschäftslogik der Anwendung
│   └── resources/
│       ├── application.properties  # Konfigurationsdatei für die Anwendung
│       └── static/                # Statische Ressourcen wie CSS, JS oder Bilder
└── test/                         # Enthält Unit- und Integrationstests
```

---

## Implementierte Funktionen

### 1. Spring Boot Setup
- **Spring Boot Starter**: Das Projekt verwendet den Spring Boot Starter für die schnelle Einrichtung und Konfiguration der Anwendung.
- **Maven**: Maven wird als Build-Tool verwendet, um Abhängigkeiten zu verwalten und das Projekt zu bauen.

### 2. Datenbankintegration
- **MySQL**: Die Anwendung verwendet eine MySQL-Datenbank, die in einem Docker-Container ausgeführt wird.
- **Spring Data JPA**: Spring Data JPA wird verwendet, um die Datenbankoperationen zu vereinfachen und die CRUD-Operationen zu implementieren.

### 3. Docker-Setup
- **Dockerfile**: Ein Dockerfile wird verwendet, um das Docker-Image für die Anwendung zu erstellen. Es enthält Anweisungen zum Kopieren der JAR-Datei und zum Starten der Anwendung.
- **Docker Compose**: Docker Compose wird verwendet, um die Anwendung und die MySQL-Datenbank in separaten Containern zu starten und zu verwalten.

### 4. REST API
- **Controller**: Ein REST-Controller wird verwendet, um HTTP-Anfragen zu verarbeiten und die entsprechenden Antworten zurückzugeben.
- **Service**: Ein Service-Layer wird verwendet, um die Geschäftslogik zu kapseln und die Kommunikation zwischen dem Controller und dem Repository zu handhaben.
- **Repository**: Ein Repository-Interface wird verwendet, um die Datenbankoperationen zu definieren.

### 5. REST API Endpunkte
- **GET /entities**: Listet alle Entitäten auf.
- **POST /entities**: Erstellt eine neue Entität.
- **PUT /entities/{id}**: Aktualisiert eine bestehende Entität.
- **DELETE /entities/{id}**: Löscht eine Entität.

---

## Docker-Konfiguration

### Dockerfile
Das **Dockerfile** enthält die folgenden Anweisungen, um das Docker-Image zu erstellen:
```dockerfile
FROM openjdk:21-jdk-alpine
VOLUME /tmp
COPY target/Lernen-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Docker Compose
Die **docker-compose.yml** Datei enthält die Konfiguration für die Anwendung und die MySQL-Datenbank:
```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - db

  db:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: lernen
    ports:
      - "3306:3306"
```

---

## Anwendungskonfiguration
Die **application.properties** Datei enthält die Konfiguration für die Datenbankverbindung:
```properties
spring.datasource.url=jdbc:mysql://db:3306/lernen
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

**Erweiterung für Spring Boot Actuator:**
Um Health-Checks und Monitoring zu aktivieren, fügen Sie Folgendes hinzu:
```properties
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
```

---

## Datenbank
Die Spring Boot Applikation verwendet eine externe Postgres Datebank zum Austausch von Daten.
- **hostname:** dpg-cv1bpftds78s73dmomk0-a.frankfurt-postgres.render.com
- **database:** geraete
- **user:** m347
- **password:** TsFHAbD0H0O8NUbVqTik7q5CnQhVTCAe

## Fazit
Dieses Projekt zeigt, wie man eine **Spring Boot**-Anwendung mit einer **MySQL-Datenbank** einrichtet und in **Docker-Containern** ausführt. Es umfasst grundlegende **CRUD**-Operationen und verwendet **Spring Data JPA** zur Vereinfachung der Datenbankoperationen. **Docker** und **Docker Compose** werden verwendet, um die Anwendung und die Datenbank in separaten Containern zu verwalten.
