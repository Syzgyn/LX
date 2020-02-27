package stratovo.effects;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import heronarts.lx.LX;
import heronarts.lx.LXEffect;
import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import heronarts.lx.parameter.EnumParameter;
import heronarts.lx.parameter.LXParameter;
import heronarts.lx.parameter.LXParameterListener;
import stratovo.model.Vehicle;

public class LavaMask extends LXEffect implements LXParameterListener {
	public enum Section {
			lava,
			scales
	}
	
	protected List<LXModel> models;
	
	public final EnumParameter<Section> section = new EnumParameter<Section>("Section", Section.lava)
		.setDescription("Which part of the model to show");
	
	public LavaMask(LX lx) {
		super(lx);
		addParameter("Section", this.section);
		onParameterChanged(section);
	}

	@Override
	protected void run(double deltaMs, double enabledAmount) {
		for(LXModel	model : this.models) {
			for (LXPoint p : model.points) {
				colors[p.index] = 0;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void onParameterChanged(LXParameter parameter) {
		Field field;
		LXModel[] models;
		
		if (parameter.getLabel().equals("Section")) {
			try {
				field = Vehicle.class.getField(((EnumParameter<Section>)parameter).getEnum().toString());
				models = (LXModel[]) field.get(model);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				return;
			}
			
			this.models = Arrays.asList(models);
		}
	}

}
