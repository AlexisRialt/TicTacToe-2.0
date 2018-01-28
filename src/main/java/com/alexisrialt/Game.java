package com.alexisrialt;

import com.alexisrialt.implementations.*;
import com.alexisrialt.services.PropertiesService;
import com.alexisrialt.services.TicTacToeCombinationService;

import java.util.*;

public class Game {
    public static void main( String[] args ) {
        Display display = new StdOutDisplay();

        Properties properties = new PropertiesService(display, args).get();
        Config config = new Config(properties);
        try{
            config.extract();
        } catch (Exception e){
            display.output(e.getMessage());
            System.exit(1);
        }

        Input input = new StdInInput();
        Board board = new TicTacToeBoard(display, config.getGridSize());
        CombinationFactory combinationFactory = new TicTacToeCombinationFactory();

        while(true){
            CombinationService combinationService = new TicTacToeCombinationService(combinationFactory, config.getGridSize());
            List<Player> players = getPlayers(combinationService, config, input);
             new TicTacToe(display, input, config, board, combinationService, players)
                .init()
                .start()
                .run();
        }
    }

    private static List<Player> getPlayers(CombinationService combinationService, Config config, Input input) {
        HumanPlayer humanPlayer1 = new HumanPlayer(
                Players.PLAYER_1.name(),
                config.getPlayChar(Players.PLAYER_1),
                input);

        HumanPlayer humanPlayer2 = new HumanPlayer(
                Players.PLAYER_2.name(),
                config.getPlayChar(Players.PLAYER_2),
                input);

        NPCPlayer npcPlayer = new NPCPlayer(
                combinationService,
                Players.PLAYER_3.name(),
                config.getPlayChar(Players.PLAYER_3),
                config.getGridSize());

        List<Player> players = new ArrayList<>();
        players.add(humanPlayer1);
        players.add(humanPlayer2);
        players.add(npcPlayer);
        return players;
    }
}