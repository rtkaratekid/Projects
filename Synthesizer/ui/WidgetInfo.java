
package ui;

import java.io.Serializable;

import common.SynthComponent;

public class WidgetInfo implements Serializable {

	public WidgetInfo(Class<?> widgetClass, double x, double y, SynthComponent source) {
		WidgetClass = widgetClass;
		this.x = x;
		this.y = y;
		this.source = source;
	}
	
	public Class<?> WidgetClass;
	public double x, y;
	public SynthComponent source;
	@Override
	public String toString() {
		return "WidgetInfo [WidgetClass=" + WidgetClass + ", x=" + x + ", y=" + y + ", source=" + source + "]";
	}

	
	
}