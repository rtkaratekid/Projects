
package ui;

import java.io.Serializable;

public class CableInfo implements Serializable{
	@Override
	public String toString() {
		return "CableInfo [source=" + source + ", target=" + target + "]";
	}
	int source, target;
	public CableInfo(int s, int t) {
		source = s;
		target = t;
	}
}