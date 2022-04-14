package me.maxrenner.engine.objects;

import lombok.Getter;
import lombok.Setter;
import me.maxrenner.engine.shaders.ShaderProgram;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;

public class Mesh {

    @Getter private final int vao;
    @Getter private final int ibo;
    @Getter private final int indLength;
    @Getter private final float centerModelX, centerModelY, centerModelZ;
    @Getter @Setter private Matrix4f model;

    public Mesh(float[] vertices, float[] normals, int[] faceIndices,  ShaderProgram program){

        centerModelX = averageVerticiesX(vertices);
        centerModelY = averageVerticiesY(vertices);
        centerModelZ = averageVerticiesZ(vertices);

        this.model = new Matrix4f().identity();

        program.bind();

        indLength = faceIndices.length;

        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(vertices.length);
        vertexBuffer.put(vertices).flip();

        FloatBuffer normalBuffer = MemoryUtil.memAllocFloat(normals.length);
        normalBuffer.put(normals).flip();

        IntBuffer faceBuffer = MemoryUtil.memAllocInt(faceIndices.length);

        faceBuffer.put(faceIndices).flip();

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0,0);

        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, faceBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        program.unbind();
    }
    private static float averageVerticiesX(float[] arr){
        float sum = 0;
        for(int i = 0; i < arr.length/3; i++){
            sum+=arr[i*3];
        }

        return sum/arr.length;
    }
    private static float averageVerticiesY(float[] arr){
        float sum = 0;
        for(int i = 1; i < arr.length/3-1; i++){
            sum+=arr[i*3];
        }
        return sum/arr.length;
    }
    private static float averageVerticiesZ(float[] arr){
        float sum = 0;
        for(int i = 2; i < arr.length/3-2; i++){
            sum+=arr[i*3];
        }
        return sum/arr.length;
    }
}
