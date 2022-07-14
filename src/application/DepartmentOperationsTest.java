package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentOperationsTest
{

	public static void main(String[] args)
	{

		Scanner sc = new Scanner(System.in);

		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

		//TESTES DE OPERAÇÕES EM DEPARTAMENT;
		System.out.println("=== TEST 1: department findById ===");
		Department department = departmentDao.findById(2);
		System.out.println(department);

		System.out.println("=== TEST 2: department findAll ===");
		List<Department> departmentList = departmentDao.findAll();

		departmentList.forEach(dL -> System.out.println(dL));
		
		
		//Duplicate primaryKey Id
		/*System.out.println("=== TEST 3: department insert ===");		
		Department newDepartment = new Department(5, "dice");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted! New id = " + newDepartment.getId());*/

		System.out.println("=== TEST 4: department update ===");
		department = departmentDao.findById(6);
		department.setName("swords");
		departmentDao.update(department);
		System.out.println("Update Completed!");

		System.out.println("=== TEST 5: department delete ===");
		System.out.println("Enter id for delete test");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Delete completed");

		sc.close();

	}

}
