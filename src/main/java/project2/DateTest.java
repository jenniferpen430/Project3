import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DateTest {

    @Test
    public void testIsValid(){
        Date date0 = new Date("05/12/979");
        assertFalse(date0.isValid());

        Date date1 = new Date("2/29/2019");
        assertFalse(date1.isValid());

        Date date2 = new Date("2/29/2020");
        assertFalse(date2.isValid());

        Date date3 = new Date("03/1/2025");
        assertFalse(date3.isValid());

        Date date4 = new Date("02/34/2021");
        assertTrue(date4.isValid());

        Date date5 = new Date("13/22/2021");
        assertFalse(date5.isValid());

        Date date6 = new Date("0/34/2021");
        assertFalse(date6.isValid());

        Date date7 = new Date("09/31/2020");
        assertFalse(date7.isValid());

        Date date8 = new Date("09/0/2020");
        assertFalse(date8.isValid());

        Date date9 = new Date("11/1/2021");
        assertTrue(date9.isValid());

        Date date10 = new Date("09/28/2021");
        assertTrue(date10.isValid());
    }


}