package validator;

import annotation.MethodValidator;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.validate.Validator;
import util.MyUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by admin on 2018/2/6.
 */

public abstract class MainValidator {
    protected boolean validateRequired(String value){
        if(value == null || "".equals(value.trim()))
            return false;
        else
            return true;
    }
}


//public abstract class MainValidator extends Validator {
//
//    protected void validate(Controller controller) {
//        Method method = getActionMethod();
//        if (method.isAnnotationPresent(MethodValidator.class)) {
//            String name = method.getAnnotation(MethodValidator.class).name();
//            for (Method me : this.getClass().getMethods()) {
//                if (me.getName().equals(name)) {
//                    try {
//                        me.invoke(this, controller);
//                    } catch (IllegalAccessException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    } catch (IllegalArgumentException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    } catch (InvocationTargetException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//            }
//        }
//    }
//
//}
