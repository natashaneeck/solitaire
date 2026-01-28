# Solitaire

Project for CS3100 Project Design and Implementation 2 (Formerly called Object-Oriented Design)  
Provided by assignment: gradle and checkstyle files  

## Overview
MVC textual solitaire with customizable rules, decks, and pile sizes, tested with JUnit.  
The game is run through command-line arguments in the terminal.  
Users may customize the game between two different solitaire variants, and pick the number of cards in the draw pile and the number of cascade piles.  
Following a Model-View-Controller architecture means each component is separated into one of those three categories, improving encapsulation, coupling, and cohesion.  

## Quickstart
This assignment did not include creating an executable JAR file. 

From the project root, run:
```bash
javac -d build -sourcepath src/main/java src/main/java/klondike/Klondike.java
java -cp build klondike.Klondike basic 7 3
```

### Argument Options:
 - <gameType> (required)
   - `basic ` - classic solitaire, specifically Klondike
   - `whitehead ` - whitehead rules variant (see How to Play for more)
 - [numPiles] (optional) - number of cascade piles, defaults to 7
 - [numDraw] (optional) - number of draw cards, defaults to 3

## How to Play

### Standard Klondike
Foundation piles are on the top, and begin empty. Players will add cards to each from Ace upwards in order, in a single suit. The number of foundation piles is equal to the number of Aces in the deck.  

Cascade piles have a variable amount given at game start, defaulting to 7. Cards are dealt face-down in a triangle shape, left to right, up to down. The bottom card of each pile is face-up.  

Draw piles contain all remaining cards, with a fixed number revealed, as configured at game start. The number of revealed cards defaults to 3. Only the topmost revealed card is usable, though can be discarded.  

Built piles must form descending sequences of ranks in alternating colors

The game ends when no legal moves remain, and the score is equal to the number of cards in the foundation piles.  


### Whitehead Klondike
Whitehead is very similar to standard Klondike, with small changes:  
- All cascade pile cards are dealt face-up, so users can see what is below the top card
- Builds must be single-colored instead of alternating
- When moving multiple cards between cascade piles, the moved cards must form a single-suit run
- Any card can be moved into an empty cascade pile (not just Kings)

### Controls
All commands are typed into the terminal during gameplay. The game waits for the command to be typed and entered, then uses the Java Scanner library to determine which it is.  
Note all user-provided indices are 1-based, so the leftmost item in the view will be referred to as the first pile/draw card, not 0.   
Most commands are case-sensitive, however any q, lowercase or uppercase, will immediately terminate the game, no matter where it is in the input.  
Invalid inputs will cause an error message and another prompt for input to continue the game.  

- `mpp <sourcePile> <numCards> <destPile>`  
  Move cards from one cascade pile to another.

- `md <destPile>`  
  Move the top card from the draw pile to a cascade pile.

- `mpf <sourcePile> <foundation>`  
  Move the top card from a cascade pile to a foundation pile.

- `mdf <foundation>`  
  Move the top card from the draw pile to a foundation pile.

- `dd`  
  Discard the top card from the draw pile.

- `q` or `Q`  
  Quit the game immediately.

## Key Components

### Model
Handles rules of the game and keeps the gamestate.  
KlondikeModel interface and AbstractKlondike abstract class are implemented and extended for both versions of the game. This structure allows a simple process for adding additional variants.  
Utilizes smaller components like Cards and Piles, as well as Rank and Suit enumerations.  
The class ValidData helps with rule validation.  

### View
Renders the game in a textual view through the command-line.  
Never mutates the model state.  
Face-down cards are shown as `?` and empty foundation piles are represented as `<none>`.

### Controller
Connects the model and view, translating user input into game actions.  
The model handles valid moves, so the controller only validates whether a command exists and arguments are given to it.  
Invalid inputs result in a new prompt for the user to retry or quit.  
Controller is not dependent on a specific Solitaire variant.  

### Testing
Uses JUnit test suite, and covers all parts of MVC architecture.  
Mocks are used to test behaviors specific to the Readable and Appendable classes used.  
