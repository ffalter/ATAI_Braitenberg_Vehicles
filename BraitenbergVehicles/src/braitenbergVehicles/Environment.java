package braitenbergVehicles;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;

public class Environment {
	
	private static Environment instance;
	
	private int vehicleCount;
	private int resourceCount;
	private int resRegrowIntervall;
	private int resRegrowCount;
	private int consumeRange;
	private int agentViewRange;
	private int agentRottingDuration;
	private double resourceMaxPoisonLevel;
	private double killingPoisonLevel;
	private double epsilonPoisonLevel;
	
	private Environment() {
		final Parameters params  = RunEnvironment.getInstance().getParameters();
		this.vehicleCount = params.getInteger("vehicleCount");
		this.resourceCount = params.getInteger("resourceCount");
		this.resRegrowCount = params.getInteger("resRegrowCount");
		this.consumeRange = params.getInteger("consumeRange");
		this.agentViewRange = params.getInteger("agentViewRange");
		this.agentRottingDuration = params.getInteger("agentRottingDuration");
		this.resourceMaxPoisonLevel = params.getDouble("resourceMaxPoisonLevel");
		this.epsilonPoisonLevel = params.getDouble("epsilonPoisonLevel");
		this.killingPoisonLevel = params.getDouble("killingPoisonLevel");
	}
	
	public static Environment getInstance() {
		
		if(instance == null) {
			instance = new Environment();
		}
		return instance;
	}
	
	public void reset() {
		instance = new Environment();
	}
	
	public int getVehicleCount() {
		return vehicleCount;
	}
	
	public int getResourceCount() {
		return resourceCount;
	}
	
	public int getResRegrowCount() {
		return resRegrowCount;
	}
	
	public int getConsumeRange() {
		return consumeRange;
	}
	
	public int getAgentViewRange() {
		return agentViewRange;
	}
	
	public int getAgentRottingDuration() {
		return agentRottingDuration;
	}
	
	public double getResourceMaxPoisonLevel() {
		return resourceMaxPoisonLevel;
	}
	
	public double getKillingPoisonLevel() {
		return killingPoisonLevel;
	}
	
	public double getEpsilonPoisonLevel() {
		return epsilonPoisonLevel;
	}
}
