/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.cronjob.PimPayloadsDeleteCronjob;
import com.siteone.core.jalo.AdobeAnalyticsCustomerExportCronJob;
import com.siteone.core.jalo.AdobeAnalyticsOrderExportCronJob;
import com.siteone.core.jalo.AdobeAnalyticsRealtimeCustomerExportCronJob;
import com.siteone.core.jalo.AutoPhraseConfig;
import com.siteone.core.jalo.BagInfo;
import com.siteone.core.jalo.BatchNotificationProcess;
import com.siteone.core.jalo.BulkListUploadCronJob;
import com.siteone.core.jalo.CCPACustomerProcess;
import com.siteone.core.jalo.CCPAProcess;
import com.siteone.core.jalo.CartAbandonmentCronJob;
import com.siteone.core.jalo.CartAbandonmentEmailProcess;
import com.siteone.core.jalo.CategoryComponent;
import com.siteone.core.jalo.CategoryFeedCronJob;
import com.siteone.core.jalo.CategoryLandingPageRotatingBannerComponent;
import com.siteone.core.jalo.CategoryProductCountCronJob;
import com.siteone.core.jalo.ContactSellerProcess;
import com.siteone.core.jalo.ContactUsCustomerProcess;
import com.siteone.core.jalo.ContactUsProcess;
import com.siteone.core.jalo.ConvertedProductMappingCronJob;
import com.siteone.core.jalo.CreatePasswordEmailProcess;
import com.siteone.core.jalo.CuratedPLPVerticaNavNodeComponent;
import com.siteone.core.jalo.CustomPushNotification;
import com.siteone.core.jalo.CustomPushNotificationCronJob;
import com.siteone.core.jalo.CustomerDataReportCronJob;
import com.siteone.core.jalo.CustomerUpdateCronJob;
import com.siteone.core.jalo.CustomerUpdateNewEmailProcess;
import com.siteone.core.jalo.CustomerUpdateOldEmailProcess;
import com.siteone.core.jalo.DeclinedCardAttemptEmailProcess;
import com.siteone.core.jalo.EWalletNotificationEmailProcess;
import com.siteone.core.jalo.EmailSubscriptions;
import com.siteone.core.jalo.EventCarouselComponent;
import com.siteone.core.jalo.EventResultsGridComponent;
import com.siteone.core.jalo.ExpiredQuoteUpdProcess;
import com.siteone.core.jalo.FeedFileMonitorNotificationProcess;
import com.siteone.core.jalo.FindDuplicateSequenceCronJob;
import com.siteone.core.jalo.FirstTimeUserCronJob;
import com.siteone.core.jalo.FullProductFeedCronJob;
import com.siteone.core.jalo.GlobalProductNavigationNode;
import com.siteone.core.jalo.HomeOwnerComponent;
import com.siteone.core.jalo.HomeOwnerProcess;
import com.siteone.core.jalo.HomePageCustomerEventsComponent;
import com.siteone.core.jalo.HomePagePromoBannerComponent;
import com.siteone.core.jalo.HomePageResponsiveBannerComponent;
import com.siteone.core.jalo.HomePageRotatingBannerComponent;
import com.siteone.core.jalo.HomePageToolsComponent;
import com.siteone.core.jalo.ImportOnHandProductStoresCronJob;
import com.siteone.core.jalo.ImportProductStoresCronJob;
import com.siteone.core.jalo.ImportQRCodeProductsCronJob;
import com.siteone.core.jalo.Inspiration;
import com.siteone.core.jalo.InventoryFeedCronJob;
import com.siteone.core.jalo.InventoryUOM;
import com.siteone.core.jalo.InventoryUPC;
import com.siteone.core.jalo.Invoice;
import com.siteone.core.jalo.InvoiceDetailsProcess;
import com.siteone.core.jalo.InvoiceEntry;
import com.siteone.core.jalo.KountDeclineProcess;
import com.siteone.core.jalo.LinkToPayAuditLog;
import com.siteone.core.jalo.LinkToPayCayanResponse;
import com.siteone.core.jalo.LinkToPayEmailPaymentProcess;
import com.siteone.core.jalo.LinkToPayPayment;
import com.siteone.core.jalo.LinkToPayPaymentProcess;
import com.siteone.core.jalo.LinkToPayProcess;
import com.siteone.core.jalo.ListEditEmailProcess;
import com.siteone.core.jalo.LocalizedProductFeedCronJob;
import com.siteone.core.jalo.NoVisibleUomCronJob;
import com.siteone.core.jalo.NoVisibleUomProductProcess;
import com.siteone.core.jalo.NotifyQuoteStatusProcess;
import com.siteone.core.jalo.NurseryInventoryFeedCronJob;
import com.siteone.core.jalo.OldInvoiceAddressRemovalCronjob;
import com.siteone.core.jalo.OrderDetailEmailProcess;
import com.siteone.core.jalo.OrderOnlineComponent;
import com.siteone.core.jalo.OrderReadyForPickUpRemainderEmailCronJob;
import com.siteone.core.jalo.OrderReadyToPickUpEmailProcess;
import com.siteone.core.jalo.OrderScheduledForDeliveryEmailProcess;
import com.siteone.core.jalo.OrderStatusEmailCronJob;
import com.siteone.core.jalo.OrderStatusNotificationCronJob;
import com.siteone.core.jalo.OrderTrackingLinkEmailProcess;
import com.siteone.core.jalo.OrphanMediaCronJob;
import com.siteone.core.jalo.PIMBatchFailureReportCronJob;
import com.siteone.core.jalo.PIMBatchFailureReportNotificationProcess;
import com.siteone.core.jalo.PIMReportMessage;
import com.siteone.core.jalo.PasswordChangedEmailProcess;
import com.siteone.core.jalo.PointsForEquipmentProcess;
import com.siteone.core.jalo.ProductFeedCronJob;
import com.siteone.core.jalo.ProductPromotionsCronJob;
import com.siteone.core.jalo.ProductRegionFeedCronJob;
import com.siteone.core.jalo.ProductSalesInfo;
import com.siteone.core.jalo.PromotionBannerComponent;
import com.siteone.core.jalo.PromotionFeedCronJob;
import com.siteone.core.jalo.PromotionImageComponent;
import com.siteone.core.jalo.PromotionProductCategory;
import com.siteone.core.jalo.ProprietaryBrandConfig;
import com.siteone.core.jalo.PunchOutAuditLog;
import com.siteone.core.jalo.PurchProductAndOrders;
import com.siteone.core.jalo.PurchasedProduct;
import com.siteone.core.jalo.PurgeRegulatoryStatesCronJob;
import com.siteone.core.jalo.QuoteApprovalProcess;
import com.siteone.core.jalo.QuoteItemDetails;
import com.siteone.core.jalo.QuoteToOrderStatusEmailCronJob;
import com.siteone.core.jalo.QuoteToOrderStatusProcess;
import com.siteone.core.jalo.RecentScanProducts;
import com.siteone.core.jalo.RegionFeedCronJob;
import com.siteone.core.jalo.RegulatoryStates;
import com.siteone.core.jalo.RegulatoryStatesCronJob;
import com.siteone.core.jalo.RequestAccountProcess;
import com.siteone.core.jalo.RequestQuoteProcess;
import com.siteone.core.jalo.SalesFeedCronJob;
import com.siteone.core.jalo.ShareAssemblyProcess;
import com.siteone.core.jalo.ShareCartEmailProcess;
import com.siteone.core.jalo.ShareListProcess;
import com.siteone.core.jalo.SharedProductProcess;
import com.siteone.core.jalo.SiteOneArticle;
import com.siteone.core.jalo.SiteOneArticleCategory;
import com.siteone.core.jalo.SiteOneArticleCategoryComponent;
import com.siteone.core.jalo.SiteOneArticleContent;
import com.siteone.core.jalo.SiteOneArticleDetailComponent;
import com.siteone.core.jalo.SiteOneArticleTagsComponent;
import com.siteone.core.jalo.SiteOneAuditCleanupCronJob;
import com.siteone.core.jalo.SiteOneAuditExportCronJob;
import com.siteone.core.jalo.SiteOneBottomBannerComponent;
import com.siteone.core.jalo.SiteOneBottomBannerListComponent;
import com.siteone.core.jalo.SiteOneBrandBannerComponent;
import com.siteone.core.jalo.SiteOneCategoryPageTopBannerComponent;
import com.siteone.core.jalo.SiteOneCategoryPromoComponent;
import com.siteone.core.jalo.SiteOneCategoryPromotionBannerComponent;
import com.siteone.core.jalo.SiteOneContrPrimaryBusiness;
import com.siteone.core.jalo.SiteOneCuratedPLPBottomBannerComponent;
import com.siteone.core.jalo.SiteOneCuratedPLPComponent;
import com.siteone.core.jalo.SiteOneCuratedPLPHSProductComponent;
import com.siteone.core.jalo.SiteOneCuratedPLPHeaderComponent;
import com.siteone.core.jalo.SiteOneCuratedPLPPromoBottomBannerComponent;
import com.siteone.core.jalo.SiteOneCuratedPLPPromoComponent;
import com.siteone.core.jalo.SiteOneCuratedPLPStoreComponent;
import com.siteone.core.jalo.SiteOneDeal;
import com.siteone.core.jalo.SiteOneEvent;
import com.siteone.core.jalo.SiteOneEventComponent;
import com.siteone.core.jalo.SiteOneEventListComponent;
import com.siteone.core.jalo.SiteOneEventType;
import com.siteone.core.jalo.SiteOneEventTypeGroup;
import com.siteone.core.jalo.SiteOneFeatureProductComponent;
import com.siteone.core.jalo.SiteOneFeatureSwitch;
import com.siteone.core.jalo.SiteOneFeedFileInfo;
import com.siteone.core.jalo.SiteOneFeedFileInfoCronJob;
import com.siteone.core.jalo.SiteOneGalleryHeaderBannerComponent;
import com.siteone.core.jalo.SiteOneGalleryHeaderComponent;
import com.siteone.core.jalo.SiteOneGalleryImageComponent;
import com.siteone.core.jalo.SiteOneGalleryParagraphComponent;
import com.siteone.core.jalo.SiteOneHomeBannerComponent;
import com.siteone.core.jalo.SiteOneHomePageBackgroundImageComponent;
import com.siteone.core.jalo.SiteOneHomePageMobileBrandBannerComponent;
import com.siteone.core.jalo.SiteOneHomePageParagraphComponent;
import com.siteone.core.jalo.SiteOneHomePageProductsComponent;
import com.siteone.core.jalo.SiteOneHomePageTextBannerComponent;
import com.siteone.core.jalo.SiteOneHotFolderArchiveCleanupCronJob;
import com.siteone.core.jalo.SiteOneInspiration;
import com.siteone.core.jalo.SiteOneLoginPageComponent;
import com.siteone.core.jalo.SiteOneMarketingBannerComponent;
import com.siteone.core.jalo.SiteOneNews;
import com.siteone.core.jalo.SiteOnePartnerPerkParagraphComponent;
import com.siteone.core.jalo.SiteOnePartnerPointsMessageComponent;
import com.siteone.core.jalo.SiteOnePartnerProgramComponent;
import com.siteone.core.jalo.SiteOnePilotPartnerParagraphComponent;
import com.siteone.core.jalo.SiteOneProductPromotionComponent;
import com.siteone.core.jalo.SiteOnePromotionCategoryComponent;
import com.siteone.core.jalo.SiteOnePromotionComponent;
import com.siteone.core.jalo.SiteOnePromotionalResponsiveBannerComponent;
import com.siteone.core.jalo.SiteOneQuickBooksHeaderComponent;
import com.siteone.core.jalo.SiteOneRegionSegment;
import com.siteone.core.jalo.SiteOneStoreDetailPromoComponent;
import com.siteone.core.jalo.SiteOneStoreDetailsPromoComponent;
import com.siteone.core.jalo.SiteOneToolsListComponent;
import com.siteone.core.jalo.SiteOneTradeClassSegment;
import com.siteone.core.jalo.SiteoneAnonymousCartRemovalCronJob;
import com.siteone.core.jalo.SiteoneArticleBannerComponent;
import com.siteone.core.jalo.SiteoneArticleFeatureComponent;
import com.siteone.core.jalo.SiteoneArticleHeroBannerComponent;
import com.siteone.core.jalo.SiteoneCADeliveryFees;
import com.siteone.core.jalo.SiteoneCCPaymentAuditLog;
import com.siteone.core.jalo.SiteoneCleanUpImpexMediaCronJob;
import com.siteone.core.jalo.SiteoneCleanUpLogsCronJob;
import com.siteone.core.jalo.SiteoneContactUsComponent;
import com.siteone.core.jalo.SiteoneCuratedPLPNavNode;
import com.siteone.core.jalo.SiteoneDeliveryFees;
import com.siteone.core.jalo.SiteoneEmbedVideoComponent;
import com.siteone.core.jalo.SiteoneEventTypeGroupComponent;
import com.siteone.core.jalo.SiteoneEventTypeGroupHeaderComponent;
import com.siteone.core.jalo.SiteoneEwalletCreditCard;
import com.siteone.core.jalo.SiteoneFAQCollectionComponent;
import com.siteone.core.jalo.SiteoneGreenButtonComponent;
import com.siteone.core.jalo.SiteoneHeaderPromotionalComponent;
import com.siteone.core.jalo.SiteoneJobsStatusCronJob;
import com.siteone.core.jalo.SiteoneJobsStatusProcess;
import com.siteone.core.jalo.SiteoneKountData;
import com.siteone.core.jalo.SiteoneLeftNavigationComponent;
import com.siteone.core.jalo.SiteoneOldInvoiceRemovalJob;
import com.siteone.core.jalo.SiteoneOldOrderRemovalJob;
import com.siteone.core.jalo.SiteoneOrderEmailStatus;
import com.siteone.core.jalo.SiteonePageTitleBannerComponent;
import com.siteone.core.jalo.SiteoneParagaraphComponent;
import com.siteone.core.jalo.SiteoneParagraphComponent;
import com.siteone.core.jalo.SiteonePartnerPerkBottomParagraphComponent;
import com.siteone.core.jalo.SiteonePartnerPromotionalComponent;
import com.siteone.core.jalo.SiteonePricingTrack;
import com.siteone.core.jalo.SiteonePromotionalBannerComponent;
import com.siteone.core.jalo.SiteoneRequestAccount;
import com.siteone.core.jalo.SiteoneShippingFees;
import com.siteone.core.jalo.SiteoneStoreDetailsComponent;
import com.siteone.core.jalo.SiteoneTitleBannerComponent;
import com.siteone.core.jalo.SiteoneVideoComponent;
import com.siteone.core.jalo.SiteonefaqComponent;
import com.siteone.core.jalo.StoreTimeZoneStatusCronJob;
import com.siteone.core.jalo.TalonOneEnrollment;
import com.siteone.core.jalo.UnlockUserEmailProcess;
import com.siteone.core.jalo.UomRewriteConfig;
import com.siteone.core.jalo.UploadErrorProductDetail;
import com.siteone.core.jalo.UploadListErrorInfo;
import com.siteone.core.jalo.VerticalBarComponent;
import com.siteone.core.jalo.WebServiceAuditLogCleanUpCronJob;
import com.siteone.core.jalo.WebserviceAuditLog;
import com.siteone.core.jalo.components.BottomConsentCMSComponent;
import com.siteone.core.jalo.components.GlobalMessageCMSComponent;
import com.siteone.core.jalo.components.NewsComponent;
import com.siteone.core.jalo.components.NewsDetailComponent;
import com.siteone.core.jalo.components.SiteOneCategoryBannerComponent;
import com.siteone.core.jalo.process.PimBatchFailureEmailProcess;
import de.hybris.platform.acceleratorcms.jalo.components.FooterNavigationComponent;
import de.hybris.platform.acceleratorcms.jalo.components.NavigationComponent;
import de.hybris.platform.acceleratorcms.jalo.components.SearchBoxComponent;
import de.hybris.platform.acceleratorservices.constants.AcceleratorServicesConstants;
import de.hybris.platform.acceleratorservices.jalo.email.EmailAttachment;
import de.hybris.platform.b2b.jalo.B2BCustomer;
import de.hybris.platform.b2b.jalo.B2BUnit;
import de.hybris.platform.basecommerce.jalo.site.BaseSite;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.cms2.jalo.pages.AbstractPage;
import de.hybris.platform.cms2.jalo.pages.ContentPage;
import de.hybris.platform.cms2.jalo.site.CMSSite;
import de.hybris.platform.cms2lib.components.AbstractBannerComponent;
import de.hybris.platform.commerceservices.jalo.OrgUnit;
import de.hybris.platform.commerceservices.jalo.process.ForgottenPasswordProcess;
import de.hybris.platform.commerceservices.jalo.process.StoreFrontCustomerProcess;
import de.hybris.platform.commerceservices.jalo.storelocator.StoreLocatorFeature;
import de.hybris.platform.couponservices.jalo.CouponRedemption;
import de.hybris.platform.deeplink.jalo.media.BarcodeMedia;
import de.hybris.platform.europe1.jalo.PDTRow;
import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LItem;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.c2l.Region;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.link.Link;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.jalo.media.MediaContainer;
import de.hybris.platform.jalo.order.AbstractOrder;
import de.hybris.platform.jalo.order.AbstractOrderEntry;
import de.hybris.platform.jalo.order.Cart;
import de.hybris.platform.jalo.order.Order;
import de.hybris.platform.jalo.order.payment.PaymentInfo;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.PrincipalGroup;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import de.hybris.platform.jalo.user.AbstractContactInfo;
import de.hybris.platform.jalo.user.Address;
import de.hybris.platform.jalo.user.Customer;
import de.hybris.platform.jalo.user.Employee;
import de.hybris.platform.jalo.user.PhoneContactInfo;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.jalo.user.UserGroup;
import de.hybris.platform.orderprocessing.jalo.OrderProcess;
import de.hybris.platform.ordersplitting.jalo.Consignment;
import de.hybris.platform.ordersplitting.jalo.ConsignmentEntry;
import de.hybris.platform.ordersplitting.jalo.StockLevel;
import de.hybris.platform.personalizationservices.jalo.CxCustomization;
import de.hybris.platform.processengine.jalo.BusinessProcess;
import de.hybris.platform.promotionengineservices.jalo.PromotionSourceRule;
import de.hybris.platform.promotions.jalo.AbstractPromotion;
import de.hybris.platform.ruleengineservices.jalo.SourceRule;
import de.hybris.platform.solrfacetsearch.jalo.config.SolrIndexedProperty;
import de.hybris.platform.storelocator.jalo.OpeningDay;
import de.hybris.platform.storelocator.jalo.PointOfService;
import de.hybris.platform.storelocator.jalo.WeekdayOpeningDay;
import de.hybris.platform.util.OneToManyHandler;
import de.hybris.platform.util.PartOfHandler;
import de.hybris.platform.util.Utilities;
import de.hybris.platform.wishlist2.jalo.Wishlist2;
import de.hybris.platform.wishlist2.jalo.Wishlist2Entry;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generated class for type <code>SiteoneCoreManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneCoreManager extends Extension
{
	/** Relation ordering override parameter constants for Store2B2BCustomerRelation from ((siteonecore))*/
	protected static String STORE2B2BCUSTOMERRELATION_SRC_ORDERED = "relation.Store2B2BCustomerRelation.source.ordered";
	protected static String STORE2B2BCUSTOMERRELATION_TGT_ORDERED = "relation.Store2B2BCustomerRelation.target.ordered";
	/** Relation disable markmodifed parameter constants for Store2B2BCustomerRelation from ((siteonecore))*/
	protected static String STORE2B2BCUSTOMERRELATION_MARKMODIFIED = "relation.Store2B2BCustomerRelation.markmodified";
	/** Relation ordering override parameter constants for Customer2WalletRelation from ((siteonecore))*/
	protected static String CUSTOMER2WALLETRELATION_SRC_ORDERED = "relation.Customer2WalletRelation.source.ordered";
	protected static String CUSTOMER2WALLETRELATION_TGT_ORDERED = "relation.Customer2WalletRelation.target.ordered";
	/** Relation disable markmodifed parameter constants for Customer2WalletRelation from ((siteonecore))*/
	protected static String CUSTOMER2WALLETRELATION_MARKMODIFIED = "relation.Customer2WalletRelation.markmodified";
	/** Relation ordering override parameter constants for Product2PointOfServiceRelation from ((siteonecore))*/
	protected static String PRODUCT2POINTOFSERVICERELATION_SRC_ORDERED = "relation.Product2PointOfServiceRelation.source.ordered";
	protected static String PRODUCT2POINTOFSERVICERELATION_TGT_ORDERED = "relation.Product2PointOfServiceRelation.target.ordered";
	/** Relation disable markmodifed parameter constants for Product2PointOfServiceRelation from ((siteonecore))*/
	protected static String PRODUCT2POINTOFSERVICERELATION_MARKMODIFIED = "relation.Product2PointOfServiceRelation.markmodified";
	/** Relation ordering override parameter constants for Product2OnHandStoresRelation from ((siteonecore))*/
	protected static String PRODUCT2ONHANDSTORESRELATION_SRC_ORDERED = "relation.Product2OnHandStoresRelation.source.ordered";
	protected static String PRODUCT2ONHANDSTORESRELATION_TGT_ORDERED = "relation.Product2OnHandStoresRelation.target.ordered";
	/** Relation disable markmodifed parameter constants for Product2OnHandStoresRelation from ((siteonecore))*/
	protected static String PRODUCT2ONHANDSTORESRELATION_MARKMODIFIED = "relation.Product2OnHandStoresRelation.markmodified";
	/** Relation ordering override parameter constants for store2ShippingHubRelation from ((siteonecore))*/
	protected static String STORE2SHIPPINGHUBRELATION_SRC_ORDERED = "relation.store2ShippingHubRelation.source.ordered";
	protected static String STORE2SHIPPINGHUBRELATION_TGT_ORDERED = "relation.store2ShippingHubRelation.target.ordered";
	/** Relation disable markmodifed parameter constants for store2ShippingHubRelation from ((siteonecore))*/
	protected static String STORE2SHIPPINGHUBRELATION_MARKMODIFIED = "relation.store2ShippingHubRelation.markmodified";
	/** Relation ordering override parameter constants for store2DistributedCenterRelation from ((siteonecore))*/
	protected static String STORE2DISTRIBUTEDCENTERRELATION_SRC_ORDERED = "relation.store2DistributedCenterRelation.source.ordered";
	protected static String STORE2DISTRIBUTEDCENTERRELATION_TGT_ORDERED = "relation.store2DistributedCenterRelation.target.ordered";
	/** Relation disable markmodifed parameter constants for store2DistributedCenterRelation from ((siteonecore))*/
	protected static String STORE2DISTRIBUTEDCENTERRELATION_MARKMODIFIED = "relation.store2DistributedCenterRelation.markmodified";
	/** Relation ordering override parameter constants for ShipTo2WalletRelation from ((siteonecore))*/
	protected static String SHIPTO2WALLETRELATION_SRC_ORDERED = "relation.ShipTo2WalletRelation.source.ordered";
	protected static String SHIPTO2WALLETRELATION_TGT_ORDERED = "relation.ShipTo2WalletRelation.target.ordered";
	/** Relation disable markmodifed parameter constants for ShipTo2WalletRelation from ((siteonecore))*/
	protected static String SHIPTO2WALLETRELATION_MARKMODIFIED = "relation.ShipTo2WalletRelation.markmodified";
	/** Relation ordering override parameter constants for b2bUnit2ProductRelation from ((siteonecore))*/
	protected static String B2BUNIT2PRODUCTRELATION_SRC_ORDERED = "relation.b2bUnit2ProductRelation.source.ordered";
	protected static String B2BUNIT2PRODUCTRELATION_TGT_ORDERED = "relation.b2bUnit2ProductRelation.target.ordered";
	/** Relation disable markmodifed parameter constants for b2bUnit2ProductRelation from ((siteonecore))*/
	protected static String B2BUNIT2PRODUCTRELATION_MARKMODIFIED = "relation.b2bUnit2ProductRelation.markmodified";
	/** Relation ordering override parameter constants for SalesProductRelation from ((siteonecore))*/
	protected static String SALESPRODUCTRELATION_SRC_ORDERED = "relation.SalesProductRelation.source.ordered";
	protected static String SALESPRODUCTRELATION_TGT_ORDERED = "relation.SalesProductRelation.target.ordered";
	/** Relation disable markmodifed parameter constants for SalesProductRelation from ((siteonecore))*/
	protected static String SALESPRODUCTRELATION_MARKMODIFIED = "relation.SalesProductRelation.markmodified";
	/** Relation ordering override parameter constants for PurchasedProduct2OrderRelation from ((siteonecore))*/
	protected static String PURCHASEDPRODUCT2ORDERRELATION_SRC_ORDERED = "relation.PurchasedProduct2OrderRelation.source.ordered";
	protected static String PURCHASEDPRODUCT2ORDERRELATION_TGT_ORDERED = "relation.PurchasedProduct2OrderRelation.target.ordered";
	/** Relation disable markmodifed parameter constants for PurchasedProduct2OrderRelation from ((siteonecore))*/
	protected static String PURCHASEDPRODUCT2ORDERRELATION_MARKMODIFIED = "relation.PurchasedProduct2OrderRelation.markmodified";
	/**
	* {@link OneToManyHandler} for handling 1:n ATTACHMENTS's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<EmailAttachment> BUSINESSPROCESS2EMAILATTACHMENTRELATTACHMENTSHANDLER = new OneToManyHandler<EmailAttachment>(
	AcceleratorServicesConstants.TC.EMAILATTACHMENT,
	true,
	"BusinessProcess",
	null,
	false,
	true,
	CollectionType.LIST
	);
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put("defaultStore", AttributeMode.INITIAL);
		tmp.put("productPriceDisclaimer", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.cms2.jalo.site.CMSSite", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("preferredStore", AttributeMode.INITIAL);
		tmp.put("guid", AttributeMode.INITIAL);
		tmp.put("isTransactionEmailOpted", AttributeMode.INITIAL);
		tmp.put("isSmsTextOpted", AttributeMode.INITIAL);
		tmp.put("isNewsLetterOpted", AttributeMode.INITIAL);
		tmp.put("invoicePermissions", AttributeMode.INITIAL);
		tmp.put("partnerProgramPermissions", AttributeMode.INITIAL);
		tmp.put("isProjectServicesEnabled", AttributeMode.INITIAL);
		tmp.put("accountOverviewForParent", AttributeMode.INITIAL);
		tmp.put("accountOverviewForShipTos", AttributeMode.INITIAL);
		tmp.put("firstName", AttributeMode.INITIAL);
		tmp.put("lastName", AttributeMode.INITIAL);
		tmp.put("isActiveInOkta", AttributeMode.INITIAL);
		tmp.put("isFirstTimeUser", AttributeMode.INITIAL);
		tmp.put("orderPromoOption", AttributeMode.INITIAL);
		tmp.put("payBillOnline", AttributeMode.INITIAL);
		tmp.put("placeOrder", AttributeMode.INITIAL);
		tmp.put("needsOrderApproval", AttributeMode.INITIAL);
		tmp.put("oldContactEmail", AttributeMode.INITIAL);
		tmp.put("updateEmailFlag", AttributeMode.INITIAL);
		tmp.put("recentCartIds", AttributeMode.INITIAL);
		tmp.put("currentCarId", AttributeMode.INITIAL);
		tmp.put("languagePreference", AttributeMode.INITIAL);
		tmp.put("isAccountOwner", AttributeMode.INITIAL);
		tmp.put("homeBranch", AttributeMode.INITIAL);
		tmp.put("enableAddModifyDeliveryAddress", AttributeMode.INITIAL);
		tmp.put("isRealtimeAccount", AttributeMode.INITIAL);
		tmp.put("ueType", AttributeMode.INITIAL);
		tmp.put("uuid", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.b2b.jalo.B2BCustomer", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("startDate", AttributeMode.INITIAL);
		tmp.put("endDate", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.storelocator.jalo.WeekdayOpeningDay", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("storeId", AttributeMode.INITIAL);
		tmp.put("storeType", AttributeMode.INITIAL);
		tmp.put("branchManagerEmail", AttributeMode.INITIAL);
		tmp.put("customerRetailId", AttributeMode.INITIAL);
		tmp.put("bigBag", AttributeMode.INITIAL);
		tmp.put("bigBagSize", AttributeMode.INITIAL);
		tmp.put("division", AttributeMode.INITIAL);
		tmp.put("isActive", AttributeMode.INITIAL);
		tmp.put("specialText", AttributeMode.INITIAL);
		tmp.put("storeSpecialities", AttributeMode.INITIAL);
		tmp.put("storeNotes", AttributeMode.INITIAL);
		tmp.put("supplyChainNodeId", AttributeMode.INITIAL);
		tmp.put("title", AttributeMode.INITIAL);
		tmp.put("licenseStartDate", AttributeMode.INITIAL);
		tmp.put("licenseEndDate", AttributeMode.INITIAL);
		tmp.put("metroStatArea", AttributeMode.INITIAL);
		tmp.put("timezoneId", AttributeMode.INITIAL);
		tmp.put("regionId", AttributeMode.INITIAL);
		tmp.put("parentId", AttributeMode.INITIAL);
		tmp.put("area", AttributeMode.INITIAL);
		tmp.put("nurseryOfferURL", AttributeMode.INITIAL);
		tmp.put("nearbyStoreSearchRadius", AttributeMode.INITIAL);
		tmp.put("longDescription", AttributeMode.INITIAL);
		tmp.put("supportProductScanner", AttributeMode.INITIAL);
		tmp.put("acquisitionOrCoBrandedBranch", AttributeMode.INITIAL);
		tmp.put("pickupfullfillment", AttributeMode.INITIAL);
		tmp.put("deliveryfullfillment", AttributeMode.INITIAL);
		tmp.put("shippingfullfillment", AttributeMode.INITIAL);
		tmp.put("excludeBranches", AttributeMode.INITIAL);
		tmp.put("isDCBranch", AttributeMode.INITIAL);
		tmp.put("deliveryOrShippingThreshold", AttributeMode.INITIAL);
		tmp.put("nurseryBuyingGroup", AttributeMode.INITIAL);
		tmp.put("regionName", AttributeMode.INITIAL);
		tmp.put("areaName", AttributeMode.INITIAL);
		tmp.put("siteoneHolidays", AttributeMode.INITIAL);
		tmp.put("enableOnlineFulfillment", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.storelocator.jalo.PointOfService", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("guid", AttributeMode.INITIAL);
		tmp.put("projectName", AttributeMode.INITIAL);
		tmp.put("deliveryInstructions", AttributeMode.INITIAL);
		tmp.put("geoCode", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.user.Address", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("division", AttributeMode.INITIAL);
		tmp.put("isLargeAccount", AttributeMode.INITIAL);
		tmp.put("guid", AttributeMode.INITIAL);
		tmp.put("isPONumberRequired", AttributeMode.INITIAL);
		tmp.put("poRegex", AttributeMode.INITIAL);
		tmp.put("isBillingAccount", AttributeMode.INITIAL);
		tmp.put("isOrderingAccount", AttributeMode.INITIAL);
		tmp.put("phoneNumber", AttributeMode.INITIAL);
		tmp.put("phoneId", AttributeMode.INITIAL);
		tmp.put("isPunchOutAccount", AttributeMode.INITIAL);
		tmp.put("payBillOnline", AttributeMode.INITIAL);
		tmp.put("tradeClass", AttributeMode.INITIAL);
		tmp.put("tradeClassName", AttributeMode.INITIAL);
		tmp.put("subTradeClass", AttributeMode.INITIAL);
		tmp.put("subTradeClassName", AttributeMode.INITIAL);
		tmp.put("customerSegment", AttributeMode.INITIAL);
		tmp.put("isPayOnAccount", AttributeMode.INITIAL);
		tmp.put("creditCode", AttributeMode.INITIAL);
		tmp.put("creditTermCode", AttributeMode.INITIAL);
		tmp.put("accountGroupCode", AttributeMode.INITIAL);
		tmp.put("priceClassCode", AttributeMode.INITIAL);
		tmp.put("nurseryClassCode", AttributeMode.INITIAL);
		tmp.put("dunsScore", AttributeMode.INITIAL);
		tmp.put("confidenceCode", AttributeMode.INITIAL);
		tmp.put("matchGrade", AttributeMode.INITIAL);
		tmp.put("customerLogo", AttributeMode.INITIAL);
		tmp.put("accountManagerEmail", AttributeMode.INITIAL);
		tmp.put("accountManagerName", AttributeMode.INITIAL);
		tmp.put("insideSalesRepEmail", AttributeMode.INITIAL);
		tmp.put("exemptDeliveryFee", AttributeMode.INITIAL);
		tmp.put("yearsInBusiness", AttributeMode.INITIAL);
		tmp.put("numberOfEmployees", AttributeMode.INITIAL);
		tmp.put("homeBranch", AttributeMode.INITIAL);
		tmp.put("shippingThresholdAndFee", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.b2b.jalo.B2BUnit", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("visibleCategories", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.user.UserGroup", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("urlForLink", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.cms2lib.components.AbstractBannerComponent", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("pageTitle", AttributeMode.INITIAL);
		tmp.put("variantCategoryList", AttributeMode.INITIAL);
		tmp.put("pimCategoryId", AttributeMode.INITIAL);
		tmp.put("seoEditableText", AttributeMode.INITIAL);
		tmp.put("marketingBanner", AttributeMode.INITIAL);
		tmp.put("marketingBannerLink", AttributeMode.INITIAL);
		tmp.put("bannerList", AttributeMode.INITIAL);
		tmp.put("isTransferrableCategory", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.category.jalo.Category", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("category", AttributeMode.INITIAL);
		tmp.put("contentTags", AttributeMode.INITIAL);
		tmp.put("isIndexable", AttributeMode.INITIAL);
		tmp.put("includeInSiteMap", AttributeMode.INITIAL);
		tmp.put("fullPagePath", AttributeMode.INITIAL);
		tmp.put("pathingChannel", AttributeMode.INITIAL);
		tmp.put("contentType", AttributeMode.INITIAL);
		tmp.put("previewTitle", AttributeMode.INITIAL);
		tmp.put("featuredArticle", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.cms2.jalo.pages.ContentPage", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("pointOfService", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.europe1.jalo.PriceRow", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("image", AttributeMode.INITIAL);
		tmp.put("notes", AttributeMode.INITIAL);
		tmp.put("shopDealUrl", AttributeMode.INITIAL);
		tmp.put("discountValue", AttributeMode.INITIAL);
		tmp.put("productsList", AttributeMode.INITIAL);
		tmp.put("maxValue", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.promotions.jalo.AbstractPromotion", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("accountName", AttributeMode.INITIAL);
		tmp.put("orderDate", AttributeMode.INITIAL);
		tmp.put("orderNumber", AttributeMode.INITIAL);
		tmp.put("poNumber", AttributeMode.INITIAL);
		tmp.put("branchNumber", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.couponservices.jalo.CouponRedemption", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("highlight", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("regulatoryStates", AttributeMode.INITIAL);
		tmp.put("lastModifiedTimeStamp", AttributeMode.INITIAL);
		tmp.put("isProductOffline", AttributeMode.INITIAL);
		tmp.put("hideCSP", AttributeMode.INITIAL);
		tmp.put("hideList", AttributeMode.INITIAL);
		tmp.put("inventoryCheck", AttributeMode.INITIAL);
		tmp.put("division", AttributeMode.INITIAL);
		tmp.put("pageTitle", AttributeMode.INITIAL);
		tmp.put("deliveryAlwaysEnabledBranches", AttributeMode.INITIAL);
		tmp.put("isShippable", AttributeMode.INITIAL);
		tmp.put("maxShippableDollarAmount", AttributeMode.INITIAL);
		tmp.put("maxShippableQuantity", AttributeMode.INITIAL);
		tmp.put("upcData", AttributeMode.INITIAL);
		tmp.put("isDeliverable", AttributeMode.INITIAL);
		tmp.put("isTransferrable", AttributeMode.INITIAL);
		tmp.put("salientBullets", AttributeMode.INITIAL);
		tmp.put("calcUrl", AttributeMode.INITIAL);
		tmp.put("brandLogos", AttributeMode.INITIAL);
		tmp.put("swatchImages", AttributeMode.INITIAL);
		tmp.put("lifeStyles", AttributeMode.INITIAL);
		tmp.put("specialImageTypes", AttributeMode.INITIAL);
		tmp.put("regionallyAssorted", AttributeMode.INITIAL);
		tmp.put("photoCredit", AttributeMode.INITIAL);
		tmp.put("secretSku", AttributeMode.INITIAL);
		tmp.put("specificationItem", AttributeMode.INITIAL);
		tmp.put("specificationType", AttributeMode.INITIAL);
		tmp.put("specificationSeries", AttributeMode.INITIAL);
		tmp.put("eeee", AttributeMode.INITIAL);
		tmp.put("allKeywords", AttributeMode.INITIAL);
		tmp.put("bulkFlag", AttributeMode.INITIAL);
		tmp.put("weighAndPayEnabled", AttributeMode.INITIAL);
		tmp.put("savingsCenter", AttributeMode.INITIAL);
		tmp.put("convertedProduct", AttributeMode.INITIAL);
		tmp.put("isConverted", AttributeMode.INITIAL);
		tmp.put("productQrCode", AttributeMode.INITIAL);
		tmp.put("InventoryUOM", AttributeMode.INITIAL);
		tmp.put("deal", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.product.Product", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("classAttributeAssignments", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.solrfacetsearch.jalo.config.SolrIndexedProperty", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("isShared", AttributeMode.INITIAL);
		tmp.put("createdBy", AttributeMode.INITIAL);
		tmp.put("owners", AttributeMode.INITIAL);
		tmp.put("viewEditOwners", AttributeMode.INITIAL);
		tmp.put("shareNotes", AttributeMode.INITIAL);
		tmp.put("listType", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.wishlist2.jalo.Wishlist2", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("orderType", AttributeMode.INITIAL);
		tmp.put("pointOfService", AttributeMode.INITIAL);
		tmp.put("contactPerson", AttributeMode.INITIAL);
		tmp.put("deliveryContactPerson", AttributeMode.INITIAL);
		tmp.put("shippingContactPerson", AttributeMode.INITIAL);
		tmp.put("requestedDate", AttributeMode.INITIAL);
		tmp.put("orderingAccount", AttributeMode.INITIAL);
		tmp.put("specialInstruction", AttributeMode.INITIAL);
		tmp.put("approvalComments", AttributeMode.INITIAL);
		tmp.put("pickupInstruction", AttributeMode.INITIAL);
		tmp.put("deliveryInstruction", AttributeMode.INITIAL);
		tmp.put("shippingInstruction", AttributeMode.INITIAL);
		tmp.put("requestedMeridian", AttributeMode.INITIAL);
		tmp.put("invoiceNumber", AttributeMode.INITIAL);
		tmp.put("hybrisOrderNumber", AttributeMode.INITIAL);
		tmp.put("storeUser", AttributeMode.INITIAL);
		tmp.put("storeContact", AttributeMode.INITIAL);
		tmp.put("storeUserName", AttributeMode.INITIAL);
		tmp.put("isShippingFeeBranch", AttributeMode.INITIAL);
		tmp.put("isOrderStatusEmailSent", AttributeMode.INITIAL);
		tmp.put("isQuoteToOrderStatusEmailSent", AttributeMode.INITIAL);
		tmp.put("isRemainderEmailSent", AttributeMode.INITIAL);
		tmp.put("isOrderStatusNotificationSent", AttributeMode.INITIAL);
		tmp.put("storeUserContactNumber", AttributeMode.INITIAL);
		tmp.put("freight", AttributeMode.INITIAL);
		tmp.put("isHybrisOrder", AttributeMode.INITIAL);
		tmp.put("isCartSizeExceeds", AttributeMode.INITIAL);
		tmp.put("isDuplicate", AttributeMode.INITIAL);
		tmp.put("totalDiscountAmount", AttributeMode.INITIAL);
		tmp.put("paymentInfoList", AttributeMode.INITIAL);
		tmp.put("poaPaymentInfoList", AttributeMode.INITIAL);
		tmp.put("homeBranchNumber", AttributeMode.INITIAL);
		tmp.put("externalSystemId", AttributeMode.INITIAL);
		tmp.put("isNPSSurveyTriggered", AttributeMode.INITIAL);
		tmp.put("isExpedited", AttributeMode.INITIAL);
		tmp.put("chooseLift", AttributeMode.INITIAL);
		tmp.put("guestContactPerson", AttributeMode.INITIAL);
		tmp.put("guestDeliveryContactPerson", AttributeMode.INITIAL);
		tmp.put("guestShippingContactPerson", AttributeMode.INITIAL);
		tmp.put("shippingAddress", AttributeMode.INITIAL);
		tmp.put("pickupAddress", AttributeMode.INITIAL);
		tmp.put("guestPickupAddress", AttributeMode.INITIAL);
		tmp.put("isSameaddressforParcelShip", AttributeMode.INITIAL);
		tmp.put("isSameaddressforDelivery", AttributeMode.INITIAL);
		tmp.put("deliveryFreight", AttributeMode.INITIAL);
		tmp.put("shippingFreight", AttributeMode.INITIAL);
		tmp.put("asmAgent", AttributeMode.INITIAL);
		tmp.put("homeStoreNumber", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.order.AbstractOrder", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("phoneId", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.user.PhoneContactInfo", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("inventoryUOM", AttributeMode.INITIAL);
		tmp.put("isBaseUom", AttributeMode.INITIAL);
		tmp.put("quantityText", AttributeMode.INITIAL);
		tmp.put("couponDescription", AttributeMode.INITIAL);
		tmp.put("discountAmount", AttributeMode.INITIAL);
		tmp.put("actualItemCost", AttributeMode.INITIAL);
		tmp.put("listPrice", AttributeMode.INITIAL);
		tmp.put("isCustomerPrice", AttributeMode.INITIAL);
		tmp.put("uomPrice", AttributeMode.INITIAL);
		tmp.put("isPromotionEnabled", AttributeMode.INITIAL);
		tmp.put("fullfillmentStoreId", AttributeMode.INITIAL);
		tmp.put("fullfilledStoreType", AttributeMode.INITIAL);
		tmp.put("isShippingOnlyProduct", AttributeMode.INITIAL);
		tmp.put("totaltax", AttributeMode.INITIAL);
		tmp.put("bigBagInfo", AttributeMode.INITIAL);
		tmp.put("storeProductCode", AttributeMode.INITIAL);
		tmp.put("storeProductItemNumber", AttributeMode.INITIAL);
		tmp.put("storeProductDesc", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.order.AbstractOrderEntry", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("ccEmails", AttributeMode.INITIAL);
		tmp.put("bccEmails", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.processengine.jalo.BusinessProcess", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("couponCode", AttributeMode.INITIAL);
		tmp.put("productCoupon", AttributeMode.INITIAL);
		tmp.put("promotionDetails", AttributeMode.INITIAL);
		tmp.put("isDescriptionEnabled", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.promotionengineservices.jalo.PromotionSourceRule", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("expirationTimeInSeconds", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.commerceservices.jalo.process.ForgottenPasswordProcess", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("paymentMethod", AttributeMode.INITIAL);
		tmp.put("QuoteNumber", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.order.Order", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("inventoryHit", AttributeMode.INITIAL);
		tmp.put("lastFeedTimeStamp", AttributeMode.INITIAL);
		tmp.put("onHand", AttributeMode.INITIAL);
		tmp.put("committed", AttributeMode.INITIAL);
		tmp.put("incomingPO", AttributeMode.INITIAL);
		tmp.put("incTransfer", AttributeMode.INITIAL);
		tmp.put("quantityLevel", AttributeMode.INITIAL);
		tmp.put("forceInStock", AttributeMode.INITIAL);
		tmp.put("respectInventory", AttributeMode.INITIAL);
		tmp.put("forceStockRespectNearby", AttributeMode.INITIAL);
		tmp.put("isNurseryCoreSku", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.ordersplitting.jalo.StockLevel", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("isTerritory", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.c2l.Region", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("recoveryToken", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.user.Customer", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("logMessage", AttributeMode.INITIAL);
		tmp.put("toEmails", AttributeMode.INITIAL);
		tmp.put("salesApplication", AttributeMode.INITIAL);
		tmp.put("isShippingFeeBranch", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.orderprocessing.jalo.OrderProcess", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("isCartAbandonmentEmailSent", AttributeMode.INITIAL);
		tmp.put("paymentMethod", AttributeMode.INITIAL);
		tmp.put("deliverableItemTotal", AttributeMode.INITIAL);
		tmp.put("shippableItemTotal", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.order.Cart", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("iTunesUrl", AttributeMode.INITIAL);
		tmp.put("playStoreUrl", AttributeMode.INITIAL);
		tmp.put("iTunesImage", AttributeMode.INITIAL);
		tmp.put("playStoreImage", AttributeMode.INITIAL);
		tmp.put("iTuneLabel", AttributeMode.INITIAL);
		tmp.put("playStoreLabel", AttributeMode.INITIAL);
		tmp.put("extendedNotice", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.acceleratorcms.jalo.components.FooterNavigationComponent", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("uomId", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.wishlist2.jalo.Wishlist2Entry", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("scheduledDate", AttributeMode.INITIAL);
		tmp.put("requestedDate", AttributeMode.INITIAL);
		tmp.put("invoiceNumber", AttributeMode.INITIAL);
		tmp.put("requestedMeridian", AttributeMode.INITIAL);
		tmp.put("total", AttributeMode.INITIAL);
		tmp.put("fullfilmentStatus", AttributeMode.INITIAL);
		tmp.put("specialInstructions", AttributeMode.INITIAL);
		tmp.put("freight", AttributeMode.INITIAL);
		tmp.put("tax", AttributeMode.INITIAL);
		tmp.put("subTotal", AttributeMode.INITIAL);
		tmp.put("consignmentAddress", AttributeMode.INITIAL);
		tmp.put("hybrisConsignmentId", AttributeMode.INITIAL);
		tmp.put("trackingLink", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.ordersplitting.jalo.Consignment", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("entryNumber", AttributeMode.INITIAL);
		tmp.put("hybrisConsignmentId", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.ordersplitting.jalo.ConsignmentEntry", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("modifiedRank", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.personalizationservices.jalo.CxCustomization", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("displayContents", AttributeMode.INITIAL);
		tmp.put("maxContents", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.acceleratorcms.jalo.components.SearchBoxComponent", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("description", AttributeMode.INITIAL);
		tmp.put("image", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.commerceservices.jalo.storelocator.StoreLocatorFeature", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("BusinessProcess", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.acceleratorservices.jalo.email.EmailAttachment", Collections.unmodifiableMap(tmp));
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.accountGroupCode</code> attribute.
	 * @return the accountGroupCode - customer Account group code
	 */
	public String getAccountGroupCode(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.ACCOUNTGROUPCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.accountGroupCode</code> attribute.
	 * @return the accountGroupCode - customer Account group code
	 */
	public String getAccountGroupCode(final B2BUnit item)
	{
		return getAccountGroupCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.accountGroupCode</code> attribute. 
	 * @param value the accountGroupCode - customer Account group code
	 */
	public void setAccountGroupCode(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.ACCOUNTGROUPCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.accountGroupCode</code> attribute. 
	 * @param value the accountGroupCode - customer Account group code
	 */
	public void setAccountGroupCode(final B2BUnit item, final String value)
	{
		setAccountGroupCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.accountManagerEmail</code> attribute.
	 * @return the accountManagerEmail
	 */
	public String getAccountManagerEmail(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.ACCOUNTMANAGEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.accountManagerEmail</code> attribute.
	 * @return the accountManagerEmail
	 */
	public String getAccountManagerEmail(final B2BUnit item)
	{
		return getAccountManagerEmail( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.accountManagerEmail</code> attribute. 
	 * @param value the accountManagerEmail
	 */
	public void setAccountManagerEmail(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.ACCOUNTMANAGEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.accountManagerEmail</code> attribute. 
	 * @param value the accountManagerEmail
	 */
	public void setAccountManagerEmail(final B2BUnit item, final String value)
	{
		setAccountManagerEmail( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.accountManagerName</code> attribute.
	 * @return the accountManagerName
	 */
	public String getAccountManagerName(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.ACCOUNTMANAGERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.accountManagerName</code> attribute.
	 * @return the accountManagerName
	 */
	public String getAccountManagerName(final B2BUnit item)
	{
		return getAccountManagerName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.accountManagerName</code> attribute. 
	 * @param value the accountManagerName
	 */
	public void setAccountManagerName(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.ACCOUNTMANAGERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.accountManagerName</code> attribute. 
	 * @param value the accountManagerName
	 */
	public void setAccountManagerName(final B2BUnit item, final String value)
	{
		setAccountManagerName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CouponRedemption.accountName</code> attribute.
	 * @return the accountName
	 */
	public String getAccountName(final SessionContext ctx, final CouponRedemption item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.CouponRedemption.ACCOUNTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CouponRedemption.accountName</code> attribute.
	 * @return the accountName
	 */
	public String getAccountName(final CouponRedemption item)
	{
		return getAccountName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CouponRedemption.accountName</code> attribute. 
	 * @param value the accountName
	 */
	public void setAccountName(final SessionContext ctx, final CouponRedemption item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.CouponRedemption.ACCOUNTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CouponRedemption.accountName</code> attribute. 
	 * @param value the accountName
	 */
	public void setAccountName(final CouponRedemption item, final String value)
	{
		setAccountName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.accountOverviewForParent</code> attribute.
	 * @return the accountOverviewForParent
	 */
	public Boolean isAccountOverviewForParent(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ACCOUNTOVERVIEWFORPARENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.accountOverviewForParent</code> attribute.
	 * @return the accountOverviewForParent
	 */
	public Boolean isAccountOverviewForParent(final B2BCustomer item)
	{
		return isAccountOverviewForParent( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.accountOverviewForParent</code> attribute. 
	 * @return the accountOverviewForParent
	 */
	public boolean isAccountOverviewForParentAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isAccountOverviewForParent( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.accountOverviewForParent</code> attribute. 
	 * @return the accountOverviewForParent
	 */
	public boolean isAccountOverviewForParentAsPrimitive(final B2BCustomer item)
	{
		return isAccountOverviewForParentAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.accountOverviewForParent</code> attribute. 
	 * @param value the accountOverviewForParent
	 */
	public void setAccountOverviewForParent(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ACCOUNTOVERVIEWFORPARENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.accountOverviewForParent</code> attribute. 
	 * @param value the accountOverviewForParent
	 */
	public void setAccountOverviewForParent(final B2BCustomer item, final Boolean value)
	{
		setAccountOverviewForParent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.accountOverviewForParent</code> attribute. 
	 * @param value the accountOverviewForParent
	 */
	public void setAccountOverviewForParent(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setAccountOverviewForParent( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.accountOverviewForParent</code> attribute. 
	 * @param value the accountOverviewForParent
	 */
	public void setAccountOverviewForParent(final B2BCustomer item, final boolean value)
	{
		setAccountOverviewForParent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.accountOverviewForShipTos</code> attribute.
	 * @return the accountOverviewForShipTos
	 */
	public Boolean isAccountOverviewForShipTos(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ACCOUNTOVERVIEWFORSHIPTOS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.accountOverviewForShipTos</code> attribute.
	 * @return the accountOverviewForShipTos
	 */
	public Boolean isAccountOverviewForShipTos(final B2BCustomer item)
	{
		return isAccountOverviewForShipTos( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.accountOverviewForShipTos</code> attribute. 
	 * @return the accountOverviewForShipTos
	 */
	public boolean isAccountOverviewForShipTosAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isAccountOverviewForShipTos( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.accountOverviewForShipTos</code> attribute. 
	 * @return the accountOverviewForShipTos
	 */
	public boolean isAccountOverviewForShipTosAsPrimitive(final B2BCustomer item)
	{
		return isAccountOverviewForShipTosAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.accountOverviewForShipTos</code> attribute. 
	 * @param value the accountOverviewForShipTos
	 */
	public void setAccountOverviewForShipTos(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ACCOUNTOVERVIEWFORSHIPTOS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.accountOverviewForShipTos</code> attribute. 
	 * @param value the accountOverviewForShipTos
	 */
	public void setAccountOverviewForShipTos(final B2BCustomer item, final Boolean value)
	{
		setAccountOverviewForShipTos( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.accountOverviewForShipTos</code> attribute. 
	 * @param value the accountOverviewForShipTos
	 */
	public void setAccountOverviewForShipTos(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setAccountOverviewForShipTos( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.accountOverviewForShipTos</code> attribute. 
	 * @param value the accountOverviewForShipTos
	 */
	public void setAccountOverviewForShipTos(final B2BCustomer item, final boolean value)
	{
		setAccountOverviewForShipTos( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.acquisitionOrCoBrandedBranch</code> attribute.
	 * @return the acquisitionOrCoBrandedBranch
	 */
	public Boolean isAcquisitionOrCoBrandedBranch(final SessionContext ctx, final PointOfService item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.ACQUISITIONORCOBRANDEDBRANCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.acquisitionOrCoBrandedBranch</code> attribute.
	 * @return the acquisitionOrCoBrandedBranch
	 */
	public Boolean isAcquisitionOrCoBrandedBranch(final PointOfService item)
	{
		return isAcquisitionOrCoBrandedBranch( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.acquisitionOrCoBrandedBranch</code> attribute. 
	 * @return the acquisitionOrCoBrandedBranch
	 */
	public boolean isAcquisitionOrCoBrandedBranchAsPrimitive(final SessionContext ctx, final PointOfService item)
	{
		Boolean value = isAcquisitionOrCoBrandedBranch( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.acquisitionOrCoBrandedBranch</code> attribute. 
	 * @return the acquisitionOrCoBrandedBranch
	 */
	public boolean isAcquisitionOrCoBrandedBranchAsPrimitive(final PointOfService item)
	{
		return isAcquisitionOrCoBrandedBranchAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.acquisitionOrCoBrandedBranch</code> attribute. 
	 * @param value the acquisitionOrCoBrandedBranch
	 */
	public void setAcquisitionOrCoBrandedBranch(final SessionContext ctx, final PointOfService item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.ACQUISITIONORCOBRANDEDBRANCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.acquisitionOrCoBrandedBranch</code> attribute. 
	 * @param value the acquisitionOrCoBrandedBranch
	 */
	public void setAcquisitionOrCoBrandedBranch(final PointOfService item, final Boolean value)
	{
		setAcquisitionOrCoBrandedBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.acquisitionOrCoBrandedBranch</code> attribute. 
	 * @param value the acquisitionOrCoBrandedBranch
	 */
	public void setAcquisitionOrCoBrandedBranch(final SessionContext ctx, final PointOfService item, final boolean value)
	{
		setAcquisitionOrCoBrandedBranch( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.acquisitionOrCoBrandedBranch</code> attribute. 
	 * @param value the acquisitionOrCoBrandedBranch
	 */
	public void setAcquisitionOrCoBrandedBranch(final PointOfService item, final boolean value)
	{
		setAcquisitionOrCoBrandedBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.actualItemCost</code> attribute.
	 * @return the actualItemCost
	 */
	public Double getActualItemCost(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.ACTUALITEMCOST);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.actualItemCost</code> attribute.
	 * @return the actualItemCost
	 */
	public Double getActualItemCost(final AbstractOrderEntry item)
	{
		return getActualItemCost( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.actualItemCost</code> attribute. 
	 * @return the actualItemCost
	 */
	public double getActualItemCostAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getActualItemCost( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.actualItemCost</code> attribute. 
	 * @return the actualItemCost
	 */
	public double getActualItemCostAsPrimitive(final AbstractOrderEntry item)
	{
		return getActualItemCostAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.actualItemCost</code> attribute. 
	 * @param value the actualItemCost
	 */
	public void setActualItemCost(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.ACTUALITEMCOST,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.actualItemCost</code> attribute. 
	 * @param value the actualItemCost
	 */
	public void setActualItemCost(final AbstractOrderEntry item, final Double value)
	{
		setActualItemCost( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.actualItemCost</code> attribute. 
	 * @param value the actualItemCost
	 */
	public void setActualItemCost(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setActualItemCost( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.actualItemCost</code> attribute. 
	 * @param value the actualItemCost
	 */
	public void setActualItemCost(final AbstractOrderEntry item, final double value)
	{
		setActualItemCost( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.allKeywords</code> attribute.
	 * @return the allKeywords
	 */
	public String getAllKeywords(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.ALLKEYWORDS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.allKeywords</code> attribute.
	 * @return the allKeywords
	 */
	public String getAllKeywords(final Product item)
	{
		return getAllKeywords( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.allKeywords</code> attribute. 
	 * @param value the allKeywords
	 */
	public void setAllKeywords(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.ALLKEYWORDS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.allKeywords</code> attribute. 
	 * @param value the allKeywords
	 */
	public void setAllKeywords(final Product item, final String value)
	{
		setAllKeywords( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.approvalComments</code> attribute.
	 * @return the approvalComments
	 */
	public String getApprovalComments(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.APPROVALCOMMENTS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.approvalComments</code> attribute.
	 * @return the approvalComments
	 */
	public String getApprovalComments(final AbstractOrder item)
	{
		return getApprovalComments( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.approvalComments</code> attribute. 
	 * @param value the approvalComments
	 */
	public void setApprovalComments(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.APPROVALCOMMENTS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.approvalComments</code> attribute. 
	 * @param value the approvalComments
	 */
	public void setApprovalComments(final AbstractOrder item, final String value)
	{
		setApprovalComments( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.area</code> attribute.
	 * @return the area
	 */
	public String getArea(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.AREA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.area</code> attribute.
	 * @return the area
	 */
	public String getArea(final PointOfService item)
	{
		return getArea( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.area</code> attribute. 
	 * @param value the area
	 */
	public void setArea(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.AREA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.area</code> attribute. 
	 * @param value the area
	 */
	public void setArea(final PointOfService item, final String value)
	{
		setArea( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.areaName</code> attribute.
	 * @return the areaName
	 */
	public String getAreaName(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.AREANAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.areaName</code> attribute.
	 * @return the areaName
	 */
	public String getAreaName(final PointOfService item)
	{
		return getAreaName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.areaName</code> attribute. 
	 * @param value the areaName
	 */
	public void setAreaName(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.AREANAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.areaName</code> attribute. 
	 * @param value the areaName
	 */
	public void setAreaName(final PointOfService item, final String value)
	{
		setAreaName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.asmAgent</code> attribute.
	 * @return the asmAgent
	 */
	public Employee getAsmAgent(final SessionContext ctx, final AbstractOrder item)
	{
		return (Employee)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ASMAGENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.asmAgent</code> attribute.
	 * @return the asmAgent
	 */
	public Employee getAsmAgent(final AbstractOrder item)
	{
		return getAsmAgent( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.asmAgent</code> attribute. 
	 * @param value the asmAgent
	 */
	public void setAsmAgent(final SessionContext ctx, final AbstractOrder item, final Employee value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ASMAGENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.asmAgent</code> attribute. 
	 * @param value the asmAgent
	 */
	public void setAsmAgent(final AbstractOrder item, final Employee value)
	{
		setAsmAgent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.assignedProducts</code> attribute.
	 * @return the assignedProducts
	 */
	public Set<Product> getAssignedProducts(final SessionContext ctx, final B2BUnit item)
	{
		final List<Product> items = item.getLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.B2BUNIT2PRODUCTRELATION,
			"Product",
			null,
			false,
			false
		);
		return new LinkedHashSet<Product>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.assignedProducts</code> attribute.
	 * @return the assignedProducts
	 */
	public Set<Product> getAssignedProducts(final B2BUnit item)
	{
		return getAssignedProducts( getSession().getSessionContext(), item );
	}
	
	public long getAssignedProductsCount(final SessionContext ctx, final B2BUnit item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			SiteoneCoreConstants.Relations.B2BUNIT2PRODUCTRELATION,
			"Product",
			null
		);
	}
	
	public long getAssignedProductsCount(final B2BUnit item)
	{
		return getAssignedProductsCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.assignedProducts</code> attribute. 
	 * @param value the assignedProducts
	 */
	public void setAssignedProducts(final SessionContext ctx, final B2BUnit item, final Set<Product> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.B2BUNIT2PRODUCTRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(B2BUNIT2PRODUCTRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.assignedProducts</code> attribute. 
	 * @param value the assignedProducts
	 */
	public void setAssignedProducts(final B2BUnit item, final Set<Product> value)
	{
		setAssignedProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to assignedProducts. 
	 * @param value the item to add to assignedProducts
	 */
	public void addToAssignedProducts(final SessionContext ctx, final B2BUnit item, final Product value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.B2BUNIT2PRODUCTRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(B2BUNIT2PRODUCTRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to assignedProducts. 
	 * @param value the item to add to assignedProducts
	 */
	public void addToAssignedProducts(final B2BUnit item, final Product value)
	{
		addToAssignedProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from assignedProducts. 
	 * @param value the item to remove from assignedProducts
	 */
	public void removeFromAssignedProducts(final SessionContext ctx, final B2BUnit item, final Product value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.B2BUNIT2PRODUCTRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(B2BUNIT2PRODUCTRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from assignedProducts. 
	 * @param value the item to remove from assignedProducts
	 */
	public void removeFromAssignedProducts(final B2BUnit item, final Product value)
	{
		removeFromAssignedProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BusinessProcess.Attachments</code> attribute.
	 * @return the Attachments
	 */
	public List<EmailAttachment> getAttachments(final SessionContext ctx, final BusinessProcess item)
	{
		return (List<EmailAttachment>)BUSINESSPROCESS2EMAILATTACHMENTRELATTACHMENTSHANDLER.getValues( ctx, item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BusinessProcess.Attachments</code> attribute.
	 * @return the Attachments
	 */
	public List<EmailAttachment> getAttachments(final BusinessProcess item)
	{
		return getAttachments( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BusinessProcess.Attachments</code> attribute. 
	 * @param value the Attachments
	 */
	public void setAttachments(final SessionContext ctx, final BusinessProcess item, final List<EmailAttachment> value)
	{
		BUSINESSPROCESS2EMAILATTACHMENTRELATTACHMENTSHANDLER.setValues( ctx, item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BusinessProcess.Attachments</code> attribute. 
	 * @param value the Attachments
	 */
	public void setAttachments(final BusinessProcess item, final List<EmailAttachment> value)
	{
		setAttachments( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to Attachments. 
	 * @param value the item to add to Attachments
	 */
	public void addToAttachments(final SessionContext ctx, final BusinessProcess item, final EmailAttachment value)
	{
		BUSINESSPROCESS2EMAILATTACHMENTRELATTACHMENTSHANDLER.addValue( ctx, item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to Attachments. 
	 * @param value the item to add to Attachments
	 */
	public void addToAttachments(final BusinessProcess item, final EmailAttachment value)
	{
		addToAttachments( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from Attachments. 
	 * @param value the item to remove from Attachments
	 */
	public void removeFromAttachments(final SessionContext ctx, final BusinessProcess item, final EmailAttachment value)
	{
		BUSINESSPROCESS2EMAILATTACHMENTRELATTACHMENTSHANDLER.removeValue( ctx, item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from Attachments. 
	 * @param value the item to remove from Attachments
	 */
	public void removeFromAttachments(final BusinessProcess item, final EmailAttachment value)
	{
		removeFromAttachments( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.b2bUnitGroup</code> attribute.
	 * @return the b2bUnitGroup
	 */
	public Set<B2BUnit> getB2bUnitGroup(final SessionContext ctx, final Product item)
	{
		final List<B2BUnit> items = item.getLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.B2BUNIT2PRODUCTRELATION,
			"B2BUnit",
			null,
			false,
			false
		);
		return new LinkedHashSet<B2BUnit>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.b2bUnitGroup</code> attribute.
	 * @return the b2bUnitGroup
	 */
	public Set<B2BUnit> getB2bUnitGroup(final Product item)
	{
		return getB2bUnitGroup( getSession().getSessionContext(), item );
	}
	
	public long getB2bUnitGroupCount(final SessionContext ctx, final Product item)
	{
		return item.getLinkedItemsCount(
			ctx,
			false,
			SiteoneCoreConstants.Relations.B2BUNIT2PRODUCTRELATION,
			"B2BUnit",
			null
		);
	}
	
	public long getB2bUnitGroupCount(final Product item)
	{
		return getB2bUnitGroupCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.b2bUnitGroup</code> attribute. 
	 * @param value the b2bUnitGroup
	 */
	public void setB2bUnitGroup(final SessionContext ctx, final Product item, final Set<B2BUnit> value)
	{
		item.setLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.B2BUNIT2PRODUCTRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(B2BUNIT2PRODUCTRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.b2bUnitGroup</code> attribute. 
	 * @param value the b2bUnitGroup
	 */
	public void setB2bUnitGroup(final Product item, final Set<B2BUnit> value)
	{
		setB2bUnitGroup( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to b2bUnitGroup. 
	 * @param value the item to add to b2bUnitGroup
	 */
	public void addToB2bUnitGroup(final SessionContext ctx, final Product item, final B2BUnit value)
	{
		item.addLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.B2BUNIT2PRODUCTRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(B2BUNIT2PRODUCTRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to b2bUnitGroup. 
	 * @param value the item to add to b2bUnitGroup
	 */
	public void addToB2bUnitGroup(final Product item, final B2BUnit value)
	{
		addToB2bUnitGroup( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from b2bUnitGroup. 
	 * @param value the item to remove from b2bUnitGroup
	 */
	public void removeFromB2bUnitGroup(final SessionContext ctx, final Product item, final B2BUnit value)
	{
		item.removeLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.B2BUNIT2PRODUCTRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(B2BUNIT2PRODUCTRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from b2bUnitGroup. 
	 * @param value the item to remove from b2bUnitGroup
	 */
	public void removeFromB2bUnitGroup(final Product item, final B2BUnit value)
	{
		removeFromB2bUnitGroup( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.bannerList</code> attribute.
	 * @return the bannerList
	 */
	public List<SiteOneMarketingBannerComponent> getBannerList(final SessionContext ctx, final Category item)
	{
		List<SiteOneMarketingBannerComponent> coll = (List<SiteOneMarketingBannerComponent>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Category.BANNERLIST);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.bannerList</code> attribute.
	 * @return the bannerList
	 */
	public List<SiteOneMarketingBannerComponent> getBannerList(final Category item)
	{
		return getBannerList( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.bannerList</code> attribute. 
	 * @param value the bannerList
	 */
	public void setBannerList(final SessionContext ctx, final Category item, final List<SiteOneMarketingBannerComponent> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Category.BANNERLIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.bannerList</code> attribute. 
	 * @param value the bannerList
	 */
	public void setBannerList(final Category item, final List<SiteOneMarketingBannerComponent> value)
	{
		setBannerList( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BusinessProcess.bccEmails</code> attribute.
	 * @return the bccEmails
	 */
	public Map<String,String> getAllBccEmails(final SessionContext ctx, final BusinessProcess item)
	{
		Map<String,String> map = (Map<String,String>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.BusinessProcess.BCCEMAILS);
		return map != null ? map : Collections.EMPTY_MAP;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BusinessProcess.bccEmails</code> attribute.
	 * @return the bccEmails
	 */
	public Map<String,String> getAllBccEmails(final BusinessProcess item)
	{
		return getAllBccEmails( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BusinessProcess.bccEmails</code> attribute. 
	 * @param value the bccEmails
	 */
	public void setAllBccEmails(final SessionContext ctx, final BusinessProcess item, final Map<String,String> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.BusinessProcess.BCCEMAILS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BusinessProcess.bccEmails</code> attribute. 
	 * @param value the bccEmails
	 */
	public void setAllBccEmails(final BusinessProcess item, final Map<String,String> value)
	{
		setAllBccEmails( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.bigBag</code> attribute.
	 * @return the bigBag
	 */
	public Boolean isBigBag(final SessionContext ctx, final PointOfService item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.BIGBAG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.bigBag</code> attribute.
	 * @return the bigBag
	 */
	public Boolean isBigBag(final PointOfService item)
	{
		return isBigBag( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.bigBag</code> attribute. 
	 * @return the bigBag
	 */
	public boolean isBigBagAsPrimitive(final SessionContext ctx, final PointOfService item)
	{
		Boolean value = isBigBag( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.bigBag</code> attribute. 
	 * @return the bigBag
	 */
	public boolean isBigBagAsPrimitive(final PointOfService item)
	{
		return isBigBagAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.bigBag</code> attribute. 
	 * @param value the bigBag
	 */
	public void setBigBag(final SessionContext ctx, final PointOfService item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.BIGBAG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.bigBag</code> attribute. 
	 * @param value the bigBag
	 */
	public void setBigBag(final PointOfService item, final Boolean value)
	{
		setBigBag( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.bigBag</code> attribute. 
	 * @param value the bigBag
	 */
	public void setBigBag(final SessionContext ctx, final PointOfService item, final boolean value)
	{
		setBigBag( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.bigBag</code> attribute. 
	 * @param value the bigBag
	 */
	public void setBigBag(final PointOfService item, final boolean value)
	{
		setBigBag( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.bigBagInfo</code> attribute.
	 * @return the bigBagInfo
	 */
	public BagInfo getBigBagInfo(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (BagInfo)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.BIGBAGINFO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.bigBagInfo</code> attribute.
	 * @return the bigBagInfo
	 */
	public BagInfo getBigBagInfo(final AbstractOrderEntry item)
	{
		return getBigBagInfo( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.bigBagInfo</code> attribute. 
	 * @param value the bigBagInfo
	 */
	public void setBigBagInfo(final SessionContext ctx, final AbstractOrderEntry item, final BagInfo value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.BIGBAGINFO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.bigBagInfo</code> attribute. 
	 * @param value the bigBagInfo
	 */
	public void setBigBagInfo(final AbstractOrderEntry item, final BagInfo value)
	{
		setBigBagInfo( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.bigBagSize</code> attribute.
	 * @return the bigBagSize
	 */
	public Product getBigBagSize(final SessionContext ctx, final PointOfService item)
	{
		return (Product)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.BIGBAGSIZE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.bigBagSize</code> attribute.
	 * @return the bigBagSize
	 */
	public Product getBigBagSize(final PointOfService item)
	{
		return getBigBagSize( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.bigBagSize</code> attribute. 
	 * @param value the bigBagSize
	 */
	public void setBigBagSize(final SessionContext ctx, final PointOfService item, final Product value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.BIGBAGSIZE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.bigBagSize</code> attribute. 
	 * @param value the bigBagSize
	 */
	public void setBigBagSize(final PointOfService item, final Product value)
	{
		setBigBagSize( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.branchManagerEmail</code> attribute.
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.BRANCHMANAGEREMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.branchManagerEmail</code> attribute.
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail(final PointOfService item)
	{
		return getBranchManagerEmail( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.branchManagerEmail</code> attribute. 
	 * @param value the branchManagerEmail
	 */
	public void setBranchManagerEmail(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.BRANCHMANAGEREMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.branchManagerEmail</code> attribute. 
	 * @param value the branchManagerEmail
	 */
	public void setBranchManagerEmail(final PointOfService item, final String value)
	{
		setBranchManagerEmail( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CouponRedemption.branchNumber</code> attribute.
	 * @return the branchNumber
	 */
	public String getBranchNumber(final SessionContext ctx, final CouponRedemption item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.CouponRedemption.BRANCHNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CouponRedemption.branchNumber</code> attribute.
	 * @return the branchNumber
	 */
	public String getBranchNumber(final CouponRedemption item)
	{
		return getBranchNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CouponRedemption.branchNumber</code> attribute. 
	 * @param value the branchNumber
	 */
	public void setBranchNumber(final SessionContext ctx, final CouponRedemption item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.CouponRedemption.BRANCHNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CouponRedemption.branchNumber</code> attribute. 
	 * @param value the branchNumber
	 */
	public void setBranchNumber(final CouponRedemption item, final String value)
	{
		setBranchNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.brandLogos</code> attribute.
	 * @return the brandLogos - A list of brand logos for the product.
	 */
	public List<MediaContainer> getBrandLogos(final SessionContext ctx, final Product item)
	{
		List<MediaContainer> coll = (List<MediaContainer>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.BRANDLOGOS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.brandLogos</code> attribute.
	 * @return the brandLogos - A list of brand logos for the product.
	 */
	public List<MediaContainer> getBrandLogos(final Product item)
	{
		return getBrandLogos( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.brandLogos</code> attribute. 
	 * @param value the brandLogos - A list of brand logos for the product.
	 */
	public void setBrandLogos(final SessionContext ctx, final Product item, final List<MediaContainer> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.BRANDLOGOS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.brandLogos</code> attribute. 
	 * @param value the brandLogos - A list of brand logos for the product.
	 */
	public void setBrandLogos(final Product item, final List<MediaContainer> value)
	{
		setBrandLogos( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.bulkFlag</code> attribute.
	 * @return the bulkFlag
	 */
	public Boolean isBulkFlag(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.BULKFLAG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.bulkFlag</code> attribute.
	 * @return the bulkFlag
	 */
	public Boolean isBulkFlag(final Product item)
	{
		return isBulkFlag( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.bulkFlag</code> attribute. 
	 * @return the bulkFlag
	 */
	public boolean isBulkFlagAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isBulkFlag( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.bulkFlag</code> attribute. 
	 * @return the bulkFlag
	 */
	public boolean isBulkFlagAsPrimitive(final Product item)
	{
		return isBulkFlagAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.bulkFlag</code> attribute. 
	 * @param value the bulkFlag
	 */
	public void setBulkFlag(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.BULKFLAG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.bulkFlag</code> attribute. 
	 * @param value the bulkFlag
	 */
	public void setBulkFlag(final Product item, final Boolean value)
	{
		setBulkFlag( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.bulkFlag</code> attribute. 
	 * @param value the bulkFlag
	 */
	public void setBulkFlag(final SessionContext ctx, final Product item, final boolean value)
	{
		setBulkFlag( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.bulkFlag</code> attribute. 
	 * @param value the bulkFlag
	 */
	public void setBulkFlag(final Product item, final boolean value)
	{
		setBulkFlag( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EmailAttachment.BusinessProcess</code> attribute.
	 * @return the BusinessProcess
	 */
	public BusinessProcess getBusinessProcess(final SessionContext ctx, final EmailAttachment item)
	{
		return (BusinessProcess)item.getProperty( ctx, SiteoneCoreConstants.Attributes.EmailAttachment.BUSINESSPROCESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>EmailAttachment.BusinessProcess</code> attribute.
	 * @return the BusinessProcess
	 */
	public BusinessProcess getBusinessProcess(final EmailAttachment item)
	{
		return getBusinessProcess( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EmailAttachment.BusinessProcess</code> attribute. 
	 * @param value the BusinessProcess
	 */
	public void setBusinessProcess(final SessionContext ctx, final EmailAttachment item, final BusinessProcess value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.EmailAttachment.BUSINESSPROCESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>EmailAttachment.BusinessProcess</code> attribute. 
	 * @param value the BusinessProcess
	 */
	public void setBusinessProcess(final EmailAttachment item, final BusinessProcess value)
	{
		setBusinessProcess( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.calcUrl</code> attribute.
	 * @return the calcUrl - URL for the calculator
	 */
	public String getCalcUrl(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.CALCURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.calcUrl</code> attribute.
	 * @return the calcUrl - URL for the calculator
	 */
	public String getCalcUrl(final Product item)
	{
		return getCalcUrl( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.calcUrl</code> attribute. 
	 * @param value the calcUrl - URL for the calculator
	 */
	public void setCalcUrl(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.CALCURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.calcUrl</code> attribute. 
	 * @param value the calcUrl - URL for the calculator
	 */
	public void setCalcUrl(final Product item, final String value)
	{
		setCalcUrl( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.category</code> attribute.
	 * @return the category - Category
	 */
	public Category getCategory(final SessionContext ctx, final ContentPage item)
	{
		return (Category)item.getProperty( ctx, SiteoneCoreConstants.Attributes.ContentPage.CATEGORY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.category</code> attribute.
	 * @return the category - Category
	 */
	public Category getCategory(final ContentPage item)
	{
		return getCategory( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.category</code> attribute. 
	 * @param value the category - Category
	 */
	public void setCategory(final SessionContext ctx, final ContentPage item, final Category value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.ContentPage.CATEGORY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.category</code> attribute. 
	 * @param value the category - Category
	 */
	public void setCategory(final ContentPage item, final Category value)
	{
		setCategory( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BusinessProcess.ccEmails</code> attribute.
	 * @return the ccEmails
	 */
	public Map<String,String> getAllCcEmails(final SessionContext ctx, final BusinessProcess item)
	{
		Map<String,String> map = (Map<String,String>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.BusinessProcess.CCEMAILS);
		return map != null ? map : Collections.EMPTY_MAP;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BusinessProcess.ccEmails</code> attribute.
	 * @return the ccEmails
	 */
	public Map<String,String> getAllCcEmails(final BusinessProcess item)
	{
		return getAllCcEmails( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BusinessProcess.ccEmails</code> attribute. 
	 * @param value the ccEmails
	 */
	public void setAllCcEmails(final SessionContext ctx, final BusinessProcess item, final Map<String,String> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.BusinessProcess.CCEMAILS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BusinessProcess.ccEmails</code> attribute. 
	 * @param value the ccEmails
	 */
	public void setAllCcEmails(final BusinessProcess item, final Map<String,String> value)
	{
		setAllCcEmails( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.chooseLift</code> attribute.
	 * @return the chooseLift
	 */
	public Boolean isChooseLift(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.CHOOSELIFT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.chooseLift</code> attribute.
	 * @return the chooseLift
	 */
	public Boolean isChooseLift(final AbstractOrder item)
	{
		return isChooseLift( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.chooseLift</code> attribute. 
	 * @return the chooseLift
	 */
	public boolean isChooseLiftAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isChooseLift( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.chooseLift</code> attribute. 
	 * @return the chooseLift
	 */
	public boolean isChooseLiftAsPrimitive(final AbstractOrder item)
	{
		return isChooseLiftAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.chooseLift</code> attribute. 
	 * @param value the chooseLift
	 */
	public void setChooseLift(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.CHOOSELIFT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.chooseLift</code> attribute. 
	 * @param value the chooseLift
	 */
	public void setChooseLift(final AbstractOrder item, final Boolean value)
	{
		setChooseLift( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.chooseLift</code> attribute. 
	 * @param value the chooseLift
	 */
	public void setChooseLift(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setChooseLift( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.chooseLift</code> attribute. 
	 * @param value the chooseLift
	 */
	public void setChooseLift(final AbstractOrder item, final boolean value)
	{
		setChooseLift( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SolrIndexedProperty.classAttributeAssignments</code> attribute.
	 * @return the classAttributeAssignments
	 */
	public List<ClassAttributeAssignment> getClassAttributeAssignments(final SessionContext ctx, final SolrIndexedProperty item)
	{
		List<ClassAttributeAssignment> coll = (List<ClassAttributeAssignment>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.SolrIndexedProperty.CLASSATTRIBUTEASSIGNMENTS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SolrIndexedProperty.classAttributeAssignments</code> attribute.
	 * @return the classAttributeAssignments
	 */
	public List<ClassAttributeAssignment> getClassAttributeAssignments(final SolrIndexedProperty item)
	{
		return getClassAttributeAssignments( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SolrIndexedProperty.classAttributeAssignments</code> attribute. 
	 * @param value the classAttributeAssignments
	 */
	public void setClassAttributeAssignments(final SessionContext ctx, final SolrIndexedProperty item, final List<ClassAttributeAssignment> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.SolrIndexedProperty.CLASSATTRIBUTEASSIGNMENTS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SolrIndexedProperty.classAttributeAssignments</code> attribute. 
	 * @param value the classAttributeAssignments
	 */
	public void setClassAttributeAssignments(final SolrIndexedProperty item, final List<ClassAttributeAssignment> value)
	{
		setClassAttributeAssignments( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.committed</code> attribute.
	 * @return the committed
	 */
	public Integer getCommitted(final SessionContext ctx, final StockLevel item)
	{
		return (Integer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.StockLevel.COMMITTED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.committed</code> attribute.
	 * @return the committed
	 */
	public Integer getCommitted(final StockLevel item)
	{
		return getCommitted( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.committed</code> attribute. 
	 * @return the committed
	 */
	public int getCommittedAsPrimitive(final SessionContext ctx, final StockLevel item)
	{
		Integer value = getCommitted( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.committed</code> attribute. 
	 * @return the committed
	 */
	public int getCommittedAsPrimitive(final StockLevel item)
	{
		return getCommittedAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.committed</code> attribute. 
	 * @param value the committed
	 */
	public void setCommitted(final SessionContext ctx, final StockLevel item, final Integer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.StockLevel.COMMITTED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.committed</code> attribute. 
	 * @param value the committed
	 */
	public void setCommitted(final StockLevel item, final Integer value)
	{
		setCommitted( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.committed</code> attribute. 
	 * @param value the committed
	 */
	public void setCommitted(final SessionContext ctx, final StockLevel item, final int value)
	{
		setCommitted( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.committed</code> attribute. 
	 * @param value the committed
	 */
	public void setCommitted(final StockLevel item, final int value)
	{
		setCommitted( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.confidenceCode</code> attribute.
	 * @return the confidenceCode
	 */
	public Integer getConfidenceCode(final SessionContext ctx, final B2BUnit item)
	{
		return (Integer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.CONFIDENCECODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.confidenceCode</code> attribute.
	 * @return the confidenceCode
	 */
	public Integer getConfidenceCode(final B2BUnit item)
	{
		return getConfidenceCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.confidenceCode</code> attribute. 
	 * @return the confidenceCode
	 */
	public int getConfidenceCodeAsPrimitive(final SessionContext ctx, final B2BUnit item)
	{
		Integer value = getConfidenceCode( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.confidenceCode</code> attribute. 
	 * @return the confidenceCode
	 */
	public int getConfidenceCodeAsPrimitive(final B2BUnit item)
	{
		return getConfidenceCodeAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.confidenceCode</code> attribute. 
	 * @param value the confidenceCode
	 */
	public void setConfidenceCode(final SessionContext ctx, final B2BUnit item, final Integer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.CONFIDENCECODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.confidenceCode</code> attribute. 
	 * @param value the confidenceCode
	 */
	public void setConfidenceCode(final B2BUnit item, final Integer value)
	{
		setConfidenceCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.confidenceCode</code> attribute. 
	 * @param value the confidenceCode
	 */
	public void setConfidenceCode(final SessionContext ctx, final B2BUnit item, final int value)
	{
		setConfidenceCode( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.confidenceCode</code> attribute. 
	 * @param value the confidenceCode
	 */
	public void setConfidenceCode(final B2BUnit item, final int value)
	{
		setConfidenceCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.consignmentAddress</code> attribute.
	 * @return the consignmentAddress
	 */
	public Address getConsignmentAddress(final SessionContext ctx, final Consignment item)
	{
		return (Address)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Consignment.CONSIGNMENTADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.consignmentAddress</code> attribute.
	 * @return the consignmentAddress
	 */
	public Address getConsignmentAddress(final Consignment item)
	{
		return getConsignmentAddress( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.consignmentAddress</code> attribute. 
	 * @param value the consignmentAddress
	 */
	public void setConsignmentAddress(final SessionContext ctx, final Consignment item, final Address value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Consignment.CONSIGNMENTADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.consignmentAddress</code> attribute. 
	 * @param value the consignmentAddress
	 */
	public void setConsignmentAddress(final Consignment item, final Address value)
	{
		setConsignmentAddress( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.contactPerson</code> attribute.
	 * @return the contactPerson
	 */
	public B2BCustomer getContactPerson(final SessionContext ctx, final AbstractOrder item)
	{
		return (B2BCustomer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.CONTACTPERSON);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.contactPerson</code> attribute.
	 * @return the contactPerson
	 */
	public B2BCustomer getContactPerson(final AbstractOrder item)
	{
		return getContactPerson( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.contactPerson</code> attribute. 
	 * @param value the contactPerson
	 */
	public void setContactPerson(final SessionContext ctx, final AbstractOrder item, final B2BCustomer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.CONTACTPERSON,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.contactPerson</code> attribute. 
	 * @param value the contactPerson
	 */
	public void setContactPerson(final AbstractOrder item, final B2BCustomer value)
	{
		setContactPerson( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.contentTags</code> attribute.
	 * @return the contentTags - List of tags the page relates to
	 */
	public Set<String> getContentTags(final SessionContext ctx, final ContentPage item)
	{
		Set<String> coll = (Set<String>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.ContentPage.CONTENTTAGS);
		return coll != null ? coll : Collections.EMPTY_SET;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.contentTags</code> attribute.
	 * @return the contentTags - List of tags the page relates to
	 */
	public Set<String> getContentTags(final ContentPage item)
	{
		return getContentTags( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.contentTags</code> attribute. 
	 * @param value the contentTags - List of tags the page relates to
	 */
	public void setContentTags(final SessionContext ctx, final ContentPage item, final Set<String> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.ContentPage.CONTENTTAGS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.contentTags</code> attribute. 
	 * @param value the contentTags - List of tags the page relates to
	 */
	public void setContentTags(final ContentPage item, final Set<String> value)
	{
		setContentTags( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.contentType</code> attribute.
	 * @return the contentType
	 */
	public EnumerationValue getContentType(final SessionContext ctx, final ContentPage item)
	{
		return (EnumerationValue)item.getProperty( ctx, SiteoneCoreConstants.Attributes.ContentPage.CONTENTTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.contentType</code> attribute.
	 * @return the contentType
	 */
	public EnumerationValue getContentType(final ContentPage item)
	{
		return getContentType( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.contentType</code> attribute. 
	 * @param value the contentType
	 */
	public void setContentType(final SessionContext ctx, final ContentPage item, final EnumerationValue value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.ContentPage.CONTENTTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.contentType</code> attribute. 
	 * @param value the contentType
	 */
	public void setContentType(final ContentPage item, final EnumerationValue value)
	{
		setContentType( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.convertedProduct</code> attribute.
	 * @return the convertedProduct
	 */
	public Product getConvertedProduct(final SessionContext ctx, final Product item)
	{
		return (Product)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.CONVERTEDPRODUCT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.convertedProduct</code> attribute.
	 * @return the convertedProduct
	 */
	public Product getConvertedProduct(final Product item)
	{
		return getConvertedProduct( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.convertedProduct</code> attribute. 
	 * @param value the convertedProduct
	 */
	public void setConvertedProduct(final SessionContext ctx, final Product item, final Product value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.CONVERTEDPRODUCT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.convertedProduct</code> attribute. 
	 * @param value the convertedProduct
	 */
	public void setConvertedProduct(final Product item, final Product value)
	{
		setConvertedProduct( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionSourceRule.couponCode</code> attribute.
	 * @return the couponCode - Attribute contains unique coupon code which needs to be send to UE
	 */
	public String getCouponCode(final SessionContext ctx, final PromotionSourceRule item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PromotionSourceRule.COUPONCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionSourceRule.couponCode</code> attribute.
	 * @return the couponCode - Attribute contains unique coupon code which needs to be send to UE
	 */
	public String getCouponCode(final PromotionSourceRule item)
	{
		return getCouponCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionSourceRule.couponCode</code> attribute. 
	 * @param value the couponCode - Attribute contains unique coupon code which needs to be send to UE
	 */
	public void setCouponCode(final SessionContext ctx, final PromotionSourceRule item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PromotionSourceRule.COUPONCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionSourceRule.couponCode</code> attribute. 
	 * @param value the couponCode - Attribute contains unique coupon code which needs to be send to UE
	 */
	public void setCouponCode(final PromotionSourceRule item, final String value)
	{
		setCouponCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.couponDescription</code> attribute.
	 * @return the couponDescription
	 */
	public String getCouponDescription(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.COUPONDESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.couponDescription</code> attribute.
	 * @return the couponDescription
	 */
	public String getCouponDescription(final AbstractOrderEntry item)
	{
		return getCouponDescription( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.couponDescription</code> attribute. 
	 * @param value the couponDescription
	 */
	public void setCouponDescription(final SessionContext ctx, final AbstractOrderEntry item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.COUPONDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.couponDescription</code> attribute. 
	 * @param value the couponDescription
	 */
	public void setCouponDescription(final AbstractOrderEntry item, final String value)
	{
		setCouponDescription( getSession().getSessionContext(), item, value );
	}
	
	public AdobeAnalyticsCustomerExportCronJob createAdobeAnalyticsCustomerExportCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.ADOBEANALYTICSCUSTOMEREXPORTCRONJOB );
			return (AdobeAnalyticsCustomerExportCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AdobeAnalyticsCustomerExportCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public AdobeAnalyticsCustomerExportCronJob createAdobeAnalyticsCustomerExportCronJob(final Map attributeValues)
	{
		return createAdobeAnalyticsCustomerExportCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public AdobeAnalyticsOrderExportCronJob createAdobeAnalyticsOrderExportCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.ADOBEANALYTICSORDEREXPORTCRONJOB );
			return (AdobeAnalyticsOrderExportCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AdobeAnalyticsOrderExportCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public AdobeAnalyticsOrderExportCronJob createAdobeAnalyticsOrderExportCronJob(final Map attributeValues)
	{
		return createAdobeAnalyticsOrderExportCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public AdobeAnalyticsRealtimeCustomerExportCronJob createAdobeAnalyticsRealtimeCustomerExportCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.ADOBEANALYTICSREALTIMECUSTOMEREXPORTCRONJOB );
			return (AdobeAnalyticsRealtimeCustomerExportCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AdobeAnalyticsRealtimeCustomerExportCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public AdobeAnalyticsRealtimeCustomerExportCronJob createAdobeAnalyticsRealtimeCustomerExportCronJob(final Map attributeValues)
	{
		return createAdobeAnalyticsRealtimeCustomerExportCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public AutoPhraseConfig createAutoPhraseConfig(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.AUTOPHRASECONFIG );
			return (AutoPhraseConfig)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AutoPhraseConfig : "+e.getMessage(), 0 );
		}
	}
	
	public AutoPhraseConfig createAutoPhraseConfig(final Map attributeValues)
	{
		return createAutoPhraseConfig( getSession().getSessionContext(), attributeValues );
	}
	
	public BagInfo createBagInfo(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.BAGINFO );
			return (BagInfo)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating BagInfo : "+e.getMessage(), 0 );
		}
	}
	
	public BagInfo createBagInfo(final Map attributeValues)
	{
		return createBagInfo( getSession().getSessionContext(), attributeValues );
	}
	
	public BatchNotificationProcess createBatchNotificationProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.BATCHNOTIFICATIONPROCESS );
			return (BatchNotificationProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating BatchNotificationProcess : "+e.getMessage(), 0 );
		}
	}
	
	public BatchNotificationProcess createBatchNotificationProcess(final Map attributeValues)
	{
		return createBatchNotificationProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public BottomConsentCMSComponent createBottomConsentCMSComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.BOTTOMCONSENTCMSCOMPONENT );
			return (BottomConsentCMSComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating BottomConsentCMSComponent : "+e.getMessage(), 0 );
		}
	}
	
	public BottomConsentCMSComponent createBottomConsentCMSComponent(final Map attributeValues)
	{
		return createBottomConsentCMSComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public BulkListUploadCronJob createBulkListUploadCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.BULKLISTUPLOADCRONJOB );
			return (BulkListUploadCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating BulkListUploadCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public BulkListUploadCronJob createBulkListUploadCronJob(final Map attributeValues)
	{
		return createBulkListUploadCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public CartAbandonmentCronJob createCartAbandonmentCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CARTABANDONMENTCRONJOB );
			return (CartAbandonmentCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CartAbandonmentCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public CartAbandonmentCronJob createCartAbandonmentCronJob(final Map attributeValues)
	{
		return createCartAbandonmentCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public CartAbandonmentEmailProcess createCartAbandonmentEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CARTABANDONMENTEMAILPROCESS );
			return (CartAbandonmentEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CartAbandonmentEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public CartAbandonmentEmailProcess createCartAbandonmentEmailProcess(final Map attributeValues)
	{
		return createCartAbandonmentEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public CategoryComponent createCategoryComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CATEGORYCOMPONENT );
			return (CategoryComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CategoryComponent : "+e.getMessage(), 0 );
		}
	}
	
	public CategoryComponent createCategoryComponent(final Map attributeValues)
	{
		return createCategoryComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public CategoryFeedCronJob createCategoryFeedCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CATEGORYFEEDCRONJOB );
			return (CategoryFeedCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CategoryFeedCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public CategoryFeedCronJob createCategoryFeedCronJob(final Map attributeValues)
	{
		return createCategoryFeedCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public CategoryLandingPageRotatingBannerComponent createCategoryLandingPageRotatingBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CATEGORYLANDINGPAGEROTATINGBANNERCOMPONENT );
			return (CategoryLandingPageRotatingBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CategoryLandingPageRotatingBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public CategoryLandingPageRotatingBannerComponent createCategoryLandingPageRotatingBannerComponent(final Map attributeValues)
	{
		return createCategoryLandingPageRotatingBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public CategoryProductCountCronJob createCategoryProductCountCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CATEGORYPRODUCTCOUNTCRONJOB );
			return (CategoryProductCountCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CategoryProductCountCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public CategoryProductCountCronJob createCategoryProductCountCronJob(final Map attributeValues)
	{
		return createCategoryProductCountCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public CCPACustomerProcess createCCPACustomerProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CCPACUSTOMERPROCESS );
			return (CCPACustomerProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CCPACustomerProcess : "+e.getMessage(), 0 );
		}
	}
	
	public CCPACustomerProcess createCCPACustomerProcess(final Map attributeValues)
	{
		return createCCPACustomerProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public CCPAProcess createCCPAProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CCPAPROCESS );
			return (CCPAProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CCPAProcess : "+e.getMessage(), 0 );
		}
	}
	
	public CCPAProcess createCCPAProcess(final Map attributeValues)
	{
		return createCCPAProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public ContactSellerProcess createContactSellerProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CONTACTSELLERPROCESS );
			return (ContactSellerProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ContactSellerProcess : "+e.getMessage(), 0 );
		}
	}
	
	public ContactSellerProcess createContactSellerProcess(final Map attributeValues)
	{
		return createContactSellerProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public ContactUsCustomerProcess createContactUsCustomerProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CONTACTUSCUSTOMERPROCESS );
			return (ContactUsCustomerProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ContactUsCustomerProcess : "+e.getMessage(), 0 );
		}
	}
	
	public ContactUsCustomerProcess createContactUsCustomerProcess(final Map attributeValues)
	{
		return createContactUsCustomerProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public ContactUsProcess createContactUsProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CONTACTUSPROCESS );
			return (ContactUsProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ContactUsProcess : "+e.getMessage(), 0 );
		}
	}
	
	public ContactUsProcess createContactUsProcess(final Map attributeValues)
	{
		return createContactUsProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public ConvertedProductMappingCronJob createConvertedProductMappingCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CONVERTEDPRODUCTMAPPINGCRONJOB );
			return (ConvertedProductMappingCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ConvertedProductMappingCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public ConvertedProductMappingCronJob createConvertedProductMappingCronJob(final Map attributeValues)
	{
		return createConvertedProductMappingCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public CreatePasswordEmailProcess createCreatePasswordEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CREATEPASSWORDEMAILPROCESS );
			return (CreatePasswordEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CreatePasswordEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public CreatePasswordEmailProcess createCreatePasswordEmailProcess(final Map attributeValues)
	{
		return createCreatePasswordEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public CuratedPLPVerticaNavNodeComponent createCuratedPLPVerticaNavNodeComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CURATEDPLPVERTICANAVNODECOMPONENT );
			return (CuratedPLPVerticaNavNodeComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CuratedPLPVerticaNavNodeComponent : "+e.getMessage(), 0 );
		}
	}
	
	public CuratedPLPVerticaNavNodeComponent createCuratedPLPVerticaNavNodeComponent(final Map attributeValues)
	{
		return createCuratedPLPVerticaNavNodeComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public CustomerDataReportCronJob createCustomerDataReportCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CUSTOMERDATAREPORTCRONJOB );
			return (CustomerDataReportCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CustomerDataReportCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public CustomerDataReportCronJob createCustomerDataReportCronJob(final Map attributeValues)
	{
		return createCustomerDataReportCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public CustomerUpdateCronJob createCustomerUpdateCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CUSTOMERUPDATECRONJOB );
			return (CustomerUpdateCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CustomerUpdateCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public CustomerUpdateCronJob createCustomerUpdateCronJob(final Map attributeValues)
	{
		return createCustomerUpdateCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public CustomerUpdateNewEmailProcess createCustomerUpdateNewEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CUSTOMERUPDATENEWEMAILPROCESS );
			return (CustomerUpdateNewEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CustomerUpdateNewEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public CustomerUpdateNewEmailProcess createCustomerUpdateNewEmailProcess(final Map attributeValues)
	{
		return createCustomerUpdateNewEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public CustomerUpdateOldEmailProcess createCustomerUpdateOldEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CUSTOMERUPDATEOLDEMAILPROCESS );
			return (CustomerUpdateOldEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CustomerUpdateOldEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public CustomerUpdateOldEmailProcess createCustomerUpdateOldEmailProcess(final Map attributeValues)
	{
		return createCustomerUpdateOldEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public CustomPushNotification createCustomPushNotification(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CUSTOMPUSHNOTIFICATION );
			return (CustomPushNotification)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CustomPushNotification : "+e.getMessage(), 0 );
		}
	}
	
	public CustomPushNotification createCustomPushNotification(final Map attributeValues)
	{
		return createCustomPushNotification( getSession().getSessionContext(), attributeValues );
	}
	
	public CustomPushNotificationCronJob createCustomPushNotificationCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.CUSTOMPUSHNOTIFICATIONCRONJOB );
			return (CustomPushNotificationCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CustomPushNotificationCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public CustomPushNotificationCronJob createCustomPushNotificationCronJob(final Map attributeValues)
	{
		return createCustomPushNotificationCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.createdBy</code> attribute.
	 * @return the createdBy - Attribute to creater of the list
	 */
	public String getCreatedBy(final SessionContext ctx, final Wishlist2 item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Wishlist2.CREATEDBY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.createdBy</code> attribute.
	 * @return the createdBy - Attribute to creater of the list
	 */
	public String getCreatedBy(final Wishlist2 item)
	{
		return getCreatedBy( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.createdBy</code> attribute. 
	 * @param value the createdBy - Attribute to creater of the list
	 */
	public void setCreatedBy(final SessionContext ctx, final Wishlist2 item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Wishlist2.CREATEDBY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.createdBy</code> attribute. 
	 * @param value the createdBy - Attribute to creater of the list
	 */
	public void setCreatedBy(final Wishlist2 item, final String value)
	{
		setCreatedBy( getSession().getSessionContext(), item, value );
	}
	
	public DeclinedCardAttemptEmailProcess createDeclinedCardAttemptEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.DECLINEDCARDATTEMPTEMAILPROCESS );
			return (DeclinedCardAttemptEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating DeclinedCardAttemptEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public DeclinedCardAttemptEmailProcess createDeclinedCardAttemptEmailProcess(final Map attributeValues)
	{
		return createDeclinedCardAttemptEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public EmailSubscriptions createEmailSubscriptions(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.EMAILSUBSCRIPTIONS );
			return (EmailSubscriptions)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating EmailSubscriptions : "+e.getMessage(), 0 );
		}
	}
	
	public EmailSubscriptions createEmailSubscriptions(final Map attributeValues)
	{
		return createEmailSubscriptions( getSession().getSessionContext(), attributeValues );
	}
	
	public EventCarouselComponent createEventCarouselComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.EVENTCAROUSELCOMPONENT );
			return (EventCarouselComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating EventCarouselComponent : "+e.getMessage(), 0 );
		}
	}
	
	public EventCarouselComponent createEventCarouselComponent(final Map attributeValues)
	{
		return createEventCarouselComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public EventResultsGridComponent createEventResultsGridComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.EVENTRESULTSGRIDCOMPONENT );
			return (EventResultsGridComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating EventResultsGridComponent : "+e.getMessage(), 0 );
		}
	}
	
	public EventResultsGridComponent createEventResultsGridComponent(final Map attributeValues)
	{
		return createEventResultsGridComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public EWalletNotificationEmailProcess createEWalletNotificationEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.EWALLETNOTIFICATIONEMAILPROCESS );
			return (EWalletNotificationEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating EWalletNotificationEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public EWalletNotificationEmailProcess createEWalletNotificationEmailProcess(final Map attributeValues)
	{
		return createEWalletNotificationEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public ExpiredQuoteUpdProcess createExpiredQuoteUpdProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.EXPIREDQUOTEUPDPROCESS );
			return (ExpiredQuoteUpdProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ExpiredQuoteUpdProcess : "+e.getMessage(), 0 );
		}
	}
	
	public ExpiredQuoteUpdProcess createExpiredQuoteUpdProcess(final Map attributeValues)
	{
		return createExpiredQuoteUpdProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public FeedFileMonitorNotificationProcess createFeedFileMonitorNotificationProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.FEEDFILEMONITORNOTIFICATIONPROCESS );
			return (FeedFileMonitorNotificationProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating FeedFileMonitorNotificationProcess : "+e.getMessage(), 0 );
		}
	}
	
	public FeedFileMonitorNotificationProcess createFeedFileMonitorNotificationProcess(final Map attributeValues)
	{
		return createFeedFileMonitorNotificationProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public FindDuplicateSequenceCronJob createFindDuplicateSequenceCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.FINDDUPLICATESEQUENCECRONJOB );
			return (FindDuplicateSequenceCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating FindDuplicateSequenceCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public FindDuplicateSequenceCronJob createFindDuplicateSequenceCronJob(final Map attributeValues)
	{
		return createFindDuplicateSequenceCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public FirstTimeUserCronJob createFirstTimeUserCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.FIRSTTIMEUSERCRONJOB );
			return (FirstTimeUserCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating FirstTimeUserCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public FirstTimeUserCronJob createFirstTimeUserCronJob(final Map attributeValues)
	{
		return createFirstTimeUserCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public FullProductFeedCronJob createFullProductFeedCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.FULLPRODUCTFEEDCRONJOB );
			return (FullProductFeedCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating FullProductFeedCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public FullProductFeedCronJob createFullProductFeedCronJob(final Map attributeValues)
	{
		return createFullProductFeedCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public GlobalMessageCMSComponent createGlobalMessageCMSComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.GLOBALMESSAGECMSCOMPONENT );
			return (GlobalMessageCMSComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating GlobalMessageCMSComponent : "+e.getMessage(), 0 );
		}
	}
	
	public GlobalMessageCMSComponent createGlobalMessageCMSComponent(final Map attributeValues)
	{
		return createGlobalMessageCMSComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public GlobalProductNavigationNode createGlobalProductNavigationNode(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.GLOBALPRODUCTNAVIGATIONNODE );
			return (GlobalProductNavigationNode)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating GlobalProductNavigationNode : "+e.getMessage(), 0 );
		}
	}
	
	public GlobalProductNavigationNode createGlobalProductNavigationNode(final Map attributeValues)
	{
		return createGlobalProductNavigationNode( getSession().getSessionContext(), attributeValues );
	}
	
	public HomeOwnerComponent createHomeOwnerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.HOMEOWNERCOMPONENT );
			return (HomeOwnerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating HomeOwnerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public HomeOwnerComponent createHomeOwnerComponent(final Map attributeValues)
	{
		return createHomeOwnerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public HomeOwnerProcess createHomeOwnerProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.HOMEOWNERPROCESS );
			return (HomeOwnerProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating HomeOwnerProcess : "+e.getMessage(), 0 );
		}
	}
	
	public HomeOwnerProcess createHomeOwnerProcess(final Map attributeValues)
	{
		return createHomeOwnerProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public HomePageCustomerEventsComponent createHomePageCustomerEventsComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.HOMEPAGECUSTOMEREVENTSCOMPONENT );
			return (HomePageCustomerEventsComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating HomePageCustomerEventsComponent : "+e.getMessage(), 0 );
		}
	}
	
	public HomePageCustomerEventsComponent createHomePageCustomerEventsComponent(final Map attributeValues)
	{
		return createHomePageCustomerEventsComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public HomePagePromoBannerComponent createHomePagePromoBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.HOMEPAGEPROMOBANNERCOMPONENT );
			return (HomePagePromoBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating HomePagePromoBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public HomePagePromoBannerComponent createHomePagePromoBannerComponent(final Map attributeValues)
	{
		return createHomePagePromoBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public HomePageResponsiveBannerComponent createHomePageResponsiveBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.HOMEPAGERESPONSIVEBANNERCOMPONENT );
			return (HomePageResponsiveBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating HomePageResponsiveBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public HomePageResponsiveBannerComponent createHomePageResponsiveBannerComponent(final Map attributeValues)
	{
		return createHomePageResponsiveBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public HomePageRotatingBannerComponent createHomePageRotatingBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.HOMEPAGEROTATINGBANNERCOMPONENT );
			return (HomePageRotatingBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating HomePageRotatingBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public HomePageRotatingBannerComponent createHomePageRotatingBannerComponent(final Map attributeValues)
	{
		return createHomePageRotatingBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public HomePageToolsComponent createHomePageToolsComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.HOMEPAGETOOLSCOMPONENT );
			return (HomePageToolsComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating HomePageToolsComponent : "+e.getMessage(), 0 );
		}
	}
	
	public HomePageToolsComponent createHomePageToolsComponent(final Map attributeValues)
	{
		return createHomePageToolsComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public ImportOnHandProductStoresCronJob createImportOnHandProductStoresCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.IMPORTONHANDPRODUCTSTORESCRONJOB );
			return (ImportOnHandProductStoresCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ImportOnHandProductStoresCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public ImportOnHandProductStoresCronJob createImportOnHandProductStoresCronJob(final Map attributeValues)
	{
		return createImportOnHandProductStoresCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public ImportProductStoresCronJob createImportProductStoresCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.IMPORTPRODUCTSTORESCRONJOB );
			return (ImportProductStoresCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ImportProductStoresCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public ImportProductStoresCronJob createImportProductStoresCronJob(final Map attributeValues)
	{
		return createImportProductStoresCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public ImportQRCodeProductsCronJob createImportQRCodeProductsCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.IMPORTQRCODEPRODUCTSCRONJOB );
			return (ImportQRCodeProductsCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ImportQRCodeProductsCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public ImportQRCodeProductsCronJob createImportQRCodeProductsCronJob(final Map attributeValues)
	{
		return createImportQRCodeProductsCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public Inspiration createInspiration(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.INSPIRATION );
			return (Inspiration)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Inspiration : "+e.getMessage(), 0 );
		}
	}
	
	public Inspiration createInspiration(final Map attributeValues)
	{
		return createInspiration( getSession().getSessionContext(), attributeValues );
	}
	
	public InventoryFeedCronJob createInventoryFeedCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.INVENTORYFEEDCRONJOB );
			return (InventoryFeedCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating InventoryFeedCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public InventoryFeedCronJob createInventoryFeedCronJob(final Map attributeValues)
	{
		return createInventoryFeedCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public InventoryUOM createInventoryUOM(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.INVENTORYUOM );
			return (InventoryUOM)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating InventoryUOM : "+e.getMessage(), 0 );
		}
	}
	
	public InventoryUOM createInventoryUOM(final Map attributeValues)
	{
		return createInventoryUOM( getSession().getSessionContext(), attributeValues );
	}
	
	public InventoryUPC createInventoryUPC(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.INVENTORYUPC );
			return (InventoryUPC)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating InventoryUPC : "+e.getMessage(), 0 );
		}
	}
	
	public InventoryUPC createInventoryUPC(final Map attributeValues)
	{
		return createInventoryUPC( getSession().getSessionContext(), attributeValues );
	}
	
	public InvoiceDetailsProcess createInvoiceDetailsProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.INVOICEDETAILSPROCESS );
			return (InvoiceDetailsProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating InvoiceDetailsProcess : "+e.getMessage(), 0 );
		}
	}
	
	public InvoiceDetailsProcess createInvoiceDetailsProcess(final Map attributeValues)
	{
		return createInvoiceDetailsProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public KountDeclineProcess createKountDeclineProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.KOUNTDECLINEPROCESS );
			return (KountDeclineProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating KountDeclineProcess : "+e.getMessage(), 0 );
		}
	}
	
	public KountDeclineProcess createKountDeclineProcess(final Map attributeValues)
	{
		return createKountDeclineProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public LinkToPayAuditLog createLinkToPayAuditLog(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.LINKTOPAYAUDITLOG );
			return (LinkToPayAuditLog)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating LinkToPayAuditLog : "+e.getMessage(), 0 );
		}
	}
	
	public LinkToPayAuditLog createLinkToPayAuditLog(final Map attributeValues)
	{
		return createLinkToPayAuditLog( getSession().getSessionContext(), attributeValues );
	}
	
	public LinkToPayCayanResponse createLinkToPayCayanResponse(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.LINKTOPAYCAYANRESPONSE );
			return (LinkToPayCayanResponse)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating LinkToPayCayanResponse : "+e.getMessage(), 0 );
		}
	}
	
	public LinkToPayCayanResponse createLinkToPayCayanResponse(final Map attributeValues)
	{
		return createLinkToPayCayanResponse( getSession().getSessionContext(), attributeValues );
	}
	
	public LinkToPayEmailPaymentProcess createLinkToPayEmailPaymentProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.LINKTOPAYEMAILPAYMENTPROCESS );
			return (LinkToPayEmailPaymentProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating LinkToPayEmailPaymentProcess : "+e.getMessage(), 0 );
		}
	}
	
	public LinkToPayEmailPaymentProcess createLinkToPayEmailPaymentProcess(final Map attributeValues)
	{
		return createLinkToPayEmailPaymentProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public LinkToPayPayment createLinkToPayPayment(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.LINKTOPAYPAYMENT );
			return (LinkToPayPayment)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating LinkToPayPayment : "+e.getMessage(), 0 );
		}
	}
	
	public LinkToPayPayment createLinkToPayPayment(final Map attributeValues)
	{
		return createLinkToPayPayment( getSession().getSessionContext(), attributeValues );
	}
	
	public LinkToPayPaymentProcess createLinkToPayPaymentProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.LINKTOPAYPAYMENTPROCESS );
			return (LinkToPayPaymentProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating LinkToPayPaymentProcess : "+e.getMessage(), 0 );
		}
	}
	
	public LinkToPayPaymentProcess createLinkToPayPaymentProcess(final Map attributeValues)
	{
		return createLinkToPayPaymentProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public LinkToPayProcess createLinkToPayProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.LINKTOPAYPROCESS );
			return (LinkToPayProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating LinkToPayProcess : "+e.getMessage(), 0 );
		}
	}
	
	public LinkToPayProcess createLinkToPayProcess(final Map attributeValues)
	{
		return createLinkToPayProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public ListEditEmailProcess createListEditEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.LISTEDITEMAILPROCESS );
			return (ListEditEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ListEditEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public ListEditEmailProcess createListEditEmailProcess(final Map attributeValues)
	{
		return createListEditEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public LocalizedProductFeedCronJob createLocalizedProductFeedCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.LOCALIZEDPRODUCTFEEDCRONJOB );
			return (LocalizedProductFeedCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating LocalizedProductFeedCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public LocalizedProductFeedCronJob createLocalizedProductFeedCronJob(final Map attributeValues)
	{
		return createLocalizedProductFeedCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public NewsComponent createNewsComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.NEWSCOMPONENT );
			return (NewsComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating NewsComponent : "+e.getMessage(), 0 );
		}
	}
	
	public NewsComponent createNewsComponent(final Map attributeValues)
	{
		return createNewsComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public NewsDetailComponent createNewsDetailComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.NEWSDETAILCOMPONENT );
			return (NewsDetailComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating NewsDetailComponent : "+e.getMessage(), 0 );
		}
	}
	
	public NewsDetailComponent createNewsDetailComponent(final Map attributeValues)
	{
		return createNewsDetailComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public NotifyQuoteStatusProcess createNotifyQuoteStatusProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.NOTIFYQUOTESTATUSPROCESS );
			return (NotifyQuoteStatusProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating NotifyQuoteStatusProcess : "+e.getMessage(), 0 );
		}
	}
	
	public NotifyQuoteStatusProcess createNotifyQuoteStatusProcess(final Map attributeValues)
	{
		return createNotifyQuoteStatusProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public NoVisibleUomCronJob createNoVisibleUomCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.NOVISIBLEUOMCRONJOB );
			return (NoVisibleUomCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating NoVisibleUomCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public NoVisibleUomCronJob createNoVisibleUomCronJob(final Map attributeValues)
	{
		return createNoVisibleUomCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public NoVisibleUomProductProcess createNoVisibleUomProductProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.NOVISIBLEUOMPRODUCTPROCESS );
			return (NoVisibleUomProductProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating NoVisibleUomProductProcess : "+e.getMessage(), 0 );
		}
	}
	
	public NoVisibleUomProductProcess createNoVisibleUomProductProcess(final Map attributeValues)
	{
		return createNoVisibleUomProductProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public NurseryInventoryFeedCronJob createNurseryInventoryFeedCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.NURSERYINVENTORYFEEDCRONJOB );
			return (NurseryInventoryFeedCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating NurseryInventoryFeedCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public NurseryInventoryFeedCronJob createNurseryInventoryFeedCronJob(final Map attributeValues)
	{
		return createNurseryInventoryFeedCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public OldInvoiceAddressRemovalCronjob createOldInvoiceAddressRemovalCronjob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.OLDINVOICEADDRESSREMOVALCRONJOB );
			return (OldInvoiceAddressRemovalCronjob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating OldInvoiceAddressRemovalCronjob : "+e.getMessage(), 0 );
		}
	}
	
	public OldInvoiceAddressRemovalCronjob createOldInvoiceAddressRemovalCronjob(final Map attributeValues)
	{
		return createOldInvoiceAddressRemovalCronjob( getSession().getSessionContext(), attributeValues );
	}
	
	public OrderDetailEmailProcess createOrderDetailEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.ORDERDETAILEMAILPROCESS );
			return (OrderDetailEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating OrderDetailEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public OrderDetailEmailProcess createOrderDetailEmailProcess(final Map attributeValues)
	{
		return createOrderDetailEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public OrderOnlineComponent createOrderOnlineComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.ORDERONLINECOMPONENT );
			return (OrderOnlineComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating OrderOnlineComponent : "+e.getMessage(), 0 );
		}
	}
	
	public OrderOnlineComponent createOrderOnlineComponent(final Map attributeValues)
	{
		return createOrderOnlineComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public OrderReadyForPickUpRemainderEmailCronJob createOrderReadyForPickUpRemainderEmailCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.ORDERREADYFORPICKUPREMAINDEREMAILCRONJOB );
			return (OrderReadyForPickUpRemainderEmailCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating OrderReadyForPickUpRemainderEmailCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public OrderReadyForPickUpRemainderEmailCronJob createOrderReadyForPickUpRemainderEmailCronJob(final Map attributeValues)
	{
		return createOrderReadyForPickUpRemainderEmailCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public OrderReadyToPickUpEmailProcess createOrderReadyToPickUpEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.ORDERREADYTOPICKUPEMAILPROCESS );
			return (OrderReadyToPickUpEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating OrderReadyToPickUpEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public OrderReadyToPickUpEmailProcess createOrderReadyToPickUpEmailProcess(final Map attributeValues)
	{
		return createOrderReadyToPickUpEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public OrderScheduledForDeliveryEmailProcess createOrderScheduledForDeliveryEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.ORDERSCHEDULEDFORDELIVERYEMAILPROCESS );
			return (OrderScheduledForDeliveryEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating OrderScheduledForDeliveryEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public OrderScheduledForDeliveryEmailProcess createOrderScheduledForDeliveryEmailProcess(final Map attributeValues)
	{
		return createOrderScheduledForDeliveryEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public OrderStatusEmailCronJob createOrderStatusEmailCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.ORDERSTATUSEMAILCRONJOB );
			return (OrderStatusEmailCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating OrderStatusEmailCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public OrderStatusEmailCronJob createOrderStatusEmailCronJob(final Map attributeValues)
	{
		return createOrderStatusEmailCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public OrderStatusNotificationCronJob createOrderStatusNotificationCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.ORDERSTATUSNOTIFICATIONCRONJOB );
			return (OrderStatusNotificationCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating OrderStatusNotificationCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public OrderStatusNotificationCronJob createOrderStatusNotificationCronJob(final Map attributeValues)
	{
		return createOrderStatusNotificationCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public OrderTrackingLinkEmailProcess createOrderTrackingLinkEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.ORDERTRACKINGLINKEMAILPROCESS );
			return (OrderTrackingLinkEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating OrderTrackingLinkEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public OrderTrackingLinkEmailProcess createOrderTrackingLinkEmailProcess(final Map attributeValues)
	{
		return createOrderTrackingLinkEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public OrphanMediaCronJob createOrphanMediaCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.ORPHANMEDIACRONJOB );
			return (OrphanMediaCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating OrphanMediaCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public OrphanMediaCronJob createOrphanMediaCronJob(final Map attributeValues)
	{
		return createOrphanMediaCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public PasswordChangedEmailProcess createPasswordChangedEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PASSWORDCHANGEDEMAILPROCESS );
			return (PasswordChangedEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PasswordChangedEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public PasswordChangedEmailProcess createPasswordChangedEmailProcess(final Map attributeValues)
	{
		return createPasswordChangedEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public PimBatchFailureEmailProcess createPimBatchFailureEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PIMBATCHFAILUREEMAILPROCESS );
			return (PimBatchFailureEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PimBatchFailureEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public PimBatchFailureEmailProcess createPimBatchFailureEmailProcess(final Map attributeValues)
	{
		return createPimBatchFailureEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public PIMBatchFailureReportCronJob createPIMBatchFailureReportCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PIMBATCHFAILUREREPORTCRONJOB );
			return (PIMBatchFailureReportCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PIMBatchFailureReportCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public PIMBatchFailureReportCronJob createPIMBatchFailureReportCronJob(final Map attributeValues)
	{
		return createPIMBatchFailureReportCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public PIMBatchFailureReportNotificationProcess createPIMBatchFailureReportNotificationProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PIMBATCHFAILUREREPORTNOTIFICATIONPROCESS );
			return (PIMBatchFailureReportNotificationProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PIMBatchFailureReportNotificationProcess : "+e.getMessage(), 0 );
		}
	}
	
	public PIMBatchFailureReportNotificationProcess createPIMBatchFailureReportNotificationProcess(final Map attributeValues)
	{
		return createPIMBatchFailureReportNotificationProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public PimPayloadsDeleteCronjob createPimPayloadsDeleteCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PIMPAYLOADSDELETECRONJOB );
			return (PimPayloadsDeleteCronjob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PimPayloadsDeleteCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public PimPayloadsDeleteCronjob createPimPayloadsDeleteCronJob(final Map attributeValues)
	{
		return createPimPayloadsDeleteCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public PIMReportMessage createPIMReportMessage(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PIMREPORTMESSAGE );
			return (PIMReportMessage)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PIMReportMessage : "+e.getMessage(), 0 );
		}
	}
	
	public PIMReportMessage createPIMReportMessage(final Map attributeValues)
	{
		return createPIMReportMessage( getSession().getSessionContext(), attributeValues );
	}
	
	public PointsForEquipmentProcess createPointsForEquipmentProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.POINTSFOREQUIPMENTPROCESS );
			return (PointsForEquipmentProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PointsForEquipmentProcess : "+e.getMessage(), 0 );
		}
	}
	
	public PointsForEquipmentProcess createPointsForEquipmentProcess(final Map attributeValues)
	{
		return createPointsForEquipmentProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public ProductFeedCronJob createProductFeedCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PRODUCTFEEDCRONJOB );
			return (ProductFeedCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ProductFeedCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public ProductFeedCronJob createProductFeedCronJob(final Map attributeValues)
	{
		return createProductFeedCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public ProductPromotionsCronJob createProductPromotionsCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PRODUCTPROMOTIONSCRONJOB );
			return (ProductPromotionsCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ProductPromotionsCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public ProductPromotionsCronJob createProductPromotionsCronJob(final Map attributeValues)
	{
		return createProductPromotionsCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public ProductRegionFeedCronJob createProductRegionFeedCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PRODUCTREGIONFEEDCRONJOB );
			return (ProductRegionFeedCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ProductRegionFeedCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public ProductRegionFeedCronJob createProductRegionFeedCronJob(final Map attributeValues)
	{
		return createProductRegionFeedCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public ProductSalesInfo createProductSalesInfo(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PRODUCTSALESINFO );
			return (ProductSalesInfo)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ProductSalesInfo : "+e.getMessage(), 0 );
		}
	}
	
	public ProductSalesInfo createProductSalesInfo(final Map attributeValues)
	{
		return createProductSalesInfo( getSession().getSessionContext(), attributeValues );
	}
	
	public PromotionBannerComponent createPromotionBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PROMOTIONBANNERCOMPONENT );
			return (PromotionBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PromotionBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public PromotionBannerComponent createPromotionBannerComponent(final Map attributeValues)
	{
		return createPromotionBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public PromotionFeedCronJob createPromotionFeedCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PROMOTIONFEEDCRONJOB );
			return (PromotionFeedCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PromotionFeedCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public PromotionFeedCronJob createPromotionFeedCronJob(final Map attributeValues)
	{
		return createPromotionFeedCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public PromotionImageComponent createPromotionImageComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PROMOTIONIMAGECOMPONENT );
			return (PromotionImageComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PromotionImageComponent : "+e.getMessage(), 0 );
		}
	}
	
	public PromotionImageComponent createPromotionImageComponent(final Map attributeValues)
	{
		return createPromotionImageComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public PromotionProductCategory createPromotionProductCategory(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PROMOTIONPRODUCTCATEGORY );
			return (PromotionProductCategory)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PromotionProductCategory : "+e.getMessage(), 0 );
		}
	}
	
	public PromotionProductCategory createPromotionProductCategory(final Map attributeValues)
	{
		return createPromotionProductCategory( getSession().getSessionContext(), attributeValues );
	}
	
	public ProprietaryBrandConfig createProprietaryBrandConfig(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PROPRIETARYBRANDCONFIG );
			return (ProprietaryBrandConfig)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ProprietaryBrandConfig : "+e.getMessage(), 0 );
		}
	}
	
	public ProprietaryBrandConfig createProprietaryBrandConfig(final Map attributeValues)
	{
		return createProprietaryBrandConfig( getSession().getSessionContext(), attributeValues );
	}
	
	public PunchOutAuditLog createPunchOutAuditLog(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PUNCHOUTAUDITLOG );
			return (PunchOutAuditLog)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PunchOutAuditLog : "+e.getMessage(), 0 );
		}
	}
	
	public PunchOutAuditLog createPunchOutAuditLog(final Map attributeValues)
	{
		return createPunchOutAuditLog( getSession().getSessionContext(), attributeValues );
	}
	
	public PurchasedProduct createPurchasedProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PURCHASEDPRODUCT );
			return (PurchasedProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PurchasedProduct : "+e.getMessage(), 0 );
		}
	}
	
	public PurchasedProduct createPurchasedProduct(final Map attributeValues)
	{
		return createPurchasedProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public PurchProductAndOrders createPurchProductAndOrders(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PURCHPRODUCTANDORDERS );
			return (PurchProductAndOrders)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PurchProductAndOrders : "+e.getMessage(), 0 );
		}
	}
	
	public PurchProductAndOrders createPurchProductAndOrders(final Map attributeValues)
	{
		return createPurchProductAndOrders( getSession().getSessionContext(), attributeValues );
	}
	
	public PurgeRegulatoryStatesCronJob createPurgeRegulatoryStatesCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.PURGEREGULATORYSTATESCRONJOB );
			return (PurgeRegulatoryStatesCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PurgeRegulatoryStatesCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public PurgeRegulatoryStatesCronJob createPurgeRegulatoryStatesCronJob(final Map attributeValues)
	{
		return createPurgeRegulatoryStatesCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public QuoteApprovalProcess createQuoteApprovalProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.QUOTEAPPROVALPROCESS );
			return (QuoteApprovalProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating QuoteApprovalProcess : "+e.getMessage(), 0 );
		}
	}
	
	public QuoteApprovalProcess createQuoteApprovalProcess(final Map attributeValues)
	{
		return createQuoteApprovalProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public QuoteItemDetails createQuoteItemDetails(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.QUOTEITEMDETAILS );
			return (QuoteItemDetails)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating QuoteItemDetails : "+e.getMessage(), 0 );
		}
	}
	
	public QuoteItemDetails createQuoteItemDetails(final Map attributeValues)
	{
		return createQuoteItemDetails( getSession().getSessionContext(), attributeValues );
	}
	
	public QuoteToOrderStatusEmailCronJob createQuoteToOrderStatusEmailCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.QUOTETOORDERSTATUSEMAILCRONJOB );
			return (QuoteToOrderStatusEmailCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating QuoteToOrderStatusEmailCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public QuoteToOrderStatusEmailCronJob createQuoteToOrderStatusEmailCronJob(final Map attributeValues)
	{
		return createQuoteToOrderStatusEmailCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public QuoteToOrderStatusProcess createQuoteToOrderStatusProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.QUOTETOORDERSTATUSPROCESS );
			return (QuoteToOrderStatusProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating QuoteToOrderStatusProcess : "+e.getMessage(), 0 );
		}
	}
	
	public QuoteToOrderStatusProcess createQuoteToOrderStatusProcess(final Map attributeValues)
	{
		return createQuoteToOrderStatusProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public RecentScanProducts createRecentScanProducts(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.RECENTSCANPRODUCTS );
			return (RecentScanProducts)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating RecentScanProducts : "+e.getMessage(), 0 );
		}
	}
	
	public RecentScanProducts createRecentScanProducts(final Map attributeValues)
	{
		return createRecentScanProducts( getSession().getSessionContext(), attributeValues );
	}
	
	public RegionFeedCronJob createRegionFeedCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.REGIONFEEDCRONJOB );
			return (RegionFeedCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating RegionFeedCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public RegionFeedCronJob createRegionFeedCronJob(final Map attributeValues)
	{
		return createRegionFeedCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public RegulatoryStates createRegulatoryStates(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.REGULATORYSTATES );
			return (RegulatoryStates)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating RegulatoryStates : "+e.getMessage(), 0 );
		}
	}
	
	public RegulatoryStates createRegulatoryStates(final Map attributeValues)
	{
		return createRegulatoryStates( getSession().getSessionContext(), attributeValues );
	}
	
	public RegulatoryStatesCronJob createRegulatoryStatesCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.REGULATORYSTATESCRONJOB );
			return (RegulatoryStatesCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating RegulatoryStatesCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public RegulatoryStatesCronJob createRegulatoryStatesCronJob(final Map attributeValues)
	{
		return createRegulatoryStatesCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public RequestAccountProcess createRequestAccountProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.REQUESTACCOUNTPROCESS );
			return (RequestAccountProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating RequestAccountProcess : "+e.getMessage(), 0 );
		}
	}
	
	public RequestAccountProcess createRequestAccountProcess(final Map attributeValues)
	{
		return createRequestAccountProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public RequestQuoteProcess createRequestQuoteProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.REQUESTQUOTEPROCESS );
			return (RequestQuoteProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating RequestQuoteProcess : "+e.getMessage(), 0 );
		}
	}
	
	public RequestQuoteProcess createRequestQuoteProcess(final Map attributeValues)
	{
		return createRequestQuoteProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public SalesFeedCronJob createSalesFeedCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SALESFEEDCRONJOB );
			return (SalesFeedCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SalesFeedCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public SalesFeedCronJob createSalesFeedCronJob(final Map attributeValues)
	{
		return createSalesFeedCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public ShareAssemblyProcess createShareAssemblyProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SHAREASSEMBLYPROCESS );
			return (ShareAssemblyProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ShareAssemblyProcess : "+e.getMessage(), 0 );
		}
	}
	
	public ShareAssemblyProcess createShareAssemblyProcess(final Map attributeValues)
	{
		return createShareAssemblyProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public ShareCartEmailProcess createShareCartEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SHARECARTEMAILPROCESS );
			return (ShareCartEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ShareCartEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public ShareCartEmailProcess createShareCartEmailProcess(final Map attributeValues)
	{
		return createShareCartEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public SharedProductProcess createSharedProductProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SHAREDPRODUCTPROCESS );
			return (SharedProductProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SharedProductProcess : "+e.getMessage(), 0 );
		}
	}
	
	public SharedProductProcess createSharedProductProcess(final Map attributeValues)
	{
		return createSharedProductProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public ShareListProcess createShareListProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SHARELISTPROCESS );
			return (ShareListProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ShareListProcess : "+e.getMessage(), 0 );
		}
	}
	
	public ShareListProcess createShareListProcess(final Map attributeValues)
	{
		return createShareListProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneAnonymousCartRemovalCronJob createSiteoneAnonymousCartRemovalCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEANONYMOUSCARTREMOVALCRONJOB );
			return (SiteoneAnonymousCartRemovalCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneAnonymousCartRemovalCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneAnonymousCartRemovalCronJob createSiteoneAnonymousCartRemovalCronJob(final Map attributeValues)
	{
		return createSiteoneAnonymousCartRemovalCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneArticle createSiteOneArticle(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEARTICLE );
			return (SiteOneArticle)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneArticle : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneArticle createSiteOneArticle(final Map attributeValues)
	{
		return createSiteOneArticle( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneArticleBannerComponent createSiteoneArticleBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEARTICLEBANNERCOMPONENT );
			return (SiteoneArticleBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneArticleBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneArticleBannerComponent createSiteoneArticleBannerComponent(final Map attributeValues)
	{
		return createSiteoneArticleBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneArticleCategory createSiteOneArticleCategory(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEARTICLECATEGORY );
			return (SiteOneArticleCategory)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneArticleCategory : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneArticleCategory createSiteOneArticleCategory(final Map attributeValues)
	{
		return createSiteOneArticleCategory( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneArticleCategoryComponent createSiteOneArticleCategoryComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEARTICLECATEGORYCOMPONENT );
			return (SiteOneArticleCategoryComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneArticleCategoryComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneArticleCategoryComponent createSiteOneArticleCategoryComponent(final Map attributeValues)
	{
		return createSiteOneArticleCategoryComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneParagaraphComponent createSiteoneArticleCategoryParagaraphComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEARTICLECATEGORYPARAGARAPHCOMPONENT );
			return (SiteoneParagaraphComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneArticleCategoryParagaraphComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneParagaraphComponent createSiteoneArticleCategoryParagaraphComponent(final Map attributeValues)
	{
		return createSiteoneArticleCategoryParagaraphComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneArticleContent createSiteOneArticleContent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEARTICLECONTENT );
			return (SiteOneArticleContent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneArticleContent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneArticleContent createSiteOneArticleContent(final Map attributeValues)
	{
		return createSiteOneArticleContent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneArticleDetailComponent createSiteOneArticleDetailComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEARTICLEDETAILCOMPONENT );
			return (SiteOneArticleDetailComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneArticleDetailComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneArticleDetailComponent createSiteOneArticleDetailComponent(final Map attributeValues)
	{
		return createSiteOneArticleDetailComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneArticleFeatureComponent createSiteoneArticleFeatureComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEARTICLEFEATURECOMPONENT );
			return (SiteoneArticleFeatureComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneArticleFeatureComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneArticleFeatureComponent createSiteoneArticleFeatureComponent(final Map attributeValues)
	{
		return createSiteoneArticleFeatureComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneArticleHeroBannerComponent createSiteoneArticleHeroBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEARTICLEHEROBANNERCOMPONENT );
			return (SiteoneArticleHeroBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneArticleHeroBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneArticleHeroBannerComponent createSiteoneArticleHeroBannerComponent(final Map attributeValues)
	{
		return createSiteoneArticleHeroBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneArticleTagsComponent createSiteOneArticleTagsComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEARTICLETAGSCOMPONENT );
			return (SiteOneArticleTagsComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneArticleTagsComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneArticleTagsComponent createSiteOneArticleTagsComponent(final Map attributeValues)
	{
		return createSiteOneArticleTagsComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneAuditCleanupCronJob createSiteOneAuditCleanupCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEAUDITCLEANUPCRONJOB );
			return (SiteOneAuditCleanupCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneAuditCleanupCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneAuditCleanupCronJob createSiteOneAuditCleanupCronJob(final Map attributeValues)
	{
		return createSiteOneAuditCleanupCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneAuditExportCronJob createSiteOneAuditExportCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEAUDITEXPORTCRONJOB );
			return (SiteOneAuditExportCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneAuditExportCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneAuditExportCronJob createSiteOneAuditExportCronJob(final Map attributeValues)
	{
		return createSiteOneAuditExportCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneBottomBannerComponent createSiteOneBottomBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEBOTTOMBANNERCOMPONENT );
			return (SiteOneBottomBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneBottomBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneBottomBannerComponent createSiteOneBottomBannerComponent(final Map attributeValues)
	{
		return createSiteOneBottomBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneBottomBannerListComponent createSiteOneBottomBannerListComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEBOTTOMBANNERLISTCOMPONENT );
			return (SiteOneBottomBannerListComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneBottomBannerListComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneBottomBannerListComponent createSiteOneBottomBannerListComponent(final Map attributeValues)
	{
		return createSiteOneBottomBannerListComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneBrandBannerComponent createSiteOneBrandBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEBRANDBANNERCOMPONENT );
			return (SiteOneBrandBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneBrandBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneBrandBannerComponent createSiteOneBrandBannerComponent(final Map attributeValues)
	{
		return createSiteOneBrandBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneCADeliveryFees createSiteoneCADeliveryFees(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECADELIVERYFEES );
			return (SiteoneCADeliveryFees)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneCADeliveryFees : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneCADeliveryFees createSiteoneCADeliveryFees(final Map attributeValues)
	{
		return createSiteoneCADeliveryFees( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneCategoryBannerComponent createSiteOneCategoryBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECATEGORYBANNERCOMPONENT );
			return (SiteOneCategoryBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneCategoryBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneCategoryBannerComponent createSiteOneCategoryBannerComponent(final Map attributeValues)
	{
		return createSiteOneCategoryBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneCategoryPageTopBannerComponent createSiteOneCategoryPageTopBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECATEGORYPAGETOPBANNERCOMPONENT );
			return (SiteOneCategoryPageTopBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneCategoryPageTopBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneCategoryPageTopBannerComponent createSiteOneCategoryPageTopBannerComponent(final Map attributeValues)
	{
		return createSiteOneCategoryPageTopBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneCategoryPromoComponent createSiteOneCategoryPromoComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECATEGORYPROMOCOMPONENT );
			return (SiteOneCategoryPromoComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneCategoryPromoComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneCategoryPromoComponent createSiteOneCategoryPromoComponent(final Map attributeValues)
	{
		return createSiteOneCategoryPromoComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneCategoryPromotionBannerComponent createSiteOneCategoryPromotionBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECATEGORYPROMOTIONBANNERCOMPONENT );
			return (SiteOneCategoryPromotionBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneCategoryPromotionBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneCategoryPromotionBannerComponent createSiteOneCategoryPromotionBannerComponent(final Map attributeValues)
	{
		return createSiteOneCategoryPromotionBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneCCPaymentAuditLog createSiteoneCCPaymentAuditLog(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECCPAYMENTAUDITLOG );
			return (SiteoneCCPaymentAuditLog)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneCCPaymentAuditLog : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneCCPaymentAuditLog createSiteoneCCPaymentAuditLog(final Map attributeValues)
	{
		return createSiteoneCCPaymentAuditLog( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneCleanUpImpexMediaCronJob createSiteoneCleanUpImpexMediaCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECLEANUPIMPEXMEDIACRONJOB );
			return (SiteoneCleanUpImpexMediaCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneCleanUpImpexMediaCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneCleanUpImpexMediaCronJob createSiteoneCleanUpImpexMediaCronJob(final Map attributeValues)
	{
		return createSiteoneCleanUpImpexMediaCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneCleanUpLogsCronJob createSiteoneCleanUpLogsCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECLEANUPLOGSCRONJOB );
			return (SiteoneCleanUpLogsCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneCleanUpLogsCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneCleanUpLogsCronJob createSiteoneCleanUpLogsCronJob(final Map attributeValues)
	{
		return createSiteoneCleanUpLogsCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneContactUsComponent createSiteoneContactUsComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECONTACTUSCOMPONENT );
			return (SiteoneContactUsComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneContactUsComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneContactUsComponent createSiteoneContactUsComponent(final Map attributeValues)
	{
		return createSiteoneContactUsComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneContrPrimaryBusiness createSiteOneContrPrimaryBusiness(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECONTRPRIMARYBUSINESS );
			return (SiteOneContrPrimaryBusiness)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneContrPrimaryBusiness : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneContrPrimaryBusiness createSiteOneContrPrimaryBusiness(final Map attributeValues)
	{
		return createSiteOneContrPrimaryBusiness( getSession().getSessionContext(), attributeValues );
	}
	
	public PaymentInfo createSiteoneCreditCardPaymentInfo(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECREDITCARDPAYMENTINFO );
			return (PaymentInfo)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneCreditCardPaymentInfo : "+e.getMessage(), 0 );
		}
	}
	
	public PaymentInfo createSiteoneCreditCardPaymentInfo(final Map attributeValues)
	{
		return createSiteoneCreditCardPaymentInfo( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneCuratedPLPBottomBannerComponent createSiteOneCuratedPLPBottomBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECURATEDPLPBOTTOMBANNERCOMPONENT );
			return (SiteOneCuratedPLPBottomBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneCuratedPLPBottomBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneCuratedPLPBottomBannerComponent createSiteOneCuratedPLPBottomBannerComponent(final Map attributeValues)
	{
		return createSiteOneCuratedPLPBottomBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneCuratedPLPPromoBottomBannerComponent createSiteOneCuratedPLPBottomPromoBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECURATEDPLPBOTTOMPROMOBANNERCOMPONENT );
			return (SiteOneCuratedPLPPromoBottomBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneCuratedPLPBottomPromoBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneCuratedPLPPromoBottomBannerComponent createSiteOneCuratedPLPBottomPromoBannerComponent(final Map attributeValues)
	{
		return createSiteOneCuratedPLPBottomPromoBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneCuratedPLPComponent createSiteOneCuratedPLPComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECURATEDPLPCOMPONENT );
			return (SiteOneCuratedPLPComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneCuratedPLPComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneCuratedPLPComponent createSiteOneCuratedPLPComponent(final Map attributeValues)
	{
		return createSiteOneCuratedPLPComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneCuratedPLPHeaderComponent createSiteOneCuratedPLPHeaderComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECURATEDPLPHEADERCOMPONENT );
			return (SiteOneCuratedPLPHeaderComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneCuratedPLPHeaderComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneCuratedPLPHeaderComponent createSiteOneCuratedPLPHeaderComponent(final Map attributeValues)
	{
		return createSiteOneCuratedPLPHeaderComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneCuratedPLPHSProductComponent createSiteOneCuratedPLPHSProductComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECURATEDPLPHSPRODUCTCOMPONENT );
			return (SiteOneCuratedPLPHSProductComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneCuratedPLPHSProductComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneCuratedPLPHSProductComponent createSiteOneCuratedPLPHSProductComponent(final Map attributeValues)
	{
		return createSiteOneCuratedPLPHSProductComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneCuratedPLPNavNode createSiteoneCuratedPLPNavNode(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECURATEDPLPNAVNODE );
			return (SiteoneCuratedPLPNavNode)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneCuratedPLPNavNode : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneCuratedPLPNavNode createSiteoneCuratedPLPNavNode(final Map attributeValues)
	{
		return createSiteoneCuratedPLPNavNode( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneCuratedPLPPromoComponent createSiteOneCuratedPLPPromoComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECURATEDPLPPROMOCOMPONENT );
			return (SiteOneCuratedPLPPromoComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneCuratedPLPPromoComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneCuratedPLPPromoComponent createSiteOneCuratedPLPPromoComponent(final Map attributeValues)
	{
		return createSiteOneCuratedPLPPromoComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneCuratedPLPStoreComponent createSiteOneCuratedPLPStoreComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONECURATEDPLPSTORECOMPONENT );
			return (SiteOneCuratedPLPStoreComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneCuratedPLPStoreComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneCuratedPLPStoreComponent createSiteOneCuratedPLPStoreComponent(final Map attributeValues)
	{
		return createSiteOneCuratedPLPStoreComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneDeal createSiteOneDeal(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEDEAL );
			return (SiteOneDeal)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneDeal : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneDeal createSiteOneDeal(final Map attributeValues)
	{
		return createSiteOneDeal( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneDeliveryFees createSiteoneDeliveryFees(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEDELIVERYFEES );
			return (SiteoneDeliveryFees)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneDeliveryFees : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneDeliveryFees createSiteoneDeliveryFees(final Map attributeValues)
	{
		return createSiteoneDeliveryFees( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneEmbedVideoComponent createSiteoneEmbedVideoComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEEMBEDVIDEOCOMPONENT );
			return (SiteoneEmbedVideoComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneEmbedVideoComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneEmbedVideoComponent createSiteoneEmbedVideoComponent(final Map attributeValues)
	{
		return createSiteoneEmbedVideoComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneEvent createSiteOneEvent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEEVENT );
			return (SiteOneEvent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneEvent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneEvent createSiteOneEvent(final Map attributeValues)
	{
		return createSiteOneEvent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneEventComponent createSiteOneEventComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEEVENTCOMPONENT );
			return (SiteOneEventComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneEventComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneEventComponent createSiteOneEventComponent(final Map attributeValues)
	{
		return createSiteOneEventComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneEventListComponent createSiteOneEventListComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEEVENTLISTCOMPONENT );
			return (SiteOneEventListComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneEventListComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneEventListComponent createSiteOneEventListComponent(final Map attributeValues)
	{
		return createSiteOneEventListComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneEventType createSiteOneEventType(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEEVENTTYPE );
			return (SiteOneEventType)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneEventType : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneEventType createSiteOneEventType(final Map attributeValues)
	{
		return createSiteOneEventType( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneEventTypeGroup createSiteOneEventTypeGroup(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEEVENTTYPEGROUP );
			return (SiteOneEventTypeGroup)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneEventTypeGroup : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneEventTypeGroup createSiteOneEventTypeGroup(final Map attributeValues)
	{
		return createSiteOneEventTypeGroup( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneEventTypeGroupComponent createSiteoneEventTypeGroupComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEEVENTTYPEGROUPCOMPONENT );
			return (SiteoneEventTypeGroupComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneEventTypeGroupComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneEventTypeGroupComponent createSiteoneEventTypeGroupComponent(final Map attributeValues)
	{
		return createSiteoneEventTypeGroupComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneEventTypeGroupHeaderComponent createSiteoneEventTypeGroupHeaderComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEEVENTTYPEGROUPHEADERCOMPONENT );
			return (SiteoneEventTypeGroupHeaderComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneEventTypeGroupHeaderComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneEventTypeGroupHeaderComponent createSiteoneEventTypeGroupHeaderComponent(final Map attributeValues)
	{
		return createSiteoneEventTypeGroupHeaderComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneEwalletCreditCard createSiteoneEwalletCreditCard(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEEWALLETCREDITCARD );
			return (SiteoneEwalletCreditCard)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneEwalletCreditCard : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneEwalletCreditCard createSiteoneEwalletCreditCard(final Map attributeValues)
	{
		return createSiteoneEwalletCreditCard( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteonefaqComponent createSiteoneFAQ(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEFAQ );
			return (SiteonefaqComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneFAQ : "+e.getMessage(), 0 );
		}
	}
	
	public SiteonefaqComponent createSiteoneFAQ(final Map attributeValues)
	{
		return createSiteoneFAQ( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneFAQCollectionComponent createSiteoneFAQCollectionComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEFAQCOLLECTIONCOMPONENT );
			return (SiteoneFAQCollectionComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneFAQCollectionComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneFAQCollectionComponent createSiteoneFAQCollectionComponent(final Map attributeValues)
	{
		return createSiteoneFAQCollectionComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneFeatureProductComponent createSiteOneFeatureProductComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEFEATUREPRODUCTCOMPONENT );
			return (SiteOneFeatureProductComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneFeatureProductComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneFeatureProductComponent createSiteOneFeatureProductComponent(final Map attributeValues)
	{
		return createSiteOneFeatureProductComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneFeatureSwitch createSiteOneFeatureSwitch(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEFEATURESWITCH );
			return (SiteOneFeatureSwitch)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneFeatureSwitch : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneFeatureSwitch createSiteOneFeatureSwitch(final Map attributeValues)
	{
		return createSiteOneFeatureSwitch( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneFeedFileInfo createSiteOneFeedFileInfo(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEFEEDFILEINFO );
			return (SiteOneFeedFileInfo)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneFeedFileInfo : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneFeedFileInfo createSiteOneFeedFileInfo(final Map attributeValues)
	{
		return createSiteOneFeedFileInfo( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneFeedFileInfoCronJob createSiteOneFeedFileInfoCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEFEEDFILEINFOCRONJOB );
			return (SiteOneFeedFileInfoCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneFeedFileInfoCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneFeedFileInfoCronJob createSiteOneFeedFileInfoCronJob(final Map attributeValues)
	{
		return createSiteOneFeedFileInfoCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneGalleryHeaderBannerComponent createSiteOneGalleryHeaderBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEGALLERYHEADERBANNERCOMPONENT );
			return (SiteOneGalleryHeaderBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneGalleryHeaderBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneGalleryHeaderBannerComponent createSiteOneGalleryHeaderBannerComponent(final Map attributeValues)
	{
		return createSiteOneGalleryHeaderBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneGalleryHeaderComponent createSiteOneGalleryHeaderComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEGALLERYHEADERCOMPONENT );
			return (SiteOneGalleryHeaderComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneGalleryHeaderComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneGalleryHeaderComponent createSiteOneGalleryHeaderComponent(final Map attributeValues)
	{
		return createSiteOneGalleryHeaderComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneGalleryImageComponent createSiteOneGalleryImageComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEGALLERYIMAGECOMPONENT );
			return (SiteOneGalleryImageComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneGalleryImageComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneGalleryImageComponent createSiteOneGalleryImageComponent(final Map attributeValues)
	{
		return createSiteOneGalleryImageComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneGalleryParagraphComponent createSiteOneGalleryParagraphComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEGALLERYPARAGRAPHCOMPONENT );
			return (SiteOneGalleryParagraphComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneGalleryParagraphComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneGalleryParagraphComponent createSiteOneGalleryParagraphComponent(final Map attributeValues)
	{
		return createSiteOneGalleryParagraphComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneGreenButtonComponent createSiteoneGreenButtonComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEGREENBUTTONCOMPONENT );
			return (SiteoneGreenButtonComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneGreenButtonComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneGreenButtonComponent createSiteoneGreenButtonComponent(final Map attributeValues)
	{
		return createSiteoneGreenButtonComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneHeaderPromotionalComponent createSiteoneHeaderPromotionalComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEHEADERPROMOTIONALCOMPONENT );
			return (SiteoneHeaderPromotionalComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneHeaderPromotionalComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneHeaderPromotionalComponent createSiteoneHeaderPromotionalComponent(final Map attributeValues)
	{
		return createSiteoneHeaderPromotionalComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneHomeBannerComponent createSiteOneHomeBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEHOMEBANNERCOMPONENT );
			return (SiteOneHomeBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneHomeBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneHomeBannerComponent createSiteOneHomeBannerComponent(final Map attributeValues)
	{
		return createSiteOneHomeBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneHomePageBackgroundImageComponent createSiteOneHomePageBackgroundImageComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEHOMEPAGEBACKGROUNDIMAGECOMPONENT );
			return (SiteOneHomePageBackgroundImageComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneHomePageBackgroundImageComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneHomePageBackgroundImageComponent createSiteOneHomePageBackgroundImageComponent(final Map attributeValues)
	{
		return createSiteOneHomePageBackgroundImageComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneHomePageMobileBrandBannerComponent createSiteOneHomePageMobileBrandBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEHOMEPAGEMOBILEBRANDBANNERCOMPONENT );
			return (SiteOneHomePageMobileBrandBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneHomePageMobileBrandBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneHomePageMobileBrandBannerComponent createSiteOneHomePageMobileBrandBannerComponent(final Map attributeValues)
	{
		return createSiteOneHomePageMobileBrandBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneHomePageParagraphComponent createSiteOneHomePageParagraphComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEHOMEPAGEPARAGRAPHCOMPONENT );
			return (SiteOneHomePageParagraphComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneHomePageParagraphComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneHomePageParagraphComponent createSiteOneHomePageParagraphComponent(final Map attributeValues)
	{
		return createSiteOneHomePageParagraphComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneHomePageProductsComponent createSiteOneHomePageProductsComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEHOMEPAGEPRODUCTSCOMPONENT );
			return (SiteOneHomePageProductsComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneHomePageProductsComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneHomePageProductsComponent createSiteOneHomePageProductsComponent(final Map attributeValues)
	{
		return createSiteOneHomePageProductsComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneHomePageTextBannerComponent createSiteOneHomePageTextBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEHOMEPAGETEXTBANNERCOMPONENT );
			return (SiteOneHomePageTextBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneHomePageTextBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneHomePageTextBannerComponent createSiteOneHomePageTextBannerComponent(final Map attributeValues)
	{
		return createSiteOneHomePageTextBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneHotFolderArchiveCleanupCronJob createSiteOneHotFolderArchiveCleanupCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEHOTFOLDERARCHIVECLEANUPCRONJOB );
			return (SiteOneHotFolderArchiveCleanupCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneHotFolderArchiveCleanupCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneHotFolderArchiveCleanupCronJob createSiteOneHotFolderArchiveCleanupCronJob(final Map attributeValues)
	{
		return createSiteOneHotFolderArchiveCleanupCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneInspiration createSiteOneInspiration(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEINSPIRATION );
			return (SiteOneInspiration)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneInspiration : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneInspiration createSiteOneInspiration(final Map attributeValues)
	{
		return createSiteOneInspiration( getSession().getSessionContext(), attributeValues );
	}
	
	public Invoice createSiteOneInvoice(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEINVOICE );
			return (Invoice)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneInvoice : "+e.getMessage(), 0 );
		}
	}
	
	public Invoice createSiteOneInvoice(final Map attributeValues)
	{
		return createSiteOneInvoice( getSession().getSessionContext(), attributeValues );
	}
	
	public InvoiceEntry createSiteOneInvoiceEntry(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEINVOICEENTRY );
			return (InvoiceEntry)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneInvoiceEntry : "+e.getMessage(), 0 );
		}
	}
	
	public InvoiceEntry createSiteOneInvoiceEntry(final Map attributeValues)
	{
		return createSiteOneInvoiceEntry( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneJobsStatusCronJob createSiteoneJobsStatusCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEJOBSSTATUSCRONJOB );
			return (SiteoneJobsStatusCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneJobsStatusCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneJobsStatusCronJob createSiteoneJobsStatusCronJob(final Map attributeValues)
	{
		return createSiteoneJobsStatusCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneJobsStatusProcess createSiteoneJobsStatusProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEJOBSSTATUSPROCESS );
			return (SiteoneJobsStatusProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneJobsStatusProcess : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneJobsStatusProcess createSiteoneJobsStatusProcess(final Map attributeValues)
	{
		return createSiteoneJobsStatusProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneKountData createSiteoneKountData(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEKOUNTDATA );
			return (SiteoneKountData)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneKountData : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneKountData createSiteoneKountData(final Map attributeValues)
	{
		return createSiteoneKountData( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneLeftNavigationComponent createSiteoneLeftNavigationComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONELEFTNAVIGATIONCOMPONENT );
			return (SiteoneLeftNavigationComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneLeftNavigationComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneLeftNavigationComponent createSiteoneLeftNavigationComponent(final Map attributeValues)
	{
		return createSiteoneLeftNavigationComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneLoginPageComponent createSiteOneLoginPageComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONELOGINPAGECOMPONENT );
			return (SiteOneLoginPageComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneLoginPageComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneLoginPageComponent createSiteOneLoginPageComponent(final Map attributeValues)
	{
		return createSiteOneLoginPageComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneMarketingBannerComponent createSiteOneMarketingBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEMARKETINGBANNERCOMPONENT );
			return (SiteOneMarketingBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneMarketingBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneMarketingBannerComponent createSiteOneMarketingBannerComponent(final Map attributeValues)
	{
		return createSiteOneMarketingBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneNews createSiteOneNews(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONENEWS );
			return (SiteOneNews)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneNews : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneNews createSiteOneNews(final Map attributeValues)
	{
		return createSiteOneNews( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneOldInvoiceRemovalJob createSiteoneOldInvoiceRemovalJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEOLDINVOICEREMOVALJOB );
			return (SiteoneOldInvoiceRemovalJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneOldInvoiceRemovalJob : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneOldInvoiceRemovalJob createSiteoneOldInvoiceRemovalJob(final Map attributeValues)
	{
		return createSiteoneOldInvoiceRemovalJob( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneOldOrderRemovalJob createSiteoneOldOrderRemovalJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEOLDORDERREMOVALJOB );
			return (SiteoneOldOrderRemovalJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneOldOrderRemovalJob : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneOldOrderRemovalJob createSiteoneOldOrderRemovalJob(final Map attributeValues)
	{
		return createSiteoneOldOrderRemovalJob( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneOrderEmailStatus createSiteoneOrderEmailStatus(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEORDEREMAILSTATUS );
			return (SiteoneOrderEmailStatus)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneOrderEmailStatus : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneOrderEmailStatus createSiteoneOrderEmailStatus(final Map attributeValues)
	{
		return createSiteoneOrderEmailStatus( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteonePageTitleBannerComponent createSiteonePageTitleBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPAGETITLEBANNERCOMPONENT );
			return (SiteonePageTitleBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteonePageTitleBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteonePageTitleBannerComponent createSiteonePageTitleBannerComponent(final Map attributeValues)
	{
		return createSiteonePageTitleBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneParagaraphComponent createSiteoneParagaraphComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPARAGARAPHCOMPONENT );
			return (SiteoneParagaraphComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneParagaraphComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneParagaraphComponent createSiteoneParagaraphComponent(final Map attributeValues)
	{
		return createSiteoneParagaraphComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneParagraphComponent createSiteoneParagraphComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPARAGRAPHCOMPONENT );
			return (SiteoneParagraphComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneParagraphComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneParagraphComponent createSiteoneParagraphComponent(final Map attributeValues)
	{
		return createSiteoneParagraphComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteonePartnerPerkBottomParagraphComponent createSiteonePartnerPerkBottomParagraphComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPARTNERPERKBOTTOMPARAGRAPHCOMPONENT );
			return (SiteonePartnerPerkBottomParagraphComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteonePartnerPerkBottomParagraphComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteonePartnerPerkBottomParagraphComponent createSiteonePartnerPerkBottomParagraphComponent(final Map attributeValues)
	{
		return createSiteonePartnerPerkBottomParagraphComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOnePartnerPerkParagraphComponent createSiteOnePartnerPerkParagraphComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPARTNERPERKPARAGRAPHCOMPONENT );
			return (SiteOnePartnerPerkParagraphComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOnePartnerPerkParagraphComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOnePartnerPerkParagraphComponent createSiteOnePartnerPerkParagraphComponent(final Map attributeValues)
	{
		return createSiteOnePartnerPerkParagraphComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOnePartnerPointsMessageComponent createSiteOnePartnerPointsMessageComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPARTNERPOINTSMESSAGECOMPONENT );
			return (SiteOnePartnerPointsMessageComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOnePartnerPointsMessageComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOnePartnerPointsMessageComponent createSiteOnePartnerPointsMessageComponent(final Map attributeValues)
	{
		return createSiteOnePartnerPointsMessageComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOnePartnerProgramComponent createSiteOnePartnerProgramComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPARTNERPROGRAMCOMPONENT );
			return (SiteOnePartnerProgramComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOnePartnerProgramComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOnePartnerProgramComponent createSiteOnePartnerProgramComponent(final Map attributeValues)
	{
		return createSiteOnePartnerProgramComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteonePartnerPromotionalComponent createSiteonePartnerPromotionalComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPARTNERPROMOTIONALCOMPONENT );
			return (SiteonePartnerPromotionalComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteonePartnerPromotionalComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteonePartnerPromotionalComponent createSiteonePartnerPromotionalComponent(final Map attributeValues)
	{
		return createSiteonePartnerPromotionalComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOnePilotPartnerParagraphComponent createSiteOnePilotPartnerParagraphComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPILOTPARTNERPARAGRAPHCOMPONENT );
			return (SiteOnePilotPartnerParagraphComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOnePilotPartnerParagraphComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOnePilotPartnerParagraphComponent createSiteOnePilotPartnerParagraphComponent(final Map attributeValues)
	{
		return createSiteOnePilotPartnerParagraphComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public PaymentInfo createSiteonePOAPaymentInfo(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPOAPAYMENTINFO );
			return (PaymentInfo)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteonePOAPaymentInfo : "+e.getMessage(), 0 );
		}
	}
	
	public PaymentInfo createSiteonePOAPaymentInfo(final Map attributeValues)
	{
		return createSiteonePOAPaymentInfo( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteonePricingTrack createSiteonePricingTrack(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPRICINGTRACK );
			return (SiteonePricingTrack)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteonePricingTrack : "+e.getMessage(), 0 );
		}
	}
	
	public SiteonePricingTrack createSiteonePricingTrack(final Map attributeValues)
	{
		return createSiteonePricingTrack( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneProductPromotionComponent createSiteOneProductPromotionComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPRODUCTPROMOTIONCOMPONENT );
			return (SiteOneProductPromotionComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneProductPromotionComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneProductPromotionComponent createSiteOneProductPromotionComponent(final Map attributeValues)
	{
		return createSiteOneProductPromotionComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteonePromotionalBannerComponent createSiteonePromotionalBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPROMOTIONALBANNERCOMPONENT );
			return (SiteonePromotionalBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteonePromotionalBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteonePromotionalBannerComponent createSiteonePromotionalBannerComponent(final Map attributeValues)
	{
		return createSiteonePromotionalBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOnePromotionalResponsiveBannerComponent createSiteOnePromotionalResponsiveBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPROMOTIONALRESPONSIVEBANNERCOMPONENT );
			return (SiteOnePromotionalResponsiveBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOnePromotionalResponsiveBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOnePromotionalResponsiveBannerComponent createSiteOnePromotionalResponsiveBannerComponent(final Map attributeValues)
	{
		return createSiteOnePromotionalResponsiveBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOnePromotionCategoryComponent createSiteOnePromotionCategoryComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPROMOTIONCATEGORYCOMPONENT );
			return (SiteOnePromotionCategoryComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOnePromotionCategoryComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOnePromotionCategoryComponent createSiteOnePromotionCategoryComponent(final Map attributeValues)
	{
		return createSiteOnePromotionCategoryComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOnePromotionComponent createSiteOnePromotionComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEPROMOTIONCOMPONENT );
			return (SiteOnePromotionComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOnePromotionComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOnePromotionComponent createSiteOnePromotionComponent(final Map attributeValues)
	{
		return createSiteOnePromotionComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneQuickBooksHeaderComponent createSiteOneQuickBooksHeaderComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEQUICKBOOKSHEADERCOMPONENT );
			return (SiteOneQuickBooksHeaderComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneQuickBooksHeaderComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneQuickBooksHeaderComponent createSiteOneQuickBooksHeaderComponent(final Map attributeValues)
	{
		return createSiteOneQuickBooksHeaderComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneRegionSegment createSiteOneRegionSegment(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEREGIONSEGMENT );
			return (SiteOneRegionSegment)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneRegionSegment : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneRegionSegment createSiteOneRegionSegment(final Map attributeValues)
	{
		return createSiteOneRegionSegment( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneRequestAccount createSiteoneRequestAccount(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEREQUESTACCOUNT );
			return (SiteoneRequestAccount)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneRequestAccount : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneRequestAccount createSiteoneRequestAccount(final Map attributeValues)
	{
		return createSiteoneRequestAccount( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneShippingFees createSiteoneShippingFees(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONESHIPPINGFEES );
			return (SiteoneShippingFees)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneShippingFees : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneShippingFees createSiteoneShippingFees(final Map attributeValues)
	{
		return createSiteoneShippingFees( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneStoreDetailPromoComponent createSiteOneStoreDetailPromoComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONESTOREDETAILPROMOCOMPONENT );
			return (SiteOneStoreDetailPromoComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneStoreDetailPromoComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneStoreDetailPromoComponent createSiteOneStoreDetailPromoComponent(final Map attributeValues)
	{
		return createSiteOneStoreDetailPromoComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneStoreDetailsComponent createSiteoneStoreDetailsComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONESTOREDETAILSCOMPONENT );
			return (SiteoneStoreDetailsComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneStoreDetailsComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneStoreDetailsComponent createSiteoneStoreDetailsComponent(final Map attributeValues)
	{
		return createSiteoneStoreDetailsComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneStoreDetailsPromoComponent createSiteOneStoreDetailsPromoComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONESTOREDETAILSPROMOCOMPONENT );
			return (SiteOneStoreDetailsPromoComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneStoreDetailsPromoComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneStoreDetailsPromoComponent createSiteOneStoreDetailsPromoComponent(final Map attributeValues)
	{
		return createSiteOneStoreDetailsPromoComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneTitleBannerComponent createSiteoneTitleBannerComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONETITLEBANNERCOMPONENT );
			return (SiteoneTitleBannerComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneTitleBannerComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneTitleBannerComponent createSiteoneTitleBannerComponent(final Map attributeValues)
	{
		return createSiteoneTitleBannerComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneToolsListComponent createSiteOneToolsListComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONETOOLSLISTCOMPONENT );
			return (SiteOneToolsListComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneToolsListComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneToolsListComponent createSiteOneToolsListComponent(final Map attributeValues)
	{
		return createSiteOneToolsListComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneTradeClassSegment createSiteOneTradeClassSegment(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONETRADECLASSSEGMENT );
			return (SiteOneTradeClassSegment)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneTradeClassSegment : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneTradeClassSegment createSiteOneTradeClassSegment(final Map attributeValues)
	{
		return createSiteOneTradeClassSegment( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteoneVideoComponent createSiteoneVideoComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.SITEONEVIDEOCOMPONENT );
			return (SiteoneVideoComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteoneVideoComponent : "+e.getMessage(), 0 );
		}
	}
	
	public SiteoneVideoComponent createSiteoneVideoComponent(final Map attributeValues)
	{
		return createSiteoneVideoComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public StoreTimeZoneStatusCronJob createStoreTimeZoneStatusCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.STORETIMEZONESTATUSCRONJOB );
			return (StoreTimeZoneStatusCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating StoreTimeZoneStatusCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public StoreTimeZoneStatusCronJob createStoreTimeZoneStatusCronJob(final Map attributeValues)
	{
		return createStoreTimeZoneStatusCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public TalonOneEnrollment createTalonOneEnrollment(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.TALONONEENROLLMENT );
			return (TalonOneEnrollment)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating TalonOneEnrollment : "+e.getMessage(), 0 );
		}
	}
	
	public TalonOneEnrollment createTalonOneEnrollment(final Map attributeValues)
	{
		return createTalonOneEnrollment( getSession().getSessionContext(), attributeValues );
	}
	
	public UnlockUserEmailProcess createUnlockUserEmailProcess(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.UNLOCKUSEREMAILPROCESS );
			return (UnlockUserEmailProcess)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating UnlockUserEmailProcess : "+e.getMessage(), 0 );
		}
	}
	
	public UnlockUserEmailProcess createUnlockUserEmailProcess(final Map attributeValues)
	{
		return createUnlockUserEmailProcess( getSession().getSessionContext(), attributeValues );
	}
	
	public UomRewriteConfig createUomRewriteConfig(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.UOMREWRITECONFIG );
			return (UomRewriteConfig)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating UomRewriteConfig : "+e.getMessage(), 0 );
		}
	}
	
	public UomRewriteConfig createUomRewriteConfig(final Map attributeValues)
	{
		return createUomRewriteConfig( getSession().getSessionContext(), attributeValues );
	}
	
	public UploadErrorProductDetail createUploadErrorProductDetail(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.UPLOADERRORPRODUCTDETAIL );
			return (UploadErrorProductDetail)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating UploadErrorProductDetail : "+e.getMessage(), 0 );
		}
	}
	
	public UploadErrorProductDetail createUploadErrorProductDetail(final Map attributeValues)
	{
		return createUploadErrorProductDetail( getSession().getSessionContext(), attributeValues );
	}
	
	public UploadListErrorInfo createUploadListErrorInfo(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.UPLOADLISTERRORINFO );
			return (UploadListErrorInfo)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating UploadListErrorInfo : "+e.getMessage(), 0 );
		}
	}
	
	public UploadListErrorInfo createUploadListErrorInfo(final Map attributeValues)
	{
		return createUploadListErrorInfo( getSession().getSessionContext(), attributeValues );
	}
	
	public VerticalBarComponent createVerticalBarComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.VERTICALBARCOMPONENT );
			return (VerticalBarComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating VerticalBarComponent : "+e.getMessage(), 0 );
		}
	}
	
	public VerticalBarComponent createVerticalBarComponent(final Map attributeValues)
	{
		return createVerticalBarComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public WebserviceAuditLog createWebserviceAuditLog(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.WEBSERVICEAUDITLOG );
			return (WebserviceAuditLog)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating WebserviceAuditLog : "+e.getMessage(), 0 );
		}
	}
	
	public WebserviceAuditLog createWebserviceAuditLog(final Map attributeValues)
	{
		return createWebserviceAuditLog( getSession().getSessionContext(), attributeValues );
	}
	
	public WebServiceAuditLogCleanUpCronJob createWebServiceAuditLogCleanUpCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( SiteoneCoreConstants.TC.WEBSERVICEAUDITLOGCLEANUPCRONJOB );
			return (WebServiceAuditLogCleanUpCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating WebServiceAuditLogCleanUpCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public WebServiceAuditLogCleanUpCronJob createWebServiceAuditLogCleanUpCronJob(final Map attributeValues)
	{
		return createWebServiceAuditLogCleanUpCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.creditCode</code> attribute.
	 * @return the creditCode - customer credit code
	 */
	public String getCreditCode(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.CREDITCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.creditCode</code> attribute.
	 * @return the creditCode - customer credit code
	 */
	public String getCreditCode(final B2BUnit item)
	{
		return getCreditCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.creditCode</code> attribute. 
	 * @param value the creditCode - customer credit code
	 */
	public void setCreditCode(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.CREDITCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.creditCode</code> attribute. 
	 * @param value the creditCode - customer credit code
	 */
	public void setCreditCode(final B2BUnit item, final String value)
	{
		setCreditCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.creditTermCode</code> attribute.
	 * @return the creditTermCode - customer credit terms code
	 */
	public String getCreditTermCode(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.CREDITTERMCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.creditTermCode</code> attribute.
	 * @return the creditTermCode - customer credit terms code
	 */
	public String getCreditTermCode(final B2BUnit item)
	{
		return getCreditTermCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.creditTermCode</code> attribute. 
	 * @param value the creditTermCode - customer credit terms code
	 */
	public void setCreditTermCode(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.CREDITTERMCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.creditTermCode</code> attribute. 
	 * @param value the creditTermCode - customer credit terms code
	 */
	public void setCreditTermCode(final B2BUnit item, final String value)
	{
		setCreditTermCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.currentCarId</code> attribute.
	 * @return the currentCarId
	 */
	public String getCurrentCarId(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.CURRENTCARID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.currentCarId</code> attribute.
	 * @return the currentCarId
	 */
	public String getCurrentCarId(final B2BCustomer item)
	{
		return getCurrentCarId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.currentCarId</code> attribute. 
	 * @param value the currentCarId
	 */
	public void setCurrentCarId(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.CURRENTCARID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.currentCarId</code> attribute. 
	 * @param value the currentCarId
	 */
	public void setCurrentCarId(final B2BCustomer item, final String value)
	{
		setCurrentCarId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.customerLogo</code> attribute.
	 * @return the customerLogo
	 */
	public Media getCustomerLogo(final SessionContext ctx, final B2BUnit item)
	{
		return (Media)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.CUSTOMERLOGO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.customerLogo</code> attribute.
	 * @return the customerLogo
	 */
	public Media getCustomerLogo(final B2BUnit item)
	{
		return getCustomerLogo( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.customerLogo</code> attribute. 
	 * @param value the customerLogo
	 */
	public void setCustomerLogo(final SessionContext ctx, final B2BUnit item, final Media value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.CUSTOMERLOGO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.customerLogo</code> attribute. 
	 * @param value the customerLogo
	 */
	public void setCustomerLogo(final B2BUnit item, final Media value)
	{
		setCustomerLogo( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.customerRetailId</code> attribute.
	 * @return the customerRetailId
	 */
	public String getCustomerRetailId(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.CUSTOMERRETAILID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.customerRetailId</code> attribute.
	 * @return the customerRetailId
	 */
	public String getCustomerRetailId(final PointOfService item)
	{
		return getCustomerRetailId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.customerRetailId</code> attribute. 
	 * @param value the customerRetailId
	 */
	public void setCustomerRetailId(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.CUSTOMERRETAILID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.customerRetailId</code> attribute. 
	 * @param value the customerRetailId
	 */
	public void setCustomerRetailId(final PointOfService item, final String value)
	{
		setCustomerRetailId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.customers</code> attribute.
	 * @return the customers
	 */
	public Set<B2BCustomer> getCustomers(final SessionContext ctx, final PointOfService item)
	{
		final List<B2BCustomer> items = item.getLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2B2BCUSTOMERRELATION,
			"B2BCustomer",
			null,
			false,
			false
		);
		return new LinkedHashSet<B2BCustomer>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.customers</code> attribute.
	 * @return the customers
	 */
	public Set<B2BCustomer> getCustomers(final PointOfService item)
	{
		return getCustomers( getSession().getSessionContext(), item );
	}
	
	public long getCustomersCount(final SessionContext ctx, final PointOfService item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2B2BCUSTOMERRELATION,
			"B2BCustomer",
			null
		);
	}
	
	public long getCustomersCount(final PointOfService item)
	{
		return getCustomersCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.customers</code> attribute. 
	 * @param value the customers
	 */
	public void setCustomers(final SessionContext ctx, final PointOfService item, final Set<B2BCustomer> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2B2BCUSTOMERRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2B2BCUSTOMERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.customers</code> attribute. 
	 * @param value the customers
	 */
	public void setCustomers(final PointOfService item, final Set<B2BCustomer> value)
	{
		setCustomers( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to customers. 
	 * @param value the item to add to customers
	 */
	public void addToCustomers(final SessionContext ctx, final PointOfService item, final B2BCustomer value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2B2BCUSTOMERRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2B2BCUSTOMERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to customers. 
	 * @param value the item to add to customers
	 */
	public void addToCustomers(final PointOfService item, final B2BCustomer value)
	{
		addToCustomers( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from customers. 
	 * @param value the item to remove from customers
	 */
	public void removeFromCustomers(final SessionContext ctx, final PointOfService item, final B2BCustomer value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2B2BCUSTOMERRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2B2BCUSTOMERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from customers. 
	 * @param value the item to remove from customers
	 */
	public void removeFromCustomers(final PointOfService item, final B2BCustomer value)
	{
		removeFromCustomers( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.customerSegment</code> attribute.
	 * @return the customerSegment - customer Segment
	 */
	public String getCustomerSegment(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.CUSTOMERSEGMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.customerSegment</code> attribute.
	 * @return the customerSegment - customer Segment
	 */
	public String getCustomerSegment(final B2BUnit item)
	{
		return getCustomerSegment( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.customerSegment</code> attribute. 
	 * @param value the customerSegment - customer Segment
	 */
	public void setCustomerSegment(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.CUSTOMERSEGMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.customerSegment</code> attribute. 
	 * @param value the customerSegment - customer Segment
	 */
	public void setCustomerSegment(final B2BUnit item, final String value)
	{
		setCustomerSegment( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.deal</code> attribute.
	 * @return the deal
	 */
	public SiteOneDeal getDeal(final SessionContext ctx, final Product item)
	{
		return (SiteOneDeal)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.DEAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.deal</code> attribute.
	 * @return the deal
	 */
	public SiteOneDeal getDeal(final Product item)
	{
		return getDeal( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.deal</code> attribute. 
	 * @param value the deal
	 */
	public void setDeal(final SessionContext ctx, final Product item, final SiteOneDeal value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.DEAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.deal</code> attribute. 
	 * @param value the deal
	 */
	public void setDeal(final Product item, final SiteOneDeal value)
	{
		setDeal( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CMSSite.defaultStore</code> attribute.
	 * @return the defaultStore - Indicates the default Store Which will be visible for anonymous user If GeoLocation can't be find
	 */
	public PointOfService getDefaultStore(final SessionContext ctx, final CMSSite item)
	{
		return (PointOfService)item.getProperty( ctx, SiteoneCoreConstants.Attributes.CMSSite.DEFAULTSTORE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CMSSite.defaultStore</code> attribute.
	 * @return the defaultStore - Indicates the default Store Which will be visible for anonymous user If GeoLocation can't be find
	 */
	public PointOfService getDefaultStore(final CMSSite item)
	{
		return getDefaultStore( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CMSSite.defaultStore</code> attribute. 
	 * @param value the defaultStore - Indicates the default Store Which will be visible for anonymous user If GeoLocation can't be find
	 */
	public void setDefaultStore(final SessionContext ctx, final CMSSite item, final PointOfService value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.CMSSite.DEFAULTSTORE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CMSSite.defaultStore</code> attribute. 
	 * @param value the defaultStore - Indicates the default Store Which will be visible for anonymous user If GeoLocation can't be find
	 */
	public void setDefaultStore(final CMSSite item, final PointOfService value)
	{
		setDefaultStore( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.deliverableItemTotal</code> attribute.
	 * @return the deliverableItemTotal
	 */
	public Double getDeliverableItemTotal(final SessionContext ctx, final Cart item)
	{
		return (Double)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Cart.DELIVERABLEITEMTOTAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.deliverableItemTotal</code> attribute.
	 * @return the deliverableItemTotal
	 */
	public Double getDeliverableItemTotal(final Cart item)
	{
		return getDeliverableItemTotal( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.deliverableItemTotal</code> attribute. 
	 * @return the deliverableItemTotal
	 */
	public double getDeliverableItemTotalAsPrimitive(final SessionContext ctx, final Cart item)
	{
		Double value = getDeliverableItemTotal( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.deliverableItemTotal</code> attribute. 
	 * @return the deliverableItemTotal
	 */
	public double getDeliverableItemTotalAsPrimitive(final Cart item)
	{
		return getDeliverableItemTotalAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.deliverableItemTotal</code> attribute. 
	 * @param value the deliverableItemTotal
	 */
	public void setDeliverableItemTotal(final SessionContext ctx, final Cart item, final Double value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Cart.DELIVERABLEITEMTOTAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.deliverableItemTotal</code> attribute. 
	 * @param value the deliverableItemTotal
	 */
	public void setDeliverableItemTotal(final Cart item, final Double value)
	{
		setDeliverableItemTotal( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.deliverableItemTotal</code> attribute. 
	 * @param value the deliverableItemTotal
	 */
	public void setDeliverableItemTotal(final SessionContext ctx, final Cart item, final double value)
	{
		setDeliverableItemTotal( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.deliverableItemTotal</code> attribute. 
	 * @param value the deliverableItemTotal
	 */
	public void setDeliverableItemTotal(final Cart item, final double value)
	{
		setDeliverableItemTotal( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.deliveryAlwaysEnabledBranches</code> attribute.
	 * @return the deliveryAlwaysEnabledBranches
	 */
	public String getDeliveryAlwaysEnabledBranches(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.DELIVERYALWAYSENABLEDBRANCHES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.deliveryAlwaysEnabledBranches</code> attribute.
	 * @return the deliveryAlwaysEnabledBranches
	 */
	public String getDeliveryAlwaysEnabledBranches(final Product item)
	{
		return getDeliveryAlwaysEnabledBranches( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.deliveryAlwaysEnabledBranches</code> attribute. 
	 * @param value the deliveryAlwaysEnabledBranches
	 */
	public void setDeliveryAlwaysEnabledBranches(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.DELIVERYALWAYSENABLEDBRANCHES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.deliveryAlwaysEnabledBranches</code> attribute. 
	 * @param value the deliveryAlwaysEnabledBranches
	 */
	public void setDeliveryAlwaysEnabledBranches(final Product item, final String value)
	{
		setDeliveryAlwaysEnabledBranches( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.deliveryContactPerson</code> attribute.
	 * @return the deliveryContactPerson
	 */
	public B2BCustomer getDeliveryContactPerson(final SessionContext ctx, final AbstractOrder item)
	{
		return (B2BCustomer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.DELIVERYCONTACTPERSON);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.deliveryContactPerson</code> attribute.
	 * @return the deliveryContactPerson
	 */
	public B2BCustomer getDeliveryContactPerson(final AbstractOrder item)
	{
		return getDeliveryContactPerson( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.deliveryContactPerson</code> attribute. 
	 * @param value the deliveryContactPerson
	 */
	public void setDeliveryContactPerson(final SessionContext ctx, final AbstractOrder item, final B2BCustomer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.DELIVERYCONTACTPERSON,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.deliveryContactPerson</code> attribute. 
	 * @param value the deliveryContactPerson
	 */
	public void setDeliveryContactPerson(final AbstractOrder item, final B2BCustomer value)
	{
		setDeliveryContactPerson( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.deliveryFreight</code> attribute.
	 * @return the deliveryFreight
	 */
	public String getDeliveryFreight(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.DELIVERYFREIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.deliveryFreight</code> attribute.
	 * @return the deliveryFreight
	 */
	public String getDeliveryFreight(final AbstractOrder item)
	{
		return getDeliveryFreight( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.deliveryFreight</code> attribute. 
	 * @param value the deliveryFreight
	 */
	public void setDeliveryFreight(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.DELIVERYFREIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.deliveryFreight</code> attribute. 
	 * @param value the deliveryFreight
	 */
	public void setDeliveryFreight(final AbstractOrder item, final String value)
	{
		setDeliveryFreight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.deliveryfullfillment</code> attribute.
	 * @return the deliveryfullfillment
	 */
	public Boolean isDeliveryfullfillment(final SessionContext ctx, final PointOfService item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.DELIVERYFULLFILLMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.deliveryfullfillment</code> attribute.
	 * @return the deliveryfullfillment
	 */
	public Boolean isDeliveryfullfillment(final PointOfService item)
	{
		return isDeliveryfullfillment( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.deliveryfullfillment</code> attribute. 
	 * @return the deliveryfullfillment
	 */
	public boolean isDeliveryfullfillmentAsPrimitive(final SessionContext ctx, final PointOfService item)
	{
		Boolean value = isDeliveryfullfillment( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.deliveryfullfillment</code> attribute. 
	 * @return the deliveryfullfillment
	 */
	public boolean isDeliveryfullfillmentAsPrimitive(final PointOfService item)
	{
		return isDeliveryfullfillmentAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.deliveryfullfillment</code> attribute. 
	 * @param value the deliveryfullfillment
	 */
	public void setDeliveryfullfillment(final SessionContext ctx, final PointOfService item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.DELIVERYFULLFILLMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.deliveryfullfillment</code> attribute. 
	 * @param value the deliveryfullfillment
	 */
	public void setDeliveryfullfillment(final PointOfService item, final Boolean value)
	{
		setDeliveryfullfillment( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.deliveryfullfillment</code> attribute. 
	 * @param value the deliveryfullfillment
	 */
	public void setDeliveryfullfillment(final SessionContext ctx, final PointOfService item, final boolean value)
	{
		setDeliveryfullfillment( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.deliveryfullfillment</code> attribute. 
	 * @param value the deliveryfullfillment
	 */
	public void setDeliveryfullfillment(final PointOfService item, final boolean value)
	{
		setDeliveryfullfillment( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.deliveryInstruction</code> attribute.
	 * @return the deliveryInstruction
	 */
	public String getDeliveryInstruction(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.DELIVERYINSTRUCTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.deliveryInstruction</code> attribute.
	 * @return the deliveryInstruction
	 */
	public String getDeliveryInstruction(final AbstractOrder item)
	{
		return getDeliveryInstruction( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.deliveryInstruction</code> attribute. 
	 * @param value the deliveryInstruction
	 */
	public void setDeliveryInstruction(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.DELIVERYINSTRUCTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.deliveryInstruction</code> attribute. 
	 * @param value the deliveryInstruction
	 */
	public void setDeliveryInstruction(final AbstractOrder item, final String value)
	{
		setDeliveryInstruction( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.deliveryInstructions</code> attribute.
	 * @return the deliveryInstructions
	 */
	public String getDeliveryInstructions(final SessionContext ctx, final Address item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Address.DELIVERYINSTRUCTIONS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.deliveryInstructions</code> attribute.
	 * @return the deliveryInstructions
	 */
	public String getDeliveryInstructions(final Address item)
	{
		return getDeliveryInstructions( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.deliveryInstructions</code> attribute. 
	 * @param value the deliveryInstructions
	 */
	public void setDeliveryInstructions(final SessionContext ctx, final Address item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Address.DELIVERYINSTRUCTIONS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.deliveryInstructions</code> attribute. 
	 * @param value the deliveryInstructions
	 */
	public void setDeliveryInstructions(final Address item, final String value)
	{
		setDeliveryInstructions( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.deliveryOrShippingThreshold</code> attribute.
	 * @return the deliveryOrShippingThreshold
	 */
	public Map<String,String> getAllDeliveryOrShippingThreshold(final SessionContext ctx, final PointOfService item)
	{
		Map<String,String> map = (Map<String,String>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.DELIVERYORSHIPPINGTHRESHOLD);
		return map != null ? map : Collections.EMPTY_MAP;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.deliveryOrShippingThreshold</code> attribute.
	 * @return the deliveryOrShippingThreshold
	 */
	public Map<String,String> getAllDeliveryOrShippingThreshold(final PointOfService item)
	{
		return getAllDeliveryOrShippingThreshold( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.deliveryOrShippingThreshold</code> attribute. 
	 * @param value the deliveryOrShippingThreshold
	 */
	public void setAllDeliveryOrShippingThreshold(final SessionContext ctx, final PointOfService item, final Map<String,String> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.DELIVERYORSHIPPINGTHRESHOLD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.deliveryOrShippingThreshold</code> attribute. 
	 * @param value the deliveryOrShippingThreshold
	 */
	public void setAllDeliveryOrShippingThreshold(final PointOfService item, final Map<String,String> value)
	{
		setAllDeliveryOrShippingThreshold( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StoreLocatorFeature.description</code> attribute.
	 * @return the description
	 */
	public String getDescription(final SessionContext ctx, final StoreLocatorFeature item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedStoreLocatorFeature.getDescription requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, SiteoneCoreConstants.Attributes.StoreLocatorFeature.DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StoreLocatorFeature.description</code> attribute.
	 * @return the description
	 */
	public String getDescription(final StoreLocatorFeature item)
	{
		return getDescription( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StoreLocatorFeature.description</code> attribute. 
	 * @return the localized description
	 */
	public Map<Language,String> getAllDescription(final SessionContext ctx, final StoreLocatorFeature item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.StoreLocatorFeature.DESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StoreLocatorFeature.description</code> attribute. 
	 * @return the localized description
	 */
	public Map<Language,String> getAllDescription(final StoreLocatorFeature item)
	{
		return getAllDescription( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StoreLocatorFeature.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final SessionContext ctx, final StoreLocatorFeature item, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedStoreLocatorFeature.setDescription requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteoneCoreConstants.Attributes.StoreLocatorFeature.DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StoreLocatorFeature.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final StoreLocatorFeature item, final String value)
	{
		setDescription( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StoreLocatorFeature.description</code> attribute. 
	 * @param value the description
	 */
	public void setAllDescription(final SessionContext ctx, final StoreLocatorFeature item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.StoreLocatorFeature.DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StoreLocatorFeature.description</code> attribute. 
	 * @param value the description
	 */
	public void setAllDescription(final StoreLocatorFeature item, final Map<Language,String> value)
	{
		setAllDescription( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.discountAmount</code> attribute.
	 * @return the discountAmount
	 */
	public Double getDiscountAmount(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.DISCOUNTAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.discountAmount</code> attribute.
	 * @return the discountAmount
	 */
	public Double getDiscountAmount(final AbstractOrderEntry item)
	{
		return getDiscountAmount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.discountAmount</code> attribute. 
	 * @return the discountAmount
	 */
	public double getDiscountAmountAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getDiscountAmount( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.discountAmount</code> attribute. 
	 * @return the discountAmount
	 */
	public double getDiscountAmountAsPrimitive(final AbstractOrderEntry item)
	{
		return getDiscountAmountAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.discountAmount</code> attribute. 
	 * @param value the discountAmount
	 */
	public void setDiscountAmount(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.DISCOUNTAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.discountAmount</code> attribute. 
	 * @param value the discountAmount
	 */
	public void setDiscountAmount(final AbstractOrderEntry item, final Double value)
	{
		setDiscountAmount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.discountAmount</code> attribute. 
	 * @param value the discountAmount
	 */
	public void setDiscountAmount(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setDiscountAmount( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.discountAmount</code> attribute. 
	 * @param value the discountAmount
	 */
	public void setDiscountAmount(final AbstractOrderEntry item, final double value)
	{
		setDiscountAmount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractPromotion.discountValue</code> attribute.
	 * @return the discountValue
	 */
	public String getDiscountValue(final SessionContext ctx, final AbstractPromotion item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractPromotion.DISCOUNTVALUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractPromotion.discountValue</code> attribute.
	 * @return the discountValue
	 */
	public String getDiscountValue(final AbstractPromotion item)
	{
		return getDiscountValue( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractPromotion.discountValue</code> attribute. 
	 * @param value the discountValue
	 */
	public void setDiscountValue(final SessionContext ctx, final AbstractPromotion item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractPromotion.DISCOUNTVALUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractPromotion.discountValue</code> attribute. 
	 * @param value the discountValue
	 */
	public void setDiscountValue(final AbstractPromotion item, final String value)
	{
		setDiscountValue( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.displayContents</code> attribute.
	 * @return the displayContents - Determines if content results are displayed in the component.
	 */
	public Boolean isDisplayContents(final SessionContext ctx, final SearchBoxComponent item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.SearchBoxComponent.DISPLAYCONTENTS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.displayContents</code> attribute.
	 * @return the displayContents - Determines if content results are displayed in the component.
	 */
	public Boolean isDisplayContents(final SearchBoxComponent item)
	{
		return isDisplayContents( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.displayContents</code> attribute. 
	 * @return the displayContents - Determines if content results are displayed in the component.
	 */
	public boolean isDisplayContentsAsPrimitive(final SessionContext ctx, final SearchBoxComponent item)
	{
		Boolean value = isDisplayContents( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.displayContents</code> attribute. 
	 * @return the displayContents - Determines if content results are displayed in the component.
	 */
	public boolean isDisplayContentsAsPrimitive(final SearchBoxComponent item)
	{
		return isDisplayContentsAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.displayContents</code> attribute. 
	 * @param value the displayContents - Determines if content results are displayed in the component.
	 */
	public void setDisplayContents(final SessionContext ctx, final SearchBoxComponent item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.SearchBoxComponent.DISPLAYCONTENTS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.displayContents</code> attribute. 
	 * @param value the displayContents - Determines if content results are displayed in the component.
	 */
	public void setDisplayContents(final SearchBoxComponent item, final Boolean value)
	{
		setDisplayContents( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.displayContents</code> attribute. 
	 * @param value the displayContents - Determines if content results are displayed in the component.
	 */
	public void setDisplayContents(final SessionContext ctx, final SearchBoxComponent item, final boolean value)
	{
		setDisplayContents( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.displayContents</code> attribute. 
	 * @param value the displayContents - Determines if content results are displayed in the component.
	 */
	public void setDisplayContents(final SearchBoxComponent item, final boolean value)
	{
		setDisplayContents( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.distributedBranches</code> attribute.
	 * @return the distributedBranches
	 */
	public Set<PointOfService> getDistributedBranches(final SessionContext ctx, final PointOfService item)
	{
		final List<PointOfService> items = item.getLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2DISTRIBUTEDCENTERRELATION,
			"PointOfService",
			null,
			false,
			false
		);
		return new LinkedHashSet<PointOfService>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.distributedBranches</code> attribute.
	 * @return the distributedBranches
	 */
	public Set<PointOfService> getDistributedBranches(final PointOfService item)
	{
		return getDistributedBranches( getSession().getSessionContext(), item );
	}
	
	public long getDistributedBranchesCount(final SessionContext ctx, final PointOfService item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2DISTRIBUTEDCENTERRELATION,
			"PointOfService",
			null
		);
	}
	
	public long getDistributedBranchesCount(final PointOfService item)
	{
		return getDistributedBranchesCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.distributedBranches</code> attribute. 
	 * @param value the distributedBranches
	 */
	public void setDistributedBranches(final SessionContext ctx, final PointOfService item, final Set<PointOfService> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2DISTRIBUTEDCENTERRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2DISTRIBUTEDCENTERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.distributedBranches</code> attribute. 
	 * @param value the distributedBranches
	 */
	public void setDistributedBranches(final PointOfService item, final Set<PointOfService> value)
	{
		setDistributedBranches( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to distributedBranches. 
	 * @param value the item to add to distributedBranches
	 */
	public void addToDistributedBranches(final SessionContext ctx, final PointOfService item, final PointOfService value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2DISTRIBUTEDCENTERRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2DISTRIBUTEDCENTERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to distributedBranches. 
	 * @param value the item to add to distributedBranches
	 */
	public void addToDistributedBranches(final PointOfService item, final PointOfService value)
	{
		addToDistributedBranches( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from distributedBranches. 
	 * @param value the item to remove from distributedBranches
	 */
	public void removeFromDistributedBranches(final SessionContext ctx, final PointOfService item, final PointOfService value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2DISTRIBUTEDCENTERRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2DISTRIBUTEDCENTERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from distributedBranches. 
	 * @param value the item to remove from distributedBranches
	 */
	public void removeFromDistributedBranches(final PointOfService item, final PointOfService value)
	{
		removeFromDistributedBranches( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.division</code> attribute.
	 * @return the division
	 */
	public OrgUnit getDivision(final SessionContext ctx, final PointOfService item)
	{
		return (OrgUnit)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.DIVISION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.division</code> attribute.
	 * @return the division
	 */
	public OrgUnit getDivision(final PointOfService item)
	{
		return getDivision( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.division</code> attribute. 
	 * @param value the division
	 */
	public void setDivision(final SessionContext ctx, final PointOfService item, final OrgUnit value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.DIVISION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.division</code> attribute. 
	 * @param value the division
	 */
	public void setDivision(final PointOfService item, final OrgUnit value)
	{
		setDivision( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.division</code> attribute.
	 * @return the division - Division for US and CA
	 */
	public OrgUnit getDivision(final SessionContext ctx, final B2BUnit item)
	{
		return (OrgUnit)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.DIVISION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.division</code> attribute.
	 * @return the division - Division for US and CA
	 */
	public OrgUnit getDivision(final B2BUnit item)
	{
		return getDivision( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.division</code> attribute. 
	 * @param value the division - Division for US and CA
	 */
	public void setDivision(final SessionContext ctx, final B2BUnit item, final OrgUnit value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.DIVISION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.division</code> attribute. 
	 * @param value the division - Division for US and CA
	 */
	public void setDivision(final B2BUnit item, final OrgUnit value)
	{
		setDivision( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.division</code> attribute.
	 * @return the division
	 */
	public OrgUnit getDivision(final SessionContext ctx, final Product item)
	{
		return (OrgUnit)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.DIVISION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.division</code> attribute.
	 * @return the division
	 */
	public OrgUnit getDivision(final Product item)
	{
		return getDivision( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.division</code> attribute. 
	 * @param value the division
	 */
	public void setDivision(final SessionContext ctx, final Product item, final OrgUnit value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.DIVISION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.division</code> attribute. 
	 * @param value the division
	 */
	public void setDivision(final Product item, final OrgUnit value)
	{
		setDivision( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.dunsScore</code> attribute.
	 * @return the dunsScore
	 */
	public Integer getDunsScore(final SessionContext ctx, final B2BUnit item)
	{
		return (Integer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.DUNSSCORE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.dunsScore</code> attribute.
	 * @return the dunsScore
	 */
	public Integer getDunsScore(final B2BUnit item)
	{
		return getDunsScore( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.dunsScore</code> attribute. 
	 * @return the dunsScore
	 */
	public int getDunsScoreAsPrimitive(final SessionContext ctx, final B2BUnit item)
	{
		Integer value = getDunsScore( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.dunsScore</code> attribute. 
	 * @return the dunsScore
	 */
	public int getDunsScoreAsPrimitive(final B2BUnit item)
	{
		return getDunsScoreAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.dunsScore</code> attribute. 
	 * @param value the dunsScore
	 */
	public void setDunsScore(final SessionContext ctx, final B2BUnit item, final Integer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.DUNSSCORE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.dunsScore</code> attribute. 
	 * @param value the dunsScore
	 */
	public void setDunsScore(final B2BUnit item, final Integer value)
	{
		setDunsScore( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.dunsScore</code> attribute. 
	 * @param value the dunsScore
	 */
	public void setDunsScore(final SessionContext ctx, final B2BUnit item, final int value)
	{
		setDunsScore( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.dunsScore</code> attribute. 
	 * @param value the dunsScore
	 */
	public void setDunsScore(final B2BUnit item, final int value)
	{
		setDunsScore( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.eeee</code> attribute.
	 * @return the eeee
	 */
	public Boolean isEeee(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.EEEE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.eeee</code> attribute.
	 * @return the eeee
	 */
	public Boolean isEeee(final Product item)
	{
		return isEeee( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.eeee</code> attribute. 
	 * @return the eeee
	 */
	public boolean isEeeeAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isEeee( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.eeee</code> attribute. 
	 * @return the eeee
	 */
	public boolean isEeeeAsPrimitive(final Product item)
	{
		return isEeeeAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.eeee</code> attribute. 
	 * @param value the eeee
	 */
	public void setEeee(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.EEEE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.eeee</code> attribute. 
	 * @param value the eeee
	 */
	public void setEeee(final Product item, final Boolean value)
	{
		setEeee( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.eeee</code> attribute. 
	 * @param value the eeee
	 */
	public void setEeee(final SessionContext ctx, final Product item, final boolean value)
	{
		setEeee( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.eeee</code> attribute. 
	 * @param value the eeee
	 */
	public void setEeee(final Product item, final boolean value)
	{
		setEeee( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.enableAddModifyDeliveryAddress</code> attribute.
	 * @return the enableAddModifyDeliveryAddress
	 */
	public Boolean isEnableAddModifyDeliveryAddress(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ENABLEADDMODIFYDELIVERYADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.enableAddModifyDeliveryAddress</code> attribute.
	 * @return the enableAddModifyDeliveryAddress
	 */
	public Boolean isEnableAddModifyDeliveryAddress(final B2BCustomer item)
	{
		return isEnableAddModifyDeliveryAddress( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.enableAddModifyDeliveryAddress</code> attribute. 
	 * @return the enableAddModifyDeliveryAddress
	 */
	public boolean isEnableAddModifyDeliveryAddressAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isEnableAddModifyDeliveryAddress( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.enableAddModifyDeliveryAddress</code> attribute. 
	 * @return the enableAddModifyDeliveryAddress
	 */
	public boolean isEnableAddModifyDeliveryAddressAsPrimitive(final B2BCustomer item)
	{
		return isEnableAddModifyDeliveryAddressAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.enableAddModifyDeliveryAddress</code> attribute. 
	 * @param value the enableAddModifyDeliveryAddress
	 */
	public void setEnableAddModifyDeliveryAddress(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ENABLEADDMODIFYDELIVERYADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.enableAddModifyDeliveryAddress</code> attribute. 
	 * @param value the enableAddModifyDeliveryAddress
	 */
	public void setEnableAddModifyDeliveryAddress(final B2BCustomer item, final Boolean value)
	{
		setEnableAddModifyDeliveryAddress( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.enableAddModifyDeliveryAddress</code> attribute. 
	 * @param value the enableAddModifyDeliveryAddress
	 */
	public void setEnableAddModifyDeliveryAddress(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setEnableAddModifyDeliveryAddress( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.enableAddModifyDeliveryAddress</code> attribute. 
	 * @param value the enableAddModifyDeliveryAddress
	 */
	public void setEnableAddModifyDeliveryAddress(final B2BCustomer item, final boolean value)
	{
		setEnableAddModifyDeliveryAddress( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.enableOnlineFulfillment</code> attribute.
	 * @return the enableOnlineFulfillment
	 */
	public Boolean isEnableOnlineFulfillment(final SessionContext ctx, final PointOfService item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.ENABLEONLINEFULFILLMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.enableOnlineFulfillment</code> attribute.
	 * @return the enableOnlineFulfillment
	 */
	public Boolean isEnableOnlineFulfillment(final PointOfService item)
	{
		return isEnableOnlineFulfillment( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.enableOnlineFulfillment</code> attribute. 
	 * @return the enableOnlineFulfillment
	 */
	public boolean isEnableOnlineFulfillmentAsPrimitive(final SessionContext ctx, final PointOfService item)
	{
		Boolean value = isEnableOnlineFulfillment( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.enableOnlineFulfillment</code> attribute. 
	 * @return the enableOnlineFulfillment
	 */
	public boolean isEnableOnlineFulfillmentAsPrimitive(final PointOfService item)
	{
		return isEnableOnlineFulfillmentAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.enableOnlineFulfillment</code> attribute. 
	 * @param value the enableOnlineFulfillment
	 */
	public void setEnableOnlineFulfillment(final SessionContext ctx, final PointOfService item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.ENABLEONLINEFULFILLMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.enableOnlineFulfillment</code> attribute. 
	 * @param value the enableOnlineFulfillment
	 */
	public void setEnableOnlineFulfillment(final PointOfService item, final Boolean value)
	{
		setEnableOnlineFulfillment( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.enableOnlineFulfillment</code> attribute. 
	 * @param value the enableOnlineFulfillment
	 */
	public void setEnableOnlineFulfillment(final SessionContext ctx, final PointOfService item, final boolean value)
	{
		setEnableOnlineFulfillment( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.enableOnlineFulfillment</code> attribute. 
	 * @param value the enableOnlineFulfillment
	 */
	public void setEnableOnlineFulfillment(final PointOfService item, final boolean value)
	{
		setEnableOnlineFulfillment( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeekdayOpeningDay.endDate</code> attribute.
	 * @return the endDate
	 */
	public Date getEndDate(final SessionContext ctx, final WeekdayOpeningDay item)
	{
		return (Date)item.getProperty( ctx, SiteoneCoreConstants.Attributes.WeekdayOpeningDay.ENDDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeekdayOpeningDay.endDate</code> attribute.
	 * @return the endDate
	 */
	public Date getEndDate(final WeekdayOpeningDay item)
	{
		return getEndDate( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeekdayOpeningDay.endDate</code> attribute. 
	 * @param value the endDate
	 */
	public void setEndDate(final SessionContext ctx, final WeekdayOpeningDay item, final Date value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.WeekdayOpeningDay.ENDDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeekdayOpeningDay.endDate</code> attribute. 
	 * @param value the endDate
	 */
	public void setEndDate(final WeekdayOpeningDay item, final Date value)
	{
		setEndDate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ConsignmentEntry.entryNumber</code> attribute.
	 * @return the entryNumber
	 */
	public String getEntryNumber(final SessionContext ctx, final ConsignmentEntry item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.ConsignmentEntry.ENTRYNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ConsignmentEntry.entryNumber</code> attribute.
	 * @return the entryNumber
	 */
	public String getEntryNumber(final ConsignmentEntry item)
	{
		return getEntryNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ConsignmentEntry.entryNumber</code> attribute. 
	 * @param value the entryNumber
	 */
	public void setEntryNumber(final SessionContext ctx, final ConsignmentEntry item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.ConsignmentEntry.ENTRYNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ConsignmentEntry.entryNumber</code> attribute. 
	 * @param value the entryNumber
	 */
	public void setEntryNumber(final ConsignmentEntry item, final String value)
	{
		setEntryNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.excludeBranches</code> attribute.
	 * @return the excludeBranches
	 */
	public String getExcludeBranches(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.EXCLUDEBRANCHES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.excludeBranches</code> attribute.
	 * @return the excludeBranches
	 */
	public String getExcludeBranches(final PointOfService item)
	{
		return getExcludeBranches( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.excludeBranches</code> attribute. 
	 * @param value the excludeBranches
	 */
	public void setExcludeBranches(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.EXCLUDEBRANCHES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.excludeBranches</code> attribute. 
	 * @param value the excludeBranches
	 */
	public void setExcludeBranches(final PointOfService item, final String value)
	{
		setExcludeBranches( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.exemptDeliveryFee</code> attribute.
	 * @return the exemptDeliveryFee
	 */
	public Boolean isExemptDeliveryFee(final SessionContext ctx, final B2BUnit item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.EXEMPTDELIVERYFEE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.exemptDeliveryFee</code> attribute.
	 * @return the exemptDeliveryFee
	 */
	public Boolean isExemptDeliveryFee(final B2BUnit item)
	{
		return isExemptDeliveryFee( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.exemptDeliveryFee</code> attribute. 
	 * @return the exemptDeliveryFee
	 */
	public boolean isExemptDeliveryFeeAsPrimitive(final SessionContext ctx, final B2BUnit item)
	{
		Boolean value = isExemptDeliveryFee( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.exemptDeliveryFee</code> attribute. 
	 * @return the exemptDeliveryFee
	 */
	public boolean isExemptDeliveryFeeAsPrimitive(final B2BUnit item)
	{
		return isExemptDeliveryFeeAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.exemptDeliveryFee</code> attribute. 
	 * @param value the exemptDeliveryFee
	 */
	public void setExemptDeliveryFee(final SessionContext ctx, final B2BUnit item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.EXEMPTDELIVERYFEE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.exemptDeliveryFee</code> attribute. 
	 * @param value the exemptDeliveryFee
	 */
	public void setExemptDeliveryFee(final B2BUnit item, final Boolean value)
	{
		setExemptDeliveryFee( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.exemptDeliveryFee</code> attribute. 
	 * @param value the exemptDeliveryFee
	 */
	public void setExemptDeliveryFee(final SessionContext ctx, final B2BUnit item, final boolean value)
	{
		setExemptDeliveryFee( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.exemptDeliveryFee</code> attribute. 
	 * @param value the exemptDeliveryFee
	 */
	public void setExemptDeliveryFee(final B2BUnit item, final boolean value)
	{
		setExemptDeliveryFee( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ForgottenPasswordProcess.expirationTimeInSeconds</code> attribute.
	 * @return the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public Long getExpirationTimeInSeconds(final SessionContext ctx, final ForgottenPasswordProcess item)
	{
		return (Long)item.getProperty( ctx, SiteoneCoreConstants.Attributes.ForgottenPasswordProcess.EXPIRATIONTIMEINSECONDS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ForgottenPasswordProcess.expirationTimeInSeconds</code> attribute.
	 * @return the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public Long getExpirationTimeInSeconds(final ForgottenPasswordProcess item)
	{
		return getExpirationTimeInSeconds( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ForgottenPasswordProcess.expirationTimeInSeconds</code> attribute. 
	 * @return the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public long getExpirationTimeInSecondsAsPrimitive(final SessionContext ctx, final ForgottenPasswordProcess item)
	{
		Long value = getExpirationTimeInSeconds( ctx,item );
		return value != null ? value.longValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ForgottenPasswordProcess.expirationTimeInSeconds</code> attribute. 
	 * @return the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public long getExpirationTimeInSecondsAsPrimitive(final ForgottenPasswordProcess item)
	{
		return getExpirationTimeInSecondsAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ForgottenPasswordProcess.expirationTimeInSeconds</code> attribute. 
	 * @param value the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public void setExpirationTimeInSeconds(final SessionContext ctx, final ForgottenPasswordProcess item, final Long value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.ForgottenPasswordProcess.EXPIRATIONTIMEINSECONDS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ForgottenPasswordProcess.expirationTimeInSeconds</code> attribute. 
	 * @param value the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public void setExpirationTimeInSeconds(final ForgottenPasswordProcess item, final Long value)
	{
		setExpirationTimeInSeconds( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ForgottenPasswordProcess.expirationTimeInSeconds</code> attribute. 
	 * @param value the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public void setExpirationTimeInSeconds(final SessionContext ctx, final ForgottenPasswordProcess item, final long value)
	{
		setExpirationTimeInSeconds( ctx, item, Long.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ForgottenPasswordProcess.expirationTimeInSeconds</code> attribute. 
	 * @param value the expirationTimeInSeconds - Attribute contains expiration time that is used in this process.
	 */
	public void setExpirationTimeInSeconds(final ForgottenPasswordProcess item, final long value)
	{
		setExpirationTimeInSeconds( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.extendedNotice</code> attribute.
	 * @return the extendedNotice - Intended to store a copyright notice or other text to be displayed in the footer with length of more than 255 characters long
	 */
	public String getExtendedNotice(final SessionContext ctx, final FooterNavigationComponent item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedFooterNavigationComponent.getExtendedNotice requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, SiteoneCoreConstants.Attributes.FooterNavigationComponent.EXTENDEDNOTICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.extendedNotice</code> attribute.
	 * @return the extendedNotice - Intended to store a copyright notice or other text to be displayed in the footer with length of more than 255 characters long
	 */
	public String getExtendedNotice(final FooterNavigationComponent item)
	{
		return getExtendedNotice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.extendedNotice</code> attribute. 
	 * @return the localized extendedNotice - Intended to store a copyright notice or other text to be displayed in the footer with length of more than 255 characters long
	 */
	public Map<Language,String> getAllExtendedNotice(final SessionContext ctx, final FooterNavigationComponent item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.FooterNavigationComponent.EXTENDEDNOTICE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.extendedNotice</code> attribute. 
	 * @return the localized extendedNotice - Intended to store a copyright notice or other text to be displayed in the footer with length of more than 255 characters long
	 */
	public Map<Language,String> getAllExtendedNotice(final FooterNavigationComponent item)
	{
		return getAllExtendedNotice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.extendedNotice</code> attribute. 
	 * @param value the extendedNotice - Intended to store a copyright notice or other text to be displayed in the footer with length of more than 255 characters long
	 */
	public void setExtendedNotice(final SessionContext ctx, final FooterNavigationComponent item, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedFooterNavigationComponent.setExtendedNotice requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteoneCoreConstants.Attributes.FooterNavigationComponent.EXTENDEDNOTICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.extendedNotice</code> attribute. 
	 * @param value the extendedNotice - Intended to store a copyright notice or other text to be displayed in the footer with length of more than 255 characters long
	 */
	public void setExtendedNotice(final FooterNavigationComponent item, final String value)
	{
		setExtendedNotice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.extendedNotice</code> attribute. 
	 * @param value the extendedNotice - Intended to store a copyright notice or other text to be displayed in the footer with length of more than 255 characters long
	 */
	public void setAllExtendedNotice(final SessionContext ctx, final FooterNavigationComponent item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.FooterNavigationComponent.EXTENDEDNOTICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.extendedNotice</code> attribute. 
	 * @param value the extendedNotice - Intended to store a copyright notice or other text to be displayed in the footer with length of more than 255 characters long
	 */
	public void setAllExtendedNotice(final FooterNavigationComponent item, final Map<Language,String> value)
	{
		setAllExtendedNotice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.externalSystemId</code> attribute.
	 * @return the externalSystemId
	 */
	public String getExternalSystemId(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.EXTERNALSYSTEMID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.externalSystemId</code> attribute.
	 * @return the externalSystemId
	 */
	public String getExternalSystemId(final AbstractOrder item)
	{
		return getExternalSystemId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.externalSystemId</code> attribute. 
	 * @param value the externalSystemId
	 */
	public void setExternalSystemId(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.EXTERNALSYSTEMID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.externalSystemId</code> attribute. 
	 * @param value the externalSystemId
	 */
	public void setExternalSystemId(final AbstractOrder item, final String value)
	{
		setExternalSystemId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.featuredArticle</code> attribute.
	 * @return the featuredArticle
	 */
	public String getFeaturedArticle(final SessionContext ctx, final ContentPage item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.ContentPage.FEATUREDARTICLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.featuredArticle</code> attribute.
	 * @return the featuredArticle
	 */
	public String getFeaturedArticle(final ContentPage item)
	{
		return getFeaturedArticle( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.featuredArticle</code> attribute. 
	 * @param value the featuredArticle
	 */
	public void setFeaturedArticle(final SessionContext ctx, final ContentPage item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.ContentPage.FEATUREDARTICLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.featuredArticle</code> attribute. 
	 * @param value the featuredArticle
	 */
	public void setFeaturedArticle(final ContentPage item, final String value)
	{
		setFeaturedArticle( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.FIRSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.firstName</code> attribute.
	 * @return the firstName
	 */
	public String getFirstName(final B2BCustomer item)
	{
		return getFirstName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.FIRSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.firstName</code> attribute. 
	 * @param value the firstName
	 */
	public void setFirstName(final B2BCustomer item, final String value)
	{
		setFirstName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.forceInStock</code> attribute.
	 * @return the forceInStock
	 */
	public Boolean isForceInStock(final SessionContext ctx, final StockLevel item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.StockLevel.FORCEINSTOCK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.forceInStock</code> attribute.
	 * @return the forceInStock
	 */
	public Boolean isForceInStock(final StockLevel item)
	{
		return isForceInStock( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.forceInStock</code> attribute. 
	 * @return the forceInStock
	 */
	public boolean isForceInStockAsPrimitive(final SessionContext ctx, final StockLevel item)
	{
		Boolean value = isForceInStock( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.forceInStock</code> attribute. 
	 * @return the forceInStock
	 */
	public boolean isForceInStockAsPrimitive(final StockLevel item)
	{
		return isForceInStockAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.forceInStock</code> attribute. 
	 * @param value the forceInStock
	 */
	public void setForceInStock(final SessionContext ctx, final StockLevel item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.StockLevel.FORCEINSTOCK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.forceInStock</code> attribute. 
	 * @param value the forceInStock
	 */
	public void setForceInStock(final StockLevel item, final Boolean value)
	{
		setForceInStock( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.forceInStock</code> attribute. 
	 * @param value the forceInStock
	 */
	public void setForceInStock(final SessionContext ctx, final StockLevel item, final boolean value)
	{
		setForceInStock( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.forceInStock</code> attribute. 
	 * @param value the forceInStock
	 */
	public void setForceInStock(final StockLevel item, final boolean value)
	{
		setForceInStock( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.forceStockRespectNearby</code> attribute.
	 * @return the forceStockRespectNearby
	 */
	public Boolean isForceStockRespectNearby(final SessionContext ctx, final StockLevel item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.StockLevel.FORCESTOCKRESPECTNEARBY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.forceStockRespectNearby</code> attribute.
	 * @return the forceStockRespectNearby
	 */
	public Boolean isForceStockRespectNearby(final StockLevel item)
	{
		return isForceStockRespectNearby( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.forceStockRespectNearby</code> attribute. 
	 * @return the forceStockRespectNearby
	 */
	public boolean isForceStockRespectNearbyAsPrimitive(final SessionContext ctx, final StockLevel item)
	{
		Boolean value = isForceStockRespectNearby( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.forceStockRespectNearby</code> attribute. 
	 * @return the forceStockRespectNearby
	 */
	public boolean isForceStockRespectNearbyAsPrimitive(final StockLevel item)
	{
		return isForceStockRespectNearbyAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.forceStockRespectNearby</code> attribute. 
	 * @param value the forceStockRespectNearby
	 */
	public void setForceStockRespectNearby(final SessionContext ctx, final StockLevel item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.StockLevel.FORCESTOCKRESPECTNEARBY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.forceStockRespectNearby</code> attribute. 
	 * @param value the forceStockRespectNearby
	 */
	public void setForceStockRespectNearby(final StockLevel item, final Boolean value)
	{
		setForceStockRespectNearby( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.forceStockRespectNearby</code> attribute. 
	 * @param value the forceStockRespectNearby
	 */
	public void setForceStockRespectNearby(final SessionContext ctx, final StockLevel item, final boolean value)
	{
		setForceStockRespectNearby( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.forceStockRespectNearby</code> attribute. 
	 * @param value the forceStockRespectNearby
	 */
	public void setForceStockRespectNearby(final StockLevel item, final boolean value)
	{
		setForceStockRespectNearby( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.freight</code> attribute.
	 * @return the freight
	 */
	public String getFreight(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.FREIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.freight</code> attribute.
	 * @return the freight
	 */
	public String getFreight(final AbstractOrder item)
	{
		return getFreight( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.freight</code> attribute. 
	 * @param value the freight
	 */
	public void setFreight(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.FREIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.freight</code> attribute. 
	 * @param value the freight
	 */
	public void setFreight(final AbstractOrder item, final String value)
	{
		setFreight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.freight</code> attribute.
	 * @return the freight
	 */
	public String getFreight(final SessionContext ctx, final Consignment item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Consignment.FREIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.freight</code> attribute.
	 * @return the freight
	 */
	public String getFreight(final Consignment item)
	{
		return getFreight( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.freight</code> attribute. 
	 * @param value the freight
	 */
	public void setFreight(final SessionContext ctx, final Consignment item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Consignment.FREIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.freight</code> attribute. 
	 * @param value the freight
	 */
	public void setFreight(final Consignment item, final String value)
	{
		setFreight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.fullfilledStoreType</code> attribute.
	 * @return the fullfilledStoreType
	 */
	public String getFullfilledStoreType(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.FULLFILLEDSTORETYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.fullfilledStoreType</code> attribute.
	 * @return the fullfilledStoreType
	 */
	public String getFullfilledStoreType(final AbstractOrderEntry item)
	{
		return getFullfilledStoreType( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.fullfilledStoreType</code> attribute. 
	 * @param value the fullfilledStoreType
	 */
	public void setFullfilledStoreType(final SessionContext ctx, final AbstractOrderEntry item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.FULLFILLEDSTORETYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.fullfilledStoreType</code> attribute. 
	 * @param value the fullfilledStoreType
	 */
	public void setFullfilledStoreType(final AbstractOrderEntry item, final String value)
	{
		setFullfilledStoreType( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.fullfillmentStoreId</code> attribute.
	 * @return the fullfillmentStoreId
	 */
	public String getFullfillmentStoreId(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.FULLFILLMENTSTOREID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.fullfillmentStoreId</code> attribute.
	 * @return the fullfillmentStoreId
	 */
	public String getFullfillmentStoreId(final AbstractOrderEntry item)
	{
		return getFullfillmentStoreId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.fullfillmentStoreId</code> attribute. 
	 * @param value the fullfillmentStoreId
	 */
	public void setFullfillmentStoreId(final SessionContext ctx, final AbstractOrderEntry item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.FULLFILLMENTSTOREID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.fullfillmentStoreId</code> attribute. 
	 * @param value the fullfillmentStoreId
	 */
	public void setFullfillmentStoreId(final AbstractOrderEntry item, final String value)
	{
		setFullfillmentStoreId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.fullfilmentStatus</code> attribute.
	 * @return the fullfilmentStatus
	 */
	public String getFullfilmentStatus(final SessionContext ctx, final Consignment item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Consignment.FULLFILMENTSTATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.fullfilmentStatus</code> attribute.
	 * @return the fullfilmentStatus
	 */
	public String getFullfilmentStatus(final Consignment item)
	{
		return getFullfilmentStatus( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.fullfilmentStatus</code> attribute. 
	 * @param value the fullfilmentStatus
	 */
	public void setFullfilmentStatus(final SessionContext ctx, final Consignment item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Consignment.FULLFILMENTSTATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.fullfilmentStatus</code> attribute. 
	 * @param value the fullfilmentStatus
	 */
	public void setFullfilmentStatus(final Consignment item, final String value)
	{
		setFullfilmentStatus( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.fullPagePath</code> attribute.
	 * @return the fullPagePath
	 */
	public String getFullPagePath(final SessionContext ctx, final ContentPage item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedContentPage.getFullPagePath requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, SiteoneCoreConstants.Attributes.ContentPage.FULLPAGEPATH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.fullPagePath</code> attribute.
	 * @return the fullPagePath
	 */
	public String getFullPagePath(final ContentPage item)
	{
		return getFullPagePath( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.fullPagePath</code> attribute. 
	 * @return the localized fullPagePath
	 */
	public Map<Language,String> getAllFullPagePath(final SessionContext ctx, final ContentPage item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.ContentPage.FULLPAGEPATH,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.fullPagePath</code> attribute. 
	 * @return the localized fullPagePath
	 */
	public Map<Language,String> getAllFullPagePath(final ContentPage item)
	{
		return getAllFullPagePath( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.fullPagePath</code> attribute. 
	 * @param value the fullPagePath
	 */
	public void setFullPagePath(final SessionContext ctx, final ContentPage item, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedContentPage.setFullPagePath requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteoneCoreConstants.Attributes.ContentPage.FULLPAGEPATH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.fullPagePath</code> attribute. 
	 * @param value the fullPagePath
	 */
	public void setFullPagePath(final ContentPage item, final String value)
	{
		setFullPagePath( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.fullPagePath</code> attribute. 
	 * @param value the fullPagePath
	 */
	public void setAllFullPagePath(final SessionContext ctx, final ContentPage item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.ContentPage.FULLPAGEPATH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.fullPagePath</code> attribute. 
	 * @param value the fullPagePath
	 */
	public void setAllFullPagePath(final ContentPage item, final Map<Language,String> value)
	{
		setAllFullPagePath( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.geoCode</code> attribute.
	 * @return the geoCode
	 */
	public String getGeoCode(final SessionContext ctx, final Address item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Address.GEOCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.geoCode</code> attribute.
	 * @return the geoCode
	 */
	public String getGeoCode(final Address item)
	{
		return getGeoCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.geoCode</code> attribute. 
	 * @param value the geoCode
	 */
	public void setGeoCode(final SessionContext ctx, final Address item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Address.GEOCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.geoCode</code> attribute. 
	 * @param value the geoCode
	 */
	public void setGeoCode(final Address item, final String value)
	{
		setGeoCode( getSession().getSessionContext(), item, value );
	}
	
	@Override
	public String getName()
	{
		return SiteoneCoreConstants.EXTENSIONNAME;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.guestContactPerson</code> attribute.
	 * @return the guestContactPerson
	 */
	public Customer getGuestContactPerson(final SessionContext ctx, final AbstractOrder item)
	{
		return (Customer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.GUESTCONTACTPERSON);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.guestContactPerson</code> attribute.
	 * @return the guestContactPerson
	 */
	public Customer getGuestContactPerson(final AbstractOrder item)
	{
		return getGuestContactPerson( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.guestContactPerson</code> attribute. 
	 * @param value the guestContactPerson
	 */
	public void setGuestContactPerson(final SessionContext ctx, final AbstractOrder item, final Customer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.GUESTCONTACTPERSON,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.guestContactPerson</code> attribute. 
	 * @param value the guestContactPerson
	 */
	public void setGuestContactPerson(final AbstractOrder item, final Customer value)
	{
		setGuestContactPerson( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.guestDeliveryContactPerson</code> attribute.
	 * @return the guestDeliveryContactPerson
	 */
	public Customer getGuestDeliveryContactPerson(final SessionContext ctx, final AbstractOrder item)
	{
		return (Customer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.GUESTDELIVERYCONTACTPERSON);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.guestDeliveryContactPerson</code> attribute.
	 * @return the guestDeliveryContactPerson
	 */
	public Customer getGuestDeliveryContactPerson(final AbstractOrder item)
	{
		return getGuestDeliveryContactPerson( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.guestDeliveryContactPerson</code> attribute. 
	 * @param value the guestDeliveryContactPerson
	 */
	public void setGuestDeliveryContactPerson(final SessionContext ctx, final AbstractOrder item, final Customer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.GUESTDELIVERYCONTACTPERSON,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.guestDeliveryContactPerson</code> attribute. 
	 * @param value the guestDeliveryContactPerson
	 */
	public void setGuestDeliveryContactPerson(final AbstractOrder item, final Customer value)
	{
		setGuestDeliveryContactPerson( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.guestPickupAddress</code> attribute.
	 * @return the guestPickupAddress
	 */
	public Address getGuestPickupAddress(final SessionContext ctx, final AbstractOrder item)
	{
		return (Address)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.GUESTPICKUPADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.guestPickupAddress</code> attribute.
	 * @return the guestPickupAddress
	 */
	public Address getGuestPickupAddress(final AbstractOrder item)
	{
		return getGuestPickupAddress( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.guestPickupAddress</code> attribute. 
	 * @param value the guestPickupAddress
	 */
	public void setGuestPickupAddress(final SessionContext ctx, final AbstractOrder item, final Address value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.GUESTPICKUPADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.guestPickupAddress</code> attribute. 
	 * @param value the guestPickupAddress
	 */
	public void setGuestPickupAddress(final AbstractOrder item, final Address value)
	{
		setGuestPickupAddress( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.guestShippingContactPerson</code> attribute.
	 * @return the guestShippingContactPerson
	 */
	public Customer getGuestShippingContactPerson(final SessionContext ctx, final AbstractOrder item)
	{
		return (Customer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.GUESTSHIPPINGCONTACTPERSON);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.guestShippingContactPerson</code> attribute.
	 * @return the guestShippingContactPerson
	 */
	public Customer getGuestShippingContactPerson(final AbstractOrder item)
	{
		return getGuestShippingContactPerson( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.guestShippingContactPerson</code> attribute. 
	 * @param value the guestShippingContactPerson
	 */
	public void setGuestShippingContactPerson(final SessionContext ctx, final AbstractOrder item, final Customer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.GUESTSHIPPINGCONTACTPERSON,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.guestShippingContactPerson</code> attribute. 
	 * @param value the guestShippingContactPerson
	 */
	public void setGuestShippingContactPerson(final AbstractOrder item, final Customer value)
	{
		setGuestShippingContactPerson( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.guid</code> attribute.
	 * @return the guid
	 */
	public String getGuid(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.GUID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.guid</code> attribute.
	 * @return the guid
	 */
	public String getGuid(final B2BCustomer item)
	{
		return getGuid( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.guid</code> attribute. 
	 * @param value the guid
	 */
	public void setGuid(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.GUID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.guid</code> attribute. 
	 * @param value the guid
	 */
	public void setGuid(final B2BCustomer item, final String value)
	{
		setGuid( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.guid</code> attribute.
	 * @return the guid
	 */
	public String getGuid(final SessionContext ctx, final Address item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Address.GUID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.guid</code> attribute.
	 * @return the guid
	 */
	public String getGuid(final Address item)
	{
		return getGuid( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.guid</code> attribute. 
	 * @param value the guid
	 */
	public void setGuid(final SessionContext ctx, final Address item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Address.GUID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.guid</code> attribute. 
	 * @param value the guid
	 */
	public void setGuid(final Address item, final String value)
	{
		setGuid( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.guid</code> attribute.
	 * @return the guid - guid
	 */
	public String getGuid(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.GUID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.guid</code> attribute.
	 * @return the guid - guid
	 */
	public String getGuid(final B2BUnit item)
	{
		return getGuid( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.guid</code> attribute. 
	 * @param value the guid - guid
	 */
	public void setGuid(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.GUID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.guid</code> attribute. 
	 * @param value the guid - guid
	 */
	public void setGuid(final B2BUnit item, final String value)
	{
		setGuid( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.hideCSP</code> attribute.
	 * @return the hideCSP
	 */
	public Boolean isHideCSP(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.HIDECSP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.hideCSP</code> attribute.
	 * @return the hideCSP
	 */
	public Boolean isHideCSP(final Product item)
	{
		return isHideCSP( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.hideCSP</code> attribute. 
	 * @return the hideCSP
	 */
	public boolean isHideCSPAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isHideCSP( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.hideCSP</code> attribute. 
	 * @return the hideCSP
	 */
	public boolean isHideCSPAsPrimitive(final Product item)
	{
		return isHideCSPAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.hideCSP</code> attribute. 
	 * @param value the hideCSP
	 */
	public void setHideCSP(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.HIDECSP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.hideCSP</code> attribute. 
	 * @param value the hideCSP
	 */
	public void setHideCSP(final Product item, final Boolean value)
	{
		setHideCSP( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.hideCSP</code> attribute. 
	 * @param value the hideCSP
	 */
	public void setHideCSP(final SessionContext ctx, final Product item, final boolean value)
	{
		setHideCSP( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.hideCSP</code> attribute. 
	 * @param value the hideCSP
	 */
	public void setHideCSP(final Product item, final boolean value)
	{
		setHideCSP( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.hideList</code> attribute.
	 * @return the hideList
	 */
	public Boolean isHideList(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.HIDELIST);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.hideList</code> attribute.
	 * @return the hideList
	 */
	public Boolean isHideList(final Product item)
	{
		return isHideList( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.hideList</code> attribute. 
	 * @return the hideList
	 */
	public boolean isHideListAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isHideList( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.hideList</code> attribute. 
	 * @return the hideList
	 */
	public boolean isHideListAsPrimitive(final Product item)
	{
		return isHideListAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.hideList</code> attribute. 
	 * @param value the hideList
	 */
	public void setHideList(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.HIDELIST,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.hideList</code> attribute. 
	 * @param value the hideList
	 */
	public void setHideList(final Product item, final Boolean value)
	{
		setHideList( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.hideList</code> attribute. 
	 * @param value the hideList
	 */
	public void setHideList(final SessionContext ctx, final Product item, final boolean value)
	{
		setHideList( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.hideList</code> attribute. 
	 * @param value the hideList
	 */
	public void setHideList(final Product item, final boolean value)
	{
		setHideList( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.highlight</code> attribute.
	 * @return the highlight
	 */
	public Boolean isHighlight(final SessionContext ctx, final ClassAttributeAssignment item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.ClassAttributeAssignment.HIGHLIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.highlight</code> attribute.
	 * @return the highlight
	 */
	public Boolean isHighlight(final ClassAttributeAssignment item)
	{
		return isHighlight( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.highlight</code> attribute. 
	 * @return the highlight
	 */
	public boolean isHighlightAsPrimitive(final SessionContext ctx, final ClassAttributeAssignment item)
	{
		Boolean value = isHighlight( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.highlight</code> attribute. 
	 * @return the highlight
	 */
	public boolean isHighlightAsPrimitive(final ClassAttributeAssignment item)
	{
		return isHighlightAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.highlight</code> attribute. 
	 * @param value the highlight
	 */
	public void setHighlight(final SessionContext ctx, final ClassAttributeAssignment item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.ClassAttributeAssignment.HIGHLIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.highlight</code> attribute. 
	 * @param value the highlight
	 */
	public void setHighlight(final ClassAttributeAssignment item, final Boolean value)
	{
		setHighlight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.highlight</code> attribute. 
	 * @param value the highlight
	 */
	public void setHighlight(final SessionContext ctx, final ClassAttributeAssignment item, final boolean value)
	{
		setHighlight( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.highlight</code> attribute. 
	 * @param value the highlight
	 */
	public void setHighlight(final ClassAttributeAssignment item, final boolean value)
	{
		setHighlight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.homeBranch</code> attribute.
	 * @return the homeBranch - customer Home Branch
	 */
	public String getHomeBranch(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.HOMEBRANCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.homeBranch</code> attribute.
	 * @return the homeBranch - customer Home Branch
	 */
	public String getHomeBranch(final B2BCustomer item)
	{
		return getHomeBranch( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.homeBranch</code> attribute. 
	 * @param value the homeBranch - customer Home Branch
	 */
	public void setHomeBranch(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.HOMEBRANCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.homeBranch</code> attribute. 
	 * @param value the homeBranch - customer Home Branch
	 */
	public void setHomeBranch(final B2BCustomer item, final String value)
	{
		setHomeBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.homeBranch</code> attribute.
	 * @return the homeBranch
	 */
	public Set<PointOfService> getHomeBranch(final SessionContext ctx, final PointOfService item)
	{
		final List<PointOfService> items = item.getLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2DISTRIBUTEDCENTERRELATION,
			"PointOfService",
			null,
			false,
			false
		);
		return new LinkedHashSet<PointOfService>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.homeBranch</code> attribute.
	 * @return the homeBranch
	 */
	public Set<PointOfService> getHomeBranch(final PointOfService item)
	{
		return getHomeBranch( getSession().getSessionContext(), item );
	}
	
	public long getHomeBranchCount(final SessionContext ctx, final PointOfService item)
	{
		return item.getLinkedItemsCount(
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2DISTRIBUTEDCENTERRELATION,
			"PointOfService",
			null
		);
	}
	
	public long getHomeBranchCount(final PointOfService item)
	{
		return getHomeBranchCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.homeBranch</code> attribute. 
	 * @param value the homeBranch
	 */
	public void setHomeBranch(final SessionContext ctx, final PointOfService item, final Set<PointOfService> value)
	{
		item.setLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2DISTRIBUTEDCENTERRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2DISTRIBUTEDCENTERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.homeBranch</code> attribute. 
	 * @param value the homeBranch
	 */
	public void setHomeBranch(final PointOfService item, final Set<PointOfService> value)
	{
		setHomeBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to homeBranch. 
	 * @param value the item to add to homeBranch
	 */
	public void addToHomeBranch(final SessionContext ctx, final PointOfService item, final PointOfService value)
	{
		item.addLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2DISTRIBUTEDCENTERRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2DISTRIBUTEDCENTERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to homeBranch. 
	 * @param value the item to add to homeBranch
	 */
	public void addToHomeBranch(final PointOfService item, final PointOfService value)
	{
		addToHomeBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from homeBranch. 
	 * @param value the item to remove from homeBranch
	 */
	public void removeFromHomeBranch(final SessionContext ctx, final PointOfService item, final PointOfService value)
	{
		item.removeLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2DISTRIBUTEDCENTERRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2DISTRIBUTEDCENTERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from homeBranch. 
	 * @param value the item to remove from homeBranch
	 */
	public void removeFromHomeBranch(final PointOfService item, final PointOfService value)
	{
		removeFromHomeBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.homeBranch</code> attribute.
	 * @return the homeBranch - Unit Home Branch
	 */
	public String getHomeBranch(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.HOMEBRANCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.homeBranch</code> attribute.
	 * @return the homeBranch - Unit Home Branch
	 */
	public String getHomeBranch(final B2BUnit item)
	{
		return getHomeBranch( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.homeBranch</code> attribute. 
	 * @param value the homeBranch - Unit Home Branch
	 */
	public void setHomeBranch(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.HOMEBRANCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.homeBranch</code> attribute. 
	 * @param value the homeBranch - Unit Home Branch
	 */
	public void setHomeBranch(final B2BUnit item, final String value)
	{
		setHomeBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.homeBranchNumber</code> attribute.
	 * @return the homeBranchNumber - Guest customer Home Branch
	 */
	public String getHomeBranchNumber(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.HOMEBRANCHNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.homeBranchNumber</code> attribute.
	 * @return the homeBranchNumber - Guest customer Home Branch
	 */
	public String getHomeBranchNumber(final AbstractOrder item)
	{
		return getHomeBranchNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.homeBranchNumber</code> attribute. 
	 * @param value the homeBranchNumber - Guest customer Home Branch
	 */
	public void setHomeBranchNumber(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.HOMEBRANCHNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.homeBranchNumber</code> attribute. 
	 * @param value the homeBranchNumber - Guest customer Home Branch
	 */
	public void setHomeBranchNumber(final AbstractOrder item, final String value)
	{
		setHomeBranchNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.homeStoreNumber</code> attribute.
	 * @return the homeStoreNumber
	 */
	public String getHomeStoreNumber(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.HOMESTORENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.homeStoreNumber</code> attribute.
	 * @return the homeStoreNumber
	 */
	public String getHomeStoreNumber(final AbstractOrder item)
	{
		return getHomeStoreNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.homeStoreNumber</code> attribute. 
	 * @param value the homeStoreNumber
	 */
	public void setHomeStoreNumber(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.HOMESTORENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.homeStoreNumber</code> attribute. 
	 * @param value the homeStoreNumber
	 */
	public void setHomeStoreNumber(final AbstractOrder item, final String value)
	{
		setHomeStoreNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.hybrisConsignmentId</code> attribute.
	 * @return the hybrisConsignmentId
	 */
	public String getHybrisConsignmentId(final SessionContext ctx, final Consignment item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Consignment.HYBRISCONSIGNMENTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.hybrisConsignmentId</code> attribute.
	 * @return the hybrisConsignmentId
	 */
	public String getHybrisConsignmentId(final Consignment item)
	{
		return getHybrisConsignmentId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.hybrisConsignmentId</code> attribute. 
	 * @param value the hybrisConsignmentId
	 */
	public void setHybrisConsignmentId(final SessionContext ctx, final Consignment item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Consignment.HYBRISCONSIGNMENTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.hybrisConsignmentId</code> attribute. 
	 * @param value the hybrisConsignmentId
	 */
	public void setHybrisConsignmentId(final Consignment item, final String value)
	{
		setHybrisConsignmentId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ConsignmentEntry.hybrisConsignmentId</code> attribute.
	 * @return the hybrisConsignmentId
	 */
	public String getHybrisConsignmentId(final SessionContext ctx, final ConsignmentEntry item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.ConsignmentEntry.HYBRISCONSIGNMENTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ConsignmentEntry.hybrisConsignmentId</code> attribute.
	 * @return the hybrisConsignmentId
	 */
	public String getHybrisConsignmentId(final ConsignmentEntry item)
	{
		return getHybrisConsignmentId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ConsignmentEntry.hybrisConsignmentId</code> attribute. 
	 * @param value the hybrisConsignmentId
	 */
	public void setHybrisConsignmentId(final SessionContext ctx, final ConsignmentEntry item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.ConsignmentEntry.HYBRISCONSIGNMENTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ConsignmentEntry.hybrisConsignmentId</code> attribute. 
	 * @param value the hybrisConsignmentId
	 */
	public void setHybrisConsignmentId(final ConsignmentEntry item, final String value)
	{
		setHybrisConsignmentId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.hybrisOrderNumber</code> attribute.
	 * @return the hybrisOrderNumber
	 */
	public String getHybrisOrderNumber(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.HYBRISORDERNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.hybrisOrderNumber</code> attribute.
	 * @return the hybrisOrderNumber
	 */
	public String getHybrisOrderNumber(final AbstractOrder item)
	{
		return getHybrisOrderNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.hybrisOrderNumber</code> attribute. 
	 * @param value the hybrisOrderNumber
	 */
	public void setHybrisOrderNumber(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.HYBRISORDERNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.hybrisOrderNumber</code> attribute. 
	 * @param value the hybrisOrderNumber
	 */
	public void setHybrisOrderNumber(final AbstractOrder item, final String value)
	{
		setHybrisOrderNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractPromotion.image</code> attribute.
	 * @return the image
	 */
	public Media getImage(final SessionContext ctx, final AbstractPromotion item)
	{
		return (Media)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractPromotion.IMAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractPromotion.image</code> attribute.
	 * @return the image
	 */
	public Media getImage(final AbstractPromotion item)
	{
		return getImage( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractPromotion.image</code> attribute. 
	 * @param value the image
	 */
	public void setImage(final SessionContext ctx, final AbstractPromotion item, final Media value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractPromotion.IMAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractPromotion.image</code> attribute. 
	 * @param value the image
	 */
	public void setImage(final AbstractPromotion item, final Media value)
	{
		setImage( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StoreLocatorFeature.image</code> attribute.
	 * @return the image
	 */
	public Media getImage(final SessionContext ctx, final StoreLocatorFeature item)
	{
		return (Media)item.getProperty( ctx, SiteoneCoreConstants.Attributes.StoreLocatorFeature.IMAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StoreLocatorFeature.image</code> attribute.
	 * @return the image
	 */
	public Media getImage(final StoreLocatorFeature item)
	{
		return getImage( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StoreLocatorFeature.image</code> attribute. 
	 * @param value the image
	 */
	public void setImage(final SessionContext ctx, final StoreLocatorFeature item, final Media value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.StoreLocatorFeature.IMAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StoreLocatorFeature.image</code> attribute. 
	 * @param value the image
	 */
	public void setImage(final StoreLocatorFeature item, final Media value)
	{
		setImage( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.includeInSiteMap</code> attribute.
	 * @return the includeInSiteMap
	 */
	public Boolean isIncludeInSiteMap(final SessionContext ctx, final ContentPage item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.ContentPage.INCLUDEINSITEMAP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.includeInSiteMap</code> attribute.
	 * @return the includeInSiteMap
	 */
	public Boolean isIncludeInSiteMap(final ContentPage item)
	{
		return isIncludeInSiteMap( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.includeInSiteMap</code> attribute. 
	 * @return the includeInSiteMap
	 */
	public boolean isIncludeInSiteMapAsPrimitive(final SessionContext ctx, final ContentPage item)
	{
		Boolean value = isIncludeInSiteMap( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.includeInSiteMap</code> attribute. 
	 * @return the includeInSiteMap
	 */
	public boolean isIncludeInSiteMapAsPrimitive(final ContentPage item)
	{
		return isIncludeInSiteMapAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.includeInSiteMap</code> attribute. 
	 * @param value the includeInSiteMap
	 */
	public void setIncludeInSiteMap(final SessionContext ctx, final ContentPage item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.ContentPage.INCLUDEINSITEMAP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.includeInSiteMap</code> attribute. 
	 * @param value the includeInSiteMap
	 */
	public void setIncludeInSiteMap(final ContentPage item, final Boolean value)
	{
		setIncludeInSiteMap( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.includeInSiteMap</code> attribute. 
	 * @param value the includeInSiteMap
	 */
	public void setIncludeInSiteMap(final SessionContext ctx, final ContentPage item, final boolean value)
	{
		setIncludeInSiteMap( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.includeInSiteMap</code> attribute. 
	 * @param value the includeInSiteMap
	 */
	public void setIncludeInSiteMap(final ContentPage item, final boolean value)
	{
		setIncludeInSiteMap( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.incomingPO</code> attribute.
	 * @return the incomingPO
	 */
	public Integer getIncomingPO(final SessionContext ctx, final StockLevel item)
	{
		return (Integer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.StockLevel.INCOMINGPO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.incomingPO</code> attribute.
	 * @return the incomingPO
	 */
	public Integer getIncomingPO(final StockLevel item)
	{
		return getIncomingPO( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.incomingPO</code> attribute. 
	 * @return the incomingPO
	 */
	public int getIncomingPOAsPrimitive(final SessionContext ctx, final StockLevel item)
	{
		Integer value = getIncomingPO( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.incomingPO</code> attribute. 
	 * @return the incomingPO
	 */
	public int getIncomingPOAsPrimitive(final StockLevel item)
	{
		return getIncomingPOAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.incomingPO</code> attribute. 
	 * @param value the incomingPO
	 */
	public void setIncomingPO(final SessionContext ctx, final StockLevel item, final Integer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.StockLevel.INCOMINGPO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.incomingPO</code> attribute. 
	 * @param value the incomingPO
	 */
	public void setIncomingPO(final StockLevel item, final Integer value)
	{
		setIncomingPO( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.incomingPO</code> attribute. 
	 * @param value the incomingPO
	 */
	public void setIncomingPO(final SessionContext ctx, final StockLevel item, final int value)
	{
		setIncomingPO( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.incomingPO</code> attribute. 
	 * @param value the incomingPO
	 */
	public void setIncomingPO(final StockLevel item, final int value)
	{
		setIncomingPO( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.incTransfer</code> attribute.
	 * @return the incTransfer
	 */
	public Integer getIncTransfer(final SessionContext ctx, final StockLevel item)
	{
		return (Integer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.StockLevel.INCTRANSFER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.incTransfer</code> attribute.
	 * @return the incTransfer
	 */
	public Integer getIncTransfer(final StockLevel item)
	{
		return getIncTransfer( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.incTransfer</code> attribute. 
	 * @return the incTransfer
	 */
	public int getIncTransferAsPrimitive(final SessionContext ctx, final StockLevel item)
	{
		Integer value = getIncTransfer( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.incTransfer</code> attribute. 
	 * @return the incTransfer
	 */
	public int getIncTransferAsPrimitive(final StockLevel item)
	{
		return getIncTransferAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.incTransfer</code> attribute. 
	 * @param value the incTransfer
	 */
	public void setIncTransfer(final SessionContext ctx, final StockLevel item, final Integer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.StockLevel.INCTRANSFER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.incTransfer</code> attribute. 
	 * @param value the incTransfer
	 */
	public void setIncTransfer(final StockLevel item, final Integer value)
	{
		setIncTransfer( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.incTransfer</code> attribute. 
	 * @param value the incTransfer
	 */
	public void setIncTransfer(final SessionContext ctx, final StockLevel item, final int value)
	{
		setIncTransfer( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.incTransfer</code> attribute. 
	 * @param value the incTransfer
	 */
	public void setIncTransfer(final StockLevel item, final int value)
	{
		setIncTransfer( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.insideSalesRepEmail</code> attribute.
	 * @return the insideSalesRepEmail
	 */
	public String getInsideSalesRepEmail(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.INSIDESALESREPEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.insideSalesRepEmail</code> attribute.
	 * @return the insideSalesRepEmail
	 */
	public String getInsideSalesRepEmail(final B2BUnit item)
	{
		return getInsideSalesRepEmail( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.insideSalesRepEmail</code> attribute. 
	 * @param value the insideSalesRepEmail
	 */
	public void setInsideSalesRepEmail(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.INSIDESALESREPEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.insideSalesRepEmail</code> attribute. 
	 * @param value the insideSalesRepEmail
	 */
	public void setInsideSalesRepEmail(final B2BUnit item, final String value)
	{
		setInsideSalesRepEmail( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.inventoryCheck</code> attribute.
	 * @return the inventoryCheck
	 */
	public Boolean isInventoryCheck(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.INVENTORYCHECK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.inventoryCheck</code> attribute.
	 * @return the inventoryCheck
	 */
	public Boolean isInventoryCheck(final Product item)
	{
		return isInventoryCheck( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.inventoryCheck</code> attribute. 
	 * @return the inventoryCheck
	 */
	public boolean isInventoryCheckAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isInventoryCheck( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.inventoryCheck</code> attribute. 
	 * @return the inventoryCheck
	 */
	public boolean isInventoryCheckAsPrimitive(final Product item)
	{
		return isInventoryCheckAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.inventoryCheck</code> attribute. 
	 * @param value the inventoryCheck
	 */
	public void setInventoryCheck(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.INVENTORYCHECK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.inventoryCheck</code> attribute. 
	 * @param value the inventoryCheck
	 */
	public void setInventoryCheck(final Product item, final Boolean value)
	{
		setInventoryCheck( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.inventoryCheck</code> attribute. 
	 * @param value the inventoryCheck
	 */
	public void setInventoryCheck(final SessionContext ctx, final Product item, final boolean value)
	{
		setInventoryCheck( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.inventoryCheck</code> attribute. 
	 * @param value the inventoryCheck
	 */
	public void setInventoryCheck(final Product item, final boolean value)
	{
		setInventoryCheck( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.inventoryHit</code> attribute.
	 * @return the inventoryHit
	 */
	public Integer getInventoryHit(final SessionContext ctx, final StockLevel item)
	{
		return (Integer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.StockLevel.INVENTORYHIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.inventoryHit</code> attribute.
	 * @return the inventoryHit
	 */
	public Integer getInventoryHit(final StockLevel item)
	{
		return getInventoryHit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.inventoryHit</code> attribute. 
	 * @return the inventoryHit
	 */
	public int getInventoryHitAsPrimitive(final SessionContext ctx, final StockLevel item)
	{
		Integer value = getInventoryHit( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.inventoryHit</code> attribute. 
	 * @return the inventoryHit
	 */
	public int getInventoryHitAsPrimitive(final StockLevel item)
	{
		return getInventoryHitAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.inventoryHit</code> attribute. 
	 * @param value the inventoryHit
	 */
	public void setInventoryHit(final SessionContext ctx, final StockLevel item, final Integer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.StockLevel.INVENTORYHIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.inventoryHit</code> attribute. 
	 * @param value the inventoryHit
	 */
	public void setInventoryHit(final StockLevel item, final Integer value)
	{
		setInventoryHit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.inventoryHit</code> attribute. 
	 * @param value the inventoryHit
	 */
	public void setInventoryHit(final SessionContext ctx, final StockLevel item, final int value)
	{
		setInventoryHit( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.inventoryHit</code> attribute. 
	 * @param value the inventoryHit
	 */
	public void setInventoryHit(final StockLevel item, final int value)
	{
		setInventoryHit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.InventoryUOM</code> attribute.
	 * @return the InventoryUOM
	 */
	public InventoryUOM getInventoryUOM(final SessionContext ctx, final Product item)
	{
		return (InventoryUOM)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.INVENTORYUOM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.InventoryUOM</code> attribute.
	 * @return the InventoryUOM
	 */
	public InventoryUOM getInventoryUOM(final Product item)
	{
		return getInventoryUOM( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.InventoryUOM</code> attribute. 
	 * @param value the InventoryUOM
	 */
	public void setInventoryUOM(final SessionContext ctx, final Product item, final InventoryUOM value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.INVENTORYUOM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.InventoryUOM</code> attribute. 
	 * @param value the InventoryUOM
	 */
	public void setInventoryUOM(final Product item, final InventoryUOM value)
	{
		setInventoryUOM( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.inventoryUOM</code> attribute.
	 * @return the inventoryUOM
	 */
	public InventoryUPC getInventoryUOM(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (InventoryUPC)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.INVENTORYUOM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.inventoryUOM</code> attribute.
	 * @return the inventoryUOM
	 */
	public InventoryUPC getInventoryUOM(final AbstractOrderEntry item)
	{
		return getInventoryUOM( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.inventoryUOM</code> attribute. 
	 * @param value the inventoryUOM
	 */
	public void setInventoryUOM(final SessionContext ctx, final AbstractOrderEntry item, final InventoryUPC value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.INVENTORYUOM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.inventoryUOM</code> attribute. 
	 * @param value the inventoryUOM
	 */
	public void setInventoryUOM(final AbstractOrderEntry item, final InventoryUPC value)
	{
		setInventoryUOM( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.invoiceNumber</code> attribute.
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.INVOICENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.invoiceNumber</code> attribute.
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber(final AbstractOrder item)
	{
		return getInvoiceNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.invoiceNumber</code> attribute. 
	 * @param value the invoiceNumber
	 */
	public void setInvoiceNumber(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.INVOICENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.invoiceNumber</code> attribute. 
	 * @param value the invoiceNumber
	 */
	public void setInvoiceNumber(final AbstractOrder item, final String value)
	{
		setInvoiceNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.invoiceNumber</code> attribute.
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber(final SessionContext ctx, final Consignment item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Consignment.INVOICENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.invoiceNumber</code> attribute.
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber(final Consignment item)
	{
		return getInvoiceNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.invoiceNumber</code> attribute. 
	 * @param value the invoiceNumber
	 */
	public void setInvoiceNumber(final SessionContext ctx, final Consignment item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Consignment.INVOICENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.invoiceNumber</code> attribute. 
	 * @param value the invoiceNumber
	 */
	public void setInvoiceNumber(final Consignment item, final String value)
	{
		setInvoiceNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.invoicePermissions</code> attribute.
	 * @return the invoicePermissions
	 */
	public Boolean isInvoicePermissions(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.INVOICEPERMISSIONS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.invoicePermissions</code> attribute.
	 * @return the invoicePermissions
	 */
	public Boolean isInvoicePermissions(final B2BCustomer item)
	{
		return isInvoicePermissions( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.invoicePermissions</code> attribute. 
	 * @return the invoicePermissions
	 */
	public boolean isInvoicePermissionsAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isInvoicePermissions( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.invoicePermissions</code> attribute. 
	 * @return the invoicePermissions
	 */
	public boolean isInvoicePermissionsAsPrimitive(final B2BCustomer item)
	{
		return isInvoicePermissionsAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.invoicePermissions</code> attribute. 
	 * @param value the invoicePermissions
	 */
	public void setInvoicePermissions(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.INVOICEPERMISSIONS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.invoicePermissions</code> attribute. 
	 * @param value the invoicePermissions
	 */
	public void setInvoicePermissions(final B2BCustomer item, final Boolean value)
	{
		setInvoicePermissions( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.invoicePermissions</code> attribute. 
	 * @param value the invoicePermissions
	 */
	public void setInvoicePermissions(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setInvoicePermissions( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.invoicePermissions</code> attribute. 
	 * @param value the invoicePermissions
	 */
	public void setInvoicePermissions(final B2BCustomer item, final boolean value)
	{
		setInvoicePermissions( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isAccountOwner</code> attribute.
	 * @return the isAccountOwner - Account Owner
	 */
	public Boolean isIsAccountOwner(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISACCOUNTOWNER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isAccountOwner</code> attribute.
	 * @return the isAccountOwner - Account Owner
	 */
	public Boolean isIsAccountOwner(final B2BCustomer item)
	{
		return isIsAccountOwner( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isAccountOwner</code> attribute. 
	 * @return the isAccountOwner - Account Owner
	 */
	public boolean isIsAccountOwnerAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isIsAccountOwner( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isAccountOwner</code> attribute. 
	 * @return the isAccountOwner - Account Owner
	 */
	public boolean isIsAccountOwnerAsPrimitive(final B2BCustomer item)
	{
		return isIsAccountOwnerAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isAccountOwner</code> attribute. 
	 * @param value the isAccountOwner - Account Owner
	 */
	public void setIsAccountOwner(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISACCOUNTOWNER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isAccountOwner</code> attribute. 
	 * @param value the isAccountOwner - Account Owner
	 */
	public void setIsAccountOwner(final B2BCustomer item, final Boolean value)
	{
		setIsAccountOwner( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isAccountOwner</code> attribute. 
	 * @param value the isAccountOwner - Account Owner
	 */
	public void setIsAccountOwner(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setIsAccountOwner( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isAccountOwner</code> attribute. 
	 * @param value the isAccountOwner - Account Owner
	 */
	public void setIsAccountOwner(final B2BCustomer item, final boolean value)
	{
		setIsAccountOwner( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.isActive</code> attribute.
	 * @return the isActive
	 */
	public Boolean isIsActive(final SessionContext ctx, final PointOfService item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.ISACTIVE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.isActive</code> attribute.
	 * @return the isActive
	 */
	public Boolean isIsActive(final PointOfService item)
	{
		return isIsActive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.isActive</code> attribute. 
	 * @return the isActive
	 */
	public boolean isIsActiveAsPrimitive(final SessionContext ctx, final PointOfService item)
	{
		Boolean value = isIsActive( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.isActive</code> attribute. 
	 * @return the isActive
	 */
	public boolean isIsActiveAsPrimitive(final PointOfService item)
	{
		return isIsActiveAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.isActive</code> attribute. 
	 * @param value the isActive
	 */
	public void setIsActive(final SessionContext ctx, final PointOfService item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.ISACTIVE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.isActive</code> attribute. 
	 * @param value the isActive
	 */
	public void setIsActive(final PointOfService item, final Boolean value)
	{
		setIsActive( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.isActive</code> attribute. 
	 * @param value the isActive
	 */
	public void setIsActive(final SessionContext ctx, final PointOfService item, final boolean value)
	{
		setIsActive( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.isActive</code> attribute. 
	 * @param value the isActive
	 */
	public void setIsActive(final PointOfService item, final boolean value)
	{
		setIsActive( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isActiveInOkta</code> attribute.
	 * @return the isActiveInOkta
	 */
	public Boolean isIsActiveInOkta(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISACTIVEINOKTA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isActiveInOkta</code> attribute.
	 * @return the isActiveInOkta
	 */
	public Boolean isIsActiveInOkta(final B2BCustomer item)
	{
		return isIsActiveInOkta( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isActiveInOkta</code> attribute. 
	 * @return the isActiveInOkta
	 */
	public boolean isIsActiveInOktaAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isIsActiveInOkta( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isActiveInOkta</code> attribute. 
	 * @return the isActiveInOkta
	 */
	public boolean isIsActiveInOktaAsPrimitive(final B2BCustomer item)
	{
		return isIsActiveInOktaAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isActiveInOkta</code> attribute. 
	 * @param value the isActiveInOkta
	 */
	public void setIsActiveInOkta(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISACTIVEINOKTA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isActiveInOkta</code> attribute. 
	 * @param value the isActiveInOkta
	 */
	public void setIsActiveInOkta(final B2BCustomer item, final Boolean value)
	{
		setIsActiveInOkta( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isActiveInOkta</code> attribute. 
	 * @param value the isActiveInOkta
	 */
	public void setIsActiveInOkta(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setIsActiveInOkta( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isActiveInOkta</code> attribute. 
	 * @param value the isActiveInOkta
	 */
	public void setIsActiveInOkta(final B2BCustomer item, final boolean value)
	{
		setIsActiveInOkta( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isBaseUom</code> attribute.
	 * @return the isBaseUom - isBaseUom
	 */
	public Boolean isIsBaseUom(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.ISBASEUOM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isBaseUom</code> attribute.
	 * @return the isBaseUom - isBaseUom
	 */
	public Boolean isIsBaseUom(final AbstractOrderEntry item)
	{
		return isIsBaseUom( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isBaseUom</code> attribute. 
	 * @return the isBaseUom - isBaseUom
	 */
	public boolean isIsBaseUomAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Boolean value = isIsBaseUom( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isBaseUom</code> attribute. 
	 * @return the isBaseUom - isBaseUom
	 */
	public boolean isIsBaseUomAsPrimitive(final AbstractOrderEntry item)
	{
		return isIsBaseUomAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isBaseUom</code> attribute. 
	 * @param value the isBaseUom - isBaseUom
	 */
	public void setIsBaseUom(final SessionContext ctx, final AbstractOrderEntry item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.ISBASEUOM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isBaseUom</code> attribute. 
	 * @param value the isBaseUom - isBaseUom
	 */
	public void setIsBaseUom(final AbstractOrderEntry item, final Boolean value)
	{
		setIsBaseUom( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isBaseUom</code> attribute. 
	 * @param value the isBaseUom - isBaseUom
	 */
	public void setIsBaseUom(final SessionContext ctx, final AbstractOrderEntry item, final boolean value)
	{
		setIsBaseUom( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isBaseUom</code> attribute. 
	 * @param value the isBaseUom - isBaseUom
	 */
	public void setIsBaseUom(final AbstractOrderEntry item, final boolean value)
	{
		setIsBaseUom( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isBillingAccount</code> attribute.
	 * @return the isBillingAccount - isBillingAccount
	 */
	public Boolean isIsBillingAccount(final SessionContext ctx, final B2BUnit item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.ISBILLINGACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isBillingAccount</code> attribute.
	 * @return the isBillingAccount - isBillingAccount
	 */
	public Boolean isIsBillingAccount(final B2BUnit item)
	{
		return isIsBillingAccount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isBillingAccount</code> attribute. 
	 * @return the isBillingAccount - isBillingAccount
	 */
	public boolean isIsBillingAccountAsPrimitive(final SessionContext ctx, final B2BUnit item)
	{
		Boolean value = isIsBillingAccount( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isBillingAccount</code> attribute. 
	 * @return the isBillingAccount - isBillingAccount
	 */
	public boolean isIsBillingAccountAsPrimitive(final B2BUnit item)
	{
		return isIsBillingAccountAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isBillingAccount</code> attribute. 
	 * @param value the isBillingAccount - isBillingAccount
	 */
	public void setIsBillingAccount(final SessionContext ctx, final B2BUnit item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.ISBILLINGACCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isBillingAccount</code> attribute. 
	 * @param value the isBillingAccount - isBillingAccount
	 */
	public void setIsBillingAccount(final B2BUnit item, final Boolean value)
	{
		setIsBillingAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isBillingAccount</code> attribute. 
	 * @param value the isBillingAccount - isBillingAccount
	 */
	public void setIsBillingAccount(final SessionContext ctx, final B2BUnit item, final boolean value)
	{
		setIsBillingAccount( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isBillingAccount</code> attribute. 
	 * @param value the isBillingAccount - isBillingAccount
	 */
	public void setIsBillingAccount(final B2BUnit item, final boolean value)
	{
		setIsBillingAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.isCartAbandonmentEmailSent</code> attribute.
	 * @return the isCartAbandonmentEmailSent - isCartAbandonmentEmailSent
	 */
	public Boolean isIsCartAbandonmentEmailSent(final SessionContext ctx, final Cart item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Cart.ISCARTABANDONMENTEMAILSENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.isCartAbandonmentEmailSent</code> attribute.
	 * @return the isCartAbandonmentEmailSent - isCartAbandonmentEmailSent
	 */
	public Boolean isIsCartAbandonmentEmailSent(final Cart item)
	{
		return isIsCartAbandonmentEmailSent( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.isCartAbandonmentEmailSent</code> attribute. 
	 * @return the isCartAbandonmentEmailSent - isCartAbandonmentEmailSent
	 */
	public boolean isIsCartAbandonmentEmailSentAsPrimitive(final SessionContext ctx, final Cart item)
	{
		Boolean value = isIsCartAbandonmentEmailSent( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.isCartAbandonmentEmailSent</code> attribute. 
	 * @return the isCartAbandonmentEmailSent - isCartAbandonmentEmailSent
	 */
	public boolean isIsCartAbandonmentEmailSentAsPrimitive(final Cart item)
	{
		return isIsCartAbandonmentEmailSentAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.isCartAbandonmentEmailSent</code> attribute. 
	 * @param value the isCartAbandonmentEmailSent - isCartAbandonmentEmailSent
	 */
	public void setIsCartAbandonmentEmailSent(final SessionContext ctx, final Cart item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Cart.ISCARTABANDONMENTEMAILSENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.isCartAbandonmentEmailSent</code> attribute. 
	 * @param value the isCartAbandonmentEmailSent - isCartAbandonmentEmailSent
	 */
	public void setIsCartAbandonmentEmailSent(final Cart item, final Boolean value)
	{
		setIsCartAbandonmentEmailSent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.isCartAbandonmentEmailSent</code> attribute. 
	 * @param value the isCartAbandonmentEmailSent - isCartAbandonmentEmailSent
	 */
	public void setIsCartAbandonmentEmailSent(final SessionContext ctx, final Cart item, final boolean value)
	{
		setIsCartAbandonmentEmailSent( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.isCartAbandonmentEmailSent</code> attribute. 
	 * @param value the isCartAbandonmentEmailSent - isCartAbandonmentEmailSent
	 */
	public void setIsCartAbandonmentEmailSent(final Cart item, final boolean value)
	{
		setIsCartAbandonmentEmailSent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isCartSizeExceeds</code> attribute.
	 * @return the isCartSizeExceeds
	 */
	public Boolean isIsCartSizeExceeds(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISCARTSIZEEXCEEDS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isCartSizeExceeds</code> attribute.
	 * @return the isCartSizeExceeds
	 */
	public Boolean isIsCartSizeExceeds(final AbstractOrder item)
	{
		return isIsCartSizeExceeds( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isCartSizeExceeds</code> attribute. 
	 * @return the isCartSizeExceeds
	 */
	public boolean isIsCartSizeExceedsAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isIsCartSizeExceeds( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isCartSizeExceeds</code> attribute. 
	 * @return the isCartSizeExceeds
	 */
	public boolean isIsCartSizeExceedsAsPrimitive(final AbstractOrder item)
	{
		return isIsCartSizeExceedsAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isCartSizeExceeds</code> attribute. 
	 * @param value the isCartSizeExceeds
	 */
	public void setIsCartSizeExceeds(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISCARTSIZEEXCEEDS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isCartSizeExceeds</code> attribute. 
	 * @param value the isCartSizeExceeds
	 */
	public void setIsCartSizeExceeds(final AbstractOrder item, final Boolean value)
	{
		setIsCartSizeExceeds( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isCartSizeExceeds</code> attribute. 
	 * @param value the isCartSizeExceeds
	 */
	public void setIsCartSizeExceeds(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setIsCartSizeExceeds( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isCartSizeExceeds</code> attribute. 
	 * @param value the isCartSizeExceeds
	 */
	public void setIsCartSizeExceeds(final AbstractOrder item, final boolean value)
	{
		setIsCartSizeExceeds( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isConverted</code> attribute.
	 * @return the isConverted
	 */
	public Boolean isIsConverted(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.ISCONVERTED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isConverted</code> attribute.
	 * @return the isConverted
	 */
	public Boolean isIsConverted(final Product item)
	{
		return isIsConverted( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isConverted</code> attribute. 
	 * @return the isConverted
	 */
	public boolean isIsConvertedAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isIsConverted( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isConverted</code> attribute. 
	 * @return the isConverted
	 */
	public boolean isIsConvertedAsPrimitive(final Product item)
	{
		return isIsConvertedAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isConverted</code> attribute. 
	 * @param value the isConverted
	 */
	public void setIsConverted(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.ISCONVERTED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isConverted</code> attribute. 
	 * @param value the isConverted
	 */
	public void setIsConverted(final Product item, final Boolean value)
	{
		setIsConverted( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isConverted</code> attribute. 
	 * @param value the isConverted
	 */
	public void setIsConverted(final SessionContext ctx, final Product item, final boolean value)
	{
		setIsConverted( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isConverted</code> attribute. 
	 * @param value the isConverted
	 */
	public void setIsConverted(final Product item, final boolean value)
	{
		setIsConverted( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isCustomerPrice</code> attribute.
	 * @return the isCustomerPrice
	 */
	public Boolean isIsCustomerPrice(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.ISCUSTOMERPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isCustomerPrice</code> attribute.
	 * @return the isCustomerPrice
	 */
	public Boolean isIsCustomerPrice(final AbstractOrderEntry item)
	{
		return isIsCustomerPrice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isCustomerPrice</code> attribute. 
	 * @return the isCustomerPrice
	 */
	public boolean isIsCustomerPriceAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Boolean value = isIsCustomerPrice( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isCustomerPrice</code> attribute. 
	 * @return the isCustomerPrice
	 */
	public boolean isIsCustomerPriceAsPrimitive(final AbstractOrderEntry item)
	{
		return isIsCustomerPriceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isCustomerPrice</code> attribute. 
	 * @param value the isCustomerPrice
	 */
	public void setIsCustomerPrice(final SessionContext ctx, final AbstractOrderEntry item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.ISCUSTOMERPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isCustomerPrice</code> attribute. 
	 * @param value the isCustomerPrice
	 */
	public void setIsCustomerPrice(final AbstractOrderEntry item, final Boolean value)
	{
		setIsCustomerPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isCustomerPrice</code> attribute. 
	 * @param value the isCustomerPrice
	 */
	public void setIsCustomerPrice(final SessionContext ctx, final AbstractOrderEntry item, final boolean value)
	{
		setIsCustomerPrice( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isCustomerPrice</code> attribute. 
	 * @param value the isCustomerPrice
	 */
	public void setIsCustomerPrice(final AbstractOrderEntry item, final boolean value)
	{
		setIsCustomerPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.isDCBranch</code> attribute.
	 * @return the isDCBranch
	 */
	public Boolean isIsDCBranch(final SessionContext ctx, final PointOfService item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.ISDCBRANCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.isDCBranch</code> attribute.
	 * @return the isDCBranch
	 */
	public Boolean isIsDCBranch(final PointOfService item)
	{
		return isIsDCBranch( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.isDCBranch</code> attribute. 
	 * @return the isDCBranch
	 */
	public boolean isIsDCBranchAsPrimitive(final SessionContext ctx, final PointOfService item)
	{
		Boolean value = isIsDCBranch( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.isDCBranch</code> attribute. 
	 * @return the isDCBranch
	 */
	public boolean isIsDCBranchAsPrimitive(final PointOfService item)
	{
		return isIsDCBranchAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.isDCBranch</code> attribute. 
	 * @param value the isDCBranch
	 */
	public void setIsDCBranch(final SessionContext ctx, final PointOfService item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.ISDCBRANCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.isDCBranch</code> attribute. 
	 * @param value the isDCBranch
	 */
	public void setIsDCBranch(final PointOfService item, final Boolean value)
	{
		setIsDCBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.isDCBranch</code> attribute. 
	 * @param value the isDCBranch
	 */
	public void setIsDCBranch(final SessionContext ctx, final PointOfService item, final boolean value)
	{
		setIsDCBranch( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.isDCBranch</code> attribute. 
	 * @param value the isDCBranch
	 */
	public void setIsDCBranch(final PointOfService item, final boolean value)
	{
		setIsDCBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isDeliverable</code> attribute.
	 * @return the isDeliverable
	 */
	public Boolean isIsDeliverable(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.ISDELIVERABLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isDeliverable</code> attribute.
	 * @return the isDeliverable
	 */
	public Boolean isIsDeliverable(final Product item)
	{
		return isIsDeliverable( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isDeliverable</code> attribute. 
	 * @return the isDeliverable
	 */
	public boolean isIsDeliverableAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isIsDeliverable( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isDeliverable</code> attribute. 
	 * @return the isDeliverable
	 */
	public boolean isIsDeliverableAsPrimitive(final Product item)
	{
		return isIsDeliverableAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isDeliverable</code> attribute. 
	 * @param value the isDeliverable
	 */
	public void setIsDeliverable(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.ISDELIVERABLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isDeliverable</code> attribute. 
	 * @param value the isDeliverable
	 */
	public void setIsDeliverable(final Product item, final Boolean value)
	{
		setIsDeliverable( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isDeliverable</code> attribute. 
	 * @param value the isDeliverable
	 */
	public void setIsDeliverable(final SessionContext ctx, final Product item, final boolean value)
	{
		setIsDeliverable( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isDeliverable</code> attribute. 
	 * @param value the isDeliverable
	 */
	public void setIsDeliverable(final Product item, final boolean value)
	{
		setIsDeliverable( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionSourceRule.isDescriptionEnabled</code> attribute.
	 * @return the isDescriptionEnabled
	 */
	public Boolean isIsDescriptionEnabled(final SessionContext ctx, final PromotionSourceRule item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PromotionSourceRule.ISDESCRIPTIONENABLED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionSourceRule.isDescriptionEnabled</code> attribute.
	 * @return the isDescriptionEnabled
	 */
	public Boolean isIsDescriptionEnabled(final PromotionSourceRule item)
	{
		return isIsDescriptionEnabled( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionSourceRule.isDescriptionEnabled</code> attribute. 
	 * @return the isDescriptionEnabled
	 */
	public boolean isIsDescriptionEnabledAsPrimitive(final SessionContext ctx, final PromotionSourceRule item)
	{
		Boolean value = isIsDescriptionEnabled( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionSourceRule.isDescriptionEnabled</code> attribute. 
	 * @return the isDescriptionEnabled
	 */
	public boolean isIsDescriptionEnabledAsPrimitive(final PromotionSourceRule item)
	{
		return isIsDescriptionEnabledAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionSourceRule.isDescriptionEnabled</code> attribute. 
	 * @param value the isDescriptionEnabled
	 */
	public void setIsDescriptionEnabled(final SessionContext ctx, final PromotionSourceRule item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PromotionSourceRule.ISDESCRIPTIONENABLED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionSourceRule.isDescriptionEnabled</code> attribute. 
	 * @param value the isDescriptionEnabled
	 */
	public void setIsDescriptionEnabled(final PromotionSourceRule item, final Boolean value)
	{
		setIsDescriptionEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionSourceRule.isDescriptionEnabled</code> attribute. 
	 * @param value the isDescriptionEnabled
	 */
	public void setIsDescriptionEnabled(final SessionContext ctx, final PromotionSourceRule item, final boolean value)
	{
		setIsDescriptionEnabled( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionSourceRule.isDescriptionEnabled</code> attribute. 
	 * @param value the isDescriptionEnabled
	 */
	public void setIsDescriptionEnabled(final PromotionSourceRule item, final boolean value)
	{
		setIsDescriptionEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isDuplicate</code> attribute.
	 * @return the isDuplicate
	 */
	public Boolean isIsDuplicate(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISDUPLICATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isDuplicate</code> attribute.
	 * @return the isDuplicate
	 */
	public Boolean isIsDuplicate(final AbstractOrder item)
	{
		return isIsDuplicate( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isDuplicate</code> attribute. 
	 * @return the isDuplicate
	 */
	public boolean isIsDuplicateAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isIsDuplicate( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isDuplicate</code> attribute. 
	 * @return the isDuplicate
	 */
	public boolean isIsDuplicateAsPrimitive(final AbstractOrder item)
	{
		return isIsDuplicateAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isDuplicate</code> attribute. 
	 * @param value the isDuplicate
	 */
	public void setIsDuplicate(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISDUPLICATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isDuplicate</code> attribute. 
	 * @param value the isDuplicate
	 */
	public void setIsDuplicate(final AbstractOrder item, final Boolean value)
	{
		setIsDuplicate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isDuplicate</code> attribute. 
	 * @param value the isDuplicate
	 */
	public void setIsDuplicate(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setIsDuplicate( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isDuplicate</code> attribute. 
	 * @param value the isDuplicate
	 */
	public void setIsDuplicate(final AbstractOrder item, final boolean value)
	{
		setIsDuplicate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isExpedited</code> attribute.
	 * @return the isExpedited
	 */
	public Boolean isIsExpedited(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISEXPEDITED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isExpedited</code> attribute.
	 * @return the isExpedited
	 */
	public Boolean isIsExpedited(final AbstractOrder item)
	{
		return isIsExpedited( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isExpedited</code> attribute. 
	 * @return the isExpedited
	 */
	public boolean isIsExpeditedAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isIsExpedited( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isExpedited</code> attribute. 
	 * @return the isExpedited
	 */
	public boolean isIsExpeditedAsPrimitive(final AbstractOrder item)
	{
		return isIsExpeditedAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isExpedited</code> attribute. 
	 * @param value the isExpedited
	 */
	public void setIsExpedited(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISEXPEDITED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isExpedited</code> attribute. 
	 * @param value the isExpedited
	 */
	public void setIsExpedited(final AbstractOrder item, final Boolean value)
	{
		setIsExpedited( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isExpedited</code> attribute. 
	 * @param value the isExpedited
	 */
	public void setIsExpedited(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setIsExpedited( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isExpedited</code> attribute. 
	 * @param value the isExpedited
	 */
	public void setIsExpedited(final AbstractOrder item, final boolean value)
	{
		setIsExpedited( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isFirstTimeUser</code> attribute.
	 * @return the isFirstTimeUser
	 */
	public Boolean isIsFirstTimeUser(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISFIRSTTIMEUSER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isFirstTimeUser</code> attribute.
	 * @return the isFirstTimeUser
	 */
	public Boolean isIsFirstTimeUser(final B2BCustomer item)
	{
		return isIsFirstTimeUser( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isFirstTimeUser</code> attribute. 
	 * @return the isFirstTimeUser
	 */
	public boolean isIsFirstTimeUserAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isIsFirstTimeUser( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isFirstTimeUser</code> attribute. 
	 * @return the isFirstTimeUser
	 */
	public boolean isIsFirstTimeUserAsPrimitive(final B2BCustomer item)
	{
		return isIsFirstTimeUserAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isFirstTimeUser</code> attribute. 
	 * @param value the isFirstTimeUser
	 */
	public void setIsFirstTimeUser(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISFIRSTTIMEUSER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isFirstTimeUser</code> attribute. 
	 * @param value the isFirstTimeUser
	 */
	public void setIsFirstTimeUser(final B2BCustomer item, final Boolean value)
	{
		setIsFirstTimeUser( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isFirstTimeUser</code> attribute. 
	 * @param value the isFirstTimeUser
	 */
	public void setIsFirstTimeUser(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setIsFirstTimeUser( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isFirstTimeUser</code> attribute. 
	 * @param value the isFirstTimeUser
	 */
	public void setIsFirstTimeUser(final B2BCustomer item, final boolean value)
	{
		setIsFirstTimeUser( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isHybrisOrder</code> attribute.
	 * @return the isHybrisOrder
	 */
	public Boolean isIsHybrisOrder(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISHYBRISORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isHybrisOrder</code> attribute.
	 * @return the isHybrisOrder
	 */
	public Boolean isIsHybrisOrder(final AbstractOrder item)
	{
		return isIsHybrisOrder( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isHybrisOrder</code> attribute. 
	 * @return the isHybrisOrder
	 */
	public boolean isIsHybrisOrderAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isIsHybrisOrder( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isHybrisOrder</code> attribute. 
	 * @return the isHybrisOrder
	 */
	public boolean isIsHybrisOrderAsPrimitive(final AbstractOrder item)
	{
		return isIsHybrisOrderAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isHybrisOrder</code> attribute. 
	 * @param value the isHybrisOrder
	 */
	public void setIsHybrisOrder(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISHYBRISORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isHybrisOrder</code> attribute. 
	 * @param value the isHybrisOrder
	 */
	public void setIsHybrisOrder(final AbstractOrder item, final Boolean value)
	{
		setIsHybrisOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isHybrisOrder</code> attribute. 
	 * @param value the isHybrisOrder
	 */
	public void setIsHybrisOrder(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setIsHybrisOrder( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isHybrisOrder</code> attribute. 
	 * @param value the isHybrisOrder
	 */
	public void setIsHybrisOrder(final AbstractOrder item, final boolean value)
	{
		setIsHybrisOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.isIndexable</code> attribute.
	 * @return the isIndexable
	 */
	public Boolean isIsIndexable(final SessionContext ctx, final ContentPage item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.ContentPage.ISINDEXABLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.isIndexable</code> attribute.
	 * @return the isIndexable
	 */
	public Boolean isIsIndexable(final ContentPage item)
	{
		return isIsIndexable( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.isIndexable</code> attribute. 
	 * @return the isIndexable
	 */
	public boolean isIsIndexableAsPrimitive(final SessionContext ctx, final ContentPage item)
	{
		Boolean value = isIsIndexable( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.isIndexable</code> attribute. 
	 * @return the isIndexable
	 */
	public boolean isIsIndexableAsPrimitive(final ContentPage item)
	{
		return isIsIndexableAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.isIndexable</code> attribute. 
	 * @param value the isIndexable
	 */
	public void setIsIndexable(final SessionContext ctx, final ContentPage item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.ContentPage.ISINDEXABLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.isIndexable</code> attribute. 
	 * @param value the isIndexable
	 */
	public void setIsIndexable(final ContentPage item, final Boolean value)
	{
		setIsIndexable( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.isIndexable</code> attribute. 
	 * @param value the isIndexable
	 */
	public void setIsIndexable(final SessionContext ctx, final ContentPage item, final boolean value)
	{
		setIsIndexable( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.isIndexable</code> attribute. 
	 * @param value the isIndexable
	 */
	public void setIsIndexable(final ContentPage item, final boolean value)
	{
		setIsIndexable( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isLargeAccount</code> attribute.
	 * @return the isLargeAccount - isLargeAccount
	 */
	public Boolean isIsLargeAccount(final SessionContext ctx, final B2BUnit item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.ISLARGEACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isLargeAccount</code> attribute.
	 * @return the isLargeAccount - isLargeAccount
	 */
	public Boolean isIsLargeAccount(final B2BUnit item)
	{
		return isIsLargeAccount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isLargeAccount</code> attribute. 
	 * @return the isLargeAccount - isLargeAccount
	 */
	public boolean isIsLargeAccountAsPrimitive(final SessionContext ctx, final B2BUnit item)
	{
		Boolean value = isIsLargeAccount( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isLargeAccount</code> attribute. 
	 * @return the isLargeAccount - isLargeAccount
	 */
	public boolean isIsLargeAccountAsPrimitive(final B2BUnit item)
	{
		return isIsLargeAccountAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isLargeAccount</code> attribute. 
	 * @param value the isLargeAccount - isLargeAccount
	 */
	public void setIsLargeAccount(final SessionContext ctx, final B2BUnit item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.ISLARGEACCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isLargeAccount</code> attribute. 
	 * @param value the isLargeAccount - isLargeAccount
	 */
	public void setIsLargeAccount(final B2BUnit item, final Boolean value)
	{
		setIsLargeAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isLargeAccount</code> attribute. 
	 * @param value the isLargeAccount - isLargeAccount
	 */
	public void setIsLargeAccount(final SessionContext ctx, final B2BUnit item, final boolean value)
	{
		setIsLargeAccount( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isLargeAccount</code> attribute. 
	 * @param value the isLargeAccount - isLargeAccount
	 */
	public void setIsLargeAccount(final B2BUnit item, final boolean value)
	{
		setIsLargeAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isNewsLetterOpted</code> attribute.
	 * @return the isNewsLetterOpted - isNewsLetterOpted
	 */
	public Boolean isIsNewsLetterOpted(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISNEWSLETTEROPTED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isNewsLetterOpted</code> attribute.
	 * @return the isNewsLetterOpted - isNewsLetterOpted
	 */
	public Boolean isIsNewsLetterOpted(final B2BCustomer item)
	{
		return isIsNewsLetterOpted( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isNewsLetterOpted</code> attribute. 
	 * @return the isNewsLetterOpted - isNewsLetterOpted
	 */
	public boolean isIsNewsLetterOptedAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isIsNewsLetterOpted( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isNewsLetterOpted</code> attribute. 
	 * @return the isNewsLetterOpted - isNewsLetterOpted
	 */
	public boolean isIsNewsLetterOptedAsPrimitive(final B2BCustomer item)
	{
		return isIsNewsLetterOptedAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isNewsLetterOpted</code> attribute. 
	 * @param value the isNewsLetterOpted - isNewsLetterOpted
	 */
	public void setIsNewsLetterOpted(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISNEWSLETTEROPTED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isNewsLetterOpted</code> attribute. 
	 * @param value the isNewsLetterOpted - isNewsLetterOpted
	 */
	public void setIsNewsLetterOpted(final B2BCustomer item, final Boolean value)
	{
		setIsNewsLetterOpted( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isNewsLetterOpted</code> attribute. 
	 * @param value the isNewsLetterOpted - isNewsLetterOpted
	 */
	public void setIsNewsLetterOpted(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setIsNewsLetterOpted( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isNewsLetterOpted</code> attribute. 
	 * @param value the isNewsLetterOpted - isNewsLetterOpted
	 */
	public void setIsNewsLetterOpted(final B2BCustomer item, final boolean value)
	{
		setIsNewsLetterOpted( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isNPSSurveyTriggered</code> attribute.
	 * @return the isNPSSurveyTriggered
	 */
	public Boolean isIsNPSSurveyTriggered(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISNPSSURVEYTRIGGERED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isNPSSurveyTriggered</code> attribute.
	 * @return the isNPSSurveyTriggered
	 */
	public Boolean isIsNPSSurveyTriggered(final AbstractOrder item)
	{
		return isIsNPSSurveyTriggered( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isNPSSurveyTriggered</code> attribute. 
	 * @return the isNPSSurveyTriggered
	 */
	public boolean isIsNPSSurveyTriggeredAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isIsNPSSurveyTriggered( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isNPSSurveyTriggered</code> attribute. 
	 * @return the isNPSSurveyTriggered
	 */
	public boolean isIsNPSSurveyTriggeredAsPrimitive(final AbstractOrder item)
	{
		return isIsNPSSurveyTriggeredAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isNPSSurveyTriggered</code> attribute. 
	 * @param value the isNPSSurveyTriggered
	 */
	public void setIsNPSSurveyTriggered(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISNPSSURVEYTRIGGERED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isNPSSurveyTriggered</code> attribute. 
	 * @param value the isNPSSurveyTriggered
	 */
	public void setIsNPSSurveyTriggered(final AbstractOrder item, final Boolean value)
	{
		setIsNPSSurveyTriggered( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isNPSSurveyTriggered</code> attribute. 
	 * @param value the isNPSSurveyTriggered
	 */
	public void setIsNPSSurveyTriggered(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setIsNPSSurveyTriggered( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isNPSSurveyTriggered</code> attribute. 
	 * @param value the isNPSSurveyTriggered
	 */
	public void setIsNPSSurveyTriggered(final AbstractOrder item, final boolean value)
	{
		setIsNPSSurveyTriggered( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.isNurseryCoreSku</code> attribute.
	 * @return the isNurseryCoreSku
	 */
	public Boolean isIsNurseryCoreSku(final SessionContext ctx, final StockLevel item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.StockLevel.ISNURSERYCORESKU);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.isNurseryCoreSku</code> attribute.
	 * @return the isNurseryCoreSku
	 */
	public Boolean isIsNurseryCoreSku(final StockLevel item)
	{
		return isIsNurseryCoreSku( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.isNurseryCoreSku</code> attribute. 
	 * @return the isNurseryCoreSku
	 */
	public boolean isIsNurseryCoreSkuAsPrimitive(final SessionContext ctx, final StockLevel item)
	{
		Boolean value = isIsNurseryCoreSku( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.isNurseryCoreSku</code> attribute. 
	 * @return the isNurseryCoreSku
	 */
	public boolean isIsNurseryCoreSkuAsPrimitive(final StockLevel item)
	{
		return isIsNurseryCoreSkuAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.isNurseryCoreSku</code> attribute. 
	 * @param value the isNurseryCoreSku
	 */
	public void setIsNurseryCoreSku(final SessionContext ctx, final StockLevel item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.StockLevel.ISNURSERYCORESKU,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.isNurseryCoreSku</code> attribute. 
	 * @param value the isNurseryCoreSku
	 */
	public void setIsNurseryCoreSku(final StockLevel item, final Boolean value)
	{
		setIsNurseryCoreSku( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.isNurseryCoreSku</code> attribute. 
	 * @param value the isNurseryCoreSku
	 */
	public void setIsNurseryCoreSku(final SessionContext ctx, final StockLevel item, final boolean value)
	{
		setIsNurseryCoreSku( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.isNurseryCoreSku</code> attribute. 
	 * @param value the isNurseryCoreSku
	 */
	public void setIsNurseryCoreSku(final StockLevel item, final boolean value)
	{
		setIsNurseryCoreSku( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isOrderingAccount</code> attribute.
	 * @return the isOrderingAccount - isOrderingAccount
	 */
	public Boolean isIsOrderingAccount(final SessionContext ctx, final B2BUnit item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.ISORDERINGACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isOrderingAccount</code> attribute.
	 * @return the isOrderingAccount - isOrderingAccount
	 */
	public Boolean isIsOrderingAccount(final B2BUnit item)
	{
		return isIsOrderingAccount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isOrderingAccount</code> attribute. 
	 * @return the isOrderingAccount - isOrderingAccount
	 */
	public boolean isIsOrderingAccountAsPrimitive(final SessionContext ctx, final B2BUnit item)
	{
		Boolean value = isIsOrderingAccount( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isOrderingAccount</code> attribute. 
	 * @return the isOrderingAccount - isOrderingAccount
	 */
	public boolean isIsOrderingAccountAsPrimitive(final B2BUnit item)
	{
		return isIsOrderingAccountAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isOrderingAccount</code> attribute. 
	 * @param value the isOrderingAccount - isOrderingAccount
	 */
	public void setIsOrderingAccount(final SessionContext ctx, final B2BUnit item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.ISORDERINGACCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isOrderingAccount</code> attribute. 
	 * @param value the isOrderingAccount - isOrderingAccount
	 */
	public void setIsOrderingAccount(final B2BUnit item, final Boolean value)
	{
		setIsOrderingAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isOrderingAccount</code> attribute. 
	 * @param value the isOrderingAccount - isOrderingAccount
	 */
	public void setIsOrderingAccount(final SessionContext ctx, final B2BUnit item, final boolean value)
	{
		setIsOrderingAccount( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isOrderingAccount</code> attribute. 
	 * @param value the isOrderingAccount - isOrderingAccount
	 */
	public void setIsOrderingAccount(final B2BUnit item, final boolean value)
	{
		setIsOrderingAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isOrderStatusEmailSent</code> attribute.
	 * @return the isOrderStatusEmailSent
	 */
	public Boolean isIsOrderStatusEmailSent(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISORDERSTATUSEMAILSENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isOrderStatusEmailSent</code> attribute.
	 * @return the isOrderStatusEmailSent
	 */
	public Boolean isIsOrderStatusEmailSent(final AbstractOrder item)
	{
		return isIsOrderStatusEmailSent( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isOrderStatusEmailSent</code> attribute. 
	 * @return the isOrderStatusEmailSent
	 */
	public boolean isIsOrderStatusEmailSentAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isIsOrderStatusEmailSent( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isOrderStatusEmailSent</code> attribute. 
	 * @return the isOrderStatusEmailSent
	 */
	public boolean isIsOrderStatusEmailSentAsPrimitive(final AbstractOrder item)
	{
		return isIsOrderStatusEmailSentAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isOrderStatusEmailSent</code> attribute. 
	 * @param value the isOrderStatusEmailSent
	 */
	public void setIsOrderStatusEmailSent(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISORDERSTATUSEMAILSENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isOrderStatusEmailSent</code> attribute. 
	 * @param value the isOrderStatusEmailSent
	 */
	public void setIsOrderStatusEmailSent(final AbstractOrder item, final Boolean value)
	{
		setIsOrderStatusEmailSent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isOrderStatusEmailSent</code> attribute. 
	 * @param value the isOrderStatusEmailSent
	 */
	public void setIsOrderStatusEmailSent(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setIsOrderStatusEmailSent( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isOrderStatusEmailSent</code> attribute. 
	 * @param value the isOrderStatusEmailSent
	 */
	public void setIsOrderStatusEmailSent(final AbstractOrder item, final boolean value)
	{
		setIsOrderStatusEmailSent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isOrderStatusNotificationSent</code> attribute.
	 * @return the isOrderStatusNotificationSent
	 */
	public Boolean isIsOrderStatusNotificationSent(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISORDERSTATUSNOTIFICATIONSENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isOrderStatusNotificationSent</code> attribute.
	 * @return the isOrderStatusNotificationSent
	 */
	public Boolean isIsOrderStatusNotificationSent(final AbstractOrder item)
	{
		return isIsOrderStatusNotificationSent( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isOrderStatusNotificationSent</code> attribute. 
	 * @return the isOrderStatusNotificationSent
	 */
	public boolean isIsOrderStatusNotificationSentAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isIsOrderStatusNotificationSent( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isOrderStatusNotificationSent</code> attribute. 
	 * @return the isOrderStatusNotificationSent
	 */
	public boolean isIsOrderStatusNotificationSentAsPrimitive(final AbstractOrder item)
	{
		return isIsOrderStatusNotificationSentAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isOrderStatusNotificationSent</code> attribute. 
	 * @param value the isOrderStatusNotificationSent
	 */
	public void setIsOrderStatusNotificationSent(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISORDERSTATUSNOTIFICATIONSENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isOrderStatusNotificationSent</code> attribute. 
	 * @param value the isOrderStatusNotificationSent
	 */
	public void setIsOrderStatusNotificationSent(final AbstractOrder item, final Boolean value)
	{
		setIsOrderStatusNotificationSent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isOrderStatusNotificationSent</code> attribute. 
	 * @param value the isOrderStatusNotificationSent
	 */
	public void setIsOrderStatusNotificationSent(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setIsOrderStatusNotificationSent( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isOrderStatusNotificationSent</code> attribute. 
	 * @param value the isOrderStatusNotificationSent
	 */
	public void setIsOrderStatusNotificationSent(final AbstractOrder item, final boolean value)
	{
		setIsOrderStatusNotificationSent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isPayOnAccount</code> attribute.
	 * @return the isPayOnAccount
	 */
	public Boolean isIsPayOnAccount(final SessionContext ctx, final B2BUnit item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.ISPAYONACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isPayOnAccount</code> attribute.
	 * @return the isPayOnAccount
	 */
	public Boolean isIsPayOnAccount(final B2BUnit item)
	{
		return isIsPayOnAccount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isPayOnAccount</code> attribute. 
	 * @return the isPayOnAccount
	 */
	public boolean isIsPayOnAccountAsPrimitive(final SessionContext ctx, final B2BUnit item)
	{
		Boolean value = isIsPayOnAccount( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isPayOnAccount</code> attribute. 
	 * @return the isPayOnAccount
	 */
	public boolean isIsPayOnAccountAsPrimitive(final B2BUnit item)
	{
		return isIsPayOnAccountAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isPayOnAccount</code> attribute. 
	 * @param value the isPayOnAccount
	 */
	public void setIsPayOnAccount(final SessionContext ctx, final B2BUnit item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.ISPAYONACCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isPayOnAccount</code> attribute. 
	 * @param value the isPayOnAccount
	 */
	public void setIsPayOnAccount(final B2BUnit item, final Boolean value)
	{
		setIsPayOnAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isPayOnAccount</code> attribute. 
	 * @param value the isPayOnAccount
	 */
	public void setIsPayOnAccount(final SessionContext ctx, final B2BUnit item, final boolean value)
	{
		setIsPayOnAccount( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isPayOnAccount</code> attribute. 
	 * @param value the isPayOnAccount
	 */
	public void setIsPayOnAccount(final B2BUnit item, final boolean value)
	{
		setIsPayOnAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isPONumberRequired</code> attribute.
	 * @return the isPONumberRequired - isPONumberRequired
	 */
	public Boolean isIsPONumberRequired(final SessionContext ctx, final B2BUnit item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.ISPONUMBERREQUIRED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isPONumberRequired</code> attribute.
	 * @return the isPONumberRequired - isPONumberRequired
	 */
	public Boolean isIsPONumberRequired(final B2BUnit item)
	{
		return isIsPONumberRequired( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isPONumberRequired</code> attribute. 
	 * @return the isPONumberRequired - isPONumberRequired
	 */
	public boolean isIsPONumberRequiredAsPrimitive(final SessionContext ctx, final B2BUnit item)
	{
		Boolean value = isIsPONumberRequired( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isPONumberRequired</code> attribute. 
	 * @return the isPONumberRequired - isPONumberRequired
	 */
	public boolean isIsPONumberRequiredAsPrimitive(final B2BUnit item)
	{
		return isIsPONumberRequiredAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isPONumberRequired</code> attribute. 
	 * @param value the isPONumberRequired - isPONumberRequired
	 */
	public void setIsPONumberRequired(final SessionContext ctx, final B2BUnit item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.ISPONUMBERREQUIRED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isPONumberRequired</code> attribute. 
	 * @param value the isPONumberRequired - isPONumberRequired
	 */
	public void setIsPONumberRequired(final B2BUnit item, final Boolean value)
	{
		setIsPONumberRequired( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isPONumberRequired</code> attribute. 
	 * @param value the isPONumberRequired - isPONumberRequired
	 */
	public void setIsPONumberRequired(final SessionContext ctx, final B2BUnit item, final boolean value)
	{
		setIsPONumberRequired( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isPONumberRequired</code> attribute. 
	 * @param value the isPONumberRequired - isPONumberRequired
	 */
	public void setIsPONumberRequired(final B2BUnit item, final boolean value)
	{
		setIsPONumberRequired( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isProductOffline</code> attribute.
	 * @return the isProductOffline
	 */
	public Boolean isIsProductOffline(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.ISPRODUCTOFFLINE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isProductOffline</code> attribute.
	 * @return the isProductOffline
	 */
	public Boolean isIsProductOffline(final Product item)
	{
		return isIsProductOffline( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isProductOffline</code> attribute. 
	 * @return the isProductOffline
	 */
	public boolean isIsProductOfflineAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isIsProductOffline( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isProductOffline</code> attribute. 
	 * @return the isProductOffline
	 */
	public boolean isIsProductOfflineAsPrimitive(final Product item)
	{
		return isIsProductOfflineAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isProductOffline</code> attribute. 
	 * @param value the isProductOffline
	 */
	public void setIsProductOffline(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.ISPRODUCTOFFLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isProductOffline</code> attribute. 
	 * @param value the isProductOffline
	 */
	public void setIsProductOffline(final Product item, final Boolean value)
	{
		setIsProductOffline( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isProductOffline</code> attribute. 
	 * @param value the isProductOffline
	 */
	public void setIsProductOffline(final SessionContext ctx, final Product item, final boolean value)
	{
		setIsProductOffline( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isProductOffline</code> attribute. 
	 * @param value the isProductOffline
	 */
	public void setIsProductOffline(final Product item, final boolean value)
	{
		setIsProductOffline( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isProjectServicesEnabled</code> attribute.
	 * @return the isProjectServicesEnabled
	 */
	public Boolean isIsProjectServicesEnabled(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISPROJECTSERVICESENABLED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isProjectServicesEnabled</code> attribute.
	 * @return the isProjectServicesEnabled
	 */
	public Boolean isIsProjectServicesEnabled(final B2BCustomer item)
	{
		return isIsProjectServicesEnabled( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isProjectServicesEnabled</code> attribute. 
	 * @return the isProjectServicesEnabled
	 */
	public boolean isIsProjectServicesEnabledAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isIsProjectServicesEnabled( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isProjectServicesEnabled</code> attribute. 
	 * @return the isProjectServicesEnabled
	 */
	public boolean isIsProjectServicesEnabledAsPrimitive(final B2BCustomer item)
	{
		return isIsProjectServicesEnabledAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isProjectServicesEnabled</code> attribute. 
	 * @param value the isProjectServicesEnabled
	 */
	public void setIsProjectServicesEnabled(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISPROJECTSERVICESENABLED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isProjectServicesEnabled</code> attribute. 
	 * @param value the isProjectServicesEnabled
	 */
	public void setIsProjectServicesEnabled(final B2BCustomer item, final Boolean value)
	{
		setIsProjectServicesEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isProjectServicesEnabled</code> attribute. 
	 * @param value the isProjectServicesEnabled
	 */
	public void setIsProjectServicesEnabled(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setIsProjectServicesEnabled( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isProjectServicesEnabled</code> attribute. 
	 * @param value the isProjectServicesEnabled
	 */
	public void setIsProjectServicesEnabled(final B2BCustomer item, final boolean value)
	{
		setIsProjectServicesEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isPromotionEnabled</code> attribute.
	 * @return the isPromotionEnabled
	 */
	public Boolean isIsPromotionEnabled(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.ISPROMOTIONENABLED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isPromotionEnabled</code> attribute.
	 * @return the isPromotionEnabled
	 */
	public Boolean isIsPromotionEnabled(final AbstractOrderEntry item)
	{
		return isIsPromotionEnabled( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isPromotionEnabled</code> attribute. 
	 * @return the isPromotionEnabled
	 */
	public boolean isIsPromotionEnabledAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Boolean value = isIsPromotionEnabled( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isPromotionEnabled</code> attribute. 
	 * @return the isPromotionEnabled
	 */
	public boolean isIsPromotionEnabledAsPrimitive(final AbstractOrderEntry item)
	{
		return isIsPromotionEnabledAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isPromotionEnabled</code> attribute. 
	 * @param value the isPromotionEnabled
	 */
	public void setIsPromotionEnabled(final SessionContext ctx, final AbstractOrderEntry item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.ISPROMOTIONENABLED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isPromotionEnabled</code> attribute. 
	 * @param value the isPromotionEnabled
	 */
	public void setIsPromotionEnabled(final AbstractOrderEntry item, final Boolean value)
	{
		setIsPromotionEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isPromotionEnabled</code> attribute. 
	 * @param value the isPromotionEnabled
	 */
	public void setIsPromotionEnabled(final SessionContext ctx, final AbstractOrderEntry item, final boolean value)
	{
		setIsPromotionEnabled( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isPromotionEnabled</code> attribute. 
	 * @param value the isPromotionEnabled
	 */
	public void setIsPromotionEnabled(final AbstractOrderEntry item, final boolean value)
	{
		setIsPromotionEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isPunchOutAccount</code> attribute.
	 * @return the isPunchOutAccount - isPunchOutAccount
	 */
	public Boolean isIsPunchOutAccount(final SessionContext ctx, final B2BUnit item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.ISPUNCHOUTACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isPunchOutAccount</code> attribute.
	 * @return the isPunchOutAccount - isPunchOutAccount
	 */
	public Boolean isIsPunchOutAccount(final B2BUnit item)
	{
		return isIsPunchOutAccount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isPunchOutAccount</code> attribute. 
	 * @return the isPunchOutAccount - isPunchOutAccount
	 */
	public boolean isIsPunchOutAccountAsPrimitive(final SessionContext ctx, final B2BUnit item)
	{
		Boolean value = isIsPunchOutAccount( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.isPunchOutAccount</code> attribute. 
	 * @return the isPunchOutAccount - isPunchOutAccount
	 */
	public boolean isIsPunchOutAccountAsPrimitive(final B2BUnit item)
	{
		return isIsPunchOutAccountAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isPunchOutAccount</code> attribute. 
	 * @param value the isPunchOutAccount - isPunchOutAccount
	 */
	public void setIsPunchOutAccount(final SessionContext ctx, final B2BUnit item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.ISPUNCHOUTACCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isPunchOutAccount</code> attribute. 
	 * @param value the isPunchOutAccount - isPunchOutAccount
	 */
	public void setIsPunchOutAccount(final B2BUnit item, final Boolean value)
	{
		setIsPunchOutAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isPunchOutAccount</code> attribute. 
	 * @param value the isPunchOutAccount - isPunchOutAccount
	 */
	public void setIsPunchOutAccount(final SessionContext ctx, final B2BUnit item, final boolean value)
	{
		setIsPunchOutAccount( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.isPunchOutAccount</code> attribute. 
	 * @param value the isPunchOutAccount - isPunchOutAccount
	 */
	public void setIsPunchOutAccount(final B2BUnit item, final boolean value)
	{
		setIsPunchOutAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isQuoteToOrderStatusEmailSent</code> attribute.
	 * @return the isQuoteToOrderStatusEmailSent
	 */
	public Boolean isIsQuoteToOrderStatusEmailSent(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISQUOTETOORDERSTATUSEMAILSENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isQuoteToOrderStatusEmailSent</code> attribute.
	 * @return the isQuoteToOrderStatusEmailSent
	 */
	public Boolean isIsQuoteToOrderStatusEmailSent(final AbstractOrder item)
	{
		return isIsQuoteToOrderStatusEmailSent( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isQuoteToOrderStatusEmailSent</code> attribute. 
	 * @return the isQuoteToOrderStatusEmailSent
	 */
	public boolean isIsQuoteToOrderStatusEmailSentAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isIsQuoteToOrderStatusEmailSent( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isQuoteToOrderStatusEmailSent</code> attribute. 
	 * @return the isQuoteToOrderStatusEmailSent
	 */
	public boolean isIsQuoteToOrderStatusEmailSentAsPrimitive(final AbstractOrder item)
	{
		return isIsQuoteToOrderStatusEmailSentAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isQuoteToOrderStatusEmailSent</code> attribute. 
	 * @param value the isQuoteToOrderStatusEmailSent
	 */
	public void setIsQuoteToOrderStatusEmailSent(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISQUOTETOORDERSTATUSEMAILSENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isQuoteToOrderStatusEmailSent</code> attribute. 
	 * @param value the isQuoteToOrderStatusEmailSent
	 */
	public void setIsQuoteToOrderStatusEmailSent(final AbstractOrder item, final Boolean value)
	{
		setIsQuoteToOrderStatusEmailSent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isQuoteToOrderStatusEmailSent</code> attribute. 
	 * @param value the isQuoteToOrderStatusEmailSent
	 */
	public void setIsQuoteToOrderStatusEmailSent(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setIsQuoteToOrderStatusEmailSent( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isQuoteToOrderStatusEmailSent</code> attribute. 
	 * @param value the isQuoteToOrderStatusEmailSent
	 */
	public void setIsQuoteToOrderStatusEmailSent(final AbstractOrder item, final boolean value)
	{
		setIsQuoteToOrderStatusEmailSent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isRealtimeAccount</code> attribute.
	 * @return the isRealtimeAccount - Realtime Account
	 */
	public Boolean isIsRealtimeAccount(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISREALTIMEACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isRealtimeAccount</code> attribute.
	 * @return the isRealtimeAccount - Realtime Account
	 */
	public Boolean isIsRealtimeAccount(final B2BCustomer item)
	{
		return isIsRealtimeAccount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isRealtimeAccount</code> attribute. 
	 * @return the isRealtimeAccount - Realtime Account
	 */
	public boolean isIsRealtimeAccountAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isIsRealtimeAccount( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isRealtimeAccount</code> attribute. 
	 * @return the isRealtimeAccount - Realtime Account
	 */
	public boolean isIsRealtimeAccountAsPrimitive(final B2BCustomer item)
	{
		return isIsRealtimeAccountAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isRealtimeAccount</code> attribute. 
	 * @param value the isRealtimeAccount - Realtime Account
	 */
	public void setIsRealtimeAccount(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISREALTIMEACCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isRealtimeAccount</code> attribute. 
	 * @param value the isRealtimeAccount - Realtime Account
	 */
	public void setIsRealtimeAccount(final B2BCustomer item, final Boolean value)
	{
		setIsRealtimeAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isRealtimeAccount</code> attribute. 
	 * @param value the isRealtimeAccount - Realtime Account
	 */
	public void setIsRealtimeAccount(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setIsRealtimeAccount( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isRealtimeAccount</code> attribute. 
	 * @param value the isRealtimeAccount - Realtime Account
	 */
	public void setIsRealtimeAccount(final B2BCustomer item, final boolean value)
	{
		setIsRealtimeAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isRemainderEmailSent</code> attribute.
	 * @return the isRemainderEmailSent
	 */
	public Boolean isIsRemainderEmailSent(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISREMAINDEREMAILSENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isRemainderEmailSent</code> attribute.
	 * @return the isRemainderEmailSent
	 */
	public Boolean isIsRemainderEmailSent(final AbstractOrder item)
	{
		return isIsRemainderEmailSent( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isRemainderEmailSent</code> attribute. 
	 * @return the isRemainderEmailSent
	 */
	public boolean isIsRemainderEmailSentAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isIsRemainderEmailSent( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isRemainderEmailSent</code> attribute. 
	 * @return the isRemainderEmailSent
	 */
	public boolean isIsRemainderEmailSentAsPrimitive(final AbstractOrder item)
	{
		return isIsRemainderEmailSentAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isRemainderEmailSent</code> attribute. 
	 * @param value the isRemainderEmailSent
	 */
	public void setIsRemainderEmailSent(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISREMAINDEREMAILSENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isRemainderEmailSent</code> attribute. 
	 * @param value the isRemainderEmailSent
	 */
	public void setIsRemainderEmailSent(final AbstractOrder item, final Boolean value)
	{
		setIsRemainderEmailSent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isRemainderEmailSent</code> attribute. 
	 * @param value the isRemainderEmailSent
	 */
	public void setIsRemainderEmailSent(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setIsRemainderEmailSent( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isRemainderEmailSent</code> attribute. 
	 * @param value the isRemainderEmailSent
	 */
	public void setIsRemainderEmailSent(final AbstractOrder item, final boolean value)
	{
		setIsRemainderEmailSent( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isSameaddressforDelivery</code> attribute.
	 * @return the isSameaddressforDelivery
	 */
	public Boolean isIsSameaddressforDelivery(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISSAMEADDRESSFORDELIVERY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isSameaddressforDelivery</code> attribute.
	 * @return the isSameaddressforDelivery
	 */
	public Boolean isIsSameaddressforDelivery(final AbstractOrder item)
	{
		return isIsSameaddressforDelivery( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isSameaddressforDelivery</code> attribute. 
	 * @return the isSameaddressforDelivery
	 */
	public boolean isIsSameaddressforDeliveryAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isIsSameaddressforDelivery( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isSameaddressforDelivery</code> attribute. 
	 * @return the isSameaddressforDelivery
	 */
	public boolean isIsSameaddressforDeliveryAsPrimitive(final AbstractOrder item)
	{
		return isIsSameaddressforDeliveryAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isSameaddressforDelivery</code> attribute. 
	 * @param value the isSameaddressforDelivery
	 */
	public void setIsSameaddressforDelivery(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISSAMEADDRESSFORDELIVERY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isSameaddressforDelivery</code> attribute. 
	 * @param value the isSameaddressforDelivery
	 */
	public void setIsSameaddressforDelivery(final AbstractOrder item, final Boolean value)
	{
		setIsSameaddressforDelivery( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isSameaddressforDelivery</code> attribute. 
	 * @param value the isSameaddressforDelivery
	 */
	public void setIsSameaddressforDelivery(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setIsSameaddressforDelivery( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isSameaddressforDelivery</code> attribute. 
	 * @param value the isSameaddressforDelivery
	 */
	public void setIsSameaddressforDelivery(final AbstractOrder item, final boolean value)
	{
		setIsSameaddressforDelivery( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isSameaddressforParcelShip</code> attribute.
	 * @return the isSameaddressforParcelShip
	 */
	public Boolean isIsSameaddressforParcelShip(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISSAMEADDRESSFORPARCELSHIP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isSameaddressforParcelShip</code> attribute.
	 * @return the isSameaddressforParcelShip
	 */
	public Boolean isIsSameaddressforParcelShip(final AbstractOrder item)
	{
		return isIsSameaddressforParcelShip( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isSameaddressforParcelShip</code> attribute. 
	 * @return the isSameaddressforParcelShip
	 */
	public boolean isIsSameaddressforParcelShipAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isIsSameaddressforParcelShip( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isSameaddressforParcelShip</code> attribute. 
	 * @return the isSameaddressforParcelShip
	 */
	public boolean isIsSameaddressforParcelShipAsPrimitive(final AbstractOrder item)
	{
		return isIsSameaddressforParcelShipAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isSameaddressforParcelShip</code> attribute. 
	 * @param value the isSameaddressforParcelShip
	 */
	public void setIsSameaddressforParcelShip(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISSAMEADDRESSFORPARCELSHIP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isSameaddressforParcelShip</code> attribute. 
	 * @param value the isSameaddressforParcelShip
	 */
	public void setIsSameaddressforParcelShip(final AbstractOrder item, final Boolean value)
	{
		setIsSameaddressforParcelShip( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isSameaddressforParcelShip</code> attribute. 
	 * @param value the isSameaddressforParcelShip
	 */
	public void setIsSameaddressforParcelShip(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setIsSameaddressforParcelShip( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isSameaddressforParcelShip</code> attribute. 
	 * @param value the isSameaddressforParcelShip
	 */
	public void setIsSameaddressforParcelShip(final AbstractOrder item, final boolean value)
	{
		setIsSameaddressforParcelShip( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.isShared</code> attribute.
	 * @return the isShared - Attribute to show savedList is shared
	 */
	public Boolean isIsShared(final SessionContext ctx, final Wishlist2 item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Wishlist2.ISSHARED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.isShared</code> attribute.
	 * @return the isShared - Attribute to show savedList is shared
	 */
	public Boolean isIsShared(final Wishlist2 item)
	{
		return isIsShared( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.isShared</code> attribute. 
	 * @return the isShared - Attribute to show savedList is shared
	 */
	public boolean isIsSharedAsPrimitive(final SessionContext ctx, final Wishlist2 item)
	{
		Boolean value = isIsShared( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.isShared</code> attribute. 
	 * @return the isShared - Attribute to show savedList is shared
	 */
	public boolean isIsSharedAsPrimitive(final Wishlist2 item)
	{
		return isIsSharedAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.isShared</code> attribute. 
	 * @param value the isShared - Attribute to show savedList is shared
	 */
	public void setIsShared(final SessionContext ctx, final Wishlist2 item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Wishlist2.ISSHARED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.isShared</code> attribute. 
	 * @param value the isShared - Attribute to show savedList is shared
	 */
	public void setIsShared(final Wishlist2 item, final Boolean value)
	{
		setIsShared( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.isShared</code> attribute. 
	 * @param value the isShared - Attribute to show savedList is shared
	 */
	public void setIsShared(final SessionContext ctx, final Wishlist2 item, final boolean value)
	{
		setIsShared( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.isShared</code> attribute. 
	 * @param value the isShared - Attribute to show savedList is shared
	 */
	public void setIsShared(final Wishlist2 item, final boolean value)
	{
		setIsShared( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isShippable</code> attribute.
	 * @return the isShippable
	 */
	public Boolean isIsShippable(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.ISSHIPPABLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isShippable</code> attribute.
	 * @return the isShippable
	 */
	public Boolean isIsShippable(final Product item)
	{
		return isIsShippable( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isShippable</code> attribute. 
	 * @return the isShippable
	 */
	public boolean isIsShippableAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isIsShippable( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isShippable</code> attribute. 
	 * @return the isShippable
	 */
	public boolean isIsShippableAsPrimitive(final Product item)
	{
		return isIsShippableAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isShippable</code> attribute. 
	 * @param value the isShippable
	 */
	public void setIsShippable(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.ISSHIPPABLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isShippable</code> attribute. 
	 * @param value the isShippable
	 */
	public void setIsShippable(final Product item, final Boolean value)
	{
		setIsShippable( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isShippable</code> attribute. 
	 * @param value the isShippable
	 */
	public void setIsShippable(final SessionContext ctx, final Product item, final boolean value)
	{
		setIsShippable( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isShippable</code> attribute. 
	 * @param value the isShippable
	 */
	public void setIsShippable(final Product item, final boolean value)
	{
		setIsShippable( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isShippingFeeBranch</code> attribute.
	 * @return the isShippingFeeBranch
	 */
	public Boolean isIsShippingFeeBranch(final SessionContext ctx, final AbstractOrder item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISSHIPPINGFEEBRANCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isShippingFeeBranch</code> attribute.
	 * @return the isShippingFeeBranch
	 */
	public Boolean isIsShippingFeeBranch(final AbstractOrder item)
	{
		return isIsShippingFeeBranch( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isShippingFeeBranch</code> attribute. 
	 * @return the isShippingFeeBranch
	 */
	public boolean isIsShippingFeeBranchAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Boolean value = isIsShippingFeeBranch( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.isShippingFeeBranch</code> attribute. 
	 * @return the isShippingFeeBranch
	 */
	public boolean isIsShippingFeeBranchAsPrimitive(final AbstractOrder item)
	{
		return isIsShippingFeeBranchAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isShippingFeeBranch</code> attribute. 
	 * @param value the isShippingFeeBranch
	 */
	public void setIsShippingFeeBranch(final SessionContext ctx, final AbstractOrder item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ISSHIPPINGFEEBRANCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isShippingFeeBranch</code> attribute. 
	 * @param value the isShippingFeeBranch
	 */
	public void setIsShippingFeeBranch(final AbstractOrder item, final Boolean value)
	{
		setIsShippingFeeBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isShippingFeeBranch</code> attribute. 
	 * @param value the isShippingFeeBranch
	 */
	public void setIsShippingFeeBranch(final SessionContext ctx, final AbstractOrder item, final boolean value)
	{
		setIsShippingFeeBranch( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.isShippingFeeBranch</code> attribute. 
	 * @param value the isShippingFeeBranch
	 */
	public void setIsShippingFeeBranch(final AbstractOrder item, final boolean value)
	{
		setIsShippingFeeBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderProcess.isShippingFeeBranch</code> attribute.
	 * @return the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public Boolean isIsShippingFeeBranch(final SessionContext ctx, final OrderProcess item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.OrderProcess.ISSHIPPINGFEEBRANCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderProcess.isShippingFeeBranch</code> attribute.
	 * @return the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public Boolean isIsShippingFeeBranch(final OrderProcess item)
	{
		return isIsShippingFeeBranch( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderProcess.isShippingFeeBranch</code> attribute. 
	 * @return the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public boolean isIsShippingFeeBranchAsPrimitive(final SessionContext ctx, final OrderProcess item)
	{
		Boolean value = isIsShippingFeeBranch( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderProcess.isShippingFeeBranch</code> attribute. 
	 * @return the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public boolean isIsShippingFeeBranchAsPrimitive(final OrderProcess item)
	{
		return isIsShippingFeeBranchAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderProcess.isShippingFeeBranch</code> attribute. 
	 * @param value the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public void setIsShippingFeeBranch(final SessionContext ctx, final OrderProcess item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.OrderProcess.ISSHIPPINGFEEBRANCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderProcess.isShippingFeeBranch</code> attribute. 
	 * @param value the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public void setIsShippingFeeBranch(final OrderProcess item, final Boolean value)
	{
		setIsShippingFeeBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderProcess.isShippingFeeBranch</code> attribute. 
	 * @param value the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public void setIsShippingFeeBranch(final SessionContext ctx, final OrderProcess item, final boolean value)
	{
		setIsShippingFeeBranch( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderProcess.isShippingFeeBranch</code> attribute. 
	 * @param value the isShippingFeeBranch - Email Notification branch for order confirmation process
	 */
	public void setIsShippingFeeBranch(final OrderProcess item, final boolean value)
	{
		setIsShippingFeeBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isShippingOnlyProduct</code> attribute.
	 * @return the isShippingOnlyProduct
	 */
	public Boolean isIsShippingOnlyProduct(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.ISSHIPPINGONLYPRODUCT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isShippingOnlyProduct</code> attribute.
	 * @return the isShippingOnlyProduct
	 */
	public Boolean isIsShippingOnlyProduct(final AbstractOrderEntry item)
	{
		return isIsShippingOnlyProduct( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isShippingOnlyProduct</code> attribute. 
	 * @return the isShippingOnlyProduct
	 */
	public boolean isIsShippingOnlyProductAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Boolean value = isIsShippingOnlyProduct( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.isShippingOnlyProduct</code> attribute. 
	 * @return the isShippingOnlyProduct
	 */
	public boolean isIsShippingOnlyProductAsPrimitive(final AbstractOrderEntry item)
	{
		return isIsShippingOnlyProductAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isShippingOnlyProduct</code> attribute. 
	 * @param value the isShippingOnlyProduct
	 */
	public void setIsShippingOnlyProduct(final SessionContext ctx, final AbstractOrderEntry item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.ISSHIPPINGONLYPRODUCT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isShippingOnlyProduct</code> attribute. 
	 * @param value the isShippingOnlyProduct
	 */
	public void setIsShippingOnlyProduct(final AbstractOrderEntry item, final Boolean value)
	{
		setIsShippingOnlyProduct( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isShippingOnlyProduct</code> attribute. 
	 * @param value the isShippingOnlyProduct
	 */
	public void setIsShippingOnlyProduct(final SessionContext ctx, final AbstractOrderEntry item, final boolean value)
	{
		setIsShippingOnlyProduct( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.isShippingOnlyProduct</code> attribute. 
	 * @param value the isShippingOnlyProduct
	 */
	public void setIsShippingOnlyProduct(final AbstractOrderEntry item, final boolean value)
	{
		setIsShippingOnlyProduct( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isSmsTextOpted</code> attribute.
	 * @return the isSmsTextOpted - isSmsTextOpted
	 */
	public Boolean isIsSmsTextOpted(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISSMSTEXTOPTED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isSmsTextOpted</code> attribute.
	 * @return the isSmsTextOpted - isSmsTextOpted
	 */
	public Boolean isIsSmsTextOpted(final B2BCustomer item)
	{
		return isIsSmsTextOpted( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isSmsTextOpted</code> attribute. 
	 * @return the isSmsTextOpted - isSmsTextOpted
	 */
	public boolean isIsSmsTextOptedAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isIsSmsTextOpted( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isSmsTextOpted</code> attribute. 
	 * @return the isSmsTextOpted - isSmsTextOpted
	 */
	public boolean isIsSmsTextOptedAsPrimitive(final B2BCustomer item)
	{
		return isIsSmsTextOptedAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isSmsTextOpted</code> attribute. 
	 * @param value the isSmsTextOpted - isSmsTextOpted
	 */
	public void setIsSmsTextOpted(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISSMSTEXTOPTED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isSmsTextOpted</code> attribute. 
	 * @param value the isSmsTextOpted - isSmsTextOpted
	 */
	public void setIsSmsTextOpted(final B2BCustomer item, final Boolean value)
	{
		setIsSmsTextOpted( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isSmsTextOpted</code> attribute. 
	 * @param value the isSmsTextOpted - isSmsTextOpted
	 */
	public void setIsSmsTextOpted(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setIsSmsTextOpted( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isSmsTextOpted</code> attribute. 
	 * @param value the isSmsTextOpted - isSmsTextOpted
	 */
	public void setIsSmsTextOpted(final B2BCustomer item, final boolean value)
	{
		setIsSmsTextOpted( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Region.isTerritory</code> attribute.
	 * @return the isTerritory
	 */
	public Boolean isIsTerritory(final SessionContext ctx, final Region item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Region.ISTERRITORY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Region.isTerritory</code> attribute.
	 * @return the isTerritory
	 */
	public Boolean isIsTerritory(final Region item)
	{
		return isIsTerritory( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Region.isTerritory</code> attribute. 
	 * @return the isTerritory
	 */
	public boolean isIsTerritoryAsPrimitive(final SessionContext ctx, final Region item)
	{
		Boolean value = isIsTerritory( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Region.isTerritory</code> attribute. 
	 * @return the isTerritory
	 */
	public boolean isIsTerritoryAsPrimitive(final Region item)
	{
		return isIsTerritoryAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Region.isTerritory</code> attribute. 
	 * @param value the isTerritory
	 */
	public void setIsTerritory(final SessionContext ctx, final Region item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Region.ISTERRITORY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Region.isTerritory</code> attribute. 
	 * @param value the isTerritory
	 */
	public void setIsTerritory(final Region item, final Boolean value)
	{
		setIsTerritory( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Region.isTerritory</code> attribute. 
	 * @param value the isTerritory
	 */
	public void setIsTerritory(final SessionContext ctx, final Region item, final boolean value)
	{
		setIsTerritory( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Region.isTerritory</code> attribute. 
	 * @param value the isTerritory
	 */
	public void setIsTerritory(final Region item, final boolean value)
	{
		setIsTerritory( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isTransactionEmailOpted</code> attribute.
	 * @return the isTransactionEmailOpted - isTransactionEmailOpted
	 */
	public Boolean isIsTransactionEmailOpted(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISTRANSACTIONEMAILOPTED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isTransactionEmailOpted</code> attribute.
	 * @return the isTransactionEmailOpted - isTransactionEmailOpted
	 */
	public Boolean isIsTransactionEmailOpted(final B2BCustomer item)
	{
		return isIsTransactionEmailOpted( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isTransactionEmailOpted</code> attribute. 
	 * @return the isTransactionEmailOpted - isTransactionEmailOpted
	 */
	public boolean isIsTransactionEmailOptedAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isIsTransactionEmailOpted( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.isTransactionEmailOpted</code> attribute. 
	 * @return the isTransactionEmailOpted - isTransactionEmailOpted
	 */
	public boolean isIsTransactionEmailOptedAsPrimitive(final B2BCustomer item)
	{
		return isIsTransactionEmailOptedAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isTransactionEmailOpted</code> attribute. 
	 * @param value the isTransactionEmailOpted - isTransactionEmailOpted
	 */
	public void setIsTransactionEmailOpted(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ISTRANSACTIONEMAILOPTED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isTransactionEmailOpted</code> attribute. 
	 * @param value the isTransactionEmailOpted - isTransactionEmailOpted
	 */
	public void setIsTransactionEmailOpted(final B2BCustomer item, final Boolean value)
	{
		setIsTransactionEmailOpted( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isTransactionEmailOpted</code> attribute. 
	 * @param value the isTransactionEmailOpted - isTransactionEmailOpted
	 */
	public void setIsTransactionEmailOpted(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setIsTransactionEmailOpted( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.isTransactionEmailOpted</code> attribute. 
	 * @param value the isTransactionEmailOpted - isTransactionEmailOpted
	 */
	public void setIsTransactionEmailOpted(final B2BCustomer item, final boolean value)
	{
		setIsTransactionEmailOpted( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isTransferrable</code> attribute.
	 * @return the isTransferrable
	 */
	public Boolean isIsTransferrable(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.ISTRANSFERRABLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isTransferrable</code> attribute.
	 * @return the isTransferrable
	 */
	public Boolean isIsTransferrable(final Product item)
	{
		return isIsTransferrable( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isTransferrable</code> attribute. 
	 * @return the isTransferrable
	 */
	public boolean isIsTransferrableAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isIsTransferrable( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.isTransferrable</code> attribute. 
	 * @return the isTransferrable
	 */
	public boolean isIsTransferrableAsPrimitive(final Product item)
	{
		return isIsTransferrableAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isTransferrable</code> attribute. 
	 * @param value the isTransferrable
	 */
	public void setIsTransferrable(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.ISTRANSFERRABLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isTransferrable</code> attribute. 
	 * @param value the isTransferrable
	 */
	public void setIsTransferrable(final Product item, final Boolean value)
	{
		setIsTransferrable( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isTransferrable</code> attribute. 
	 * @param value the isTransferrable
	 */
	public void setIsTransferrable(final SessionContext ctx, final Product item, final boolean value)
	{
		setIsTransferrable( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.isTransferrable</code> attribute. 
	 * @param value the isTransferrable
	 */
	public void setIsTransferrable(final Product item, final boolean value)
	{
		setIsTransferrable( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.isTransferrableCategory</code> attribute.
	 * @return the isTransferrableCategory
	 */
	public Boolean isIsTransferrableCategory(final SessionContext ctx, final Category item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Category.ISTRANSFERRABLECATEGORY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.isTransferrableCategory</code> attribute.
	 * @return the isTransferrableCategory
	 */
	public Boolean isIsTransferrableCategory(final Category item)
	{
		return isIsTransferrableCategory( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.isTransferrableCategory</code> attribute. 
	 * @return the isTransferrableCategory
	 */
	public boolean isIsTransferrableCategoryAsPrimitive(final SessionContext ctx, final Category item)
	{
		Boolean value = isIsTransferrableCategory( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.isTransferrableCategory</code> attribute. 
	 * @return the isTransferrableCategory
	 */
	public boolean isIsTransferrableCategoryAsPrimitive(final Category item)
	{
		return isIsTransferrableCategoryAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.isTransferrableCategory</code> attribute. 
	 * @param value the isTransferrableCategory
	 */
	public void setIsTransferrableCategory(final SessionContext ctx, final Category item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Category.ISTRANSFERRABLECATEGORY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.isTransferrableCategory</code> attribute. 
	 * @param value the isTransferrableCategory
	 */
	public void setIsTransferrableCategory(final Category item, final Boolean value)
	{
		setIsTransferrableCategory( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.isTransferrableCategory</code> attribute. 
	 * @param value the isTransferrableCategory
	 */
	public void setIsTransferrableCategory(final SessionContext ctx, final Category item, final boolean value)
	{
		setIsTransferrableCategory( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.isTransferrableCategory</code> attribute. 
	 * @param value the isTransferrableCategory
	 */
	public void setIsTransferrableCategory(final Category item, final boolean value)
	{
		setIsTransferrableCategory( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.iTuneLabel</code> attribute.
	 * @return the iTuneLabel
	 */
	public String getITuneLabel(final SessionContext ctx, final FooterNavigationComponent item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedFooterNavigationComponent.getITuneLabel requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, SiteoneCoreConstants.Attributes.FooterNavigationComponent.ITUNELABEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.iTuneLabel</code> attribute.
	 * @return the iTuneLabel
	 */
	public String getITuneLabel(final FooterNavigationComponent item)
	{
		return getITuneLabel( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.iTuneLabel</code> attribute. 
	 * @return the localized iTuneLabel
	 */
	public Map<Language,String> getAllITuneLabel(final SessionContext ctx, final FooterNavigationComponent item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.FooterNavigationComponent.ITUNELABEL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.iTuneLabel</code> attribute. 
	 * @return the localized iTuneLabel
	 */
	public Map<Language,String> getAllITuneLabel(final FooterNavigationComponent item)
	{
		return getAllITuneLabel( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.iTuneLabel</code> attribute. 
	 * @param value the iTuneLabel
	 */
	public void setITuneLabel(final SessionContext ctx, final FooterNavigationComponent item, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedFooterNavigationComponent.setITuneLabel requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteoneCoreConstants.Attributes.FooterNavigationComponent.ITUNELABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.iTuneLabel</code> attribute. 
	 * @param value the iTuneLabel
	 */
	public void setITuneLabel(final FooterNavigationComponent item, final String value)
	{
		setITuneLabel( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.iTuneLabel</code> attribute. 
	 * @param value the iTuneLabel
	 */
	public void setAllITuneLabel(final SessionContext ctx, final FooterNavigationComponent item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.FooterNavigationComponent.ITUNELABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.iTuneLabel</code> attribute. 
	 * @param value the iTuneLabel
	 */
	public void setAllITuneLabel(final FooterNavigationComponent item, final Map<Language,String> value)
	{
		setAllITuneLabel( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.iTunesImage</code> attribute.
	 * @return the iTunesImage
	 */
	public Media getITunesImage(final SessionContext ctx, final FooterNavigationComponent item)
	{
		return (Media)item.getProperty( ctx, SiteoneCoreConstants.Attributes.FooterNavigationComponent.ITUNESIMAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.iTunesImage</code> attribute.
	 * @return the iTunesImage
	 */
	public Media getITunesImage(final FooterNavigationComponent item)
	{
		return getITunesImage( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.iTunesImage</code> attribute. 
	 * @param value the iTunesImage
	 */
	public void setITunesImage(final SessionContext ctx, final FooterNavigationComponent item, final Media value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.FooterNavigationComponent.ITUNESIMAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.iTunesImage</code> attribute. 
	 * @param value the iTunesImage
	 */
	public void setITunesImage(final FooterNavigationComponent item, final Media value)
	{
		setITunesImage( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.iTunesUrl</code> attribute.
	 * @return the iTunesUrl
	 */
	public String getITunesUrl(final SessionContext ctx, final FooterNavigationComponent item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.FooterNavigationComponent.ITUNESURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.iTunesUrl</code> attribute.
	 * @return the iTunesUrl
	 */
	public String getITunesUrl(final FooterNavigationComponent item)
	{
		return getITunesUrl( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.iTunesUrl</code> attribute. 
	 * @param value the iTunesUrl
	 */
	public void setITunesUrl(final SessionContext ctx, final FooterNavigationComponent item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.FooterNavigationComponent.ITUNESURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.iTunesUrl</code> attribute. 
	 * @param value the iTunesUrl
	 */
	public void setITunesUrl(final FooterNavigationComponent item, final String value)
	{
		setITunesUrl( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.languagePreference</code> attribute.
	 * @return the languagePreference
	 */
	public Language getLanguagePreference(final SessionContext ctx, final B2BCustomer item)
	{
		return (Language)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.LANGUAGEPREFERENCE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.languagePreference</code> attribute.
	 * @return the languagePreference
	 */
	public Language getLanguagePreference(final B2BCustomer item)
	{
		return getLanguagePreference( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.languagePreference</code> attribute. 
	 * @param value the languagePreference
	 */
	public void setLanguagePreference(final SessionContext ctx, final B2BCustomer item, final Language value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.LANGUAGEPREFERENCE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.languagePreference</code> attribute. 
	 * @param value the languagePreference
	 */
	public void setLanguagePreference(final B2BCustomer item, final Language value)
	{
		setLanguagePreference( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.lastFeedTimeStamp</code> attribute.
	 * @return the lastFeedTimeStamp
	 */
	public Date getLastFeedTimeStamp(final SessionContext ctx, final StockLevel item)
	{
		return (Date)item.getProperty( ctx, SiteoneCoreConstants.Attributes.StockLevel.LASTFEEDTIMESTAMP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.lastFeedTimeStamp</code> attribute.
	 * @return the lastFeedTimeStamp
	 */
	public Date getLastFeedTimeStamp(final StockLevel item)
	{
		return getLastFeedTimeStamp( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.lastFeedTimeStamp</code> attribute. 
	 * @param value the lastFeedTimeStamp
	 */
	public void setLastFeedTimeStamp(final SessionContext ctx, final StockLevel item, final Date value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.StockLevel.LASTFEEDTIMESTAMP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.lastFeedTimeStamp</code> attribute. 
	 * @param value the lastFeedTimeStamp
	 */
	public void setLastFeedTimeStamp(final StockLevel item, final Date value)
	{
		setLastFeedTimeStamp( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.lastModifiedTimeStamp</code> attribute.
	 * @return the lastModifiedTimeStamp
	 */
	public Date getLastModifiedTimeStamp(final SessionContext ctx, final Product item)
	{
		return (Date)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.LASTMODIFIEDTIMESTAMP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.lastModifiedTimeStamp</code> attribute.
	 * @return the lastModifiedTimeStamp
	 */
	public Date getLastModifiedTimeStamp(final Product item)
	{
		return getLastModifiedTimeStamp( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.lastModifiedTimeStamp</code> attribute. 
	 * @param value the lastModifiedTimeStamp
	 */
	public void setLastModifiedTimeStamp(final SessionContext ctx, final Product item, final Date value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.LASTMODIFIEDTIMESTAMP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.lastModifiedTimeStamp</code> attribute. 
	 * @param value the lastModifiedTimeStamp
	 */
	public void setLastModifiedTimeStamp(final Product item, final Date value)
	{
		setLastModifiedTimeStamp( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.lastName</code> attribute.
	 * @return the lastName
	 */
	public String getLastName(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.LASTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.lastName</code> attribute.
	 * @return the lastName
	 */
	public String getLastName(final B2BCustomer item)
	{
		return getLastName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.lastName</code> attribute. 
	 * @param value the lastName
	 */
	public void setLastName(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.LASTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.lastName</code> attribute. 
	 * @param value the lastName
	 */
	public void setLastName(final B2BCustomer item, final String value)
	{
		setLastName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.licenseEndDate</code> attribute.
	 * @return the licenseEndDate
	 */
	public Date getLicenseEndDate(final SessionContext ctx, final PointOfService item)
	{
		return (Date)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.LICENSEENDDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.licenseEndDate</code> attribute.
	 * @return the licenseEndDate
	 */
	public Date getLicenseEndDate(final PointOfService item)
	{
		return getLicenseEndDate( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.licenseEndDate</code> attribute. 
	 * @param value the licenseEndDate
	 */
	public void setLicenseEndDate(final SessionContext ctx, final PointOfService item, final Date value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.LICENSEENDDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.licenseEndDate</code> attribute. 
	 * @param value the licenseEndDate
	 */
	public void setLicenseEndDate(final PointOfService item, final Date value)
	{
		setLicenseEndDate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.licenseStartDate</code> attribute.
	 * @return the licenseStartDate
	 */
	public Date getLicenseStartDate(final SessionContext ctx, final PointOfService item)
	{
		return (Date)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.LICENSESTARTDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.licenseStartDate</code> attribute.
	 * @return the licenseStartDate
	 */
	public Date getLicenseStartDate(final PointOfService item)
	{
		return getLicenseStartDate( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.licenseStartDate</code> attribute. 
	 * @param value the licenseStartDate
	 */
	public void setLicenseStartDate(final SessionContext ctx, final PointOfService item, final Date value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.LICENSESTARTDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.licenseStartDate</code> attribute. 
	 * @param value the licenseStartDate
	 */
	public void setLicenseStartDate(final PointOfService item, final Date value)
	{
		setLicenseStartDate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.lifeStyles</code> attribute.
	 * @return the lifeStyles - A list of lifestyle images for the product.
	 */
	public List<MediaContainer> getLifeStyles(final SessionContext ctx, final Product item)
	{
		List<MediaContainer> coll = (List<MediaContainer>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.LIFESTYLES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.lifeStyles</code> attribute.
	 * @return the lifeStyles - A list of lifestyle images for the product.
	 */
	public List<MediaContainer> getLifeStyles(final Product item)
	{
		return getLifeStyles( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.lifeStyles</code> attribute. 
	 * @param value the lifeStyles - A list of lifestyle images for the product.
	 */
	public void setLifeStyles(final SessionContext ctx, final Product item, final List<MediaContainer> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.LIFESTYLES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.lifeStyles</code> attribute. 
	 * @param value the lifeStyles - A list of lifestyle images for the product.
	 */
	public void setLifeStyles(final Product item, final List<MediaContainer> value)
	{
		setLifeStyles( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.listPrice</code> attribute.
	 * @return the listPrice
	 */
	public Double getListPrice(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.LISTPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.listPrice</code> attribute.
	 * @return the listPrice
	 */
	public Double getListPrice(final AbstractOrderEntry item)
	{
		return getListPrice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.listPrice</code> attribute. 
	 * @return the listPrice
	 */
	public double getListPriceAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getListPrice( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.listPrice</code> attribute. 
	 * @return the listPrice
	 */
	public double getListPriceAsPrimitive(final AbstractOrderEntry item)
	{
		return getListPriceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.listPrice</code> attribute. 
	 * @param value the listPrice
	 */
	public void setListPrice(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.LISTPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.listPrice</code> attribute. 
	 * @param value the listPrice
	 */
	public void setListPrice(final AbstractOrderEntry item, final Double value)
	{
		setListPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.listPrice</code> attribute. 
	 * @param value the listPrice
	 */
	public void setListPrice(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setListPrice( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.listPrice</code> attribute. 
	 * @param value the listPrice
	 */
	public void setListPrice(final AbstractOrderEntry item, final double value)
	{
		setListPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.listType</code> attribute.
	 * @return the listType
	 */
	public EnumerationValue getListType(final SessionContext ctx, final Wishlist2 item)
	{
		return (EnumerationValue)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Wishlist2.LISTTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.listType</code> attribute.
	 * @return the listType
	 */
	public EnumerationValue getListType(final Wishlist2 item)
	{
		return getListType( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.listType</code> attribute. 
	 * @param value the listType
	 */
	public void setListType(final SessionContext ctx, final Wishlist2 item, final EnumerationValue value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Wishlist2.LISTTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.listType</code> attribute. 
	 * @param value the listType
	 */
	public void setListType(final Wishlist2 item, final EnumerationValue value)
	{
		setListType( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderProcess.logMessage</code> attribute.
	 * @return the logMessage
	 */
	public String getLogMessage(final SessionContext ctx, final OrderProcess item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.OrderProcess.LOGMESSAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderProcess.logMessage</code> attribute.
	 * @return the logMessage
	 */
	public String getLogMessage(final OrderProcess item)
	{
		return getLogMessage( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderProcess.logMessage</code> attribute. 
	 * @param value the logMessage
	 */
	public void setLogMessage(final SessionContext ctx, final OrderProcess item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.OrderProcess.LOGMESSAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderProcess.logMessage</code> attribute. 
	 * @param value the logMessage
	 */
	public void setLogMessage(final OrderProcess item, final String value)
	{
		setLogMessage( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.longDescription</code> attribute.
	 * @return the longDescription
	 */
	public String getLongDescription(final SessionContext ctx, final PointOfService item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedPointOfService.getLongDescription requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.LONGDESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.longDescription</code> attribute.
	 * @return the longDescription
	 */
	public String getLongDescription(final PointOfService item)
	{
		return getLongDescription( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.longDescription</code> attribute. 
	 * @return the localized longDescription
	 */
	public Map<Language,String> getAllLongDescription(final SessionContext ctx, final PointOfService item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.PointOfService.LONGDESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.longDescription</code> attribute. 
	 * @return the localized longDescription
	 */
	public Map<Language,String> getAllLongDescription(final PointOfService item)
	{
		return getAllLongDescription( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.longDescription</code> attribute. 
	 * @param value the longDescription
	 */
	public void setLongDescription(final SessionContext ctx, final PointOfService item, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedPointOfService.setLongDescription requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.LONGDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.longDescription</code> attribute. 
	 * @param value the longDescription
	 */
	public void setLongDescription(final PointOfService item, final String value)
	{
		setLongDescription( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.longDescription</code> attribute. 
	 * @param value the longDescription
	 */
	public void setAllLongDescription(final SessionContext ctx, final PointOfService item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.PointOfService.LONGDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.longDescription</code> attribute. 
	 * @param value the longDescription
	 */
	public void setAllLongDescription(final PointOfService item, final Map<Language,String> value)
	{
		setAllLongDescription( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.marketingBanner</code> attribute.
	 * @return the marketingBanner - Marketing Banner
	 */
	public Media getMarketingBanner(final SessionContext ctx, final Category item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.getMarketingBanner requires a session language", 0 );
		}
		return (Media)item.getLocalizedProperty( ctx, SiteoneCoreConstants.Attributes.Category.MARKETINGBANNER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.marketingBanner</code> attribute.
	 * @return the marketingBanner - Marketing Banner
	 */
	public Media getMarketingBanner(final Category item)
	{
		return getMarketingBanner( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.marketingBanner</code> attribute. 
	 * @return the localized marketingBanner - Marketing Banner
	 */
	public Map<Language,Media> getAllMarketingBanner(final SessionContext ctx, final Category item)
	{
		return (Map<Language,Media>)item.getAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.Category.MARKETINGBANNER,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.marketingBanner</code> attribute. 
	 * @return the localized marketingBanner - Marketing Banner
	 */
	public Map<Language,Media> getAllMarketingBanner(final Category item)
	{
		return getAllMarketingBanner( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.marketingBanner</code> attribute. 
	 * @param value the marketingBanner - Marketing Banner
	 */
	public void setMarketingBanner(final SessionContext ctx, final Category item, final Media value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.setMarketingBanner requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteoneCoreConstants.Attributes.Category.MARKETINGBANNER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.marketingBanner</code> attribute. 
	 * @param value the marketingBanner - Marketing Banner
	 */
	public void setMarketingBanner(final Category item, final Media value)
	{
		setMarketingBanner( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.marketingBanner</code> attribute. 
	 * @param value the marketingBanner - Marketing Banner
	 */
	public void setAllMarketingBanner(final SessionContext ctx, final Category item, final Map<Language,Media> value)
	{
		item.setAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.Category.MARKETINGBANNER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.marketingBanner</code> attribute. 
	 * @param value the marketingBanner - Marketing Banner
	 */
	public void setAllMarketingBanner(final Category item, final Map<Language,Media> value)
	{
		setAllMarketingBanner( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.marketingBannerLink</code> attribute.
	 * @return the marketingBannerLink - Marketing Banner Link
	 */
	public String getMarketingBannerLink(final SessionContext ctx, final Category item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Category.MARKETINGBANNERLINK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.marketingBannerLink</code> attribute.
	 * @return the marketingBannerLink - Marketing Banner Link
	 */
	public String getMarketingBannerLink(final Category item)
	{
		return getMarketingBannerLink( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.marketingBannerLink</code> attribute. 
	 * @param value the marketingBannerLink - Marketing Banner Link
	 */
	public void setMarketingBannerLink(final SessionContext ctx, final Category item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Category.MARKETINGBANNERLINK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.marketingBannerLink</code> attribute. 
	 * @param value the marketingBannerLink - Marketing Banner Link
	 */
	public void setMarketingBannerLink(final Category item, final String value)
	{
		setMarketingBannerLink( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.matchGrade</code> attribute.
	 * @return the matchGrade
	 */
	public String getMatchGrade(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.MATCHGRADE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.matchGrade</code> attribute.
	 * @return the matchGrade
	 */
	public String getMatchGrade(final B2BUnit item)
	{
		return getMatchGrade( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.matchGrade</code> attribute. 
	 * @param value the matchGrade
	 */
	public void setMatchGrade(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.MATCHGRADE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.matchGrade</code> attribute. 
	 * @param value the matchGrade
	 */
	public void setMatchGrade(final B2BUnit item, final String value)
	{
		setMatchGrade( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.maxContents</code> attribute.
	 * @return the maxContents - Determines the max number of content to display in the component.
	 */
	public Integer getMaxContents(final SessionContext ctx, final SearchBoxComponent item)
	{
		return (Integer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.SearchBoxComponent.MAXCONTENTS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.maxContents</code> attribute.
	 * @return the maxContents - Determines the max number of content to display in the component.
	 */
	public Integer getMaxContents(final SearchBoxComponent item)
	{
		return getMaxContents( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.maxContents</code> attribute. 
	 * @return the maxContents - Determines the max number of content to display in the component.
	 */
	public int getMaxContentsAsPrimitive(final SessionContext ctx, final SearchBoxComponent item)
	{
		Integer value = getMaxContents( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SearchBoxComponent.maxContents</code> attribute. 
	 * @return the maxContents - Determines the max number of content to display in the component.
	 */
	public int getMaxContentsAsPrimitive(final SearchBoxComponent item)
	{
		return getMaxContentsAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.maxContents</code> attribute. 
	 * @param value the maxContents - Determines the max number of content to display in the component.
	 */
	public void setMaxContents(final SessionContext ctx, final SearchBoxComponent item, final Integer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.SearchBoxComponent.MAXCONTENTS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.maxContents</code> attribute. 
	 * @param value the maxContents - Determines the max number of content to display in the component.
	 */
	public void setMaxContents(final SearchBoxComponent item, final Integer value)
	{
		setMaxContents( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.maxContents</code> attribute. 
	 * @param value the maxContents - Determines the max number of content to display in the component.
	 */
	public void setMaxContents(final SessionContext ctx, final SearchBoxComponent item, final int value)
	{
		setMaxContents( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SearchBoxComponent.maxContents</code> attribute. 
	 * @param value the maxContents - Determines the max number of content to display in the component.
	 */
	public void setMaxContents(final SearchBoxComponent item, final int value)
	{
		setMaxContents( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.maxShippableDollarAmount</code> attribute.
	 * @return the maxShippableDollarAmount
	 */
	public Double getMaxShippableDollarAmount(final SessionContext ctx, final Product item)
	{
		return (Double)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.MAXSHIPPABLEDOLLARAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.maxShippableDollarAmount</code> attribute.
	 * @return the maxShippableDollarAmount
	 */
	public Double getMaxShippableDollarAmount(final Product item)
	{
		return getMaxShippableDollarAmount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.maxShippableDollarAmount</code> attribute. 
	 * @return the maxShippableDollarAmount
	 */
	public double getMaxShippableDollarAmountAsPrimitive(final SessionContext ctx, final Product item)
	{
		Double value = getMaxShippableDollarAmount( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.maxShippableDollarAmount</code> attribute. 
	 * @return the maxShippableDollarAmount
	 */
	public double getMaxShippableDollarAmountAsPrimitive(final Product item)
	{
		return getMaxShippableDollarAmountAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.maxShippableDollarAmount</code> attribute. 
	 * @param value the maxShippableDollarAmount
	 */
	public void setMaxShippableDollarAmount(final SessionContext ctx, final Product item, final Double value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.MAXSHIPPABLEDOLLARAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.maxShippableDollarAmount</code> attribute. 
	 * @param value the maxShippableDollarAmount
	 */
	public void setMaxShippableDollarAmount(final Product item, final Double value)
	{
		setMaxShippableDollarAmount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.maxShippableDollarAmount</code> attribute. 
	 * @param value the maxShippableDollarAmount
	 */
	public void setMaxShippableDollarAmount(final SessionContext ctx, final Product item, final double value)
	{
		setMaxShippableDollarAmount( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.maxShippableDollarAmount</code> attribute. 
	 * @param value the maxShippableDollarAmount
	 */
	public void setMaxShippableDollarAmount(final Product item, final double value)
	{
		setMaxShippableDollarAmount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.maxShippableQuantity</code> attribute.
	 * @return the maxShippableQuantity
	 */
	public Integer getMaxShippableQuantity(final SessionContext ctx, final Product item)
	{
		return (Integer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.MAXSHIPPABLEQUANTITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.maxShippableQuantity</code> attribute.
	 * @return the maxShippableQuantity
	 */
	public Integer getMaxShippableQuantity(final Product item)
	{
		return getMaxShippableQuantity( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.maxShippableQuantity</code> attribute. 
	 * @return the maxShippableQuantity
	 */
	public int getMaxShippableQuantityAsPrimitive(final SessionContext ctx, final Product item)
	{
		Integer value = getMaxShippableQuantity( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.maxShippableQuantity</code> attribute. 
	 * @return the maxShippableQuantity
	 */
	public int getMaxShippableQuantityAsPrimitive(final Product item)
	{
		return getMaxShippableQuantityAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.maxShippableQuantity</code> attribute. 
	 * @param value the maxShippableQuantity
	 */
	public void setMaxShippableQuantity(final SessionContext ctx, final Product item, final Integer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.MAXSHIPPABLEQUANTITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.maxShippableQuantity</code> attribute. 
	 * @param value the maxShippableQuantity
	 */
	public void setMaxShippableQuantity(final Product item, final Integer value)
	{
		setMaxShippableQuantity( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.maxShippableQuantity</code> attribute. 
	 * @param value the maxShippableQuantity
	 */
	public void setMaxShippableQuantity(final SessionContext ctx, final Product item, final int value)
	{
		setMaxShippableQuantity( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.maxShippableQuantity</code> attribute. 
	 * @param value the maxShippableQuantity
	 */
	public void setMaxShippableQuantity(final Product item, final int value)
	{
		setMaxShippableQuantity( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractPromotion.maxValue</code> attribute.
	 * @return the maxValue
	 */
	public String getMaxValue(final SessionContext ctx, final AbstractPromotion item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractPromotion.MAXVALUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractPromotion.maxValue</code> attribute.
	 * @return the maxValue
	 */
	public String getMaxValue(final AbstractPromotion item)
	{
		return getMaxValue( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractPromotion.maxValue</code> attribute. 
	 * @param value the maxValue
	 */
	public void setMaxValue(final SessionContext ctx, final AbstractPromotion item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractPromotion.MAXVALUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractPromotion.maxValue</code> attribute. 
	 * @param value the maxValue
	 */
	public void setMaxValue(final AbstractPromotion item, final String value)
	{
		setMaxValue( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.metroStatArea</code> attribute.
	 * @return the metroStatArea
	 */
	public String getMetroStatArea(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.METROSTATAREA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.metroStatArea</code> attribute.
	 * @return the metroStatArea
	 */
	public String getMetroStatArea(final PointOfService item)
	{
		return getMetroStatArea( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.metroStatArea</code> attribute. 
	 * @param value the metroStatArea
	 */
	public void setMetroStatArea(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.METROSTATAREA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.metroStatArea</code> attribute. 
	 * @param value the metroStatArea
	 */
	public void setMetroStatArea(final PointOfService item, final String value)
	{
		setMetroStatArea( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CxCustomization.modifiedRank</code> attribute.
	 * @return the modifiedRank
	 */
	public Integer getModifiedRank(final SessionContext ctx, final CxCustomization item)
	{
		return (Integer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.CxCustomization.MODIFIEDRANK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CxCustomization.modifiedRank</code> attribute.
	 * @return the modifiedRank
	 */
	public Integer getModifiedRank(final CxCustomization item)
	{
		return getModifiedRank( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CxCustomization.modifiedRank</code> attribute. 
	 * @return the modifiedRank
	 */
	public int getModifiedRankAsPrimitive(final SessionContext ctx, final CxCustomization item)
	{
		Integer value = getModifiedRank( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CxCustomization.modifiedRank</code> attribute. 
	 * @return the modifiedRank
	 */
	public int getModifiedRankAsPrimitive(final CxCustomization item)
	{
		return getModifiedRankAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CxCustomization.modifiedRank</code> attribute. 
	 * @param value the modifiedRank
	 */
	public void setModifiedRank(final SessionContext ctx, final CxCustomization item, final Integer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.CxCustomization.MODIFIEDRANK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CxCustomization.modifiedRank</code> attribute. 
	 * @param value the modifiedRank
	 */
	public void setModifiedRank(final CxCustomization item, final Integer value)
	{
		setModifiedRank( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CxCustomization.modifiedRank</code> attribute. 
	 * @param value the modifiedRank
	 */
	public void setModifiedRank(final SessionContext ctx, final CxCustomization item, final int value)
	{
		setModifiedRank( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CxCustomization.modifiedRank</code> attribute. 
	 * @param value the modifiedRank
	 */
	public void setModifiedRank(final CxCustomization item, final int value)
	{
		setModifiedRank( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.nearbyStoreSearchRadius</code> attribute.
	 * @return the nearbyStoreSearchRadius
	 */
	public Integer getNearbyStoreSearchRadius(final SessionContext ctx, final PointOfService item)
	{
		return (Integer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.NEARBYSTORESEARCHRADIUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.nearbyStoreSearchRadius</code> attribute.
	 * @return the nearbyStoreSearchRadius
	 */
	public Integer getNearbyStoreSearchRadius(final PointOfService item)
	{
		return getNearbyStoreSearchRadius( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.nearbyStoreSearchRadius</code> attribute. 
	 * @return the nearbyStoreSearchRadius
	 */
	public int getNearbyStoreSearchRadiusAsPrimitive(final SessionContext ctx, final PointOfService item)
	{
		Integer value = getNearbyStoreSearchRadius( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.nearbyStoreSearchRadius</code> attribute. 
	 * @return the nearbyStoreSearchRadius
	 */
	public int getNearbyStoreSearchRadiusAsPrimitive(final PointOfService item)
	{
		return getNearbyStoreSearchRadiusAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.nearbyStoreSearchRadius</code> attribute. 
	 * @param value the nearbyStoreSearchRadius
	 */
	public void setNearbyStoreSearchRadius(final SessionContext ctx, final PointOfService item, final Integer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.NEARBYSTORESEARCHRADIUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.nearbyStoreSearchRadius</code> attribute. 
	 * @param value the nearbyStoreSearchRadius
	 */
	public void setNearbyStoreSearchRadius(final PointOfService item, final Integer value)
	{
		setNearbyStoreSearchRadius( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.nearbyStoreSearchRadius</code> attribute. 
	 * @param value the nearbyStoreSearchRadius
	 */
	public void setNearbyStoreSearchRadius(final SessionContext ctx, final PointOfService item, final int value)
	{
		setNearbyStoreSearchRadius( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.nearbyStoreSearchRadius</code> attribute. 
	 * @param value the nearbyStoreSearchRadius
	 */
	public void setNearbyStoreSearchRadius(final PointOfService item, final int value)
	{
		setNearbyStoreSearchRadius( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.needsOrderApproval</code> attribute.
	 * @return the needsOrderApproval
	 */
	public Boolean isNeedsOrderApproval(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.NEEDSORDERAPPROVAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.needsOrderApproval</code> attribute.
	 * @return the needsOrderApproval
	 */
	public Boolean isNeedsOrderApproval(final B2BCustomer item)
	{
		return isNeedsOrderApproval( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.needsOrderApproval</code> attribute. 
	 * @return the needsOrderApproval
	 */
	public boolean isNeedsOrderApprovalAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isNeedsOrderApproval( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.needsOrderApproval</code> attribute. 
	 * @return the needsOrderApproval
	 */
	public boolean isNeedsOrderApprovalAsPrimitive(final B2BCustomer item)
	{
		return isNeedsOrderApprovalAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.needsOrderApproval</code> attribute. 
	 * @param value the needsOrderApproval
	 */
	public void setNeedsOrderApproval(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.NEEDSORDERAPPROVAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.needsOrderApproval</code> attribute. 
	 * @param value the needsOrderApproval
	 */
	public void setNeedsOrderApproval(final B2BCustomer item, final Boolean value)
	{
		setNeedsOrderApproval( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.needsOrderApproval</code> attribute. 
	 * @param value the needsOrderApproval
	 */
	public void setNeedsOrderApproval(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setNeedsOrderApproval( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.needsOrderApproval</code> attribute. 
	 * @param value the needsOrderApproval
	 */
	public void setNeedsOrderApproval(final B2BCustomer item, final boolean value)
	{
		setNeedsOrderApproval( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractPromotion.notes</code> attribute.
	 * @return the notes
	 */
	public String getNotes(final SessionContext ctx, final AbstractPromotion item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractPromotion.NOTES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractPromotion.notes</code> attribute.
	 * @return the notes
	 */
	public String getNotes(final AbstractPromotion item)
	{
		return getNotes( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractPromotion.notes</code> attribute. 
	 * @param value the notes
	 */
	public void setNotes(final SessionContext ctx, final AbstractPromotion item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractPromotion.NOTES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractPromotion.notes</code> attribute. 
	 * @param value the notes
	 */
	public void setNotes(final AbstractPromotion item, final String value)
	{
		setNotes( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.numberOfEmployees</code> attribute.
	 * @return the numberOfEmployees - Number of Employees
	 */
	public String getNumberOfEmployees(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.NUMBEROFEMPLOYEES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.numberOfEmployees</code> attribute.
	 * @return the numberOfEmployees - Number of Employees
	 */
	public String getNumberOfEmployees(final B2BUnit item)
	{
		return getNumberOfEmployees( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.numberOfEmployees</code> attribute. 
	 * @param value the numberOfEmployees - Number of Employees
	 */
	public void setNumberOfEmployees(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.NUMBEROFEMPLOYEES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.numberOfEmployees</code> attribute. 
	 * @param value the numberOfEmployees - Number of Employees
	 */
	public void setNumberOfEmployees(final B2BUnit item, final String value)
	{
		setNumberOfEmployees( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.nurseryBuyingGroup</code> attribute.
	 * @return the nurseryBuyingGroup
	 */
	public String getNurseryBuyingGroup(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.NURSERYBUYINGGROUP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.nurseryBuyingGroup</code> attribute.
	 * @return the nurseryBuyingGroup
	 */
	public String getNurseryBuyingGroup(final PointOfService item)
	{
		return getNurseryBuyingGroup( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.nurseryBuyingGroup</code> attribute. 
	 * @param value the nurseryBuyingGroup
	 */
	public void setNurseryBuyingGroup(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.NURSERYBUYINGGROUP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.nurseryBuyingGroup</code> attribute. 
	 * @param value the nurseryBuyingGroup
	 */
	public void setNurseryBuyingGroup(final PointOfService item, final String value)
	{
		setNurseryBuyingGroup( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.nurseryClassCode</code> attribute.
	 * @return the nurseryClassCode
	 */
	public String getNurseryClassCode(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.NURSERYCLASSCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.nurseryClassCode</code> attribute.
	 * @return the nurseryClassCode
	 */
	public String getNurseryClassCode(final B2BUnit item)
	{
		return getNurseryClassCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.nurseryClassCode</code> attribute. 
	 * @param value the nurseryClassCode
	 */
	public void setNurseryClassCode(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.NURSERYCLASSCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.nurseryClassCode</code> attribute. 
	 * @param value the nurseryClassCode
	 */
	public void setNurseryClassCode(final B2BUnit item, final String value)
	{
		setNurseryClassCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.nurseryOfferURL</code> attribute.
	 * @return the nurseryOfferURL
	 */
	public String getNurseryOfferURL(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.NURSERYOFFERURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.nurseryOfferURL</code> attribute.
	 * @return the nurseryOfferURL
	 */
	public String getNurseryOfferURL(final PointOfService item)
	{
		return getNurseryOfferURL( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.nurseryOfferURL</code> attribute. 
	 * @param value the nurseryOfferURL
	 */
	public void setNurseryOfferURL(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.NURSERYOFFERURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.nurseryOfferURL</code> attribute. 
	 * @param value the nurseryOfferURL
	 */
	public void setNurseryOfferURL(final PointOfService item, final String value)
	{
		setNurseryOfferURL( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.oldContactEmail</code> attribute.
	 * @return the oldContactEmail
	 */
	public String getOldContactEmail(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.OLDCONTACTEMAIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.oldContactEmail</code> attribute.
	 * @return the oldContactEmail
	 */
	public String getOldContactEmail(final B2BCustomer item)
	{
		return getOldContactEmail( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.oldContactEmail</code> attribute. 
	 * @param value the oldContactEmail
	 */
	public void setOldContactEmail(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.OLDCONTACTEMAIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.oldContactEmail</code> attribute. 
	 * @param value the oldContactEmail
	 */
	public void setOldContactEmail(final B2BCustomer item, final String value)
	{
		setOldContactEmail( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.onHand</code> attribute.
	 * @return the onHand
	 */
	public Integer getOnHand(final SessionContext ctx, final StockLevel item)
	{
		return (Integer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.StockLevel.ONHAND);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.onHand</code> attribute.
	 * @return the onHand
	 */
	public Integer getOnHand(final StockLevel item)
	{
		return getOnHand( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.onHand</code> attribute. 
	 * @return the onHand
	 */
	public int getOnHandAsPrimitive(final SessionContext ctx, final StockLevel item)
	{
		Integer value = getOnHand( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.onHand</code> attribute. 
	 * @return the onHand
	 */
	public int getOnHandAsPrimitive(final StockLevel item)
	{
		return getOnHandAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.onHand</code> attribute. 
	 * @param value the onHand
	 */
	public void setOnHand(final SessionContext ctx, final StockLevel item, final Integer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.StockLevel.ONHAND,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.onHand</code> attribute. 
	 * @param value the onHand
	 */
	public void setOnHand(final StockLevel item, final Integer value)
	{
		setOnHand( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.onHand</code> attribute. 
	 * @param value the onHand
	 */
	public void setOnHand(final SessionContext ctx, final StockLevel item, final int value)
	{
		setOnHand( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.onHand</code> attribute. 
	 * @param value the onHand
	 */
	public void setOnHand(final StockLevel item, final int value)
	{
		setOnHand( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.onHandProducts</code> attribute.
	 * @return the onHandProducts
	 */
	public Collection<Product> getOnHandProducts(final SessionContext ctx, final PointOfService item)
	{
		final List<Product> items = item.getLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.PRODUCT2ONHANDSTORESRELATION,
			"Product",
			null,
			Utilities.getRelationOrderingOverride(PRODUCT2ONHANDSTORESRELATION_SRC_ORDERED, true),
			false
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.onHandProducts</code> attribute.
	 * @return the onHandProducts
	 */
	public Collection<Product> getOnHandProducts(final PointOfService item)
	{
		return getOnHandProducts( getSession().getSessionContext(), item );
	}
	
	public long getOnHandProductsCount(final SessionContext ctx, final PointOfService item)
	{
		return item.getLinkedItemsCount(
			ctx,
			false,
			SiteoneCoreConstants.Relations.PRODUCT2ONHANDSTORESRELATION,
			"Product",
			null
		);
	}
	
	public long getOnHandProductsCount(final PointOfService item)
	{
		return getOnHandProductsCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.onHandProducts</code> attribute. 
	 * @param value the onHandProducts
	 */
	public void setOnHandProducts(final SessionContext ctx, final PointOfService item, final Collection<Product> value)
	{
		item.setLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.PRODUCT2ONHANDSTORESRELATION,
			null,
			value,
			Utilities.getRelationOrderingOverride(PRODUCT2ONHANDSTORESRELATION_SRC_ORDERED, true),
			false,
			Utilities.getMarkModifiedOverride(PRODUCT2ONHANDSTORESRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.onHandProducts</code> attribute. 
	 * @param value the onHandProducts
	 */
	public void setOnHandProducts(final PointOfService item, final Collection<Product> value)
	{
		setOnHandProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to onHandProducts. 
	 * @param value the item to add to onHandProducts
	 */
	public void addToOnHandProducts(final SessionContext ctx, final PointOfService item, final Product value)
	{
		item.addLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.PRODUCT2ONHANDSTORESRELATION,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(PRODUCT2ONHANDSTORESRELATION_SRC_ORDERED, true),
			false,
			Utilities.getMarkModifiedOverride(PRODUCT2ONHANDSTORESRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to onHandProducts. 
	 * @param value the item to add to onHandProducts
	 */
	public void addToOnHandProducts(final PointOfService item, final Product value)
	{
		addToOnHandProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from onHandProducts. 
	 * @param value the item to remove from onHandProducts
	 */
	public void removeFromOnHandProducts(final SessionContext ctx, final PointOfService item, final Product value)
	{
		item.removeLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.PRODUCT2ONHANDSTORESRELATION,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(PRODUCT2ONHANDSTORESRELATION_SRC_ORDERED, true),
			false,
			Utilities.getMarkModifiedOverride(PRODUCT2ONHANDSTORESRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from onHandProducts. 
	 * @param value the item to remove from onHandProducts
	 */
	public void removeFromOnHandProducts(final PointOfService item, final Product value)
	{
		removeFromOnHandProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.onHandStores</code> attribute.
	 * @return the onHandStores
	 */
	public List<PointOfService> getOnHandStores(final SessionContext ctx, final Product item)
	{
		final List<PointOfService> items = item.getLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.PRODUCT2ONHANDSTORESRELATION,
			"PointOfService",
			null,
			Utilities.getRelationOrderingOverride(PRODUCT2ONHANDSTORESRELATION_SRC_ORDERED, true),
			false
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.onHandStores</code> attribute.
	 * @return the onHandStores
	 */
	public List<PointOfService> getOnHandStores(final Product item)
	{
		return getOnHandStores( getSession().getSessionContext(), item );
	}
	
	public long getOnHandStoresCount(final SessionContext ctx, final Product item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			SiteoneCoreConstants.Relations.PRODUCT2ONHANDSTORESRELATION,
			"PointOfService",
			null
		);
	}
	
	public long getOnHandStoresCount(final Product item)
	{
		return getOnHandStoresCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.onHandStores</code> attribute. 
	 * @param value the onHandStores
	 */
	public void setOnHandStores(final SessionContext ctx, final Product item, final List<PointOfService> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.PRODUCT2ONHANDSTORESRELATION,
			null,
			value,
			Utilities.getRelationOrderingOverride(PRODUCT2ONHANDSTORESRELATION_SRC_ORDERED, true),
			false,
			Utilities.getMarkModifiedOverride(PRODUCT2ONHANDSTORESRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.onHandStores</code> attribute. 
	 * @param value the onHandStores
	 */
	public void setOnHandStores(final Product item, final List<PointOfService> value)
	{
		setOnHandStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to onHandStores. 
	 * @param value the item to add to onHandStores
	 */
	public void addToOnHandStores(final SessionContext ctx, final Product item, final PointOfService value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.PRODUCT2ONHANDSTORESRELATION,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(PRODUCT2ONHANDSTORESRELATION_SRC_ORDERED, true),
			false,
			Utilities.getMarkModifiedOverride(PRODUCT2ONHANDSTORESRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to onHandStores. 
	 * @param value the item to add to onHandStores
	 */
	public void addToOnHandStores(final Product item, final PointOfService value)
	{
		addToOnHandStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from onHandStores. 
	 * @param value the item to remove from onHandStores
	 */
	public void removeFromOnHandStores(final SessionContext ctx, final Product item, final PointOfService value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.PRODUCT2ONHANDSTORESRELATION,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(PRODUCT2ONHANDSTORESRELATION_SRC_ORDERED, true),
			false,
			Utilities.getMarkModifiedOverride(PRODUCT2ONHANDSTORESRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from onHandStores. 
	 * @param value the item to remove from onHandStores
	 */
	public void removeFromOnHandStores(final Product item, final PointOfService value)
	{
		removeFromOnHandStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CouponRedemption.orderDate</code> attribute.
	 * @return the orderDate
	 */
	public Date getOrderDate(final SessionContext ctx, final CouponRedemption item)
	{
		return (Date)item.getProperty( ctx, SiteoneCoreConstants.Attributes.CouponRedemption.ORDERDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CouponRedemption.orderDate</code> attribute.
	 * @return the orderDate
	 */
	public Date getOrderDate(final CouponRedemption item)
	{
		return getOrderDate( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CouponRedemption.orderDate</code> attribute. 
	 * @param value the orderDate
	 */
	public void setOrderDate(final SessionContext ctx, final CouponRedemption item, final Date value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.CouponRedemption.ORDERDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CouponRedemption.orderDate</code> attribute. 
	 * @param value the orderDate
	 */
	public void setOrderDate(final CouponRedemption item, final Date value)
	{
		setOrderDate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.orderingAccount</code> attribute.
	 * @return the orderingAccount
	 */
	public B2BUnit getOrderingAccount(final SessionContext ctx, final AbstractOrder item)
	{
		return (B2BUnit)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ORDERINGACCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.orderingAccount</code> attribute.
	 * @return the orderingAccount
	 */
	public B2BUnit getOrderingAccount(final AbstractOrder item)
	{
		return getOrderingAccount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.orderingAccount</code> attribute. 
	 * @param value the orderingAccount
	 */
	public void setOrderingAccount(final SessionContext ctx, final AbstractOrder item, final B2BUnit value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ORDERINGACCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.orderingAccount</code> attribute. 
	 * @param value the orderingAccount
	 */
	public void setOrderingAccount(final AbstractOrder item, final B2BUnit value)
	{
		setOrderingAccount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CouponRedemption.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber(final SessionContext ctx, final CouponRedemption item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.CouponRedemption.ORDERNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CouponRedemption.orderNumber</code> attribute.
	 * @return the orderNumber
	 */
	public String getOrderNumber(final CouponRedemption item)
	{
		return getOrderNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CouponRedemption.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final SessionContext ctx, final CouponRedemption item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.CouponRedemption.ORDERNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CouponRedemption.orderNumber</code> attribute. 
	 * @param value the orderNumber
	 */
	public void setOrderNumber(final CouponRedemption item, final String value)
	{
		setOrderNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.orderPromoOption</code> attribute.
	 * @return the orderPromoOption
	 */
	public Boolean isOrderPromoOption(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ORDERPROMOOPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.orderPromoOption</code> attribute.
	 * @return the orderPromoOption
	 */
	public Boolean isOrderPromoOption(final B2BCustomer item)
	{
		return isOrderPromoOption( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.orderPromoOption</code> attribute. 
	 * @return the orderPromoOption
	 */
	public boolean isOrderPromoOptionAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isOrderPromoOption( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.orderPromoOption</code> attribute. 
	 * @return the orderPromoOption
	 */
	public boolean isOrderPromoOptionAsPrimitive(final B2BCustomer item)
	{
		return isOrderPromoOptionAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.orderPromoOption</code> attribute. 
	 * @param value the orderPromoOption
	 */
	public void setOrderPromoOption(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.ORDERPROMOOPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.orderPromoOption</code> attribute. 
	 * @param value the orderPromoOption
	 */
	public void setOrderPromoOption(final B2BCustomer item, final Boolean value)
	{
		setOrderPromoOption( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.orderPromoOption</code> attribute. 
	 * @param value the orderPromoOption
	 */
	public void setOrderPromoOption(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setOrderPromoOption( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.orderPromoOption</code> attribute. 
	 * @param value the orderPromoOption
	 */
	public void setOrderPromoOption(final B2BCustomer item, final boolean value)
	{
		setOrderPromoOption( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.orderType</code> attribute.
	 * @return the orderType
	 */
	public EnumerationValue getOrderType(final SessionContext ctx, final AbstractOrder item)
	{
		return (EnumerationValue)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ORDERTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.orderType</code> attribute.
	 * @return the orderType
	 */
	public EnumerationValue getOrderType(final AbstractOrder item)
	{
		return getOrderType( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.orderType</code> attribute. 
	 * @param value the orderType
	 */
	public void setOrderType(final SessionContext ctx, final AbstractOrder item, final EnumerationValue value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.ORDERTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.orderType</code> attribute. 
	 * @param value the orderType
	 */
	public void setOrderType(final AbstractOrder item, final EnumerationValue value)
	{
		setOrderType( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.owners</code> attribute.
	 * @return the owners - Attribute to show owner of the list
	 */
	public List<User> getOwners(final SessionContext ctx, final Wishlist2 item)
	{
		List<User> coll = (List<User>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Wishlist2.OWNERS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.owners</code> attribute.
	 * @return the owners - Attribute to show owner of the list
	 */
	public List<User> getOwners(final Wishlist2 item)
	{
		return getOwners( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.owners</code> attribute. 
	 * @param value the owners - Attribute to show owner of the list
	 */
	public void setOwners(final SessionContext ctx, final Wishlist2 item, final List<User> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Wishlist2.OWNERS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.owners</code> attribute. 
	 * @param value the owners - Attribute to show owner of the list
	 */
	public void setOwners(final Wishlist2 item, final List<User> value)
	{
		setOwners( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.pageTitle</code> attribute.
	 * @return the pageTitle - The categories page title
	 */
	public String getPageTitle(final SessionContext ctx, final Category item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.getPageTitle requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, SiteoneCoreConstants.Attributes.Category.PAGETITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.pageTitle</code> attribute.
	 * @return the pageTitle - The categories page title
	 */
	public String getPageTitle(final Category item)
	{
		return getPageTitle( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.pageTitle</code> attribute. 
	 * @return the localized pageTitle - The categories page title
	 */
	public Map<Language,String> getAllPageTitle(final SessionContext ctx, final Category item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.Category.PAGETITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.pageTitle</code> attribute. 
	 * @return the localized pageTitle - The categories page title
	 */
	public Map<Language,String> getAllPageTitle(final Category item)
	{
		return getAllPageTitle( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.pageTitle</code> attribute. 
	 * @param value the pageTitle - The categories page title
	 */
	public void setPageTitle(final SessionContext ctx, final Category item, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.setPageTitle requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteoneCoreConstants.Attributes.Category.PAGETITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.pageTitle</code> attribute. 
	 * @param value the pageTitle - The categories page title
	 */
	public void setPageTitle(final Category item, final String value)
	{
		setPageTitle( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.pageTitle</code> attribute. 
	 * @param value the pageTitle - The categories page title
	 */
	public void setAllPageTitle(final SessionContext ctx, final Category item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.Category.PAGETITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.pageTitle</code> attribute. 
	 * @param value the pageTitle - The categories page title
	 */
	public void setAllPageTitle(final Category item, final Map<Language,String> value)
	{
		setAllPageTitle( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.pageTitle</code> attribute.
	 * @return the pageTitle
	 */
	public String getPageTitle(final SessionContext ctx, final Product item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedProduct.getPageTitle requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, SiteoneCoreConstants.Attributes.Product.PAGETITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.pageTitle</code> attribute.
	 * @return the pageTitle
	 */
	public String getPageTitle(final Product item)
	{
		return getPageTitle( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.pageTitle</code> attribute. 
	 * @return the localized pageTitle
	 */
	public Map<Language,String> getAllPageTitle(final SessionContext ctx, final Product item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.Product.PAGETITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.pageTitle</code> attribute. 
	 * @return the localized pageTitle
	 */
	public Map<Language,String> getAllPageTitle(final Product item)
	{
		return getAllPageTitle( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.pageTitle</code> attribute. 
	 * @param value the pageTitle
	 */
	public void setPageTitle(final SessionContext ctx, final Product item, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedProduct.setPageTitle requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteoneCoreConstants.Attributes.Product.PAGETITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.pageTitle</code> attribute. 
	 * @param value the pageTitle
	 */
	public void setPageTitle(final Product item, final String value)
	{
		setPageTitle( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.pageTitle</code> attribute. 
	 * @param value the pageTitle
	 */
	public void setAllPageTitle(final SessionContext ctx, final Product item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.Product.PAGETITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.pageTitle</code> attribute. 
	 * @param value the pageTitle
	 */
	public void setAllPageTitle(final Product item, final Map<Language,String> value)
	{
		setAllPageTitle( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.parentId</code> attribute.
	 * @return the parentId
	 */
	public String getParentId(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.PARENTID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.parentId</code> attribute.
	 * @return the parentId
	 */
	public String getParentId(final PointOfService item)
	{
		return getParentId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.parentId</code> attribute. 
	 * @param value the parentId
	 */
	public void setParentId(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.PARENTID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.parentId</code> attribute. 
	 * @param value the parentId
	 */
	public void setParentId(final PointOfService item, final String value)
	{
		setParentId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.participatingBranch</code> attribute.
	 * @return the participatingBranch
	 */
	public Set<PointOfService> getParticipatingBranch(final SessionContext ctx, final PointOfService item)
	{
		final List<PointOfService> items = item.getLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2SHIPPINGHUBRELATION,
			"PointOfService",
			null,
			false,
			false
		);
		return new LinkedHashSet<PointOfService>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.participatingBranch</code> attribute.
	 * @return the participatingBranch
	 */
	public Set<PointOfService> getParticipatingBranch(final PointOfService item)
	{
		return getParticipatingBranch( getSession().getSessionContext(), item );
	}
	
	public long getParticipatingBranchCount(final SessionContext ctx, final PointOfService item)
	{
		return item.getLinkedItemsCount(
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2SHIPPINGHUBRELATION,
			"PointOfService",
			null
		);
	}
	
	public long getParticipatingBranchCount(final PointOfService item)
	{
		return getParticipatingBranchCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.participatingBranch</code> attribute. 
	 * @param value the participatingBranch
	 */
	public void setParticipatingBranch(final SessionContext ctx, final PointOfService item, final Set<PointOfService> value)
	{
		item.setLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2SHIPPINGHUBRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2SHIPPINGHUBRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.participatingBranch</code> attribute. 
	 * @param value the participatingBranch
	 */
	public void setParticipatingBranch(final PointOfService item, final Set<PointOfService> value)
	{
		setParticipatingBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to participatingBranch. 
	 * @param value the item to add to participatingBranch
	 */
	public void addToParticipatingBranch(final SessionContext ctx, final PointOfService item, final PointOfService value)
	{
		item.addLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2SHIPPINGHUBRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2SHIPPINGHUBRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to participatingBranch. 
	 * @param value the item to add to participatingBranch
	 */
	public void addToParticipatingBranch(final PointOfService item, final PointOfService value)
	{
		addToParticipatingBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from participatingBranch. 
	 * @param value the item to remove from participatingBranch
	 */
	public void removeFromParticipatingBranch(final SessionContext ctx, final PointOfService item, final PointOfService value)
	{
		item.removeLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2SHIPPINGHUBRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2SHIPPINGHUBRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from participatingBranch. 
	 * @param value the item to remove from participatingBranch
	 */
	public void removeFromParticipatingBranch(final PointOfService item, final PointOfService value)
	{
		removeFromParticipatingBranch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.partnerProgramPermissions</code> attribute.
	 * @return the partnerProgramPermissions
	 */
	public Boolean isPartnerProgramPermissions(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.PARTNERPROGRAMPERMISSIONS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.partnerProgramPermissions</code> attribute.
	 * @return the partnerProgramPermissions
	 */
	public Boolean isPartnerProgramPermissions(final B2BCustomer item)
	{
		return isPartnerProgramPermissions( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.partnerProgramPermissions</code> attribute. 
	 * @return the partnerProgramPermissions
	 */
	public boolean isPartnerProgramPermissionsAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isPartnerProgramPermissions( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.partnerProgramPermissions</code> attribute. 
	 * @return the partnerProgramPermissions
	 */
	public boolean isPartnerProgramPermissionsAsPrimitive(final B2BCustomer item)
	{
		return isPartnerProgramPermissionsAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.partnerProgramPermissions</code> attribute. 
	 * @param value the partnerProgramPermissions
	 */
	public void setPartnerProgramPermissions(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.PARTNERPROGRAMPERMISSIONS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.partnerProgramPermissions</code> attribute. 
	 * @param value the partnerProgramPermissions
	 */
	public void setPartnerProgramPermissions(final B2BCustomer item, final Boolean value)
	{
		setPartnerProgramPermissions( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.partnerProgramPermissions</code> attribute. 
	 * @param value the partnerProgramPermissions
	 */
	public void setPartnerProgramPermissions(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setPartnerProgramPermissions( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.partnerProgramPermissions</code> attribute. 
	 * @param value the partnerProgramPermissions
	 */
	public void setPartnerProgramPermissions(final B2BCustomer item, final boolean value)
	{
		setPartnerProgramPermissions( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.pathingChannel</code> attribute.
	 * @return the pathingChannel
	 */
	public String getPathingChannel(final SessionContext ctx, final ContentPage item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedContentPage.getPathingChannel requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, SiteoneCoreConstants.Attributes.ContentPage.PATHINGCHANNEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.pathingChannel</code> attribute.
	 * @return the pathingChannel
	 */
	public String getPathingChannel(final ContentPage item)
	{
		return getPathingChannel( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.pathingChannel</code> attribute. 
	 * @return the localized pathingChannel
	 */
	public Map<Language,String> getAllPathingChannel(final SessionContext ctx, final ContentPage item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.ContentPage.PATHINGCHANNEL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.pathingChannel</code> attribute. 
	 * @return the localized pathingChannel
	 */
	public Map<Language,String> getAllPathingChannel(final ContentPage item)
	{
		return getAllPathingChannel( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.pathingChannel</code> attribute. 
	 * @param value the pathingChannel
	 */
	public void setPathingChannel(final SessionContext ctx, final ContentPage item, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedContentPage.setPathingChannel requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteoneCoreConstants.Attributes.ContentPage.PATHINGCHANNEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.pathingChannel</code> attribute. 
	 * @param value the pathingChannel
	 */
	public void setPathingChannel(final ContentPage item, final String value)
	{
		setPathingChannel( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.pathingChannel</code> attribute. 
	 * @param value the pathingChannel
	 */
	public void setAllPathingChannel(final SessionContext ctx, final ContentPage item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.ContentPage.PATHINGCHANNEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.pathingChannel</code> attribute. 
	 * @param value the pathingChannel
	 */
	public void setAllPathingChannel(final ContentPage item, final Map<Language,String> value)
	{
		setAllPathingChannel( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.payBillOnline</code> attribute.
	 * @return the payBillOnline - payBillOnline
	 */
	public Boolean isPayBillOnline(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.PAYBILLONLINE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.payBillOnline</code> attribute.
	 * @return the payBillOnline - payBillOnline
	 */
	public Boolean isPayBillOnline(final B2BCustomer item)
	{
		return isPayBillOnline( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.payBillOnline</code> attribute. 
	 * @return the payBillOnline - payBillOnline
	 */
	public boolean isPayBillOnlineAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isPayBillOnline( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.payBillOnline</code> attribute. 
	 * @return the payBillOnline - payBillOnline
	 */
	public boolean isPayBillOnlineAsPrimitive(final B2BCustomer item)
	{
		return isPayBillOnlineAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.payBillOnline</code> attribute. 
	 * @param value the payBillOnline - payBillOnline
	 */
	public void setPayBillOnline(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.PAYBILLONLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.payBillOnline</code> attribute. 
	 * @param value the payBillOnline - payBillOnline
	 */
	public void setPayBillOnline(final B2BCustomer item, final Boolean value)
	{
		setPayBillOnline( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.payBillOnline</code> attribute. 
	 * @param value the payBillOnline - payBillOnline
	 */
	public void setPayBillOnline(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setPayBillOnline( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.payBillOnline</code> attribute. 
	 * @param value the payBillOnline - payBillOnline
	 */
	public void setPayBillOnline(final B2BCustomer item, final boolean value)
	{
		setPayBillOnline( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.payBillOnline</code> attribute.
	 * @return the payBillOnline - payBillOnline
	 */
	public Boolean isPayBillOnline(final SessionContext ctx, final B2BUnit item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.PAYBILLONLINE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.payBillOnline</code> attribute.
	 * @return the payBillOnline - payBillOnline
	 */
	public Boolean isPayBillOnline(final B2BUnit item)
	{
		return isPayBillOnline( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.payBillOnline</code> attribute. 
	 * @return the payBillOnline - payBillOnline
	 */
	public boolean isPayBillOnlineAsPrimitive(final SessionContext ctx, final B2BUnit item)
	{
		Boolean value = isPayBillOnline( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.payBillOnline</code> attribute. 
	 * @return the payBillOnline - payBillOnline
	 */
	public boolean isPayBillOnlineAsPrimitive(final B2BUnit item)
	{
		return isPayBillOnlineAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.payBillOnline</code> attribute. 
	 * @param value the payBillOnline - payBillOnline
	 */
	public void setPayBillOnline(final SessionContext ctx, final B2BUnit item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.PAYBILLONLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.payBillOnline</code> attribute. 
	 * @param value the payBillOnline - payBillOnline
	 */
	public void setPayBillOnline(final B2BUnit item, final Boolean value)
	{
		setPayBillOnline( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.payBillOnline</code> attribute. 
	 * @param value the payBillOnline - payBillOnline
	 */
	public void setPayBillOnline(final SessionContext ctx, final B2BUnit item, final boolean value)
	{
		setPayBillOnline( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.payBillOnline</code> attribute. 
	 * @param value the payBillOnline - payBillOnline
	 */
	public void setPayBillOnline(final B2BUnit item, final boolean value)
	{
		setPayBillOnline( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.paymentInfoList</code> attribute.
	 * @return the paymentInfoList
	 */
	public List<PaymentInfo> getPaymentInfoList(final SessionContext ctx, final AbstractOrder item)
	{
		List<PaymentInfo> coll = (List<PaymentInfo>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.PAYMENTINFOLIST);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.paymentInfoList</code> attribute.
	 * @return the paymentInfoList
	 */
	public List<PaymentInfo> getPaymentInfoList(final AbstractOrder item)
	{
		return getPaymentInfoList( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.paymentInfoList</code> attribute. 
	 * @param value the paymentInfoList
	 */
	public void setPaymentInfoList(final SessionContext ctx, final AbstractOrder item, final List<PaymentInfo> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.PAYMENTINFOLIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.paymentInfoList</code> attribute. 
	 * @param value the paymentInfoList
	 */
	public void setPaymentInfoList(final AbstractOrder item, final List<PaymentInfo> value)
	{
		setPaymentInfoList( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Order.paymentMethod</code> attribute.
	 * @return the paymentMethod
	 */
	public EnumerationValue getPaymentMethod(final SessionContext ctx, final Order item)
	{
		return (EnumerationValue)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Order.PAYMENTMETHOD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Order.paymentMethod</code> attribute.
	 * @return the paymentMethod
	 */
	public EnumerationValue getPaymentMethod(final Order item)
	{
		return getPaymentMethod( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Order.paymentMethod</code> attribute. 
	 * @param value the paymentMethod
	 */
	public void setPaymentMethod(final SessionContext ctx, final Order item, final EnumerationValue value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Order.PAYMENTMETHOD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Order.paymentMethod</code> attribute. 
	 * @param value the paymentMethod
	 */
	public void setPaymentMethod(final Order item, final EnumerationValue value)
	{
		setPaymentMethod( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.paymentMethod</code> attribute.
	 * @return the paymentMethod
	 */
	public EnumerationValue getPaymentMethod(final SessionContext ctx, final Cart item)
	{
		return (EnumerationValue)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Cart.PAYMENTMETHOD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.paymentMethod</code> attribute.
	 * @return the paymentMethod
	 */
	public EnumerationValue getPaymentMethod(final Cart item)
	{
		return getPaymentMethod( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.paymentMethod</code> attribute. 
	 * @param value the paymentMethod
	 */
	public void setPaymentMethod(final SessionContext ctx, final Cart item, final EnumerationValue value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Cart.PAYMENTMETHOD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.paymentMethod</code> attribute. 
	 * @param value the paymentMethod
	 */
	public void setPaymentMethod(final Cart item, final EnumerationValue value)
	{
		setPaymentMethod( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.phoneId</code> attribute.
	 * @return the phoneId - phone Id
	 */
	public String getPhoneId(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.PHONEID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.phoneId</code> attribute.
	 * @return the phoneId - phone Id
	 */
	public String getPhoneId(final B2BUnit item)
	{
		return getPhoneId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.phoneId</code> attribute. 
	 * @param value the phoneId - phone Id
	 */
	public void setPhoneId(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.PHONEID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.phoneId</code> attribute. 
	 * @param value the phoneId - phone Id
	 */
	public void setPhoneId(final B2BUnit item, final String value)
	{
		setPhoneId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PhoneContactInfo.phoneId</code> attribute.
	 * @return the phoneId
	 */
	public String getPhoneId(final SessionContext ctx, final PhoneContactInfo item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PhoneContactInfo.PHONEID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PhoneContactInfo.phoneId</code> attribute.
	 * @return the phoneId
	 */
	public String getPhoneId(final PhoneContactInfo item)
	{
		return getPhoneId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PhoneContactInfo.phoneId</code> attribute. 
	 * @param value the phoneId
	 */
	public void setPhoneId(final SessionContext ctx, final PhoneContactInfo item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PhoneContactInfo.PHONEID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PhoneContactInfo.phoneId</code> attribute. 
	 * @param value the phoneId
	 */
	public void setPhoneId(final PhoneContactInfo item, final String value)
	{
		setPhoneId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.phoneNumber</code> attribute.
	 * @return the phoneNumber - phone Number
	 */
	public String getPhoneNumber(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.PHONENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.phoneNumber</code> attribute.
	 * @return the phoneNumber - phone Number
	 */
	public String getPhoneNumber(final B2BUnit item)
	{
		return getPhoneNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.phoneNumber</code> attribute. 
	 * @param value the phoneNumber - phone Number
	 */
	public void setPhoneNumber(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.PHONENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.phoneNumber</code> attribute. 
	 * @param value the phoneNumber - phone Number
	 */
	public void setPhoneNumber(final B2BUnit item, final String value)
	{
		setPhoneNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.photoCredit</code> attribute.
	 * @return the photoCredit
	 */
	public String getPhotoCredit(final SessionContext ctx, final Product item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedProduct.getPhotoCredit requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, SiteoneCoreConstants.Attributes.Product.PHOTOCREDIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.photoCredit</code> attribute.
	 * @return the photoCredit
	 */
	public String getPhotoCredit(final Product item)
	{
		return getPhotoCredit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.photoCredit</code> attribute. 
	 * @return the localized photoCredit
	 */
	public Map<Language,String> getAllPhotoCredit(final SessionContext ctx, final Product item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.Product.PHOTOCREDIT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.photoCredit</code> attribute. 
	 * @return the localized photoCredit
	 */
	public Map<Language,String> getAllPhotoCredit(final Product item)
	{
		return getAllPhotoCredit( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.photoCredit</code> attribute. 
	 * @param value the photoCredit
	 */
	public void setPhotoCredit(final SessionContext ctx, final Product item, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedProduct.setPhotoCredit requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteoneCoreConstants.Attributes.Product.PHOTOCREDIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.photoCredit</code> attribute. 
	 * @param value the photoCredit
	 */
	public void setPhotoCredit(final Product item, final String value)
	{
		setPhotoCredit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.photoCredit</code> attribute. 
	 * @param value the photoCredit
	 */
	public void setAllPhotoCredit(final SessionContext ctx, final Product item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.Product.PHOTOCREDIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.photoCredit</code> attribute. 
	 * @param value the photoCredit
	 */
	public void setAllPhotoCredit(final Product item, final Map<Language,String> value)
	{
		setAllPhotoCredit( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.pickupAddress</code> attribute.
	 * @return the pickupAddress
	 */
	public Address getPickupAddress(final SessionContext ctx, final AbstractOrder item)
	{
		return (Address)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.PICKUPADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.pickupAddress</code> attribute.
	 * @return the pickupAddress
	 */
	public Address getPickupAddress(final AbstractOrder item)
	{
		return getPickupAddress( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.pickupAddress</code> attribute. 
	 * @param value the pickupAddress
	 */
	public void setPickupAddress(final SessionContext ctx, final AbstractOrder item, final Address value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.PICKUPADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.pickupAddress</code> attribute. 
	 * @param value the pickupAddress
	 */
	public void setPickupAddress(final AbstractOrder item, final Address value)
	{
		setPickupAddress( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.pickupfullfillment</code> attribute.
	 * @return the pickupfullfillment
	 */
	public Boolean isPickupfullfillment(final SessionContext ctx, final PointOfService item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.PICKUPFULLFILLMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.pickupfullfillment</code> attribute.
	 * @return the pickupfullfillment
	 */
	public Boolean isPickupfullfillment(final PointOfService item)
	{
		return isPickupfullfillment( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.pickupfullfillment</code> attribute. 
	 * @return the pickupfullfillment
	 */
	public boolean isPickupfullfillmentAsPrimitive(final SessionContext ctx, final PointOfService item)
	{
		Boolean value = isPickupfullfillment( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.pickupfullfillment</code> attribute. 
	 * @return the pickupfullfillment
	 */
	public boolean isPickupfullfillmentAsPrimitive(final PointOfService item)
	{
		return isPickupfullfillmentAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.pickupfullfillment</code> attribute. 
	 * @param value the pickupfullfillment
	 */
	public void setPickupfullfillment(final SessionContext ctx, final PointOfService item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.PICKUPFULLFILLMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.pickupfullfillment</code> attribute. 
	 * @param value the pickupfullfillment
	 */
	public void setPickupfullfillment(final PointOfService item, final Boolean value)
	{
		setPickupfullfillment( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.pickupfullfillment</code> attribute. 
	 * @param value the pickupfullfillment
	 */
	public void setPickupfullfillment(final SessionContext ctx, final PointOfService item, final boolean value)
	{
		setPickupfullfillment( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.pickupfullfillment</code> attribute. 
	 * @param value the pickupfullfillment
	 */
	public void setPickupfullfillment(final PointOfService item, final boolean value)
	{
		setPickupfullfillment( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.pickupInstruction</code> attribute.
	 * @return the pickupInstruction
	 */
	public String getPickupInstruction(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.PICKUPINSTRUCTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.pickupInstruction</code> attribute.
	 * @return the pickupInstruction
	 */
	public String getPickupInstruction(final AbstractOrder item)
	{
		return getPickupInstruction( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.pickupInstruction</code> attribute. 
	 * @param value the pickupInstruction
	 */
	public void setPickupInstruction(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.PICKUPINSTRUCTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.pickupInstruction</code> attribute. 
	 * @param value the pickupInstruction
	 */
	public void setPickupInstruction(final AbstractOrder item, final String value)
	{
		setPickupInstruction( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.pimCategoryId</code> attribute.
	 * @return the pimCategoryId - PIM Category ID
	 */
	public String getPimCategoryId(final SessionContext ctx, final Category item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Category.PIMCATEGORYID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.pimCategoryId</code> attribute.
	 * @return the pimCategoryId - PIM Category ID
	 */
	public String getPimCategoryId(final Category item)
	{
		return getPimCategoryId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.pimCategoryId</code> attribute. 
	 * @param value the pimCategoryId - PIM Category ID
	 */
	public void setPimCategoryId(final SessionContext ctx, final Category item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Category.PIMCATEGORYID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.pimCategoryId</code> attribute. 
	 * @param value the pimCategoryId - PIM Category ID
	 */
	public void setPimCategoryId(final Category item, final String value)
	{
		setPimCategoryId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.placeOrder</code> attribute.
	 * @return the placeOrder
	 */
	public Boolean isPlaceOrder(final SessionContext ctx, final B2BCustomer item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.PLACEORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.placeOrder</code> attribute.
	 * @return the placeOrder
	 */
	public Boolean isPlaceOrder(final B2BCustomer item)
	{
		return isPlaceOrder( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.placeOrder</code> attribute. 
	 * @return the placeOrder
	 */
	public boolean isPlaceOrderAsPrimitive(final SessionContext ctx, final B2BCustomer item)
	{
		Boolean value = isPlaceOrder( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.placeOrder</code> attribute. 
	 * @return the placeOrder
	 */
	public boolean isPlaceOrderAsPrimitive(final B2BCustomer item)
	{
		return isPlaceOrderAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.placeOrder</code> attribute. 
	 * @param value the placeOrder
	 */
	public void setPlaceOrder(final SessionContext ctx, final B2BCustomer item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.PLACEORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.placeOrder</code> attribute. 
	 * @param value the placeOrder
	 */
	public void setPlaceOrder(final B2BCustomer item, final Boolean value)
	{
		setPlaceOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.placeOrder</code> attribute. 
	 * @param value the placeOrder
	 */
	public void setPlaceOrder(final SessionContext ctx, final B2BCustomer item, final boolean value)
	{
		setPlaceOrder( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.placeOrder</code> attribute. 
	 * @param value the placeOrder
	 */
	public void setPlaceOrder(final B2BCustomer item, final boolean value)
	{
		setPlaceOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.playStoreImage</code> attribute.
	 * @return the playStoreImage
	 */
	public Media getPlayStoreImage(final SessionContext ctx, final FooterNavigationComponent item)
	{
		return (Media)item.getProperty( ctx, SiteoneCoreConstants.Attributes.FooterNavigationComponent.PLAYSTOREIMAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.playStoreImage</code> attribute.
	 * @return the playStoreImage
	 */
	public Media getPlayStoreImage(final FooterNavigationComponent item)
	{
		return getPlayStoreImage( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.playStoreImage</code> attribute. 
	 * @param value the playStoreImage
	 */
	public void setPlayStoreImage(final SessionContext ctx, final FooterNavigationComponent item, final Media value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.FooterNavigationComponent.PLAYSTOREIMAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.playStoreImage</code> attribute. 
	 * @param value the playStoreImage
	 */
	public void setPlayStoreImage(final FooterNavigationComponent item, final Media value)
	{
		setPlayStoreImage( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.playStoreLabel</code> attribute.
	 * @return the playStoreLabel
	 */
	public String getPlayStoreLabel(final SessionContext ctx, final FooterNavigationComponent item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedFooterNavigationComponent.getPlayStoreLabel requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, SiteoneCoreConstants.Attributes.FooterNavigationComponent.PLAYSTORELABEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.playStoreLabel</code> attribute.
	 * @return the playStoreLabel
	 */
	public String getPlayStoreLabel(final FooterNavigationComponent item)
	{
		return getPlayStoreLabel( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.playStoreLabel</code> attribute. 
	 * @return the localized playStoreLabel
	 */
	public Map<Language,String> getAllPlayStoreLabel(final SessionContext ctx, final FooterNavigationComponent item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.FooterNavigationComponent.PLAYSTORELABEL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.playStoreLabel</code> attribute. 
	 * @return the localized playStoreLabel
	 */
	public Map<Language,String> getAllPlayStoreLabel(final FooterNavigationComponent item)
	{
		return getAllPlayStoreLabel( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.playStoreLabel</code> attribute. 
	 * @param value the playStoreLabel
	 */
	public void setPlayStoreLabel(final SessionContext ctx, final FooterNavigationComponent item, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedFooterNavigationComponent.setPlayStoreLabel requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteoneCoreConstants.Attributes.FooterNavigationComponent.PLAYSTORELABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.playStoreLabel</code> attribute. 
	 * @param value the playStoreLabel
	 */
	public void setPlayStoreLabel(final FooterNavigationComponent item, final String value)
	{
		setPlayStoreLabel( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.playStoreLabel</code> attribute. 
	 * @param value the playStoreLabel
	 */
	public void setAllPlayStoreLabel(final SessionContext ctx, final FooterNavigationComponent item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.FooterNavigationComponent.PLAYSTORELABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.playStoreLabel</code> attribute. 
	 * @param value the playStoreLabel
	 */
	public void setAllPlayStoreLabel(final FooterNavigationComponent item, final Map<Language,String> value)
	{
		setAllPlayStoreLabel( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.playStoreUrl</code> attribute.
	 * @return the playStoreUrl
	 */
	public String getPlayStoreUrl(final SessionContext ctx, final FooterNavigationComponent item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.FooterNavigationComponent.PLAYSTOREURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>FooterNavigationComponent.playStoreUrl</code> attribute.
	 * @return the playStoreUrl
	 */
	public String getPlayStoreUrl(final FooterNavigationComponent item)
	{
		return getPlayStoreUrl( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.playStoreUrl</code> attribute. 
	 * @param value the playStoreUrl
	 */
	public void setPlayStoreUrl(final SessionContext ctx, final FooterNavigationComponent item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.FooterNavigationComponent.PLAYSTOREURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>FooterNavigationComponent.playStoreUrl</code> attribute. 
	 * @param value the playStoreUrl
	 */
	public void setPlayStoreUrl(final FooterNavigationComponent item, final String value)
	{
		setPlayStoreUrl( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.poaPaymentInfoList</code> attribute.
	 * @return the poaPaymentInfoList
	 */
	public List<PaymentInfo> getPoaPaymentInfoList(final SessionContext ctx, final AbstractOrder item)
	{
		List<PaymentInfo> coll = (List<PaymentInfo>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.POAPAYMENTINFOLIST);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.poaPaymentInfoList</code> attribute.
	 * @return the poaPaymentInfoList
	 */
	public List<PaymentInfo> getPoaPaymentInfoList(final AbstractOrder item)
	{
		return getPoaPaymentInfoList( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.poaPaymentInfoList</code> attribute. 
	 * @param value the poaPaymentInfoList
	 */
	public void setPoaPaymentInfoList(final SessionContext ctx, final AbstractOrder item, final List<PaymentInfo> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.POAPAYMENTINFOLIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.poaPaymentInfoList</code> attribute. 
	 * @param value the poaPaymentInfoList
	 */
	public void setPoaPaymentInfoList(final AbstractOrder item, final List<PaymentInfo> value)
	{
		setPoaPaymentInfoList( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceRow.pointOfService</code> attribute.
	 * @return the pointOfService
	 */
	public PointOfService getPointOfService(final SessionContext ctx, final PriceRow item)
	{
		return (PointOfService)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PriceRow.POINTOFSERVICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceRow.pointOfService</code> attribute.
	 * @return the pointOfService
	 */
	public PointOfService getPointOfService(final PriceRow item)
	{
		return getPointOfService( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceRow.pointOfService</code> attribute. 
	 * @param value the pointOfService
	 */
	public void setPointOfService(final SessionContext ctx, final PriceRow item, final PointOfService value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PriceRow.POINTOFSERVICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceRow.pointOfService</code> attribute. 
	 * @param value the pointOfService
	 */
	public void setPointOfService(final PriceRow item, final PointOfService value)
	{
		setPointOfService( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.pointOfService</code> attribute.
	 * @return the pointOfService
	 */
	public PointOfService getPointOfService(final SessionContext ctx, final AbstractOrder item)
	{
		return (PointOfService)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.POINTOFSERVICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.pointOfService</code> attribute.
	 * @return the pointOfService
	 */
	public PointOfService getPointOfService(final AbstractOrder item)
	{
		return getPointOfService( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.pointOfService</code> attribute. 
	 * @param value the pointOfService
	 */
	public void setPointOfService(final SessionContext ctx, final AbstractOrder item, final PointOfService value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.POINTOFSERVICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.pointOfService</code> attribute. 
	 * @param value the pointOfService
	 */
	public void setPointOfService(final AbstractOrder item, final PointOfService value)
	{
		setPointOfService( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CouponRedemption.poNumber</code> attribute.
	 * @return the poNumber
	 */
	public String getPoNumber(final SessionContext ctx, final CouponRedemption item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.CouponRedemption.PONUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CouponRedemption.poNumber</code> attribute.
	 * @return the poNumber
	 */
	public String getPoNumber(final CouponRedemption item)
	{
		return getPoNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CouponRedemption.poNumber</code> attribute. 
	 * @param value the poNumber
	 */
	public void setPoNumber(final SessionContext ctx, final CouponRedemption item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.CouponRedemption.PONUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CouponRedemption.poNumber</code> attribute. 
	 * @param value the poNumber
	 */
	public void setPoNumber(final CouponRedemption item, final String value)
	{
		setPoNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.poRegex</code> attribute.
	 * @return the poRegex
	 */
	public String getPoRegex(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.POREGEX);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.poRegex</code> attribute.
	 * @return the poRegex
	 */
	public String getPoRegex(final B2BUnit item)
	{
		return getPoRegex( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.poRegex</code> attribute. 
	 * @param value the poRegex
	 */
	public void setPoRegex(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.POREGEX,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.poRegex</code> attribute. 
	 * @param value the poRegex
	 */
	public void setPoRegex(final B2BUnit item, final String value)
	{
		setPoRegex( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.preferredStore</code> attribute.
	 * @return the preferredStore - Indicates the Preferred Store Of Customer
	 */
	public PointOfService getPreferredStore(final SessionContext ctx, final B2BCustomer item)
	{
		return (PointOfService)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.PREFERREDSTORE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.preferredStore</code> attribute.
	 * @return the preferredStore - Indicates the Preferred Store Of Customer
	 */
	public PointOfService getPreferredStore(final B2BCustomer item)
	{
		return getPreferredStore( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.preferredStore</code> attribute. 
	 * @param value the preferredStore - Indicates the Preferred Store Of Customer
	 */
	public void setPreferredStore(final SessionContext ctx, final B2BCustomer item, final PointOfService value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.PREFERREDSTORE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.preferredStore</code> attribute. 
	 * @param value the preferredStore - Indicates the Preferred Store Of Customer
	 */
	public void setPreferredStore(final B2BCustomer item, final PointOfService value)
	{
		setPreferredStore( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.previewTitle</code> attribute.
	 * @return the previewTitle
	 */
	public String getPreviewTitle(final SessionContext ctx, final ContentPage item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedContentPage.getPreviewTitle requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, SiteoneCoreConstants.Attributes.ContentPage.PREVIEWTITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.previewTitle</code> attribute.
	 * @return the previewTitle
	 */
	public String getPreviewTitle(final ContentPage item)
	{
		return getPreviewTitle( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.previewTitle</code> attribute. 
	 * @return the localized previewTitle
	 */
	public Map<Language,String> getAllPreviewTitle(final SessionContext ctx, final ContentPage item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.ContentPage.PREVIEWTITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ContentPage.previewTitle</code> attribute. 
	 * @return the localized previewTitle
	 */
	public Map<Language,String> getAllPreviewTitle(final ContentPage item)
	{
		return getAllPreviewTitle( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.previewTitle</code> attribute. 
	 * @param value the previewTitle
	 */
	public void setPreviewTitle(final SessionContext ctx, final ContentPage item, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedContentPage.setPreviewTitle requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteoneCoreConstants.Attributes.ContentPage.PREVIEWTITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.previewTitle</code> attribute. 
	 * @param value the previewTitle
	 */
	public void setPreviewTitle(final ContentPage item, final String value)
	{
		setPreviewTitle( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.previewTitle</code> attribute. 
	 * @param value the previewTitle
	 */
	public void setAllPreviewTitle(final SessionContext ctx, final ContentPage item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.ContentPage.PREVIEWTITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ContentPage.previewTitle</code> attribute. 
	 * @param value the previewTitle
	 */
	public void setAllPreviewTitle(final ContentPage item, final Map<Language,String> value)
	{
		setAllPreviewTitle( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.priceClassCode</code> attribute.
	 * @return the priceClassCode
	 */
	public String getPriceClassCode(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.PRICECLASSCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.priceClassCode</code> attribute.
	 * @return the priceClassCode
	 */
	public String getPriceClassCode(final B2BUnit item)
	{
		return getPriceClassCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.priceClassCode</code> attribute. 
	 * @param value the priceClassCode
	 */
	public void setPriceClassCode(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.PRICECLASSCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.priceClassCode</code> attribute. 
	 * @param value the priceClassCode
	 */
	public void setPriceClassCode(final B2BUnit item, final String value)
	{
		setPriceClassCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionSourceRule.productCoupon</code> attribute.
	 * @return the productCoupon
	 */
	public String getProductCoupon(final SessionContext ctx, final PromotionSourceRule item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PromotionSourceRule.PRODUCTCOUPON);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionSourceRule.productCoupon</code> attribute.
	 * @return the productCoupon
	 */
	public String getProductCoupon(final PromotionSourceRule item)
	{
		return getProductCoupon( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionSourceRule.productCoupon</code> attribute. 
	 * @param value the productCoupon
	 */
	public void setProductCoupon(final SessionContext ctx, final PromotionSourceRule item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PromotionSourceRule.PRODUCTCOUPON,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionSourceRule.productCoupon</code> attribute. 
	 * @param value the productCoupon
	 */
	public void setProductCoupon(final PromotionSourceRule item, final String value)
	{
		setProductCoupon( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CMSSite.productPriceDisclaimer</code> attribute.
	 * @return the productPriceDisclaimer - Indicates the disclaimer text related to product prices
	 */
	public String getProductPriceDisclaimer(final SessionContext ctx, final CMSSite item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCMSSite.getProductPriceDisclaimer requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, SiteoneCoreConstants.Attributes.CMSSite.PRODUCTPRICEDISCLAIMER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CMSSite.productPriceDisclaimer</code> attribute.
	 * @return the productPriceDisclaimer - Indicates the disclaimer text related to product prices
	 */
	public String getProductPriceDisclaimer(final CMSSite item)
	{
		return getProductPriceDisclaimer( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CMSSite.productPriceDisclaimer</code> attribute. 
	 * @return the localized productPriceDisclaimer - Indicates the disclaimer text related to product prices
	 */
	public Map<Language,String> getAllProductPriceDisclaimer(final SessionContext ctx, final CMSSite item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.CMSSite.PRODUCTPRICEDISCLAIMER,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CMSSite.productPriceDisclaimer</code> attribute. 
	 * @return the localized productPriceDisclaimer - Indicates the disclaimer text related to product prices
	 */
	public Map<Language,String> getAllProductPriceDisclaimer(final CMSSite item)
	{
		return getAllProductPriceDisclaimer( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CMSSite.productPriceDisclaimer</code> attribute. 
	 * @param value the productPriceDisclaimer - Indicates the disclaimer text related to product prices
	 */
	public void setProductPriceDisclaimer(final SessionContext ctx, final CMSSite item, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCMSSite.setProductPriceDisclaimer requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteoneCoreConstants.Attributes.CMSSite.PRODUCTPRICEDISCLAIMER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CMSSite.productPriceDisclaimer</code> attribute. 
	 * @param value the productPriceDisclaimer - Indicates the disclaimer text related to product prices
	 */
	public void setProductPriceDisclaimer(final CMSSite item, final String value)
	{
		setProductPriceDisclaimer( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CMSSite.productPriceDisclaimer</code> attribute. 
	 * @param value the productPriceDisclaimer - Indicates the disclaimer text related to product prices
	 */
	public void setAllProductPriceDisclaimer(final SessionContext ctx, final CMSSite item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.CMSSite.PRODUCTPRICEDISCLAIMER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CMSSite.productPriceDisclaimer</code> attribute. 
	 * @param value the productPriceDisclaimer - Indicates the disclaimer text related to product prices
	 */
	public void setAllProductPriceDisclaimer(final CMSSite item, final Map<Language,String> value)
	{
		setAllProductPriceDisclaimer( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productQrCode</code> attribute.
	 * @return the productQrCode
	 */
	public BarcodeMedia getProductQrCode(final SessionContext ctx, final Product item)
	{
		return (BarcodeMedia)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.PRODUCTQRCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productQrCode</code> attribute.
	 * @return the productQrCode
	 */
	public BarcodeMedia getProductQrCode(final Product item)
	{
		return getProductQrCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productQrCode</code> attribute. 
	 * @param value the productQrCode
	 */
	public void setProductQrCode(final SessionContext ctx, final Product item, final BarcodeMedia value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.PRODUCTQRCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productQrCode</code> attribute. 
	 * @param value the productQrCode
	 */
	public void setProductQrCode(final Product item, final BarcodeMedia value)
	{
		setProductQrCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.products</code> attribute.
	 * @return the products
	 */
	public Collection<Product> getProducts(final SessionContext ctx, final PointOfService item)
	{
		final List<Product> items = item.getLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.PRODUCT2POINTOFSERVICERELATION,
			"Product",
			null,
			Utilities.getRelationOrderingOverride(PRODUCT2POINTOFSERVICERELATION_SRC_ORDERED, true),
			false
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.products</code> attribute.
	 * @return the products
	 */
	public Collection<Product> getProducts(final PointOfService item)
	{
		return getProducts( getSession().getSessionContext(), item );
	}
	
	public long getProductsCount(final SessionContext ctx, final PointOfService item)
	{
		return item.getLinkedItemsCount(
			ctx,
			false,
			SiteoneCoreConstants.Relations.PRODUCT2POINTOFSERVICERELATION,
			"Product",
			null
		);
	}
	
	public long getProductsCount(final PointOfService item)
	{
		return getProductsCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.products</code> attribute. 
	 * @param value the products
	 */
	public void setProducts(final SessionContext ctx, final PointOfService item, final Collection<Product> value)
	{
		item.setLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.PRODUCT2POINTOFSERVICERELATION,
			null,
			value,
			Utilities.getRelationOrderingOverride(PRODUCT2POINTOFSERVICERELATION_SRC_ORDERED, true),
			false,
			Utilities.getMarkModifiedOverride(PRODUCT2POINTOFSERVICERELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.products</code> attribute. 
	 * @param value the products
	 */
	public void setProducts(final PointOfService item, final Collection<Product> value)
	{
		setProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products. 
	 * @param value the item to add to products
	 */
	public void addToProducts(final SessionContext ctx, final PointOfService item, final Product value)
	{
		item.addLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.PRODUCT2POINTOFSERVICERELATION,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(PRODUCT2POINTOFSERVICERELATION_SRC_ORDERED, true),
			false,
			Utilities.getMarkModifiedOverride(PRODUCT2POINTOFSERVICERELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products. 
	 * @param value the item to add to products
	 */
	public void addToProducts(final PointOfService item, final Product value)
	{
		addToProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products. 
	 * @param value the item to remove from products
	 */
	public void removeFromProducts(final SessionContext ctx, final PointOfService item, final Product value)
	{
		item.removeLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.PRODUCT2POINTOFSERVICERELATION,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(PRODUCT2POINTOFSERVICERELATION_SRC_ORDERED, true),
			false,
			Utilities.getMarkModifiedOverride(PRODUCT2POINTOFSERVICERELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products. 
	 * @param value the item to remove from products
	 */
	public void removeFromProducts(final PointOfService item, final Product value)
	{
		removeFromProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productSalesInfos</code> attribute.
	 * @return the productSalesInfos
	 */
	public Set<ProductSalesInfo> getProductSalesInfos(final SessionContext ctx, final Product item)
	{
		final List<ProductSalesInfo> items = item.getLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.SALESPRODUCTRELATION,
			"ProductSalesInfo",
			null,
			false,
			false
		);
		return new LinkedHashSet<ProductSalesInfo>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productSalesInfos</code> attribute.
	 * @return the productSalesInfos
	 */
	public Set<ProductSalesInfo> getProductSalesInfos(final Product item)
	{
		return getProductSalesInfos( getSession().getSessionContext(), item );
	}
	
	public long getProductSalesInfosCount(final SessionContext ctx, final Product item)
	{
		return item.getLinkedItemsCount(
			ctx,
			false,
			SiteoneCoreConstants.Relations.SALESPRODUCTRELATION,
			"ProductSalesInfo",
			null
		);
	}
	
	public long getProductSalesInfosCount(final Product item)
	{
		return getProductSalesInfosCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productSalesInfos</code> attribute. 
	 * @param value the productSalesInfos
	 */
	public void setProductSalesInfos(final SessionContext ctx, final Product item, final Set<ProductSalesInfo> value)
	{
		item.setLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.SALESPRODUCTRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(SALESPRODUCTRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productSalesInfos</code> attribute. 
	 * @param value the productSalesInfos
	 */
	public void setProductSalesInfos(final Product item, final Set<ProductSalesInfo> value)
	{
		setProductSalesInfos( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to productSalesInfos. 
	 * @param value the item to add to productSalesInfos
	 */
	public void addToProductSalesInfos(final SessionContext ctx, final Product item, final ProductSalesInfo value)
	{
		item.addLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.SALESPRODUCTRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(SALESPRODUCTRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to productSalesInfos. 
	 * @param value the item to add to productSalesInfos
	 */
	public void addToProductSalesInfos(final Product item, final ProductSalesInfo value)
	{
		addToProductSalesInfos( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from productSalesInfos. 
	 * @param value the item to remove from productSalesInfos
	 */
	public void removeFromProductSalesInfos(final SessionContext ctx, final Product item, final ProductSalesInfo value)
	{
		item.removeLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.SALESPRODUCTRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(SALESPRODUCTRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from productSalesInfos. 
	 * @param value the item to remove from productSalesInfos
	 */
	public void removeFromProductSalesInfos(final Product item, final ProductSalesInfo value)
	{
		removeFromProductSalesInfos( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractPromotion.productsList</code> attribute.
	 * @return the productsList
	 */
	public List<Product> getProductsList(final SessionContext ctx, final AbstractPromotion item)
	{
		List<Product> coll = (List<Product>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractPromotion.PRODUCTSLIST);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractPromotion.productsList</code> attribute.
	 * @return the productsList
	 */
	public List<Product> getProductsList(final AbstractPromotion item)
	{
		return getProductsList( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractPromotion.productsList</code> attribute. 
	 * @param value the productsList
	 */
	public void setProductsList(final SessionContext ctx, final AbstractPromotion item, final List<Product> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractPromotion.PRODUCTSLIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractPromotion.productsList</code> attribute. 
	 * @param value the productsList
	 */
	public void setProductsList(final AbstractPromotion item, final List<Product> value)
	{
		setProductsList( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.projectName</code> attribute.
	 * @return the projectName
	 */
	public String getProjectName(final SessionContext ctx, final Address item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Address.PROJECTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Address.projectName</code> attribute.
	 * @return the projectName
	 */
	public String getProjectName(final Address item)
	{
		return getProjectName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.projectName</code> attribute. 
	 * @param value the projectName
	 */
	public void setProjectName(final SessionContext ctx, final Address item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Address.PROJECTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Address.projectName</code> attribute. 
	 * @param value the projectName
	 */
	public void setProjectName(final Address item, final String value)
	{
		setProjectName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionSourceRule.promotionDetails</code> attribute.
	 * @return the promotionDetails
	 */
	public String getPromotionDetails(final SessionContext ctx, final PromotionSourceRule item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PromotionSourceRule.PROMOTIONDETAILS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionSourceRule.promotionDetails</code> attribute.
	 * @return the promotionDetails
	 */
	public String getPromotionDetails(final PromotionSourceRule item)
	{
		return getPromotionDetails( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionSourceRule.promotionDetails</code> attribute. 
	 * @param value the promotionDetails
	 */
	public void setPromotionDetails(final SessionContext ctx, final PromotionSourceRule item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PromotionSourceRule.PROMOTIONDETAILS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionSourceRule.promotionDetails</code> attribute. 
	 * @param value the promotionDetails
	 */
	public void setPromotionDetails(final PromotionSourceRule item, final String value)
	{
		setPromotionDetails( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.purchasedProducts</code> attribute.
	 * @return the purchasedProducts
	 */
	public Set<PurchasedProduct> getPurchasedProducts(final SessionContext ctx, final AbstractOrder item)
	{
		final List<PurchasedProduct> items = item.getLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.PURCHASEDPRODUCT2ORDERRELATION,
			"PurchasedProduct",
			null,
			false,
			false
		);
		return new LinkedHashSet<PurchasedProduct>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.purchasedProducts</code> attribute.
	 * @return the purchasedProducts
	 */
	public Set<PurchasedProduct> getPurchasedProducts(final AbstractOrder item)
	{
		return getPurchasedProducts( getSession().getSessionContext(), item );
	}
	
	public long getPurchasedProductsCount(final SessionContext ctx, final AbstractOrder item)
	{
		return item.getLinkedItemsCount(
			ctx,
			false,
			SiteoneCoreConstants.Relations.PURCHASEDPRODUCT2ORDERRELATION,
			"PurchasedProduct",
			null
		);
	}
	
	public long getPurchasedProductsCount(final AbstractOrder item)
	{
		return getPurchasedProductsCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.purchasedProducts</code> attribute. 
	 * @param value the purchasedProducts
	 */
	public void setPurchasedProducts(final SessionContext ctx, final AbstractOrder item, final Set<PurchasedProduct> value)
	{
		item.setLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.PURCHASEDPRODUCT2ORDERRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(PURCHASEDPRODUCT2ORDERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.purchasedProducts</code> attribute. 
	 * @param value the purchasedProducts
	 */
	public void setPurchasedProducts(final AbstractOrder item, final Set<PurchasedProduct> value)
	{
		setPurchasedProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to purchasedProducts. 
	 * @param value the item to add to purchasedProducts
	 */
	public void addToPurchasedProducts(final SessionContext ctx, final AbstractOrder item, final PurchasedProduct value)
	{
		item.addLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.PURCHASEDPRODUCT2ORDERRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(PURCHASEDPRODUCT2ORDERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to purchasedProducts. 
	 * @param value the item to add to purchasedProducts
	 */
	public void addToPurchasedProducts(final AbstractOrder item, final PurchasedProduct value)
	{
		addToPurchasedProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from purchasedProducts. 
	 * @param value the item to remove from purchasedProducts
	 */
	public void removeFromPurchasedProducts(final SessionContext ctx, final AbstractOrder item, final PurchasedProduct value)
	{
		item.removeLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.PURCHASEDPRODUCT2ORDERRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(PURCHASEDPRODUCT2ORDERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from purchasedProducts. 
	 * @param value the item to remove from purchasedProducts
	 */
	public void removeFromPurchasedProducts(final AbstractOrder item, final PurchasedProduct value)
	{
		removeFromPurchasedProducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.quantityLevel</code> attribute.
	 * @return the quantityLevel
	 */
	public Integer getQuantityLevel(final SessionContext ctx, final StockLevel item)
	{
		return (Integer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.StockLevel.QUANTITYLEVEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.quantityLevel</code> attribute.
	 * @return the quantityLevel
	 */
	public Integer getQuantityLevel(final StockLevel item)
	{
		return getQuantityLevel( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.quantityLevel</code> attribute. 
	 * @return the quantityLevel
	 */
	public int getQuantityLevelAsPrimitive(final SessionContext ctx, final StockLevel item)
	{
		Integer value = getQuantityLevel( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.quantityLevel</code> attribute. 
	 * @return the quantityLevel
	 */
	public int getQuantityLevelAsPrimitive(final StockLevel item)
	{
		return getQuantityLevelAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.quantityLevel</code> attribute. 
	 * @param value the quantityLevel
	 */
	public void setQuantityLevel(final SessionContext ctx, final StockLevel item, final Integer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.StockLevel.QUANTITYLEVEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.quantityLevel</code> attribute. 
	 * @param value the quantityLevel
	 */
	public void setQuantityLevel(final StockLevel item, final Integer value)
	{
		setQuantityLevel( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.quantityLevel</code> attribute. 
	 * @param value the quantityLevel
	 */
	public void setQuantityLevel(final SessionContext ctx, final StockLevel item, final int value)
	{
		setQuantityLevel( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.quantityLevel</code> attribute. 
	 * @param value the quantityLevel
	 */
	public void setQuantityLevel(final StockLevel item, final int value)
	{
		setQuantityLevel( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.quantityText</code> attribute.
	 * @return the quantityText
	 */
	public String getQuantityText(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.QUANTITYTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.quantityText</code> attribute.
	 * @return the quantityText
	 */
	public String getQuantityText(final AbstractOrderEntry item)
	{
		return getQuantityText( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.quantityText</code> attribute. 
	 * @param value the quantityText
	 */
	public void setQuantityText(final SessionContext ctx, final AbstractOrderEntry item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.QUANTITYTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.quantityText</code> attribute. 
	 * @param value the quantityText
	 */
	public void setQuantityText(final AbstractOrderEntry item, final String value)
	{
		setQuantityText( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Order.QuoteNumber</code> attribute.
	 * @return the QuoteNumber
	 */
	public String getQuoteNumber(final SessionContext ctx, final Order item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Order.QUOTENUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Order.QuoteNumber</code> attribute.
	 * @return the QuoteNumber
	 */
	public String getQuoteNumber(final Order item)
	{
		return getQuoteNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Order.QuoteNumber</code> attribute. 
	 * @param value the QuoteNumber
	 */
	public void setQuoteNumber(final SessionContext ctx, final Order item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Order.QUOTENUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Order.QuoteNumber</code> attribute. 
	 * @param value the QuoteNumber
	 */
	public void setQuoteNumber(final Order item, final String value)
	{
		setQuoteNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.recentCartIds</code> attribute.
	 * @return the recentCartIds
	 */
	public Collection<String> getRecentCartIds(final SessionContext ctx, final B2BCustomer item)
	{
		Collection<String> coll = (Collection<String>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.RECENTCARTIDS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.recentCartIds</code> attribute.
	 * @return the recentCartIds
	 */
	public Collection<String> getRecentCartIds(final B2BCustomer item)
	{
		return getRecentCartIds( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.recentCartIds</code> attribute. 
	 * @param value the recentCartIds
	 */
	public void setRecentCartIds(final SessionContext ctx, final B2BCustomer item, final Collection<String> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.RECENTCARTIDS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.recentCartIds</code> attribute. 
	 * @param value the recentCartIds
	 */
	public void setRecentCartIds(final B2BCustomer item, final Collection<String> value)
	{
		setRecentCartIds( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.recoveryToken</code> attribute.
	 * @return the recoveryToken - Attribute is used during forgotten password to validate in okta
	 */
	public String getRecoveryToken(final SessionContext ctx, final Customer item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Customer.RECOVERYTOKEN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Customer.recoveryToken</code> attribute.
	 * @return the recoveryToken - Attribute is used during forgotten password to validate in okta
	 */
	public String getRecoveryToken(final Customer item)
	{
		return getRecoveryToken( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.recoveryToken</code> attribute. 
	 * @param value the recoveryToken - Attribute is used during forgotten password to validate in okta
	 */
	public void setRecoveryToken(final SessionContext ctx, final Customer item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Customer.RECOVERYTOKEN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Customer.recoveryToken</code> attribute. 
	 * @param value the recoveryToken - Attribute is used during forgotten password to validate in okta
	 */
	public void setRecoveryToken(final Customer item, final String value)
	{
		setRecoveryToken( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.regionallyAssorted</code> attribute.
	 * @return the regionallyAssorted
	 */
	public Boolean isRegionallyAssorted(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.REGIONALLYASSORTED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.regionallyAssorted</code> attribute.
	 * @return the regionallyAssorted
	 */
	public Boolean isRegionallyAssorted(final Product item)
	{
		return isRegionallyAssorted( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.regionallyAssorted</code> attribute. 
	 * @return the regionallyAssorted
	 */
	public boolean isRegionallyAssortedAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isRegionallyAssorted( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.regionallyAssorted</code> attribute. 
	 * @return the regionallyAssorted
	 */
	public boolean isRegionallyAssortedAsPrimitive(final Product item)
	{
		return isRegionallyAssortedAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.regionallyAssorted</code> attribute. 
	 * @param value the regionallyAssorted
	 */
	public void setRegionallyAssorted(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.REGIONALLYASSORTED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.regionallyAssorted</code> attribute. 
	 * @param value the regionallyAssorted
	 */
	public void setRegionallyAssorted(final Product item, final Boolean value)
	{
		setRegionallyAssorted( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.regionallyAssorted</code> attribute. 
	 * @param value the regionallyAssorted
	 */
	public void setRegionallyAssorted(final SessionContext ctx, final Product item, final boolean value)
	{
		setRegionallyAssorted( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.regionallyAssorted</code> attribute. 
	 * @param value the regionallyAssorted
	 */
	public void setRegionallyAssorted(final Product item, final boolean value)
	{
		setRegionallyAssorted( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.regionId</code> attribute.
	 * @return the regionId
	 */
	public String getRegionId(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.REGIONID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.regionId</code> attribute.
	 * @return the regionId
	 */
	public String getRegionId(final PointOfService item)
	{
		return getRegionId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.regionId</code> attribute. 
	 * @param value the regionId
	 */
	public void setRegionId(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.REGIONID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.regionId</code> attribute. 
	 * @param value the regionId
	 */
	public void setRegionId(final PointOfService item, final String value)
	{
		setRegionId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.regionName</code> attribute.
	 * @return the regionName
	 */
	public String getRegionName(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.REGIONNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.regionName</code> attribute.
	 * @return the regionName
	 */
	public String getRegionName(final PointOfService item)
	{
		return getRegionName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.regionName</code> attribute. 
	 * @param value the regionName
	 */
	public void setRegionName(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.REGIONNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.regionName</code> attribute. 
	 * @param value the regionName
	 */
	public void setRegionName(final PointOfService item, final String value)
	{
		setRegionName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.regulatoryStates</code> attribute.
	 * @return the regulatoryStates
	 */
	public List<Region> getRegulatoryStates(final SessionContext ctx, final Product item)
	{
		List<Region> coll = (List<Region>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.REGULATORYSTATES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.regulatoryStates</code> attribute.
	 * @return the regulatoryStates
	 */
	public List<Region> getRegulatoryStates(final Product item)
	{
		return getRegulatoryStates( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.regulatoryStates</code> attribute. 
	 * @param value the regulatoryStates
	 */
	public void setRegulatoryStates(final SessionContext ctx, final Product item, final List<Region> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.REGULATORYSTATES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.regulatoryStates</code> attribute. 
	 * @param value the regulatoryStates
	 */
	public void setRegulatoryStates(final Product item, final List<Region> value)
	{
		setRegulatoryStates( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.requestedDate</code> attribute.
	 * @return the requestedDate
	 */
	public Date getRequestedDate(final SessionContext ctx, final AbstractOrder item)
	{
		return (Date)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.REQUESTEDDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.requestedDate</code> attribute.
	 * @return the requestedDate
	 */
	public Date getRequestedDate(final AbstractOrder item)
	{
		return getRequestedDate( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.requestedDate</code> attribute. 
	 * @param value the requestedDate
	 */
	public void setRequestedDate(final SessionContext ctx, final AbstractOrder item, final Date value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.REQUESTEDDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.requestedDate</code> attribute. 
	 * @param value the requestedDate
	 */
	public void setRequestedDate(final AbstractOrder item, final Date value)
	{
		setRequestedDate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.requestedDate</code> attribute.
	 * @return the requestedDate
	 */
	public Date getRequestedDate(final SessionContext ctx, final Consignment item)
	{
		return (Date)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Consignment.REQUESTEDDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.requestedDate</code> attribute.
	 * @return the requestedDate
	 */
	public Date getRequestedDate(final Consignment item)
	{
		return getRequestedDate( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.requestedDate</code> attribute. 
	 * @param value the requestedDate
	 */
	public void setRequestedDate(final SessionContext ctx, final Consignment item, final Date value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Consignment.REQUESTEDDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.requestedDate</code> attribute. 
	 * @param value the requestedDate
	 */
	public void setRequestedDate(final Consignment item, final Date value)
	{
		setRequestedDate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.requestedMeridian</code> attribute.
	 * @return the requestedMeridian
	 */
	public EnumerationValue getRequestedMeridian(final SessionContext ctx, final AbstractOrder item)
	{
		return (EnumerationValue)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.REQUESTEDMERIDIAN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.requestedMeridian</code> attribute.
	 * @return the requestedMeridian
	 */
	public EnumerationValue getRequestedMeridian(final AbstractOrder item)
	{
		return getRequestedMeridian( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.requestedMeridian</code> attribute. 
	 * @param value the requestedMeridian
	 */
	public void setRequestedMeridian(final SessionContext ctx, final AbstractOrder item, final EnumerationValue value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.REQUESTEDMERIDIAN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.requestedMeridian</code> attribute. 
	 * @param value the requestedMeridian
	 */
	public void setRequestedMeridian(final AbstractOrder item, final EnumerationValue value)
	{
		setRequestedMeridian( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.requestedMeridian</code> attribute.
	 * @return the requestedMeridian
	 */
	public EnumerationValue getRequestedMeridian(final SessionContext ctx, final Consignment item)
	{
		return (EnumerationValue)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Consignment.REQUESTEDMERIDIAN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.requestedMeridian</code> attribute.
	 * @return the requestedMeridian
	 */
	public EnumerationValue getRequestedMeridian(final Consignment item)
	{
		return getRequestedMeridian( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.requestedMeridian</code> attribute. 
	 * @param value the requestedMeridian
	 */
	public void setRequestedMeridian(final SessionContext ctx, final Consignment item, final EnumerationValue value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Consignment.REQUESTEDMERIDIAN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.requestedMeridian</code> attribute. 
	 * @param value the requestedMeridian
	 */
	public void setRequestedMeridian(final Consignment item, final EnumerationValue value)
	{
		setRequestedMeridian( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.respectInventory</code> attribute.
	 * @return the respectInventory
	 */
	public Boolean isRespectInventory(final SessionContext ctx, final StockLevel item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.StockLevel.RESPECTINVENTORY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.respectInventory</code> attribute.
	 * @return the respectInventory
	 */
	public Boolean isRespectInventory(final StockLevel item)
	{
		return isRespectInventory( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.respectInventory</code> attribute. 
	 * @return the respectInventory
	 */
	public boolean isRespectInventoryAsPrimitive(final SessionContext ctx, final StockLevel item)
	{
		Boolean value = isRespectInventory( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockLevel.respectInventory</code> attribute. 
	 * @return the respectInventory
	 */
	public boolean isRespectInventoryAsPrimitive(final StockLevel item)
	{
		return isRespectInventoryAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.respectInventory</code> attribute. 
	 * @param value the respectInventory
	 */
	public void setRespectInventory(final SessionContext ctx, final StockLevel item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.StockLevel.RESPECTINVENTORY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.respectInventory</code> attribute. 
	 * @param value the respectInventory
	 */
	public void setRespectInventory(final StockLevel item, final Boolean value)
	{
		setRespectInventory( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.respectInventory</code> attribute. 
	 * @param value the respectInventory
	 */
	public void setRespectInventory(final SessionContext ctx, final StockLevel item, final boolean value)
	{
		setRespectInventory( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockLevel.respectInventory</code> attribute. 
	 * @param value the respectInventory
	 */
	public void setRespectInventory(final StockLevel item, final boolean value)
	{
		setRespectInventory( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderProcess.salesApplication</code> attribute.
	 * @return the salesApplication - The sales application for which the order was placed.
	 */
	public EnumerationValue getSalesApplication(final SessionContext ctx, final OrderProcess item)
	{
		return (EnumerationValue)item.getProperty( ctx, SiteoneCoreConstants.Attributes.OrderProcess.SALESAPPLICATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderProcess.salesApplication</code> attribute.
	 * @return the salesApplication - The sales application for which the order was placed.
	 */
	public EnumerationValue getSalesApplication(final OrderProcess item)
	{
		return getSalesApplication( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderProcess.salesApplication</code> attribute. 
	 * @param value the salesApplication - The sales application for which the order was placed.
	 */
	public void setSalesApplication(final SessionContext ctx, final OrderProcess item, final EnumerationValue value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.OrderProcess.SALESAPPLICATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderProcess.salesApplication</code> attribute. 
	 * @param value the salesApplication - The sales application for which the order was placed.
	 */
	public void setSalesApplication(final OrderProcess item, final EnumerationValue value)
	{
		setSalesApplication( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.salientBullets</code> attribute.
	 * @return the salientBullets
	 */
	public String getSalientBullets(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.SALIENTBULLETS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.salientBullets</code> attribute.
	 * @return the salientBullets
	 */
	public String getSalientBullets(final Product item)
	{
		return getSalientBullets( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.salientBullets</code> attribute. 
	 * @param value the salientBullets
	 */
	public void setSalientBullets(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.SALIENTBULLETS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.salientBullets</code> attribute. 
	 * @param value the salientBullets
	 */
	public void setSalientBullets(final Product item, final String value)
	{
		setSalientBullets( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.savingsCenter</code> attribute.
	 * @return the savingsCenter
	 */
	public List<EnumerationValue> getSavingsCenter(final SessionContext ctx, final Product item)
	{
		List<EnumerationValue> coll = (List<EnumerationValue>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.SAVINGSCENTER);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.savingsCenter</code> attribute.
	 * @return the savingsCenter
	 */
	public List<EnumerationValue> getSavingsCenter(final Product item)
	{
		return getSavingsCenter( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.savingsCenter</code> attribute. 
	 * @param value the savingsCenter
	 */
	public void setSavingsCenter(final SessionContext ctx, final Product item, final List<EnumerationValue> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.SAVINGSCENTER,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.savingsCenter</code> attribute. 
	 * @param value the savingsCenter
	 */
	public void setSavingsCenter(final Product item, final List<EnumerationValue> value)
	{
		setSavingsCenter( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.scheduledDate</code> attribute.
	 * @return the scheduledDate
	 */
	public Date getScheduledDate(final SessionContext ctx, final Consignment item)
	{
		return (Date)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Consignment.SCHEDULEDDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.scheduledDate</code> attribute.
	 * @return the scheduledDate
	 */
	public Date getScheduledDate(final Consignment item)
	{
		return getScheduledDate( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.scheduledDate</code> attribute. 
	 * @param value the scheduledDate
	 */
	public void setScheduledDate(final SessionContext ctx, final Consignment item, final Date value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Consignment.SCHEDULEDDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.scheduledDate</code> attribute. 
	 * @param value the scheduledDate
	 */
	public void setScheduledDate(final Consignment item, final Date value)
	{
		setScheduledDate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.secretSku</code> attribute.
	 * @return the secretSku
	 */
	public Boolean isSecretSku(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.SECRETSKU);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.secretSku</code> attribute.
	 * @return the secretSku
	 */
	public Boolean isSecretSku(final Product item)
	{
		return isSecretSku( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.secretSku</code> attribute. 
	 * @return the secretSku
	 */
	public boolean isSecretSkuAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isSecretSku( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.secretSku</code> attribute. 
	 * @return the secretSku
	 */
	public boolean isSecretSkuAsPrimitive(final Product item)
	{
		return isSecretSkuAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.secretSku</code> attribute. 
	 * @param value the secretSku
	 */
	public void setSecretSku(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.SECRETSKU,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.secretSku</code> attribute. 
	 * @param value the secretSku
	 */
	public void setSecretSku(final Product item, final Boolean value)
	{
		setSecretSku( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.secretSku</code> attribute. 
	 * @param value the secretSku
	 */
	public void setSecretSku(final SessionContext ctx, final Product item, final boolean value)
	{
		setSecretSku( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.secretSku</code> attribute. 
	 * @param value the secretSku
	 */
	public void setSecretSku(final Product item, final boolean value)
	{
		setSecretSku( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.seoEditableText</code> attribute.
	 * @return the seoEditableText - SEO Editable Text
	 */
	public String getSeoEditableText(final SessionContext ctx, final Category item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.getSeoEditableText requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, SiteoneCoreConstants.Attributes.Category.SEOEDITABLETEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.seoEditableText</code> attribute.
	 * @return the seoEditableText - SEO Editable Text
	 */
	public String getSeoEditableText(final Category item)
	{
		return getSeoEditableText( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.seoEditableText</code> attribute. 
	 * @return the localized seoEditableText - SEO Editable Text
	 */
	public Map<Language,String> getAllSeoEditableText(final SessionContext ctx, final Category item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.Category.SEOEDITABLETEXT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.seoEditableText</code> attribute. 
	 * @return the localized seoEditableText - SEO Editable Text
	 */
	public Map<Language,String> getAllSeoEditableText(final Category item)
	{
		return getAllSeoEditableText( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.seoEditableText</code> attribute. 
	 * @param value the seoEditableText - SEO Editable Text
	 */
	public void setSeoEditableText(final SessionContext ctx, final Category item, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.setSeoEditableText requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, SiteoneCoreConstants.Attributes.Category.SEOEDITABLETEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.seoEditableText</code> attribute. 
	 * @param value the seoEditableText - SEO Editable Text
	 */
	public void setSeoEditableText(final Category item, final String value)
	{
		setSeoEditableText( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.seoEditableText</code> attribute. 
	 * @param value the seoEditableText - SEO Editable Text
	 */
	public void setAllSeoEditableText(final SessionContext ctx, final Category item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,SiteoneCoreConstants.Attributes.Category.SEOEDITABLETEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.seoEditableText</code> attribute. 
	 * @param value the seoEditableText - SEO Editable Text
	 */
	public void setAllSeoEditableText(final Category item, final Map<Language,String> value)
	{
		setAllSeoEditableText( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.shareNotes</code> attribute.
	 * @return the shareNotes - Attribute to share Notes of the list
	 */
	public String getShareNotes(final SessionContext ctx, final Wishlist2 item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Wishlist2.SHARENOTES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.shareNotes</code> attribute.
	 * @return the shareNotes - Attribute to share Notes of the list
	 */
	public String getShareNotes(final Wishlist2 item)
	{
		return getShareNotes( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.shareNotes</code> attribute. 
	 * @param value the shareNotes - Attribute to share Notes of the list
	 */
	public void setShareNotes(final SessionContext ctx, final Wishlist2 item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Wishlist2.SHARENOTES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.shareNotes</code> attribute. 
	 * @param value the shareNotes - Attribute to share Notes of the list
	 */
	public void setShareNotes(final Wishlist2 item, final String value)
	{
		setShareNotes( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.shippableItemTotal</code> attribute.
	 * @return the shippableItemTotal
	 */
	public Double getShippableItemTotal(final SessionContext ctx, final Cart item)
	{
		return (Double)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Cart.SHIPPABLEITEMTOTAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.shippableItemTotal</code> attribute.
	 * @return the shippableItemTotal
	 */
	public Double getShippableItemTotal(final Cart item)
	{
		return getShippableItemTotal( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.shippableItemTotal</code> attribute. 
	 * @return the shippableItemTotal
	 */
	public double getShippableItemTotalAsPrimitive(final SessionContext ctx, final Cart item)
	{
		Double value = getShippableItemTotal( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Cart.shippableItemTotal</code> attribute. 
	 * @return the shippableItemTotal
	 */
	public double getShippableItemTotalAsPrimitive(final Cart item)
	{
		return getShippableItemTotalAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.shippableItemTotal</code> attribute. 
	 * @param value the shippableItemTotal
	 */
	public void setShippableItemTotal(final SessionContext ctx, final Cart item, final Double value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Cart.SHIPPABLEITEMTOTAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.shippableItemTotal</code> attribute. 
	 * @param value the shippableItemTotal
	 */
	public void setShippableItemTotal(final Cart item, final Double value)
	{
		setShippableItemTotal( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.shippableItemTotal</code> attribute. 
	 * @param value the shippableItemTotal
	 */
	public void setShippableItemTotal(final SessionContext ctx, final Cart item, final double value)
	{
		setShippableItemTotal( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Cart.shippableItemTotal</code> attribute. 
	 * @param value the shippableItemTotal
	 */
	public void setShippableItemTotal(final Cart item, final double value)
	{
		setShippableItemTotal( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.shippingAddress</code> attribute.
	 * @return the shippingAddress
	 */
	public Address getShippingAddress(final SessionContext ctx, final AbstractOrder item)
	{
		return (Address)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.SHIPPINGADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.shippingAddress</code> attribute.
	 * @return the shippingAddress
	 */
	public Address getShippingAddress(final AbstractOrder item)
	{
		return getShippingAddress( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.shippingAddress</code> attribute. 
	 * @param value the shippingAddress
	 */
	public void setShippingAddress(final SessionContext ctx, final AbstractOrder item, final Address value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.SHIPPINGADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.shippingAddress</code> attribute. 
	 * @param value the shippingAddress
	 */
	public void setShippingAddress(final AbstractOrder item, final Address value)
	{
		setShippingAddress( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.shippingContactPerson</code> attribute.
	 * @return the shippingContactPerson
	 */
	public B2BCustomer getShippingContactPerson(final SessionContext ctx, final AbstractOrder item)
	{
		return (B2BCustomer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.SHIPPINGCONTACTPERSON);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.shippingContactPerson</code> attribute.
	 * @return the shippingContactPerson
	 */
	public B2BCustomer getShippingContactPerson(final AbstractOrder item)
	{
		return getShippingContactPerson( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.shippingContactPerson</code> attribute. 
	 * @param value the shippingContactPerson
	 */
	public void setShippingContactPerson(final SessionContext ctx, final AbstractOrder item, final B2BCustomer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.SHIPPINGCONTACTPERSON,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.shippingContactPerson</code> attribute. 
	 * @param value the shippingContactPerson
	 */
	public void setShippingContactPerson(final AbstractOrder item, final B2BCustomer value)
	{
		setShippingContactPerson( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.shippingFreight</code> attribute.
	 * @return the shippingFreight
	 */
	public String getShippingFreight(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.SHIPPINGFREIGHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.shippingFreight</code> attribute.
	 * @return the shippingFreight
	 */
	public String getShippingFreight(final AbstractOrder item)
	{
		return getShippingFreight( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.shippingFreight</code> attribute. 
	 * @param value the shippingFreight
	 */
	public void setShippingFreight(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.SHIPPINGFREIGHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.shippingFreight</code> attribute. 
	 * @param value the shippingFreight
	 */
	public void setShippingFreight(final AbstractOrder item, final String value)
	{
		setShippingFreight( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.shippingfullfillment</code> attribute.
	 * @return the shippingfullfillment
	 */
	public Boolean isShippingfullfillment(final SessionContext ctx, final PointOfService item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.SHIPPINGFULLFILLMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.shippingfullfillment</code> attribute.
	 * @return the shippingfullfillment
	 */
	public Boolean isShippingfullfillment(final PointOfService item)
	{
		return isShippingfullfillment( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.shippingfullfillment</code> attribute. 
	 * @return the shippingfullfillment
	 */
	public boolean isShippingfullfillmentAsPrimitive(final SessionContext ctx, final PointOfService item)
	{
		Boolean value = isShippingfullfillment( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.shippingfullfillment</code> attribute. 
	 * @return the shippingfullfillment
	 */
	public boolean isShippingfullfillmentAsPrimitive(final PointOfService item)
	{
		return isShippingfullfillmentAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.shippingfullfillment</code> attribute. 
	 * @param value the shippingfullfillment
	 */
	public void setShippingfullfillment(final SessionContext ctx, final PointOfService item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.SHIPPINGFULLFILLMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.shippingfullfillment</code> attribute. 
	 * @param value the shippingfullfillment
	 */
	public void setShippingfullfillment(final PointOfService item, final Boolean value)
	{
		setShippingfullfillment( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.shippingfullfillment</code> attribute. 
	 * @param value the shippingfullfillment
	 */
	public void setShippingfullfillment(final SessionContext ctx, final PointOfService item, final boolean value)
	{
		setShippingfullfillment( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.shippingfullfillment</code> attribute. 
	 * @param value the shippingfullfillment
	 */
	public void setShippingfullfillment(final PointOfService item, final boolean value)
	{
		setShippingfullfillment( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.shippingHubBranches</code> attribute.
	 * @return the shippingHubBranches
	 */
	public Set<PointOfService> getShippingHubBranches(final SessionContext ctx, final PointOfService item)
	{
		final List<PointOfService> items = item.getLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2SHIPPINGHUBRELATION,
			"PointOfService",
			null,
			false,
			false
		);
		return new LinkedHashSet<PointOfService>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.shippingHubBranches</code> attribute.
	 * @return the shippingHubBranches
	 */
	public Set<PointOfService> getShippingHubBranches(final PointOfService item)
	{
		return getShippingHubBranches( getSession().getSessionContext(), item );
	}
	
	public long getShippingHubBranchesCount(final SessionContext ctx, final PointOfService item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2SHIPPINGHUBRELATION,
			"PointOfService",
			null
		);
	}
	
	public long getShippingHubBranchesCount(final PointOfService item)
	{
		return getShippingHubBranchesCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.shippingHubBranches</code> attribute. 
	 * @param value the shippingHubBranches
	 */
	public void setShippingHubBranches(final SessionContext ctx, final PointOfService item, final Set<PointOfService> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2SHIPPINGHUBRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2SHIPPINGHUBRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.shippingHubBranches</code> attribute. 
	 * @param value the shippingHubBranches
	 */
	public void setShippingHubBranches(final PointOfService item, final Set<PointOfService> value)
	{
		setShippingHubBranches( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to shippingHubBranches. 
	 * @param value the item to add to shippingHubBranches
	 */
	public void addToShippingHubBranches(final SessionContext ctx, final PointOfService item, final PointOfService value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2SHIPPINGHUBRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2SHIPPINGHUBRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to shippingHubBranches. 
	 * @param value the item to add to shippingHubBranches
	 */
	public void addToShippingHubBranches(final PointOfService item, final PointOfService value)
	{
		addToShippingHubBranches( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from shippingHubBranches. 
	 * @param value the item to remove from shippingHubBranches
	 */
	public void removeFromShippingHubBranches(final SessionContext ctx, final PointOfService item, final PointOfService value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.STORE2SHIPPINGHUBRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2SHIPPINGHUBRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from shippingHubBranches. 
	 * @param value the item to remove from shippingHubBranches
	 */
	public void removeFromShippingHubBranches(final PointOfService item, final PointOfService value)
	{
		removeFromShippingHubBranches( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.shippingInstruction</code> attribute.
	 * @return the shippingInstruction
	 */
	public String getShippingInstruction(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.SHIPPINGINSTRUCTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.shippingInstruction</code> attribute.
	 * @return the shippingInstruction
	 */
	public String getShippingInstruction(final AbstractOrder item)
	{
		return getShippingInstruction( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.shippingInstruction</code> attribute. 
	 * @param value the shippingInstruction
	 */
	public void setShippingInstruction(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.SHIPPINGINSTRUCTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.shippingInstruction</code> attribute. 
	 * @param value the shippingInstruction
	 */
	public void setShippingInstruction(final AbstractOrder item, final String value)
	{
		setShippingInstruction( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.shippingThresholdAndFee</code> attribute.
	 * @return the shippingThresholdAndFee
	 */
	public Map<String,String> getAllShippingThresholdAndFee(final SessionContext ctx, final B2BUnit item)
	{
		Map<String,String> map = (Map<String,String>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.SHIPPINGTHRESHOLDANDFEE);
		return map != null ? map : Collections.EMPTY_MAP;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.shippingThresholdAndFee</code> attribute.
	 * @return the shippingThresholdAndFee
	 */
	public Map<String,String> getAllShippingThresholdAndFee(final B2BUnit item)
	{
		return getAllShippingThresholdAndFee( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.shippingThresholdAndFee</code> attribute. 
	 * @param value the shippingThresholdAndFee
	 */
	public void setAllShippingThresholdAndFee(final SessionContext ctx, final B2BUnit item, final Map<String,String> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.SHIPPINGTHRESHOLDANDFEE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.shippingThresholdAndFee</code> attribute. 
	 * @param value the shippingThresholdAndFee
	 */
	public void setAllShippingThresholdAndFee(final B2BUnit item, final Map<String,String> value)
	{
		setAllShippingThresholdAndFee( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractPromotion.shopDealUrl</code> attribute.
	 * @return the shopDealUrl
	 */
	public String getShopDealUrl(final SessionContext ctx, final AbstractPromotion item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractPromotion.SHOPDEALURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractPromotion.shopDealUrl</code> attribute.
	 * @return the shopDealUrl
	 */
	public String getShopDealUrl(final AbstractPromotion item)
	{
		return getShopDealUrl( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractPromotion.shopDealUrl</code> attribute. 
	 * @param value the shopDealUrl
	 */
	public void setShopDealUrl(final SessionContext ctx, final AbstractPromotion item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractPromotion.SHOPDEALURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractPromotion.shopDealUrl</code> attribute. 
	 * @param value the shopDealUrl
	 */
	public void setShopDealUrl(final AbstractPromotion item, final String value)
	{
		setShopDealUrl( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.siteoneHolidays</code> attribute.
	 * @return the siteoneHolidays
	 */
	public List<String> getSiteoneHolidays(final SessionContext ctx, final PointOfService item)
	{
		List<String> coll = (List<String>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.SITEONEHOLIDAYS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.siteoneHolidays</code> attribute.
	 * @return the siteoneHolidays
	 */
	public List<String> getSiteoneHolidays(final PointOfService item)
	{
		return getSiteoneHolidays( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.siteoneHolidays</code> attribute. 
	 * @param value the siteoneHolidays
	 */
	public void setSiteoneHolidays(final SessionContext ctx, final PointOfService item, final List<String> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.SITEONEHOLIDAYS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.siteoneHolidays</code> attribute. 
	 * @param value the siteoneHolidays
	 */
	public void setSiteoneHolidays(final PointOfService item, final List<String> value)
	{
		setSiteoneHolidays( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.specialImageTypes</code> attribute.
	 * @return the specialImageTypes - A list of special images for the product.
	 */
	public List<MediaContainer> getSpecialImageTypes(final SessionContext ctx, final Product item)
	{
		List<MediaContainer> coll = (List<MediaContainer>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.SPECIALIMAGETYPES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.specialImageTypes</code> attribute.
	 * @return the specialImageTypes - A list of special images for the product.
	 */
	public List<MediaContainer> getSpecialImageTypes(final Product item)
	{
		return getSpecialImageTypes( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.specialImageTypes</code> attribute. 
	 * @param value the specialImageTypes - A list of special images for the product.
	 */
	public void setSpecialImageTypes(final SessionContext ctx, final Product item, final List<MediaContainer> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.SPECIALIMAGETYPES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.specialImageTypes</code> attribute. 
	 * @param value the specialImageTypes - A list of special images for the product.
	 */
	public void setSpecialImageTypes(final Product item, final List<MediaContainer> value)
	{
		setSpecialImageTypes( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.specialInstruction</code> attribute.
	 * @return the specialInstruction
	 */
	public String getSpecialInstruction(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.SPECIALINSTRUCTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.specialInstruction</code> attribute.
	 * @return the specialInstruction
	 */
	public String getSpecialInstruction(final AbstractOrder item)
	{
		return getSpecialInstruction( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.specialInstruction</code> attribute. 
	 * @param value the specialInstruction
	 */
	public void setSpecialInstruction(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.SPECIALINSTRUCTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.specialInstruction</code> attribute. 
	 * @param value the specialInstruction
	 */
	public void setSpecialInstruction(final AbstractOrder item, final String value)
	{
		setSpecialInstruction( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.specialInstructions</code> attribute.
	 * @return the specialInstructions
	 */
	public String getSpecialInstructions(final SessionContext ctx, final Consignment item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Consignment.SPECIALINSTRUCTIONS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.specialInstructions</code> attribute.
	 * @return the specialInstructions
	 */
	public String getSpecialInstructions(final Consignment item)
	{
		return getSpecialInstructions( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.specialInstructions</code> attribute. 
	 * @param value the specialInstructions
	 */
	public void setSpecialInstructions(final SessionContext ctx, final Consignment item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Consignment.SPECIALINSTRUCTIONS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.specialInstructions</code> attribute. 
	 * @param value the specialInstructions
	 */
	public void setSpecialInstructions(final Consignment item, final String value)
	{
		setSpecialInstructions( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.specialText</code> attribute.
	 * @return the specialText
	 */
	public String getSpecialText(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.SPECIALTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.specialText</code> attribute.
	 * @return the specialText
	 */
	public String getSpecialText(final PointOfService item)
	{
		return getSpecialText( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.specialText</code> attribute. 
	 * @param value the specialText
	 */
	public void setSpecialText(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.SPECIALTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.specialText</code> attribute. 
	 * @param value the specialText
	 */
	public void setSpecialText(final PointOfService item, final String value)
	{
		setSpecialText( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.specificationItem</code> attribute.
	 * @return the specificationItem
	 */
	public String getSpecificationItem(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.SPECIFICATIONITEM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.specificationItem</code> attribute.
	 * @return the specificationItem
	 */
	public String getSpecificationItem(final Product item)
	{
		return getSpecificationItem( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.specificationItem</code> attribute. 
	 * @param value the specificationItem
	 */
	public void setSpecificationItem(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.SPECIFICATIONITEM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.specificationItem</code> attribute. 
	 * @param value the specificationItem
	 */
	public void setSpecificationItem(final Product item, final String value)
	{
		setSpecificationItem( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.specificationSeries</code> attribute.
	 * @return the specificationSeries
	 */
	public String getSpecificationSeries(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.SPECIFICATIONSERIES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.specificationSeries</code> attribute.
	 * @return the specificationSeries
	 */
	public String getSpecificationSeries(final Product item)
	{
		return getSpecificationSeries( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.specificationSeries</code> attribute. 
	 * @param value the specificationSeries
	 */
	public void setSpecificationSeries(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.SPECIFICATIONSERIES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.specificationSeries</code> attribute. 
	 * @param value the specificationSeries
	 */
	public void setSpecificationSeries(final Product item, final String value)
	{
		setSpecificationSeries( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.specificationType</code> attribute.
	 * @return the specificationType
	 */
	public String getSpecificationType(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.SPECIFICATIONTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.specificationType</code> attribute.
	 * @return the specificationType
	 */
	public String getSpecificationType(final Product item)
	{
		return getSpecificationType( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.specificationType</code> attribute. 
	 * @param value the specificationType
	 */
	public void setSpecificationType(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.SPECIFICATIONTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.specificationType</code> attribute. 
	 * @param value the specificationType
	 */
	public void setSpecificationType(final Product item, final String value)
	{
		setSpecificationType( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeekdayOpeningDay.startDate</code> attribute.
	 * @return the startDate
	 */
	public Date getStartDate(final SessionContext ctx, final WeekdayOpeningDay item)
	{
		return (Date)item.getProperty( ctx, SiteoneCoreConstants.Attributes.WeekdayOpeningDay.STARTDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeekdayOpeningDay.startDate</code> attribute.
	 * @return the startDate
	 */
	public Date getStartDate(final WeekdayOpeningDay item)
	{
		return getStartDate( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeekdayOpeningDay.startDate</code> attribute. 
	 * @param value the startDate
	 */
	public void setStartDate(final SessionContext ctx, final WeekdayOpeningDay item, final Date value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.WeekdayOpeningDay.STARTDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeekdayOpeningDay.startDate</code> attribute. 
	 * @param value the startDate
	 */
	public void setStartDate(final WeekdayOpeningDay item, final Date value)
	{
		setStartDate( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.storeContact</code> attribute.
	 * @return the storeContact
	 */
	public String getStoreContact(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.STORECONTACT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.storeContact</code> attribute.
	 * @return the storeContact
	 */
	public String getStoreContact(final AbstractOrder item)
	{
		return getStoreContact( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.storeContact</code> attribute. 
	 * @param value the storeContact
	 */
	public void setStoreContact(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.STORECONTACT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.storeContact</code> attribute. 
	 * @param value the storeContact
	 */
	public void setStoreContact(final AbstractOrder item, final String value)
	{
		setStoreContact( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.storeId</code> attribute.
	 * @return the storeId
	 */
	public String getStoreId(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.STOREID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.storeId</code> attribute.
	 * @return the storeId
	 */
	public String getStoreId(final PointOfService item)
	{
		return getStoreId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.storeId</code> attribute. 
	 * @param value the storeId
	 */
	public void setStoreId(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.STOREID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.storeId</code> attribute. 
	 * @param value the storeId
	 */
	public void setStoreId(final PointOfService item, final String value)
	{
		setStoreId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.storeNotes</code> attribute.
	 * @return the storeNotes
	 */
	public String getStoreNotes(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.STORENOTES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.storeNotes</code> attribute.
	 * @return the storeNotes
	 */
	public String getStoreNotes(final PointOfService item)
	{
		return getStoreNotes( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.storeNotes</code> attribute. 
	 * @param value the storeNotes
	 */
	public void setStoreNotes(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.STORENOTES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.storeNotes</code> attribute. 
	 * @param value the storeNotes
	 */
	public void setStoreNotes(final PointOfService item, final String value)
	{
		setStoreNotes( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.storeProductCode</code> attribute.
	 * @return the storeProductCode
	 */
	public String getStoreProductCode(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.STOREPRODUCTCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.storeProductCode</code> attribute.
	 * @return the storeProductCode
	 */
	public String getStoreProductCode(final AbstractOrderEntry item)
	{
		return getStoreProductCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.storeProductCode</code> attribute. 
	 * @param value the storeProductCode
	 */
	public void setStoreProductCode(final SessionContext ctx, final AbstractOrderEntry item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.STOREPRODUCTCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.storeProductCode</code> attribute. 
	 * @param value the storeProductCode
	 */
	public void setStoreProductCode(final AbstractOrderEntry item, final String value)
	{
		setStoreProductCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.storeProductDesc</code> attribute.
	 * @return the storeProductDesc
	 */
	public String getStoreProductDesc(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.STOREPRODUCTDESC);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.storeProductDesc</code> attribute.
	 * @return the storeProductDesc
	 */
	public String getStoreProductDesc(final AbstractOrderEntry item)
	{
		return getStoreProductDesc( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.storeProductDesc</code> attribute. 
	 * @param value the storeProductDesc
	 */
	public void setStoreProductDesc(final SessionContext ctx, final AbstractOrderEntry item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.STOREPRODUCTDESC,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.storeProductDesc</code> attribute. 
	 * @param value the storeProductDesc
	 */
	public void setStoreProductDesc(final AbstractOrderEntry item, final String value)
	{
		setStoreProductDesc( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.storeProductItemNumber</code> attribute.
	 * @return the storeProductItemNumber
	 */
	public String getStoreProductItemNumber(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.STOREPRODUCTITEMNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.storeProductItemNumber</code> attribute.
	 * @return the storeProductItemNumber
	 */
	public String getStoreProductItemNumber(final AbstractOrderEntry item)
	{
		return getStoreProductItemNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.storeProductItemNumber</code> attribute. 
	 * @param value the storeProductItemNumber
	 */
	public void setStoreProductItemNumber(final SessionContext ctx, final AbstractOrderEntry item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.STOREPRODUCTITEMNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.storeProductItemNumber</code> attribute. 
	 * @param value the storeProductItemNumber
	 */
	public void setStoreProductItemNumber(final AbstractOrderEntry item, final String value)
	{
		setStoreProductItemNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.stores</code> attribute.
	 * @return the stores
	 */
	public Set<PointOfService> getStores(final SessionContext ctx, final B2BCustomer item)
	{
		final List<PointOfService> items = item.getLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2B2BCUSTOMERRELATION,
			"PointOfService",
			null,
			false,
			false
		);
		return new LinkedHashSet<PointOfService>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.stores</code> attribute.
	 * @return the stores
	 */
	public Set<PointOfService> getStores(final B2BCustomer item)
	{
		return getStores( getSession().getSessionContext(), item );
	}
	
	public long getStoresCount(final SessionContext ctx, final B2BCustomer item)
	{
		return item.getLinkedItemsCount(
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2B2BCUSTOMERRELATION,
			"PointOfService",
			null
		);
	}
	
	public long getStoresCount(final B2BCustomer item)
	{
		return getStoresCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.stores</code> attribute. 
	 * @param value the stores
	 */
	public void setStores(final SessionContext ctx, final B2BCustomer item, final Set<PointOfService> value)
	{
		item.setLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2B2BCUSTOMERRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2B2BCUSTOMERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.stores</code> attribute. 
	 * @param value the stores
	 */
	public void setStores(final B2BCustomer item, final Set<PointOfService> value)
	{
		setStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to stores. 
	 * @param value the item to add to stores
	 */
	public void addToStores(final SessionContext ctx, final B2BCustomer item, final PointOfService value)
	{
		item.addLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2B2BCUSTOMERRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2B2BCUSTOMERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to stores. 
	 * @param value the item to add to stores
	 */
	public void addToStores(final B2BCustomer item, final PointOfService value)
	{
		addToStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from stores. 
	 * @param value the item to remove from stores
	 */
	public void removeFromStores(final SessionContext ctx, final B2BCustomer item, final PointOfService value)
	{
		item.removeLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.STORE2B2BCUSTOMERRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(STORE2B2BCUSTOMERRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from stores. 
	 * @param value the item to remove from stores
	 */
	public void removeFromStores(final B2BCustomer item, final PointOfService value)
	{
		removeFromStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.stores</code> attribute.
	 * @return the stores
	 */
	public List<PointOfService> getStores(final SessionContext ctx, final Product item)
	{
		final List<PointOfService> items = item.getLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.PRODUCT2POINTOFSERVICERELATION,
			"PointOfService",
			null,
			Utilities.getRelationOrderingOverride(PRODUCT2POINTOFSERVICERELATION_SRC_ORDERED, true),
			false
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.stores</code> attribute.
	 * @return the stores
	 */
	public List<PointOfService> getStores(final Product item)
	{
		return getStores( getSession().getSessionContext(), item );
	}
	
	public long getStoresCount(final SessionContext ctx, final Product item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			SiteoneCoreConstants.Relations.PRODUCT2POINTOFSERVICERELATION,
			"PointOfService",
			null
		);
	}
	
	public long getStoresCount(final Product item)
	{
		return getStoresCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.stores</code> attribute. 
	 * @param value the stores
	 */
	public void setStores(final SessionContext ctx, final Product item, final List<PointOfService> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.PRODUCT2POINTOFSERVICERELATION,
			null,
			value,
			Utilities.getRelationOrderingOverride(PRODUCT2POINTOFSERVICERELATION_SRC_ORDERED, true),
			false,
			Utilities.getMarkModifiedOverride(PRODUCT2POINTOFSERVICERELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.stores</code> attribute. 
	 * @param value the stores
	 */
	public void setStores(final Product item, final List<PointOfService> value)
	{
		setStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to stores. 
	 * @param value the item to add to stores
	 */
	public void addToStores(final SessionContext ctx, final Product item, final PointOfService value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.PRODUCT2POINTOFSERVICERELATION,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(PRODUCT2POINTOFSERVICERELATION_SRC_ORDERED, true),
			false,
			Utilities.getMarkModifiedOverride(PRODUCT2POINTOFSERVICERELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to stores. 
	 * @param value the item to add to stores
	 */
	public void addToStores(final Product item, final PointOfService value)
	{
		addToStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from stores. 
	 * @param value the item to remove from stores
	 */
	public void removeFromStores(final SessionContext ctx, final Product item, final PointOfService value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.PRODUCT2POINTOFSERVICERELATION,
			null,
			Collections.singletonList(value),
			Utilities.getRelationOrderingOverride(PRODUCT2POINTOFSERVICERELATION_SRC_ORDERED, true),
			false,
			Utilities.getMarkModifiedOverride(PRODUCT2POINTOFSERVICERELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from stores. 
	 * @param value the item to remove from stores
	 */
	public void removeFromStores(final Product item, final PointOfService value)
	{
		removeFromStores( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.storeSpecialities</code> attribute.
	 * @return the storeSpecialities
	 */
	public List<String> getStoreSpecialities(final SessionContext ctx, final PointOfService item)
	{
		List<String> coll = (List<String>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.STORESPECIALITIES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.storeSpecialities</code> attribute.
	 * @return the storeSpecialities
	 */
	public List<String> getStoreSpecialities(final PointOfService item)
	{
		return getStoreSpecialities( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.storeSpecialities</code> attribute. 
	 * @param value the storeSpecialities
	 */
	public void setStoreSpecialities(final SessionContext ctx, final PointOfService item, final List<String> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.STORESPECIALITIES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.storeSpecialities</code> attribute. 
	 * @param value the storeSpecialities
	 */
	public void setStoreSpecialities(final PointOfService item, final List<String> value)
	{
		setStoreSpecialities( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.storeType</code> attribute.
	 * @return the storeType
	 */
	public String getStoreType(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.STORETYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.storeType</code> attribute.
	 * @return the storeType
	 */
	public String getStoreType(final PointOfService item)
	{
		return getStoreType( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.storeType</code> attribute. 
	 * @param value the storeType
	 */
	public void setStoreType(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.STORETYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.storeType</code> attribute. 
	 * @param value the storeType
	 */
	public void setStoreType(final PointOfService item, final String value)
	{
		setStoreType( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.storeUser</code> attribute.
	 * @return the storeUser
	 */
	public String getStoreUser(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.STOREUSER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.storeUser</code> attribute.
	 * @return the storeUser
	 */
	public String getStoreUser(final AbstractOrder item)
	{
		return getStoreUser( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.storeUser</code> attribute. 
	 * @param value the storeUser
	 */
	public void setStoreUser(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.STOREUSER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.storeUser</code> attribute. 
	 * @param value the storeUser
	 */
	public void setStoreUser(final AbstractOrder item, final String value)
	{
		setStoreUser( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.storeUserContactNumber</code> attribute.
	 * @return the storeUserContactNumber
	 */
	public String getStoreUserContactNumber(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.STOREUSERCONTACTNUMBER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.storeUserContactNumber</code> attribute.
	 * @return the storeUserContactNumber
	 */
	public String getStoreUserContactNumber(final AbstractOrder item)
	{
		return getStoreUserContactNumber( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.storeUserContactNumber</code> attribute. 
	 * @param value the storeUserContactNumber
	 */
	public void setStoreUserContactNumber(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.STOREUSERCONTACTNUMBER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.storeUserContactNumber</code> attribute. 
	 * @param value the storeUserContactNumber
	 */
	public void setStoreUserContactNumber(final AbstractOrder item, final String value)
	{
		setStoreUserContactNumber( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.storeUserName</code> attribute.
	 * @return the storeUserName
	 */
	public String getStoreUserName(final SessionContext ctx, final AbstractOrder item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.STOREUSERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.storeUserName</code> attribute.
	 * @return the storeUserName
	 */
	public String getStoreUserName(final AbstractOrder item)
	{
		return getStoreUserName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.storeUserName</code> attribute. 
	 * @param value the storeUserName
	 */
	public void setStoreUserName(final SessionContext ctx, final AbstractOrder item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.STOREUSERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.storeUserName</code> attribute. 
	 * @param value the storeUserName
	 */
	public void setStoreUserName(final AbstractOrder item, final String value)
	{
		setStoreUserName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.subTotal</code> attribute.
	 * @return the subTotal
	 */
	public String getSubTotal(final SessionContext ctx, final Consignment item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Consignment.SUBTOTAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.subTotal</code> attribute.
	 * @return the subTotal
	 */
	public String getSubTotal(final Consignment item)
	{
		return getSubTotal( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.subTotal</code> attribute. 
	 * @param value the subTotal
	 */
	public void setSubTotal(final SessionContext ctx, final Consignment item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Consignment.SUBTOTAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.subTotal</code> attribute. 
	 * @param value the subTotal
	 */
	public void setSubTotal(final Consignment item, final String value)
	{
		setSubTotal( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.subTradeClass</code> attribute.
	 * @return the subTradeClass - customer  Sub Trade Class
	 */
	public String getSubTradeClass(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.SUBTRADECLASS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.subTradeClass</code> attribute.
	 * @return the subTradeClass - customer  Sub Trade Class
	 */
	public String getSubTradeClass(final B2BUnit item)
	{
		return getSubTradeClass( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.subTradeClass</code> attribute. 
	 * @param value the subTradeClass - customer  Sub Trade Class
	 */
	public void setSubTradeClass(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.SUBTRADECLASS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.subTradeClass</code> attribute. 
	 * @param value the subTradeClass - customer  Sub Trade Class
	 */
	public void setSubTradeClass(final B2BUnit item, final String value)
	{
		setSubTradeClass( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.subTradeClassName</code> attribute.
	 * @return the subTradeClassName - customer Sub Trade Class name
	 */
	public String getSubTradeClassName(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.SUBTRADECLASSNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.subTradeClassName</code> attribute.
	 * @return the subTradeClassName - customer Sub Trade Class name
	 */
	public String getSubTradeClassName(final B2BUnit item)
	{
		return getSubTradeClassName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.subTradeClassName</code> attribute. 
	 * @param value the subTradeClassName - customer Sub Trade Class name
	 */
	public void setSubTradeClassName(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.SUBTRADECLASSNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.subTradeClassName</code> attribute. 
	 * @param value the subTradeClassName - customer Sub Trade Class name
	 */
	public void setSubTradeClassName(final B2BUnit item, final String value)
	{
		setSubTradeClassName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.supplyChainNodeId</code> attribute.
	 * @return the supplyChainNodeId
	 */
	public String getSupplyChainNodeId(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.SUPPLYCHAINNODEID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.supplyChainNodeId</code> attribute.
	 * @return the supplyChainNodeId
	 */
	public String getSupplyChainNodeId(final PointOfService item)
	{
		return getSupplyChainNodeId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.supplyChainNodeId</code> attribute. 
	 * @param value the supplyChainNodeId
	 */
	public void setSupplyChainNodeId(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.SUPPLYCHAINNODEID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.supplyChainNodeId</code> attribute. 
	 * @param value the supplyChainNodeId
	 */
	public void setSupplyChainNodeId(final PointOfService item, final String value)
	{
		setSupplyChainNodeId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.supportProductScanner</code> attribute.
	 * @return the supportProductScanner
	 */
	public Boolean isSupportProductScanner(final SessionContext ctx, final PointOfService item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.SUPPORTPRODUCTSCANNER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.supportProductScanner</code> attribute.
	 * @return the supportProductScanner
	 */
	public Boolean isSupportProductScanner(final PointOfService item)
	{
		return isSupportProductScanner( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.supportProductScanner</code> attribute. 
	 * @return the supportProductScanner
	 */
	public boolean isSupportProductScannerAsPrimitive(final SessionContext ctx, final PointOfService item)
	{
		Boolean value = isSupportProductScanner( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.supportProductScanner</code> attribute. 
	 * @return the supportProductScanner
	 */
	public boolean isSupportProductScannerAsPrimitive(final PointOfService item)
	{
		return isSupportProductScannerAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.supportProductScanner</code> attribute. 
	 * @param value the supportProductScanner
	 */
	public void setSupportProductScanner(final SessionContext ctx, final PointOfService item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.SUPPORTPRODUCTSCANNER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.supportProductScanner</code> attribute. 
	 * @param value the supportProductScanner
	 */
	public void setSupportProductScanner(final PointOfService item, final Boolean value)
	{
		setSupportProductScanner( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.supportProductScanner</code> attribute. 
	 * @param value the supportProductScanner
	 */
	public void setSupportProductScanner(final SessionContext ctx, final PointOfService item, final boolean value)
	{
		setSupportProductScanner( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.supportProductScanner</code> attribute. 
	 * @param value the supportProductScanner
	 */
	public void setSupportProductScanner(final PointOfService item, final boolean value)
	{
		setSupportProductScanner( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.swatchImages</code> attribute.
	 * @return the swatchImages - A list of swatch images for the product.
	 */
	public List<MediaContainer> getSwatchImages(final SessionContext ctx, final Product item)
	{
		List<MediaContainer> coll = (List<MediaContainer>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.SWATCHIMAGES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.swatchImages</code> attribute.
	 * @return the swatchImages - A list of swatch images for the product.
	 */
	public List<MediaContainer> getSwatchImages(final Product item)
	{
		return getSwatchImages( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.swatchImages</code> attribute. 
	 * @param value the swatchImages - A list of swatch images for the product.
	 */
	public void setSwatchImages(final SessionContext ctx, final Product item, final List<MediaContainer> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.SWATCHIMAGES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.swatchImages</code> attribute. 
	 * @param value the swatchImages - A list of swatch images for the product.
	 */
	public void setSwatchImages(final Product item, final List<MediaContainer> value)
	{
		setSwatchImages( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.tax</code> attribute.
	 * @return the tax
	 */
	public String getTax(final SessionContext ctx, final Consignment item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Consignment.TAX);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.tax</code> attribute.
	 * @return the tax
	 */
	public String getTax(final Consignment item)
	{
		return getTax( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.tax</code> attribute. 
	 * @param value the tax
	 */
	public void setTax(final SessionContext ctx, final Consignment item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Consignment.TAX,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.tax</code> attribute. 
	 * @param value the tax
	 */
	public void setTax(final Consignment item, final String value)
	{
		setTax( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.timezoneId</code> attribute.
	 * @return the timezoneId
	 */
	public String getTimezoneId(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.TIMEZONEID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.timezoneId</code> attribute.
	 * @return the timezoneId
	 */
	public String getTimezoneId(final PointOfService item)
	{
		return getTimezoneId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.timezoneId</code> attribute. 
	 * @param value the timezoneId
	 */
	public void setTimezoneId(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.TIMEZONEID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.timezoneId</code> attribute. 
	 * @param value the timezoneId
	 */
	public void setTimezoneId(final PointOfService item, final String value)
	{
		setTimezoneId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.title</code> attribute.
	 * @return the title
	 */
	public String getTitle(final SessionContext ctx, final PointOfService item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.PointOfService.TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PointOfService.title</code> attribute.
	 * @return the title
	 */
	public String getTitle(final PointOfService item)
	{
		return getTitle( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final SessionContext ctx, final PointOfService item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.PointOfService.TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PointOfService.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final PointOfService item, final String value)
	{
		setTitle( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails(final SessionContext ctx, final OrderProcess item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.OrderProcess.TOEMAILS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderProcess.toEmails</code> attribute.
	 * @return the toEmails
	 */
	public String getToEmails(final OrderProcess item)
	{
		return getToEmails( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final SessionContext ctx, final OrderProcess item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.OrderProcess.TOEMAILS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderProcess.toEmails</code> attribute. 
	 * @param value the toEmails
	 */
	public void setToEmails(final OrderProcess item, final String value)
	{
		setToEmails( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.total</code> attribute.
	 * @return the total
	 */
	public String getTotal(final SessionContext ctx, final Consignment item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Consignment.TOTAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.total</code> attribute.
	 * @return the total
	 */
	public String getTotal(final Consignment item)
	{
		return getTotal( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.total</code> attribute. 
	 * @param value the total
	 */
	public void setTotal(final SessionContext ctx, final Consignment item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Consignment.TOTAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.total</code> attribute. 
	 * @param value the total
	 */
	public void setTotal(final Consignment item, final String value)
	{
		setTotal( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalDiscountAmount</code> attribute.
	 * @return the totalDiscountAmount
	 */
	public Double getTotalDiscountAmount(final SessionContext ctx, final AbstractOrder item)
	{
		return (Double)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrder.TOTALDISCOUNTAMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalDiscountAmount</code> attribute.
	 * @return the totalDiscountAmount
	 */
	public Double getTotalDiscountAmount(final AbstractOrder item)
	{
		return getTotalDiscountAmount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalDiscountAmount</code> attribute. 
	 * @return the totalDiscountAmount
	 */
	public double getTotalDiscountAmountAsPrimitive(final SessionContext ctx, final AbstractOrder item)
	{
		Double value = getTotalDiscountAmount( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrder.totalDiscountAmount</code> attribute. 
	 * @return the totalDiscountAmount
	 */
	public double getTotalDiscountAmountAsPrimitive(final AbstractOrder item)
	{
		return getTotalDiscountAmountAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalDiscountAmount</code> attribute. 
	 * @param value the totalDiscountAmount
	 */
	public void setTotalDiscountAmount(final SessionContext ctx, final AbstractOrder item, final Double value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrder.TOTALDISCOUNTAMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalDiscountAmount</code> attribute. 
	 * @param value the totalDiscountAmount
	 */
	public void setTotalDiscountAmount(final AbstractOrder item, final Double value)
	{
		setTotalDiscountAmount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalDiscountAmount</code> attribute. 
	 * @param value the totalDiscountAmount
	 */
	public void setTotalDiscountAmount(final SessionContext ctx, final AbstractOrder item, final double value)
	{
		setTotalDiscountAmount( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrder.totalDiscountAmount</code> attribute. 
	 * @param value the totalDiscountAmount
	 */
	public void setTotalDiscountAmount(final AbstractOrder item, final double value)
	{
		setTotalDiscountAmount( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totaltax</code> attribute.
	 * @return the totaltax
	 */
	public Double getTotaltax(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.TOTALTAX);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totaltax</code> attribute.
	 * @return the totaltax
	 */
	public Double getTotaltax(final AbstractOrderEntry item)
	{
		return getTotaltax( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totaltax</code> attribute. 
	 * @return the totaltax
	 */
	public double getTotaltaxAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getTotaltax( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.totaltax</code> attribute. 
	 * @return the totaltax
	 */
	public double getTotaltaxAsPrimitive(final AbstractOrderEntry item)
	{
		return getTotaltaxAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totaltax</code> attribute. 
	 * @param value the totaltax
	 */
	public void setTotaltax(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.TOTALTAX,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totaltax</code> attribute. 
	 * @param value the totaltax
	 */
	public void setTotaltax(final AbstractOrderEntry item, final Double value)
	{
		setTotaltax( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totaltax</code> attribute. 
	 * @param value the totaltax
	 */
	public void setTotaltax(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setTotaltax( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.totaltax</code> attribute. 
	 * @param value the totaltax
	 */
	public void setTotaltax(final AbstractOrderEntry item, final double value)
	{
		setTotaltax( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.trackingLink</code> attribute.
	 * @return the trackingLink
	 */
	public String getTrackingLink(final SessionContext ctx, final Consignment item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Consignment.TRACKINGLINK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Consignment.trackingLink</code> attribute.
	 * @return the trackingLink
	 */
	public String getTrackingLink(final Consignment item)
	{
		return getTrackingLink( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.trackingLink</code> attribute. 
	 * @param value the trackingLink
	 */
	public void setTrackingLink(final SessionContext ctx, final Consignment item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Consignment.TRACKINGLINK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Consignment.trackingLink</code> attribute. 
	 * @param value the trackingLink
	 */
	public void setTrackingLink(final Consignment item, final String value)
	{
		setTrackingLink( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.tradeClass</code> attribute.
	 * @return the tradeClass - customer Trade Class
	 */
	public String getTradeClass(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.TRADECLASS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.tradeClass</code> attribute.
	 * @return the tradeClass - customer Trade Class
	 */
	public String getTradeClass(final B2BUnit item)
	{
		return getTradeClass( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.tradeClass</code> attribute. 
	 * @param value the tradeClass - customer Trade Class
	 */
	public void setTradeClass(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.TRADECLASS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.tradeClass</code> attribute. 
	 * @param value the tradeClass - customer Trade Class
	 */
	public void setTradeClass(final B2BUnit item, final String value)
	{
		setTradeClass( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.tradeClassName</code> attribute.
	 * @return the tradeClassName - customer Trade Class name
	 */
	public String getTradeClassName(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.TRADECLASSNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.tradeClassName</code> attribute.
	 * @return the tradeClassName - customer Trade Class name
	 */
	public String getTradeClassName(final B2BUnit item)
	{
		return getTradeClassName( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.tradeClassName</code> attribute. 
	 * @param value the tradeClassName - customer Trade Class name
	 */
	public void setTradeClassName(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.TRADECLASSNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.tradeClassName</code> attribute. 
	 * @param value the tradeClassName - customer Trade Class name
	 */
	public void setTradeClassName(final B2BUnit item, final String value)
	{
		setTradeClassName( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.ueType</code> attribute.
	 * @return the ueType
	 */
	public String getUeType(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.UETYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.ueType</code> attribute.
	 * @return the ueType
	 */
	public String getUeType(final B2BCustomer item)
	{
		return getUeType( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.ueType</code> attribute. 
	 * @param value the ueType
	 */
	public void setUeType(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.UETYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.ueType</code> attribute. 
	 * @param value the ueType
	 */
	public void setUeType(final B2BCustomer item, final String value)
	{
		setUeType( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2Entry.uomId</code> attribute.
	 * @return the uomId
	 */
	public Integer getUomId(final SessionContext ctx, final Wishlist2Entry item)
	{
		return (Integer)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Wishlist2Entry.UOMID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2Entry.uomId</code> attribute.
	 * @return the uomId
	 */
	public Integer getUomId(final Wishlist2Entry item)
	{
		return getUomId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2Entry.uomId</code> attribute. 
	 * @return the uomId
	 */
	public int getUomIdAsPrimitive(final SessionContext ctx, final Wishlist2Entry item)
	{
		Integer value = getUomId( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2Entry.uomId</code> attribute. 
	 * @return the uomId
	 */
	public int getUomIdAsPrimitive(final Wishlist2Entry item)
	{
		return getUomIdAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2Entry.uomId</code> attribute. 
	 * @param value the uomId
	 */
	public void setUomId(final SessionContext ctx, final Wishlist2Entry item, final Integer value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Wishlist2Entry.UOMID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2Entry.uomId</code> attribute. 
	 * @param value the uomId
	 */
	public void setUomId(final Wishlist2Entry item, final Integer value)
	{
		setUomId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2Entry.uomId</code> attribute. 
	 * @param value the uomId
	 */
	public void setUomId(final SessionContext ctx, final Wishlist2Entry item, final int value)
	{
		setUomId( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2Entry.uomId</code> attribute. 
	 * @param value the uomId
	 */
	public void setUomId(final Wishlist2Entry item, final int value)
	{
		setUomId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.uomPrice</code> attribute.
	 * @return the uomPrice
	 */
	public Double getUomPrice(final SessionContext ctx, final AbstractOrderEntry item)
	{
		return (Double)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.UOMPRICE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.uomPrice</code> attribute.
	 * @return the uomPrice
	 */
	public Double getUomPrice(final AbstractOrderEntry item)
	{
		return getUomPrice( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.uomPrice</code> attribute. 
	 * @return the uomPrice
	 */
	public double getUomPriceAsPrimitive(final SessionContext ctx, final AbstractOrderEntry item)
	{
		Double value = getUomPrice( ctx,item );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractOrderEntry.uomPrice</code> attribute. 
	 * @return the uomPrice
	 */
	public double getUomPriceAsPrimitive(final AbstractOrderEntry item)
	{
		return getUomPriceAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.uomPrice</code> attribute. 
	 * @param value the uomPrice
	 */
	public void setUomPrice(final SessionContext ctx, final AbstractOrderEntry item, final Double value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractOrderEntry.UOMPRICE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.uomPrice</code> attribute. 
	 * @param value the uomPrice
	 */
	public void setUomPrice(final AbstractOrderEntry item, final Double value)
	{
		setUomPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.uomPrice</code> attribute. 
	 * @param value the uomPrice
	 */
	public void setUomPrice(final SessionContext ctx, final AbstractOrderEntry item, final double value)
	{
		setUomPrice( ctx, item, Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractOrderEntry.uomPrice</code> attribute. 
	 * @param value the uomPrice
	 */
	public void setUomPrice(final AbstractOrderEntry item, final double value)
	{
		setUomPrice( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.upcData</code> attribute.
	 * @return the upcData
	 */
	public List<InventoryUPC> getUpcData(final SessionContext ctx, final Product item)
	{
		List<InventoryUPC> coll = (List<InventoryUPC>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.UPCDATA);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.upcData</code> attribute.
	 * @return the upcData
	 */
	public List<InventoryUPC> getUpcData(final Product item)
	{
		return getUpcData( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.upcData</code> attribute. 
	 * @param value the upcData
	 */
	public void setUpcData(final SessionContext ctx, final Product item, final List<InventoryUPC> value)
	{
		new PartOfHandler<List<InventoryUPC>>()
		{
			@Override
			protected List<InventoryUPC> doGetValue(final SessionContext ctx)
			{
				return getUpcData( ctx, item );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<InventoryUPC> _value)
			{
				final List<InventoryUPC> value = _value;
				item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.UPCDATA,value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.upcData</code> attribute. 
	 * @param value the upcData
	 */
	public void setUpcData(final Product item, final List<InventoryUPC> value)
	{
		setUpcData( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.updateEmailFlag</code> attribute.
	 * @return the updateEmailFlag
	 */
	public EnumerationValue getUpdateEmailFlag(final SessionContext ctx, final B2BCustomer item)
	{
		return (EnumerationValue)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.UPDATEEMAILFLAG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.updateEmailFlag</code> attribute.
	 * @return the updateEmailFlag
	 */
	public EnumerationValue getUpdateEmailFlag(final B2BCustomer item)
	{
		return getUpdateEmailFlag( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.updateEmailFlag</code> attribute. 
	 * @param value the updateEmailFlag
	 */
	public void setUpdateEmailFlag(final SessionContext ctx, final B2BCustomer item, final EnumerationValue value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.UPDATEEMAILFLAG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.updateEmailFlag</code> attribute. 
	 * @param value the updateEmailFlag
	 */
	public void setUpdateEmailFlag(final B2BCustomer item, final EnumerationValue value)
	{
		setUpdateEmailFlag( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractBannerComponent.urlForLink</code> attribute.
	 * @return the urlForLink
	 */
	public String getUrlForLink(final SessionContext ctx, final AbstractBannerComponent item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.AbstractBannerComponent.URLFORLINK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractBannerComponent.urlForLink</code> attribute.
	 * @return the urlForLink
	 */
	public String getUrlForLink(final AbstractBannerComponent item)
	{
		return getUrlForLink( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractBannerComponent.urlForLink</code> attribute. 
	 * @param value the urlForLink
	 */
	public void setUrlForLink(final SessionContext ctx, final AbstractBannerComponent item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.AbstractBannerComponent.URLFORLINK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractBannerComponent.urlForLink</code> attribute. 
	 * @param value the urlForLink
	 */
	public void setUrlForLink(final AbstractBannerComponent item, final String value)
	{
		setUrlForLink( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.uuid</code> attribute.
	 * @return the uuid
	 */
	public String getUuid(final SessionContext ctx, final B2BCustomer item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BCustomer.UUID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.uuid</code> attribute.
	 * @return the uuid
	 */
	public String getUuid(final B2BCustomer item)
	{
		return getUuid( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.uuid</code> attribute. 
	 * @param value the uuid
	 */
	public void setUuid(final SessionContext ctx, final B2BCustomer item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BCustomer.UUID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.uuid</code> attribute. 
	 * @param value the uuid
	 */
	public void setUuid(final B2BCustomer item, final String value)
	{
		setUuid( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.variantCategoryList</code> attribute.
	 * @return the variantCategoryList - The list of Variant Category
	 */
	public String getVariantCategoryList(final SessionContext ctx, final Category item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Category.VARIANTCATEGORYLIST);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.variantCategoryList</code> attribute.
	 * @return the variantCategoryList - The list of Variant Category
	 */
	public String getVariantCategoryList(final Category item)
	{
		return getVariantCategoryList( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.variantCategoryList</code> attribute. 
	 * @param value the variantCategoryList - The list of Variant Category
	 */
	public void setVariantCategoryList(final SessionContext ctx, final Category item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Category.VARIANTCATEGORYLIST,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.variantCategoryList</code> attribute. 
	 * @param value the variantCategoryList - The list of Variant Category
	 */
	public void setVariantCategoryList(final Category item, final String value)
	{
		setVariantCategoryList( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.vaultToken</code> attribute.
	 * @return the vaultToken
	 */
	public Set<SiteoneEwalletCreditCard> getVaultToken(final SessionContext ctx, final B2BCustomer item)
	{
		final List<SiteoneEwalletCreditCard> items = item.getLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.CUSTOMER2WALLETRELATION,
			"SiteoneEwalletCreditCard",
			null,
			false,
			false
		);
		return new LinkedHashSet<SiteoneEwalletCreditCard>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BCustomer.vaultToken</code> attribute.
	 * @return the vaultToken
	 */
	public Set<SiteoneEwalletCreditCard> getVaultToken(final B2BCustomer item)
	{
		return getVaultToken( getSession().getSessionContext(), item );
	}
	
	public long getVaultTokenCount(final SessionContext ctx, final B2BCustomer item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			SiteoneCoreConstants.Relations.CUSTOMER2WALLETRELATION,
			"SiteoneEwalletCreditCard",
			null
		);
	}
	
	public long getVaultTokenCount(final B2BCustomer item)
	{
		return getVaultTokenCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.vaultToken</code> attribute. 
	 * @param value the vaultToken
	 */
	public void setVaultToken(final SessionContext ctx, final B2BCustomer item, final Set<SiteoneEwalletCreditCard> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.CUSTOMER2WALLETRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(CUSTOMER2WALLETRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BCustomer.vaultToken</code> attribute. 
	 * @param value the vaultToken
	 */
	public void setVaultToken(final B2BCustomer item, final Set<SiteoneEwalletCreditCard> value)
	{
		setVaultToken( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to vaultToken. 
	 * @param value the item to add to vaultToken
	 */
	public void addToVaultToken(final SessionContext ctx, final B2BCustomer item, final SiteoneEwalletCreditCard value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.CUSTOMER2WALLETRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(CUSTOMER2WALLETRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to vaultToken. 
	 * @param value the item to add to vaultToken
	 */
	public void addToVaultToken(final B2BCustomer item, final SiteoneEwalletCreditCard value)
	{
		addToVaultToken( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from vaultToken. 
	 * @param value the item to remove from vaultToken
	 */
	public void removeFromVaultToken(final SessionContext ctx, final B2BCustomer item, final SiteoneEwalletCreditCard value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.CUSTOMER2WALLETRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(CUSTOMER2WALLETRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from vaultToken. 
	 * @param value the item to remove from vaultToken
	 */
	public void removeFromVaultToken(final B2BCustomer item, final SiteoneEwalletCreditCard value)
	{
		removeFromVaultToken( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.vaultToken</code> attribute.
	 * @return the vaultToken
	 */
	public Set<SiteoneEwalletCreditCard> getVaultToken(final SessionContext ctx, final B2BUnit item)
	{
		final List<SiteoneEwalletCreditCard> items = item.getLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.SHIPTO2WALLETRELATION,
			"SiteoneEwalletCreditCard",
			null,
			false,
			false
		);
		return new LinkedHashSet<SiteoneEwalletCreditCard>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.vaultToken</code> attribute.
	 * @return the vaultToken
	 */
	public Set<SiteoneEwalletCreditCard> getVaultToken(final B2BUnit item)
	{
		return getVaultToken( getSession().getSessionContext(), item );
	}
	
	public long getVaultTokenCount(final SessionContext ctx, final B2BUnit item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			SiteoneCoreConstants.Relations.SHIPTO2WALLETRELATION,
			"SiteoneEwalletCreditCard",
			null
		);
	}
	
	public long getVaultTokenCount(final B2BUnit item)
	{
		return getVaultTokenCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.vaultToken</code> attribute. 
	 * @param value the vaultToken
	 */
	public void setVaultToken(final SessionContext ctx, final B2BUnit item, final Set<SiteoneEwalletCreditCard> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.SHIPTO2WALLETRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(SHIPTO2WALLETRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.vaultToken</code> attribute. 
	 * @param value the vaultToken
	 */
	public void setVaultToken(final B2BUnit item, final Set<SiteoneEwalletCreditCard> value)
	{
		setVaultToken( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to vaultToken. 
	 * @param value the item to add to vaultToken
	 */
	public void addToVaultToken(final SessionContext ctx, final B2BUnit item, final SiteoneEwalletCreditCard value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.SHIPTO2WALLETRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(SHIPTO2WALLETRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to vaultToken. 
	 * @param value the item to add to vaultToken
	 */
	public void addToVaultToken(final B2BUnit item, final SiteoneEwalletCreditCard value)
	{
		addToVaultToken( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from vaultToken. 
	 * @param value the item to remove from vaultToken
	 */
	public void removeFromVaultToken(final SessionContext ctx, final B2BUnit item, final SiteoneEwalletCreditCard value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			SiteoneCoreConstants.Relations.SHIPTO2WALLETRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(SHIPTO2WALLETRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from vaultToken. 
	 * @param value the item to remove from vaultToken
	 */
	public void removeFromVaultToken(final B2BUnit item, final SiteoneEwalletCreditCard value)
	{
		removeFromVaultToken( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.viewEditOwners</code> attribute.
	 * @return the viewEditOwners - Attribute to show owner of the list
	 */
	public List<User> getViewEditOwners(final SessionContext ctx, final Wishlist2 item)
	{
		List<User> coll = (List<User>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Wishlist2.VIEWEDITOWNERS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Wishlist2.viewEditOwners</code> attribute.
	 * @return the viewEditOwners - Attribute to show owner of the list
	 */
	public List<User> getViewEditOwners(final Wishlist2 item)
	{
		return getViewEditOwners( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.viewEditOwners</code> attribute. 
	 * @param value the viewEditOwners - Attribute to show owner of the list
	 */
	public void setViewEditOwners(final SessionContext ctx, final Wishlist2 item, final List<User> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Wishlist2.VIEWEDITOWNERS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Wishlist2.viewEditOwners</code> attribute. 
	 * @param value the viewEditOwners - Attribute to show owner of the list
	 */
	public void setViewEditOwners(final Wishlist2 item, final List<User> value)
	{
		setViewEditOwners( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserGroup.visibleCategories</code> attribute.
	 * @return the visibleCategories - The categories for the PunchOut groups
	 */
	public List<Category> getVisibleCategories(final SessionContext ctx, final UserGroup item)
	{
		List<Category> coll = (List<Category>)item.getProperty( ctx, SiteoneCoreConstants.Attributes.UserGroup.VISIBLECATEGORIES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserGroup.visibleCategories</code> attribute.
	 * @return the visibleCategories - The categories for the PunchOut groups
	 */
	public List<Category> getVisibleCategories(final UserGroup item)
	{
		return getVisibleCategories( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserGroup.visibleCategories</code> attribute. 
	 * @param value the visibleCategories - The categories for the PunchOut groups
	 */
	public void setVisibleCategories(final SessionContext ctx, final UserGroup item, final List<Category> value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.UserGroup.VISIBLECATEGORIES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserGroup.visibleCategories</code> attribute. 
	 * @param value the visibleCategories - The categories for the PunchOut groups
	 */
	public void setVisibleCategories(final UserGroup item, final List<Category> value)
	{
		setVisibleCategories( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.weighAndPayEnabled</code> attribute.
	 * @return the weighAndPayEnabled
	 */
	public Boolean isWeighAndPayEnabled(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, SiteoneCoreConstants.Attributes.Product.WEIGHANDPAYENABLED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.weighAndPayEnabled</code> attribute.
	 * @return the weighAndPayEnabled
	 */
	public Boolean isWeighAndPayEnabled(final Product item)
	{
		return isWeighAndPayEnabled( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.weighAndPayEnabled</code> attribute. 
	 * @return the weighAndPayEnabled
	 */
	public boolean isWeighAndPayEnabledAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isWeighAndPayEnabled( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.weighAndPayEnabled</code> attribute. 
	 * @return the weighAndPayEnabled
	 */
	public boolean isWeighAndPayEnabledAsPrimitive(final Product item)
	{
		return isWeighAndPayEnabledAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.weighAndPayEnabled</code> attribute. 
	 * @param value the weighAndPayEnabled
	 */
	public void setWeighAndPayEnabled(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.Product.WEIGHANDPAYENABLED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.weighAndPayEnabled</code> attribute. 
	 * @param value the weighAndPayEnabled
	 */
	public void setWeighAndPayEnabled(final Product item, final Boolean value)
	{
		setWeighAndPayEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.weighAndPayEnabled</code> attribute. 
	 * @param value the weighAndPayEnabled
	 */
	public void setWeighAndPayEnabled(final SessionContext ctx, final Product item, final boolean value)
	{
		setWeighAndPayEnabled( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.weighAndPayEnabled</code> attribute. 
	 * @param value the weighAndPayEnabled
	 */
	public void setWeighAndPayEnabled(final Product item, final boolean value)
	{
		setWeighAndPayEnabled( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.yearsInBusiness</code> attribute.
	 * @return the yearsInBusiness - Years in business
	 */
	public String getYearsInBusiness(final SessionContext ctx, final B2BUnit item)
	{
		return (String)item.getProperty( ctx, SiteoneCoreConstants.Attributes.B2BUnit.YEARSINBUSINESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>B2BUnit.yearsInBusiness</code> attribute.
	 * @return the yearsInBusiness - Years in business
	 */
	public String getYearsInBusiness(final B2BUnit item)
	{
		return getYearsInBusiness( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.yearsInBusiness</code> attribute. 
	 * @param value the yearsInBusiness - Years in business
	 */
	public void setYearsInBusiness(final SessionContext ctx, final B2BUnit item, final String value)
	{
		item.setProperty(ctx, SiteoneCoreConstants.Attributes.B2BUnit.YEARSINBUSINESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>B2BUnit.yearsInBusiness</code> attribute. 
	 * @param value the yearsInBusiness - Years in business
	 */
	public void setYearsInBusiness(final B2BUnit item, final String value)
	{
		setYearsInBusiness( getSession().getSessionContext(), item, value );
	}
	
}
