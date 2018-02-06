package validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import util.MD5Util;
import util.MyUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LoginValidator extends MainValidator {
    public boolean dologinValidate(Controller c, Map<String, Object> json) {
        String login_name = (String) json.get("login_name");
        String login_pwd = (String) json.get("login_pwd");
        Boolean nameFlag = validateRequired(login_name);
        Boolean pwdFlag = false;
        if (nameFlag){
            pwdFlag = validateRequired(login_pwd);
            if (!pwdFlag)
                c.renderJson(MyUtil.getJson("请输入密码", 606, "").toString());
        }
        else
            c.renderJson(MyUtil.getJson("请输入用户名", 606, "").toString());
        return nameFlag && pwdFlag;
    }
}

//public class LoginValidator extends MainValidator {
//    public void dologinValidate(Controller c) {
//    }
//    @Override
//    protected void handleError(Controller c) {
//        c.renderJson(MyUtil.getJson("LoginValidator出错了", 606, ""));
//    }
//}
