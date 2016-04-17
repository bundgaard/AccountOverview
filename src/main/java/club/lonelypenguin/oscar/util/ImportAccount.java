package club.lonelypenguin.oscar.util;

import club.lonelypenguin.oscar.models.AccountSummaryMonth;
import club.lonelypenguin.oscar.reader.Excel2003Reader;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by dbundgaard on 2016-04-17.
 */
public class ImportAccount {
    private static final Logger log = Logger.getLogger(ImportAccount.class);
    private String fileName;
    private String userName;
    private String password;
    private String url;

    //<editor-fold desc="Getters and Setters">
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    //</editor-fold>


    //<editor-fold desc="Constructor">
    public ImportAccount() {
    }

    public ImportAccount(String fileName){
        this.fileName = fileName;
    }
    //</editor-fold>



    public boolean Import(){
        Excel2003Reader repository = new Excel2003Reader(getFileName());
        List<AccountSummaryMonth> accountEntries = repository.getEntries();
        if(accountEntries.size() > 0){
            Connection c = null;
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                c = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Nordea?user=nordea&password=n0rd35&useSSL=false");
                final String INSERT_ACCOUNT_ENTRY = "INSERT INTO AccountOverview "
                        + "(AccountOverviewDate, AccountOverviewText, AccountOverviewCategory, AccountOverviewAmount) "
                        + "VALUES( ?, ?, ?, ?)";
                c.setAutoCommit(false);
                long starttime = System.currentTimeMillis();
                PreparedStatement preparedStatement = c.prepareStatement(INSERT_ACCOUNT_ENTRY);

                for(AccountSummaryMonth asm : accountEntries){
                    preparedStatement.setDate(1,java.sql.Date.valueOf(asm.get_date()));
                    preparedStatement.setString(2, asm.get_action());
                    preparedStatement.setString(3, asm.get_category());
                    preparedStatement.setBigDecimal(4, asm.get_amount());
                    preparedStatement.addBatch();
                }
                int[] intBatchExecution = preparedStatement.executeBatch();
                c.commit();
                log.info(String.format("The time to process %d entries took %d ms", accountEntries.size(), System.currentTimeMillis() - starttime));
                preparedStatement.close();
            }catch(Exception e){
                log.error(e.getMessage());
            }finally{
                try {
                    if (c != null) {
                        c.close();
                    }
                }catch(Exception e){}
            }
        } else {
            log.error("Nothing to import.");
            return false;
        }

        System.out.println("Our file has #: "+accountEntries.size() + " entries");
        return true;
    }
}
