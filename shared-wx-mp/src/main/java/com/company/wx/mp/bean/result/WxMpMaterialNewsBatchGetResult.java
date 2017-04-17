package com.company.wx.mp.bean.result;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.company.util.json.JacksonHelper;
import com.company.wx.mp.bean.WxMpMaterialNews;

public class WxMpMaterialNewsBatchGetResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private int totalCount;
    private int itemCount;
    private List<WxMaterialNewsBatchGetNewsItem> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public List<WxMaterialNewsBatchGetNewsItem> getItems() {
        return items;
    }

    public void setItems(List<WxMaterialNewsBatchGetNewsItem> items) {
        this.items = items;
    }

    public static WxMpMaterialNewsBatchGetResult fromJson(String json) {
        return JacksonHelper.toObject(json, WxMpMaterialNewsBatchGetResult.class);
    }

    @Override
    public String toString() {
        return "WxMpMaterialNewsBatchGetResult [" + "totalCount=" + totalCount + ", itemCount=" + itemCount
                + ", items=" + items + "]";
    }

    public static class WxMaterialNewsBatchGetNewsItem {
        private String mediaId;
        private Date updateTime;
        private WxMpMaterialNews content;

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

        public WxMpMaterialNews getContent() {
            return content;
        }

        public void setContent(WxMpMaterialNews content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "WxMaterialNewsBatchGetNewsItem [" + "mediaId=" + mediaId + ", updateTime=" + updateTime
                    + ", content=" + content + "]";
        }
    }
}
