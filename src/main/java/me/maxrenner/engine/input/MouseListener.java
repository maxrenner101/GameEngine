package me.maxrenner.engine.input;

import lombok.Getter;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class MouseListener {
    private boolean isDragging;
    @Getter private double xPos, yPos, xOffset, yOffset, displayX, displayY;
    private double xLast = 0, yLast = 0;
    private final int[] buttons = new int[3];

    public void cursor_position_callback(long window, double xpos, double ypos) {
        this.xPos = xpos;
        this.yPos = ypos;
    }

    public void handleInput() {
        this.xOffset = this.xPos - this.xLast;
        this.yOffset = this.yPos - this.yLast;
        if(xPos > 0 && yPos > 0){
            boolean rotateX = this.xOffset != 0;
            boolean rotateY = this.yOffset != 0;
            if(rotateX)
                displayX = this.xOffset;
            if(rotateY)
                displayY = this.yOffset;
        }
        this.xLast = this.xPos;
        this.yLast = this.yPos;
    }

    public void mouse_button_callback(long window, int button, int action, int mods) {
        buttons[button] = action;
    }

    public boolean leftClicked(){
        return buttons[0] == GLFW_PRESS;
    }

    public boolean rightClicked() {
        return buttons[1] == GLFW_PRESS;
    }
}
