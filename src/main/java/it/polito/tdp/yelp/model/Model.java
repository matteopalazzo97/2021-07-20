package it.polito.tdp.yelp.model;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private YelpDao dao;
	private Graph<User, DefaultWeightedEdge> grafo;
	
	

	public Model() {
		super();
		this.dao = new YelpDao();
	}

	public void creaGrafo(int n, int anno) {

		this.grafo = new SimpleWeightedGraph<User, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, this.dao.getVertici(n));
		
		Map<String, User> mappaUser = new HashMap<String, User>();
		
		for(User u: this.dao.getVertici(n)) {
			mappaUser.put(u.getUserId(), u);
		}
		
		List<Arco> archi = this.dao.getArchi(n, anno, mappaUser);
		
		for(Arco a: archi) {
			this.grafo.addEdge(a.getU1(), a.getU2());
			this.grafo.setEdgeWeight(a.getU1(), a.getU2(), a.getPeso());
		}
		
	}
	
	public List<UtenteSim> utentiSim(User u){
		
		List<UtenteSim> res = new LinkedList<UtenteSim>();
		
		List<User> vicini = Graphs.neighborListOf(grafo, u);
		
		int max = 0;
		
		for(User uu: vicini) {
			if(this.grafo.getEdgeWeight(this.grafo.getEdge(u, uu)) > max)
				max = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(u, uu));
		}
		
		for(User uu: vicini) {
			if(this.grafo.getEdgeWeight(this.grafo.getEdge(u, uu)) == max) {
				res.add(new UtenteSim(uu, max));
			}
				
		}
		
		return res;
		
	}

	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}

	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}

	public List<User> getVertici(int n) {
		return this.dao.getVertici(n);
	}
	
	
}
