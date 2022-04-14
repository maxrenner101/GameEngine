package me.maxrenner.engine;

import lombok.Getter;
import me.maxrenner.game.Game;
import me.maxrenner.engine.input.KeyboardListener;
import me.maxrenner.engine.input.MouseListener;
import me.maxrenner.engine.objects.Camera;
import me.maxrenner.engine.objects.MeshLoader;
import me.maxrenner.engine.objects.ObjectHandler;
import me.maxrenner.engine.shaders.ShaderProgram;
import me.maxrenner.engine.shaders.ShaderSource;

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
    private Game game;

    private Engine() {
        loader = new MeshLoader();
        objectHandler = new ObjectHandler(this);
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

    public void setGame(Game game){
        this.game = game;
    }

    public void run() {
        glfwShowWindow(window.getWindow());
        glEnable(GL_DEPTH_TEST);

        program.bind();
        game.run();
        glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
        int mvpLoc = program.setUniformLoc("m_MVP");
        program.unbind();

        Camera camera = game.getCamera();
        glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        double lastUpdate = glfwGetTime(), deltaUpdate = 0.0;
        while(!glfwWindowShouldClose(window.getWindow())){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            program.bind();

            double currentTime = glfwGetTime();
            deltaUpdate = currentTime - lastUpdate;
            game.update(deltaUpdate);
            lastUpdate = glfwGetTime();

            objectHandler.getObjects().forEach(obj -> {
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

        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public void setKeyboardListener(KeyboardListener kl){
        glfwSetKeyCallback(window.getWindow(), kl::key_callback);
    }

    public void setMouseListener(MouseListener ml){
        glfwSetMouseButtonCallback(window.getWindow(), ml::mouse_button_callback);
        glfwSetCursorPosCallback(window.getWindow(), ml::cursor_position_callback);
    }
}
