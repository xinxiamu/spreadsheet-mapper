package com.supwisdom.spreadsheet.mapper.o2w.converter;

import org.joda.time.LocalDate;

/**
 * local date text value with supplied pattern converter
 * Created by hanwen on 5/3/16.
 */
public class JodaLocalDateToStringConverter
    extends ToStringConverterTemplate<LocalDate, JodaLocalDateToStringConverter> {

  private String pattern;

  public JodaLocalDateToStringConverter(String pattern) {
    this.pattern = pattern;
  }

  @Override
  public String convertProperty(Object property) {

    if (!LocalDate.class.equals(property.getClass())) {
      throw new IllegalArgumentException(
          "Not a Joda LocalDateTime: " + property.getClass().getName() + " value: " + property.toString());
    }
    return ((LocalDate) property).toString(pattern);

  }

}
