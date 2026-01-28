package klondike.model.hw04;

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import klondike.model.hw02.BasicCard;
import klondike.model.hw02.BasicPile;
import klondike.model.hw02.KlondikeModel;
import klondike.model.hw02.PlayingCard;
import klondike.model.hw02.Rank;
import klondike.model.hw02.Suit;
import klondike.model.hw02.ValidData;

/**
 * An abstract class containing duplicate code across implementations of KlondikeModels.
 * Expects implementations to handle most face-up and face-down rules.
 */
public abstract class AbstractKlondike implements KlondikeModel<BasicCard> {
  private boolean started;
  private List<BasicCard> deck;
  private ArrayList<ArrayList<BasicCard>> foundations;
  protected ArrayList<BasicPile> cascades;
  private List<BasicCard> drawCards;
  private int numDrawCards;
  private final ValidData<BasicCard> dataChecker;


  /**
   * Initializes the game into a state that is ready for someone to call startGame and begin
   * playing. Game is not yet playable after just running the constructor.
   */
  public AbstractKlondike(ValidData<BasicCard> dataChecker) {
    this.started = false;
    this.dataChecker = dataChecker;
  }

  /**
   * Return a valid and complete deck of cards for a game of Klondike.
   * There is no restriction imposed on the ordering of these cards in the deck.
   * The validity of the deck is determined by the rules of the specific game in
   * the classes implementing this interface.  This method may be called as often
   * as desired.
   *
   * @return the deck of cards as a list
   */
  @Override
  public List<BasicCard> createNewDeck() {
    List<Suit> suits = List.of(Suit.DIAMONDS, Suit.CLUBS, Suit.HEARTS, Suit.SPADES);

    List<Rank> ranks =
        List.of(Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX, Rank.SEVEN,
            Rank.EIGHT, Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING);

    List<BasicCard> newDeck = new ArrayList<BasicCard>();

    for (Suit suit : suits) {
      for (Rank rank : ranks) {
        newDeck.add(new BasicCard(rank, suit));
      }
    }
    return newDeck;
  }

  /**
   * Deal a new game of Klondike.
   * The cards to be used and their order are specified by the given deck,
   * unless the {@code shuffle} parameter indicates the order should be ignored.
   *
   * <p>This method first verifies that the deck is valid. It deals cards in rows
   * (left-to-right, top-to-bottom) into the characteristic cascade shape
   * with the specified number of rows, followed by (at most) the specified number of
   * drawCards cards. When {@code shuffle} is {@code false}, the {@code deck}
   * must be used in order and
   * the 0th card in {@code deck} is used as the first card dealt.
   * There will be as many foundation piles as there are Aces in the deck.</p>
   *
   * <p>A valid deck must consist cards that can be grouped into equal-length,
   * consecutive runs of cards (each one starting at an Ace, and each of a single suit).</p>
   *
   * <p>This method should have no side effects other than configuring this model instance,
   * and should work for any valid arguments.</p>
   *
   * @param deck     the deck to be dealt
   * @param shuffle  if {@code false}, use the order as given by {@code deck},
   *                 otherwise use a randomly shuffled order
   * @param numPiles number of piles to be dealt
   * @param numDraw  maximum number of drawCards cards available at a time
   * @throws IllegalStateException    if the game has already started
   * @throws IllegalArgumentException if the deck is null or invalid,
   *                                  a full cascade cannot be dealt with the given sizes,
   *                                  or another input is invalid
   */
  @Override
  public void startGame(List<BasicCard> deck, boolean shuffle, int numPiles, int numDraw)
      throws IllegalArgumentException, IllegalStateException {
    if (started) {
      throw new IllegalStateException("Game is already started");
    }
    if (!this.dataChecker.validateDeck(deck) || deck.size() < numPiles || numDraw <= 0
        || numPiles <= 0) {
      throw new IllegalArgumentException("Invalid state to start a game in");
    }

    this.started = true;
    this.deck = new ArrayList<>(deck);

    if (shuffle) {
      Collections.shuffle(this.deck);
    }

    this.cascades = new ArrayList<BasicPile>();
    for (int index = 0; index < numPiles; index++) {
      this.cascades.add(new BasicPile());
    }

    this.foundations = new ArrayList<ArrayList<BasicCard>>();
    for (int index = 0; index < this.dataChecker.numAces(deck); index++) {
      this.foundations.add(new ArrayList<BasicCard>());
    }

    for (int row = 0; row < numPiles; row++) {
      for (int column = row; column < numPiles; column++) {
        this.cascades.get(column).buildPile(this.deck.removeFirst());
      }
    }

    this.drawCards = new ArrayList<BasicCard>();
    this.numDrawCards = numDraw;
    for (int index = 0; index < numDraw; index++) {
      this.revealNextDraw();
    }
  }

