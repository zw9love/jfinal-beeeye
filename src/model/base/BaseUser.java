package model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({ "serial", "unchecked" })
public abstract class BaseUser<M extends BaseUser<M>> extends Model<M> implements IBean {
	public void setIds(String ids) {
		set("ids", ids);
	}

	public String getIds() {
		return get("ids");
	}

	public void setLoginName(String loginName) {
		set("login_name", loginName);
	}

	public String getLoginName() {
		return get("login_name");
	}

	public void setLoginPwd(String loginPwd) {
		set("login_pwd", loginPwd);
	}

	public String getLoginPwd() {
		return get("login_pwd");
	}

	public void setUsername(String username) {
		set("username", username);
	}

	public String getUsername() {
		return get("username");
	}

	public void setEmail(String email) {
		set("email", email);
	}

	public String getEmail() {
		return get("email");
	}

	public void setPhone(String phone) {
		set("phone", phone);
	}

	public String getPhone() {
		return get("phone");
	}

	public void setStatus(String status) {
		set("status", status);
	}

	public String getStatus() {
		return get("status");
	}

	public void setRoleIds(String roleIds) {
		set("role_ids", roleIds);
	}

	public String getRoleIds() {
		return get("role_ids");
	}

	public void setRecordHash(String recordHash) {
		set("record_hash", recordHash);
	}

	public String getRecordHash() {
		return get("record_hash");
	}

	protected String getTable() {
		return "common_user";
	}
}
