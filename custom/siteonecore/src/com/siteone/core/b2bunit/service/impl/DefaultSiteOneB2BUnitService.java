/**
 *
 */
package com.siteone.core.b2bunit.service.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.impl.DefaultB2BUnitService;
import de.hybris.platform.catalog.model.CatalogUnawareMediaModel;
import de.hybris.platform.commerceservices.model.OrgUnitModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;

import com.siteone.core.article.dao.SiteOneB2BUnitPrincipalGroupMembersDao;
import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;


/**
 * @author Abdul Rahman Sheikh M
 *
 */
public class DefaultSiteOneB2BUnitService extends DefaultB2BUnitService implements SiteOneB2BUnitService
{
	private SiteOneB2BUnitPrincipalGroupMembersDao siteOneB2BUnitPrincipalGroupMembersDao;

	/**
	 * @return the siteOneB2BUnitPrincipalGroupMembersDao
	 */
	public SiteOneB2BUnitPrincipalGroupMembersDao getSiteOneB2BUnitPrincipalGroupMembersDao()
	{
		return siteOneB2BUnitPrincipalGroupMembersDao;
	}

	/**
	 * @param siteOneB2BUnitPrincipalGroupMembersDao
	 *           the siteOneB2BUnitPrincipalGroupMembersDao to set
	 */
	public void setSiteOneB2BUnitPrincipalGroupMembersDao(
			final SiteOneB2BUnitPrincipalGroupMembersDao siteOneB2BUnitPrincipalGroupMembersDao)
	{
		this.siteOneB2BUnitPrincipalGroupMembersDao = siteOneB2BUnitPrincipalGroupMembersDao;
	}



	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;


	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "mediaService")
	private MediaService mediaService;

	@Override
	public List<B2BUnitModel> getShipTosForCurrentCustomer()
	{
		final List<B2BUnitModel> shipTos = new ArrayList<>();

		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		if (null != customer)
		{
			customer.getGroups().stream().forEach(group -> {
				if (group instanceof B2BUnitModel)
				{
					final B2BUnitModel shipto = (B2BUnitModel) group;
					shipTos.add(shipto);
				}
			});
		}

		return shipTos;
	}

	@Override
	public List<B2BUnitModel> getShipTosForCustomer(final String uid)
	{
		final List<B2BUnitModel> shipTos = new ArrayList<>();

		if (null != uid)
		{
			final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getUserForUID(uid);

			if (null != customer)
			{
				customer.getGroups().stream().forEach(group -> {
					if (group instanceof B2BUnitModel)
					{
						final B2BUnitModel shipto = (B2BUnitModel) group;
						shipTos.add(shipto);
					}
				});
			}
		}

		return shipTos;
	}

	@Override
	public B2BUnitModel getParentUnitForCustomer()
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();

		if (null != customer)
		{
			final B2BUnitModel shipTo = (B2BUnitModel) CollectionUtils.find(customer.getGroups(),
					PredicateUtils.instanceofPredicate(B2BUnitModel.class));
			if (null != shipTo)
			{
				return shipTo.getReportingOrganization() != null ? (B2BUnitModel) shipTo.getReportingOrganization() : shipTo;
			}
		}

		return null;
	}

	@Override
	public B2BUnitModel getParentForUnit(final String unitId)
	{
		final B2BUnitModel unit = super.getUnitForUid(unitId);
		if (null != unit)
		{
			return unit.getReportingOrganization() != null ? (B2BUnitModel) unit.getReportingOrganization() : unit;
		}
		return null;
	}

	@Override
	public B2BUnitModel getMainAccountForUnit(final String unitId)
	{
		final B2BUnitModel unit = super.getUnitForUid(unitId);
		final B2BUnitModel parentUnit = getParentForUnit(unitId);

		if (unit != null && parentUnit != null)
		{
			if (unit.getUid().equalsIgnoreCase(parentUnit.getUid()))
			{
				return unit; //Main Account
			}
			else
			{
				return getMainAccountForUnit(parentUnit.getUid());
			}
		}
		return unit;
	}

	@Override
	public B2BUnitModel getDefaultUnitForCurrentCustomer()
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();

		if (null != customer)
		{
			final B2BUnitModel b2bUnitModel = customer.getDefaultB2BUnit();

			return null != b2bUnitModel ? b2bUnitModel : this.getParentUnitForCustomer();
		}

		return null;
	}

	@Override
	public Set<B2BCustomerModel> getAdminUserForUnit(final B2BUnitModel unitModel)
	{
		final Set<B2BCustomerModel> adminUsers = new HashSet<>();

		final Set<B2BCustomerModel> customers = super.getB2BCustomers(unitModel);

		customers.forEach(customer -> {
			if (customer.getGroups().stream().anyMatch(group -> group.getUid().equalsIgnoreCase("b2badmingroup")))
			{
				adminUsers.add(customer);
			}
		});

		return adminUsers;
	}

	@Override
	public boolean isAdminUser()
	{
		final B2BCustomerModel customer = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		if (null != customer)
		{
			final String adminGroupsStr = configurationService.getConfiguration().getString("siteone.admin.groups");
			final List<String> adminGroups = Arrays.asList(adminGroupsStr.split(","));

			for (final String adminGroup : adminGroups)
			{
				if (customer.getGroups().stream().anyMatch(group -> group.getUid().equalsIgnoreCase(adminGroup)))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isOrderingAccount(final String unitId)
	{
		final B2BUnitModel unit = this.getUnitForUid(unitId);

		return unit.getIsOrderingAccount();
	}


	@Override
	public Set<B2BCustomerModel> getB2BCustomers(final B2BUnitModel unit, final String searchTerm, final String searchType)
	{
		return new HashSet<B2BCustomerModel>(
				getSiteOneB2BUnitPrincipalGroupMembersDao().findMembersByType(unit, B2BCustomerModel.class, searchTerm, searchType));
	}

	@Override
	public OrgUnitModel getDivisionForUnit(final String unitId)
	{

		return getSiteOneB2BUnitPrincipalGroupMembersDao().getDivisionForUnit(unitId);
	}


	@Override
	public void uploadImage(final String[] filename, final InputStream inputStream)
	{
		if (null != filename && filename.length > 1)
		{
			final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
			CatalogUnawareMediaModel media = modelService.create(CatalogUnawareMediaModel.class);
			final String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			media.setCode(b2bCustomerModel.getDefaultB2BUnit().getUid() + "_" + timestamp);
			media.setRealFileName(b2bCustomerModel.getDefaultB2BUnit().getUid() + "_" + timestamp + "." + filename[1]);
			modelService.save(media);
			mediaService.setStreamForMedia(media, inputStream);
			b2bCustomerModel.getDefaultB2BUnit().setCustomerLogo(media);
			modelService.save(b2bCustomerModel.getDefaultB2BUnit());
			modelService.refresh(b2bCustomerModel.getDefaultB2BUnit());
		}
	}

	@Override
	public boolean deleteImage()
	{
		final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) b2bCustomerService.getCurrentB2BCustomer();
		return (null != b2bCustomerModel.getDefaultB2BUnit().getCustomerLogo()
				&& mediaService.removeDataFromMediaQuietly(b2bCustomerModel.getDefaultB2BUnit().getCustomerLogo())
				);

	}

}