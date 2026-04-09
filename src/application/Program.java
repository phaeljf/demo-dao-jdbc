package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {

    static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TESTE 1: Seller findById ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println();
        System.out.println("=== TESTE 2: Seller findByDepartment ===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller s : list) {
            System.out.println(s);
        }
        System.out.println();
        System.out.println("=== TESTE 3: All Seller findAll ===");
        list = sellerDao.findAll();
        for (Seller s : list) {
            System.out.println(s);
        }

        System.out.println();
        System.out.println("=== TESTE 4: Seller Insert ===");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
        sellerDao.insert(newSeller);
        System.out.println("Inserted new seller id = "+ newSeller.getId());

        System.out.println();
        System.out.println("=== TESTE 7: Seller update ===");
        seller = sellerDao.findById(1);
        seller.setName("Martha Waine");
        sellerDao.update(seller);
        System.out.println("Updated complete");


        System.out.println();
        System.out.println("=== TESTE 6: Seller delete ===");
        System.out.print("Entre com ID para teste de deleção:");
        int id = sc.nextInt();
        Seller sellerToDelete = sellerDao.findById(id);

        if(sellerDao.findById(id) == null) {
            System.out.println("ID inexistente");
        } else {
            sellerDao.deleteById(id);
            System.out.println("seller "+ sellerToDelete.getName() +" deleted");
        }





        sc.close();
    }
}
