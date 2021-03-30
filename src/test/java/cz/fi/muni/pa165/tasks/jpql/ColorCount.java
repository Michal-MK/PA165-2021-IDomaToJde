package cz.fi.muni.pa165.tasks.jpql;

import cz.fi.muni.pa165.enums.Color;

public class ColorCount {

	private Color color;
	private Long count;
	
	public ColorCount(Color color, Long count) {
		this.color = color;
		this.count = count;
	}

	public Color getColor() {
		return color;
	}

	public Long getCount() {
		return count;
	}
}
