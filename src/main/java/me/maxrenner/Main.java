package me.maxrenner;

import me.maxrenner.engine.Engine;
import me.maxrenner.engine.Object;

public class Main {
    public static void main(String[] args) {
        Engine engine = Engine.get();
        engine.genWindow(600,600,"Hello");
        engine.getObjectHandler().getObjects().add(new Object(engine.getLoader().getMesh(Main. class.getClassLoader().getResource("Cube.obj").getPath(), engine.getProgram())));
//        engine.getObjectHandler().getMeshes().add(engine.getLoader().getMesh(Objects.requireNonNull(Main.class.getClassLoader().getResource("Bunny.obj")).getPath(), new Matrix4f().identity().translate(new Vector3f(0.0f,0.0f,9.0f)), engine.getProgram()));
        engine.run();
    }
}
