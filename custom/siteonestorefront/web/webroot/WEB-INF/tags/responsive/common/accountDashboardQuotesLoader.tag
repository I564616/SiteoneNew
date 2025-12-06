<%@ attribute name="type" required="true" type="java.lang.String" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="dashboard-${type}-loader hidden">
							<div class="bg-light-grey border-radius-3 f-s-12 f-w-600 l-h-12 margin0 p-x-10 p-y-15 row text-default text-uppercase hidden-xs hidden-sm">
									<div class="col-xs-8"><spring:theme code="accountDashboardPage.idname" /></div>
									<div class="col-xs-4 p-l-10"><spring:theme code="accountDashboardPage.date.submitted" /></div>
								</div>
							<div class="lazy-loader-container m-t-10">	
							<div class="lazy-loader-row"></div>
							<div class="lazy-loader-row"></div>
							<div class="lazy-loader-row"></div>
							<div class="lazy-loader-row"></div>
							</div>
							</div>