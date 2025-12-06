/**
 *
 */
package com.siteone.core.services.impl;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;

import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.siteone.core.cronjob.dao.ImportProductStoresCronJobDao;
import com.siteone.core.services.SiteOneProductService;

import junit.framework.Assert;


/**
 *
 */
@UnitTest
public class DefaultImportQRCodeProductsCronJobServiceTest
{

	@Mock
	private DefaultImportQRCodeProductsCronJobService importQRCodeProductsJob;

	@Mock
	private SiteOneProductService siteOneProductService;

	@Mock
	private MediaService mediaService;

	@Mock
	private ModelService modelService;

	@Mock
	private ImportProductStoresCronJobDao importProductStoresCronJobDao;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		importQRCodeProductsJob = new DefaultImportQRCodeProductsCronJobService();
		importQRCodeProductsJob.setSiteOneProductService(siteOneProductService);
		importQRCodeProductsJob.setModelService(modelService);
		importQRCodeProductsJob.setMediaService(mediaService);
		importQRCodeProductsJob.setImportProductStoresCronJobDao(importProductStoresCronJobDao);
	}

	@Test
	public final void testGetProductQRCode() throws Exception
	{

		final BufferedImage actual = importQRCodeProductsJob.generateQRCodeImage("https://www.siteone.com/en/p/01234");
		Assert.assertNotNull(actual);
	}

	@Test
	public final void testGetProductQRCode1() throws Exception
	{

		BufferedImage actual = null;
		try
		{
			actual = importQRCodeProductsJob.generateQRCodeImage(null);
		}
		catch (final Exception e)
		{
			System.out.println("Exception  occured");
		}

		Assert.assertNull(actual);
	}
}
