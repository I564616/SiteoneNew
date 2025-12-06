package com.siteone.integration.jobs.promotions.data.action;

import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PromotionFixedPriceAction {

@SerializedName("definitionId")
@Expose
private String definitionId;
@SerializedName("parameters")
@Expose
private Parameters parameters;

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


public class Parameters {

@SerializedName("selection_strategy")
@Expose
private SelectionStrategy selectionStrategy;
@SerializedName("qualifying_containers")
@Expose
private QualifyingContainers qualifyingContainers;
@SerializedName("target_containers")
@Expose
private TargetContainers targetContainers;
@SerializedName("value")
@Expose
private Value__ value;

public SelectionStrategy getSelectionStrategy() {
return selectionStrategy;
}

public void setSelectionStrategy(SelectionStrategy selectionStrategy) {
this.selectionStrategy = selectionStrategy;
}

public QualifyingContainers getQualifyingContainers() {
return qualifyingContainers;
}

public void setQualifyingContainers(QualifyingContainers qualifyingContainers) {
this.qualifyingContainers = qualifyingContainers;
}

public TargetContainers getTargetContainers() {
return targetContainers;
}

public void setTargetContainers(TargetContainers targetContainers) {
this.targetContainers = targetContainers;
}

public Value__ getValue() {
return value;
}

public void setValue(Value__ value) {
this.value = value;
}

}




public class QualifyingContainers {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private Map<String, Integer> value;

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

public Map<String, Integer> getValue() {
return value;
}

public void setValue(Map<String, Integer> value) {
this.value = value;
}

}


public class SelectionStrategy {

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


public class TargetContainers {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private Map<String, Integer> value;

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

public Map<String, Integer> getValue() {
return value;
}

public void setValue(Map<String, Integer> value) {
this.value = value;
}

}


public class Value {

@SerializedName("CONTAINER_X")
@Expose
private Integer cONTAINERX;

public Integer getCONTAINERX() {
return cONTAINERX;
}

public void setCONTAINERX(Integer cONTAINERX) {
this.cONTAINERX = cONTAINERX;
}

}


public class Value_ {

@SerializedName("CONTAINER_Y")
@Expose
private Integer cONTAINERY;

public Integer getCONTAINERY() {
return cONTAINERY;
}

public void setCONTAINERY(Integer cONTAINERY) {
this.cONTAINERY = cONTAINERY;
}

}


public class Value__ {

@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("type")
@Expose
private String type;
@SerializedName("value")
@Expose
private Value___ value;

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

public Value___ getValue() {
return value;
}

public void setValue(Value___ value) {
this.value = value;
}

}


public class Value___ {

@SerializedName("USD")
@Expose
private Double uSD;

public Double getUSD() {
return uSD;
}

public void setUSD(Double uSD) {
this.uSD = uSD;
}
}
}