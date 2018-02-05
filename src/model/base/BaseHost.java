package model.base;
/**
 * Created by admin on 2018/2/2.
 */
import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class BaseHost<M extends BaseHost<M>> extends Model<M> implements IBean {
	public String getHostIds() {
		return get("host_ids");
	}

	public void setHostIds(String hostIds) {
		set("host_ids", hostIds);
	}

	public String getName() {
		return get("name");
	}

	public void setName(String name) { set("name", name); }

	public String getIp() {
		return get("ip");
	}

	public void setIp(String ip) {
		set("ip", ip);
	}

	public Integer getPort() {
		return get("port");
	}

	public void setPort(Integer port) {
		set("port", port);
	}

	public String getOsType() {
		return get("os_type");
	}

	public void setOsType(String osType) {
		set("os_type", osType);
	}

	public String getOsVersion() {
		return get("os_version");
	}

	public void setOsVersion(String osVersion) {
		set("os_version", osVersion);
	}

	public String getOsArch() {
		return get("os_arch");
	}

	public void setOsArch(String osArch) {
		set("os_arch", osArch);
	}

	public String getLoginName() {
		return get("login_name");
	}

	public void setLoginName(String loginName) {
		set("login_name", loginName);
	}

	public String getLoginPwd() {
		return get("login_pwd");
	}

	public void setLoginPwd(String loginPwd) {
		set("login_pwd", loginPwd);
	}

	public Integer getDefaultRName() {
		return get("defaultRName");
	}

	public void setDefaultRName(Integer defaultRName) {
		set("defaultRName", defaultRName);
	}

	public Integer getDisabledGuest() {
		return get("disabledGuest");
	}

	public void setDisabledGuest(Integer disabledGuest) {
		set("disabledGuest", disabledGuest);
	}

	public Integer getLockedPolicy() {
		return get("lockedPolicy");
	}

	public void setLockedPolicy(Integer lockedPolicy) {
		set("lockedPolicy", lockedPolicy);
	}

	public Integer getPwdcomplex() {
		return get("pwdcomplex");
	}

	public void setPwdcomplex(Integer pwdcomplex) {
		set("pwdcomplex", pwdcomplex);
	}

	public String getPublicKey() {
		return get("public_key");
	}

	public void setPublicKey(String publicKey) {
		set("public_key", publicKey);
	}

	public String getPrivateKey() {
		return get("private_key");
	}

	public void setPrivateKey(String privateKey) {
		set("private_key", privateKey);
	}

	public String getAuthType() {
		return get("auth_type");
	}

	public void setAuthType(String authType) {
		set("auth_type", authType);
	}

	public String getRecordHash() {
		return get("record_hash");
	}

	public void setRecordHash(String recordHash) {
		set("record_hash", recordHash);
	}

	protected String getTable() {
		return "beeeye_host";
	}


	//添加状态
	public Integer getStatus() {
		return get("status");
	}

	public void setStatus(Integer status) {
		set("status", status);
	}
	public String getPath() {
		return get("skip_dir");
	}
	public void setPath(String path) {
		set("skip_dir", path);
	}
}
