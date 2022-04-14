package me.maxrenner.game;

import lombok.Getter;
import me.maxrenner.Main;
import me.maxrenner.engine.input.KeyboardListener;
import me.maxrenner.engine.input.MouseListener;
import me.maxrenner.engine.objects.Camera;
import me.maxrenner.engine.Engine;
import me.maxrenner.engine.objects.Object;

import java.util.ArrayList;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

public class Game {

    private Engine engine;
    private Object player;
    private Object bunny;
    private Object enemy;
    private Object obj;
    private Object anotherOne;
    private ArrayList<Object> floor = new ArrayList<>();
    @Getter
    private Camera camera;
    private KeyboardListener keyboard;
    private MouseListener mouse;

    public Game(Engine engine) {
        this.engine = engine;
    }

    public void run() {
        player = engine.getObjectHandler().createObject(Objects.requireNonNull(Main.class.getClassLoader().getResource("Cube.obj").getPath()));
        enemy = engine.getObjectHandler().createObject(Objects.requireNonNull(Main.class.getClassLoader().getResource("Cube.obj").getPath()));
        obj = engine.getObjectHandler().createObject(Objects.requireNonNull(Main.class.getClassLoader().getResource("Cubet.obj").getPath()));
        bunny = engine.getObjectHandler().createObject(Objects.requireNonNull(Main.class.getClassLoader().getResource("Bunny.obj")).getPath());
        anotherOne = engine.getObjectHandler().createObject(Objects.requireNonNull(Main.class.getClassLoader().getResource("Obj.obj")).getPath());
        anotherOne.setPos(5.0f,0.0f,0.0f);
        bunny.setSizeX(100.0f);
        bunny.setSizeY(100.0f);
        bunny.setSizeZ(100.0f);
        Object floor = engine.getObjectHandler().createObject(Objects.requireNonNull(Main.class.getClassLoader().getResource("Cube.obj").getPath()));
        floor.setSizeX(40);
        floor.setSizeZ(40);
        floor.setSizeY(0.5f);
        floor.setY(-5.0f);

        keyboard = new KeyboardListener();
        mouse = new MouseListener();
        engine.setKeyboardListener(keyboard);
        engine.setMouseListener(mouse);

        camera = new Camera();
        camera.setZ(10.0f);
    }
    public void update(double dt) {
        mouse.handleInput();
        if(keyboard.keyPress(GLFW_KEY_W))
            camera.setZ(camera.getZ() - (float)(20 * dt));
        if(keyboard.keyPress(GLFW_KEY_A))
            camera.setX(camera.getX() - (float)(20 * dt));
        if(keyboard.keyPress(GLFW_KEY_D))
            camera.setX(camera.getX() + (float)(20 * dt));
        if(keyboard.keyPress(GLFW_KEY_S))
            camera.setZ(camera.getZ() + (float)(20 * dt));
        camera.setRotation((float) (camera.getYaw() + mouse.getYOffset() * camera.getMouseSensY()), (float) (camera.getPitch() + mouse.getXOffset() * camera.getMouseSensY()));

        enemy.setX(enemy.getX() + 0.02f);
    }
}
