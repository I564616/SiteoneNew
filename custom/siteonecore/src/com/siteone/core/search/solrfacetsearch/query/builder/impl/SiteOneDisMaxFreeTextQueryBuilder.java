/**
 *
 */
package com.siteone.core.search.solrfacetsearch.query.builder.impl;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.search.*;
import de.hybris.platform.solrfacetsearch.search.impl.DisMaxFreeTextQueryBuilder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hybris.platform.util.Config;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import jakarta.annotation.Resource;

import com.siteone.core.model.ProprietaryBrandConfigModel;
import com.siteone.core.services.SiteOneProprietaryBrandConfigService;


/**
 * @author i849388
 *
 */
public class SiteOneDisMaxFreeTextQueryBuilder extends DisMaxFreeTextQueryBuilder
{
    public static final String DEF_TYPE = "defType";

    private static final Logger LOG = Logger.getLogger(SiteOneDisMaxFreeTextQueryBuilder.class);
    private static final String DECAY_BOOST_VAR_1 = Config.getString("siteone.solr.boost.decay.var1", "1");
    private static final String DECAY_BOOST_VAR_2 = Config.getString("siteone.solr.boost.decay.var2", "1");

	@Resource(name = "siteOneProprietaryBrandConfigService")
	private SiteOneProprietaryBrandConfigService siteOneProprietaryBrandConfigService;

    @Resource(name = "sessionService")
    private SessionService sessionService;

    @Override
    public String buildQuery(final SearchQuery searchQuery)
    {
        if (StringUtils.isBlank(searchQuery.getUserQuery()))
        {
            return StringUtils.EMPTY;
        }

        final Map<String, List<FieldParameter>> queryFields = new LinkedHashMap<>();

        final List<QueryValue> terms = prepareTerms(searchQuery);
        final List<QueryValue> phraseQueries = preparePhraseQueries(searchQuery);

        final StringBuilder query = new StringBuilder();

        final String queryTerms = getQueryTerms(terms);
        query.append(queryTerms.replace("\\", ""));

        final String invariants = getQueryInvariants(searchQuery);
        query.append(invariants);

        final String freeTextParams = getFreeTextParams(searchQuery);
        query.append(freeTextParams);

        final String qf = getFreeTextQueryFields(searchQuery);
        final String qfz = getFreeTextFuzzyQueryFields(searchQuery);
        final String pf = getFreeTextPhraseQueryFields(searchQuery);
        final String bf= getFreeTextBoostQueryFunction(searchQuery);

        if (!StringUtils.isEmpty(qf)) {
            query.append("&qf=").append(qf);
        }

        LOG.info("********* qf=" + qf);

        if (!StringUtils.isEmpty(qfz)) {
            query.append(qfz);
        }

        LOG.info("********* qfz=" + qfz);

        if (!StringUtils.isEmpty(pf)) {
            query.append("&pf=").append(pf);
        }

        LOG.info("********* pf=" + pf);

        if (!StringUtils.isEmpty(bf)) {
            query.append("&bf=").append(bf);
        }

        LOG.info("********* bf=" + bf);

		final String bq = getBrandBoostQueryFunction();
		query.append("&bq=").append(bq);
		LOG.info("********* bq=" + bq);

        final String solrQuery = query.toString();

        LOG.debug(solrQuery);
        LOG.info("********* solrQuery =" + solrQuery);

        return solrQuery;
    }
	
	
	/**
	 * Get all the boost brand name from Brand Config Service
	 * @return String
	 */
	private String getBrandBoostQueryFunction()
	{
		LOG.info("Entered getBrandBoostQueryFunction method ");
		final String brandBoostvalue = Config.getString(SiteoneCoreConstants.BRAND_BOOST_VALUE, "5.0");
		final String field = "soproductBrandName_string";
		String boostQuery = null;
		final List<ProprietaryBrandConfigModel> proprietaryBrandConfigModels = siteOneProprietaryBrandConfigService
				.getProprietaryBrandConfigByIndex("siteoneIndex");
		String proprietaryBoostQuery = "";
		for (final ProprietaryBrandConfigModel model : proprietaryBrandConfigModels)
		{
			proprietaryBoostQuery += field + ":" + model.getBrandName() + "^" + brandBoostvalue + ",";
		}	
		boostQuery = proprietaryBoostQuery.substring(0, proprietaryBoostQuery.length() - 1);
        LOG.info("********* boostQuery =" + boostQuery);
		return boostQuery;
	}


