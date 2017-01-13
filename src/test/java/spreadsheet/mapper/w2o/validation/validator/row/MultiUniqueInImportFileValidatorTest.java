package spreadsheet.mapper.w2o.validation.validator.row;

import org.testng.annotations.Test;
import spreadsheet.mapper.TestFactory;
import spreadsheet.mapper.model.core.Sheet;
import spreadsheet.mapper.model.meta.SheetMeta;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by hanwen on 2017/1/4.
 */
public class MultiUniqueInImportFileValidatorTest {

  @Test
  public void testCustomValidate() throws Exception {

    Sheet sheet = TestFactory.createSheet();

    SheetMeta sheetMeta = TestFactory.createSheetMeta(true);

    RowValidator validator1 = new MultiUniqueInImportFileValidator().multiUniqueFields("test.int1", "test.int2").group("test.multi.unique").end();

    assertTrue(validator1.valid(sheet.getRow(2), sheetMeta));
    assertTrue(validator1.valid(sheet.getRow(3), sheetMeta));

    RowValidator validator2 = new MultiUniqueInImportFileValidator().multiUniqueFields("test.string").group("test.multi.unique").end();

    assertTrue(validator2.valid(sheet.getRow(2), sheetMeta));
    assertFalse(validator2.valid(sheet.getRow(3), sheetMeta));

  }

}