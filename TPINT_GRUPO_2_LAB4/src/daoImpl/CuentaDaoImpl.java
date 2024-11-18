package daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.CuentaDao;
import entidades.Cliente;
import entidades.Cuenta;
import entidades.TipoCuenta;
import entidades.Usuario;

public class CuentaDaoImpl implements CuentaDao{

	@Override
	public Cuenta obtenerCuentaPorId(int idCuenta) {
	    String query = "SELECT idTipoCuenta, fechaCreacion, numeroCuenta, cbu, saldo, estadoCuenta FROM cuentas where idCuenta= ?";     
	    Cuenta cuenta = new Cuenta();
	    
	    try (Connection conexion = Conexion.getConnection();
	         PreparedStatement statement = conexion.prepareStatement(query)) {
	        
	        statement.setInt(1, idCuenta);
	        
	        try (ResultSet resultSet = statement.executeQuery()) {

	            // Si se encuentra la cuenta, se crea el objeto.
	            if (resultSet.next()) {                
		        	TipoCuenta tipoCuenta = new TipoCuentaDaoImpl().obtenerTipoCuentaPorId(resultSet.getInt("idTipoCuenta"));		        		        		        	
		        	cuenta.setIdCuenta(idCuenta);
		            cuenta.setTipoCuenta(tipoCuenta);
		            cuenta.setFechaCreacion(resultSet.getDate("fechaCreacion"));
		            cuenta.setNumeroCuenta(resultSet.getLong("numeroCuenta"));	            
		            cuenta.setCbu(resultSet.getLong("cbu"));
		            cuenta.setSaldo(resultSet.getFloat("saldo"));
		            cuenta.setEstadoCuenta(resultSet.getBoolean("estadoCuenta"));
		            
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }	  

	    return cuenta;
	}

	@Override
	public ArrayList<Cuenta> listarCuentas() {
	    String query = "SELECT idCuenta, idTipoCuenta, fechaCreacion, numeroCuenta, cbu, saldo, estadoCuenta FROM cuentas where estado=1";
	    ArrayList<Cuenta> listaCuentas = new ArrayList<>();

	    try (Connection conexion = Conexion.getConnection();
	         PreparedStatement statement = conexion.prepareStatement(query);
	         ResultSet resultSet = statement.executeQuery()) {

	        while (resultSet.next()) {
	        	Cuenta cuenta = new Cuenta();
	        	
	        	TipoCuenta tipoCuenta= new TipoCuentaDaoImpl().obtenerTipoCuentaPorId(resultSet.getInt("idTipoCuenta"));
	        		        		        	
	        	cuenta.setIdCuenta(resultSet.getInt("idCuenta"));
	            cuenta.setTipoCuenta(tipoCuenta);
	            cuenta.setFechaCreacion(resultSet.getDate("fechaCreacion"));
	            cuenta.setNumeroCuenta(resultSet.getLong("numeroCuenta"));	            
	            cuenta.setCbu(resultSet.getLong("cbu"));
	            cuenta.setSaldo(resultSet.getFloat("saldo"));
	            cuenta.setEstadoCuenta(resultSet.getBoolean("estado"));
	      
	            listaCuentas.add(cuenta);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return listaCuentas;
	}

	@Override
	public boolean modificarCuenta(Cuenta cuenta) {
	    String query = "UPDATE cuentas SET idTipoCuenta = ?, saldo = ? WHERE idCuenta = ?";
	    
	    try (Connection conexion = Conexion.getConnection();
	         PreparedStatement statement = conexion.prepareStatement(query)) {
	        
	        // Asignación de parámetros para actualizar los datos
	        statement.setInt(1, cuenta.getTipoCuenta().getId());
	        statement.setFloat(2, cuenta.getSaldo());
	        statement.setInt(3, cuenta.getIdCuenta());
	        
	        // Ejecuta la actualización y verifica si fue exitosa
	        return statement.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public boolean agregarCuenta(Cuenta cuenta, int idCliente) {
	    String query = "INSERT INTO cuentas(idcliente, idTipoCuenta, fechaCreacion, numeroCuenta, cbu, saldo, estadoCuenta) "
	                 + "VALUES (?, ?, ?, ?, ?, ?, ?);";
	    
	    try (Connection conexion = Conexion.getConnection();
	         PreparedStatement statement = conexion.prepareStatement(query)) {

	        // Asignación de parámetros
	        statement.setInt(1, idCliente);
	        statement.setInt(2, cuenta.getTipoCuenta().getId());
	        statement.setDate(3, new java.sql.Date(cuenta.getFechaCreacion().getTime())); 
	        statement.setLong(4, cuenta.getNumeroCuenta()); 
	        statement.setLong(5, cuenta.getCbu()); 
	        statement.setFloat(6, cuenta.getSaldo()); 
	        statement.setBoolean(7, cuenta.getEstadoCuenta());

	        // Ejecuta la actualización y devuelve si al menos una fila fue afectada
	        int filas = statement.executeUpdate();
	        return filas > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public boolean bajaCuenta(int idCuenta) {
		String query = "UPDATE cuentas SET estadoCuenta = 0 WHERE idCuenta = ?";
		System.out.println("cccccccccc111111111");
	    try (Connection conexion = Conexion.getConnection();
	         PreparedStatement statement = conexion.prepareStatement(query)) {
	        statement.setInt(1, idCuenta);
	        System.out.println("ccccccc333333333");
	        return statement.executeUpdate() > 0;
	    } catch (Exception e) {
	    	System.out.println("cccccccc444444444");
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public long obtenerProximoCBU() {
	    String query = "SELECT MAX(cbu) FROM cuentas";
	    long proximoCBU = 1111111111111111111L;  

	    try (Connection conexion = Conexion.getConnection();
	         PreparedStatement statement = conexion.prepareStatement(query);
	         ResultSet resultSet = statement.executeQuery()) {

	        if (resultSet.next()) {
	            long maxCBU = resultSet.getLong(1);
	            if (!resultSet.wasNull()) {
	                proximoCBU = maxCBU + 1;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return proximoCBU;
	}
	
	
	@Override
	public long obtenerProximoNumeroCuenta() {
	    String query = "SELECT MAX(numeroCuenta) FROM cuentas"; 
	    long proximoNumeroCuenta = 1111111111111111111L;
	    
	    try (Connection conexion = Conexion.getConnection();
	         PreparedStatement statement = conexion.prepareStatement(query);
	         ResultSet resultSet = statement.executeQuery()) {

	        if (resultSet.next()) {
	            long maxNumeroCuenta = resultSet.getLong(1);
	            
	            if (!resultSet.wasNull()) {
	                proximoNumeroCuenta = maxNumeroCuenta + 1;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return proximoNumeroCuenta; 
	}
	
	public ArrayList<Cuenta> obtenerCuentasPorCliente(int idCliente) {
        ArrayList<Cuenta> listaCuentas = new ArrayList<>();
        String query = "SELECT idCuenta, idTipoCuenta, fechaCreacion, numeroCuenta, cbu, saldo, estadoCuenta "
                     + "FROM cuentas WHERE idcliente = ? AND estadoCuenta = 1"; 

        try (Connection conexion = Conexion.getConnection();
             PreparedStatement statement = conexion.prepareStatement(query)) {
            
            statement.setInt(1, idCliente);
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Cuenta cuenta = new Cuenta();
                    cuenta.setIdCuenta(resultSet.getInt("idCuenta"));
                    cuenta.setFechaCreacion(resultSet.getDate("fechaCreacion"));
                    cuenta.setNumeroCuenta(resultSet.getLong("numeroCuenta"));
                    cuenta.setCbu(resultSet.getLong("cbu"));
                    cuenta.setSaldo(resultSet.getFloat("saldo"));
                    cuenta.setEstadoCuenta(resultSet.getBoolean("estadoCuenta"));
                    
                    
                    TipoCuenta tipoCuenta = new TipoCuentaDaoImpl().obtenerTipoCuentaPorId(resultSet.getInt("idTipoCuenta"));
                    
                    cuenta.setTipoCuenta(tipoCuenta);
                    
                    listaCuentas.add(cuenta);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCuentas;
    }
	
	public void ejecutarSPTransferencia(long cbuOrigen, long cbuDestino, float monto, String concepto) throws SQLException
	{
		  try
		  {
			 Connection conexion = Conexion.getConnection();
			 CallableStatement cst = conexion.prepareCall("CALL spRealizarTransferencia"
			 		+ "(?,?,?,?)");			 
		 			 				 
			 cst.setLong(1, cbuDestino);
			 cst.setLong(2, cbuOrigen);
			 cst.setFloat(3, monto);
			 cst.setString(4, concepto);
			 
			 cst.execute();
			 conexion.close();
		  }
		  catch (SQLException e) {
			  e.printStackTrace();	
			  //ClienteSPException exc1 = new ClienteSPException();
			  throw e;
		  }
		  finally {
			  
		  }			
	}
}

