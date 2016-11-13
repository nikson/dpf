// package com.github.nikson.dpf;

import java.util.Map;
import java.util.HashMap;

class PrototypePattern {

    public static void main(String args[]) {
      	new PrototypePattern().new MazeGame();
    }

    public enum Direction { EAST, WEST, NORTH, SOUTH }

    interface IWall extends Cloneable {
        void enter();
		Object clone() throws CloneNotSupportedException;
    }

    interface IRoom extends Cloneable {
        int getRoomNo();
        IWall getSide(Direction dir);
        void setSide(Direction d, IWall w) ;

	void initialize(int roomno); 
        void enter();
        void walk(); 
		Object clone() throws CloneNotSupportedException;
    }

    public class MazeGame {
		// Client 
		public MazeGame() {
			PrototypeFactory factory = new PrototypeFactory(new Maze(), new Room(), new Wall(), new DoorWall());
        	// create maze object using prototype pattern
	        Maze aMaze = createMaze(factory);
        
        	// Walk through the all rooms 
	        aMaze.walk();      
		}

		public Maze createMaze(PrototypeFactory proto) {
			Maze aMaze = null;

			try {
				aMaze = proto.makeMaze();

				IRoom r1 = proto.makeRoom(1);
				IRoom r2 = proto.makeRoom(2);
				IWall w = proto.makeWall();
				IWall dw = proto.makeDoor(r1, r2);

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
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return aMaze;             
    	}
    }

    class PrototypeFactory { 
	
	// private static PrototypeFactory _instance; 

	// Prototypes
	private Maze pMaze;
	private IRoom pRoom;
	private IWall pWall;
	private IWall pDoorWall;

	public PrototypeFactory(Maze m, IRoom r, IWall w, IWall doorWall) {
		pMaze = m;
		pRoom = r;
		pWall = w;
		pDoorWall = doorWall;
	}

//	###singleton is not working here because of nested inner class ###
//	public static PrototypeFactory instance (Maze m, IRoom r, IWall w, IWall doorWall) {
//		if ( _instance == null ) {
//			_instance = new PrototypeFactory(m, r, w, doorWall);
//		}
//
//		return _instance;
//	}
	
	public Maze makeMaze() throws CloneNotSupportedException { return (Maze) pMaze.clone(); }

	public IRoom makeRoom(int n) throws CloneNotSupportedException {
		IRoom r = (IRoom) pRoom.clone(); 
		r.initialize(n);
		return r;
	}

	public IWall makeWall() throws CloneNotSupportedException { return (IWall) pWall.clone(); } 


	public IWall makeDoor(IRoom r1, IRoom r2) throws CloneNotSupportedException {
		// ToDo
		return (IWall) pDoorWall.clone();
	}

    }

    class Wall implements IWall {
        public void enter() {
            System.out.println("Simple Wall...");
        }

		public Object clone() throws CloneNotSupportedException {
			return super.clone();
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

		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
    }

    class Room implements IRoom {
        protected Map<Direction, IWall> sides = new HashMap<Direction, IWall>();
        protected int roomNo;

        public Room () { } 

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

		public void initialize(int roomno) { roomNo = roomno; } 
	
		public Object clone() throws CloneNotSupportedException {
			return super.clone();
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
		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
    }

    public class Maze implements Cloneable  {
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
	
		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
    }
}
