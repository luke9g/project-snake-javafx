## Project Snake (JavaFX)

My implementation of the popular game "Snake", where the player controls a growing line of squares (aka "snake"). App 
goal of the game is to collect points and avoid hitting yourself, or the walls of board.

The game was created as the final student project at the end of semester II. (wymagania.......)

### Setup
_The project uses Java 11_

1. ściągnąć pliki do wybranego folderu
2. przejść w konsoli do folderu z programem
3. `javac -encoding utf-8 App.java`
4. `java App`

### Manual
####Controls
* `🠈` `🠊` `🠉` `🠋` - changes movement direction of the snake
* `space` - pauses the game
* `Ctrl` + `Q` - ends the game and goes back to the main menu
####Food
* red - worth 1 point, increase the length of the snake by 1 unit
* yellow - worth 1 point, decrease the length of the snake by 1 unit
####Scoring
* final score is calculated based on the following formula:
  <br>
  <img src="https://latex.codecogs.com/svg.latex?finalScore&space;=&space;\frac{eatenFood&space;\cdot&space;time&space;\cdot&space;10}{boardSize}"/>
  <br>
  where `boardSize` is the number of board cells
  
### Screenshots
