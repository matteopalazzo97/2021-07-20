package it.polito.tdp.yelp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.yelp.model.Arco;
import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Review;
import it.polito.tdp.yelp.model.User;

public class YelpDao {

	public List<Business> getAllBusiness(){
		String sql = "SELECT * FROM Business";
		List<Business> result = new ArrayList<Business>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Business business = new Business(res.getString("business_id"), 
						res.getString("full_address"),
						res.getString("active"),
						res.getString("categories"),
						res.getString("city"),
						res.getInt("review_count"),
						res.getString("business_name"),
						res.getString("neighborhoods"),
						res.getDouble("latitude"),
						res.getDouble("longitude"),
						res.getString("state"),
						res.getDouble("stars"));
				result.add(business);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Review> getAllReviews(){
		String sql = "SELECT * FROM Reviews";
		List<Review> result = new ArrayList<Review>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Review review = new Review(res.getString("review_id"), 
						res.getString("business_id"),
						res.getString("user_id"),
						res.getDouble("stars"),
						res.getDate("review_date").toLocalDate(),
						res.getInt("votes_funny"),
						res.getInt("votes_useful"),
						res.getInt("votes_cool"),
						res.getString("review_text"));
				result.add(review);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<User> getAllUsers(){
		String sql = "SELECT * FROM Users";
		List<User> result = new ArrayList<User>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				User user = new User(res.getString("user_id"),
						res.getInt("votes_funny"),
						res.getInt("votes_useful"),
						res.getInt("votes_cool"),
						res.getString("name"),
						res.getDouble("average_stars"),
						res.getInt("review_count"));
				
				result.add(user);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<User> getVertici(int n){
		String sql = "SELECT DISTINCT u.*, COUNT(r.`review_id`) AS nrev "
				+ "FROM Reviews r, Users u "
				+ "WHERE r.`user_id`=u.`user_id` "
				+ "GROUP BY r.`user_id` "
				+ "HAVING nrev>=?";
		List<User> result = new ArrayList<User>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				User user = new User(res.getString("user_id"),
						res.getInt("votes_funny"),
						res.getInt("votes_useful"),
						res.getInt("votes_cool"),
						res.getString("name"),
						res.getDouble("average_stars"),
						res.getInt("review_count"));
				
				result.add(user);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Arco> getArchi(int n, int anno, Map<String, User> mappa){
		String sql = "SELECT r1.`user_id` AS u1, r2.`user_id` AS u2, COUNT(*) AS peso "
				+ "FROM Reviews r1, Reviews r2 "
				+ "WHERE r1.`business_id`=r2.`business_id` "
				+ "AND r1.`user_id`<r2.`user_id` "
				+ "AND r1.`user_id` IN (SELECT p1.`user_id` "
				+ "								FROM (SELECT DISTINCT u.* "
				+ "								FROM Reviews r, Users u "
				+ "								WHERE r.`user_id`=u.`user_id` "
				+ "								GROUP BY r.`user_id` "
				+ "								HAVING COUNT(r.review_id)>=?) AS p1, Reviews r "
				+ "							WHERE r.`user_id`=p1.`user_id`) "
				+ "AND r2.`user_id` IN (SELECT p1.`user_id` "
				+ "								FROM (SELECT DISTINCT u.* "
				+ "								FROM Reviews r, Users u "
				+ "								WHERE r.`user_id`=u.`user_id` "
				+ "								GROUP BY r.`user_id` "
				+ "								HAVING COUNT(r.review_id)>=?) AS p1, Reviews r "
				+ "							WHERE r.`user_id`=p1.`user_id`) "
				+ "AND YEAR(r1.`review_date`)=? "
				+ "AND YEAR(r2.`review_date`)=? "
				+ "GROUP BY r1.`user_id`, r2.`user_id`";
		List<Arco> result = new ArrayList<Arco>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n);
			st.setInt(2, n);
			st.setInt(3, anno);
			st.setInt(4, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Arco arco = new Arco(mappa.get(res.getString("u1")), mappa.get(res.getString("u2")), res.getInt("peso"));
				
				result.add(arco);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
