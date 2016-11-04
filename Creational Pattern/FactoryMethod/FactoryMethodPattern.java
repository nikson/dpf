// package com.github.nikson.dpf;

import java.util.Map;
import java.util.HashMap;

class FactoryMethodPattern {

    public static void main(String args[]) {
       
        // create maze object using abstract factory
        FactoryMethodPattern.IMazeBase maze = new FactoryMethodPattern().new MagicMazeGame();
        FactoryMethodPattern.Maze aMaze = maze.createMaze();
        
        // Walk through the all rooms 
        aMaze.walk();        
    }

    public enum Direction { EAST, WEST, NORTH, SOUTH }

    // Maze interface of factory methods  
    public interface IMazeBase {
        IRoom createRoom();
        IWall createWall();
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

    public abstract class AbastractMaze implements IMazeBase {
        // abstract protected IRoom createRoom();
        // abstract protected IWall createWall();

        public Maze createMaze() {
            Maze aMaze = new Maze();
                        
            IRoom r1 = createRoom();
            IRoom r2 = createRoom();
            IWall w = createWall();

            r1.setSide(Direction.EAST, w);
         
            aMaze.addRoom(r1);
            aMaze.addRoom(r2);

            return aMaze;             
        }
    }

    public class MazeGame extends AbastractMaze {
        int roomNo = 1;

        @Override 
        public IRoom createRoom() {
            return new Room(roomNo++);      // Return ordinary room
        }

        @Override 
        public IWall createWall() {
            return new Wall();              // Return simple wall
        }
    }

    
    public class MagicMazeGame extends AbastractMaze {
        int roomNo = 1;

        @Override 
        public IRoom createRoom() {
            return new MagicRoom(roomNo++);         // Return Magic room 
        }

        @Override 
        public IWall createWall() {
            return new DoorWall();                  // Return door wall 
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

        public DoorWall() { }
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