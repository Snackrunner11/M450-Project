Der **Geräte-Manager** ist eine webbasierte Full-Stack-Anwendung auf Basis des **Spring Boot Frameworks**.Sie dient der effizienten digitalen Erfassung, Verwaltung und Statusverfolgung von Hardware-Beständen innerhalb einer Organisation.

---

## 🚀 Kernfunktionen

* **Zentrales Dashboard:** Eine tabellarische Übersicht aller Geräte inklusive ID, Produktbezeichnung, Typ und aktuellem Status.
* **Bestandsmanagement:** Über ein dediziertes Formular können neue Geräte (Produktname und Typ) zum System hinzugefügt werden.
* **Digitales Leihsystem (Checkout):** Verfügbare Geräte können durch Eingabe eines Namens direkt einer Person zugeordnet werden.
* **Echtzeit-Status:** Visuelle Kennzeichnung, ob ein Gerät verfügbar ist oder von wem es aktuell ausgeliehen wurde.
* **Rückgabeprozess:** Mit einem Klick können ausgeliehene Geräte freigegeben werden, woraufhin der Status sofort wieder auf „Verfügbar“ springt.

---

## 🧪 Test-Driven Development (TDD) Strategie

Die weitere Entwicklung des **Geräte-Managers** wird nach den Prinzipien des Test-Driven Development (TDD) durchgeführt. Das Ziel dieser Strategie ist es, eine hohe Code-Qualität sicherzustellen, Fehler frühzeitig zu erkennen und sicherzustellen, dass neuer Code stets durch automatisierte Tests abgedeckt ist. TDD zwingt dazu, die Anforderungen an ein neues Feature genau zu verstehen, bevor die eigentliche Implementierung beginnt.

### 🔄 Das TDD-Grundprinzip: Der Red-Green-Refactor Zyklus
Für jede neue Funktion durchlaufen wir strikt den folgenden Zyklus:

1. **🔴 RED (Test schreiben):** Bevor produktiver Code geschrieben wird, wird ein Test für die gewünschte Funktionalität erstellt. Da der produktive Code noch fehlt, *muss* dieser Test fehlschlagen.
2. **🟢 GREEN (Code implementieren):** Es wird nur so viel produktiver Code geschrieben, wie nötig ist, um den zuvor geschriebenen Test erfolgreich bestehen zu lassen.
3. **🔵 REFACTOR (Code optimieren):** Der geschriebene Code wird optimiert, strukturiert und bereinigt (z. B. Entfernen von Duplikaten, bessere Namensgebung), ohne dass die Funktionalität verändert wird. Die Tests stellen sicher, dass dabei nichts kaputtgeht.

### 🏗️ TDD-Strategie für den Geräte-Manager
Wir stützen uns auf die bereits etablierten Werkzeuge aus der bestehenden Testumgebung: **JUnit 5**, **Spring Boot Starter Test**, **Mockito** sowie **MockMvc** und **Selenium**. Die Entwicklung neuer Features erfolgt mit einem **Bottom-Up-Ansatz**:

#### Phase 1: Datenzugriffsschicht (Repositories)
* **Strategie:** Wenn ein neues Feature neue Datenbankabfragen erfordert, starten wir im Repository.
* **TDD-Umsetzung:** Wir schreiben einen Test mit `@DataJpaTest` gegen die H2-In-Memory-Datenbank.

#### Phase 2: Geschäftslogik und Controller (Web-Layer)
* **Strategie:** Als Nächstes entwickeln wir die Endpunkte und die Logik, die die Repositories aufrufen.
* **TDD-Umsetzung:** Wir nutzen `MockMvc` für Controller-Tests und isolieren die Repositories mit Mockito.

#### Phase 3: Benutzeroberfläche (Frontend)
* **Strategie:** Die neuen Funktionen in die Thymeleaf-Templates integrieren.
* **TDD-Umsetzung:** Systemtests (End-to-End) mit Werkzeugen wie Selenium erstellen, die das Feature aus Nutzersicht simulieren.

### 📝 Geplanter Implementierungsablauf (Definition of Done)
1. **Analyse:** Anforderung ist klar formuliert (z. B. "Als User möchte ich Geräte löschen können").
2. **Test-Skelette anlegen:** Leere Testmethoden in der Testklasse erstellen (z.B. `GeraeteControllerTest`).
3. **Zyklus starten:** Unit-Test schreiben (RED) ➡️ Controller-Methode implementieren ➡️ Test ausführen (GREEN).
4. **Integration prüfen:** Integrationstest über H2-Test-Datenbank schreiben und ausführen. Falls nötig Refactoring durchführen.
5. **UI anpassen:** UI-Elemente in Thymeleaf integrieren und E2E-Tests durchlaufen lassen.

### ⚠️ Risiken und Regeln beim TDD-Einsatz
* **Regel 1:** Kein produktiver Code ohne fehlschlagenden Test!
* **Regel 2:** Teste das "Was" (Verhalten), nicht das "Wie" (interne Implementierungsdetails), um Refactoring zu erleichtern.
* **Risiko-Mitigation:**
  * Um die MySQL-Produktionsdatenbank nicht zu beeinflussen, werden für Integrationstests strikt **H2-In-Memory-Datenbanken** eingesetzt.
  * Bei instabilen E2E-Browser-Tests (z. B. durch Ladezeiten) werden konsequent "Explicit Waits" in Selenium verwendet.

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
