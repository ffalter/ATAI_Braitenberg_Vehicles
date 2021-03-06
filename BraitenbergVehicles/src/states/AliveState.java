package states;

import java.awt.Color;

import saf.v3d.ShapeFactory2D;
import saf.v3d.scene.VSpatial;

public class AliveState implements IState{

	@Override
	public Color getColor() {
		return Color.BLUE;
	}

	@Override
	public VSpatial getSpatialVisualisation(ShapeFactory2D shapeFactory) {
		return shapeFactory.createRectangle(20, 10);
	}

	@Override
	public boolean isAlive() {
		return true;
	}
	
}
