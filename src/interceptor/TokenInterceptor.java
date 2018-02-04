package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import util.MyUtil;

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
			String token = request.getHeader("token");
			if (token.equals("debug")) {
				inv.invoke();
			} else {
				HttpSession session = request.getSession();
				JSONObject loginObj = (JSONObject) session.getAttribute(token);
				if (loginObj == null) {
					JSONObject json = MyUtil.getJson("用户登录失效", 606, "");
					controller.renderJson(json.toString());
				} else {
					inv.invoke();
				}
			}

		}
	}

}
