///**
// *
// */
//package com.siteone.facades.populators;
//
//import de.hybris.platform.converters.Populator;
//import de.hybris.platform.core.model.product.ProductModel;
//import de.hybris.platform.ruleengineservices.rao.ProductRAO;
//import de.hybris.platform.ruleengineservices.rao.PromotionCategoryRAO;
//import de.hybris.platform.servicelayer.dto.converter.Converter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//import com.siteone.core.model.PromotionProductCategoryModel;
//
//
///**
// * @author BS
// *
// */
//public class PromotionCategoryRaoPopulator implements Populator<PromotionProductCategoryModel, PromotionCategoryRAO>
//{
//	private Converter<ProductModel, ProductRAO> productConverter;
//
//	@Override
//	public void populate(final PromotionProductCategoryModel source, final PromotionCategoryRAO target)
//	{
//		target.setCode(source.getCode());
//		if (Objects.nonNull(source.getProductsList()))
//		{
//			final List<ProductRAO> productRao = new ArrayList<>();
//			for (final ProductModel product : source.getProductsList())
//			{
//				productRao.add((this.getProductConverter().convert(product)));
//			}
//			target.setProductList(productRao);
//		}
//	}
//
//	/**
//	 * @return the productConverter
//	 */
//	public Converter<ProductModel, ProductRAO> getProductConverter()
//	{
//		return productConverter;
//	}
//
//	/**
//	 * @param productConverter
//	 *           the productConverter to set
//	 */
//	public void setProductConverter(final Converter<ProductModel, ProductRAO> productConverter)
//	{
//		this.productConverter = productConverter;
//	}
//}