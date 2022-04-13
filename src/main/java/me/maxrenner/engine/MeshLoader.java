package me.maxrenner.engine;

import org.joml.Matrix4f;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MeshLoader {
    public Mesh getMesh(String file, ShaderProgram program){

        File f;
        Scanner sc;
        try {
            f = new File(file);
            sc = new Scanner(f);
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }

        ArrayList<Float> vertices = new ArrayList<>();
        ArrayList<Float> normals = new ArrayList<>();
        ArrayList<Integer> faceIndices = new ArrayList<>();
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            if(line.isEmpty())
                continue;
            if(line.charAt(0) == 'v') {
                if (line.charAt(1) == 'n') {
                    String[] normLine = line.split(" ");

                    for(String norm : normLine){
                        float normCord;
                        try {
                            normCord = Float.parseFloat(norm);
                        } catch (NumberFormatException e){
                            continue;
                        }

                        normals.add(normCord);
                    }
                } else {
                    String[] vertLine = line.split(" ");

                    for(int i = 1; i< vertLine.length; i++){
                        float vertCord;
                        try {
                            vertCord = Float.parseFloat(vertLine[i]);
                        } catch (NumberFormatException e){
                            continue;
                        }

                        vertices.add(vertCord);
                    }
                }
            } else if(line.charAt(0) == 'f') {
                String[] faceLine = line.split(" ");

                for(int i = 1; i < faceLine.length; i++){
                    String[] indNorm = faceLine[i].split("/");
                    int indicy;
                    try {
                        indicy = Integer.parseInt(indNorm[0]);
                    } catch (NumberFormatException e){
                        continue;
                    }

                    faceIndices.add(--indicy);
                }
            }
        }


        float[] vert = new float[vertices.size()];
        for(int i = 0; i < vert.length; i++) vert[i] = vertices.get(i);

        float[] norms = new float[normals.size()];
        for(int i = 0; i < norms.length; i++) vert[i] = normals.get(i);

        int[] face = new int[faceIndices.size()];
        for(int i = 0; i < face.length; i++) face[i] = faceIndices.get(i);

        return new Mesh(vert, norms, face,  program);
    }
}
