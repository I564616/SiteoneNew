/**
 *
 */
package com.siteone.storefront.validator;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.siteone.storefront.forms.SiteoneCartUploadForm;
import com.siteone.storefront.forms.SiteoneSavedListCreateForm;


/**
 * @author bs
 *
 */

@Component("siteOneCSVUploadListValidator")
public class SiteOneCSVUploadListValidator implements Validator
{
	public static final String IMPORT_CSV_FILE_MAX_SIZE_BYTES_KEY = "import.csv.file.max.size.bytes";
	public static final String IMPORT_CSV_FILE_MAX_PRODUCTS_LIMIT = "import.csv.file.max.products.limits";
	public static final String IMPORT_CSV_FILE_MAX_CART_PRODUCTS_LIMIT = "import.csv.file.max.savedcart.products.limits";
	public static final String CSV_FILE_FIELD = "csvFile";
	public static final String TEXT_CSV_CONTENT_TYPE = "text/csv";
	public static final String APP_EXCEL_CONTENT_TYPE = "application/vnd.ms-excel";
	public static final String CSV_FILE_EXTENSION = ".csv";

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;
	private static final Logger LOG = Logger.getLogger(SiteOneCSVUploadListValidator.class);

	@Override
	public boolean supports(final Class<?> aClass)
	{
		return SiteoneSavedListCreateForm.class.equals(aClass);
	}

	@Override
	public void validate(final Object target, final Errors errors)
	{
		final MultipartFile csvFile;
		long maxProductLimit = 100L;
		if (target instanceof SiteoneSavedListCreateForm)
		{
			final SiteoneSavedListCreateForm siteOneUploadListForm = (SiteoneSavedListCreateForm) target;
			csvFile = siteOneUploadListForm.getCsvFile();
			maxProductLimit = getSiteConfigService().getLong(IMPORT_CSV_FILE_MAX_PRODUCTS_LIMIT, 0);
			if (StringUtils.isBlank(siteOneUploadListForm.getName()))
			{
				errors.rejectValue("name", "list.name.invalid");
				return;
			}
		}
		else
		{
			final SiteoneCartUploadForm siteoneCartUploadForm = (SiteoneCartUploadForm) target;
			csvFile = siteoneCartUploadForm.getCsvFile();
			maxProductLimit = getSiteConfigService().getLong(IMPORT_CSV_FILE_MAX_CART_PRODUCTS_LIMIT, 0);


		}
		try
		{
			final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()));
			final String reader = bufferedReader.readLine();
			if (reader == null || reader.isEmpty())
			{
				errors.rejectValue(CSV_FILE_FIELD, "import.csv.savedCart.fileRequired");
				LOG.error("empty file inside validator");
				return;
			}
		}
		catch (final IOException e)
		{
			LOG.error("Exception while creating PDF : " + e);
			LOG.error(e.getMessage(), e);
		}


		if (csvFile.isEmpty())
		{
			errors.rejectValue(CSV_FILE_FIELD, "import.csv.savedCart.fileRequired");
			return;
		}

		final String fileName = csvFile.getOriginalFilename();
		final String fileContentType = csvFile.getContentType();
		if (!(TEXT_CSV_CONTENT_TYPE.equalsIgnoreCase(fileContentType) || APP_EXCEL_CONTENT_TYPE.equalsIgnoreCase(fileContentType))
				|| fileName == null || !fileName.toLowerCase().endsWith(CSV_FILE_EXTENSION))
		{
			errors.rejectValue(CSV_FILE_FIELD, "import.csv.savedCart.fileCSVRequired");
			return;
		}

		if (csvFile.getSize() > getFileMaxSize())
		{
			errors.rejectValue(CSV_FILE_FIELD, "import.csv.savedCart.fileMaxSizeExceeded");
			return;
		}

		if (maxProductsExceeded(csvFile, maxProductLimit))
		{
			errors.rejectValue(CSV_FILE_FIELD, "import.csv.savedCart.fileMaxProductsExceeded");
			return;
		}
	}

	protected boolean maxProductsExceeded(final MultipartFile csvFile, final long maxProductLimit)
	{
		boolean maxProductExceeded = false;
		try (final InputStream inputStream = csvFile.getInputStream();
				final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));)
		{
			final Stream<String> lines = bufferedReader.lines();
			final Long noOfLines = lines.filter(StringUtils::isNotBlank).count();
			if (noOfLines > maxProductLimit + 1)
			{
				maxProductExceeded = true;
			}
		}
		catch (final IOException e)
		{
			LOG.error(e.getMessage(), e);
		}
		return maxProductExceeded;
	}

	protected long getMaxProductLimit()
	{
		return getSiteConfigService().getLong(IMPORT_CSV_FILE_MAX_PRODUCTS_LIMIT, 0);
	}


	protected long getFileMaxSize()
	{
		return getSiteConfigService().getLong(IMPORT_CSV_FILE_MAX_SIZE_BYTES_KEY, 0);
	}

	protected SiteConfigService getSiteConfigService()
	{
		return siteConfigService;
	}
}