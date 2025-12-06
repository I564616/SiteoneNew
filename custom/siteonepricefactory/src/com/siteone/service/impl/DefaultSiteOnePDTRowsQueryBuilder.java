package com.siteone.service.impl;

import de.hybris.platform.europe1.jalo.PDTRowsQueryBuilder.QueryWithParams;
import com.siteone.service.SiteOnePDTRowsQueryBuilder;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import de.hybris.platform.core.PK;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import de.hybris.platform.europe1.jalo.Europe1PriceFactory;

public class DefaultSiteOnePDTRowsQueryBuilder implements SiteOnePDTRowsQueryBuilder
{
	private final String type;
	private boolean anyProduct;
	private PK productPk;
	private PK productGroupPk;
	private String productId;
	private boolean anyUser;
	private PK userPk;
	private PK userGroupPk;
	private PK posPK;

	public DefaultSiteOnePDTRowsQueryBuilder(String type) {
		this.type = (String) Objects.requireNonNull(type);
	}

	public SiteOnePDTRowsQueryBuilder withAnyProduct() {
		this.anyProduct = true;
		return this;
	}

	public SiteOnePDTRowsQueryBuilder withProduct(PK productPk) {
		this.productPk = productPk;
		return this;
	}

	public SiteOnePDTRowsQueryBuilder withProductGroup(PK productGroupPk) {
		this.productGroupPk = productGroupPk;
		return this;
	}

	public SiteOnePDTRowsQueryBuilder withProductId(String productId) {
		this.productId = productId;
		return this;
	}

	public SiteOnePDTRowsQueryBuilder withAnyUser() {
		this.anyUser = true;
		return this;
	}

	public SiteOnePDTRowsQueryBuilder withUser(PK userPk) {
		this.userPk = userPk;
		return this;
	}

	public SiteOnePDTRowsQueryBuilder withUserGroup(PK userGroupPk) {
		this.userGroupPk = userGroupPk;
		return this;
	}
	
	public SiteOnePDTRowsQueryBuilder withPOS(PK posPK) {
		this.posPK = posPK;
		return this;
	}

	private Map<String, Object> getProductRelatedParameters() {
		Builder<String, Object> params = ImmutableMap.builder();
		if (this.anyProduct) {
			params.put("anyProduct", Europe1PriceFactory.MATCH_ANY);
		}

		if (this.productPk != null) {
			params.put("product", this.productPk.getLong());
		}

		if (this.productGroupPk != null) {
			params.put("productGroup", this.productGroupPk.getLong());
		}

		return params.build();
	}

	private Map<String, Object> getPOSParameters() {
		Builder<String, Object> params = ImmutableMap.builder();
		
		if (this.posPK != null) {
			params.put("pos", this.posPK.getLong());
		}

		return params.build();
	}
	
	private Map<String, Object> getUserRelatedParameters() {
		Builder<String, Object> params = ImmutableMap.builder();
		if (this.anyUser) {
			params.put("anyUser", Europe1PriceFactory.MATCH_ANY);
		}

		if (this.userPk != null) {
			params.put("user", this.userPk.getLong());
		}

		if (this.userGroupPk != null) {
			params.put("userGroup", this.userGroupPk.getLong());
		}

		return params.build();
	}

	public QueryWithParams build() {
		StringBuilder query = new StringBuilder();
		Builder<String, Object> params = ImmutableMap.builder();
		Map<String, Object> productParams = this.getProductRelatedParameters();
		Map<String, Object> userParams = this.getUserRelatedParameters();
		Map<String, Object> posParams = this.getPOSParameters();
		boolean addPricesByProductId = this.productId != null;
		boolean isUnion = false;
		boolean matchByProduct = !productParams.isEmpty();
		boolean matchByUser = !userParams.isEmpty();
		boolean matchPOS = !posParams.isEmpty();
		if (!matchByProduct && !matchByUser && !addPricesByProductId) {
			return new QueryWithParams("select {PK} from {" + this.type + "}", Collections.EMPTY_MAP,Collections.EMPTY_LIST);
		} else {
			if (matchByProduct || matchByUser) {
				query.append("select {PK} from {").append(this.type).append("} where ");
				if (matchByProduct) {
					query.append("{").append("productMatchQualifier").append("} in (?");
					query.append(Joiner.on(", ?").join(productParams.keySet())).append(")");
					params.putAll(productParams);
					if (matchByUser) {
						query.append(" and ");
					}
				}

				if (matchByUser) {
					query.append("{").append("userMatchQualifier").append("} in (?");
					query.append(Joiner.on(", ?").join(userParams.keySet())).append(")");
					params.putAll(userParams);
				}
			}

			if (addPricesByProductId) {
				if (matchByProduct || matchByUser) {
					query.append("}} UNION {{");
					isUnion = true;
				}

				query.append("select {PK} from {").append(this.type).append("} where {");
				query.append("productMatchQualifier").append("}=?matchByProductId and {");
				query.append("productId").append("}=?").append("productId");
				if(matchPOS){
					query.append(" and {").append("pointofservice").append("} in (?").append("posPK)");
				}
				params.put("matchByProductId", Europe1PriceFactory.MATCH_BY_PRODUCT_ID);
				params.put("productId", this.productId);
				params.put("posPK", this.posPK);
				if (matchByUser) {
					query.append(" and {").append("userMatchQualifier").append("} in (?");
					query.append(Joiner.on(", ?").join(userParams.keySet())).append(")");
				}
			}

			StringBuilder resultQuery;
			if (isUnion) {
				resultQuery = (new StringBuilder("select x.PK from ({{")).append(query).append("}}) x");
			} else {
				resultQuery = query;
			}

			return new QueryWithParams(resultQuery.toString(), params.build(),Collections.EMPTY_LIST);
		}
	}

	
}
