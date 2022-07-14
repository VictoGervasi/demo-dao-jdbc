package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao
{

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn)
	{

		this.conn = conn;
	}

	@Override
	public void insert(Department department)
	{

		PreparedStatement st = null;

		try
		{
			st = conn.prepareStatement("INSERT INTO department " + "(Id, Name) " + "Values " + "(?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, department.getId());
			st.setString(2, department.getName());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0)
			{
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next())
				{
					int id = rs.getInt(1);
					department.setId(id);
				}

			}
		}
		catch (SQLException e)
		{
			throw new DbException(e.getMessage());
		}
		finally
		{
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Department department)
	{

		PreparedStatement st = null;

		try
		{
			st = conn.prepareStatement("UPDATE department " + "SET Id = ?, Name = ? " + "WHERE Id = ?");

			st.setInt(1, department.getId());
			st.setString(2, department.getName());
			st.setInt(3, department.getId());
			
			st.executeUpdate();

		}
		catch (SQLException e)
		{
			throw new DbException(e.getMessage());
		}
		finally
		{
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id)
	{
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"DELETE FROM department "
					+ "WHERE Id = ?");
			st.setInt(1, id);
			st.executeUpdate();
		
	} 
		catch (SQLException e) 
		{
		throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}
	@Override
	public Department findById(Integer id)
	{
		PreparedStatement st = null;
		ResultSet rs = null;

		try
		{
			st = conn.prepareStatement(
					"SELECT department.* "
					+ "FROM department "
					+ "WHERE Id = ?");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				Department department = instantiateDepartment(rs);
				return department;
			}
			else {
				return null;
			}
								
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}	

	@Override
	public List<Department> findAll()
	{
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT department.* "
					+ "FROM department "
					+ "ORDER BY Id");
			
			rs = st.executeQuery();

			List<Department> departments = new ArrayList<>();

			while (rs.next()) {
				Department department = instantiateDepartment(rs);
				departments.add(department);
			}
			return departments;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
	private Department instantiateDepartment(ResultSet rs) throws SQLException
	{

		Department department = new Department();
		department.setId(rs.getInt("Id"));
		department.setName(rs.getString("Name"));

		return department;
	}

}
