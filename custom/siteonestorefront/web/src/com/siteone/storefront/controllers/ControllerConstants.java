/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.storefront.controllers;

import de.hybris.platform.acceleratorcms.model.components.CartSuggestionComponentModel;
import de.hybris.platform.acceleratorcms.model.components.CategoryFeatureComponentModel;
import de.hybris.platform.acceleratorcms.model.components.DynamicBannerComponentModel;
import de.hybris.platform.acceleratorcms.model.components.FooterNavigationComponentModel;
import de.hybris.platform.acceleratorcms.model.components.MiniCartComponentModel;
import de.hybris.platform.acceleratorcms.model.components.NavigationBarComponentModel;
import de.hybris.platform.acceleratorcms.model.components.ProductFeatureComponentModel;
import de.hybris.platform.acceleratorcms.model.components.ProductReferencesComponentModel;
import de.hybris.platform.acceleratorcms.model.components.PurchasedCategorySuggestionComponentModel;
import de.hybris.platform.acceleratorcms.model.components.SimpleResponsiveBannerComponentModel;
import de.hybris.platform.acceleratorcms.model.components.SubCategoryListComponentModel;
import de.hybris.platform.cms2.model.contents.components.CMSLinkComponentModel;
import de.hybris.platform.cms2lib.model.components.ProductCarouselComponentModel;

import com.siteone.core.model.HomePageResponsiveBannerComponentModel;
import com.siteone.core.model.SiteOneArticleDetailComponentModel;
import com.siteone.core.model.SiteOneArticleTagsComponentModel;
import com.siteone.core.model.SiteOneBottomBannerListComponentModel;
import com.siteone.core.model.SiteOneCuratedPLPComponentModel;
import com.siteone.core.model.SiteOneCuratedPLPHSProductComponentModel;
import com.siteone.core.model.SiteOneCuratedPLPStoreComponentModel;
import com.siteone.core.model.SiteOneFeatureProductComponentModel;
import com.siteone.core.model.SiteOneHomePageProductsComponentModel;
import com.siteone.core.model.SiteOnePartnerPerkParagraphComponentModel;
import com.siteone.core.model.SiteOnePilotPartnerParagraphComponentModel;
import com.siteone.core.model.SiteOnePromotionalResponsiveBannerComponentModel;
import com.siteone.core.model.VerticalBarComponentModel;
import com.siteone.core.model.components.NewsComponentModel;


/**
 */
public interface ControllerConstants
{
	// Constant names cannot be changed due to their usage in dependant extensions, thus nosonar

	/**
	 * Class with action name constants
	 */
	interface Actions
	{
		interface Cms // NOSONAR
		{
			String _Prefix = "/view/"; // NOSONAR
			String _Suffix = "Controller"; // NOSONAR

			/**
			 * Default CMS component controller
			 */
			String DefaultCMSComponent = _Prefix + "DefaultCMSComponentController"; // NOSONAR

			/**
			 * CMS components that have specific handlers
			 */
			String PurchasedCategorySuggestionComponent = _Prefix + PurchasedCategorySuggestionComponentModel._TYPECODE + _Suffix; // NOSONAR
			String CartSuggestionComponent = _Prefix + CartSuggestionComponentModel._TYPECODE + _Suffix; // NOSONAR
			String ProductReferencesComponent = _Prefix + ProductReferencesComponentModel._TYPECODE + _Suffix; // NOSONAR
			String ProductCarouselComponent = _Prefix + ProductCarouselComponentModel._TYPECODE + _Suffix; // NOSONAR
			String MiniCartComponent = _Prefix + MiniCartComponentModel._TYPECODE + _Suffix; // NOSONAR
			String ProductFeatureComponent = _Prefix + ProductFeatureComponentModel._TYPECODE + _Suffix; // NOSONAR
			String CategoryFeatureComponent = _Prefix + CategoryFeatureComponentModel._TYPECODE + _Suffix; // NOSONAR
			String NavigationBarComponent = _Prefix + NavigationBarComponentModel._TYPECODE + _Suffix; // NOSONAR
			String CMSLinkComponent = _Prefix + CMSLinkComponentModel._TYPECODE + _Suffix; // NOSONAR
			String SiteOneArticleTagsComponent = _Prefix + SiteOneArticleTagsComponentModel._TYPECODE + _Suffix; // NOSONAR
			String SiteOneArticleDetailComponent = _Prefix + SiteOneArticleDetailComponentModel._TYPECODE + _Suffix; // NOSONAR
			String DynamicBannerComponent = _Prefix + DynamicBannerComponentModel._TYPECODE + _Suffix; // NOSONAR
			String SubCategoryListComponent = _Prefix + SubCategoryListComponentModel._TYPECODE + _Suffix; // NOSONAR
			String SimpleResponsiveBannerComponent = _Prefix + SimpleResponsiveBannerComponentModel._TYPECODE + _Suffix; // NOSONAR

