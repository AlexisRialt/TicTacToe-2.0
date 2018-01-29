# Tic-Tac-Toe 2.0 README

Instructions are the same for Linux and for Windows.

Install/Compile instructions
- install JRE/JDK >= 1.8
- install Apache Maven >= 3.2
- Make sure Java is in your path.
- Make sure Maven is in your path.
- ```git clone https://github.com/AlexisRialt/TicTacToe-2.0.git```
- ```cd TicTacToe-2.0```
- ```mvn package```

"mvn package" will download packages, run the tests, and create an executable .jar file.

Edit the file "config.properties" with the settings you like:
- ```../TicTacToe-2.0/config.properties```

Start the game :
- ```java -jar target/tictactoe-2.0.jar config.properties```

That's it.