    protected String getQueryTerms(final List<QueryValue> terms)
    {

        final StringBuilder buffer = new StringBuilder();

        for (final QueryValue queryValue : terms)
        {
            buffer.append(queryValue.getEscapedValue()).append(" ");
        }

        return buffer.toString();
    }

    protected String getFreeTextQueryFields(final SearchQuery searchQuery)
    {
        final StringBuilder builder = new StringBuilder();

        final List<FreeTextQueryField> fields = searchQuery.getFreeTextQueries();

        for (final FreeTextQueryField field : fields)
        {

            String boostString = "";
            if (field.getBoost() != null)
            {
                boostString = "^" + field.getBoost();
            }

            final String translatedField = translateField(field.getField(), searchQuery);

            builder.append(translatedField + boostString).append(" ");

        }

        return builder.toString();

    }

    protected String getFreeTextFuzzyQueryFields(final SearchQuery searchQuery)
    {
        final StringBuilder builder = new StringBuilder();

        final List<FreeTextFuzzyQueryField> fields = searchQuery.getFreeTextFuzzyQueries();

        for (final FreeTextFuzzyQueryField field : fields)
        {

            String boostString = "";
            if (field.getBoost() != null)
            {
                boostString = "^" + field.getBoost();
            }

            final String translatedField = translateField(field.getField(), searchQuery);

            builder
                    .append(
                            translatedField + "~" + (((field.getFuzziness() == null) ? "" : field.getFuzziness()) + boostString))
                    .append(" ");

        }

        return builder.toString();

    }

    protected String getFreeTextPhraseQueryFields(final SearchQuery searchQuery)
    {

        final StringBuilder builder = new StringBuilder();

        final List<FreeTextPhraseQueryField> fields = searchQuery.getFreeTextPhraseQueries();

        for (final FreeTextPhraseQueryField field : fields)
        {
            String slopString = "";
            if (field.getSlop() != null)
            {
                //edismax requires slop param to be set as int
                slopString = "~" + field.getSlop().intValue();
            }

            String boostString = "";
            if (field.getBoost() != null)
            {
                boostString = "^" + field.getBoost();
            }

            final String translatedField = translateField(field.getField(), searchQuery);

            builder.append(translatedField + slopString + boostString).append(" ");

        }
        return builder.toString();

    }

    protected String getFreeTextBoostQueryFields(final SearchQuery searchQuery)
    {

        final StringBuilder builder = new StringBuilder();

        final List<BoostField> fields = searchQuery.getBoosts();

        for (final BoostField field : fields)
        {
            String boostString = "";
            if (field.getBoostValue() != null)
            {
                boostString = "^" + field.getBoostValue();
            }

            builder.append(field.getField() + ":" + field.getValue().toString() + boostString).append(" ");

        }
        return builder.toString();

    }

    protected String getFreeTextBoostQueryFunction(final SearchQuery searchQuery)
    {
        final StringBuilder builder = new StringBuilder();
        final List<BoostField> fields = searchQuery.getBoosts();
        List<String> queryParts = new ArrayList<>();
        String sales_boost_field = "soYtdSales_double";

        final PointOfServiceData homeStore = sessionService.getAttribute("sessionStore");
        if (null != homeStore && null != homeStore.getRegionId()) {
            sales_boost_field = "regionalSales_" + homeStore.getRegionId().trim() + "_double";
        }

        for (final BoostField field : fields)
        {
            queryParts.add("if(" + field.getField() + "," + field.getBoostValue() + ",0)");
        }
        builder.append("sum(max(");
        builder.append(StringUtils.join(queryParts.toArray(new String[queryParts.size()]), ","));

		final PointOfServiceData pointOfService = sessionService.getAttribute("sessionStore");
		final List<PointOfServiceData> stores = sessionService.getAttribute(SiteoneCoreConstants.NEARBY_SESSION_STORES);
		String hubStore = null;
		if (null != pointOfService && null != pointOfService.getHubStores())
		{
			final List<String> hubStoresList = pointOfService.getHubStores();
			hubStore = hubStoresList != null && !hubStoresList.isEmpty() ? hubStoresList.get(0) : null;
		}

        final String isHubStockAvailableBoost = Config.getString("siteone.solr.boost.soIsStockAvailable.boost.hub", "2.65");
      //final String isHubSellableBoost = Config.getString("siteone.solr.boost.isSellable.boost.hub", "2.35");

  		if (hubStore != null)
  		{
  			/*
  			 * builder.append(",if(soisShippable_boolean,max(if(soIsStockAvailable_" + hubStore.trim() + "_boolean," +
  			 * isHubStockAvailableBoost + ",0),"); builder.append("if(isSellable_" + hubStore.trim() + "_boolean," +
  			 * isHubSellableBoost + ",0)),0)");
  			 */
  			builder.append(",if(soisShippable_boolean,if(soIsStockAvailable_" + hubStore.trim() + "_boolean,"
  					+ isHubStockAvailableBoost + ",0),0)");
  		}

        builder.append("),scale(").append(sales_boost_field);
        builder.append(",0,1))");
        return builder.toString();
    }

