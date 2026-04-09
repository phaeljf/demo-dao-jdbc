package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program2 {

    static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println();
        System.out.println("=== TESTE 1: Seller findById ===");


        System.out.println();
        System.out.println("=== TESTE 2: Seller findByDepartment ===");



        System.out.println();
        System.out.println("=== TESTE 3: All Seller findAll ===");


        System.out.println();
        System.out.println("=== TESTE 4: Seller Insert ===");


        System.out.println();
        System.out.println("=== TESTE 5: Seller update ===");



        System.out.println();
        System.out.println("=== TESTE 6: Seller delete ===");






        sc.close();
    }
}
