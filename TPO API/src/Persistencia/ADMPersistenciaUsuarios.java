package Persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import Negocio.Usuario;

public class ADMPersistenciaUsuarios 
{	
	private static ADMPersistenciaUsuarios instancia;
	
	private ADMPersistenciaUsuarios()
	{
		
	}
	
	public static ADMPersistenciaUsuarios getInstancia()
	{
		if (instancia == null)
			instancia = new ADMPersistenciaUsuarios();
		return instancia;
	}
	
	public void altaUsuario(String usuario, String contrasena, String nombre,boolean idTipoUsuario, Date fechaNacimiento,String mail) throws Exception
	{
		PreparedStatement s;
		try {
			Connection con = DataAccess.getConexion().getInstanciaDB();
			int tipo;
			if(idTipoUsuario)
				tipo = 1;
			else
				tipo = 0;
			DateFormat df = new SimpleDateFormat("yyyy-M-d");
			String fechaParaSQL = df.format(fechaNacimiento);
			String laConsulta = "INSERT INTO USUARIO (usuario,contrasena,nombre,tipo,fechaNacimiento,mail,estado) VALUES ('" + usuario + "','" + contrasena + "','" + nombre + "'," + tipo + "," + java.sql.Date.valueOf(fechaParaSQL) + ",'" + mail + "',1)";
			s = con.prepareStatement(laConsulta);
			//statement stmtConsulta = laConexion.prepareStatement(laConsulta);
			//stmtConsulta.execute();
			s.execute();
			DataAccess.getConexion().cerrarConexion();
		}catch(Exception ex) {
			throw ex;
		}
	}
	
	public Usuario LoginUser(String usuario, String password) throws Exception
	{
		try
		{
			Connection con = DataAccess.getConexion().getInstanciaDB();
			String laConsulta = "select u.usuario,u.contrasena,u.nombre,u.fechaNacimiento,u.mail,u.estado,u.tipo from usuario u where usuario = '" + usuario + "' AND contrasena = '" + password + "'";
			Statement stmtConsulta = con.createStatement();
			ResultSet rs = stmtConsulta.executeQuery(laConsulta);
			if (!rs.next())
				throw new Exception("El usuario o contraseņa ingresada es incorrecta");	
						
			String user = rs.getString(1);
			String contrasena = rs.getString(2);
			String nombre = rs.getString(3);
			Date fecha_nac = rs.getDate(4);
			String mail = rs.getString(5);
			int estado = rs.getInt(6);
			int idTipoUsuario = rs.getInt(7);
			
			boolean estadoB;
			if(estado > 0)
				estadoB = true;
			else
				estadoB = false;
			
			boolean tipoDeUsuario;
			if(idTipoUsuario > 0)
				tipoDeUsuario = true;
			else
				tipoDeUsuario = false;
			
			Usuario u = new Usuario(nombre, user, contrasena, fecha_nac, estadoB, mail, tipoDeUsuario);			
			
			DataAccess.getConexion().cerrarConexion();
			
			return u;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public Vector<Usuario> buscarUsuarios() throws Exception
	{
		Vector<Usuario> v = new Vector<Usuario>();
		try
		{
			Connection con = DataAccess.getConexion().getInstanciaDB();
			PreparedStatement s = con.prepareStatement("select u.usuario,u.contrasena,u.nombre,u.tipo,u.fechaNacimiento,u.mail,u.estado from usuario u");		
			
			ResultSet result = s.executeQuery();
			
			while(result.next())
			{
				String user = result.getString(1);
				String contrasena = result.getString(2);
				String nombre = result.getString(3);
				int idTipoUsuario = result.getInt(4);
				//String codTipoUsuario = result.getString(5);
				Date fecha_nac = result.getDate(5);
				String mail = result.getString(6);
				int estado = result.getInt(7);
				boolean tipoUsuario;
				if(idTipoUsuario > 0)
					tipoUsuario = true;
				else
					tipoUsuario = false;
				
				Usuario u = new Usuario(nombre,user,contrasena,fecha_nac,estado==1,mail,tipoUsuario);
				
				v.add(u);
			}
			
			DataAccess.getConexion().cerrarConexion();
					
			return v;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public Vector<Usuario> buscarUsuariosParticipantes() throws Exception
	{
		Vector<Usuario> v = new Vector<Usuario>();
		try
		{
			Connection con = DataAccess.getConexion().getInstanciaDB();
			PreparedStatement s = con.prepareStatement("select u.usuario,u.contrasena,u.nombre,u.tipo,u.fechaNacimiento,u.mail,u.estado from usuario u where tipo = 0;");		
			
			ResultSet result = s.executeQuery();
			
			while(result.next())
			{
				String user = result.getString(1);
				String contrasena = result.getString(2);
				String nombre = result.getString(3);
				int idTipoUsuario = result.getInt(4);
				Date fecha_nac = result.getDate(5);
				String mail = result.getString(6);
				int estado = result.getInt(7);
				boolean tipoUsuario;
				if(idTipoUsuario > 0)
					tipoUsuario = true;
				else
					tipoUsuario = false;
				
				Usuario u = new Usuario(nombre,user,contrasena,fecha_nac,estado==1,mail,tipoUsuario);
				
				v.add(u);
			}
			
			DataAccess.getConexion().cerrarConexion();
					
			return v;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public void modificarUsuario(String usuario, String contrasena, String nombre,int tipoUsuario, Date fechaNacimiento,String mail) throws Exception
	{
		Connection con = DataAccess.getConexion().getInstanciaDB();
		PreparedStatement s;
		try
		{
			s = con.prepareStatement("UPDATE usuario SET contrasena = ?,nombre = ?,tipo = ?,fechaNacimiento = ?,mail = ? WHERE usuario = ?");
					
			s.setString(1, contrasena);
			s.setString(2, nombre);
			s.setInt(3, tipoUsuario);
			
			DateFormat df = new SimpleDateFormat("yyyy-M-d");
			String fechaParaSQL = df.format(fechaNacimiento);
			s.setDate(4, java.sql.Date.valueOf(fechaParaSQL));
			
			s.setString(5, mail);
			s.setString(6,usuario);
			
			s.execute();
			
			DataAccess.getConexion().cerrarConexion();
		}
		catch(Exception e) 
		{
			throw e;
		}
	}
	
	public void eliminarUsuario(String usuario) throws Exception
	{
		Connection con = DataAccess.getConexion().getInstanciaDB();
		PreparedStatement s;
		try
		{
			s = con.prepareStatement("UPDATE usuario SET estado = 0 where USUARIO = ?");
			s.setString(1,usuario);
			s.execute();
			
			DataAccess.getConexion().cerrarConexion();
		}
		catch(Exception e) 
		{
			throw e;
		}
	}
}