			String HomePageResponsiveBannerComponent = _Prefix + HomePageResponsiveBannerComponentModel._TYPECODE + _Suffix; // NOSONAR
			String SiteOneFeatureProductComponent = _Prefix + SiteOneFeatureProductComponentModel._TYPECODE + _Suffix;
			String SiteOneHomePageProductsComponent = _Prefix + SiteOneHomePageProductsComponentModel._TYPECODE + _Suffix;
			String SiteOnePromotionalResponsiveBannerComponent = _Prefix + SiteOnePromotionalResponsiveBannerComponentModel._TYPECODE
					+ _Suffix;
			String VerticalBarComponent = _Prefix + VerticalBarComponentModel._TYPECODE + _Suffix;
			String FooterNavigationComponent = _Prefix + FooterNavigationComponentModel._TYPECODE + _Suffix;
			String NewsComponent = _Prefix + NewsComponentModel._TYPECODE + _Suffix;
			String SiteOneBottomBannerListComponent = _Prefix + SiteOneBottomBannerListComponentModel._TYPECODE + _Suffix; // NOSONAR
			String SiteOneCuratedPLPComponent = _Prefix + SiteOneCuratedPLPComponentModel._TYPECODE + _Suffix;
			String SiteOnePartnerPerkParagraphComponent = _Prefix + SiteOnePartnerPerkParagraphComponentModel._TYPECODE + _Suffix;
			String SiteOnePilotPartnerParagraphComponent = _Prefix + SiteOnePilotPartnerParagraphComponentModel._TYPECODE + _Suffix;
			String SiteOneCuratedPLPStoreComponent = _Prefix + SiteOneCuratedPLPStoreComponentModel._TYPECODE + _Suffix;
			String SiteOneCuratedPLPHSProductComponent = _Prefix + SiteOneCuratedPLPHSProductComponentModel._TYPECODE + _Suffix;
		}
	}

	/**
	 * Class with view name constants
	 */
	interface Views
	{
		interface Cms // NOSONAR
		{
			String ComponentPrefix = "cms/"; // NOSONAR
		}

		interface Pages
		{
			interface Account // NOSONAR
			{
				String AccountLoginPage = "pages/account/accountLoginPage"; // NOSONAR
				String AccountHomePage = "pages/account/accountHomePage"; // NOSONAR
				String AccountOrderHistoryPage = "pages/account/accountOrderHistoryPage"; // NOSONAR
				String AccountOrderPage = "pages/account/accountOrderPage"; // NOSONAR
				String AccountProfilePage = "pages/account/accountProfilePage"; // NOSONAR
				String AccountProfileEditPage = "pages/account/accountProfileEditPage"; // NOSONAR
				String AccountProfileEmailEditPage = "pages/account/accountProfileEmailEditPage"; // NOSONAR
				String AccountChangePasswordPage = "pages/account/accountChangePasswordPage"; // NOSONAR
				String AccountAddressBookPage = "pages/account/accountAddressBookPage"; // NOSONAR
				String AccountEditAddressPage = "pages/account/accountEditAddressPage"; // NOSONAR
				String AccountPaymentInfoPage = "pages/account/accountPaymentInfoPage"; // NOSONAR
				String AccountRegisterPage = "pages/account/accountRegisterPage"; // NOSONAR
				String AccountDashboardPage = "pages/account/accountDashboardPage"; // NOSONAR
				String ShipToPage = "pages/account/shipToPage";// NOSONAR
				String ShipToPagePopup = "pages/account/shipToPagePopup";// NOSONAR
				String InvoiceShipToPagePopup = "pages/account/invoiceShipToPagePopup";
				String OrderShipToPagePopup = "pages/account/orderShipToPagePopup";
				String AccountInformationPage = "pages/account/accountinfo";// NOSONAR
				String RequestAccountPage = "pages/account/requestaccount";// NOSONAR
				String RequestAccountOnlineAccessPage = "pages/account/requestAccountOnlineAccess";// NOSONAR
				String RequestAccountSuccessPage = "pages/account/requestaccountsuccess";// NOSONAR
				String PurchasedProductPage = "pages/account/purchasedProductPage";

