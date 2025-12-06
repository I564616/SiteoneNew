package com.siteone.core.ewallet.cache;

import de.hybris.platform.regioncache.region.impl.DefaultCacheRegion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.siteone.core.ewallet.service.impl.DefaultSiteOneEwalletService;


/**
 * @author KARASAN
 *
 */
public class SiteOneFeatureSwitchCacheService extends DefaultSiteOneEwalletService
{

	private SiteOneFeatureSwitchCacheValueLoader siteOneFeatureSwitchCacheValueLoader;
	private DefaultCacheRegion siteOneFeatureSwitchCacheRegion;

	public SiteOneFeatureSwitchCacheValueLoader getSiteOneFeatureSwitchCacheValueLoader()
	{
		return siteOneFeatureSwitchCacheValueLoader;
	}

	public void setSiteOneFeatureSwitchCacheValueLoader(
			final SiteOneFeatureSwitchCacheValueLoader siteOneFeatureSwitchCacheValueLoader)
	{
		this.siteOneFeatureSwitchCacheValueLoader = siteOneFeatureSwitchCacheValueLoader;
	}

	public DefaultCacheRegion getSiteOneFeatureSwitchCacheRegion()
	{
		return siteOneFeatureSwitchCacheRegion;
	}

	public void setSiteOneFeatureSwitchCacheRegion(final DefaultCacheRegion siteOneFeatureSwitchCacheRegion)
	{
		this.siteOneFeatureSwitchCacheRegion = siteOneFeatureSwitchCacheRegion;
	}

	@Override
	public String getValueForSwitch(final String key)
	{
		try
		{
			final SiteOneFeatureSwitchCacheKey cacheKey = new SiteOneFeatureSwitchCacheKey(key);
			final String value = (String) getSiteOneFeatureSwitchCacheRegion().getWithLoader(cacheKey,
					getSiteOneFeatureSwitchCacheValueLoader());
			return value;
		}
		catch (final Exception e)
		{
			return super.getValueForSwitch(key);
		}
	}

	public List<String> getListOfBranchesUnderFeatureSwitch(final String key)
	{
		final String branches = getValueForSwitch(key);
		List<String> arrayOfBranches = new ArrayList<>();
		if (null != branches)
		{
			arrayOfBranches = Arrays.asList(branches.split(","));
		}
		return arrayOfBranches;
	}

	public boolean isProductPresentUnderFeatureSwitch(final String key, final String skuId)
	{
		final List<String> listOfSkus = getListOfBranchesUnderFeatureSwitch(key);
		if (!CollectionUtils.isEmpty(listOfSkus))
		{
			if (listOfSkus.contains("All") || listOfSkus.stream().anyMatch(t -> t.equalsIgnoreCase(skuId)))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}

	public boolean isAllPresentUnderFeatureSwitch(final String key)
	{
		final List<String> listOfBranches = getListOfBranchesUnderFeatureSwitch(key);
		if (!CollectionUtils.isEmpty(listOfBranches))
		{
			if (listOfBranches.contains("All"))

			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;

	}

	public boolean isBranchPresentUnderFeatureSwitch(final String key, final String storeId)
	{
		final List<String> listOfBranches = getListOfBranchesUnderFeatureSwitch(key);
		if (!CollectionUtils.isEmpty(listOfBranches))
		{
			if (listOfBranches.contains("All") || listOfBranches.stream().anyMatch(t -> t.equalsIgnoreCase(storeId)))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}


	public Map<String, String> getPunchoutB2BUnitMapping(final String key)
	{
		final String punchoutAccounts = getValueForSwitch(key);
		Map<String, String> collect = new HashMap();
		if (!ObjectUtils.isEmpty(punchoutAccounts))
		{
			collect = Arrays.stream(punchoutAccounts.split(",")).map(group -> group.split("\\|", 2))
					.collect(Collectors.toMap(a -> a[0].trim(), a -> a[1]));
		}
		return collect;
	}

	public List<String> getDeliveryEnabledPunchoutAccount(final String key)
	{
		final String b2bAccounts = getValueForSwitch(key);
		List<String> listB2bAccount = new ArrayList<>();
		if (null != b2bAccounts)
		{
			listB2bAccount = Arrays.asList(b2bAccounts.split(","));
		}
		return listB2bAccount;
	}

    public List<String> getListOfB2BUnitsUnderFeatureSwitch(final String key)
    {
        final String b2bUnits = getValueForSwitch(key);
        List<String> arrayOfB2BUnits = new ArrayList<>();
        if (null != b2bUnits)
        {
            arrayOfB2BUnits = Arrays.asList(b2bUnits.split(","));
        }
        return arrayOfB2BUnits;
    }

    public boolean isB2BUnitPresentUnderFeatureSwitch(final String key, final String unitId)
    {
        final List<String> listOfB2BUnits = getListOfB2BUnitsUnderFeatureSwitch(key);
        if (!CollectionUtils.isEmpty(listOfB2BUnits))
        {
            if (listOfB2BUnits.contains("All") || listOfB2BUnits.stream().anyMatch(t -> t.equalsIgnoreCase(unitId)))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }
}
