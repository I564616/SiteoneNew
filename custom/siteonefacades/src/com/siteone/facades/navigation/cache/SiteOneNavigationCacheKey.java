/**
 *
 */
package com.siteone.facades.navigation.cache;

import de.hybris.platform.core.Registry;
import de.hybris.platform.regioncache.key.CacheKey;
import de.hybris.platform.regioncache.key.CacheUnitValueType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author 1099417
 *
 */
public class SiteOneNavigationCacheKey implements CacheKey
{
	private String code;

	public String getCode()
	{
		return code;
	}

	public void setCode(final String code)
	{
		this.code = code;
	}

	public SiteOneNavigationCacheKey(final String code)
	{
		this.code = code;
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
		final SiteOneNavigationCacheKey cacheKey = (SiteOneNavigationCacheKey) obj;
		return new EqualsBuilder().append(code, cacheKey.getCode()).isEquals();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17, 31).append(code).toHashCode();
	}
}
