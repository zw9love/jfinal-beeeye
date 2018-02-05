package model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Created by admin on 2018/2/5.
 */
public class BasePelf<M extends BasePelf<M>> extends Model<M> implements IBean {
    public String getIds() {
        return get("ids");
    }

    public void setIds(String ids) {
        set("ids", ids);
    }

    public String getMd5() {
        return get("md5");
    }

    public void setMd5(String md5) {
        set("md5", md5);
    }

    public String getSha1() {
        return get("sha1");
    }

    public void setSha1(String sha1) {
        set("sha1", sha1);
    }

    public String getHostIds() {
        return get("host_ids");
    }

    public void setHostIds(String hostIds) {
        set("host_ids", hostIds);
    }

    public String getGroupIds() {
        return get("group_ids");
    }

    public void setGroupIds(String groupIds) {
        set("group_ids", groupIds);
    }

    public String getFileShortName() {
        return get("file_short_name");
    }

    public void setFileShortName(String fileShortName) {
        set("file_short_name", fileShortName);
    }

    public String getFullPath() {
        return get("full_path");
    }

    public void setFullPath(String fullPath) {
        set("full_path", fullPath);
    }

    public String getCompany() {
        return get("company");
    }

    public void setCompany(String company) {
        set("company", company);
    }

    public Integer getCTime() {
        return get("c_time");
    }

    public void setCTime(Integer cTime) {
        set("c_time", cTime);
    }

    public Integer getMTime() {
        return get("m_time");
    }

    public void setMTime(Integer mTime) {
        set("m_time", mTime);
    }

    public Integer getVTime() {
        return get("v_time");
    }

    public void setVTime(Integer vTime) {
        set("v_time", vTime);
    }

    public Integer getSize() {
        return get("size");
    }

    public void setSize(Integer size) {
        set("size", size);
    }

    public String getSuffix() {
        return get("suffix");
    }

    public void setSuffix(String suffix) {
        set("suffix", suffix);
    }

    public String getOriginalName() {
        return get("original_name");
    }

    public void setOriginalName(String originalName) {
        set("original_name", originalName);
    }

    public String getVersion() {
        return get("version");
    }

    public void setVersion(String version) {
        set("version", version);
    }

    public String getPelfstatus() {
        return get("pelfstatus");
    }

    public void setPelfstatus(String pelfstatus) {
        set("pelfstatus", pelfstatus);
    }

    protected String getTable() {
        return "beeeye_pelf";
    }
}
