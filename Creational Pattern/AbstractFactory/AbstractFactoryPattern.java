// package com.github.nikson.dpf;

import java.util.Map;
import java.util.HashMap;

class AbstractFactoryPattern {

    public static void main(String args[]) {
       
        // create maze object using abstract factory
        AbstractFactoryPattern.IMazeFactory mazeFactory = new AbstractFactoryPattern().new MazeFactory();
        AbstractFactoryPattern.Maze aMaze = mazeFactory.createMaze();
        
        // Walk through the all rooms 
        aMaze.walk();        
    }

    public enum Direction { EAST, WEST, NORTH, SOUTH }

    // Abstraction using interface
    // Maze factory 
    public interface IMazeFactory {
        Maze createMaze();
    }

    interface IWall {
        void enter();
    }

    interface IRoom {
        void enter();
        void walk(); 
    }

    // Factory for room and wall 
    interface IElementFactory {
        Room createRoom(int roomNo);
        Wall createWall();
        DoorWall createDoorWall(Room r1, Room r2);
    }
    // end of abstraction 

    public class MazeFactory implements IMazeFactory {

        public Maze createMaze() {
            // N.B.:  I'm not sure can I create Room and Wall  here
            Maze aMaze = new Maze();
            // create room & wall factory
            IElementFactory roomFactory = new RoomFactory();
            IElementFactory wallFactory = new WallFactory();

            Room r1 = roomFactory.createRoom(1);
            Room r2 = roomFactory.createRoom (2);        
            DoorWall d = wallFactory.createDoorWall(r1, r2);
                    
            r1.setSide(Direction.NORTH, new Wall());
            r1.setSide(Direction.EAST, d);
            r1.setSide(Direction.SOUTH, new Wall());
            r1.setSide(Direction.WEST, new Wall());
            r2.setSide(Direction.NORTH, new Wall());
            r2.setSide(Direction.EAST, new Wall());
            r2.setSide(Direction.SOUTH, new Wall());
            r2.setSide(Direction.WEST, d);

            aMaze.addRoom(r1);
            aMaze.addRoom(r2);

            return aMaze;             
        }
    }

    class Wall implements IWall {
        public void enter() {
            System.out.println("Simple Wall...");
        }
    }

    class Room implements IRoom {
        private Map<Direction, Wall> sides = new HashMap<Direction, Wall>();
        private int roomNo;

        public Room (int rno){
            roomNo = rno;
        }

        public int getRoomNo() { return roomNo; }
        public Wall getSide(Direction dir){ return sides.get(dir); }
        public void setSide(Direction d, Wall w) { sides.put(d, w); }

        public void enter() {
            System.out.println("Entered into room: " + roomNo );
        }

        public void walk() {
            for ( Wall w : sides.values() ) {
                w.enter();
            }
        }
    }

    class DoorWall extends Wall {
        private Room r1;
        private Room r2;
        private boolean isOpen;

        public DoorWall (Room r1, Room r2) {
            this.r1 = r1;
            this.r2 = r2;
            isOpen = false;
        }

        public void enter() {
            System.out.println("Crossing a door wall...");
        }
    }

    class RoomFactory implements IElementFactory {
        public Room createRoom(int rno) {
            return new Room(rno);
        }

        public Wall createWall() { return null; }

        public DoorWall createDoorWall(Room r1, Room r2) { return null; }
    }


    class WallFactory implements IElementFactory {
        public Room createRoom(int rno) {
            return null;
        }

        public Wall createWall() {
            return new Wall();
        }

        public DoorWall createDoorWall(Room r1, Room r2) {
            return new DoorWall(r1, r2);
        }
    }

    public class Maze {
        private Map<Integer, Room> rooms = new HashMap<Integer, Room>();
        
        public void addRoom(Room r) { 
            rooms.put(r.getRoomNo(), r); 
        }
        
        public Room roomNo (int r) { return rooms.get(r); }

        public void walk() {
            for ( Room r : rooms.values() ) {
                r.enter();
                r.walk();
            }
        }
    }
}