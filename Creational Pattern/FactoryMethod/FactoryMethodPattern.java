// package com.github.nikson.dpf;

import java.util.Map;
import java.util.HashMap;

class FactoryMethodPattern {

    public static void main(String args[]) {
       
        // create maze object using abstract factory
        FactoryMethodPattern.IMazeGame maze = new FactoryMethodPattern().new MagicMazeGame();
        FactoryMethodPattern.Maze aMaze = maze.createMazeGame();
        
        // Walk through the all rooms 
        aMaze.walk();        
    }

    public enum Direction { EAST, WEST, NORTH, SOUTH }

    // Maze interface of factory methods  
    public interface IMazeGame {    	
        IRoom makeRoom(int rno);
        IWall makeWall();
	IWall makeDoor(IRoom r1, IRoom r2);
        Maze makeMaze();
	Maze createMazeGame();
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

    public class MazeGame implements IMazeGame {

        public Maze createMazeGame() {
            Maze aMaze = makeMaze();
                        
            IRoom r1 = makeRoom(1);
            IRoom r2 = makeRoom(2);
            IWall w = makeWall();
	    IWall dw = makeDoor(r1, r2);

            r1.setSide(Direction.EAST, w);
            r1.setSide(Direction.WEST, dw);
            r1.setSide(Direction.NORTH, w);
            r1.setSide(Direction.SOUTH, w);
            r1.setSide(Direction.EAST, dw);
            r1.setSide(Direction.WEST, w);
            r1.setSide(Direction.NORTH, w);
            r1.setSide(Direction.SOUTH, w);
         
            aMaze.addRoom(r1);
            aMaze.addRoom(r2);

            return aMaze;             
        }
	
	public Maze makeMaze() { return new Maze(); }

        public IWall makeWall() {
            return new Wall();         
        }

	public IRoom makeRoom(int rno) { return new Room(rno); }; 

	public IWall makeDoor(IRoom r1, IRoom r2) { return new DoorWall(r1, r2); }
    }

    
    public class MagicMazeGame extends MazeGame {

	public IRoom makeRoom(int rno) { return new MagicRoom(rno); }; 

	public IWall makeDoor(IRoom r1, IRoom r2) { return new DoorWall(r1, r2); }
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
	private String spell; 

	public MagicRoom(int rno) { super(rno); } 

        public MagicRoom(int rno, String spell) {
            super(rno);
	    this.spell = spell;
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
