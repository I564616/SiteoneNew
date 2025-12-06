package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.siteone.core.model.InventoryUPCModel;
import com.siteone.facades.product.data.DataSheetData;
import com.siteone.facades.product.data.InventoryUOMData;


public class SiteOneProductDataForPopupPopulator implements Populator<ProductModel, ProductData>
{

	@Override
	public void populate(final ProductModel productModel, final ProductData productData) throws ConversionException
	{
		
		final List<InventoryUPCModel> inventoryUPC = productModel.getUpcData();
		if (null != inventoryUPC && inventoryUPC.size() > 0)
		{
			final List<InventoryUOMData> inventoryUOMList = new CopyOnWriteArrayList<>();
			boolean hideUom = false;
			boolean singleUom = false;
			int sellableUomCount = 0;
			final List<InventoryUPCModel> inventoryUPCChildList = inventoryUPC.stream()
					.filter(upc -> !upc.getBaseUPC().booleanValue()).collect(Collectors.toList());
			final InventoryUPCModel inventoryUPCParent = inventoryUPC.stream().filter(upc -> upc.getBaseUPC().booleanValue())
					.findFirst().orElse(null);

			if (CollectionUtils.isNotEmpty(inventoryUPCChildList))
			{
				for (final InventoryUPCModel inventoryUOMModel : inventoryUPCChildList)
				{
					final InventoryUOMData inventoryUOMData = new InventoryUOMData();
					inventoryUOMData.setInventoryUOMID(inventoryUOMModel.getInventoryUPCID());
					inventoryUOMData.setDescription(inventoryUOMModel.getInventoryUPCDesc());
					if (null != inventoryUPCParent && null != inventoryUPCParent.getInventoryUPCID())
					{
						inventoryUOMData.setParentInventoryUOMID(inventoryUPCParent.getInventoryUPCID());
					}
					inventoryUOMData.setInventoryMultiplier(inventoryUOMModel.getInventoryMultiplier());
					inventoryUOMData.setHideUOMOnline(
							inventoryUOMModel.getHideUPCOnline() != null ? inventoryUOMModel.getHideUPCOnline() : false);
					if (null != inventoryUOMModel.getInventoryUPCDesc())
					{
						inventoryUOMData.setInventoryUOMDesc(inventoryUOMModel.getInventoryUPCDesc());
						inventoryUOMData.setMeasure(inventoryUOMModel.getInventoryUPCDesc());

					}
					if (inventoryUOMModel.getHideUPCOnline() != null && !inventoryUOMModel.getHideUPCOnline().booleanValue())
					{
						sellableUomCount++;
						inventoryUOMList.add(inventoryUOMData);
					}
				}
				if (null != inventoryUPCParent && null != inventoryUPCParent.getInventoryUPCID()
						&& !inventoryUPCParent.getHideUPCOnline().booleanValue())
				{
					if (CollectionUtils.isNotEmpty(inventoryUOMList) && inventoryUOMList.size() > 0)
					{
						final InventoryUOMData inventoryUOMData = new InventoryUOMData();
						inventoryUOMData.setInventoryUOMID(inventoryUPCParent.getInventoryUPCID());
						inventoryUOMData.setDescription(inventoryUPCParent.getInventoryUPCDesc());
						inventoryUOMData.setParentInventoryUOMID(inventoryUPCParent.getInventoryUPCID());
						inventoryUOMData.setInventoryMultiplier(inventoryUPCParent.getInventoryMultiplier());
						inventoryUOMData.setHideUOMOnline(
								inventoryUPCParent.getHideUPCOnline() != null ? inventoryUPCParent.getHideUPCOnline() : false);

						if (null != inventoryUPCParent.getInventoryUPCDesc())
						{
							inventoryUOMData.setInventoryUOMDesc(inventoryUPCParent.getInventoryUPCDesc());
							inventoryUOMData.setMeasure(inventoryUPCParent.getInventoryUPCDesc());
						}

						inventoryUOMList.add(inventoryUOMData);
					}
					else
					{
						singleUom = true;
						productData.setSingleUom(true);
						productData.setSingleUomMeasure(inventoryUPCParent.getInventoryUPCDesc());
						productData.setSingleUomDescription(inventoryUPCParent.getInventoryUPCDesc());
					}
				}
				else if (CollectionUtils.isNotEmpty(inventoryUOMList) && inventoryUOMList.size() == 1)
				{
					hideUom = true;
					productData.setInventoryMultiplier(inventoryUOMList.get(0).getInventoryMultiplier());
				}
			}
			else
			{
				singleUom = true;
				productData.setSingleUom(true);
				productData.setSingleUomMeasure(inventoryUPCParent.getInventoryUPCDesc());
				productData.setSingleUomDescription(inventoryUPCParent.getInventoryUPCDesc());
				if (null != inventoryUPCParent && null != inventoryUPCParent.getInventoryUPCID()
						&& inventoryUPCParent.getHideUPCOnline().booleanValue())
				{
					hideUom = true;
				}

			}
			productData.setHideUom(hideUom);
			productData.setSingleUom(singleUom);
			Collections.sort(inventoryUOMList,
					(o1, o2) -> o1.getInventoryMultiplier().intValue() - o2.getInventoryMultiplier().intValue());
			productData.setSellableUoms(inventoryUOMList);
			productData.setSellableUomsCount(Integer.valueOf(inventoryUOMList.size()));
		}

	}



}




