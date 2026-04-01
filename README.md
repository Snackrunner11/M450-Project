Der **Geräte-Manager** ist eine webbasierte Full-Stack-Anwendung auf Basis des **Spring Boot Frameworks**.Sie dient der effizienten digitalen Erfassung, Verwaltung und Statusverfolgung von Hardware-Beständen innerhalb einer Organisation.

---

## 🚀 Kernfunktionen

* **Zentrales Dashboard:** Eine tabellarische Übersicht aller Geräte inklusive ID, Produktbezeichnung, Typ und aktuellem Status.
* **Bestandsmanagement:** Über ein dediziertes Formular können neue Geräte (Produktname und Typ) zum System hinzugefügt werden.
* **Digitales Leihsystem (Checkout):** Verfügbare Geräte können durch Eingabe eines Namens direkt einer Person zugeordnet werden.
* **Echtzeit-Status:** Visuelle Kennzeichnung, ob ein Gerät verfügbar ist oder von wem es aktuell ausgeliehen wurde.
* **Rückgabeprozess:** Mit einem Klick können ausgeliehene Geräte freigegeben werden, woraufhin der Status sofort wieder auf „Verfügbar“ springt.

---

## 🛠️ Technologien

* **Backend:** Java 21 und Spring Boot 3.4.2.
* **Datenhaltung:** MySQL 8.0 Datenbank.
* **Frontend:** Serverseitiges Rendering mit Thymeleaf, HTML5, CSS3 und FontAwesome-Icons.
* **Infrastruktur:** Containerisierung mit Docker & Orchestrierung via Docker Compose.
* **Build-Management:** Maven.

---

## 📋 Voraussetzungen

Stellen Sie sicher, dass die folgenden Tools installiert sind:
* **Java 21** oder höher
* **Maven**
* **Docker & Docker Compose**

---

## ⚙️ Installation & Ausführung

### 1. Projekt bauen
Erzeugen Sie zunächst die JAR-Datei im `target`-Ordner:
```bash
mvn clean install -DskipTests
