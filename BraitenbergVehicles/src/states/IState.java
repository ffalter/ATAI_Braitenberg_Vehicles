package states;

import java.awt.Color;

import saf.v3d.ShapeFactory2D;
import saf.v3d.scene.VSpatial;

public interface IState {
	
	public Color getColor();

	public VSpatial getSpatialVisualisation(ShapeFactory2D shapeFactory);
	
	public boolean isAlive();
}
