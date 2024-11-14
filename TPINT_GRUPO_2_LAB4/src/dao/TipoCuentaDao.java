package dao;
import java.util.ArrayList;
import entidades.TipoCuenta;

public interface TipoCuentaDao {
	public ArrayList<TipoCuenta> listarTiposCuenta();
	public TipoCuenta obtenerTipoCuentaPorId(int id);
}
