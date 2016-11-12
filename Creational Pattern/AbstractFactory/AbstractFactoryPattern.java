// package com.github.nikson.dpf;

import java.util.Map;
import java.util.HashMap;

class AbstractFactoryPattern {

    public static void main(String args[]) {
      
	System.out.println("Loading General Maze:.....");
        // create maze object using abstract factory
        AbstractFactoryPattern.IMazeFactory mazeFactory = 
		new AbstractFactoryPattern().new MazeFactory();
        AbstractFactoryPattern.Maze aMaze = buildMaze(mazeFactory );
        
        // Walk through the all rooms 
        aMaze.walk();        

	System.out.println("Loading Magic Maze:.....");
	mazeFactory = new AbstractFactoryPattern().new MagicMazeFactory();
        aMaze = buildMaze(mazeFactory );
        aMaze.walk();        
    }

    public static Maze buildMaze(IMazeFactory factories) {
    
            Maze aMaze = factories.createMaze();
            
            // client unaware the exact type of object return by factory
            IRoom r1 = factories.createRoom(1);
            IRoom r2 = factories.createRoom (2); 
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

    public enum Direction { EAST, WEST, NORTH, SOUTH }

    // Abstraction using interface: Maze factory 
    public interface IMazeFactory {
        Maze createMaze();
        IRoom createRoom(int roomNo);
        IWall createWall();
        IWall createDoorWall(IRoom r1, IRoom r2);
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


    class SpellDoorWall extends Wall {
        private IRoom r1;
        private IRoom r2;
        private boolean isOpen;
	private String spell;

        public SpellDoorWall (IRoom r1, IRoom r2, String spell) {
            this.r1 = r1;
            this.r2 = r2;
            this.isOpen = false;
	    this.spell = spell;
        }

        public void enter() {
            System.out.println("Crossing a spell door wall...");
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

        public MagicRoom(int rno) {
            super(rno);
        }


        public MagicRoom(int rno, String spell) {
            super(rno);
	    this.spell = spell;
	}

        public void enter() {
            System.out.println("Entered into magic room: " + this.roomNo );
        }        
    }


    public class MazeFactory implements IMazeFactory {

        public Maze createMaze () {
            return new Maze();
        }        
        public IRoom createRoom(int rno) {
	    return new Room(rno);        
	}

        public IWall createWall() { 
            return new Wall(); 
        }

        public IWall createDoorWall(IRoom r1, IRoom r2) { 
            return new DoorWall(r1, r2); 
        }
    }


    public class MagicMazeFactory extends MazeFactory {

        public IRoom createRoom(int rno) {
	    return new MagicRoom(rno, genSpell());        
	}


        public IWall createDoorWall(IRoom r1, IRoom r2) { 
            return new SpellDoorWall(r1, r2, genSpell()); 
        }

	private String genSpell() {
		return "XXX"; 	   // generate a random spell word
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
