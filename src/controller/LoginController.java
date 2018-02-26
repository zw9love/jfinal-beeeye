package controller;
/**
 * Created by admin on 2018/2/2.
 */

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import annotation.MethodValidator;
import com.jfinal.aop.Before;
import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import util.MD5Util;
import util.MyUtil;
import validator.LoginValidator;

//@Before(LoginValidator.class)
public class LoginController extends Controller {
    public void index() {
        render("../login.html");
    }

//    @MethodValidator(name="dologinValidate")
    public void dologin() throws JSONException {
        Map<String, Object> json = MyUtil.getJsonData(getRequest());
        if(new LoginValidator().dologinValidate(this, json)){
            // System.out.println(json.toString());
            String login_name = (String) json.get("login_name");
            String login_pwd = MD5Util.encrypt(json.get("login_pwd").toString());
            JSONObject jsonObj = MyUtil.getJson("成功", 200, "");
            List<Record> list = Db.find("SELECT * FROM common_user where login_name = ? and login_pwd = ? ", login_name,
                    login_pwd);
            if (list.size() > 0) {
                JSONObject obj = new JSONObject();
                Map<String, Object> map = list.get(0).getColumns();
                for (String key : map.keySet()) {
                    obj.put(key, map.get(key));
                }
                int expireTime = MyUtil.getRefreshTime();
                obj.put("expireTime", expireTime);
                HttpSession session = getSession();
                String token = MyUtil.getRandomString();
                session.setAttribute(token, obj);
                session.setAttribute(list.get(0).get("login_name").toString(), token);
                jsonObj = MyUtil.getJson("成功", 200, "");
                HttpServletResponse response = getResponse();
                response.setHeader("token", token);
            } else {
                jsonObj = MyUtil.getJson("账号或者密码错误。", 606, "");
            }
            renderJson(jsonObj.toString());
        }
    }

    public void loged() throws JSONException {
        // System.out.println("进入了loged方法");
        HttpSession session = getSession();
        Map<String, Object> json = MyUtil.getJsonData(getRequest());
        String token = (String) json.get("token");
        JSONObject role = (JSONObject) session.getAttribute(token);
        JSONObject roleObj = new JSONObject();
        roleObj.put("role", role);
        // System.out.println(roleObj.toString());
        JSONObject jsonObj = new JSONObject();
        if (role == null) {
            jsonObj = MyUtil.getJson("失败", 606, "");
            IndexController.setLoginActive(false);
        } else {
            // System.out.println("---------------111111111111111-------------------");
            // System.out.println(roleObj.toString());
            // System.out.println("---------------222222222222222------------------");
            jsonObj = MyUtil.getJson("成功", 200, roleObj);
            IndexController.setLoginActive(true);
        }
        renderJson(jsonObj.toString());
    }
}
