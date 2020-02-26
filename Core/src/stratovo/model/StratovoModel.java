package stratovo.model;

import heronarts.lx.model.LXModel;

public interface StratovoModel {

    public default StratovoModel addKey(String key) {
        String[] oldKeys = this.getKeys();
        String[] newKeys = new String[oldKeys.length + 1];

        for (int i = 0; i < oldKeys.length; i++) {
            newKeys[i] = oldKeys[i];
        }

        newKeys[newKeys.length - 1] = key;

        this.setKeys(newKeys);

        return this;
    }
    
    public LXModel setKeys(String[] keys);
    public String[] getKeys();
    
    public default boolean hasKey(String key) {
    	for(String s: this.getKeys()) {
    		if(s.equals(key)) {
    			return true;
    		}
    	}
    	return false;
    }
}
