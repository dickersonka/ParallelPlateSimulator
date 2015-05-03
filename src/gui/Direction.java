package gui;

public enum Direction {
	NORTH {
		@Override
		public Direction getClockwiseDir() {
			return EAST;
		}

		@Override
		public Direction getAntiClockwiseDir() {
			return WEST;
		}
		
		@Override
		public Direction getOppositeDir() {
			return SOUTH;
		}
	}, EAST {
		@Override
		public Direction getClockwiseDir() {
			return SOUTH;
		}

		@Override
		public Direction getAntiClockwiseDir() {
			return NORTH;
		}
		
		@Override
		public Direction getOppositeDir() {
			return WEST;
		}
	}, SOUTH {
		@Override
		public Direction getClockwiseDir() {
			return WEST;
		}

		@Override
		public Direction getAntiClockwiseDir() {
			return EAST;
		}
		
		@Override
		public Direction getOppositeDir() {
			return NORTH;
		}
	}, WEST {
		@Override
		public Direction getClockwiseDir() {
			return NORTH;
		}

		@Override
		public Direction getAntiClockwiseDir() {
			return SOUTH;
		}
		
		@Override
		public Direction getOppositeDir() {
			return EAST;
		}
	};
	
	public abstract Direction getClockwiseDir();
	public abstract Direction getAntiClockwiseDir();
	public abstract Direction getOppositeDir();
	
	public static Direction fromString(String s) throws IllegalArgumentException{
		s = s.toUpperCase();
		if(s.equals("NORTH"))
			return NORTH;
		else if(s.equals("EAST"))
			return EAST;
		else if(s.equals("SOUTH"))
			return SOUTH;
		else if(s.equals("WEST"))
			return WEST;
		else
			throw new IllegalArgumentException();
	}
}