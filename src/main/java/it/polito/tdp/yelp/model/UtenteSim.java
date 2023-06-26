package it.polito.tdp.yelp.model;

public class UtenteSim {
	
	private User u;
	private int sim;
	
	public UtenteSim(User u, int sim) {
		super();
		this.u = u;
		this.sim = sim;
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public int getSim() {
		return sim;
	}

	public void setSim(int sim) {
		this.sim = sim;
	}

	@Override
	public String toString() {
		return u + "    GRADO: " + sim ;
	}
	
	

}
