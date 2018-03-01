package route;
/**
 * Created by admin on 2018/2/2.
 */
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

import controller.*;
import interceptor.TokenInterceptor;
import model._MappingKit;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法 详见 JFinal 俱乐部:
 * http://jfinal.com/club
 * 
 * API引导式配置
 */
public class RouteConfig extends JFinalConfig {

	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
	 * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为： -XX:PermSize=64M
	 * -XX:MaxPermSize=256M
	 */
	public static void main(String[] args) {
		/**
		 * 特别注意：Eclipse 之下建议的启动方式
		 */
		JFinal.start("WebRoot", 80, "/", 5);

		/**
		 * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
		 */
		// JFinal.start("WebRoot", 80, "/");
	}

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("jdbc_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		String systemName = System.getProperty("os.name");
		if(systemName.toLowerCase().contains("windows")){
//			System.out.println("windows操作系统");
			me.setBaseUploadPath("C:\\jfinal-beeeye\\upload");
		}else{
			me.setBaseUploadPath("/usr/local/share/lpdata/tomcat/webapp");
		}
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		// me.add("/", LoginController.class);
		me.add("/", IndexController.class); // 第三个参数为该Controller的视图存放路径
		me.add("/login", LoginController.class);
		me.add("/menu", BeeeyeMenuController.class);
		me.add("/host", BeeeyeHostController.class);
		me.add("/user", BeeeyeUserController.class);
		me.add("/role", BeeeyeRoleController.class);
		me.add("/setting", BeeeyeSettingController.class);
		me.add("/BeeneedleProcessSubject", BeeneedleProcessSubjectController.class);
		me.add("/BeeneedleObjectLabel", BeeneedleObjectLabelController.class);
		me.add("/BeeneedleProcessHost", BeeneedleProcessHostController.class);
		me.add("/BeeneedleObjectHost", BeeneedleObjectHostController.class);
		me.add("/BeeneedleIntegrity", BeeneedleIntegrityController.class);
		me.add("/BeeneedlePelf", BeeeyePelfController.class);
		me.add("/SoftWare_SPEC/updown", BeeeyeSoftWareController.class);
		me.add("/bl/win", BeeeyeWindowsSafeLibController.class);
		me.add("/bl/linux", BeeeyeLinuxSafeLibController.class);
		me.add("/hostTh", BeeeyeThresholdController.class);
		me.add("/audit/output", BeeeyeAuditOutController.class);
	}

	public void configEngine(Engine me) {
		// me.addSharedFunction("/common/_layout.html");
		// me.addSharedFunction("/common/_paginate.html");
	}

	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		DruidPlugin druidPlugin = createDruidPlugin();
		me.add(druidPlugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		me.add(arp);
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new TokenInterceptor());
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {

	}
}