  /**
   * Moves the requested number of cards from the source pile to the destination pile,
   * if allowable by the rules of the game.
   *
   * @param srcPile  the 0-based index (from the left) of the pile to be moved
   * @param numCards how many cards to be moved from that pile
   * @param destPile the 0-based index (from the left) of the destination pile for the moved cards
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if either pile number is invalid, if the pile numbers are
   *                                  the same, or there are not enough cards to move
   *                                  from the srcPile to the
   *                                  destPile (i.e. the move is not physically possible)
   * @throws IllegalStateException    if the move is not allowable (i.e. the move is not logically
   *                                  possible)
   */
  @Override
  public void movePile(int srcPile, int numCards, int destPile)
      throws IllegalArgumentException, IllegalStateException {
    this.checkStarted();

    if (!this.dataChecker.validPileIndex(srcPile, this.cascades.size())
        || !this.dataChecker.validPileMove(srcPile, numCards, destPile,
        this.cascades.size(), this.cascades.get(srcPile).countVisibleCards())) {
      throw new IllegalArgumentException("Move is not physically possible");
    }

    ArrayList<BasicCard> movingPile = new ArrayList<>();
    int removeIndex = this.getPileHeight(srcPile) - numCards;
    while (this.getPileHeight(srcPile) > removeIndex) {
      movingPile.add(this.cascades.get(srcPile).removeIndex(removeIndex));
    }

    this.dataChecker.validAddToCascade(this.cascades.get(destPile), new BasicPile(movingPile));

    this.cascades.get(destPile).addAll(movingPile);
    this.cascades.get(srcPile).removeAll(movingPile);

    if (!this.cascades.get(srcPile).getList().isEmpty()) {
      this.cascades.get(srcPile).revealLast();
    }
  }

  /**
   * Moves the topmost drawCards-card to the destination pile.  Reveals the next
   * available drawCards cards (if any).
   *
   * @param destPile the 0-based index (from the left) of the destination pile for the card
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if destination pile number is invalid
   * @throws IllegalStateException    if there are no drawCards cards, or
   *                                  if the move is not allowable
   */
  @Override
  public void moveDraw(int destPile) throws IllegalArgumentException, IllegalStateException {
    this.checkStarted();

    if (destPile < 0 || destPile >= this.cascades.size()) {
      throw new IllegalArgumentException("Invalid destination pile index");
    }

    if (this.getDrawCards().isEmpty()) {
      throw new IllegalStateException("No draw cards to move");
    }

    BasicCard nextDraw = this.drawCards.getFirst();
    ArrayList<BasicCard> toAdd = new ArrayList<>();
    toAdd.add(nextDraw);
    this.dataChecker.validAddToCascade(this.cascades.get(destPile), new BasicPile(toAdd));

    this.cascades.get(destPile).addCard(nextDraw);
    this.drawCards.remove(nextDraw);
    this.revealNextDraw();
  }

  /**
   * Reveals the next draw card, taking it from the top of the deck and adding it to the list of
   * draw cards. If the draw pile deck is empty, does not flip over a card.
   *
   * @throws IllegalStateException if the maximum number of draw cards are already visible
   */
  private void revealNextDraw() throws IllegalStateException {
    if (this.getDrawCards().size() >= this.getNumDraw()) {
      throw new IllegalStateException("Cannot reveal next draw card");
    }
    if (!this.deck.isEmpty()) {
      this.drawCards.add(this.deck.getFirst());
      this.deck.removeFirst();
    }
  }

