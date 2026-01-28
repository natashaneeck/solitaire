package klondike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import klondike.model.hw02.BasicCard;
import klondike.model.hw02.KlondikeModel;
import klondike.model.hw04.WhiteheadKlondike;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for WhiteheadKlondike model.
 */
public class WhiteheadKlondikeModelTests {
  KlondikeModel<BasicCard> standardGame;
  KlondikeModel<BasicCard> smallGame;
  KlondikeModel<BasicCard> easyGame;

  /**
   * Creates KlondikeModels with decks for testing.
   */
  @Before
  public void setUp() {
    standardGame = new WhiteheadKlondike();
    standardGame.startGame(standardGame.createNewDeck(), false, 7, 3);

    smallGame = new WhiteheadKlondike();
    List<BasicCard> testingDeck = new ArrayList<>();

    for (BasicCard card : smallGame.createNewDeck()) {
      if (Arrays.asList(new String[] {"A♣", "2♣", "3♣", "A♡", "2♡", "3♡"})
          .contains(card.toString())) {
        testingDeck.add(card);
      }
    }

    smallGame.startGame(testingDeck, false, 3, 1);
    smallGame.moveToFoundation(0, 0);

    easyGame = new WhiteheadKlondike();
    List<BasicCard> simpleDeck = new ArrayList<>();

    for (BasicCard card : easyGame.createNewDeck()) {
      if (Arrays.asList(new String[] {"A♢", "2♢", "3♢", "A♣", "2♣", "3♣", "A♡", "2♡", "3♡"})
          .contains(card.toString())) {
        simpleDeck.add(card);
      }
    }

    easyGame.startGame(simpleDeck, false, 3, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithInvalidDeck() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    List<BasicCard> testingDeck = new ArrayList<>();

    for (BasicCard card : badGame.createNewDeck()) {
      if (Arrays.asList(new String[] {"A♡", "3♡", "A♣", "2♣", "3♣"})
          .contains(card.toString())) {
        testingDeck.add(card);
      }
    }

    badGame.startGame(testingDeck, false, 3, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithBadDrawParam() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    List<BasicCard> testingDeck = new ArrayList<>();

    for (BasicCard card : badGame.createNewDeck()) {
      if (Arrays.asList(new String[] {"A♡", "2♡", "3♡", "A♣", "2♣", "3♣"})
          .contains(card.toString())) {
        testingDeck.add(card);
      }
    }

    badGame.startGame(testingDeck, false, 3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithBadPileNumParam() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    List<BasicCard> testingDeck = new ArrayList<>();

    for (BasicCard card : badGame.createNewDeck()) {
      if (Arrays.asList(new String[] {"A♡", "2♡", "3♡", "A♣", "2♣", "3♣"})
          .contains(card.toString())) {
        testingDeck.add(card);
      }
    }

    badGame.startGame(testingDeck, false, -2, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithTooSmallDeck() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    List<BasicCard> testingDeck = new ArrayList<>();

    for (BasicCard card : badGame.createNewDeck()) {
      if (Arrays.asList(new String[] {"A♡", "2♡", "3♡", "A♣", "2♣", "3♣"})
          .contains(card.toString())) {
        testingDeck.add(card);
      }
    }

    badGame.startGame(testingDeck, false, 20, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithEmptyDeck() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    List<BasicCard> testingDeck = new ArrayList<>();

    badGame.startGame(testingDeck, false, 20, 1);
  }

  @Test
  public void testGetScore() {
    assertEquals(0, standardGame.getScore());
    assertEquals(1, smallGame.getScore());
  }

  @Test
  public void testCreateNewDeck() {
    String[] standardDeck = new String[] {"A♢", "2♢", "3♢", "4♢", "5♢", "6♢", "7♢",
        "8♢", "9♢", "10♢", "J♢", "Q♢", "K♢", "A♣", "2♣", "3♣", "4♣", "5♣", "6♣", "7♣", "8♣",
        "9♣", "10♣", "J♣", "Q♣", "K♣", "A♡", "2♡", "3♡", "4♡", "5♡", "6♡",
        "7♡", "8♡", "9♡", "10♡", "J♡", "Q♡", "K♡", "A♠", "2♠", "3♠", "4♠",
        "5♠", "6♠", "7♠", "8♠", "9♠", "10♠", "J♠", "Q♠", "K♠"};
    List<BasicCard> createdDeck = standardGame.createNewDeck();

    for (int index = 0; index < standardDeck.length; index++) {
      assertEquals(standardDeck[index], createdDeck.get(index).toString());
    }
  }

  @Test
  public void testMovePileAnyCardToEmptyCascade() {
    assertEquals(0, smallGame.getPileHeight(0));
    smallGame.movePile(2, 1, 0);
    assertEquals(1, smallGame.getPileHeight(0));
  }

  @Test
  public void testMovePileValidSingleColorRunMultipleCards() {
    assertEquals(0, smallGame.getPileHeight(0));
    smallGame.movePile(2, 1, 0);
    smallGame.movePile(1, 1, 2);
    smallGame.movePile(2, 2, 0);
    assertEquals(3, smallGame.getPileHeight(0));
    assertEquals(1, smallGame.getPileHeight(2));
  }

  @Test
  public void testMoveSingleCardToSameColorDifferentSuitPile() {
    assertEquals(1, standardGame.getPileHeight(0));
    assertEquals(7, standardGame.getPileHeight(6));
    standardGame.movePile(0, 1, 6);
    assertEquals(0, standardGame.getPileHeight(0));
    assertEquals(8, standardGame.getPileHeight(6));
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveToDifferentColorFails() {
    assertEquals(7, standardGame.getPileHeight(6));
    assertEquals(3, standardGame.getPileHeight(2));
    standardGame.movePile(6, 1, 2);
    assertEquals(6, standardGame.getPileHeight(6));
    assertEquals(4, standardGame.getPileHeight(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePilePilesEqual() {
    this.standardGame.movePile(0, 1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePileLowSrcPile() {
    this.standardGame.movePile(-1, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePileLowDestPile() {
    this.standardGame.movePile(1, 1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePileHighSrcPile() {
    this.standardGame.movePile(10, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePileHighDestPile() {
    this.standardGame.movePile(1, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePileLowNumCards() {
    this.standardGame.movePile(1, 0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePileHighNumCards() {
    this.standardGame.movePile(1, 10, 2);
  }

  @Test
  public void testMoveDrawToCascadeValid() {
    //setup
    easyGame.moveToFoundation(0, 0);

    //important to the test
    assertEquals(3, easyGame.getDrawCards().size());
    assertEquals(0, easyGame.getPileHeight(0));
    easyGame.moveDraw(0);
    assertEquals(1, easyGame.getPileHeight(0));
    assertEquals(2, easyGame.getDrawCards().size());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveDrawTooLowPileNum() {
    this.smallGame.moveToFoundation(-3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveDrawTooHighPileNum() {
    this.smallGame.moveToFoundation(9, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveDrawEmptyDrawHand() {
    this.smallGame.moveToFoundation(0, 0);
  }

  @Test
  public void testMoveToFoundationValid() {
    assertNull(easyGame.getCardAt(0));

    easyGame.moveToFoundation(0, 0);
    assertEquals("A♢", easyGame.getCardAt(0).toString());

    assertNull(standardGame.getCardAt(3));

    standardGame.moveToFoundation(0, 3);
    assertEquals("A♢", standardGame.getCardAt(3).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveToFoundationTooLowSrcPileNum() {
    this.smallGame.moveToFoundation(-3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveToFoundationTooHighSrcPileNum() {
    this.smallGame.moveToFoundation(9, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveToFoundationTooLowFndPileNum() {
    this.smallGame.moveToFoundation(1, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveToFoundationTooHighFndPileNum() {
    this.smallGame.moveToFoundation(1, 5);
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveToFoundationEmptySrcPile() {
    this.smallGame.moveToFoundation(0, 0);
  }

  @Test
  public void testMoveDrawToFoundationValid() {
    assertNull(easyGame.getCardAt(0));
    String[] drawCardsBefore = new String[] {"A♡", "2♡", "3♡"};

    for (int index = 0; index < drawCardsBefore.length; index++) {
      assertEquals(drawCardsBefore[index], this.easyGame.getDrawCards().get(index).toString());
    }

    easyGame.moveDrawToFoundation(0);
    assertEquals("A♡", easyGame.getCardAt(0).toString());

    easyGame.moveDrawToFoundation(0);
    assertEquals("2♡", easyGame.getCardAt(0).toString());

    easyGame.moveDrawToFoundation(0);
    assertEquals("3♡", easyGame.getCardAt(0).toString());

    assertTrue(easyGame.getDrawCards().isEmpty());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveDrawToFoundationTooLowPileNum() {
    this.smallGame.moveDrawToFoundation(-2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveDrawToFoundationTooHighPileNum() {
    this.smallGame.moveDrawToFoundation(8);
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveDrawToFoundationEmptyDrawCards() {
    this.smallGame.moveDrawToFoundation(0);
  }

  @Test
  public void testDiscardDrawValid() {
    String[] drawCardsBefore = new String[] {"3♡", "4♡", "5♡"};

    for (int index = 0; index < drawCardsBefore.length; index++) {
      assertEquals(drawCardsBefore[index], this.standardGame.getDrawCards().get(index).toString());
    }

    this.standardGame.discardDraw();

    String[] drawCardsAfter = new String[] {"4♡", "5♡", "6♡"};

    for (int index = 0; index < drawCardsAfter.length; index++) {
      assertEquals(drawCardsAfter[index], this.standardGame.getDrawCards().get(index).toString());
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testDiscardEmptyDrawPile() {
    assertTrue(this.smallGame.getDrawCards().isEmpty());
    this.smallGame.discardDraw();
  }

  @Test
  public void testGetCardAtValid() {
    assertEquals("A♢", this.standardGame.getCardAt(0, 0).toString());
    assertEquals("3♡", this.smallGame.getCardAt(2, 2).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtTooLowPileNum() {
    this.smallGame.getCardAt(-9, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtTooHighPileNum() {
    this.smallGame.getCardAt(90, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtTooLowCardNum() {
    this.smallGame.getCardAt(0, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtTooHighCardNum() {
    this.smallGame.getCardAt(0, 5);
  }

  @Test
  public void testGetFoundationCardAtValid() {
    assertNull(this.standardGame.getCardAt(1));
    assertEquals("A♣", this.smallGame.getCardAt(0).toString());
    assertNull(this.smallGame.getCardAt(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtTooLowPileNum() {
    this.standardGame.getCardAt(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtTooHighPileNum() {
    this.standardGame.getCardAt(5);
  }

  @Test
  public void testAreAllCardsVisible() {
    assertTrue(this.standardGame.isCardVisible(1, 1));
    assertTrue(this.standardGame.isCardVisible(6, 6));
    assertTrue(this.standardGame.isCardVisible(3, 0));

    assertTrue(this.smallGame.isCardVisible(1, 1));
    assertTrue(this.smallGame.isCardVisible(2, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsCardVisibleTooLowPileNum() {
    this.standardGame.isCardVisible(-2, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsCardVisibleTooHighPileNum() {
    this.standardGame.isCardVisible(18, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsCardVisibleTooLowCardNum() {
    this.standardGame.isCardVisible(2, -5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsCardVisibleTooHighCardNum() {
    this.standardGame.isCardVisible(2, 20);
  }

  @Test
  public void testNumRows() {
    assertEquals(7, this.standardGame.getNumRows());
    assertEquals(3, this.smallGame.getNumRows());
  }

  @Test
  public void testNumPiles() {
    assertEquals(7, this.standardGame.getNumPiles());
    assertEquals(3, this.smallGame.getNumPiles());
  }

  @Test
  public void testNumDraw() {
    assertEquals(3, this.standardGame.getNumDraw());
    assertEquals(1, this.smallGame.getNumDraw());
  }

  @Test
  public void testIsGameOverYes() {
    KlondikeModel<BasicCard> doneGame = new WhiteheadKlondike();
    doneGame.startGame(doneGame.createNewDeck(), false, 7, 3);

    doneGame = new WhiteheadKlondike();
    List<BasicCard> testingDeck = new ArrayList<>();

    for (BasicCard card : doneGame.createNewDeck()) {
      if (Arrays.asList(new String[] {"A♡"})
          .contains(card.toString())) {
        testingDeck.add(card);
      }
    }

    doneGame.startGame(testingDeck, false, 1, 1);
    doneGame.moveToFoundation(0, 0);
    assertEquals(true, doneGame.isGameOver());
  }

  @Test
  public void testIsGameOverNo() {
    assertFalse(standardGame.isGameOver());
  }

  @Test
  public void testGetPileHeightValid() {
    assertEquals(3, this.smallGame.getPileHeight(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPileHeightTooLow() {
    this.smallGame.getPileHeight(-3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPileHeightTooHigh() {
    this.smallGame.getPileHeight(10);
  }

  @Test
  public void testGetDrawCards() {
    String[] deck1 = new String[] {"3♡", "4♡", "5♡"};

    for (int index = 0; index < deck1.length; index++) {
      assertEquals(deck1[index], this.standardGame.getDrawCards().get(index).toString());
    }

    assertTrue(this.smallGame.getDrawCards().isEmpty());
  }

  @Test
  public void testGetNumFoundations() {
    assertEquals(4, this.standardGame.getNumFoundations());
    assertEquals(2, this.smallGame.getNumFoundations());
  }


  // Game not started yet exception section
  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnMovePile() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.movePile(1, 1, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnMoveDraw() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.moveDraw(1);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnMoveToFoundation() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.moveToFoundation(1, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnMoveDrawToFoundation() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.moveDrawToFoundation(1);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnDiscardDraw() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.discardDraw();
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnGetNumRows() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.getNumRows();
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnGetNumPiles() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.getNumPiles();
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnGetNumDraw() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.getNumDraw();
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnIsGameOver() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.isGameOver();
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnGetScore() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.getScore();
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnGetPileHeight() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.getPileHeight(1);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnGetCardAtCascade() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.getCardAt(1, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnGetCardAtFoundation() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.getCardAt(1);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnIsCardVisible() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.isCardVisible(1, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnGetDrawCards() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.getDrawCards();
  }

  @Test(expected = IllegalStateException.class)
  public void testStartExceptionOnGetNumFoundations() {
    KlondikeModel<BasicCard> badGame = new WhiteheadKlondike();
    badGame.getNumFoundations();
  }

}
