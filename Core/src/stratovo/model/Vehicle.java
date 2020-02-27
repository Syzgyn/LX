package stratovo.model;

import java.util.Arrays;
import java.util.stream.Stream;

import heronarts.lx.model.LXModel;

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
	}
}
