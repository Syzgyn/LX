package stratovo.model;

import java.util.ArrayList;

public class VehicleFactory {
	public static Vehicle getVehicle() {
		ArrayList<VehicleSide> sides = new ArrayList<VehicleSide>();
		sides.add(VehicleSideFactory.getVehicleSide("left"));
		sides.add(VehicleSideFactory.getVehicleSide("right"));
		
		VehicleSide[] array = new VehicleSide[sides.size()];
		array = sides.toArray(array);
        return new Vehicle(array);
	}
}