				String AccountOverviewPage = "pages/account/accountOverviewPage";// NOSONAR

				String InvoiceListingPage = "pages/account/invoicelistingpage";// NOSONAR
				String InvoiceDetailsPage = "pages/account/invoicedetailpage";// NOSONAR
				String EWalletDetailsPage = "pages/account/ewalletdetailpage";//NOSONAR
				String AddEwalletPopup = "pages/account/addEwalletPopup";
				String EWalletShipToPage = "pages/account/ewalletShipToPagePopup"; //Ewallet NOSONAR
				String AssignRevokeEwalletPopup = "pages/account/assignRevokeEwalletPopup";
				String EWalletEditCardPage = "pages/account/editcard"; //Ewallet NOSONAR
				String UpdateEwalletPage = "pages/account/updateEwalletPage"; //Ewallet NOSONAR
				String UpdateEwalletMessagePage = "pages/account/updateEwalletMessagePage";
				String OpenOrdersPage = "pages/account/openorderspage";// NOSONAR
				String AccountOrdersPage = "pages/account/accountOrdersPage";// NOSONAR
				String AccountPartnerProgramPage = "pages/account/accountPartnerProgramPage";// NOSONAR
				String OfflineProductPage = "pages/account/offlineProductPage";// NOSONAR
				String MasterOrderPage = "pages/account/masterOrderPage";// NOSONAR
				String LinkToPayVerificationPage = "pages/account/linktopayverification";
				String LinkToPayPaymentPage = "pages/account/linktopaypayment";
				String LinkToPayConfirmationPage = "pages/account/linktopayconfirmation";


			}

			interface Checkout // NOSONAR
			{
				String CheckoutRegisterPage = "pages/checkout/checkoutRegisterPage"; // NOSONAR
				String CheckoutConfirmationPage = "pages/checkout/checkoutConfirmationPage"; // NOSONAR
				String CheckoutLoginPage = "pages/checkout/checkoutLoginPage"; // NOSONAR
			}

			interface MultiStepCheckout // NOSONAR
			{
				String AddEditDeliveryAddressPage = "pages/checkout/multi/addEditDeliveryAddressPage"; // NOSONAR
				String ChooseDeliveryMethodPage = "pages/checkout/multi/chooseDeliveryMethodPage"; // NOSONAR
				String ChoosePickupLocationPage = "pages/checkout/multi/choosePickupLocationPage"; // NOSONAR
				String AddPaymentMethodPage = "pages/checkout/multi/addPaymentMethodPage"; // NOSONAR
				String CheckoutSummaryPage = "pages/checkout/multi/checkoutSummaryPage"; // NOSONAR
				String HostedOrderPageErrorPage = "pages/checkout/multi/hostedOrderPageErrorPage"; // NOSONAR
				String HostedOrderPostPage = "pages/checkout/multi/hostedOrderPostPage"; // NOSONAR
				String SilentOrderPostPage = "pages/checkout/multi/silentOrderPostPage"; // NOSONAR
				String GiftWrapPage = "pages/checkout/multi/giftWrapPage"; // NOSONAR
				String ChoosePickupDeliveryMethodPage = "pages/checkout/multi/choosePickupDeliveryMethodPage";
				String OrderSummaryPage = "pages/checkout/multi/orderSummaryPage";
				String AddSiteOnePaymentMethodPage = "pages/checkout/multi/addSiteOnePaymentMethodPage";
				String SiteOnePaymentMethodPage = "pages/checkout/multi/siteOnePaymentMethodPage";
				String SiteOneCheckoutPage = "pages/checkout/multi/siteOneCheckoutPage";
			}

			interface Order
			{
				String OrderDetailsPage = "pages/order/orderEntryDetailsPage";
			}

			interface Password // NOSONAR
			{
				String PasswordResetChangePage = "pages/password/passwordResetChangePage"; // NOSONAR
				String PasswordResetRequest = "pages/password/passwordResetRequestPage"; // NOSONAR
				String PasswordResetRequestConfirmation = "pages/password/passwordResetRequestConfirmationPage";
				String SetPasswordPage = "pages/password/setPasswordPage"; // NOSONAR// NOSONAR
			}

			interface Error // NOSONAR
			{
				String ErrorNotFoundPage = "pages/notfound/notFoundPage"; // NOSONAR
				String ServerErrorEngPage = "pages/error/serverError";
				String ServerErrorSpanPage = "pages/error/serverError_es";
			}

