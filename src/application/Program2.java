package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program2 {

    static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println();
        System.out.println("=== TESTE 1: Department findById ===");
        System.out.println("=== Enter Department Id ===");
        int id = sc.nextInt();
        Department departmentTemp = departmentDao.findById(id);
        System.out.println(departmentTemp);

        System.out.println();
        System.out.println("=== TESTE 3: All Seller findAll ===");
        List<Department> departments = departmentDao.findAll();
        for (Department department : departments) {
            System.out.println(department);
        }

        System.out.println();
        System.out.println("=== TESTE 4: Department Insert ===");
        System.out.println("=== Enter Department Name ===");
        String name = sc.next();
        departmentTemp = new Department(null, name);
        departmentDao.insert(departmentTemp);

        System.out.println();
        System.out.println("=== TESTE 5: Department update ===");
        System.out.println("=== Enter Department Id ===");
        id = sc.nextInt();
        System.out.println("=== Enter New Department Name ===");
        name = sc.next();
        departmentTemp = new Department(id,name);
        departmentDao.update(departmentTemp);


        System.out.println();
        System.out.println("=== TESTE 6: Department delete ===");
        System.out.println("=== Enter Department Id ===");
        id = sc.nextInt();
        departmentDao.deleteById(id);


        sc.close();
    }
}
