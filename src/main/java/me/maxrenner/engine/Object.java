package me.maxrenner.engine;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL15C.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15C.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;

public class Object {

    @Getter private final Mesh mesh;
    @Getter @Setter private float x,y,z;
    @Getter @Setter private float xRot, yRot, zRot;

    public void setPos(float x, float y, float z){
        this.x = x-mesh.getCenterModelX();
        this.y = y-mesh.getCenterModelY();
        this.z = z-mesh.getCenterModelZ();
    }

    public void setRotation(float xRot, float yRot, float zRot){
        this.xRot = xRot;
        this.zRot = zRot;
        this.yRot = yRot;
    }

    public Object(Mesh mesh){
        this.mesh = mesh;
        this.x = -mesh.getCenterModelX()/2;
        this.y = -mesh.getCenterModelY()/2;
        this.z = -mesh.getCenterModelZ()/2;
        this.xRot = 0;
        this.yRot = 0;
        this.zRot = 0;
    }

    public void bind() {
        glBindVertexArray(mesh.getVao());
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIbo());
        glEnableVertexAttribArray(0);
    }

    public void unbind() {
        glDisableVertexAttribArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public Matrix4f getMVP(Camera camera){
        Matrix4f model = new Matrix4f(mesh.getModel());
        model.translate(new Vector3f(x,y,z));
        model.rotateAffineXYZ(xRot, yRot, zRot);
        return camera.getProjectionMatrix().mul(camera.getWorldMatrix().mul(model));
    }
}
