package com.ts.server.safe.base.controller.comm;

import org.apache.commons.lang3.StringUtils;

/**
 * Http header range
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class RangeSetting {
    private static final String RANGE_SEPARATOR = "-";

    /**
     * 构建{@link RangeSetting}
     */
    public static class Builder {
        private final long totalLen;
        private final String range;

        public Builder(long totalLen, String range) {
            this.totalLen = totalLen;
            this.range = range;
        }

        public RangeSetting build(){
            if(StringUtils.isBlank(range) || !StringUtils.contains(range, '-')){
                return new RangeSetting(0, totalLen -1, totalLen);
            }

            if (StringUtils.startsWith(range,RANGE_SEPARATOR)) {
                long contentLen = Long.parseLong(StringUtils.substring(range, 1));
                return new RangeSetting(totalLen - contentLen, totalLen -1,  totalLen);
            }

            if (StringUtils.endsWith(range,RANGE_SEPARATOR)){
                long  start = Long.parseLong(StringUtils.left(range, range.length() -1));
                return new RangeSetting(start, totalLen -1, totalLen);
            }

            String[] se = StringUtils.split(range, RANGE_SEPARATOR);
            long start = Long.parseLong(se[0]);
            long end = Long.parseLong(se[1]);
            return new RangeSetting(start, end, totalLen);
        }
    }

    private final long start;
    private final long end;
    private final long totalLen;

    private RangeSetting(long start, long end, long totalLen) {
        this.start = start;
        this.end = end;
        this.totalLen = totalLen;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public long getTotalLen() {
        return totalLen;
    }
}
