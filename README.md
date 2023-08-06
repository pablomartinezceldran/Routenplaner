# Programmierprojekt - Routenplaner

Ein webbasierter Routenplaner

## Mitglieder

- Pablo Martinez

- Esther Uerek

- Metin Arab

## Wie wird das Projekt durchgeführt

### Option 1:
- Führe die Datei **run.sh** aus.  
  - *Wartezeit ca. 120 Sekunden*
- **Hinweis**: Wenn die Ausführung beendet ist, ist der Server noch geöffnet. Um ihn zu schließen, führen Sie die folgenden Befehle aus:

  - ```lsof -i:8080```
  - Suche nach der Java-PID, zum Beispiel 33014.
  - ```kill 33014```

  - So beendet man die Ausführung des Servers auf dem Computer.

### Option 2:
- im aktuellen Ordner, führe die folgenden Befehle aus:

```javac -d out src/main/java/**/*.java```

```java -Xmx8G -Dfile.encoding=UTF-8 -classpath out main.java.Server```

- Warten Sie, bis der Server geöffnet ist (ungefähr 100 Sekunden).

- Öffnen Sie die Datei index.html im Browser:
  - Windows:
  ```start index.html```
  - MacOS:
  ```open index.html```
  - Linux:
  ```xdg-open index.html```