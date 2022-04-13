package me.maxrenner.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private final Matrix4f projection;
    private final Matrix4f view;
    private final float FOV = 60.0f, ASPECT = 1.0f, ZNEAR = 0.01f, ZFAR = 1000.0f;

    public Matrix4f getProjectionMatrix() {
        projection.identity();
        projection.perspective((float)Math.toRadians(FOV), ASPECT, ZNEAR, ZFAR);

        return projection;
    }

    public Matrix4f getWorldMatrix() {
        view.identity();
        view.translate(new Vector3f(-2.0f,0.0f,-8.0f));
        return view;
    }

    public Camera(){
        projection = new Matrix4f();
        view = new Matrix4f();
    }
}
