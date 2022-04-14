package me.maxrenner;

import me.maxrenner.engine.Engine;
import me.maxrenner.game.Game;

public class Main {
    public static void main(String[] args) {
        Engine engine = Engine.get();
        engine.genWindow(1920,1080,"Hello");
        Game game = new Game(engine);
        engine.setGame(game);
        engine.run();
    }
}
