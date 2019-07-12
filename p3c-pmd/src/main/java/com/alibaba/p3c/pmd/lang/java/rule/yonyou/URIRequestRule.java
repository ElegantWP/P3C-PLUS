package com.alibaba.p3c.pmd.lang.java.rule.yonyou;

import com.alibaba.p3c.pmd.lang.java.rule.AbstractAliRule;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTLiteral;
import net.sourceforge.pmd.lang.java.ast.ASTName;

/**
 * @author weipan
 * @date 2019/7/10 15:51
 * @deprecated Uri请求的规范问题 只允许使用 Get 和  Post方式请求服务器资源
 */
public class URIRequestRule extends AbstractAliRule {

  /**
   * uri规范只允许小写字母 以-连接
   */
  private static final String ANNOTATION_VALUE_XPATH = "//Annotation//Literal";


  /**
   * @RequestMapping
   * @GetMapping
   * @PostMapping 三类通用注解的位置在方法和接口的声明
   */
  private static final String ANNOTATE_METHOD_XPATH = "//Annotation//Name";


  @Override
  public Object visit(ASTCompilationUnit node, Object data) {
    try {
      List<Node> nodes = node
          .findChildNodesWithXPath(ANNOTATE_METHOD_XPATH);
      List<Node> uriNodes = new ArrayList<>();
      if (nodes != null && nodes.size() > 0) {
        //对所有除了Get Post方式的请求检查
        for (Node temporaryNode : nodes) {
          if (temporaryNode instanceof ASTName) {
            ASTName astName = (ASTName) temporaryNode;
            String image = astName.getImage();
            if (judgeConditionsWithRequestMapping(image)||judgeConditions(image)) {
              // 违反规则提示信息，第二个参数是提示信息位置，第三个参数是提示信息key，第四个参数用来替换提示信息
              // 中的占位符，这里获取的节点image属性就是方法名称
              addViolationWithMessage(data, node,
                  "java.yonyou.URIRequestRule.rule.msg",
                  new Object[]{node.getXPathNodeName()});
            }
            if("RequestMapping".equals(image) || "GetMapping".equals(image) || "PostMapping".equals(image)){
              uriNodes.add(temporaryNode);
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  private boolean judgeConditions(String annotationName) {
    boolean flag = "PutMapping".equals(annotationName) || "PatchMapping".equals(annotationName)
        || "DeleteMapping".equals(annotationName);
    return flag;
  }

  private boolean judgeConditionsWithRequestMapping(String image) {
    boolean flag = "RequestMethod.PUT".equals(image) || "RequestMethod.DELETE".equals(image)
        || "RequestMethod.PATCH".equals(image);
    return flag;
  }

}
