package club.lonelypenguin.oscar.reader;

import club.lonelypenguin.oscar.base.ExcelFileReader;
import club.lonelypenguin.oscar.models.AccountSummaryMonth;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.math.BigDecimal;
import java.text.*;
import java.util.*;

/**
 * Created by dbundgaard on 2016-04-15.
 */
public class Excel2003Reader extends ExcelFileReader<AccountSummaryMonth> {

    public Excel2003Reader(String _incomingFile) {
        super(_incomingFile);
    }

    public List<AccountSummaryMonth> getEntries(){

        List<AccountSummaryMonth> entries = new ArrayList<AccountSummaryMonth>();
        Locale locale = new Locale("sv","SE");
        DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols(locale);
        decimalSymbols.setDecimalSeparator(',');
        decimalSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = ((DecimalFormat) NumberFormat.getNumberInstance(locale));
        decimalFormat.setDecimalFormatSymbols(decimalSymbols);
        decimalFormat.setParseBigDecimal(true);
        Logger.getLogger(Excel2003Reader.class).info(Excel2003Reader.class.toString() + " using following locale " + locale.getCountry());
        if(get_workBook() == null){
            System.err.println("Workbook is empty please see the stacktrace");
        }
        Sheet sheet = get_workBook().getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        if(rowIterator.hasNext())
            rowIterator.next();
        try {
            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                AccountSummaryMonth summary = new AccountSummaryMonth();
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            summary.set_date((String) super.getCellValue(nextCell));
                            break;
                        case 1:
                            summary.set_action((String)super.getCellValue(nextCell));
                            break;
                        case 2:
                            summary.set_category((String)super.getCellValue(nextCell));
                            break;
                        case 3:
                            summary.set_amount((BigDecimal) decimalFormat.parse((String)super.getCellValue(nextCell)));
                            break;
                    }

                }
                entries.add(summary);
            }
        }catch(Exception e){
            Logger.getLogger(Excel2003Reader.class).error(e.getMessage());
            return new ArrayList<AccountSummaryMonth>();
        }
        return entries;
    }
}
