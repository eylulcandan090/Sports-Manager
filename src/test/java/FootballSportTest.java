import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FootballSportTest {

    @Test
    public void teamDrawPointIsOne() {
        Football football=new Football();
        assertEquals(1,football.teamDrawPoint(),"teamDrawPoint should return 1 for Football");
    }

    @Test
    public void teamDrawPointIsNonNegativeAndConsistent() {
        Football f1=new Football();
        Football f2=new Football();
        int p1=f1.teamDrawPoint();
        int p2=f2.teamDrawPoint();

        assertTrue(p1 >= 0,"teamDrawPoint should be non-negative");
        assertEquals(p1,p2,"teamDrawPoint should be consistent across Football instances");
    }
}
