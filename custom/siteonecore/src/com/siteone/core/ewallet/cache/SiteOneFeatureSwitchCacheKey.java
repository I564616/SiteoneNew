package com.siteone.core.ewallet.cache;

import de.hybris.platform.core.Registry;
import de.hybris.platform.regioncache.key.CacheKey;
import de.hybris.platform.regioncache.key.CacheUnitValueType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author KARASAN
 *
 */
public class SiteOneFeatureSwitchCacheKey implements CacheKey
{

	private String key;

	public String getKey()
	{
		return key;
	}

	public void setKey(final String key)
	{
		this.key = key;
	}

	public SiteOneFeatureSwitchCacheKey(final String key)
	{
		this.key = key;
	}

	@Override
	public CacheUnitValueType getCacheValueType()
	{
		return null;
	}

	@Override
	public Object getTypeCode()
	{
		return null;
	}

	@Override
	public String getTenantId()
	{
		return Registry.getCurrentTenant().getTenantID();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (obj == this)
		{
			return true;
		}
		if (obj.getClass() != getClass())
		{
			return false;
		}
		final SiteOneFeatureSwitchCacheKey cacheKey = (SiteOneFeatureSwitchCacheKey) obj;
		return new EqualsBuilder().append(key, cacheKey.getKey()).isEquals();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17, 31).append(key).toHashCode();
	}
}
