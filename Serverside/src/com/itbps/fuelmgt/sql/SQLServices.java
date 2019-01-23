package com.itbps.fuelmgt.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.sql.RowSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.itbps.fuelmgt.Dispatch;
import com.itbps.fuelmgt.DispatchList;
import com.itbps.fuelmgt.Employee;
import com.itbps.fuelmgt.EmployeeList;
import com.itbps.fuelmgt.Itemterminalpickup;
import com.itbps.fuelmgt.Itemterminalpickup;
import com.itbps.fuelmgt.ItemterminalpickupList;
import com.itbps.fuelmgt.TruckSchedule;
import com.itbps.fuelmgt.TruckScheduleList;
import com.itbps.user.security.UserSecurity;
import com.itbps.utils.GSONCreator;
import com.itbps.utils.IUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class SQLServices
{
	private static final Logger logger = LogManager.getLogger(SQLServices.class.getName());
	final JsonParser parser = new JsonParser();
	
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
	
	public String asJSON(Object apexObj)
	{
		String result = null;
		try
		{
			// Gson json = new Gson();
			Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
			
			JsonElement je = parser.parse(gson.toJson(apexObj));
			
			String json = gson.toJson(je);
			System.out.println(json);
			
			JSONObject jobj = new JSONObject(json);
			
			JSONObject job2 = new JSONObject();
			job2.put("data", jobj);
			
			result = job2.toString();
			System.out.println(result);
			return result;
			
		} catch(Exception _exx)
		{
			logger.error(IUtils.getPrintTrace(_exx));
			
		}
		
		return result;
		
	}
	
	public Itemterminalpickup createitempickup(String JSONStr)
	{
		Itemterminalpickup disp = null;
		Itemterminalpickup itemterminalpickup = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO itemterminalpickup (terminaldispatchdetailid, qtypickup, billoflading, pickupdate, createddate, lastupdated, lastupdateduser, verificationdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try
		{
			conn = getConnection();
			if (conn != null)
			{
				Gson gson = new Gson();
				disp = gson.fromJson(JSONStr, Itemterminalpickup.class);
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, disp.getItemterminalpickupid());
				pstmt.setDouble(2, disp.getQtypickup());
				pstmt.setString(3, disp.getBilloflading());
				pstmt.setDate(4, new java.sql.Date(disp.getPickupdate().getTime()));
				pstmt.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
				pstmt.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
				pstmt.setString(7, disp.getLastupdateduser());
				
				int recordid = pstmt.executeUpdate();
				
				itemterminalpickup = this.getItempickupById(recordid);
				
			}
		} catch(Exception _exx)
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
		return itemterminalpickup;
		
	}
	
	public ItemterminalpickupList getItempickuphByDispatchId(int id)
	{
		ItemterminalpickupList lst = new ItemterminalpickupList();
		StringBuffer sb = new StringBuffer();
		
		sb.append("select itp.itemterminalpickupid, itp.terminaldispatchdetailid, co.compartmentid, qtypickup, billoflading,  ");
		sb.append(
				"pickupdate, itp.createddate itempickup_createddate, itp.lastupdated, itp.lastupdateduser, verifierid, verificationdate, ");
		sb.append("tdd.fuelname, tdd.scheduledquantity, td.createddate dispatch_createddate, td.driverid,  td.disbatchid, td.terminalid, ");
		sb.append("td.truckname, td.scheduledate, co.compartment, em.nameId driver, em2.nameId dispatcher ");
		sb.append(
				"from itemterminalpickup itp, terminaldispatch td, compartment co, terminaldispatchdetail tdd, employee em, employee em2 ");
		sb.append("where td.disbatchid = ");
		sb.append(id);
		sb.append(" ");
		sb.append("and  tdd.terminaldispatchid = td.terminaldispatchid ");
		sb.append("and   itp.terminaldispatchdetailid = tdd.terminaldispatchdetailid ");
		sb.append("and    co.compartmentid    = tdd.compartmentid ");
		sb.append("and   td.driverid = em.id ");
		sb.append("and   td.disbatchid = em2.id ");
		
		Itemterminalpickup[] pd = getItempickupList(sb.toString());
		if (pd != null && pd.length > 0)
		{
			lst.setPickupList(pd);
		}
		return lst;
		
	}
	
	public ItemterminalpickupList getAllItempickups()
	{
		String json = "";
		StringBuffer sb = new StringBuffer();
		
		sb.append("select itp.itemterminalpickupid, itp.terminaldispatchdetailid, co.compartmentid, qtypickup, billoflading,  ");
		sb.append(
				"pickupdate, itp.createddate itempickup_createddate, itp.lastupdated, itp.lastupdateduser, verifierid, verificationdate, ");
		sb.append("tdd.fuelname, tdd.scheduledquantity, td.createddate dispatch_createddate, td.driverid,  td.disbatchid, td.terminalid, ");
		sb.append("td.truckname, td.scheduledate, co.compartment, em.nameId driver, em2.nameId dispatcher ");
		sb.append(
				"from itemterminalpickup itp, terminaldispatch td, compartment co, terminaldispatchdetail tdd, employee em, employee em2 ");
		sb.append("where  tdd.terminaldispatchid = td.terminaldispatchid ");
		sb.append("and   itp.terminaldispatchdetailid = tdd.terminaldispatchdetailid ");
		sb.append("and    co.compartmentid    = tdd.compartmentid ");
		sb.append("and   td.driverid = em.id ");
		sb.append("and   td.disbatchid = em2.id ");
		
		Itemterminalpickup[] pd = getItempickupList(sb.toString());
		ItemterminalpickupList lst = new ItemterminalpickupList();
		
		if (pd != null && pd.length > 0)
		{
			lst.setPickupList(pd);
			;
			// json = asJSON(lst);
		}
		return lst;
	}
	
	public ItemterminalpickupList getItempickupByDriverId(int id)
	{
		ItemterminalpickupList pList = new ItemterminalpickupList();
		String json = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select itp.itemterminalpickupid, itp.terminaldispatchdetailid, co.compartmentid, qtypickup, billoflading,  ");
		sb.append(
				"pickupdate, itp.createddate itempickup_createddate, itp.lastupdated, itp.lastupdateduser, verifierid, verificationdate, ");
		sb.append("tdd.fuelname, tdd.scheduledquantity, td.createddate dispatch_createddate, td.driverid,  td.disbatchid, td.terminalid, ");
		sb.append("td.truckname, td.scheduledate, co.compartment, em.nameId driver, em2.nameId dispatcher ");
		sb.append(
				"from itemterminalpickup itp, terminaldispatch td, compartment co, terminaldispatchdetail tdd, employee em, employee em2 ");
		sb.append("where td.driverid = ");
		sb.append(id);
		sb.append(" ");
		sb.append("and  tdd.terminaldispatchid = td.terminaldispatchid ");
		sb.append("and   itp.terminaldispatchdetailid = tdd.terminaldispatchdetailid ");
		sb.append("and    co.compartmentid    = tdd.compartmentid ");
		sb.append("and   td.driverid = em.id ");
		sb.append("and   td.disbatchid = em2.id ");
		
		Itemterminalpickup[] pd = getItempickupList(sb.toString());
		if (pd != null && pd.length > 0) pList.setPickupList(pd);
		
		return pList;
		
	}
	
	public Itemterminalpickup getItempickupById(int id)
	{
		Itemterminalpickup itemterminalpickup = null;
		StringBuffer sb = new StringBuffer();
		
		sb.append("select itp.itemterminalpickupid, itp.terminaldispatchdetailid, co.compartmentid, qtypickup, billoflading,  ");
		sb.append(
				"pickupdate, itp.createddate itempickup_createddate, itp.lastupdated, itp.lastupdateduser, verifierid, verificationdate, ");
		sb.append("tdd.fuelname, tdd.scheduledquantity, td.createddate dispatch_createddate, td.driverid,  td.disbatchid, td.terminalid, ");
		sb.append("td.truckname, td.scheduledate, co.compartment, em.nameId driver, em2.nameId dispatcher ");
		sb.append(
				"from itemterminalpickup itp, terminaldispatch td, compartment co, terminaldispatchdetail tdd, employee em, employee em2 ");
		sb.append("where itp.itemterminalpickupid = ");
		sb.append(id);
		sb.append(" ");
		sb.append("and  tdd.terminaldispatchdetailid = itp.terminaldispatchdetailid ");
		sb.append("and  td.terminaldispatchid = tdd.terminaldispatchid ");
		sb.append("and  co.compartmentid    = tdd.compartmentid ");
		sb.append("and   td.driverid = em.id ");
		sb.append("and   td.disbatchid = em2.id ");
		
		Itemterminalpickup[] pd = getItempickupList(sb.toString());
		if (pd != null && pd.length > 0)
		{
			itemterminalpickup = pd[0];
		}
		return itemterminalpickup;
		
	}
	
	private Itemterminalpickup[] getItempickupList(String sql)
	{
		Connection conn = null;
		ResultSet rset = null;
		Statement pstmt = null;
		List<Itemterminalpickup> list = new ArrayList<Itemterminalpickup>();
		ItemterminalpickupList dispList = new ItemterminalpickupList();
		Itemterminalpickup[] dlist = null;
		
		try
		{
			conn = getConnection();
			if (conn != null)
			{
				pstmt = conn.createStatement();
				// pstmt.setInt(1, id);
				
				rset = pstmt.executeQuery(sql);
				
				while (rset.next())
				{
					Itemterminalpickup pickup = new Itemterminalpickup();
					pickup.setBilloflading(rset.getString("billoflading"));
					pickup.setCompartment(rset.getString("compartment"));
					pickup.setCompartmentid(rset.getInt("compartmentid"));
					pickup.setItempickup_createddate(rset.getDate("itempickup_createddate"));
					pickup.setDriverid(rset.getInt("driverid"));
					pickup.setDisbatchid(rset.getInt("disbatchid"));
					pickup.setFuelname(rset.getString("fuelname"));
					pickup.setItemterminalpickupid(rset.getInt("itemterminalpickupid"));
					pickup.setLastupdated(rset.getDate("lastupdated"));
					pickup.setLastupdateduser(rset.getString("lastupdateduser"));
					pickup.setDispatcher(rset.getString("dispatcher"));
					pickup.setDriver(rset.getString("driver"));
					pickup.setPickupdate(rset.getDate("pickupdate"));
					pickup.setQtypickup(rset.getDouble("qtypickup"));
					pickup.setDispatch_createddate(rset.getDate("dispatch_createddate"));
					pickup.setScheduledate(rset.getDate("scheduledate"));
					pickup.setScheduledquantity(rset.getDouble("scheduledquantity"));
					pickup.setTerminalid(rset.getString("terminalid"));
					pickup.setTruckname(rset.getString("truckname"));
					pickup.setVerificationdate(rset.getDate("verificationdate"));
					pickup.setVerifierid(rset.getBoolean("verifierid"));
					
					list.add(pickup);
					
				}
				
				if (list.size() > 0)
				{
					dlist = new Itemterminalpickup[list.size()];
					int count = 0;
					for (Itemterminalpickup dis : list)
					{
						dlist[count] = dis;
						count++;
					}
					dispList.setPickupList(dlist);
				}
				return dlist;
				
			}
			
		} catch(Exception _exx)
		{
			logger.error(IUtils.getPrintTrace(_exx));
			return null;
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
		return dlist;
	}
	
	public String getDispatchById(int pickupdispatchId)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(
				"select pk.pickupdispatchId, pk.driverid, em.nameId driver, disbatchid, em2.nameId dispatcher, terminalid, pk.truckname, scheduledate, createddate ");
		sb.append("from pickupdispatch pk, driver dr, truck tr, employee em, employee em2 ");
		sb.append("where pk.pickupdispatchId = ");
		sb.append(pickupdispatchId);
		sb.append(" and em.id = pk.driverid and   tr.truckname  = pk.truckname ");
		sb.append("and em2.id  = pk.disbatchid");
		
		return asJSON(getDisptachList(sb.toString()));
		
	}
	
	public String getDispatchByDriver(int driverid)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(
				"select pk.pickupdispatchId, pk.driverid, em.nameId driver, disbatchid, em2.nameId dispatcher, terminalid, pk.truckname, scheduledate, createddate ");
		sb.append("from pickupdispatch pk, driver dr, truck tr, employee em, employee em2 ");
		sb.append("where pk.driverid = ");
		sb.append(driverid);
		sb.append(" and em.id = pk.driverid and   tr.truckname  = pk.truckname ");
		sb.append("and em2.id  = pk.disbatchid");
		
		return asJSON(getDisptachList(sb.toString()));
		
	}
	
	public String getDispatch(int dispatchid)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(
				"select pk.pickupdispatchId, pk.driverid, em.nameId driver, disbatchid, em2.nameId dispatcher, terminalid, pk.truckname, scheduledate, createddate ");
		sb.append("from pickupdispatch pk, driver dr, truck tr, employee em, employee em2 ");
		sb.append("where pk.pickupdispatchId = ");
		sb.append(dispatchid);
		sb.append(" and em.id = pk.driverid and   tr.truckname  = pk.truckname ");
		sb.append("and em2.id  = pk.disbatchid");
		
		return asJSON(getDisptachList(sb.toString()));
		
	}
	
	public TruckSchedule updatetruckpickupschedule(TruckSchedule disp)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		sb.append("UPDATE truckpickupschedule ");
		sb.append("SET scheduledquantity=?, ");
		sb.append("fuelname=? ");
		sb.append("WHERE  pickupdispatchid = ? AND compartmentid=? ");
		
		try
		{
			conn = getConnection();
			if (conn != null)
			{
				
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setDouble(1, disp.getScheduledquantity());
				pstmt.setString(2, disp.getFuelname());
				pstmt.setInt(3, disp.getPickupdispatchid());
				pstmt.setInt(4, disp.getCompartmentid());
				int recordid = pstmt.executeUpdate();
			}
		} catch(Exception _exx)
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
		return disp;
		
	}
	
	public String updateDispatch(String JSONStr)
	{
		Dispatch disp = null;
		String resultJSON = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE apexpms_apex.pickupdispatch ");
		sb.append("set driverid = ?, ");
		sb.append(" disbatchid = ?,");
		sb.append("terminalid = ?, ");
		sb.append("truckname = ?,");
		sb.append("scheduledate = ");
		sb.append("where pickupdispatchId=? ");
		
		try
		{
			conn = getConnection();
			if (conn != null)
			{
				Gson gson = new Gson();
				disp = gson.fromJson(JSONStr, Dispatch.class);
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, disp.getDriverid());
				pstmt.setInt(2, disp.getDisbatchid());
				pstmt.setString(3, disp.getTerminalid());
				pstmt.setString(4, disp.getTruckname());
				pstmt.setDate(5, new java.sql.Date(disp.getScheduledate().getTime()));
				pstmt.setInt(6, disp.getPickupdispatchId());
				
				int recordid = pstmt.executeUpdate();
				TruckSchedule[] tShed = disp.getTruckSchedule();
				for (TruckSchedule sched : tShed)
				{
					TruckSchedule tched = gson.fromJson(JSONStr, TruckSchedule.class);
					tched.setPickupdispatchid(recordid);
					updatetruckpickupschedule(tched);
					
				}
				resultJSON = this.getDispatchById(recordid);
				
			}
		} catch(Exception _exx)
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
		return resultJSON;
		
	}
	
	public boolean deleteDispatch(String JSONStr)
	{
		Dispatch disp = null;
		String resultJSON = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM pickupdispatch WHERE pickupdispatchId=?";
		
		try
		{
			conn = getConnection();
			if (conn != null)
			{
				Gson gson = new Gson();
				disp = gson.fromJson(JSONStr, Dispatch.class);
				
				TruckSchedule[] tShed = disp.getTruckSchedule();
				for (TruckSchedule sched : tShed)
				{
					TruckSchedule tched = gson.fromJson(JSONStr, TruckSchedule.class);
					deletetruckpickupschedule(tched);
					
				}
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, disp.getPickupdispatchId());
				
				int recordid = pstmt.executeUpdate();
				return true;
				
			}
		} catch(Exception _exx)
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
		return true;
		
	}
	
	public String createDispatch(String JSONStr)
	{
		Dispatch disp = null;
		String resultJSON = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO pickupdispatch (driverid, disbatchid, terminalid, truckname, scheduledate) " + "VALUES (?, ?, ?, ?, ?)";
		
		try
		{
			conn = getConnection();
			if (conn != null)
			{
				Gson gson = new Gson();
				disp = gson.fromJson(JSONStr, Dispatch.class);
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, disp.getDisbatchid());
				pstmt.setInt(2, disp.getDisbatchid());
				pstmt.setString(3, disp.getTerminalid());
				pstmt.setString(4, disp.getTruckname());
				pstmt.setDate(5, new java.sql.Date(disp.getScheduledate().getTime()));
				
				int recordid = pstmt.executeUpdate();
				TruckSchedule[] tShed = disp.getTruckSchedule();
				for (TruckSchedule sched : tShed)
				{
					TruckSchedule tched = gson.fromJson(JSONStr, TruckSchedule.class);
					tched.setPickupdispatchid(recordid);
					createtruckpickupschedule(tched);
					
				}
				resultJSON = this.getDispatchById(recordid);
				
			}
		} catch(Exception _exx)
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
		return resultJSON;
		
	}
	
	public TruckSchedule createtruckpickupschedule(TruckSchedule disp)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO truckpickupschedule (pickupdispatchid, compartmentid, scheduledquantity, fuelname) "
				+ "VALUES (?, ?, ?, ?);)";
		
		try
		{
			conn = getConnection();
			if (conn != null)
			{
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, disp.getPickupdispatchid());
				pstmt.setInt(2, disp.getCompartmentid());
				pstmt.setDouble(3, disp.getScheduledquantity());
				pstmt.setString(4, disp.getFuelname());
				int recordid = pstmt.executeUpdate();
			}
		} catch(Exception _exx)
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
		return disp;
		
	}
	
	public boolean deletetruckpickupschedule(TruckSchedule disp)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM truckpickupschedule WHERE  pickupdispatchid=? AND compartmentid=?) ";
		
		try
		{
			conn = getConnection();
			if (conn != null)
			{
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, disp.getPickupdispatchid());
				pstmt.setInt(2, disp.getCompartmentid());
				int recordid = pstmt.executeUpdate();
			}
		} catch(Exception _exx)
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
		return true;
		
	}
	
	private DispatchList getDisptachList(String sql)
	{
		Connection conn = null;
		ResultSet rset = null;
		Statement pstmt = null;
		List<Dispatch> list = new ArrayList<Dispatch>();
		DispatchList dispList = new DispatchList();
		Dispatch[] dlist = null;
		
		try
		{
			conn = getConnection();
			if (conn != null)
			{
				pstmt = conn.createStatement();
				// pstmt.setInt(1, id);
				
				rset = pstmt.executeQuery(sql);
				
				while (rset.next())
				{
					Dispatch disp = new Dispatch();
					disp.setCreateddate(rset.getDate("createddate"));
					disp.setDisbatchid(rset.getInt("disbatchid"));
					disp.setDispatcher(rset.getString("dispatcher"));
					disp.setDriver(rset.getString("driver"));
					disp.setDriverid(rset.getInt("driverid"));
					disp.setPickupdispatchId(rset.getInt("pickupdispatchId"));
					disp.setScheduledate(rset.getDate("scheduledate"));
					disp.setTerminalid(rset.getString("terminalid"));
					disp.setTruckname(rset.getString("truckname"));
					disp.setTruckSchedule(getTruckSchedule(disp.getPickupdispatchId()));
					list.add(disp);
					
				}
				
				if (list.size() > 0)
				{
					dlist = new Dispatch[list.size()];
					int count = 0;
					for (Dispatch dis : list)
					{
						dlist[count] = dis;
						count++;
					}
					dispList.setDispatchList(dlist);
				}
				
			}
			
		} catch(Exception _exx)
		{
			logger.error(IUtils.getPrintTrace(_exx));
			return null;
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
		return dispList;
	}
	
	private TruckSchedule[] getTruckSchedule(int pickupdispatchid)
	{
		String sql2 = "select pickupdispatchid, compartmentid, scheduledquantity, fuelname, createddate from truckpickupschedule";
		
		StringBuffer sb = new StringBuffer();
		sb.append("select pickupdispatchid, compartmentid, scheduledquantity, fuelname, createddate from truckpickupschedule ");
		sb.append("where pickupdispatchid = ");
		sb.append(pickupdispatchid);
		
		return queryTruchSchedule(sb.toString()).getTruckSchedule();
		
	}
	
	private TruckScheduleList queryTruchSchedule(String sql)
	{
		
		Connection conn = null;
		ResultSet rset = null;
		Statement pstmt = null;
		TruckScheduleList tschedList = new TruckScheduleList();
		TruckSchedule[] dlist = null;
		List<TruckSchedule> list = new ArrayList<TruckSchedule>();
		try
		{
			conn = getConnection();
			if (conn != null)
			{
				pstmt = conn.createStatement();
				
				rset = pstmt.executeQuery(sql);
				
				while (rset.next())
				{
					TruckSchedule tsched = new TruckSchedule();
					tsched.setCompartmentid(rset.getInt("compartmentid"));
					tsched.setCreateddate(rset.getDate("createddate"));
					tsched.setFuelname(rset.getString("fuelname"));
					tsched.setPickupdispatchid(rset.getInt("pickupdispatchid"));
					tsched.setScheduledquantity(rset.getDouble("scheduledquantity"));
					list.add(tsched);
					
				}
				
				if (list.size() > 0)
				{
					dlist = new TruckSchedule[list.size()];
					int count = 0;
					for (TruckSchedule dis : list)
					{
						dlist[count] = dis;
						count++;
					}
					tschedList.setTruckSchedule(dlist);
				}
				
			}
			
		} catch(Exception _exx)
		{
			logger.error(IUtils.getPrintTrace(_exx));
			return null;
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
		return tschedList;
	}
	
	public String addEmployee(String jsonEmp)
	{
		String result = null;
		try
		{
			Gson json = new Gson();
			
			Employee emp = this.addEmployee(json.fromJson(jsonEmp, Employee.class));
			
			result = new JSONObject().put("data", json.toJson(emp)).toString();
		} catch(Exception _exx)
		{
			logger.error(IUtils.getPrintTrace(_exx));
			
		}
		
		return result;
		
	}
	
	public UserSecurity createEmployee(UserSecurity emp)
	{
		logger.info("addEmployee Started...");
		String sql = "INSERT INTO apexpms_apex.employee (nameId, firstname, lastname, password) VALUES (?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		Employee rtnEmp = null;
		UserSecurity newsec = null;
		try
		{
			conn = getConnection();
			if (conn != null)
			{
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, emp.getUserId());
				pstmt.setString(2, emp.getFirstName());
				pstmt.setString(3, emp.getLastName());
				pstmt.setString(4, emp.getPassword());
				// pstmt.setString(5, emp.getToken() );
				//
				int id = pstmt.executeUpdate();
				rtnEmp = getEmployee(emp.getUserId());
				newsec = new UserSecurity();
				newsec.setFirstName(rtnEmp.getFirstName());
				newsec.setLastName(rtnEmp.getLastName());
				newsec.setId(rtnEmp.getId());
				newsec.setRole(rtnEmp.getRole());
				newsec.setPassword(rtnEmp.getPassword());
				newsec.setUserId(rtnEmp.getNameid());
				
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
		
		return newsec;
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
				if (emp.getDateofhire() != null) pstmt.setDate(10, new java.sql.Date(emp.getDateofhire().getTime()));
				else pstmt.setDate(10, null);
				
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
				
				pstmt.setString(1, emp.getFirstName());
				pstmt.setString(2, emp.getLastName());
				pstmt.setString(3, emp.getPassword());
				pstmt.setString(4, emp.getAddress1());
				pstmt.setString(5, emp.getAddress2());
				pstmt.setString(6, emp.getCity());
				pstmt.setString(7, emp.getState());
				pstmt.setString(8, emp.getZipcode());
				if (emp.getDateofhire() != null) pstmt.setDate(9, new java.sql.Date(emp.getDateofhire().getTime()));
				else pstmt.setDate(9, null);
				
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
	
	public String getEmployeeasJSON(int id)
	{
		String result = null;
		try
		{
			
			Employee emp = getEmployee(id);
			
			result = this.asJSON(emp);
			
			logger.debug(result);
			
		} catch(Exception _exx)
		{
			logger.error(IUtils.getPrintTrace(_exx));
			
		}
		
		return result;
		
	}
	
	public EmployeeList getEmployees()
	{
		logger.info("getEmployees(); Started....");
		
		String sql = "SELECT * FROM apexpms_apex.employee";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		Employee employee = null;
		Employee[] empList = null;
		List<Employee>list = new ArrayList<Employee>();
		EmployeeList empls = new EmployeeList();
		
		try
		{
			conn = getConnection();
			
			if (conn != null)
			{
				pstmt = conn.prepareStatement(sql);
				
				rset = pstmt.executeQuery();
				while (rset.next())
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
					try
					{
					   employee.setDateofhire(rset.getDate("dateofhire"));
					}
					catch(Exception _exx)
					{
						//
					}
					employee.setState(rset.getString("state"));
					employee.setSsn(rset.getString("ssn"));
					employee.setActive(rset.getBoolean("active"));
					employee.setRole(getEmployeeRole(employee.getNameid()));
					list.add(employee);
				}
				
				if (list.size() > 0)
				{
					empList = new Employee[list.size()];
					int count = 0;
					for (Employee emp : list)
					{
						empList[count] = emp;
						count++;
					}
					empls.setEmployees(empList);
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
		
		return empls;
		
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
	
	public static UserSecurity getUserForSecurity(String id)
	{
		logger.info("getEmployee(int); Started....");
		
		String sql = "SELECT * FROM apexpms_apex.employee WHERE nameid= ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		UserSecurity employee = null;
		
		try
		{
			conn = getConnection();
			
			if (conn != null)
			{
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				
				rset = pstmt.executeQuery();
				if (rset.next())
				{
					employee = new UserSecurity();
					employee.setId(rset.getInt("id"));
					employee.setPassword(rset.getString("password"));
					employee.setUserId(rset.getString("nameId"));
					
					employee.setRole(getEmployeeRole(employee.getUserId()));
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
	
	public Employee getEmployee(String loginid)
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
		
		return null;
		
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}
	
}
