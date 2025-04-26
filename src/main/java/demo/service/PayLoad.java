package demo.service;

import lombok.Data;

@Data
class Pose { // public ig
    private float x;
    private float y;
    private float z;
    private Object angle;
}

@Data
public class PayLoad {
    private String name;
    private Pose pose;
    private String color;
}