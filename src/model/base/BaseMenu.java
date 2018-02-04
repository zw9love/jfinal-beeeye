package model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({ "serial", "unchecked" })
public abstract class BaseMenu<M extends BaseMenu<M>> extends Model<M> implements IBean {
	public void setIds(String ids) {
		set("ids", ids);
	}

	public String getIds() {
		return get("ids");
	}

	public void setNames(String names) {
		set("names", names);
	}

	public String getNames() {
		return get("names");
	}

	public void setLevel(Integer level) {
		set("level", level);
	}

	public Integer getLevel() {
		return get("level");
	}

	public void setParentIds(String parentIds) {
		set("parent_ids", parentIds);
	}

	public String getParentIds() {
		return get("parent_ids");
	}

	public void setDescription(String description) {
		set("description", description);
	}

	public String getDescription() {
		return get("description");
	}

	public void setUrl(String url) {
		set("url", url);
	}

	public String getUrl() {
		return get("url");
	}

	public void setIcon(String icon) {
		set("icon", icon);
	}

	public String getIcon() {
		return get("icon");
	}

	protected String getTable() {
		return "common_menu";
	}
}
