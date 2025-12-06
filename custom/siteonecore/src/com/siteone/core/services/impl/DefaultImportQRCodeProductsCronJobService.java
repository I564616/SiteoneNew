
package com.siteone.core.services.impl;

import de.hybris.platform.basecommerce.enums.BarcodeType;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.deeplink.model.media.BarcodeMediaModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.siteone.core.cronjob.dao.ImportProductStoresCronJobDao;
import com.siteone.core.model.ImportQRCodeProductsCronJobModel;
import com.siteone.core.services.ImportQRCodeProductsCronJobService;
import com.siteone.core.services.SiteOneProductService;


/**
	*
	*/
public class DefaultImportQRCodeProductsCronJobService implements ImportQRCodeProductsCronJobService
{
	private static final Logger LOG = Logger.getLogger(DefaultImportQRCodeProductsCronJobService.class);

	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;

	@Resource(name = "mediaService")
	private MediaService mediaService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "importProductStoresCronJobDao")
	private ImportProductStoresCronJobDao importProductStoresCronJobDao;



	@Override
	public void importQRCodeForProducts(final ImportQRCodeProductsCronJobModel importQRCodeProductsCronJobModel)
	{

		final List<ProductModel> products = getImportProductStoresCronJobDao().findProductCodeForModifiedProduct();
		String url = "";
		final Date date = new Date();

		for (final ProductModel productModel : products)
		{
			try
			{
				if (null == productModel.getProductQrCode())
				{
					if (productModel.getCatalogVersion().getCatalog().getId().equalsIgnoreCase("siteoneProductCatalog"))
					{
						url = Config.getString("website.siteone-us.https", "https://www.siteone.com");
					}
					else
					{
						url = Config.getString("website.siteone-ca.https", "https://www.siteone.ca");
					}

					final BufferedImage QRImage = this.generateQRCodeImage(productModel.getCode());
					LOG.info("finished generateQRCodeImage  ");
					final String filePath = "productQRCode.png";
					final int size = 125;
					final File qrFile = new File(filePath);

					final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ImageIO.write(QRImage, "png", outputStream); // Passing: ​(RenderedImage im, String formatName, OutputStream output)
					final InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
					LOG.info("Created Input stream  ");
					final BarcodeMediaModel barcodeMediaModel = getModelService().create(BarcodeMediaModel.class);
					barcodeMediaModel.setCatalogVersion(productModel.getCatalogVersion());
					barcodeMediaModel.setCode("productQRCode" + productModel.getCode());
					barcodeMediaModel.setRealFileName("productQRCode" + productModel.getCode() + ".png");
					barcodeMediaModel.setBarcodeText(productModel.getCode());
					barcodeMediaModel.setBarcodeType(BarcodeType.QR);
					getModelService().save(barcodeMediaModel);
					getModelService().refresh(barcodeMediaModel);
					mediaService.setStreamForMedia(barcodeMediaModel, inputStream);

					final Collection<BarcodeMediaModel> barcodeMediaList = new ArrayList<>();
					barcodeMediaList.add(barcodeMediaModel);

					productModel.setProductQrCode(barcodeMediaModel);
					getModelService().saveAll(barcodeMediaModel, productModel);


					LOG.info("Created Barcode media model in  product ");
				}
			}
			catch (final UnknownIdentifierException e)
			{
				LOG.error("Exception occurred in finding the product :ImportProductStoresCronJob code:" + productModel.getCode());
			}
			catch (final Exception e)
			{
				LOG.error("Exception occured ImportProductQRCronJob ", e);
				importQRCodeProductsCronJobModel.setResult(CronJobResult.FAILURE);
				importQRCodeProductsCronJobModel.setStatus(CronJobStatus.ABORTED);
				LOG.error("ImportProductQRCronJob failed!");
			}
		}
		importQRCodeProductsCronJobModel.setLastExecutionTime(date);

		getModelService().save(importQRCodeProductsCronJobModel);

		LOG.info("ImportProductQRCronJob executed successfully!");

	}

	public static BufferedImage generateQRCodeImage(final String barcodeText) throws Exception
	{
		LOG.info("Inside generateQRCodeImage  ");
		final QRCodeWriter barcodeWriter = new QRCodeWriter();
		final BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

	/**
	 * @return the siteOneProductService
	 */
	public SiteOneProductService getSiteOneProductService()
	{
		return siteOneProductService;
	}

	/**
	 * @param siteOneProductService
	 *           the siteOneProductService to set
	 */
	public void setSiteOneProductService(final SiteOneProductService siteOneProductService)
	{
		this.siteOneProductService = siteOneProductService;
	}

	/**
	 * @return the mediaService
	 */
	public MediaService getMediaService()
	{
		return mediaService;
	}

	/**
	 * @param mediaService
	 *           the mediaService to set
	 */
	public void setMediaService(final MediaService mediaService)
	{
		this.mediaService = mediaService;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the importProductStoresCronJobDao
	 */
	public ImportProductStoresCronJobDao getImportProductStoresCronJobDao()
	{
		return importProductStoresCronJobDao;
	}

	/**
	 * @param importProductStoresCronJobDao
	 *           the importProductStoresCronJobDao to set
	 */
	public void setImportProductStoresCronJobDao(final ImportProductStoresCronJobDao importProductStoresCronJobDao)
	{
		this.importProductStoresCronJobDao = importProductStoresCronJobDao;
	}

}
