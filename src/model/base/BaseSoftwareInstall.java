package model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;
/**
 * Created by admin on 2018/2/5.
 */
public class BaseSoftwareInstall<M extends BaseSoftwareInstall<M>> extends Model<M> implements IBean {

    public String getIds() {
        return get("ids");
    }

    public void setIds(String ids) {
        set("ids", ids);
    }

    public String getGroupName() {
        return get("group_name");
    }

    public void setGroupName(String groupName) {
        set("group_name", groupName);
    }

    public String getVersion() {
        return get("version");
    }

    public void setVersion(String version) {
        set("version", version);
    }

    public String getPlatformIds() {
        return get("platform_ids");
    }

    public void setPlatformIds(String platformIds) {
        set("platform_ids", platformIds);
    }

    public String getHash() {
        return get("hash");
    }

    public void setHash(String hash) {
        set("hash", hash);
    }

    protected String getTable() {
        return "common_software_group";
    }
}
