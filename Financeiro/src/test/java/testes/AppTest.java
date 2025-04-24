package testes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AppTest {
    @Test
    public void testString() {
        String original = "Banana";
        String expected = "Banana";
        assertEquals(expected, original, "As strings deveriam ser iguais");
    }
    @Test
    public void testStringErrado() {
        String original = "Banana";
        String expected = "Maça";
        assertEquals(expected, original, "As strings não são iguais");
}
  
}
