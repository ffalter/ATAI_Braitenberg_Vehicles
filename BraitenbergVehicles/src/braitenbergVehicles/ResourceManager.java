package braitenbergVehicles;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

public class ResourceManager {
	
	private Context<Object> context;
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	
	
	public ResourceManager(Context<Object> context, ContinuousSpace<Object> space, Grid<Object> grid) {
		this.context = context;
		this.space = space;
		this.grid = grid;
	}
	
	@ScheduledMethod(start = 1, interval = 5000)
	public void run() {
		int resCount = Environment.getInstance().getResRegrowCount();
		
		for(int i = 0; i < resCount; i++) {
			double poisonLevel = RandomHelper.nextDoubleFromTo(0, Environment.getInstance().getResourceMaxPoisonLevel());
			Resource res = new Resource(space, grid, poisonLevel, context);
			context.add(res);
			NdPoint p = space.getLocation(res);
			grid.moveTo(res, (int)p.getX(), (int)p.getY());
		}
	}

}
