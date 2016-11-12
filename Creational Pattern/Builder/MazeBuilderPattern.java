
import java.util.Map;
import java.util.HashMap;

class MazeBuilderPattern {

    public static void main(String args[]) {
      
	IMazeBuilder mazeBuilder = new MazeBuilderPattern().new MazeBuilder();
	createMaze(mazeBuilder).walk();
    }

    public static Maze createMaze(IMazeBuilder builder) {
            builder.buildMaze();
	    builder.buildRoom(1);
            builder.buildRoom (2); 
            builder.buildDoor(1, 2);
                    
            return builder.getMaze();
    }

    public enum Direction { EAST, WEST, NORTH, SOUTH }

    public interface IMazeBuilder {
        void buildMaze();
        void buildRoom(int roomNo);
        void buildDoor(int r1, int r2);
	Maze getMaze();
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

    public class MazeBuilder implements IMazeBuilder {

	private Maze _currentMaze; 

        public void buildMaze () {
            _currentMaze = new Maze();
        }        
        public void buildRoom(int rno) {	    
	    Room r1 = new Room(rno);
	    Wall w = new Wall();

            r1.setSide(Direction.NORTH, w);
            r1.setSide(Direction.EAST, w);
            r1.setSide(Direction.SOUTH, w);
            r1.setSide(Direction.WEST, w);

	    _currentMaze.addRoom(r1);
	}


        public void buildDoor(int n1, int n2) { 

	    IRoom r1 = _currentMaze.getRoomNo(n1);
	    IRoom r2 = _currentMaze.getRoomNo(n2);

	    DoorWall dw = new DoorWall(r1, r2);

            r1.setSide(Direction.EAST,dw);
            r2.setSide(Direction.WEST,dw);
        }

	public Maze getMaze() {
	    return _currentMaze;
	}
    }

    public class Maze {
        private Map<Integer, IRoom> rooms = new HashMap<Integer, IRoom>();
        
        public void addRoom(IRoom r) { 
            rooms.put(r.getRoomNo(), r); 
        }
        
        public IRoom getRoomNo (int r) { return rooms.get(r); }

        public void walk() {
            for ( IRoom r : rooms.values() ) {
                r.enter();
                r.walk();
            }
        }
    }
}
