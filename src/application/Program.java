package application;

import model.entities.Department;

public class Program {

    static void main(String[] args) {

        Department department = new Department(1, "Books");

        IO.println(department);

    }


}
