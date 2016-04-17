package club.lonelypenguin.oscar.base;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by dbundgaard on 2016-04-15.
 */
public abstract class ExcelFileReader<K> {

    private String _incomingFile;
    private Workbook _workBook;

    public Workbook get_workBook() {
        if(_workBook != null)
            return _workBook;
        try {
            FileInputStream inputStream = new FileInputStream(new File(this._incomingFile));
            _workBook = new HSSFWorkbook(inputStream);
            return _workBook;
        }catch(Exception e){
            Logger.getLogger(ExcelFileReader.class).error(e.getMessage());
        }
        return _workBook;
    }

    public void set_workBook(Workbook _workBook) {
        this._workBook = _workBook;
    }

    public ExcelFileReader(String _incomingFile) {

        this._incomingFile = _incomingFile;

    }

    protected Object getCellValue(Cell cell){
        switch(cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
        }
        return null; // should maybe return empty string?
    }
    public abstract List<K> getEntries();

}