  /**
   * Moves the top card of the given pile to the requested foundation pile.
   *
   * @param srcPile        the 0-based index (from the left) of the pile to move a card
   * @param foundationPile the 0-based index (from the left) of the foundation pile to place card
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if either pile number is invalid
   * @throws IllegalStateException    if the source pile is empty or if the move is not allowable
   */
  @Override
  public void moveToFoundation(int srcPile, int foundationPile)
      throws IllegalArgumentException, IllegalStateException {
    this.checkStarted();

    if (srcPile < 0 || foundationPile < 0 || srcPile >= this.getNumPiles()
        || foundationPile >= this.getNumFoundations()) {
      throw new IllegalArgumentException("Invalid pile index");
    }

    if (this.cascades.get(srcPile).getList().isEmpty()) {
      throw new IllegalStateException("Cascade source pile is empty");
    }

    BasicCard moving = this.cascades.get(srcPile).getList().getLast();

    this.dataChecker.validAddToFoundation(moving, this.foundations.get(foundationPile));

    this.cascades.get(srcPile).removeCard(moving);
    this.foundations.get(foundationPile).add(moving);

    if (!this.cascades.get(srcPile).getList().isEmpty()) {
      this.cascades.get(srcPile).revealLast();
    }
  }

  /**
   * Moves the topmost drawCards-card directly to a foundation pile, and reveals
   * the next drawCards card, if there are any.
   *
   * @param foundationPile the 0-based index (from the left) of the foundation pile to place card
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the foundation pile number is invalid
   * @throws IllegalStateException    if there are no drawCards cards
   *                                  or if the move is not allowable
   */
  @Override
  public void moveDrawToFoundation(int foundationPile)
      throws IllegalArgumentException, IllegalStateException {
    this.checkStarted();

    if (foundationPile < 0 || foundationPile >= this.getNumFoundations()) {
      throw new IllegalArgumentException("Invalid pile index");
    }
    if (this.getDrawCards().isEmpty()) {
      throw new IllegalStateException("Draw card pile is empty");
    }

    BasicCard moving = this.drawCards.getFirst();

    this.dataChecker.validAddToFoundation(moving, this.foundations.get(foundationPile));

    this.drawCards.remove(moving);
    this.foundations.get(foundationPile).add(moving);
  }

  /**
   * Discards the topmost drawCards-card.
   *
   * @throws IllegalStateException if the game hasn't been started yet
   * @throws IllegalStateException if move is not allowable
   */
  @Override
  public void discardDraw() throws IllegalStateException {
    this.checkStarted();

    if (this.deck.isEmpty()) {
      throw new IllegalStateException("Cannot discard to empty draw pile");
    }

    this.deck.add(this.drawCards.getFirst());
    this.drawCards.removeFirst();
    this.revealNextDraw();
  }

  /**
   * Returns the number of rows currently in the game.
   *
   * @return the height of the current table of cards
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public int getNumRows() throws IllegalStateException {
    this.checkStarted();
    int maxHeight = 0;

    for (int index = 0; index < this.getNumPiles(); index++) {
      maxHeight = max(maxHeight, this.getPileHeight(index));
    }
    return maxHeight;
  }

  /**
   * Returns the number of piles for this game.
   *
   * @return the number of piles
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public int getNumPiles() throws IllegalStateException {
    this.checkStarted();
    return this.cascades.size(); //do I have to check if any are empty and change the number?
  }

  /**
   * Returns the maximum number of visible cards in the drawCards pile set in initialization.
   *
   * @return the maximum allowable number of visible cards in the drawCards pile
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public int getNumDraw() throws IllegalStateException {
    this.checkStarted();
    return this.numDrawCards;
  }

  /**
   * Signal if the game is over or not.  A game is over if there are no more
   * possible moves to be made, and no drawCards cards to be used (or discarded).
   *
   * @return true if game is over, false otherwise
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public boolean isGameOver() throws IllegalStateException {
    this.checkStarted();

    if (!this.getDrawCards().isEmpty()) {
      return false;
    }

    boolean moveExists = false;

    for (BasicPile pile : this.cascades) {
      if (!pile.getList().isEmpty()) {
        for (ArrayList<BasicCard> foundation : this.foundations) {
          try {
            this.dataChecker.validAddToFoundation(pile.getList().getLast(), foundation);
            moveExists = true;
          } catch (IllegalStateException exception) {
            moveExists = moveExists || false;
          }
        }
      }
    }
    if (moveExists) {
      return false;
    }

    for (BasicPile pile : this.cascades) {
      if (!pile.getList().isEmpty()) {
        ArrayList<PlayingCard> visibleCards = new ArrayList<>();
        for (int index = Math.max(0, (pile.getPileSize() - pile.countVisibleCards() - 1));
             index < pile.getPileSize(); index++) {
          visibleCards.add(pile.getList().get(index));
        }

        for (int repeats = visibleCards.size(); repeats > 0; repeats--) {
          for (BasicPile cascade : this.cascades) {
            if (!visibleCards.isEmpty() && !cascade.getList().isEmpty()) {
              if (cascade != pile && this.dataChecker.validNext(visibleCards.getFirst(),
                  cascade.getList().getLast())) {
                return false;
              }
              visibleCards.removeFirst();
            }
          }
        }
      }
    }
    return true;
  }

  /**
   * Return the current score,  which is the number of cards in the foundation piles.
   *
   * @return the score
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public int getScore() throws IllegalStateException {
    this.checkStarted();
    int score = 0;

    for (ArrayList<BasicCard> pile : this.foundations) {
      if (!pile.isEmpty()) {
        score += pile.size();
      }
    }
    return score;
  }

  /**
   * Returns the number of cards in the specified pile.
   *
   * @param pileNum the 0-based index (from the left) of the pile
   * @return the number of cards in the specified pile
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if pile number is invalid
   */
  @Override
  public int getPileHeight(int pileNum) throws IllegalArgumentException, IllegalStateException {
    this.checkStarted();
    if (pileNum >= this.cascades.size() || pileNum < 0) {
      throw new IllegalArgumentException("Pile number given is invalid.");
    }
    return this.cascades.get(pileNum).getPileSize();
  }

