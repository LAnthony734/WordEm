Date Created: 05/10/22

---

WordEm© is a word guessing game based on the popular web-based New York Times game Wordle©. It implements a Mastermind-like approach to word guessing, as the gamer uses their acquired clues to narrow in on the hidden word! My team and I initially wrote this game as a school project to improve our skills in team organization and communication, software architecture and design, and JAVA Swing as well as to simply pay homage to Wordle©. As such, this game is not intended for external use.

https://en.wikipedia.org/wiki/Wordle

https://www.nytimes.com/games/wordle/index.html

---

**Getting Started**:

Running WordEm.bat will launch a new game with the existing Data.sqlite database. This .bat creates and runs a .jar of all compiled byte code in bin/wordem using manifest.txt. The main method for launching the game is provided in src/wordem.GameLauncher. The Words_en.txt and Words_es.txt files are used exclusively for constructing a new database. The main method for constructing this database is provided in src/wordem.DatabaseConstructor.

*Disclaimer*: Although effort was made to remove profanity from the word tables, some may still exist.
