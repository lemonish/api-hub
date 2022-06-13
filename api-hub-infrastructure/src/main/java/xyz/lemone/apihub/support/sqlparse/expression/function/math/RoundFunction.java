package xyz.lemone.apihub.support.sqlparse.expression.function.math;

import java.math.BigDecimal;
import java.util.List;

import xyz.lemone.apihub.support.sqlparse.expression.function.Functions;
import xyz.lemone.apihub.ureport.Utils;
import xyz.lemone.apihub.ureport.build.Context;
import xyz.lemone.apihub.support.sqlparse.exception.ExpressionComputeException;
import xyz.lemone.apihub.support.sqlparse.expression.model.data.ExpressionData;
import xyz.lemone.apihub.support.sqlparse.expression.model.data.ObjectExpressionData;

/**
 * 按位数四舍五入.
 * @author lemon
 */
public class RoundFunction extends MathFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context) {
		BigDecimal data=buildBigDecimal(dataList);
		int pos=0;
		if(dataList.size()==2){
			ExpressionData<?> exprData=dataList.get(1);
			if(exprData instanceof ObjectExpressionData){
				ObjectExpressionData objData=(ObjectExpressionData)exprData;
				Object obj=objData.getData();
				if(obj==null){
					throw new ExpressionComputeException("Round Function second parameter can not be null.");
				}
				pos=Utils.toBigDecimal(obj).intValue();
			}
		}
		return data.setScale(pos, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public String name() {
		return Functions.ROUND;
	}
}
