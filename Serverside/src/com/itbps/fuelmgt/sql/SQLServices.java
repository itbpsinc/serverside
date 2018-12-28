package com.itbps.fuelmgt.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.itbps.fuelmgt.Employee;

public class SQLServices {

	public Connection getConnection() throws Exception {
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource datasource = (DataSource) ctx.lookup("jdbc/apexdb");
			if (datasource != null)
				conn = datasource.getConnection();

		} catch (Exception _exx) {
			_exx.printStackTrace();
		}
		return conn;
	}

	public Employee getEmployee(String loginid) {
		String sql = "SELECT * FROM apexpms_apex.employee WHERE nameId= ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		Employee employee = null;

		try {
			conn = this.getConnection();

			if (conn != null) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, loginid);

				rset = pstmt.executeQuery();
				if (rset.next()) {
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
				}
			}
		} catch (Exception _exx) {
			_exx.printStackTrace();
		} finally {
			try {
				if (rset != null && !rset.isClosed()) {
					rset.close();
					rset = null;
				}
			} catch (Exception _ex) {
				// ignore
			}
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
					pstmt = null;
				}
			} catch (Exception _ex) {
				// ignore
			}
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
					conn = null;
				}
			} catch (Exception _ex) {
				// ignore
			}

		}

		return employee;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
