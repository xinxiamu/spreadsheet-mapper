package me.excel.tools.validator.cell;

import me.excel.tools.model.excel.CellBean;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by hanwen on 2016/12/22.
 */
public class LocalDateValidatorTest {

  @Test
  public void testCustomValidate() throws Exception {

    LocalDateTimeValidator localDateTimeValidator = new LocalDateTimeValidator("person.localDate", "yyyy-MM-dd");

    CellBean cell4 = new CellBean(1, 1, "");
    cell4.setField("person.localDate");
    assertTrue(localDateTimeValidator.validate(cell4));

    CellBean cell = new CellBean(1, 1, "1988-10-10 09:00:00");
    cell.setField("person.localDate");
    assertFalse(localDateTimeValidator.validate(cell));

    CellBean cell1 = new CellBean(1, 1, "1988-10-10");
    cell1.setField("person.localDate");
    assertTrue(localDateTimeValidator.validate(cell1));

    CellBean cell2 = new CellBean(1, 1, "asdasd");
    cell2.setField("person.localDate");
    assertFalse(localDateTimeValidator.validate(cell2));
  }

}