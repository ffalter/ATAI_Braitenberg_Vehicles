package braitenbergVehicles;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;

public class BbergVehicleBuilder implements ContextBuilder<Object> {

	@Override
	public Context build(Context<Object> context) {
		context.setId("bbergvehicles");
		
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace("space", context, new RandomCartesianAdder<Object>(),
				new repast.simphony.space.continuous.WrapAroundBorders(), 100, 100);
		
		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		Grid<Object> grid = gridFactory.createGrid("grid", context, new GridBuilderParameters<Object>(new WrapAroundBorders(), new SimpleGridAdder<Object>(), true, 100, 100));
		
		int vehicleCount = Environment.getInstance().getVehicleCount();
		ResourceManager resMan = new ResourceManager(context, space, grid);
		context.add(resMan);
		for (int i = 0; i < vehicleCount; i++) {
			context.add(new Agent(space, grid, context));
		}
		
		int resourceCount = Environment.getInstance().getResourceCount();
		for (int i = 0; i < resourceCount; i++) {
			double poisonLevel = RandomHelper.nextDoubleFromTo(0, Environment.getInstance().getResourceMaxPoisonLevel());
			context.add(new Resource(space, grid, poisonLevel, context));
		}
		
		for (Object obj : context) {
			NdPoint p = space.getLocation(obj);
			grid.moveTo(obj, (int)p.getX(), (int)p.getY());
		}
		
		return context;
	}

}