			interface Cart // NOSONAR
			{
				String CartPage = "pages/cart/cartPage"; // NOSONAR
			}

			interface StoreFinder // NOSONAR
			{
				String StoreFinderSearchPage = "pages/storeFinder/storeFinderSearchPage"; // NOSONAR
				String StoreFinderDetailsPage = "pages/storeFinder/storeFinderDetailsPage"; // NOSONAR
				String StoreFinderViewMapPage = "pages/storeFinder/storeFinderViewMapPage"; // NOSONAR
				String SiteOneStoreFinderDetailsPage = "pages/storeFinder/siteOneStoreContentPage";
				String STORE_SEARCH_PAGE = "pages/storeFinder/storeSearchPage";


			}


			interface StoreDirectory // NOSONAR
			{
				String StoreDirectoryCityPage = "pages/storeDirectory/storeDirectoryCityPage"; // NOSONAR
				String StoreDirectoryListPage = "pages/storeDirectory/storeDirectoryListPage"; // NOSONAR
				String StoreDirectoryStateListPage = "pages/storeDirectory/storeDirectoryStateListPage"; // NOSONAR
			}

			interface Misc // NOSONAR
			{
				String MiscRobotsPage = "pages/misc/miscRobotsPage"; // NOSONAR
				String MiscSiteMapPage = "pages/misc/miscSiteMapPage"; // NOSONAR
			}

			interface Guest // NOSONAR
			{ // NOSONAR
				String GuestOrderPage = "pages/guest/guestOrderPage"; // NOSONAR
				String GuestOrderErrorPage = "pages/guest/guestOrderErrorPage"; // NOSONAR
			}

			interface Product // NOSONAR
			{
				String WriteReview = "pages/product/writeReview"; // NOSONAR
				String OrderForm = "pages/product/productOrderFormPage"; // NOSONAR
				String CompareProductPage = "pages/product/compareProductContent";
				String PDPVariantLoadPage = "pages/product/productVariantDisplayView";
			}

			interface QuickOrder // NOSONAR
			{
				String QuickOrderPage = "pages/quickOrder/quickOrderPage"; // NOSONAR
			}

			interface CSV // NOSONAR
			{
				String ImportCSVSavedCartPage = "pages/csv/importCSVSavedCartPage"; // NOSONAR
			}

			interface Event // NOSONAR
			{
				String SiteOneEventPage = "pages/event/siteOneEventPage";
				String SiteOneEventDetailPage = "pages/event/siteOneEventDetailPage";
			}

			interface SavedList
			{
				String SavedListDetailsPage = "pages/savedList/saveListDetailsPage"; //NOSONAR
				String SavedListLandingPage = "pages/savedList/savedListLandingPage"; //NOSONAR
				String SavedListEditPage = "pages/savedList/savedListEditPage"; //NOSONAR
				String UploadSavedListPage = "pages/savedList/importCSVSavedListPage"; //NOSONAR
				String AssemblyDetailsPage = "pages/assembly/assemblyDetailsPage"; //NOSONAR
				String AssemblyLandingPage = "pages/assembly/assemblyLandingPage"; //NOSONAR
				String AssemblyEditPage = "pages/assembly/assemblyEditPage"; //NOSONAR
				String ListOfListsPage = "pages/assembly/listOfListsPage"; //NOSONAR
				String GenarateEstimatePage = "pages/savedList/genarateEstimatePage"; //NOSONAR
				String RecommendListDetailsPage = "pages/savedList/recommendListDetailsPage";
				String RecommendListLandingPage = "pages/savedList/recommendListLandingPage";
			}

			interface Promotion
			{
				String PromotionPage = "pages/promotion/promotionPage"; //NOSONAR
				String LeadGenerationPage = "pages/promotion/leadGenerationPage"; //NOSONAR
				String EmailSignUpPage = "pages/promotion/emailSignUpPage"; //NOSONAR
				String EmailSignUpSuccessPage = "pages/promotion/emailSignUpSuccessPage"; //NOSONAR
				String MonthlyFlyerPage = "pages/promotion/monthlyFlyerPage";
			}

			interface PartnersProgram
			{
				String PointsForEquipmentPage = "/pages/partnersProgram/pointsForEquipmentPage";//NOSONAR
				String PointsForEquipmentSuccessPage = "/pages/partnersProgram/pointsForEquipmentSuccess";//NOSONAR
				String PARTNERSPROGRAMPAGE = "/pages/partnersProgram/pilotPartnersProgramPage";
				String PARTNERPERKSPAGE = "/pages/partnersProgram/partnerPerksPage";
				String PARTNERSPROGRAMTERMSPAGE = "/pages/partnersProgram/pilotPartnersProgramTermsPage";

			}

