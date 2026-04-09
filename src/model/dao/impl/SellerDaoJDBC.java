package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

        /*
            INSERT INTO seller
            (Name, Email, BirthDate, BaseSalary, DepartmentId)
            VALUES
            (?, ?, ?, ?, ?)
         */
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "INSERT INTO seller"
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
                    + "VALUES"
                    + " (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Erro ao inserir seller");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {
        /*

        SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?
        WHERE Id = ?
         */
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE seller "
                    +"SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                    +"WHERE Id = ? "
            );
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {
      /*
        DELETE FROM seller
        WHERE Id = ?
         */

        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "DELETE FROM seller "
                       +"WHERE Id = ? "
            );
            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) {
                throw new DbException("Erro ao deletar seller ID não encontrado");
            }

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){
                //criar um objeto departamento e vendedor temporario para inserir os dados da busca
                Department depTemporary = instantiateDeparment(rs);
                Seller sellerTemporary = instantiateSeller(rs, depTemporary);

                return sellerTemporary;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());

        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }


    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{

            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name"
            );

            rs = st.executeQuery();

            List<Seller> sellersList = new ArrayList<>();
            //Usando Map para controlar a não repetição do departamento
            Map<Integer, Department> map = new HashMap<>();

            while(rs.next()){
                //criar um objeto departamento e vendedor temporario para inserir os dados da busca

                //Testanto se departamento ja existe pegando o departamentoId e se não tiver ainda vai setar como nulo
                Department depTemporary = map.get(rs.getInt("DepartmentId"));

                //verifica se no map esta como null e se estiver, ou seja, sem um departamento setado, ai entrara e irá setar o departamento
                if (depTemporary == null){
                    depTemporary = instantiateDeparment(rs);
                    map.put(rs.getInt("DepartmentId"), depTemporary);
                }

                Seller sellerTemporary = instantiateSeller(rs, depTemporary);
                sellersList.add(sellerTemporary);
            }
            return sellersList;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());

        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department dep) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{

            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name"
            );

            st.setInt(1, dep.getId());
            rs = st.executeQuery();

            List<Seller> sellersList = new ArrayList<>();
            //Usando Map para controlar a não repetição do departamento
            Map<Integer, Department> map = new HashMap<>();

            while(rs.next()){
                //criar um objeto departamento e vendedor temporario para inserir os dados da busca

                //Testanto se departamento ja existe pegando o departamentoId e se não tiver ainda vai setar como nulo
                Department depTemporary = map.get(rs.getInt("DepartmentId"));

                //verifica se no map esta como null e se estiver, ou seja, sem um departamento setado, ai entrara e irá setar o departamento
                if (depTemporary == null){
                    depTemporary = instantiateDeparment(rs);
                    map.put(rs.getInt("DepartmentId"), depTemporary);
                }

                Seller sellerTemporary = instantiateSeller(rs, depTemporary);
                sellersList.add(sellerTemporary);
            }
            return sellersList;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());

        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }



    }


    //Metodos auxiliares de criação de objeto departamento e vendedor
    private Department instantiateDeparment(ResultSet rs) throws SQLException {
        Department depTemporary =  new Department();
        depTemporary.setId(rs.getInt("DepartmentId"));
        depTemporary.setName(rs.getString("DepName"));
        return depTemporary;
    }

    private Seller instantiateSeller(ResultSet rs, Department depTemporary) throws SQLException {
        Seller sellerTemporary = new Seller();
        sellerTemporary.setId(rs.getInt("Id"));
        sellerTemporary.setName(rs.getString("Name"));
        sellerTemporary.setEmail(rs.getString("Email"));
        sellerTemporary.setBaseSalary(rs.getDouble("BaseSalary"));
        sellerTemporary.setBirthDate(rs.getDate("BirthDate"));
        sellerTemporary.setDepartment(depTemporary);
        return sellerTemporary;
    }
}
