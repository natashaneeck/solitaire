package klondike;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import klondike.controller.KlondikeController;
import klondike.controller.KlondikeTextualController;
import klondike.mocks.FailingAppendable;
import klondike.mocks.InputLoggerMock;
import klondike.model.hw02.BasicCard;
import klondike.model.hw02.BasicKlondike;
import klondike.model.hw02.KlondikeModel;
import org.junit.Test;

/**
 * Contains all tests for KlondikeController.
 */
public class KlondikeControllerTests {


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructionApNull() {
    KlondikeController control = new KlondikeTextualController(new StringReader(""), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructionRdNull() {
    KlondikeController control = new KlondikeTextualController(null, new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameNullModel() {
    KlondikeController control =
        new KlondikeTextualController(new StringReader(""), new StringBuilder());
    control.playGame(null, new BasicKlondike().createNewDeck(), false, 7, 3);
  }

  @Test(expected = IllegalStateException.class)
  public void testCannotStartGame() {
    KlondikeController control =
        new KlondikeTextualController(new StringReader(""), new StringBuilder());
    KlondikeModel<BasicCard> model = new BasicKlondike();
    control.playGame(model, null, false, 7, 3);
  }

  @Test(expected = IllegalStateException.class)
  public void testOutOfInputThrowsStateException() {
    KlondikeController control =
        new KlondikeTextualController(new StringReader(""), new StringBuilder());
    KlondikeModel<BasicCard> model = new BasicKlondike();
    control.playGame(model, new BasicKlondike().createNewDeck(), false, 7, 3);
  }

  @Test(expected = IllegalStateException.class)
  public void testBadAppendThrowsStateException() {
    KlondikeController control =
        new KlondikeTextualController(new StringReader(""), new FailingAppendable());
    KlondikeModel<BasicCard> model = new BasicKlondike();
    control.playGame(model, new BasicKlondike().createNewDeck(), false, 7, 3);
  }

  @Test
  public void testInputToModelAdjustsCorrectly() {
    StringBuilder ap = new StringBuilder();
    StringReader in = new StringReader("mpp 1 3 2 md 4 mpf 1 1 mdf 3 q");
    KlondikeController control = new KlondikeTextualController(in, ap);
    KlondikeModel<BasicCard> model = new InputLoggerMock(ap);
    control.playGame(model, new BasicKlondike().createNewDeck(), false, 7, 3);

    String output = String.join("",
        String.join("", ap.toString().split("Draw: \nFoundation\n\nScore: 0\n"))
            .split("Game quit!\nState of game when quit:\n"));

    String expectedSegments = "srcPile = 0 numCards = 3 destPile  = 1\n" + "destPile  = 3\n"
        + "srcPile = 0 foundationPile = 0\n" + "foundationPile = 2\n";
    assertEquals(expectedSegments, output);
  }

  @Test
  public void testQuitAnywhere() {
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("mpp 1 3 Q 3");
    KlondikeController control = new KlondikeTextualController(in, out);

    KlondikeModel<BasicCard> model = new BasicKlondike();
    control.playGame(model, new BasicKlondike().createNewDeck(), false, 7, 3);

    String output = out.toString();

    String expectedSegments =
        "Draw: 3♡, 4♡, 5♡\n"
            + "Foundation: <none>, <none>, <none>, <none>\n"
            + " A♢  ?  ?  ?  ?  ?  ?\n"
            + "    8♢  ?  ?  ?  ?  ?\n"
            + "       A♣  ?  ?  ?  ?\n"
            + "          6♣  ?  ?  ?\n"
            + "            10♣  ?  ?\n"
            + "                K♣  ?\n"
            + "                   2♡\n"
            + "Score: 0\n" + "Game quit!\n"
            + "State of game when quit:\n" + "Draw: 3♡, 4♡, 5♡\n"
            + "Foundation: <none>, <none>, "
            + "<none>, <none>\n" + " A♢  ?  ?  ?  ?  ?  ?\n"
            + "    8♢  ?  ?  ?  ?  ?\n" + "     "
            + "  A♣  ?  ?  ?  ?\n"
            + "          6♣  ?  ?  ?\n"
            + "            10♣  ?  ?\n"
            + "                K♣  ?\n"
            + "                   2♡\n" + "Score: 0\n";
    assertEquals(expectedSegments, output);
  }

  @Test
  public void testMpfNormal() {
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("mpf 1 2 q");
    KlondikeController control = new KlondikeTextualController(in, out);

    KlondikeModel<BasicCard> model = new BasicKlondike();
    control.playGame(model, new BasicKlondike().createNewDeck(), false, 7, 3);

    String output = out.toString();

    String expectedSegments = "Draw: 3♡, 4♡, 5♡\n"
        + "Foundation: <none>, <none>, <none>, <none>\n"
        + " A♢  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 0\n" + "Draw: 3♡, 4♡, 5♡\n"
        + "Foundation: <none>, A♢, <none>, <none>\n"
        + "  X  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 1\n" + "Game quit!\n" + "State of game when quit:\n"
        + "Draw: 3♡, 4♡, 5♡\n" + "Foundation: <none>, A♢, <none>, <none>\n"
        + "  X  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 1\n";
    assertEquals(expectedSegments, output);
  }

  @Test
  public void testMpfWithGarbage() {
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("mpf 1 a b c 2 q");
    KlondikeController control = new KlondikeTextualController(in, out);

    KlondikeModel<BasicCard> model = new BasicKlondike();
    control.playGame(model, new BasicKlondike().createNewDeck(), false, 7, 3);

    String output = out.toString();

    String expectedSegments = "Draw: 3♡, 4♡, 5♡\n"
        + "Foundation: <none>, <none>, <none>, <none>\n"
        + " A♢  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 0\n" + "Draw: 3♡, 4♡, 5♡\n"
        + "Foundation: <none>, A♢, <none>, <none>\n"
        + "  X  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 1\n" + "Game quit!\n" + "State of game when quit:\n"
        + "Draw: 3♡, 4♡, 5♡\n" + "Foundation: <none>, A♢, <none>, <none>\n"
        + "  X  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 1\n";
    assertEquals(expectedSegments, output);
  }

  @Test
  public void testMpfPreceededWithGarbageCmd() {
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("aaaa mpf 1 2 q");
    KlondikeController control = new KlondikeTextualController(in, out);

    KlondikeModel<BasicCard> model = new BasicKlondike();
    control.playGame(model, new BasicKlondike().createNewDeck(), false, 7, 3);

    String output = out.toString();

    String expectedSegments = "Draw: 3♡, 4♡, 5♡\n"
        + "Foundation: <none>, <none>, <none>, <none>\n"
        + " A♢  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 0\n" + "Invalid move. Play again. Need valid command prompt\n"
        + "Draw: 3♡, 4♡, 5♡\n"
        + "Foundation: <none>, A♢, <none>, <none>\n"
        + "  X  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 1\n" + "Game quit!\n" + "State of game when quit:\n"
        + "Draw: 3♡, 4♡, 5♡\n" + "Foundation: <none>, A♢, <none>, <none>\n"
        + "  X  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 1\n";
    assertEquals(expectedSegments, output);
  }

  @Test
  public void testMultipleValidCommands() {
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("dd mpf 1 4 mpp 6 1 1 q");
    KlondikeController control = new KlondikeTextualController(in, out);

    KlondikeModel<BasicCard> model = new BasicKlondike();
    control.playGame(model, new BasicKlondike().createNewDeck(), false, 7, 3);

    String output = out.toString();

    String expectedSegments = "Draw: 3♡, 4♡, 5♡\n"
        + "Foundation: <none>, <none>, <none>, <none>\n"
        + " A♢  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 0\n"
        + "Draw: 4♡, 5♡, 6♡\n"
        + "Foundation: <none>, <none>, <none>, <none>\n"
        + " A♢  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 0\n"
        + "Draw: 4♡, 5♡, 6♡\n"
        + "Foundation: <none>, <none>, <none>, A♢\n"
        + "  X  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 1\n"
        + "Draw: 4♡, 5♡, 6♡\n"
        + "Foundation: <none>, <none>, <none>, A♢\n"
        + " K♣  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣ J♣  ?\n"
        + "                    ?\n"
        + "                   2♡\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "Draw: 4♡, 5♡, 6♡\n"
        + "Foundation: <none>, <none>, <none>, A♢\n"
        + " K♣  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣ J♣  ?\n"
        + "                    ?\n"
        + "                   2♡\n"
        + "Score: 1\n";
    assertEquals(expectedSegments, output);
  }

  @Test
  public void testInvalidModelInputs() {
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("mpf 0 9 mpf 1 2 Q");
    KlondikeController control = new KlondikeTextualController(in, out);

    KlondikeModel<BasicCard> model = new BasicKlondike();
    control.playGame(model, new BasicKlondike().createNewDeck(), false, 7, 3);

    String output = out.toString();

    String expectedSegments = "Draw: 3♡, 4♡, 5♡\n"
        + "Foundation: <none>, <none>, <none>, <none>\n"
        + " A♢  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 0\n"
        + "Invalid move. Play again. Invalid pile index\n"
        + "Draw: 3♡, 4♡, 5♡\n"
        + "Foundation: <none>, A♢, <none>, <none>\n"
        + "  X  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "Draw: 3♡, 4♡, 5♡\n"
        + "Foundation: <none>, A♢, <none>, <none>\n"
        + "  X  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡\n"
        + "Score: 1\n";
    assertEquals(expectedSegments, output);
  }
}
