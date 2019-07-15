package com.alibaba.p3c.pmd.lang.java.rule.yonyou;

import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.testframework.SimpleAggregatorTst;
import org.junit.Test;

/**
 * @author weipan
 * @date 2019/7/11 16:26
 */
public class YonyouRuleTest extends SimpleAggregatorTst {

  // 加载类路径Classpath下的 rulesets/java/yonyou-rule.xml
  private static final String RULESET = "java-yonyou-rule";

  @Override
  public void setUp() {
    addRule(RULESET, "UriYonyouRequestRule");
    addRule(RULESET,"ServiceLoopCallRule");
    addRule(RULESET,"LombokUseRule");
    addRule(RULESET,"MethodParameterCountRule");
  }

  @Test
  public void testURI(){
    Rule rule = this.findRule(RULESET,"UriYonyouRequestRule");
    runTests(rule,"UriYonyouRequestRule");
  }

  @Test
  public void testServiceLoopCallRule(){
    Rule rule = this.findRule(RULESET,"ServiceLoopCallRule");
    runTests(rule,"ServiceLoopCallRule");
  }

  @Test
  public void testLombokUseRule(){
    Rule rule = this.findRule(RULESET,"LombokUseRule");
    runTests(rule,"LombokUseRule");
  }

  @Test
  public void testCodeStyle(){
    Rule rule = this.findRule(RULESET,"MethodParameterCountRule");
    runTests(rule,"MethodParameterCountRule");
  }
}
