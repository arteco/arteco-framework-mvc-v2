package com.arteco.mvc.ajaxui.components.datalist;

import com.arteco.mvc.ajaxui.components.base.Elem;
import com.arteco.mvc.ajaxui.components.navigation.LinkElem;
import com.arteco.mvc.ajaxui.components.util.ComponentUtils;
import lombok.Data;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.List;

@Data
public class DataListElem<T> implements Elem {
    private DataList<T> dataList;
    private List<DataColElem> cols = new ArrayList<>();

    public DataListElem(List<T> list) {
        this.dataList = new DataList<>(list);
    }

    public DataListElem(PagedList<T> pagedList, String pageablePath) {
        this.dataList = pagedList;
        pagedList.updateLinks(pageablePath);
    }

    public DataListElem(DataList<T> list) {
        this.dataList = list;
    }

    public DataListElem<T> col(Elem title, LazyElem<T> elem) {
        cols.add(new DataColElem<>(title, elem));
        return this;
    }

    @Override
    public String render() {
        VelocityContext context = new VelocityContext();
        context.put("elem", this);
        context.put("LinkElem", LinkElem.class);
        return ComponentUtils.renderVelocity(this, context);
    }

}
