package me.maxrenner.engine;

import lombok.Getter;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;

public class Engine {

    private static Engine instance;
    @Getter private Window window;
    @Getter private final ObjectHandler objectHandler;
    @Getter private final MeshLoader loader;
    @Getter private ShaderProgram program;

    private Engine() {
        loader = new MeshLoader();
        objectHandler = new ObjectHandler();
    }

    public static Engine get() {
        if(instance == null)
            instance = new Engine();

        return instance;
    }

    public void genWindow(int width, int height, String title){
        window = new Window(width, height, title);

        try {
            program = new ShaderProgram();
            ShaderSource source = new ShaderSource("");
            program.createVertexShader(source.getVertex());
            program.createFragmentShader(source.getFragment());
            program.link();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void run() {
        glfwShowWindow(window.getWindow());

        glEnable(GL_DEPTH_TEST);

        program.bind();
        int mvpLoc = program.setUniformLoc("m_MVP");
        program.unbind();

        Camera camera = new Camera();

        float deg = 0;
        float x = 0;
        while(!glfwWindowShouldClose(window.getWindow())){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            program.bind();

            deg += 0.01f;
            x+=0.005f;
            float finalDeg1 = deg;
            float finalDeg2 = deg;
            float finalDeg3 = deg;
            float t = x;
            objectHandler.getObjects().forEach(obj -> {

                obj.setX(t);
                obj.setRotation(finalDeg1, finalDeg2, finalDeg3);

                program.setUniformMat4(mvpLoc, obj.getMVP(camera));

                obj.bind();

                glDrawElements(GL_TRIANGLES, obj.getMesh().getIndLength(), GL_UNSIGNED_INT, 0);

                obj.unbind();
            });
            program.unbind();

            glfwPollEvents();
            glfwSwapBuffers(window.getWindow());
        }

        glfwFreeCallbacks(window.getWindow());
        glfwDestroyWindow(window.getWindow());

        // Terminate GLFW and free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }
}
