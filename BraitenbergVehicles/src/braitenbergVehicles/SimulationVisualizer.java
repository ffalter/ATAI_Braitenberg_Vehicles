package braitenbergVehicles;

import java.awt.Color;

import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.scene.VSpatial;

public class SimulationVisualizer extends DefaultStyleOGL2D{
	
	@Override
	public VSpatial getVSpatial(Object agent, VSpatial spatial) {
		return ((Agent) agent).getState().getSpatialVisualisation(shapeFactory);
	}

	@Override
	public Color getColor(Object agent) {
		return ((Agent) agent).getState().getColor();
	}

}
