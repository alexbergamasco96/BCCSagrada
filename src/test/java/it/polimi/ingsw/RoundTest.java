package it.polimi.ingsw;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Round;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundTest {
    
    final static String P1 = "Alex";
    final static String P2 = "Simo";
    final static String P3 = "Fede";

    @Test
    void RoundTest(){
        Player p1 = new Player(P3, null);
        p1.setNickname(P3);
        Player p2 = new Player(P2, null);
        p2.setNickname(P2);
        Player p3 = new Player(P1, null);
        p3.setNickname(P1);

        ArrayList<Player> players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Round round = new Round(1,players);

        assertEquals(P3,round.getActivePlayer().getNickname());
        assertEquals(true,round.nextTurn());
        assertEquals(P2,round.getActivePlayer().getNickname());
        assertEquals(true,round.nextTurn());
        assertEquals(P1,round.getActivePlayer().getNickname());
        assertEquals(true,round.nextTurn());
        assertEquals(P1,round.getActivePlayer().getNickname());
        assertEquals(true,round.nextTurn());
        assertEquals(P2,round.getActivePlayer().getNickname());
        assertEquals(true,round.nextTurn());
        assertEquals(P3,round.getActivePlayer().getNickname());
        assertEquals(false,round.nextTurn());
        assertEquals(false,round.nextTurn());
        assertEquals(players.size()*2,round.getTurn());


    }

}
