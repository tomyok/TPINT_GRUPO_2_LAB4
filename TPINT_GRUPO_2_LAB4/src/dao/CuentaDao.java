package dao;
import java.util.ArrayList;
import entidades.Cuenta;

public interface CuentaDao {
	public boolean  agregarCuenta(Cuenta cuenta, int idCliente);
	public ArrayList<Cuenta> listarCuentas();
	public boolean modificarCuenta(Cuenta cuenta, int idCliente);
	public boolean bajaCuenta(int idCuenta);
	public Cuenta obtenerCuentaPorId(int idCuenta);
	
	public ArrayList<Cuenta> obtenerCuentasPorCliente(int idCliente);
}