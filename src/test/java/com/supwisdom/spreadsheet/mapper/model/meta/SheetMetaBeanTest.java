package com.supwisdom.spreadsheet.mapper.model.meta;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by qianjia on 2017/2/12.
 */
public class SheetMetaBeanTest {

  @Test
  public void testAddFieldMetaGood() throws Exception {

    SheetMetaBean sheetMetaBean = new SheetMetaBean(1);

    FieldMetaBean fieldMeta1 = new FieldMetaBean("a", 1);
    FieldMetaBean fieldMeta2 = new FieldMetaBean("b", 2);

    sheetMetaBean.addFieldMeta(fieldMeta1);
    sheetMetaBean.addFieldMeta(fieldMeta2);

    assertEquals(sheetMetaBean.getFieldMeta("a"), fieldMeta1);
    assertEquals(sheetMetaBean.getFieldMeta("b"), fieldMeta2);

    assertEquals(sheetMetaBean.getFieldMeta(1), fieldMeta1);
    assertEquals(sheetMetaBean.getFieldMeta(2), fieldMeta2);

  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*already has FieldMeta for column.*")
  public void testAddFieldMetaBad1() throws Exception {

    SheetMetaBean sheetMetaBean = new SheetMetaBean(1);

    FieldMetaBean fieldMeta1 = new FieldMetaBean("a", 1);
    FieldMetaBean fieldMeta2 = new FieldMetaBean("b", 1);

    sheetMetaBean.addFieldMeta(fieldMeta1);
    sheetMetaBean.addFieldMeta(fieldMeta2);

  }

  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*already has FieldMeta for name.*")
  public void testAddFieldMetaBad2() throws Exception {

    SheetMetaBean sheetMetaBean = new SheetMetaBean(1);

    FieldMetaBean fieldMeta1 = new FieldMetaBean("a", 1);
    FieldMetaBean fieldMeta2 = new FieldMetaBean("a", 2);

    sheetMetaBean.addFieldMeta(fieldMeta1);
    sheetMetaBean.addFieldMeta(fieldMeta2);

  }


  @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "fieldMeta is null")
  public void testAddFieldMetaBad3() throws Exception {

    SheetMetaBean sheetMetaBean = new SheetMetaBean(1);

    sheetMetaBean.addFieldMeta(null);

  }

  @Test
  public void testRemoveFieldMeta() throws Exception {

    SheetMetaBean sheetMetaBean = new SheetMetaBean(1);
    FieldMetaBean fieldMeta1 = new FieldMetaBean("a", 1);
    sheetMetaBean.addFieldMeta(fieldMeta1);

    sheetMetaBean.removeFieldMeta("a");

    assertNull(sheetMetaBean.getFieldMeta("a"));
    assertNull(sheetMetaBean.getFieldMeta(1));

  }

  @Test
  public void testRemoveFieldMeta2() throws Exception {

    SheetMetaBean sheetMetaBean = new SheetMetaBean(1);
    sheetMetaBean.removeFieldMeta("a");

    assertNull(sheetMetaBean.getFieldMeta("a"));
    assertNull(sheetMetaBean.getFieldMeta(1));

  }


}
