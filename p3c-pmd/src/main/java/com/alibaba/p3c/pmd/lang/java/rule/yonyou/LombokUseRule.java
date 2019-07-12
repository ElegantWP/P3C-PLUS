package com.alibaba.p3c.pmd.lang.java.rule.yonyou;

import com.alibaba.p3c.pmd.lang.java.rule.AbstractAliRule;
import java.util.List;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTImportDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import org.jaxen.JaxenException;

/**
 * @author weipan
 * @date 2019/7/12 15:09
 * lombok 使用规约
 */

public class LombokUseRule extends AbstractAliRule {


  private static final String ANNOTATION_LOMBOK_NAME = "//ImportDeclaration//Name";

  @Override
  public Object visit(ASTCompilationUnit node, Object data){
    this.match(node,data);
    return null;
  }

  private void match(AbstractJavaNode node, Object data){

    try {
      List<Node> importDeclarations = node.findChildNodesWithXPath(ANNOTATION_LOMBOK_NAME);
      if (importDeclarations != null && importDeclarations.size() > 0 ){
        for (Node importDeclaration : importDeclarations){
          if (importDeclaration instanceof ASTName){
            ASTName name = (ASTName) importDeclaration;
            String lombokAnnotationName = name.getImage();
            if (lombokAnnotationName != null && lombokAnnotationName.length() >0){
              String[] strs = lombokAnnotationName.split("\\.");
              if ("lombok".equals(strs[0]) && judge(strs[1])){
                this.addViolationWithMessage(data, node,"java.yonyou.LombokUseRule.rule.msg",
                    new Object[]{lombokAnnotationName});
              }
            }
          }
        }
      }
    } catch (JaxenException e) {
      e.printStackTrace();
    }
  }
//之后替换枚举类
  private boolean judge(String str){
    if ("Data".equals(str) || "Log4j".equals(str) || "NoArgsConstructor".equals(str) || "AllArgsConstructor".equals(str) ||
        "Cleanup".equals(str) || "Synchronized".equals(str) || "SneakyThrows".equals(str) || "NonNull".equals(str) || "Value".equals(str)){
      return true;
    }
    return false;
  }

}
