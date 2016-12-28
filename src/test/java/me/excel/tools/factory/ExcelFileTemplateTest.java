package me.excel.tools.factory;

import me.excel.tools.ExcelConstants;
import me.excel.tools.extractor.BooleanZhExtractor;
import me.excel.tools.generator.DefaultExcelGenerator;
import me.excel.tools.generator.ExcelGenerator;
import me.excel.tools.model.excel.Row;
import me.excel.tools.model.excel.Sheet;
import me.excel.tools.processor.ObjectFactory;
import me.excel.tools.processor.ObjectProcessor;
import me.excel.tools.processor.ObjectProcessorListener;
import me.excel.tools.setter.BooleanValueSetter;
import me.excel.tools.setter.LocalDateValueSetter;
import me.excel.tools.validator.ExcelValidator;
import me.excel.tools.validator.cell.BooleanValidator;
import me.excel.tools.validator.cell.IntValidator;
import me.excel.tools.validator.cell.LocalDateValidator;
import me.excel.tools.validator.sheet.FieldScopeValidator;
import me.excel.tools.validator.sheet.RequireFieldValidator;
import org.apache.poi.util.TempFile;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by hanwen on 5/3/16.
 */
public class ExcelFileTemplateTest {

  @Test
  public void testProcess() throws Exception {
    URL resource = this.getClass().getResource("test" + ExcelConstants.SUFFIX_XLSX);
    File excel = new File(resource.getFile());

    ExcelFileTemplate excelFileTemplate = new ExcelFileTemplate(excel);

    ExcelValidator excelValidator = excelFileTemplate.getExcelValidator();

    excelValidator.addSheetValidator(new FieldScopeValidator(new String[]{"student.code", "student.age", "student.name", "student.enrollDate", "student.inSchool"}));
    excelValidator.addSheetValidator(new RequireFieldValidator(new String[]{"student.code", "student.age", "student.name", "student.enrollDate", "student.inSchool"}));

    excelValidator.addCellValidator(
        new LocalDateValidator("student.enrollDate", "yyyy-MM-dd"),
        new BooleanValidator("student.inSchool"),
        new IntValidator("student.age")
    );

    assertTrue(excelValidator.valid());

    ObjectProcessor objectProcessor = excelFileTemplate.getObjectProcessor();

    objectProcessor.addCellValueSetter(
        new LocalDateValueSetter("student.enrollDate", "yyyy-MM-dd"),
        new BooleanValueSetter("student.inSchool")
    );

    objectProcessor.addModelFactory(new StudentObjectFactoryTest());
    objectProcessor.process();
  }

  @Test
  public void testExport() throws IOException {

    StudentTest s1 = new StudentTest();
    s1.setAge(18);
    s1.setCode("111111");
    s1.setName("std1");
    s1.setEnrollDate(LocalDate.now());
    s1.setInSchool(true);

    StudentTest s2 = new StudentTest();
    s2.setAge(18);
    s2.setCode("222222");
    s2.setName("std2");
    s2.setEnrollDate(LocalDate.now());
    s2.setInSchool(true);

    List<StudentTest> list = new ArrayList<>();
    list.add(s1);
    list.add(s2);

    File file = TempFile.createTempFile("test", ExcelConstants.SUFFIX_XLSX);


    ExcelGenerator excelGenerator = new DefaultExcelGenerator();

    excelGenerator.addValueExtractors(new BooleanZhExtractor("student.inSchool"));

//    excelGenerator.setData(list);
//    excelGenerator.setFields("student.code", "student.age", "student.name", "student.enrollDate", "student.inSchool");
//    excelGenerator.setTitles("学号", "年龄", "姓名", "入学日期", "是否在校");

    excelGenerator.write(file);
  }

  public class StudentObjectProcessorListenerTest implements ObjectProcessorListener {

    @Override
    public void beforeSheet(Sheet sheet, List<Object> objects) {

    }

    @Override
    public void beforeRow(Row row, Object object) {

    }

    @Override
    public void afterRow(Row row, Object object) {

    }

    @Override
    public void afterSheet(Sheet sheet, List<Object> objects) {

      assertEquals(objects.size(), 2);

      DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

      StudentTest model1 = (StudentTest) objects.get(0);
      StudentTest model2 = (StudentTest) objects.get(1);
      assertEquals(model1.getCode(), "111111");
      assertEquals(model2.getCode(), "2222");

      assertEquals(model1.getName(), "std1");
      assertEquals(model2.getName(), "std2");

      assertEquals(model1.getAge(), new Integer(18));
      assertEquals(model2.getAge(), new Integer(18));

      assertTrue(model1.isInSchool());
      assertTrue(model2.isInSchool());

      assertEquals(model1.getEnrollDate(), dateTimeFormatter.parseLocalDate("2015-09-02"));
      assertEquals(model2.getEnrollDate(), dateTimeFormatter.parseLocalDate("2015-09-02"));

    }

    @Override
    public int getSheetIndex() {
      return 1;
    }
  }

  public class StudentObjectFactoryTest implements ObjectFactory {

    @Override
    public Object create(Row row) {
      return new StudentTest();
    }

    @Override
    public int getSheetIndex() {
      return 1;
    }
  }

  public class StudentTest {

    private String code;

    private String name;

    private Integer age;

    private LocalDate enrollDate;

    private boolean inSchool;

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Integer getAge() {
      return age;
    }

    public void setAge(Integer age) {
      this.age = age;
    }

    public LocalDate getEnrollDate() {
      return enrollDate;
    }

    public void setEnrollDate(LocalDate enrollDate) {
      this.enrollDate = enrollDate;
    }

    public boolean isInSchool() {
      return inSchool;
    }

    public void setInSchool(boolean inSchool) {
      this.inSchool = inSchool;
    }
  }

}