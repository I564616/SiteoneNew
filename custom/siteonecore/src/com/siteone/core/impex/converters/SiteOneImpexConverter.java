/**
 *
 */
package com.siteone.core.impex.converters;

import de.hybris.platform.acceleratorservices.dataimport.batch.converter.ImpexRowFilter;
import de.hybris.platform.acceleratorservices.dataimport.batch.converter.impl.DefaultImpexConverter;
import de.hybris.platform.acceleratorservices.dataimport.batch.converter.impl.NullImpexRowFilter;
import de.hybris.platform.servicelayer.exceptions.SystemException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.util.Assert;


/**
 * @author 1085284
 *
 */
public class SiteOneImpexConverter extends DefaultImpexConverter
{
	private static final char SEMICOLON_CHAR = ';';
	private static final char PLUS_CHAR = '+';
	private static final char SEQUENCE_CHAR = 'S';
	private static final String EMPTY_STRING = "";
	private static final char BRACKET_END = '}';
	private static final char BRACKET_START = '{';
	private static final String SEMICOLON = ";";

	private String header;
	private String impexRow;
	private String type;
	private ImpexRowFilter rowFilter = new NullImpexRowFilter();

	@Override
	public String convert(final Map<Integer, String> row, final Long sequenceId)
	{
		String result = EMPTY_STRING;
		if (!MapUtils.isEmpty(row))
		{
			final StringBuilder builder = new StringBuilder();
			int copyIdx = 0;
			int idx = impexRow.indexOf(BRACKET_START);
			while (idx > -1)
			{
				final int endIdx = impexRow.indexOf(BRACKET_END, idx);
				processRow(row, sequenceId, builder, copyIdx, idx, endIdx);
				copyIdx = endIdx + 1;
				idx = impexRow.indexOf(BRACKET_START, endIdx);
			}
			if (copyIdx < impexRow.length())
			{
				builder.append(impexRow.substring(copyIdx));
			}
			result = builder.toString();
		}
		return result;
	}

	@Override
	protected void processRow(final Map<Integer, String> row, final Long sequenceId, final StringBuilder builder,
			final int copyIdx, final int idx, final int endIdx)
	{
		if (endIdx < 0)
		{
			throw new SystemException("Invalid row syntax [brackets not closed]: " + impexRow);
		}
		builder.append(impexRow.substring(copyIdx, idx));
		if (impexRow.charAt(idx + 1) == SEQUENCE_CHAR)
		{
			builder.append(sequenceId);
		}
		else
		{
			processValues(row, builder, idx, endIdx);
		}
	}

	@Override
	protected void processValues(final Map<Integer, String> row, final StringBuilder builder, final int idx, final int endIdx)
	{
		final boolean mandatory = impexRow.charAt(idx + 1) == PLUS_CHAR;
		Integer mapIdx = null;
		try
		{
			mapIdx = Integer.valueOf(impexRow.substring(mandatory ? idx + 2 : idx + 1, endIdx));
		}
		catch (final NumberFormatException e)
		{
			throw new SystemException("Invalid row syntax [invalid column number]: " + impexRow, e);
		}
		final String colValue = row.get(mapIdx);
		if (mandatory && StringUtils.isBlank(colValue))
		{
			throw new IllegalArgumentException("Missing value for " + mapIdx);
		}
		if (colValue != null)
		{
			escapeQuotes(colValue);
			if (colValue.contains(SEMICOLON) && !(colValue.startsWith("\"") && colValue.endsWith("\"")))
			{
				builder.append("\"" + colValue + "\"");
			}
			else
			{
				builder.append(colValue);
			}
		}
	}

	@Override
	protected String escapeQuotes(final String input)
	{
		final String[] splitedInput = StringUtils.splitPreserveAllTokens(input, SEMICOLON_CHAR);
		final List<String> tmp = new ArrayList<String>();
		for (final String string : splitedInput)
		{
			if (doesNotContainNewLine(string))
			{
				tmp.add(StringEscapeUtils.escapeCsv(string));
			}
			else
			{
				tmp.add(string);
			}
		}
		return StringUtils.join(tmp, SEMICOLON_CHAR);
	}

	@Override
	protected boolean doesNotContainNewLine(final String string)
	{
		return !StringUtils.contains(string, CharUtils.LF);
	}

	/**
	 * @see de.hybris.platform.acceleratorservices.dataimport.batch.converter.ImpexConverter#filter(java.util.Map)
	 */
	@Override
	public boolean filter(final Map<Integer, String> row)
	{
		return rowFilter.filter(row);
	}

	/**
	 * @see de.hybris.platform.acceleratorservices.dataimport.batch.converter.ImpexConverter#getHeader()
	 */
	@Override
	public String getHeader()
	{
		return header;
	}

	/**
	 * @param header
	 *           the header to set
	 */
	@Override
	public void setHeader(final String header)
	{
		Assert.hasText(header, "must have text; it must not be null, empty, or blank");
		this.header = header;
	}

	/**
	 * @param impexRow
	 *           the impexRow to set
	 */
	@Override
	public void setImpexRow(final String impexRow)
	{
		Assert.hasText(impexRow, "must have text; it must not be null, empty, or blank");
		this.impexRow = impexRow;
	}

	/**
	 * @param type
	 *           the type to set
	 */
	@Override
	public void setType(final String type)
	{
		this.type = type;
	}

	/**
	 * @return the type
	 */
	@Override
	public String getType()
	{
		return type;
	}

	/**
	 * @param rowFilter
	 *           the rowFilter to set
	 */
	@Override
	public void setRowFilter(final ImpexRowFilter rowFilter)
	{
		Assert.notNull(rowFilter, "must not be null");
		this.rowFilter = rowFilter;
	}
}
