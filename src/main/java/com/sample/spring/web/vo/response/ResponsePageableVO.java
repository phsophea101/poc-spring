package com.sample.spring.web.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sample.spring.web.vo.request.RequestPageableVO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

public class ResponsePageableVO<T> {
    protected static final long DEFAULT_RECORDS = NumberUtils.LONG_ZERO;
    private Long records = DEFAULT_RECORDS;
    private List<T> items;
    private Integer pages;
    private Integer page;
    public List<T> getItems() {
        return items;
    }
    public void setItems(List<T> items) {
        this.items = items;
    }
    public Integer getPages() {
        return pages;
    }
    public void setPages(Integer pages) {
        this.pages = pages;
    }
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getRecordFrom() {
        return recordFrom;
    }
    public void setRecordFrom(Integer recordFrom) {
        this.recordFrom = recordFrom;
    }
    public Integer getRecordTo() {
        return recordTo;
    }
    public void setRecordTo(Integer recordTo) {
        this.recordTo = recordTo;
    }
    @JsonProperty("record_from")
    private Integer recordFrom;
    @JsonProperty("record_to")
    private Integer recordTo;
    public Long getRecords() {
        return records;
    }
    public void setRecords(Long records) {
        this.records = records;
    }
    public ResponsePageableVO(int records, List<T> items, RequestPageableVO pageable) {
        this((long) records, items, pageable);
    }
    public ResponsePageableVO(long records, List<T> items, RequestPageableVO pageable) {
        this.records = records;
        this.items = items;
        this.pages = (int) Math.ceil((double) this.records / pageable.getRpp());
        this.page = pageable.getPage();
        if (ObjectUtils.isEmpty(this.items)) {
            this.recordFrom = 1;
            this.recordTo = Math.toIntExact(this.records);
        } else {
            this.recordFrom = (this.page * pageable.getRpp()) - pageable.getRpp() + 1;
            this.recordTo = (int) (this.page.equals(this.pages) ? this.records : this.page * pageable.getRpp());
        }
    }
}