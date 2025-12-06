package com.siteone.v2.config;

import de.hybris.platform.servicelayer.config.impl.TenantAwareHybrisPropertiesFactory;

import java.io.InputStream;
import java.util.Properties;

import wiremock.org.slf4j.Logger;
import wiremock.org.slf4j.LoggerFactory;


public class OCCPropertiesFactory extends TenantAwareHybrisPropertiesFactory
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OCCPropertiesFactory.class);

    public static final String V2_SWAGGER_PROPERTIES = "v2/swagger.properties";

    @Override
    public Properties getObject() throws Exception
    {
        Properties properties = super.getObject();
        if (properties != null)
        {
            properties.putAll(loadProperties(V2_SWAGGER_PROPERTIES));
        }
        return properties;
    }

    private Properties loadProperties(String propertiesFileName)
    {
        Properties properties = new Properties();

        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFileName))
        {
            if (inputStream != null)
            {
                properties.load(inputStream);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("cannot load OCC property file", e);
        }

        return properties;
    }
}
