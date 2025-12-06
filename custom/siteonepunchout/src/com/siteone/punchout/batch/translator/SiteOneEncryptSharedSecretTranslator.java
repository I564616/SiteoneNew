/**
 *
 */
package com.siteone.punchout.batch.translator;

import de.hybris.platform.b2b.punchout.jalo.PunchOutCredential;
import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.persistence.security.PasswordEncoderFactory;

import org.apache.log4j.Logger;



/**
 * 
 *
 */
public class SiteOneEncryptSharedSecretTranslator extends AbstractValueTranslator
{
	private static final Logger LOG = Logger.getLogger(SiteOneEncryptSharedSecretTranslator.class);
	
	@Override
	public void init(final StandardColumnDescriptor descriptor)
	{
		//Do nothing
	}

	@Override
	public Object importValue(final String cellValue, final Item punchOutCred) throws JaloInvalidParameterException
	{
		LOG.info("cellValue==" + cellValue + " ,punchOutCred==" + punchOutCred);

		final String encoding = "md5".intern();
		String identity = null;

		final String password = cellValue;
		if(punchOutCred instanceof PunchOutCredential)
		{
			identity = ((PunchOutCredential) punchOutCred).getIdentity();
		}
		LOG.info("identity=="+identity);


		final PasswordEncoderFactory factory = Registry.getCurrentTenant().getJaloConnection().getPasswordEncoderFactory();
		final String encodedPassword = factory.getEncoder(encoding).encode(identity, password);
		
		return encodedPassword;
	}

	@Override
	public String exportValue(final Object arg0) throws JaloInvalidParameterException
	{

		return null;
	}

}