			interface Article
			{
				String ArticleLandingPage = "/pages/article/articleLandingPage";//NOSONAR
				String ArticleCategoryPage = "/pages/article/articleCategoryPage";//NOSONAR
				String ArticleDetailsPage = "/pages/article/articleDetailsPage";//NOSONAR
			}

			interface Calculator
			{
				String FlagStone = "pages/calculator/flagStonePage";
				String StoneWalls = "pages/calculator/stoneWallsPage";
				String RoadBasefill = "pages/calculator/roadBasefillPage";
				String DecorativeRock = "pages/calculator/decorativeRockPage";
				String TopSoil = "pages/calculator/topSoilPage";
				String BarkMulch = "pages/calculator/barkMulchPage";
				String VoltageDrop = "pages/calculator/voltageDropPage";
				String HolidayLighting="pages/calculator/holidayLightingPage";
				String PlantSpacing = "pages/calculator/plantSpacingPage";
			}

			interface Category
			{
				String PLPVariantLoadPage = "pages/category/productListViewVariant";
				String PLPCardLoadPage = "pages/category/productCardViewVariant";
				String FacetFragment = "pages/category/categoryPageFacet";
			}
		}

		interface Fragments
		{
			interface Cart // NOSONAR
			{
				String AddToCartPopup = "fragments/cart/addToCartPopup"; // NOSONAR
				String MiniCartPanel = "fragments/cart/miniCartPanel"; // NOSONAR
				String MiniCartErrorPanel = "fragments/cart/miniCartErrorPanel"; // NOSONAR
				String CartPopup = "fragments/cart/cartPopup"; // NOSONAR
				String ExpandGridInCart = "fragments/cart/expandGridInCart"; // NOSONAR
			}

			interface Account // NOSONAR
			{
				String CountryAddressForm = "fragments/address/countryAddressForm"; // NOSONAR
				String SavedCartRestorePopup = "fragments/account/savedCartRestorePopup"; // NOSONAR
				String AccDashRecentProductSection = "fragments/account/accDashRecentProductSection"; // NOSONAR
				String AccDashBuyItAgainProductSection = "fragments/account/accDashBuyItAgainProductSection"; // NOSONAR
				String AccDashRecommendProductSection = "fragments/account/accDashRecommendProductSection"; // NOSONAR
			
			}

			interface Checkout // NOSONAR
			{
				String TermsAndConditionsPopup = "fragments/checkout/termsAndConditionsPopup"; // NOSONAR
				String BillingAddressForm = "fragments/checkout/billingAddressForm"; // NOSONAR
				String ReadOnlyExpandedOrderForm = "fragments/checkout/readOnlyExpandedOrderForm"; // NOSONAR
				String AddPhoneNumberPopup = "fragments/checkout/addPhoneNumberRequestPopup";
				String AddPhoneNumberSuccessPopup = "fragments/checkout/addPhoneNumberSuccessPopup";
			}

			interface Password // NOSONAR
			{
				String PasswordResetRequestPopup = "fragments/password/passwordResetRequestPopup"; // NOSONAR
				String ForgotPasswordValidationMessage = "fragments/password/forgotPasswordValidationMessage";
				String UnlockUserRequestPopup = "fragments/password/unlockUserRequestPopup"; // NOSONAR
				String UnlockUserValidationMessage = "fragments/password/unlockUserValidationMessage";
			}

			interface Product // NOSONAR
			{
				String FutureStockPopup = "fragments/product/futureStockPopup"; // NOSONAR
				String QuickViewPopup = "fragments/product/quickViewPopup"; // NOSONAR
				String ZoomImagesPopup = "fragments/product/zoomImagesPopup"; // NOSONAR
				String ReviewsTab = "fragments/product/reviewsTab"; // NOSONAR
				String StorePickupSearchResults = "fragments/product/storePickupSearchResults"; // NOSONAR
				String ProductRecommendationSectionPLP = "fragments/product/productRecommendationSectionPLP"; // NOSONAR
				String ProductRecommendationSectionPDP = "fragments/product/productRecommendationSectionPDP"; // NOSONAR
				String ProductRecommendationSectionHomePage = "fragments/product/productRecommendationSectionHomePage"; // NOSONAR
			}

			interface Navigation
			{
				String NavigationTab = "fragments/navigation/navigationTab";
			}

		}
	}
}
