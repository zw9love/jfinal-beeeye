package model.base;
/**
 * Created by admin on 2018/2/2.
 */
import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({ "serial", "unchecked" })
public class BaseSetting<M extends BaseSetting<M>> extends Model<M> implements IBean {
	public void setIds(String ids) {
		set("ids", ids);
	}

	public String getIds() {
		return get("ids");
	}

	public void setSiteName(String SiteName) {
		set("site_name", SiteName);
	}

	public String getSiteName() {
		return get("site_name");
	}

	public void setLoginErrorCount(String loginErrorCount) {
		set("login_error_count", loginErrorCount);
	}

	public String getLoginErrorCount() {
		return get("login_error_count");
	}

	// public void setUsername(String username) {
	// set("username", username);
	// }
	//
	// public String getUsername() {
	// return get("username");
	// }
	//
	// public void setEmail(String email) {
	// set("email", email);
	// }
	//
	// public String getEmail() {
	// return get("email");
	// }
	//
	// public void setPhone(String phone) {
	// set("phone", phone);
	// }
	//
	// public String getPhone() {
	// return get("phone");
	// }
	//
	// public void setStatus(String status) {
	// set("status", status);
	// }
	//
	// public String getStatus() {
	// return get("status");
	// }
	//
	// public void setRoleIds(String roleIds) {
	// set("role_ids", roleIds);
	// }
	//
	// public String getRoleIds() {
	// return get("role_ids");
	// }
	//
	// public void setRecordHash(String recordHash) {
	// set("record_hash", recordHash);
	// }
	//
	// public String getRecordHash() {
	// return get("record_hash");
	// }

	protected String getTable() {
		return "common_setting";
	}
}
