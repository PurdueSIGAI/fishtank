package environment;

import java.awt.Color;

import control.FishAI;

public class Fish {
	protected FishState requested_state;
	public final int id;
	public static int max_id;
	protected FishAI controller;
	
	public enum FishCode {
		OK,
		DEAD,
		TOO_SMALL,
		TOO_HUNGRY,
		OUT_OF_BOUNDS,
		COLLISION,
		NOT_DEFINED
	}

	/* member variables */

	/* the usual methods */

	// constructor
	public Fish(FishAI controller) {
		this.controller = controller;
		id = ++max_id;
		requested_state = new FishState(id);
	}

	/* fish action methods */

	// degrees are standard math textbook: from the right side counterclockwise
	public FishCode setRudderDirection(double degrees) {
		Vector direction = new Vector(Math.cos(degrees * 2 * Math.PI / 360), Math.sin(degrees * 2 * Math.PI / 360));
		synchronized (requested_state) {
			requested_state.heading = direction;
		}
		return FishCode.OK;
	}

	// set rudder direction using the given two-dimensional vector
	public FishCode setRudderDirection(double x, double y) {
		synchronized(requested_state) {
			requested_state.heading = new Vector(x, y);
			requested_state.heading = requested_state.heading.normalize();
			return FishCode.OK;
		}
	}

	// set the fish's speed
	public FishCode setSpeed(double speed) {
		synchronized(requested_state) {
			requested_state.speed = speed;
		}
		return FishCode.OK;
	}

	// reproduce the fish
	public FishCode reproduce() {
		controller.engine.requestRepro(this);
		return FishCode.OK;
	}

	// let the fish eat
	public FishCode eat() {
		//TODO
		return FishCode.NOT_DEFINED;
	}
	
	public Color getColor () {
		return controller.color;
	}
}

