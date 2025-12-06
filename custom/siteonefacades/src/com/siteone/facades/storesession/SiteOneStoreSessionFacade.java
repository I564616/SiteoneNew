/**
 *
 */
package com.siteone.facades.storesession;

import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;

import java.util.List;


/**
 * @author 965504
 *
 *
 */
public interface SiteOneStoreSessionFacade extends StoreSessionFacade
{
	public void setSessionStore(PointOfServiceData pointOfServiceData);

	void setNearbyStoresInSession(List<PointOfServiceData> nearbyStoresList);

	void setNurseryNearbyBranches(List<PointOfServiceData> nurseryBuyingGroup);

	void setExtendedNearbyStoresInSession(List<PointOfServiceData> extendedNearbyStoresList);

	void setInstockFilterSelected(boolean instockFilterSelected);

	boolean getInstockFilterSelected();

	List<PointOfServiceData> getNearbyStoresFromSession();

	List<PointOfServiceData> getExtendedNearbyStoresFromSession();
	
	List<PointOfServiceData> getNurseryNearbyBranchesFromSession();

	public PointOfServiceData getSessionStore();

	public void removeSessionStore();

	public void setSessionPageSize(final String pageSize);

	String getSessionPageSize();

	public void setSessionTabId(final String tabId);

	String getSessionTabId();
	
	public void setCurrentBaseStore(String baseStoreId);
	
	public void removeCurrentBaseStore();


	/**
	 * @return
	 */
	B2BUnitData getSessionShipTo();

	/**
	 * @param unitData
	 */
	void setSessionShipTo(B2BUnitData unitData);

	/**
	 *
	 */
	void removeSessionShipTo();

	/**
	 *
	 */
	void setGeolocatedSessionStore();
	
	PointOfServiceData getNearestStoreFromHomeBranch();

	List<PointOfServiceData> sortNearbyStoresBasedOnDistanceFromHomeBranch(boolean isPDP);
	
	public List<PointOfServiceData> sortHubStoresBasedOnDistanceFromHomeBranch();
}
