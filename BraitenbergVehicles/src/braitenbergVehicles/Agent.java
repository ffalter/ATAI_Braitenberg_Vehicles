package braitenbergVehicles;

import java.util.ArrayList;
import java.util.List;

import cern.jet.random.Normal;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;
import states.AliveState;
import states.DeathState;
import states.IState;

public class Agent {
	
	private ContinuousSpace<Object> space;
	
	private Grid<Object> grid;
	
	private Context<Object> context;
	
	private double speed;
	
	private double poisonLevel;
	
	private double maxPoisonLevel;
	
	private IState state;
	
	private int deathCounter;
	
	public Agent (ContinuousSpace<Object> space, Grid<Object> grid, Context<Object> context) {
		this.space = space;
		this.grid = grid;
		this.speed = 1;
		this.poisonLevel = 0;
		this.state = new AliveState();
		this.deathCounter = 0;
		this.context = context;
		this.maxPoisonLevel = 1;
	}
	
	@ScheduledMethod(start = 1, interval = 5000)
	public void step() {
		
		if(deathCounter > 0 && deathCounter <= Environment.getInstance().getAgentRottingDuration()) {
			deathCounter++;
			return;
		}
		
		if(deathCounter > Environment.getInstance().getAgentRottingDuration()) {
			this.context.remove(this);
			return;
		}
		
		if (this.poisonLevel >= Environment.getInstance().getKillingPoisonLevel() && deathCounter == 0) {
			this.state = new DeathState();
			this.speed = 0;
			this.deathCounter++;
			System.out.println("Agent dies with Poisonlevel " + this.poisonLevel);
			return;
		}
		
		if(this.poisonLevel >= 0.15) {
			this.poisonLevel -= 0.15;
		}
		
		Resource closestRes = getClosestResource();
		
		if(closestRes != null) {
			this.moveTowards(grid.getLocation(closestRes));
			this.consume(closestRes);
		}
		
		this.adaptMaxPoisonLevel();
	}
	
	public void moveTowards(GridPoint gp) {
		if (!gp.equals(grid.getLocation(this))) {
			NdPoint myP = space.getLocation(this);
			NdPoint otherP = new NdPoint(gp.getX(), gp.getY());
			double angle = SpatialMath.calcAngleFor2DMovement(space, myP, otherP);
			space.moveByVector(this, speed, angle, 0);
			myP = space.getLocation(this);
			grid.moveTo(this, (int)myP.getX(), (int)myP.getY());
		}
	}
	
	public void consume(Resource closestRes) {
		
		if(grid.getDistance(grid.getLocation(this), grid.getLocation(closestRes)) <= Environment.getInstance().getConsumeRange()) {
			this.poisonLevel += closestRes.getPoisonousLevel();
			context.remove(closestRes);
		}
		
	}
	
	private Resource getClosestResource() {
		Resource closestRes = null;
		double dist = -1;
		for (Object o : context.getObjects(Resource.class)) {
			Resource res = (Resource) o;
			if ((dist == -1 || grid.getDistance(grid.getLocation(o), grid.getLocation(this)) < dist) && this.poisonLevel + res.getPoisonousLevel() < this.maxPoisonLevel) {
				closestRes = (Resource) o;
				dist = grid.getDistance(grid.getLocation(closestRes), grid.getLocation(this));
			}
		}
		return closestRes;
	}
	
	private void adaptMaxPoisonLevel() {
		double newMaxPoisonLevel = this.maxPoisonLevel;
		for(Object o : context.getObjects(Agent.class)) {
			Agent agent = (Agent) o;
			if(grid.getDistance(grid.getLocation(this), grid.getLocation(o)) <= Environment.getInstance().getAgentViewRange() && !agent.getState().isAlive()) {
				if (agent.getPoisonLevel() < newMaxPoisonLevel) {
					newMaxPoisonLevel = agent.getPoisonLevel();
				}
			}
		}
		if(newMaxPoisonLevel != this.maxPoisonLevel) {
			this.maxPoisonLevel = newMaxPoisonLevel;
			System.out.println("Agent got new max Poison level " + this.maxPoisonLevel);
		}
	}
	
	public IState getState() {
		return state;
	}
	
	public double getPoisonLevel() {
		return this.poisonLevel;
	}
	
	public double getMaxPoisonLevel() {
		return this.maxPoisonLevel;
	}
}
