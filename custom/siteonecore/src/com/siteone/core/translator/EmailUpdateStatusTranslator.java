/**
 *
 */
package com.siteone.core.translator;

import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.siteone.core.adapter.UpdateEmailStatusAdapter;
import com.siteone.core.enums.UpdateEmailStatusEnum;


/**
 * @author ASaha
 *
 */
public class EmailUpdateStatusTranslator extends AbstractValueTranslator
{
	private static final String DEFAULT_IMPORT_ADAPTER_NAME = "updateEmailStatusAdapter";
	private UpdateEmailStatusAdapter updateEmailStatusAdapter;
	private static final Logger LOGGER = Logger.getLogger(EmailUpdateStatusTranslator.class);

	@Override
	public String exportValue(final Object arg0) throws JaloInvalidParameterException
	{
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.impex.jalo.translators.AbstractValueTranslator#importValue(java.lang.String,
	 * de.hybris.platform.jalo.Item)
	 */
	@Override
	public Object importValue(final String userId, final Item b2bCustomer) throws JaloInvalidParameterException
	{
		LOGGER.info("userId==" + userId);

		String oldEmailId = null;

		if (b2bCustomer != null)
		{
			try
			{
				oldEmailId = b2bCustomer.toString().substring(0, b2bCustomer.toString().indexOf("["));

				LOGGER.info("userId==" + userId + "-----oldEmailId==" + oldEmailId);
				
				if (StringUtils.isNotEmpty(oldEmailId) && StringUtils.isNotEmpty(userId) && !userId.equalsIgnoreCase(oldEmailId))
				{
					getUpdateEmailStatusAdapter().saveOldEmailId(userId, oldEmailId);
					return UpdateEmailStatusEnum.UPDATE_OKTA;
				}
			}
			catch (final IndexOutOfBoundsException ex)
			{
				LOGGER.error(ex.getMessage());
			}
			catch (final Exception e)
			{
				LOGGER.error(e.getMessage());
			}
		}

		return null;
	}

	@Override
	public void init(final StandardColumnDescriptor descriptor)
	{
		super.init(descriptor);
		updateEmailStatusAdapter = (UpdateEmailStatusAdapter) Registry.getApplicationContext().getBean(DEFAULT_IMPORT_ADAPTER_NAME);
	}

	/**
	 * @return the updateEmailStatusAdapter
	 */
	public UpdateEmailStatusAdapter getUpdateEmailStatusAdapter()
	{
		return updateEmailStatusAdapter;
	}

	/**
	 * @param updateEmailStatusAdapter
	 *           the updateEmailStatusAdapter to set
	 */
	public void setUpdateEmailStatusAdapter(final UpdateEmailStatusAdapter updateEmailStatusAdapter)
	{
		this.updateEmailStatusAdapter = updateEmailStatusAdapter;
	}



}