  /**
   * Returns the card at the specified coordinates, if it is visible.
   *
   * @param pileNum column of the desired card (0-indexed from the left)
   * @param card    row of the desired card (0-indexed from the top)
   * @return the card at the given position
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the coordinates are invalid
   */
  @Override
  public BasicCard getCardAt(int pileNum, int card)
      throws IllegalArgumentException, IllegalStateException {
    this.checkStarted();

    if (pileNum >= this.cascades.size() || card > this.getPileHeight(pileNum)) {
      throw new IllegalArgumentException("Invalid coordinates, bad indices");
    }
    if (!this.isCardVisible(pileNum, card)) {
      throw new IllegalArgumentException("Invalid coordinates, only get card when it is visible");
    }

    return this.cascades.get(pileNum).getList().get(card);
  }

  /**
   * Returns the card at the top of the specified foundation pile.
   *
   * @param foundationPile 0-based index (from the left) of the foundation pile
   * @return the card at the given position, or null if no card is there
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the foundation pile number is invalid
   */
  @Override
  public BasicCard getCardAt(int foundationPile)
      throws IllegalArgumentException, IllegalStateException {
    this.checkStarted();

    if (foundationPile < 0 || foundationPile >= this.getNumFoundations()) {
      throw new IllegalArgumentException("Invalid pile index");
    }

    if (this.foundations.get(foundationPile).isEmpty()) {
      return null;
    }

    return this.foundations.get(foundationPile).getLast();
  }

  /**
   * Returns whether the card at the specified coordinates is face-up or not.
   *
   * @param pileNum column of the desired card (0-indexed from the left)
   * @param card    row of the desired card (0-indexed from the top)
   * @return whether the card at the given position is face-up or not
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the coordinates are invalid
   */
  @Override
  public boolean isCardVisible(int pileNum, int card)
      throws IllegalArgumentException, IllegalStateException {
    this.checkStarted();

    if (pileNum < 0 || pileNum >= this.cascades.size() || card < 0
        || card >= this.cascades.get(pileNum).getPileSize()) {
      throw new IllegalArgumentException("Invalid coordinates");
    }

    return this.cascades.get(pileNum).isCardVisible(card);
  }

  /**
   * Returns the currently available drawCards cards.
   * There should be at most {@link KlondikeModel#getNumDraw} cards (the number
   * specified when the game started) -- there may be fewer, if cards have been removed.
   * If any user modifies the resulting list, there should be no effect on
   * the model.
   *
   * @return the ordered list of available drawCards cards (first element = first to draw)
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public List<BasicCard> getDrawCards() throws IllegalStateException {
    this.checkStarted();

    ArrayList<BasicCard> drawCards = new ArrayList<>();
    for (BasicCard card : this.drawCards) {
      drawCards.add(new BasicCard(card));
    }

    return drawCards;
  }

  /**
   * Return the number of foundation piles in this game.
   *
   * @return the number of foundation piles
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public int getNumFoundations() throws IllegalStateException {
    this.checkStarted();
    return this.foundations.size();
  }

  /**
   * Ensures the game has been started so other methods can run safely.
   *
   * @throws IllegalStateException when game has not been started.
   */
  private void checkStarted() throws IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("Game has not been started");
    }
  }

}
