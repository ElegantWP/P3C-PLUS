package com.alibaba.p3c.pmd.lang.java.util;

/**
 * @author weipan
 * @date 2019/7/15 10:34
 */
public enum  LombokAnnotation {
  /**
   * Lombok的一些排除的注解信息
   */
  Data("Data"),Log4j("Log4j"),
  NoArgsConstructor("NoArgsConstructor"),
  AllArgsConstructor("AllArgsConstructor"),
  Cleanup("Cleanup"),
  Synchronized("Synchronized"),
  SneakyThrows("SneakyThrows"),
  NonNull("NonNull"),
  Value("Value");

  /**
   * Lombok注解的一些枚举类
   */
  private String annotationName;

  public String getAnnotationName() {
    return annotationName;
  }

  public void setAnnotationName(String annotationName) {
    this.annotationName = annotationName;
  }

  LombokAnnotation(String annotationName) {
    this.annotationName = annotationName;
  }
}
