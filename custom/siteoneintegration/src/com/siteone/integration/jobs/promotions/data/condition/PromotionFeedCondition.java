package com.siteone.integration.jobs.promotions.data.condition;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromotionFeedCondition {

@SerializedName("definitionId")
@Expose
private String definitionId;
@SerializedName("parameters")
@Expose
private Parameters parameters;
@SerializedName("children")
@Expose
private List<PromotionFeedCondition> children = null;

public String getDefinitionId() {
return definitionId;
}

public void setDefinitionId(String definitionId) {
this.definitionId = definitionId;
}

public Parameters getParameters() {
return parameters;
}

public void setParameters(Parameters parameters) {
this.parameters = parameters;
}

public List<PromotionFeedCondition> getChildren() {
return children;
}

public void setChildren(List<PromotionFeedCondition> children) {
this.children = children;
}



public class Categories {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private List<String> value = null;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public List<String> getValue() {
return value;
}

public void setValue(List<String> value) {
this.value = value;
}

}


public class CategoriesOperator {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private String value;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getValue() {
return value;
}

public void setValue(String value) {
this.value = value;
}

}


public class CustomerGroups {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private List<String> value = null;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public List<String> getValue() {
return value;
}

public void setValue(List<String> value) {
this.value = value;
}

}


public class CustomerGroupsOperator {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private String value;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getValue() {
return value;
}

public void setValue(String value) {
this.value = value;
}

}

public class Customers {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private List<Object> value = null;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public List<Object> getValue() {
return value;
}

public void setValue(List<Object> value) {
this.value = value;
}

}


public class ExcludedCategories {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private List<String> value = null;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public List<String> getValue() {
return value;
}

public void setValue(List<String> value) {
this.value = value;
}

}


public class ExcludedCustomerGroups {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private List<String> value = null;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public List<String> getValue() {
return value;
}

public void setValue(List<String> value) {
this.value = value;
}

}


public class ExcludedCustomers {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private List<Object> value = null;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public List<Object> getValue() {
return value;
}

public void setValue(List<Object> value) {
this.value = value;
}

}


public class ExcludedProducts {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private List<String> value = null;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public List<String> getValue() {
return value;
}

public void setValue(List<String> value) {
this.value = value;
}

}


public class Operator {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private String value;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getValue() {
return value;
}

public void setValue(String value) {
this.value = value;
}

}

public class Parameters {

@SerializedName("quantity")
@Expose
private Quantity quantity;
@SerializedName("categories_operator")
@Expose
private CategoriesOperator categoriesOperator;
@SerializedName("excluded_categories")
@Expose
private ExcludedCategories excludedCategories;
@SerializedName("categories")
@Expose
private Categories categories;
@SerializedName("excluded_products")
@Expose
private ExcludedProducts excludedProducts;
@SerializedName("operator")
@Expose
private Operator operator;
@SerializedName("products_operator")
@Expose
private ProductsOperator productsOperator;
@SerializedName("products")
@Expose
private Products products;
@SerializedName("customer_groups")
@Expose
private CustomerGroups customerGroups;
@SerializedName("customer_groups_operator")
@Expose
private CustomerGroupsOperator customerGroupsOperator;
@SerializedName("customers")
@Expose
private Customers customers;
@SerializedName("excluded_customer_groups")
@Expose
private ExcludedCustomerGroups excludedCustomerGroups;
@SerializedName("excluded_customers")
@Expose
private ExcludedCustomers excludedCustomers;
@SerializedName("coupons")
@Expose
private Coupons coupons;
@SerializedName("value")
@Expose
private Value value;
@SerializedName("id")
@Expose
private Id id;

public Quantity getQuantity() {
return quantity;
}

public void setQuantity(Quantity quantity) {
this.quantity = quantity;
}

public CategoriesOperator getCategoriesOperator() {
return categoriesOperator;
}

public void setCategoriesOperator(CategoriesOperator categoriesOperator) {
this.categoriesOperator = categoriesOperator;
}

public ExcludedCategories getExcludedCategories() {
return excludedCategories;
}

public void setExcludedCategories(ExcludedCategories excludedCategories) {
this.excludedCategories = excludedCategories;
}

public Categories getCategories() {
return categories;
}

public void setCategories(Categories categories) {
this.categories = categories;
}

public ExcludedProducts getExcludedProducts() {
return excludedProducts;
}

public void setExcludedProducts(ExcludedProducts excludedProducts) {
this.excludedProducts = excludedProducts;
}

public Operator getOperator() {
return operator;
}

public void setOperator(Operator operator) {
this.operator = operator;
}

public ProductsOperator getProductsOperator() {
return productsOperator;
}

public void setProductsOperator(ProductsOperator productsOperator) {
this.productsOperator = productsOperator;
}

public Products getProducts() {
return products;
}

public void setProducts(Products products) {
this.products = products;
}

public CustomerGroups getCustomerGroups() {
return customerGroups;
}

public void setCustomerGroups(CustomerGroups customerGroups) {
this.customerGroups = customerGroups;
}

public CustomerGroupsOperator getCustomerGroupsOperator() {
return customerGroupsOperator;
}

public void setCustomerGroupsOperator(CustomerGroupsOperator customerGroupsOperator) {
this.customerGroupsOperator = customerGroupsOperator;
}

public Customers getCustomers() {
return customers;
}

public void setCustomers(Customers customers) {
this.customers = customers;
}

public ExcludedCustomerGroups getExcludedCustomerGroups() {
return excludedCustomerGroups;
}

public void setExcludedCustomerGroups(ExcludedCustomerGroups excludedCustomerGroups) {
this.excludedCustomerGroups = excludedCustomerGroups;
}

public ExcludedCustomers getExcludedCustomers() {
return excludedCustomers;
}

public void setExcludedCustomers(ExcludedCustomers excludedCustomers) {
this.excludedCustomers = excludedCustomers;
}

public Coupons getCoupons() {
	return coupons;
}

public void setCoupons(Coupons coupons) {
	this.coupons = coupons;
}

public Value getValue() {
	return value;
}

public void setValue(Value value) {
	this.value = value;
}

public Id getId() {
	return id;
}

public void setId(Id id) {
	this.id = id;
}
}


public class Products {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private List<String> value = null;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public List<String> getValue() {
return value;
}

public void setValue(List<String> value) {
this.value = value;
}

}

public class ProductsOperator {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private String value;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getValue() {
return value;
}

public void setValue(String value) {
this.value = value;
}

}


public class Quantity {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private Integer value;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Integer getValue() {
return value;
}

public void setValue(Integer value) {
this.value = value;
}

}

public class Value {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private Value_ value;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Value_ getValue() {
return value;
}

public void setValue(Value_ value) {
this.value = value;
}

}

public class Value_ {

@SerializedName("USD")
@Expose
private Integer uSD;

public Integer getUSD() {
return uSD;
}

public void setUSD(Integer uSD) {
this.uSD = uSD;
}

}

public class Coupons {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private List<String> value = null;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public List<String> getValue() {
return value;
}

public void setValue(List<String> value) {
this.value = value;
}

}

public class Id {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private String value;

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getValue() {
return value;
}

public void setValue(String value) {
this.value = value;
}

}
}