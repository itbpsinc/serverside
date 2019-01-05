package com.itbps.fuelmgt.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.itbps.fuelmgt.Employee;
import com.itbps.utils.IUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SQLServices
{
	private static final Logger logger = LogManager.getLogger(SQLServices.class.getName());
	
	public static Connection getConnection() throws Exception
	{
		Connection conn = null;
		try
		{
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			DataSource datasource = (DataSource) envContext.lookup("jdbc/apexdb");
			if (datasource != null) conn = datasource.getConnection();
			
		} catch(Exception _exx)
		{
			_exx.printStackTrace();
		}
		return conn;
	}
	
	public Employee addEmployee(Employee emp)
	{
		logger.info("addEmployee Started...");
		String sql = "INSERT INTO apexpms_apex.employee (nameId, firstname, lastname, password, address1, address2, city, state, zipcode, dateofhire, ssn) VALUES (?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		Employee rtnEmp = null;
		
		try
		{
			conn = getConnection();
			if (conn != null)
			{
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, emp.getNameid());
				pstmt.setString(2, emp.getFirstName());
				pstmt.setString(3, emp.getLastName());
				pstmt.setString(4, emp.getPassword());
				pstmt.setString(5, emp.getAddress1());
				pstmt.setString(6, emp.getAddress2());
				pstmt.setString(7, emp.getCity());
				pstmt.setString(8, emp.getState());
				pstmt.setString(9, emp.getZipcode());
				if (emp.getDateofhire() != null)
					pstmt.setDate(10, new java.sql.Date(emp.getDateofhire().getTime()));
				else
					pstmt.setDate(10, null);
				
				pstmt.setString(11, emp.getSsn());
				
				int id = pstmt.executeUpdate();
				rtnEmp = getEmployee(id);
				logger.info("addEmployee Processing completed....");
				
			}
		}
		
		catch(Exception _exx)
		{
			logger.error(IUtils.getPrintTrace(_exx));
			return null;
			
		} finally
		{
			
			try
			{
				if (pstmt != null && !pstmt.isClosed())
				{
					pstmt.close();
					pstmt = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			try
			{
				if (conn != null && !conn.isClosed())
				{
					conn.close();
					conn = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			
		}
		
		return rtnEmp;
	}
	
	
	public boolean deleteEmployee(int id)
	{
		logger.info("deleteEmployee Started...");
		String sql = "DELETE FROM apexpms_apex.employee WHERE  id=?";
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = getConnection();
			if (conn != null)
			{
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				int idkey = pstmt.executeUpdate();
				result = true;
				logger.info("deleteEmployee Processing completed....");
				
			}
		}
		
		catch(Exception _exx)
		{
			logger.error(IUtils.getPrintTrace(_exx));
			return false;
			
		} finally
		{
			
			try
			{
				if (pstmt != null && !pstmt.isClosed())
				{
					pstmt.close();
					pstmt = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			try
			{
				if (conn != null && !conn.isClosed())
				{
					conn.close();
					conn = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			
		}
		
		return result;
	}
	
	
	public Employee updateEmployee(Employee emp)
	{
		logger.info("updateEmployee Started...");
		String sql = "UPDATE apexpms_apex.employee SET firstName=?, lastName=?, password=?, address1=?, address2=?, city=?, state=?, zipcode=?, dateofhire=? WHERE  id=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		Employee rtnEmp = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			conn = getConnection();
			if (conn != null)
			{
				pstmt = conn.prepareStatement(sql);
				;
			    
				pstmt.setString(1, emp.getFirstName());
				pstmt.setString(2, emp.getLastName());
				pstmt.setString(3, emp.getPassword());
				pstmt.setString(4, emp.getAddress1());
				pstmt.setString(5, emp.getAddress2());
				pstmt.setString(6, emp.getCity());
				pstmt.setString(7, emp.getState());
				pstmt.setString(8, emp.getZipcode());
				if (emp.getDateofhire() != null)
					pstmt.setDate(9, new java.sql.Date(emp.getDateofhire().getTime()));
				else
					pstmt.setDate(9, null);
				
				pstmt.setString(10, emp.getSsn());
				pstmt.setInt(11, emp.getId());
				
				int id = pstmt.executeUpdate();
				rtnEmp = getEmployee(id);
				logger.info("updateEmployee Processing completed....");
				
			}
			logger.info("updateEmployee Completed...");
		}
		
		catch(Exception _exx)
		{
			logger.error(IUtils.getPrintTrace(_exx));
			return null;
			
		} finally
		{
			
			try
			{
				if (pstmt != null && !pstmt.isClosed())
				{
					pstmt.close();
					pstmt = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			try
			{
				if (conn != null && !conn.isClosed())
				{
					conn.close();
					conn = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			
		}
		
		return rtnEmp;
	}
	
	
	public static Employee getEmployee(int id)
	{
		logger.info("getEmployee(int); Started....");
		
		String sql = "SELECT * FROM apexpms_apex.employee WHERE id= ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		Employee employee = null;
		
		try
		{
			conn = getConnection();
			
			if (conn != null)
			{
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				
				rset = pstmt.executeQuery();
				if (rset.next())
				{
					employee = new Employee();
					employee.setId(rset.getInt("id"));
					employee.setAddress1(rset.getString("address1"));
					employee.setAddress2(rset.getString("address2"));
					employee.setNameid(rset.getString("nameid"));
					employee.setCity(rset.getString("city"));
					employee.setFirstName(rset.getString("firstname"));
					employee.setLastName(rset.getString("lastname"));
					employee.setPassword(rset.getString("password"));
					employee.setDateofhire(rset.getDate("dateofhire"));
					employee.setState(rset.getString("state"));
					employee.setSsn(rset.getString("ssn"));
					employee.setActive(rset.getBoolean("active"));
					employee.setRole(getEmployeeRole(employee.getNameid()));
				}
			}
			logger.info("getEmployee(int); Completed....");
		} catch(Exception _exx)
		{
			logger.error(IUtils.getPrintTrace(_exx));
			
		} finally
		{
			try
			{
				if (rset != null && !rset.isClosed())
				{
					rset.close();
					rset = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			try
			{
				if (pstmt != null && !pstmt.isClosed())
				{
					pstmt.close();
					pstmt = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			try
			{
				if (conn != null && !conn.isClosed())
				{
					conn.close();
					conn = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			
		}
		
		return employee;
		
	}
	
	public static Employee getEmployee(String loginid)
	{
		logger.info("getEmployee(Strng); Started....");
		
		String sql = "SELECT * FROM apexpms_apex.employee WHERE nameId= ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		Employee employee = null;
		
		try
		{
			conn = getConnection();
			
			if (conn != null)
			{
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, loginid);
				
				rset = pstmt.executeQuery();
				if (rset.next())
				{
					employee = new Employee();
					employee.setAddress1(rset.getString("address1"));
					employee.setAddress2(rset.getString("address2"));
					employee.setNameid(rset.getString("nameid"));
					employee.setCity(rset.getString("city"));
					employee.setFirstName(rset.getString("firstname"));
					employee.setLastName(rset.getString("lastname"));
					employee.setPassword(rset.getString("password"));
					employee.setDateofhire(rset.getDate("dateofhire"));
					employee.setState(rset.getString("state"));
					employee.setSsn(rset.getString("ssn"));
					employee.setActive(rset.getBoolean("active"));
					employee.setRole(getEmployeeRole(employee.getNameid()));
				}
			}
			logger.info("getEmployee(String); Completed....");
		} catch(Exception _exx)
		{
			_exx.printStackTrace();
		} finally
		{
			try
			{
				if (rset != null && !rset.isClosed())
				{
					rset.close();
					rset = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			try
			{
				if (pstmt != null && !pstmt.isClosed())
				{
					pstmt.close();
					pstmt = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			try
			{
				if (conn != null && !conn.isClosed())
				{
					conn.close();
					conn = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			
		}
		
		return employee;
		
	}
	
	private static String getEmployeeRole(String loginid)
	{
		String sql = "select role from apexpms_apex.userrole WHERE  nameId=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		Employee employee = null;
		
		try
		{
			conn = getConnection();
			
			if (conn != null)
			{
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, loginid);
				
				rset = pstmt.executeQuery();
				if (rset.next()) return rset.getString("role");
				
			}
		} catch(Exception _exx)
		{
			logger.error(IUtils.getPrintTrace(_exx));
			
		} 
		finally
		{
			try
			{
				if (rset != null && !rset.isClosed())
				{
					rset.close();
					rset = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			try
			{
				if (pstmt != null && !pstmt.isClosed())
				{
					pstmt.close();
					pstmt = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			try
			{
				if (conn != null && !conn.isClosed())
				{
					conn.close();
					conn = null;
				}
			} catch(Exception _ex)
			{
				// ignore
			}
			
		}
		
		return null;
		
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
