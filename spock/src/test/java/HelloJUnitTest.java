import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloJUnitTest {

    @Test
    public void twoPlusThreeIsFive() throws Exception {
        assertEquals(5, 2 + 3);
    }
}
