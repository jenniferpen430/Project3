import static org.junit.Assert.*;
import org.junit.Test;


public class RosterTest {


    @Test
    public void testAdd(){
        Roster roster = new Roster();

        int credits = 20;
        Profile profile = new Profile("Adams Lopez", "CS");
        Resident resident = new Resident(profile, credits);
        assertTrue(roster.add(resident));
        assertFalse(roster.add(resident));

        int creditsInternational = 19;
        boolean abroad = false;
        Profile profile1 = new Profile("Jenn Penn", "IT");
        International international = new International(profile1, creditsInternational, abroad);
        assertTrue(roster.add(international));

        int creditsTristate = 18;
        String state = "NY";
        Profile profile2 = new Profile("Kim Lin", "ee");
        TriState tristate = new TriState(profile2, creditsTristate, state);
        assertTrue(roster.add(tristate));

        int creditsNR = 17;
        Profile profile3 = new Profile("LIL X", "ME");
        NonResident nonResident = new NonResident(profile3, creditsNR);
        assertTrue(roster.add(nonResident));

        Profile profile4 = new Profile("Kim Lin", "ee");
        TriState tristate1 = new TriState(profile4, creditsTristate, state);
        assertFalse(roster.add(tristate1));

        Profile profile5 = new Profile("LIL X", "ME");
        NonResident nonResident1 = new NonResident(profile5, creditsNR);
        assertFalse(roster.add(nonResident1));


    }

    @Test
    public void testRemove(){
        Roster roster = new Roster();

        int creditsNR = 17;
        Profile profile3 = new Profile("LIL X", "ME");
        NonResident nonResident = new NonResident(profile3, creditsNR);
        roster.add(nonResident);
        assertTrue(roster.remove(nonResident));

        int creditsTristate = 18;
        String state = "NY";
        Profile profile2 = new Profile("Kim Lin", "ee");
        TriState tristate = new TriState(profile2, creditsTristate, state);
        roster.add(tristate);
        assertTrue(roster.remove(tristate));
        assertFalse(roster.remove(tristate));

        int creditsInternational = 19;
        boolean abroad = false;
        Profile profile1 = new Profile("Jenn Penn", "IT");
        International international = new International(profile1, creditsInternational, abroad);
        assertTrue(roster.add(international));

        int creditsR = 17;
        Profile profile4 = new Profile("star boy", "ME");
        Resident resident = new Resident(profile4, creditsR);
        roster.add(resident);
        assertTrue(roster.remove(resident));

    }
}