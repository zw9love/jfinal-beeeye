package interceptor;
/**
 * Created by admin on 2018/2/4.
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import util.MyUtil;

import java.util.Date;

public class TokenInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		String path = inv.getViewPath();
		// String[] slash = path.split("/");
		// System.out.println(slash.length);
		if (path.equals("/") || path.contains("/login")) {
			inv.invoke();
		} else {
			Controller controller = inv.getController();
			HttpServletRequest request = controller.getRequest();
			String method = request.getMethod().toLowerCase();
			String token;
			if(method.equals("get")){
				token = controller.getPara("token");
			}else{
				token = request.getHeader("token");
			}
			if (token.equals("debug")) {
				inv.invoke();
			} else {
				inv.invoke();
//				HttpSession session = request.getSession();
//				JSONObject loginObj = (JSONObject) session.getAttribute(token);
//				// 如果loginObj已经是null
//				if (loginObj == null) {
//					JSONObject json = MyUtil.getJson("用户登录失效", 611, "");
//					controller.renderJson(json.toString());
//				} else {
//					String loginName;
//					try {
//						loginName =(String) loginObj.get("login_name");
//						String sessionToken = (String) session.getAttribute(loginName);
//						int nowTime = Math.round(new Date().getTime() / 1000);
//						int expireTime = (int)loginObj.get("expireTime");
//						// 两个token值相同
//						if(sessionToken.equals(token)) && expireTime >= nowTime){
//							loginObj.put("expireTime", MyUtil.getRefreshTime()); // 刷新过期时间
//							session.setAttribute(token, loginObj);
//							inv.invoke();
//						}else{
//							session.removeAttribute(token);
//							JSONObject json = MyUtil.getJson("用户登录失效", 611, "");
//							controller.renderJson(json.toString());
//						}
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
			}

		}
	}

}
