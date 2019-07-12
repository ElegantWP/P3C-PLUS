package com.alibaba.p3c.pmd.lang.java.rule.yonyou;

/**
 * @author weipan
 * @date 2019/7/12 13:28
 */

import com.alibaba.p3c.pmd.lang.java.rule.AbstractAliRule;
import java.util.Iterator;
import java.util.List;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTDoStatement;
import net.sourceforge.pmd.lang.java.ast.ASTForStatement;
import net.sourceforge.pmd.lang.java.ast.ASTWhileStatement;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;

/**
 * 排查所有循环中调用service或dao方法的代码
 */
public class ServiceLoopCallRule extends AbstractAliRule {

  private static final String METHOD_CALL_PATH = "Statement//PrimaryExpression/PrimaryPrefix/Name";
  private static String packageName = "";

  public ServiceLoopCallRule() {
  }

  @Override
  public Object visit(ASTWhileStatement node, Object data) {
    this.match(node, data);
    return super.visit(node, data);
  }

  @Override
  public Object visit(ASTDoStatement node, Object data) {
    this.match(node, data);
    return super.visit(node, data);
  }

  @Override
  public Object visit(ASTForStatement node, Object data) {
    this.match(node, data);
    return super.visit(node, data);
  }

  private void match(AbstractJavaNode node, Object data) {
    try {
      List<Node> callNodes = node.findChildNodesWithXPath("Statement//PrimaryExpression/PrimaryPrefix/Name");
      if (callNodes != null && callNodes.size() > 0) {
        Iterator iterator = callNodes.iterator();

        while (iterator.hasNext()) {
          Node callNode = (Node) iterator.next();
          String callString = callNode.getImage();
          String[] callSplits = callString.split("\\.");

          if (callSplits.length <= 1) {
            continue;
          }
          String serviceProvider = callSplits[0];
          if (serviceProvider.toLowerCase().endsWith("dao") || serviceProvider.toLowerCase().endsWith("service") || serviceProvider.toLowerCase().endsWith("serviceimpl")) {
            this.addViolationWithMessage(data, callNode,"java.yonyou.ServiceLoopCallRule.rule.msg",
                new Object[]{node.getXPathNodeName()});

          }
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

