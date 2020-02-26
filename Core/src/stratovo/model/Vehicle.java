package stratovo.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import heronarts.lx.model.GridModel;
import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;

public class Vehicle extends LXModel {
	public VehicleSide[] sides;
	public CSVModel[] scales;
	public CSVModel[] lava;

	public Vehicle(VehicleSide[] sides) {
		super(sides);
		
		this.sides = sides;
		
		this.scales = Arrays.stream(sides)
				.flatMap(side -> Stream.of(side.scales))
				.toArray(size -> new CSVModel[size]);
		
		this.lava = Arrays.stream(sides)
				.flatMap(side -> Stream.of(side.lava))
				.toArray(size -> new CSVModel[size]);
		
		GridModel g = new GridModel(20, 4, 2.4f, 4);
		List<LXPoint> points = g.getPoints();
	}
}
