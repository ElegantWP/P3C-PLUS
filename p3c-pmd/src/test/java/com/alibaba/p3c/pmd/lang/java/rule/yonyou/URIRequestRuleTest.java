package com.alibaba.p3c.pmd.lang.java.rule.yonyou;

import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.testframework.SimpleAggregatorTst;
import org.junit.Test;

/**
 * @author weipan
 * @date 2019/7/11 16:26
 */
public class URIRequestRuleTest extends SimpleAggregatorTst {

  // 加载类路径Classpath下的 rulesets/java/yonyou-uri.xml
  private static final String RULESET = "java-yonyou-uri";

  @Override
  public void setUp() {
    addRule(RULESET, "URIRequestRule");
    addRule(RULESET,"ServiceLoopCallRule");
  }

  @Test
  public void testCodeStyle(){
    Rule rule = this.findRule(RULESET,"URIRequestRule");
    runTests(rule,"URIRequestRule");
  }

  @Test
  public void testServiceLoopCallRule(){
    Rule rule = this.findRule(RULESET,"ServiceLoopCallRule");
    runTests(rule,"ServiceLoopCallRule");
  }

}
