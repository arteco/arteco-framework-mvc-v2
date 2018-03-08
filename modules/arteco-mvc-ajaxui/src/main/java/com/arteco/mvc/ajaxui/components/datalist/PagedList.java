package com.arteco.mvc.ajaxui.components.datalist;

import com.damnhandy.uri.template.UriTemplate;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

/**
 * Created by rarnau on 13/11/17.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class PagedList<T> extends DataList<T> {

    public static final String PAGE = "page";

    PageReq pageReq;
    Long total;
    PagedListHelper helper;

    public PagedList(PageReq pageReq, List<T> list, Long total) {
        super(list);
        this.pageReq = pageReq;
        this.total = total;
    }

    public void updateLinks(String pageablePath) {
        Long totalPages = getTotalPages();
        UriTemplate template = UriTemplate.buildFromTemplate(pageablePath).build();
        String previous = null;
        String next = null;
        String last = template.set(PAGE, totalPages).expand();
        if (pageReq.getPage() > 0) {
            previous = template.set(PAGE, pageReq.getPage() - 1).expand();
        }
        if (pageReq.getPage() < totalPages - 1) {
            next = template.set(PAGE, pageReq.getPage() + 1).expand();
        }
        this.helper = new PagedListHelper(pageablePath, last, next, previous);
    }

    public Long getTotalPages() {
        return (long) Math.ceil(((float) total) / (float) pageReq.getPageSize());
    }

}
