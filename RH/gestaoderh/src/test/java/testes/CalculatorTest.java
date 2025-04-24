package testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
    @Test
    public void testMedia() {
        int result = (3 + 3 + 6) / 3;
        assertEquals(4, result, "(3 + 3 + 6) / 3 should equal 4");
    }

    @Test
    void testMultiplicacaoComNumerosNegativos() {
        int result = (-2 * -3);
        assertEquals(6, result, "(-2 * -3) should equal 6");
    }
}