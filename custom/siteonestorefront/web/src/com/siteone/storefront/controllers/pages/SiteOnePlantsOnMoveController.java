package com.siteone.storefront.controllers.pages;

        import com.siteone.facades.customer.SiteOneCustomerFacade;
        import com.siteone.facades.neareststore.SiteOneStoreFinderFacade;
        import de.hybris.platform.acceleratorservices.customer.CustomerLocationService;
        import de.hybris.platform.acceleratorservices.store.data.UserLocationData;
        import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
        import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
        import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
        import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
        import de.hybris.platform.commercefacades.customer.CustomerFacade;
        import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
        import de.hybris.platform.commerceservices.store.data.GeoPoint;
        import de.hybris.platform.util.Config;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

        import jakarta.annotation.Resource;
        import jakarta.servlet.http.HttpServletRequest;
        import jakarta.servlet.http.HttpServletResponse;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Set;
        import java.util.TreeMap;


@Controller
@RequestMapping(value = "/plantsonmove" )
public class SiteOnePlantsOnMoveController extends AbstractPageController
{
    @Resource(name = "storeFinderFacade")
    private SiteOneStoreFinderFacade storeFinderFacade;

    @Resource(name = "customerFacade")
    private CustomerFacade customerFacade;

    @Resource(name = "customerLocationService")
    private CustomerLocationService customerLocationService;

    private static final String POM_PAGE = "plantsonmoves";
    private static final String POM = "Plantsonmove";
    private static final String NURSERY = "Nursery";
    private static final String SPECIALITY_POM = Config.getString("plantsonmove.specialities", "Nursery");

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SiteOnePlantsOnMoveController.class);

    @GetMapping
    public String statePoMoveDirectory(final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
    {
        final Set<String> states = storeFinderFacade.getListofStatesFromSpecialty(NURSERY);
        model.addAttribute("statesList", states);
        String path = request.getServletPath();
        if (null != path && path.startsWith("/"))
        {
            path = path.substring(1, path.length());
        }
        else {
            path = POM_PAGE;
        }
        setUpPoMoveBreadcrumb(model, path);
        storeCmsPageInModel(model, getContentPageForLabelOrId(path));
        setUpMetaDataForContentPage(model, getContentPageForLabelOrId(path));
        return getViewForPage(model);

    }


    @PostMapping("/state")
    public @ResponseBody TreeMap<String, List<PointOfServiceData>> findGeoStores(@RequestParam(value = "state")
                                                                                 final String state, final Model model, final HttpServletRequest request, final HttpServletResponse response)

    {
        updateLocalUserPreferences(null, state);
        final TreeMap<String, List<PointOfServiceData>> pos = storeFinderFacade.getListofStoresFromSpecialty(state,SPECIALITY_POM);
        return pos;

    }

    @ModelAttribute("myStoresIdList")
    public List<String> getMyStoresIdList()
    {
        return (((SiteOneCustomerFacade) customerFacade).getMyStoresIdList());
    }

    protected void updateLocalUserPreferences(final GeoPoint geoPoint, final String location)
    {
        final UserLocationData userLocationData = new UserLocationData();
        userLocationData.setSearchTerm(location);
        userLocationData.setPoint(geoPoint);
        customerLocationService.setUserLocation(userLocationData);
    }


    private void setUpPoMoveBreadcrumb(final Model model, final String path)
    {
        final List<Breadcrumb> breadcrumbs = new ArrayList<>();

            final Breadcrumb breadcrumb = new Breadcrumb("#",
                    getMessageSource().getMessage("breadcrumb.pom", null, getI18nService().getCurrentLocale()), null);
            breadcrumbs.add(breadcrumb);
        model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadcrumbs);

    }

}
