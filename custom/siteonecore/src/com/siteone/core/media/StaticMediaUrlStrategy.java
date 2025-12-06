/**
 *
 */
package com.siteone.core.media;

import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.media.MediaSource;
import de.hybris.platform.media.storage.MediaStorageConfigService;
import de.hybris.platform.media.storage.MediaStorageConfigService.MediaFolderConfig;
import de.hybris.platform.media.url.MediaURLStrategy;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.util.MediaUtil;

import jakarta.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.base.Preconditions;


/**
 * @author 1190626
 *
 */
public class StaticMediaUrlStrategy implements MediaURLStrategy
{
	private static final Logger LOG = Logger.getLogger(StaticMediaUrlStrategy.class);

	public static final String CONTEXT_PARAM_DELIM = "|";
	public static final String NO_CTX_PART_MARKER = "-";
	public static final String CONTEXT_PARAM = "context";
	public static final String MEDIA_LEGACY_PRETTY_URL = "media.legacy.prettyURL";
	private static final String URL_SEPARATOR = "/";
	private static final String MEDIAWEB_DEFAULT_CONTEXT = "/medias";
	public static final String CONTEXT_PARAM_MARK = "?";
	public static final String CONTEXT_PARAM_AMP = "&";
	public static final String CONTEXT_PARAM_EQ = "=";
	public static final String CONTEXT_PARAM_TRUE = "true";
	public static final String CONTEXT_PARAM_MARKERS = "https://";


	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Value("${mediaweb.webroot:/medias}")
	private String mediaWebRoot;

	private boolean prettyUrlEnabled;

	protected ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	@Override
	public String getUrlForMedia(final MediaFolderConfig config, final MediaSource mediaSource)
	{
		Preconditions.checkArgument(config != null, "Folder config is required to perform this operation");
		Preconditions.checkArgument(mediaSource != null, "MediaSource is required to perform this operation");

		//final String mediaHostName = getConfigurationService().getConfiguration().getString("media.host.name");


		if (mediaSource.getSource() instanceof MediaModel)
		{
			final MediaModel media = (MediaModel) mediaSource.getSource();

			if (media.getLocation() != null)
			{


				final StringBuilder sb = new StringBuilder(URL_SEPARATOR + "medias/");
				sb.append("sys_").append(getTenantId()).append(URL_SEPARATOR);
				sb.append(mediaSource.getLocation());
				LOG.debug("getUrlForMedia - " + sb.toString());
				return sb.toString();

			}
			else
			{
				return new StringBuilder(media.getURL()).toString();
			}
		}

		return assembleUrl(config.getFolderQualifier(), mediaSource);
	}

	public static String getSystemDir()
	{
		final String sysID = Registry.getCurrentTenant().getTenantID();
		return "sys_" + sysID.trim().toLowerCase();
	}


	private String assembleUrl(final String folderQualifier, final MediaSource mediaSource)
	{
		String result = "";
		if ((!(GenericValidator.isBlankOrNull(folderQualifier))) && (!(GenericValidator.isBlankOrNull(mediaSource.getLocation()))))
		{
			if (this.prettyUrlEnabled)
			{
				result = assembleLegacyURL(mediaSource);
			}
			else
			{
				result = assembleURLWithMediaContext(folderQualifier, mediaSource);
			}
		}
		return result;
	}

	private String assembleLegacyURL(final MediaSource mediaSource)
	{
		final StringBuilder sb = new StringBuilder(MediaUtil.addTrailingFileSepIfNeeded(getMediaWebRootContext()));
		sb.append("sys_").append(getTenantId()).append(URL_SEPARATOR);
		sb.append(mediaSource.getLocation());
		LOG.debug("assembleLegacyURL - " + sb.toString());
		return sb.toString();
	}

	private String assembleURLWithMediaContext(final String folderQualifier, final MediaSource mediaSource)
	{
		final StringBuilder builder = new StringBuilder(MediaUtil.addTrailingFileSepIfNeeded(getMediaWebRootContext()));

		final String realFilename = getRealFileNameForMedia(mediaSource);
		if (realFilename != null)
		{
			builder.append(realFilename);
		}

		builder.append(CONTEXT_PARAM_MARK).append(CONTEXT_PARAM).append(CONTEXT_PARAM_EQ);
		builder.append(assembleMediaLocationContext(folderQualifier, mediaSource));
		return builder.toString();
	}

	private String getRealFileNameForMedia(final MediaSource mediaSource)
	{
		final String realFileName = mediaSource.getRealFileName();
		return ((StringUtils.isNotBlank(realFileName)) ? MediaUtil.normalizeRealFileName(realFileName) : null);
	}

	public String getMediaWebRootContext()
	{
		return MediaUtil
				.addLeadingFileSepIfNeeded((StringUtils.isBlank(this.mediaWebRoot)) ? MEDIAWEB_DEFAULT_CONTEXT : this.mediaWebRoot);
	}

	private String assembleMediaLocationContext(final String folderQualifier, final MediaSource mediaSource)
	{
		final StringBuilder builder = new StringBuilder(getTenantId());
		builder.append(CONTEXT_PARAM_DELIM).append(folderQualifier.replace(CONTEXT_PARAM_DELIM, ""));
		builder.append(CONTEXT_PARAM_DELIM).append(mediaSource.getSize());
		builder.append(CONTEXT_PARAM_DELIM).append(getCtxPartOrNullMarker(mediaSource.getMime()));
		builder.append(CONTEXT_PARAM_DELIM).append(getCtxPartOrNullMarker(mediaSource.getLocation()));
		builder.append(CONTEXT_PARAM_DELIM).append(getCtxPartOrNullMarker(mediaSource.getLocationHash()));

		return new Base64(-1, null, true).encodeAsString(builder.toString().getBytes());
	}

	private String getCtxPartOrNullMarker(final String ctxPart)
	{
		return ((StringUtils.isNotBlank(ctxPart)) ? ctxPart : NO_CTX_PART_MARKER);
	}

	protected String getTenantId()
	{
		return Registry.getCurrentTenantNoFallback().getTenantID();
	}

	public String getDownloadUrlForMedia(final MediaStorageConfigService.MediaFolderConfig config, final MediaSource mediaSource)
	{
		final StringBuilder url = new StringBuilder(getUrlForMedia(config, mediaSource));
		if (this.prettyUrlEnabled)
		{
			url.append(CONTEXT_PARAM_MARK);
		}
		else
		{
			url.append(CONTEXT_PARAM_AMP);
		}
		url.append("attachment").append(CONTEXT_PARAM_EQ).append(CONTEXT_PARAM_TRUE);
		return url.toString();
	}

	public void setPrettyUrlEnabled(final boolean prettyUrlEnabled)
	{
		this.prettyUrlEnabled = prettyUrlEnabled;
	}



}
