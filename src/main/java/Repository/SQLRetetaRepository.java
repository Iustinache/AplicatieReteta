package Repository;

import Domain.Reteta;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQLRetetaRepository extends RepoMemory<Reteta> {
    Connection connection;
    //change URL here to relative path or your (absolute) path
    String db_url = "jdbc:sqlite:masa.db";

    public SQLRetetaRepository() {
        openConnection();
        createTable();
        //initReteteTable();
        loadData();
    }

    private void loadData() {
        entities.addAll(this.findAll());
    }


    private void initReteteTable() {
        List<Reteta> reteteList = new ArrayList<>();

        reteteList.add(new Reteta(1,"Chiftelute de linte", 50, "linte; ulei de masline; salota; usturoi; oua; condimemte"));
        reteteList.add(new Reteta(2,"Pizza", 45, "drojdie; apa; ulei de masline; sare; sos de rosii; mozarella"));
        reteteList.add(new Reteta(3,"Tarta Lorraine", 60, "oua; unt; faina; apa; smantana; lapte; Emmentaller"));
        reteteList.add(new Reteta(4,"Quesadilla", 35, "carne de pui; porumb; sos tzatziki; ceapa; condimente"));
        reteteList.add(new Reteta(5,"Supa de legume", 40, "mix de legume; usturoi; telina; ceapa; apa; rosii"));

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO retete VALUES (?,?,?,?);")) {
            for (Reteta r : reteteList) {
                statement.setInt(1, r.getId());
                statement.setString(2, r.getNume());
                statement.setInt(3, r.getMinute());
                statement.setString(4, r.getIngrediente());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void openConnection() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(db_url);
        try {
            if (connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();
            }
        } catch (SQLException e) {
            System.out.println("eroare la crearea conexiuni" + e);
        }
    }

    private void createTable() {
        String s = "Create Table if not exists retete( id_r int, nume varchar(30), timpGatire int, listaIngrediente varchar(100), PRIMARY KEY (id_r) )";
        try {

            Statement statement = connection.createStatement();
            boolean execution_result = statement.execute(s);
            System.out.println("Execution result from createTable()" + execution_result);
        } catch (SQLException e) {
            System.out.println("eroare la crearea tabelei retete" + e);
        }
    }

    @Override
    public void add(Reteta r) throws RepositoryException {
        super.add(r);
        String s = "INSERT INTO retete VALUES (?,?,?,?);";
        try {
            PreparedStatement add_statement = connection.prepareStatement(s);

            add_statement.setInt(1, r.getId());
            add_statement.setString(2, r.getNume());
            add_statement.setInt(3, r.getMinute());
            add_statement.setString(4, r.getIngrediente());

            add_statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }

    }

    @Override
    public void removeById(int id) {
        super.removeById(id);
        String s = "DELETE FROM retete WHERE id_r=?";
        try (PreparedStatement remove_statement = connection.prepareStatement(s)) {
            remove_statement.setInt(1, id);
            remove_statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public List<Reteta> findAll() {
        List<Reteta> resultList = new ArrayList<>();
        String s = "SELECT * FROM retete";
        try (PreparedStatement getAllSstatement = connection.prepareStatement(s)) {
            ResultSet result = getAllSstatement.executeQuery();
            while (result.next()) {
                Reteta r = new Reteta(result.getInt("id_r"), result.getString("nume"),
                        result.getInt("timpGatire"), result.getString("listaIngrediente"));
                resultList.add(r);
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}