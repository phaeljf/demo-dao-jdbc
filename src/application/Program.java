package application;

import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {

    static void main(String[] args) {

        Department departmentBooks = new Department(1, "Books");

        IO.println(departmentBooks);

        Seller seller1 = new Seller(1, "João", "joao@gmail.com", new Date() ,3500.00, departmentBooks);

        System.out.println(seller1);

    }


}
