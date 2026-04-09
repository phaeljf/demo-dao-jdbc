package model.dao;

import model.entities.Department;

import java.util.List;

public interface DepartmentDao {

    /*
    insert(obj: Department): void
    update(obj: Department): void
    deleteById(id: Integer): void
    findById(id: Integer): Department
    findALl(): List<Department>
     */

    void insert(Department obj);

    void update(Department obj);

    void deleteById(Integer id);

    Department findById(Integer id);

    List<Department> findAll();


}
