package game.cells;

public class Cell {

	String name = "";

	Cell(String name) {
//		super();
		this.name = name;
	}

	public Cell() {
		name = "unKnown";
	}

	public String getName() {
		return name;
	}
}
