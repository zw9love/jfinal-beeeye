package validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import util.MD5Util;
import util.MyUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LoginValidator extends Validator {

    @Override
    protected void validate(Controller c) {
        Map<String, Object> json = MyUtil.getJsonData(c.getRequest());
        String login_name = (String) json.get("login_name");
        String login_pwd = MD5Util.encrypt(json.get("login_pwd").toString());
        if(login_name.trim().equals("")){
            c.renderJson(MyUtil.getJson("用户名不能为空", 606, ""));
        }else if(login_pwd.trim().equals("")){
            c.renderJson(MyUtil.getJson("密码不能为空", 606, ""));
        }
    }

    @Override
    protected void handleError(Controller c) {
        c.renderJson(MyUtil.getJson(" loginvalidator出错了", 606, ""));
    }
}
