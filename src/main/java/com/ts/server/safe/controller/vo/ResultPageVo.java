package com.ts.server.safe.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * 输出分页记录
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ResultPageVo<T> extends ResultVo<Collection<T>> {
    @ApiModelProperty(value = "查询当前页数")
    private final int page;
    @ApiModelProperty(value = "每页记录数")
    private final int limit;
    @ApiModelProperty(value = "总记录数，不查询记录时返回-1L")
    private final long count;
    @ApiModelProperty(value = "扩展信息")
    private final Object ext;

    public static class Builder<T> {
        private final int page;
        private final int limit;
        private final Collection<T> rs;
        private Supplier<Long> count = () -> -1L;
        private Object ext;

        public Builder(int page, int limit, Collection<T> rs){
            this.page = page;
            this.limit = limit;
            this.rs = rs;
        }

        public Builder<T> count(boolean isCount, Supplier<Long> count){
            if(isCount){
                this.count = count;
            }
            return this;
        }

        public Builder<T> setExt(Object ext){
            this.ext = ext;
            return this;
        }

        public ResultPageVo<T> build(){
            return new ResultPageVo<>(page, limit, count.get(), rs, ext);
        }
    }

    private ResultPageVo(int page, int limit, long count, Collection<T> rs, Object ext){
        super(0, new String[0], rs);
        this.page = page;
        this.limit = limit;
        this.count = count;
        this.ext = ext;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public long getCount() {
        return count;
    }

    public Object getExt() {
        return ext;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("page", page)
                .append("limit", limit)
                .append("count", count)
                .append("ext", ext)
                .toString();
    }
}
