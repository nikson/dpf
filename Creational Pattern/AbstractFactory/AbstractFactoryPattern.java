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
        int getRoomNo();
        IWall getSide(Direction dir);
        void setSide(Direction d, IWall w) ;

        void enter();
        void walk(); 
    }

    // AbstractFactory: encapsulate room and wall factories 
    interface IElementFactory {
        IRoom createRoom(int roomNo);
        IRoom createMagicRoom(int rno);
        IWall createWall();
        IWall createDoorWall(IRoom r1, IRoom r2);
    }
    // end of abstraction 

    public class MazeFactory implements IMazeFactory {

        public Maze createMaze() {
            // N.B.:  I'm not sure can I create Room and Wall  here
            Maze aMaze = new Maze();
            // create room & wall factory
            IElementFactory factories = new ElementFactory();
            
            // client unaware the exact type of object return by factory
            IRoom r1 = factories.createRoom(1);
            IRoom r2 = factories.createMagicRoom (2); 
            IWall w = factories.createWall();       
            IWall d = factories.createDoorWall(r1, r2);
                    
            r1.setSide(Direction.NORTH, w);
            r1.setSide(Direction.EAST, d);
            r1.setSide(Direction.SOUTH, w);
            r1.setSide(Direction.WEST, w);
            r2.setSide(Direction.NORTH, w);
            r2.setSide(Direction.EAST, w);
            r2.setSide(Direction.SOUTH, w);
            r2.setSide(Direction.WEST, d);

            aMaze.addRoom(r1);
            aMaze.addRoom(r2);

            return aMaze;             
        }
    }

    // Another abstraction on top of ElementFactory (Room & Wall)
    public class MagicMazeFactory implements IMazeFactory {
        public Maze createMaze () {
            // ToDo: 
            return new Maze();
        }        
    }

    class Wall implements IWall {
        public void enter() {
            System.out.println("Simple Wall...");
        }
    }
    
    class DoorWall extends Wall {
        private IRoom r1;
        private IRoom r2;
        private boolean isOpen;

        public DoorWall (IRoom r1, IRoom r2) {
            this.r1 = r1;
            this.r2 = r2;
            isOpen = false;
        }

        public void enter() {
            System.out.println("Crossing a door wall...");
        }
    }

    class Room implements IRoom {
        protected Map<Direction, IWall> sides = new HashMap<Direction, IWall>();
        protected int roomNo;

        public Room (int rno){
            roomNo = rno;
        }

        public int getRoomNo() { return roomNo; }
        public IWall getSide(Direction dir){ return sides.get(dir); }
        public void setSide(Direction d, IWall w) { sides.put(d, w); }

        public void enter() {
            System.out.println("Entered into room: " + roomNo );
        }

        public void walk() {
            for ( IWall w : sides.values() ) {
                w.enter();
            }
        }
    }

    class MagicRoom extends Room {        

        public MagicRoom(int rno) {
            super(rno);
        }

        public void enter() {
            System.out.println("Entered into magic room: " + this.roomNo );
        }        
    }


    // Concrete class of IElementFactory (AbstractFactory) 
    class ElementFactory implements IElementFactory {
        IElementFactory roomFactory = new RoomFactory();
        IElementFactory mroomFactory = new MagicRoomFactory();
        IElementFactory wallFactory = new WallFactory();
        IElementFactory dwallFactory = new DoorWallFactory();

        public IRoom createRoom(int rno) {
            return roomFactory.createRoom(rno);
        }

        public IRoom createMagicRoom(int rno) {
            return mroomFactory.createMagicRoom(rno);
        }

        public IWall createWall() { 
            return wallFactory.createWall(); 
        }

        public IWall createDoorWall(IRoom r1, IRoom r2) { 
            return dwallFactory.createDoorWall(r1, r2); 
        }
    }

    // Concrete Factory 1 : Room
    class RoomFactory implements IElementFactory {
        public IRoom createRoom(int rno) {
              return new Room(rno);
        }

        public IRoom createMagicRoom(int rno) {
            return null;
        }

        public IWall createWall() { return null; }

        public IWall createDoorWall(IRoom r1, IRoom r2) { return null; }
    }

    // Concrete Factory 2 : Room
    class MagicRoomFactory implements IElementFactory {
        public IRoom createRoom(int rno) {
            return null;
        }

        public IRoom createMagicRoom(int rno) {
            return new MagicRoom(rno);
        }

        public IWall createWall() { return null; }

        public IWall createDoorWall(IRoom r1, IRoom r2) { return null; }
    }

    // Concrete Factory 1 : Wall 
    class WallFactory implements IElementFactory {
        public IRoom createRoom(int rno) {
            return null;
        }

        public IRoom createMagicRoom(int rno) { return null; } 

        public IWall createWall() {
            return new Wall();
        }

        public IWall createDoorWall(IRoom r1, IRoom r2) { return null; }            
    }

    // Concrete Factory 2 : Wall
    class DoorWallFactory implements IElementFactory {
        public IRoom createRoom(int rno) {
            return null;
        }

        public IRoom createMagicRoom(int rno) { return null; }

        public IWall createWall() {
            return null;
        }

        public IWall createDoorWall(IRoom r1, IRoom r2) { 
            return new DoorWall(r1, r2);
        }
    }

    public class Maze {
        private Map<Integer, IRoom> rooms = new HashMap<Integer, IRoom>();
        
        public void addRoom(IRoom r) { 
            rooms.put(r.getRoomNo(), r); 
        }
        
        public IRoom roomNo (int r) { return rooms.get(r); }

        public void walk() {
            for ( IRoom r : rooms.values() ) {
                r.enter();
                r.walk();
            }
        }
    }
}