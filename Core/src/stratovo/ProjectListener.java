package stratovo;

import java.io.File;
import java.util.List;

import heronarts.lx.LX;
import heronarts.lx.LXChannelBus;
import heronarts.lx.model.LXModel;
import stratovo.model.Vehicle;

public class ProjectListener implements heronarts.lx.LX.ProjectListener {
	private LX lx;
	
	public ProjectListener(LX lx) {
		this.lx = lx;
		
		//lx.registerEffect(LavaMask.class);
	}

	@Override
	public void projectChanged(File file, Change change) {
		System.out.println(change);
		//if (change.equals(Change.OPEN)) {
			processChannels();
		//}
	}
	
	private void processChannels() {
		Vehicle vehicle = (Vehicle) lx.getModel();
		List<LXChannelBus> channels = lx.engine.getChannels();
		for (LXChannelBus channel : channels) {
			if (channel.getGroup() == null) {
				continue;
			}
			String label = channel.getCanonicalLabel();
			String groupLabel = channel.getGroup().getCanonicalLabel();
			
			if (!label.equals("Pattern")) {
				channel.setModel(vehicle);
				continue;
			}
			
			switch (groupLabel) {
			case "Lava":
				channel.setModel(new LXModel(vehicle.lava));
				System.out.println("Set lava channel model");
				break;
			case "Scales":
				channel.setModel(new LXModel(vehicle.scales));
				System.out.println("Set scales channel model");
				break;
			}
		}
	}
}
