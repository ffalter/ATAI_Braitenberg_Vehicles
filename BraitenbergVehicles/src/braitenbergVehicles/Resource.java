package braitenbergVehicles;

import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.SimUtilities;

public class Resource {
	
	private ContinuousSpace<Object> space;
	
	private Grid<Object> grid;
	
	private Context<Object> context;
	
	private double poisonousLevel;
	
	private int lifetime;

	public Resource (ContinuousSpace<Object> space, Grid<Object> grid, double poisonousLevel, Context<Object> context) {
		this.space = space;
		this.grid = grid;
		this.poisonousLevel = poisonousLevel;
		this.context = context;
		this.lifetime = 0;
	}
	
	@ScheduledMethod(start = 1, interval = 5000)
	public void run() {
		
		if(lifetime > 50) {
			this.context.remove(this);
		}
		
		lifetime++;
	}
	
	public double getPoisonousLevel() {
		return this.poisonousLevel;
	}
}
