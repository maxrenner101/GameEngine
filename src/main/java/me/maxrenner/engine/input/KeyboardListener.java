package me.maxrenner.engine.input;

import lombok.Getter;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardListener {
    @Getter private final int[] keys = new int[600];

    public boolean keyPress(int keyCode){
        return keys[keyCode] == GLFW_PRESS || keys[keyCode] == 2;
    }

    public void key_callback(long window, int key, int scancode, int action, int mods){
        keys[key] = action;
    }
}
