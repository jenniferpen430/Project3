import static org.junit.Assert.*;
import java.text.DecimalFormat;
import org.junit.Test;

public class InternationalTest{

    @Test
    public void testTuitionDue(){
        DecimalFormat decimal = new DecimalFormat("#,###.##");

        int credits = 12;
        boolean status = true;
        Profile profile = new Profile("Namjoon Kim", "IT");
        International international = new International(profile, credits, status);
        international.tuitionDue();
        assertEquals(decimal.format(international.getTuition()),  decimal.format(5918.00)); // study abroad

        credits = 16;
        Profile profile1 = new Profile("jeon jungkook", "EE");
        status = false;
        International international1 = new International(profile, credits, status);
        international1.tuitionDue();
        assertEquals(decimal.format(international1.getTuition()), decimal.format(35655.00) ); // at 16

        int credits2 = 13;
        Profile profile2 = new Profile("felix lee", "BA");
        status = false;
        International international2 = new International(profile2, credits2, status);
        international2.tuitionDue();
        assertEquals(decimal.format(international2.getTuition()), decimal.format(35655.00) ); // below 16

        int credits3 = 18;
        Profile profile3 = new Profile("felix lee", "BA");
        status = false;
        International international3 = new International(profile3, credits3, status);
        international3.tuitionDue();
        assertEquals(decimal.format(international3.getTuition()), decimal.format(37587.00) ); //over 16

    }

}