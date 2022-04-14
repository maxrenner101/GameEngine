package me.maxrenner.engine.shaders;

import lombok.Getter;

public class ShaderSource {
    @Getter private String vertex;
    @Getter private String fragment;

    public ShaderSource(String file) {
        vertex = "#version 330\n" +
                "\n" +
                "layout(location = 0) in vec3 pos;\n" +
                "\n" +
                "uniform mat4 m_MVP;\n" +
                "\n" +
                "void main() {\n" +
                "    gl_Position = m_MVP * vec4(pos,1.0f);\n" +
                "}";

        fragment = "#version 330\n" +
                "\n" +
                "out vec4 color;\n" +
                "\n" +
                "void main() {\n" +
                "    color = vec4(0.0f,1.0f,1.0f,1.0f);\n" +
                "}";
    }
}
