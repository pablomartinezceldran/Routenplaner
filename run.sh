#!/bin/bash
# This script compiles and runs the server and opens the index.html in default browser.

echo "Compiling server..."
javac -d out src/main/java/Server.java src/main/java/services/Dijkstra.java src/main/java/models/k2Tree/K2Tree.java src/main/java/models/k2Tree/K2Node.java src/main/java/models/map/MapGraph.java
echo "Done!"

echo "Running server..."
java -Xmx8G -Dfile.encoding=UTF-8 -classpath out main.java.Server &
sleep 120
echo "Done!"

echo "Opening web client..."
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS
    open index.html
    echo "Done!"
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
    # Linux
    if command -v xdg-open >/dev/null; then
        xdg-open index.html
        echo "Done!"
    else
        echo "No xdg-open available. Please open index.html manually."
    fi
elif [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" ]]; then
    # Windows
    if command -v start >/dev/null; then
        start index.html
        echo "Done!"
    else
        echo "No start available. Please open index.html manually."
    fi
else
    echo "Unsupported operating system. Please open index.html manually."
fi