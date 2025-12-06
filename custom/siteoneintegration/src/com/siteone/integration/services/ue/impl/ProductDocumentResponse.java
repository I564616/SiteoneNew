package com.siteone.integration.services.ue.impl;

public class ProductDocumentResponse {
    public int SkuID;
    public int ProdResourceID;
    public int IsFromMultimediaDB;
    public String ProdResourceType;
    public String Description;
    public String ContentType;
    public String BinaryData;

    public void setSkuId(final int SkuID)
    {
        this.SkuID = SkuID;
    }

    public int getSkuID()
    {
        return SkuID;
    }

    public int getProdResourceID()
    {
        return ProdResourceID;
    }

    public void setProdResourceID(final int ProdResourceID)
    {
        this.ProdResourceID = ProdResourceID;
    }

    public int getIsFromMultimediaDB()
    {
        return IsFromMultimediaDB;
    }

    public void setIsFromMultimediaDB(final int IsFromMultimediaDB) { this.IsFromMultimediaDB = IsFromMultimediaDB; }

    public String getProdResourceType()
    {
        return ProdResourceType;
    }

    public void setProdResourceType(final String ProdResourceType)
    {
        this.ProdResourceType = ProdResourceType;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription(final String Description)
    {
        this.Description = Description;
    }

    public String getContentType()
    {
        return ContentType;
    }

    public void setContentType(final String ContentType)
    {
        this.ContentType = ContentType;
    }

    public String getBinaryData()
    {
        return BinaryData;
    }

    public void setBinaryData(final String BinaryData)
    {
        this.BinaryData = BinaryData;
    }
}
