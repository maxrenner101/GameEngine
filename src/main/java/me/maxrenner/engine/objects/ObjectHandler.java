package me.maxrenner.engine.objects;

import lombok.Getter;
import me.maxrenner.engine.Engine;
import me.maxrenner.engine.objects.Object;

import java.util.ArrayList;

public class ObjectHandler {
    @Getter
    private final ArrayList<Object> objects = new ArrayList<>();
    private Engine engine;

    public ObjectHandler(Engine engine){
        this.engine = engine;
    }

    public Object createObject(String meshPath){
        Object obj = new Object(engine.getLoader().getMesh(meshPath, engine.getProgram()));
        objects.add(obj);

        return obj;
    }
}
