package it.polimi.ingsw;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BagTest.class,
        CellTest.class,
        DieTest.class,
        PatternTest.class,
        PrivateObjectiveCardTest.class,
        PublicObjectiveCardTest.class,
        RoundTest.class,
        RoundTrackTest.class
})


public class JUnitSuite {

}
