package xyz.lemone.apihub.support.sqlparse.expression.model.expression;

import xyz.lemone.apihub.support.sqlparse.exception.ExpressionComputeException;
import xyz.lemone.apihub.support.sqlparse.expression.function.Function;
import xyz.lemone.apihub.support.sqlparse.expression.model.data.ExpressionData;
import xyz.lemone.apihub.support.sqlparse.expression.model.data.ObjectExpressionData;
import xyz.lemone.apihub.support.sqlparse.expression.model.data.ObjectListExpressionData;
import xyz.lemone.apihub.support.sqlparse.expression.model.expression.BaseExpression;
import xyz.lemone.apihub.ureport.build.Context;
import xyz.lemone.apihub.ureport.expression.ExpressionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 方法表达式.
 *
 * @author lemon
 */
public class FunctionExpression extends BaseExpression {

    private static final long serialVersionUID = 1L;

    private String name;

    private List<BaseExpression> expressions;

    @Override
    public ExpressionData<?> compute(Context context) {
        Map<String, Function> functions = ExpressionUtils.getFunctions();
        Function targetFunction = functions.get(name);
        if (targetFunction == null) {
            throw new ExpressionComputeException("Function [" + name + "] not exist.");
        }
        List<ExpressionData<?>> dataList = new ArrayList<>();
        if (expressions != null) {
            for (BaseExpression expr : expressions) {
                ExpressionData<?> data = expr.execute(context);
                dataList.add(data);
            }
        }
        Object obj = targetFunction.execute(dataList, context);
        if (obj instanceof List) {
            return new ObjectListExpressionData((List<?>) obj);
        }
        return new ObjectExpressionData(obj);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpressions(List<BaseExpression> expressions) {
        this.expressions = expressions;
    }

    public List<BaseExpression> getExpressions() {
        return expressions;
    }
}
