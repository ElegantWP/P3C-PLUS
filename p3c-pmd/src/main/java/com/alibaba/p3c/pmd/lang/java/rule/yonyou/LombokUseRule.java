package com.alibaba.p3c.pmd.lang.java.rule.yonyou;

import com.alibaba.p3c.pmd.lang.java.rule.AbstractAliRule;
import com.alibaba.p3c.pmd.lang.java.util.LombokAnnotation;
import java.util.List;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
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

  /**
   * 校验lombok的规约 lombok.Value
   * @param str 校验字符串
   * @return boolean
   */
  private boolean judge(String str){
    if (LombokAnnotation.Data.getAnnotationName().equals(str) || LombokAnnotation.Log4j.getAnnotationName().equals(str) ||
        LombokAnnotation.NoArgsConstructor.getAnnotationName().equals(str) || LombokAnnotation.AllArgsConstructor.getAnnotationName().equals(str) ||
        LombokAnnotation.Cleanup.getAnnotationName().equals(str) || LombokAnnotation.Synchronized.getAnnotationName().equals(str) ||
        LombokAnnotation.SneakyThrows.getAnnotationName().equals(str) || LombokAnnotation.NonNull.getAnnotationName().equals(str) ||
        LombokAnnotation.Value.getAnnotationName().equals(str)){
      return true;
    }
    return false;
  }

}
