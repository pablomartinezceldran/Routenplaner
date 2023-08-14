# Programming Project - Route Planner

A web-based route planner

## Members

- Pablo Martinez
- Esther Uerek
- Metin Arab

## How the Project is Carried Out

### Option 1:
- Execute the **run.sh** file.
  - *Wait time approximately 120 seconds*
- **Note**: When the execution is completed, the server is still open. To close it, execute the following commands (Linux and MacOS):

  - ```lsof -i:8080```
  - Look for the Java PID, for example, 33014.
  - ```kill 33014```

  - This is how you terminate the server execution on the computer.

### Option 2:
- In the current folder, execute the following commands:

```javac -d out src/main/java/**/*.java```

```java -Xmx8G -Dfile.encoding=UTF-8 -classpath out main.java.Server```

- Wait until the server is open (*Wait time approximately 100 seconds*).

- Open the index.html file in the browser:
  - Windows:
    ```start index.html```
  - MacOS:
    ```open index.html```
  - Linux:
    ```xdg-open index.html```

## Road Network File Link
Download the file (700 MB zipped) and save it in the **resources** folder:
- https://fmi.uni-stuttgart.de/files/alg/data/graphs/germany.fmi.bz2
