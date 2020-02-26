package stratovo.model;

import java.net.UnknownHostException;
import java.util.Arrays;

import heronarts.lx.model.LXModel;
import heronarts.lx.output.LXDatagram;
import heronarts.lx.output.StreamingACNDatagram;

public class VehicleSide extends LXModel {
    public CSVModel[] scales;
    public CSVModel[] lava;

    public VehicleSide(CSVModel[] scales) {
    	super(scales);
    	this.scales = scales;
    	this.lava = new Scale[0];
    }
    public VehicleSide(CSVModel[] scales, CSVModel[] lava) {
        super(combineModels(scales, lava));
        
        this.scales = scales;
        this.lava = lava;
        
        /*
        int[] l = this.lava[0].toIndexBuffer(0, 5);
        int[] s = this.scales[0].toIndexBuffer(0, 10);
        try {
        	int[] buffer = Arrays.copyOf(l, l.length + s.length);
        	System.arraycopy(s, 0, buffer, l.length, s.length);
        	LXDatagram data = new StreamingACNDatagram(buffer, 900).setAddress("192.168.0.50");
			//this.addDatagram(data);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
    }
    
    private static LXModel[] combineModels(CSVModel[] scales, CSVModel[] lava) {
    	LXModel[] both = Arrays.copyOf(scales, scales.length + lava.length);
    	System.arraycopy(lava, 0, both, scales.length, lava.length);
    	
    	return both;
    }

}
