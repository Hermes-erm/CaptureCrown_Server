package demo.service;

import lombok.Data;

enum RoomGenre {
    BASE, MODERATE, EXCEPTION
}

@Data
public class PayLoad {
    private int id;
    private String name;
    private RoomGenre room;
}