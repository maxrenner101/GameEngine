package me.maxrenner.engine.objects;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private final Matrix4f projection;
    private final Matrix4f view;
    @Getter @Setter private float x,y,z, yaw,pitch, mouseSensX = 0.1f, mouseSensY = 0.1f;
    private final float FOV = 90.0f, ASPECT = 1920.0f/1080, ZNEAR = 0.01f, ZFAR = 1600.0f;

    public Matrix4f getProjectionMatrix() {
        projection.identity();
        projection.perspective((float)Math.toRadians(FOV), ASPECT, ZNEAR, ZFAR);

        return projection;
    }

    public Matrix4f getWorldMatrix() {
        view.identity();
        view.rotate((float)Math.toRadians(yaw), new Vector3f(1,0,0)).rotate((float) Math.toRadians(pitch), new Vector3f(0,1,0));
        view.translate(new Vector3f(-x,-y,-z));
        return view;
    }

    public Camera(){
        projection = new Matrix4f();
        view = new Matrix4f();

        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    public void setPos(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setRotation(float yaw, float pitch){
        this.yaw = yaw;
        this.pitch = pitch;
    }
}