    protected String translateField(final String fieldName, final SearchQuery searchQuery)
    {
        final String translatedField = escape(
                getFieldNameTranslator().translate(searchQuery, fieldName, FieldNameProvider.FieldType.INDEX));

        return translatedField;

    }


    protected String getFreeTextParams(final SearchQuery searchQuery)
    {
        final StringBuilder buffer = new StringBuilder();

        for (final String paramName : searchQuery.getFreeTextQueryBuilderParameters().keySet())
        {
            final String paramValue = searchQuery.getFreeTextQueryBuilderParameters().get(paramName);

            if (!GROUP_BY_QUERY_TYPE.equals(paramName) && !TIE.equals(paramName) && !(DEF_TYPE).equals(paramName))
            {
                buffer.append("&").append(paramName).append("=").append(paramValue);
            }
        }

        return buffer.toString();
    }

    protected String getQueryInvariants(final SearchQuery searchQuery)
    {

        final StringBuilder buffer = new StringBuilder();

        final String tieParam = searchQuery.getFreeTextQueryBuilderParameters().get(TIE);
        /*final float tie = StringUtils.isNotEmpty(tieParam) ? Float.valueOf(tieParam) : TIE_DEFAULT_VALUE;*/
        final float tie = StringUtils.isNotEmpty(tieParam) ? Float.valueOf(tieParam) : SiteoneCoreConstants.TIE_VALUE;

        final String userTextQuery = searchQuery.getUserQuery();
  		if (userTextQuery != null)
  		{
  			final Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
  			final Matcher matcher = pattern.matcher(userTextQuery);
  			final boolean check = matcher.find();
  			if (check)
  			{
  				@SuppressWarnings("boxing")
  				final Character character = userTextQuery.charAt(userTextQuery.length() - 1);
  				if (character.equals('~'))
  				{
  					buffer.append("&defType=edismax&tie=").append(tie);
  				}
  				else
  				{
  					buffer.append("~&defType=edismax&tie=").append(tie);
  				}
  			}
  			else
  			{
  				buffer.append("&defType=edismax&tie=").append(tie);
  			}
  		}
        return buffer.toString();
    }

    protected String buildQuery(final Map<String, List<FieldParameter>> queryFields, final SearchQuery searchQuery)
    {
        final List<String> joinedQueries = new ArrayList<>();
        final Map<String, String> translatedFields = new HashMap<>();

        for (final Map.Entry<String, List<FieldParameter>> entry : queryFields.entrySet())
        {
            final StringBuilder stringBuilder = new StringBuilder();
            final List<FieldParameter> fields = entry.getValue();

            if (!fields.isEmpty())
            {
                final List<String> groupedQueries = new ArrayList<>();

                for (final FieldParameter field : fields)
                {
                    final String translatedField = translateField(field.getFieldName(), translatedFields, searchQuery);

                    groupedQueries.add("(" + translatedField + ":" + field.getFieldValue() + ")");
                }

                stringBuilder.append('(');
                stringBuilder.append(
                        StringUtils.join(groupedQueries.toArray(new String[groupedQueries.size()]), SearchQuery.Operator.OR.getName()));
                stringBuilder.append(')');
            }

            joinedQueries.add(stringBuilder.toString());
        }

        if (CollectionUtils.isEmpty(joinedQueries))
        {
            return StringUtils.EMPTY;
        }

        final String localParams = getFreeTextParams(searchQuery);

        return localParams + StringUtils.join(joinedQueries, SearchQuery.Operator.OR.getName());
    }
}