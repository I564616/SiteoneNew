package com.siteone.core.trigger.strategy;

import de.hybris.platform.personalizationservices.model.CxCustomizationModel;
import de.hybris.platform.personalizationservices.model.CxCustomizationsGroupModel;
import de.hybris.platform.personalizationservices.strategies.RankAssignmentStrategy;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;


public class SiteOneCustomizationRankAttributeHandler implements DynamicAttributeHandler<Integer, CxCustomizationModel>
{
	private RankAssignmentStrategy rankAssigmentStrategy;
	@Resource(name = "modelService")
	private ModelService modelService;


	public Integer get(final CxCustomizationModel model)
	{
		final CxCustomizationsGroupModel catalog = model.getGroup();
		if (catalog != null)
		{
			saveSortedCustomizations(catalog);
			return catalog.getCustomizations().indexOf(model);
		}
		else
		{
			return null;
		}
	}

	public void set(final CxCustomizationModel model, final Integer value)
	{
		final CxCustomizationsGroupModel catalog = model.getGroup();
		if (catalog != null)
		{
			saveSortedCustomizations(catalog);
		}
	}

	protected List<CxCustomizationModel> getCustomizationList(final CxCustomizationsGroupModel catalog)
	{
		final List<CxCustomizationModel> customizations = catalog.getCustomizations();
		return customizations == null ? new LinkedList() : new LinkedList(customizations);
	}

	public void saveSortedCustomizations(final CxCustomizationsGroupModel catalog)
	{
		final List<CxCustomizationModel> sortedList = new LinkedList();
		if (catalog.getCustomizations() != null)
		{
			for (final CxCustomizationModel customization : catalog.getCustomizations())
			{
				if (customization.getModifiedRank() == null)
				{
					final CxCustomizationModel cust = catalog.getCustomizations().stream().filter(c -> c.getModifiedRank() != null)
							.max((c1, c2) -> c1.getModifiedRank().compareTo(c2.getModifiedRank())).orElse(null);
					if (cust != null)
					{
						customization.setModifiedRank(cust.getModifiedRank() + 1);
						modelService.save(customization);
					}
				}
			}

			sortedList.addAll(catalog.getCustomizations().stream()
					.sorted((c1, c2) -> Integer.compare(c1.getModifiedRank(), c2.getModifiedRank())).collect(Collectors.toList()));
			if (!sortedList.equals(catalog.getCustomizations()))
			{
				catalog.setCustomizations(sortedList);
				modelService.save(catalog);
			}
		}
	}

	public void setRankAssigmentStrategy(final RankAssignmentStrategy rankAssigmentStrategy)
	{
		this.rankAssigmentStrategy = rankAssigmentStrategy;
	}

	protected RankAssignmentStrategy getRankAssigmentStrategy()
	{
		return this.rankAssigmentStrategy;
	}
}
