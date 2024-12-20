package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.LocalidadDao;
import entidades.Localidad;

public class LocalidadDaoImpl implements LocalidadDao {
	
	@Override
	public ArrayList<Localidad> listarLocalidades(){
	    String query = "SELECT idLocalidad, localidad FROM localidades";
		ArrayList<Localidad> listaLocalidades = new ArrayList<>();	
		
	    try (Connection conexion = Conexion.getConnection();
		         PreparedStatement statement = conexion.prepareStatement(query);
		         ResultSet resultSet = statement.executeQuery()) {

		        while (resultSet.next()) {
		            Localidad localidad = new Localidad();
		            
		            localidad.setId(resultSet.getInt("idLocalidad"));
		            localidad.setLocalidad(resultSet.getString("localidad"));
		            listaLocalidades.add(localidad);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	    
		return listaLocalidades;
	}
	public Localidad obtenerLocalidadPorId(int id) {
	    String query = "SELECT idLocalidad, localidad FROM localidades WHERE idLocalidad = ?";
	    Localidad localidad = null;

	    try (Connection conexion = Conexion.getConnection();
	         PreparedStatement statement = conexion.prepareStatement(query)) {

	        // Establecer el parámetro del id
	        statement.setInt(1, id);

	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                localidad = new Localidad();
	                localidad.setId(resultSet.getInt("idLocalidad"));
	                localidad.setLocalidad(resultSet.getString("localidad"));
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return localidad;
	}

}
