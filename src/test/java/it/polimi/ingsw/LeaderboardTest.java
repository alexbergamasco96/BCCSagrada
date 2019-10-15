package it.polimi.ingsw;

import it.polimi.ingsw.server.model.Leaderboard;
import it.polimi.ingsw.server.model.Placement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaderboardTest {

    final static int SP = 3;
    final static int FP = 1;
    final static int AP = 10;
    final static int MP = 13;
    final static int FFP = 8;
    final static int LP = 2;

    @Test
    public void LeaderboardTest(){


        Leaderboard leaderboard = new Leaderboard();
        Placement a = new Placement("Simo", SP);
        Placement b = new Placement("Fede", FP);
        Placement c = new Placement("Alex", AP);
        Placement d = new Placement("Marco", MP);
        Placement e = new Placement("Frank", FFP);
        Placement f = new Placement("Luca", LP);


        leaderboard.getPlacements().add(a);
        leaderboard.getPlacements().add(b);
        leaderboard.getPlacements().add(c);
        leaderboard.getPlacements().add(d);
        leaderboard.getPlacements().add(e);
        leaderboard.getPlacements().add(f);

        leaderboard.sort();


        assertEquals("Marco", leaderboard.getPlacements().get(0).getName());
        assertEquals("Alex", leaderboard.getPlacements().get(1).getName());
        assertEquals("Frank", leaderboard.getPlacements().get(2).getName());
        assertEquals("Simo", leaderboard.getPlacements().get(3).getName());
        assertEquals("Luca", leaderboard.getPlacements().get(4).getName());
        assertEquals("Fede", leaderboard.getPlacements().get(5).getName());

    }
}
