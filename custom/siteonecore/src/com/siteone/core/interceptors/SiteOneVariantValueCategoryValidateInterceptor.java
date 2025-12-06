/**
 *
 */
package com.siteone.core.interceptors;

import com.google.common.collect.Sets;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.servicelayer.i18n.L10NService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.variants.interceptor.VariantValueCategoryValidateInterceptor;
import de.hybris.platform.variants.model.VariantValueCategoryModel;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;


/**
 * @author SD02010
 *
 */
public class SiteOneVariantValueCategoryValidateInterceptor extends VariantValueCategoryValidateInterceptor
{
	  private L10NService l10NService;
	  private SessionService sessionService;
	   
	   @Override
		public void onValidate(VariantValueCategoryModel variantValueCategory, InterceptorContext ctx) throws InterceptorException {
    
	      List<CategoryModel> variantCategories = variantValueCategory.getSupercategories();
	       
	       if (CollectionUtils.isEmpty(variantCategories))
          {
	         throw new InterceptorException(getL10NService().getLocalizedString("error.variantvaluecategory.nosupercategoryfound"));
	       }
	       
	       if (variantCategories.size() > 1)
	       {
	         throw new InterceptorException(getL10NService().getLocalizedString("error.variantvaluecategory.maxonesupercategory"));
	       }
       
	       if (variantCategories.size() == 1) {
         
	       CategoryModel variantCategory = variantCategories.iterator().next();
         if (variantCategory instanceof de.hybris.platform.variants.model.VariantCategoryModel) {
	           
           validateVariantValueCategory(variantValueCategory);
	           List<CategoryModel> siblings = variantCategory.getCategories();
           validateSequenceWithinSiblings(siblings, variantValueCategory);
        }
	         else {
	           
	           throw new InterceptorException(getL10NService().getLocalizedString("error.variantvaluecategory.wrongcategorytype"));
	         } 
	       } 
	     } 
	   
	 
	 
	   @Override
		protected void validateVariantValueCategory(VariantValueCategoryModel variantValueCategory) throws InterceptorException {
	     if (variantValueCategory.getSequence() == null)
	     {
	      throw new InterceptorException(getL10NService().getLocalizedString("error.variantvaluecategory.nosequencenumberprovided"));
	     }
	 
	     
	     if (variantValueCategory.getSequence() != null && variantValueCategory.getSequence().intValue() < 0)
	     {
	       throw new InterceptorException(getL10NService().getLocalizedString("error.variantvaluecategory.negativesequencenumber"));
	     }
	  }
	
 
	   @Override
		protected void validateSequenceWithinSiblings(List<CategoryModel> siblings, VariantValueCategoryModel currentCategory) throws InterceptorException {
	     HashSet<Integer> sequences = Sets.newHashSet();
	     
	     if (!siblings.contains(currentCategory))
     {
	      sequences.add(currentCategory.getSequence());
     }
	     
	     for (CategoryModel c : siblings) {
	       
	       if (c instanceof VariantValueCategoryModel) {
	         
	         VariantValueCategoryModel variantValueCat = (VariantValueCategoryModel)c;
	         
	        if (sequences.contains(variantValueCat.getSequence()))
	         {
	           throw new InterceptorException(getL10NService().getLocalizedString("error.genericvariantproduct.morethenonecategorywithsamesequence", new Object[] { variantValueCat
	                   
	                  .getSequence() }));
	         }
	 
	         
	         sequences.add(variantValueCat.getSequence());
	       } 
      } 
	   }
	 
	 
	
   @Override
	protected L10NService getL10NService() {
	     return this.l10NService;
	  }
	 

   @Override
	public void setL10NService(L10NService l10NService) {
       this.l10NService = l10NService;
	   }
	 
 
	  @Override
	  protected SessionService getSessionService() {
	     return this.sessionService;
	   }
	 
	 
	   @Override
	   public void setSessionService(SessionService sessionService) {
	    this.sessionService = sessionService;
	   }
}