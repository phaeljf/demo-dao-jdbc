package model.dao;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SellerDao {

    /*
    insert(obj: Seller): void
    update(obj: Seller): void
    deleteById(id: Integer): void
    findById(id: Integer): Seller
    findALl(): List<Seller>
     */

    void insert(Seller obj);

    void update(Seller obj);

    void deleteById(Seller id);

    Seller findById(Integer id);

    List<Seller> findAll();

    List<Seller> findByDepartment(Department dep);


}
