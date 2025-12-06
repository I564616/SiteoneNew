package com.siteone.core.translator;

import com.siteone.core.customer.SiteOneCustomerAccountService;
import com.siteone.integration.okta.OKTAAPI;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.header.StandardColumnDescriptor;
import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RemoveCustomerFromOktaTranslator extends AbstractValueTranslator {

    private static final Logger LOGGER = Logger.getLogger(RemoveCustomerFromOktaTranslator.class);
    private OKTAAPI oktaAPI ;
    private UserService userService;
    private ModelService modelService;

    @Override
    public String exportValue(final Object arg0) throws JaloInvalidParameterException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object importValue(final String cellValue, final Item b2bCustomer) throws JaloInvalidParameterException
    {
        if (StringUtils.isNotEmpty(cellValue))
        {
            final String[] splitValues = cellValue.split(",");
            String guid = splitValues[0];
            String uid = splitValues[1];
            final B2BCustomerModel hybrisB2BCustomerModel = (B2BCustomerModel) userService.getUserForUID(uid);

            if (hybrisB2BCustomerModel != null && StringUtils.isNotEmpty(hybrisB2BCustomerModel.getEmail()))
            {
                if (StringUtils.isNotEmpty(hybrisB2BCustomerModel.getGuid()) && !guid.equalsIgnoreCase(hybrisB2BCustomerModel.getGuid()))
                {
                    if (oktaAPI.deleteUser(uid)) {
                        final String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                        String newUid = "bak" + timestamp + "_" + uid;
                        hybrisB2BCustomerModel.setUid(newUid);
                        hybrisB2BCustomerModel.setName("Disabled-" + hybrisB2BCustomerModel.getName());
                        hybrisB2BCustomerModel.setActive(Boolean.FALSE);
                        modelService.save(hybrisB2BCustomerModel);
                        LOGGER.error("Customer Uid changed from " + uid + " to " + newUid);
                    }
                }
                else {
                    oktaAPI.suspendUser(uid);
                }
            }
            return guid;
        }
        else {
            return null;
        }
    }

    @Override
    public void init(final StandardColumnDescriptor descriptor)
    {
        super.init(descriptor);
        oktaAPI  = (OKTAAPI) Registry.getApplicationContext().getBean("oktaAPI");
        userService = (UserService) Registry.getApplicationContext().getBean("userService");
        modelService = (ModelService) Registry.getApplicationContext().getBean("modelService");
    }
}
