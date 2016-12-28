package me.excel.tools.validator.cell;

import me.excel.tools.model.excel.CellBean;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by hanwen on 2016/12/22.
 */
public class NumberValidatorTest {

  @Test
  public void testCustomValidate() throws Exception {

    NumberValidator numberValidator = new NumberValidator("person.number");

    CellBean cell = new CellBean(1, 1, "");
    cell.setField("person.number");
    assertTrue(numberValidator.validate(cell));

    CellBean cell1 = new CellBean(1, 1, "dasdasd");
    cell1.setField("person.number");
    assertFalse(numberValidator.validate(cell1));

    CellBean cell2 = new CellBean(1, 1, "1");
    cell2.setField("person.number");
    assertTrue(numberValidator.validate(cell2));
  }

}