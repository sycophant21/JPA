import com.Managers.*;
import com.domain.helper.Entity;
import com.domain.helper.Table;
import com.utils.FieldManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        List<Table> tables = new ArrayList<>();
        FilesManager filesManager = new FilesManager();
        ClassManager classManager = new ClassManager(filesManager);
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        QueryManager<Entity> queryManager = new QueryManager<>(connectionManager, classManager, tables);
        DBManager dbManager = new DBManager(queryManager);
        TableCorrector tableCorrector = new TableCorrector(queryManager);
        TableManager tableManager = new TableManager(classManager, tables, new HashMap<>(), ClassTableMapper.getInstance(), new FieldManager());
        tableManager.fillTables();
        TableExistenceChecker tableExistenceChecker = new TableExistenceChecker(tableCorrector, classManager, dbManager, tables);
        tableExistenceChecker.checkExistence();

    }
}
