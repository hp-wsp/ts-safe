package com.ts.server.safe.channel.domain;

import com.ts.server.safe.base.domain.UniCheckContent;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * 企业检修内容
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CheckContent extends UniCheckContent {
    private String channelId;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CheckContent that = (CheckContent) o;
        return Objects.equals(channelId, that.channelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), channelId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("channelId", channelId)
                .toString();
    }
}
