package klondike;

import static org.junit.Assert.assertTrue;

import klondike.model.hw02.BasicKlondike;
import klondike.model.hw04.KlondikeCreator;
import klondike.model.hw04.WhiteheadKlondike;
import org.junit.Test;

/**
 * Tests for KlondikeCreator.
 */
public class KlondikeCreatorTests {

  @Test
  public void testCreatesWhitehead() {
    assertTrue(KlondikeCreator.create(KlondikeCreator.GameType.WHITEHEAD)
        instanceof WhiteheadKlondike);
  }

  @Test
  public void testCreatesBasic() {
    assertTrue(KlondikeCreator.create(KlondikeCreator.GameType.BASIC)
        instanceof BasicKlondike);
  }
}
