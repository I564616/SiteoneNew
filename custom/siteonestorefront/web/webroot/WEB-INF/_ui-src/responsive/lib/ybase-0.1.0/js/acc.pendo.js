ACC.pendo = {
  _autoload: ["initializePendo"],

  initializePendo() {
    console.log("Pendo Initialized...");
  },
  _safeGetElementValue(id) {
    const element = document.getElementById(id);
    if (element) {
        return element.value;
    }
    return "";
  },
  _safeGetTrimmedValue(selector) {
    const element = $(selector);
    return $.trim(element.val() || element.text() || "");
  },
  captureEvent(eventType, payload = {}) {
    try {
      // --- Capture User Agent (Browser data) ---
      var uaData =
        navigator.userAgentData &&
        navigator.userAgentData.brands &&
        navigator.userAgentData.brands[0];
      var userAgent = uaData
        ? uaData.brand + "," + uaData.version
        : navigator.userAgent || "";

      // --- Get Email id (plain text) ----
      var accountId;
      if (eventType === "3XGUEST") {
        accountId = ACC.pendo._safeGetTrimmedValue("#emaildata");
      } else {
        accountId = ACC.pendo._safeGetTrimmedValue("#emailaddres");
      }

      // --- Event Payload to BE ------
      var data = {
        type: "track",
        event: eventType,
        timestamp: Date.now(),
        ip: "00.000.000.00",
        userAgent,
        url: window.location.href || "https://www.siteone.com/",
        title: document.title || "siteone - Home",
        emailId: accountId,
        phone: ACC.pendo._safeGetTrimmedValue("#phonedata"),
        kountSessionId:ACC.pendo._safeGetElementValue("kountSessionIdLink"),
        orderNumber: ACC.pendo._safeGetElementValue("orderNumberLink"),
        orderAmount: ACC.pendo._safeGetElementValue("orderAmountLink"),
        listCode: "",
        quoteNumber: "",
      };

      if (
        eventType === "REQUESTQUOTEFAIL" ||
        eventType === "APPROVEQUOTEFAIL"
      ) {
        data = { ...data, ...payload };
      }

      // --- Prepare Pendo Data based on event types ---
      var pendoData;
      if (eventType === "3XGUEST") {
        pendoData = $.extend({}, payload, {
          emailId: accountId,
          phone: ACC.pendo._safeGetTrimmedValue("#phonedata"),
        });
      } else if (eventType === "3XLINK2PAY" || eventType === "LINK2PAYFAIL") {
        pendoData = $.extend({}, payload, {
          kountSessionId:ACC.pendo._safeGetElementValue("kountSessionIdLink"),
          orderNumber: ACC.pendo._safeGetElementValue("orderNumberLink"),
          orderAmount: ACC.pendo._safeGetElementValue("orderAmountLink"),
        });
      } else {
        pendoData = $.extend({}, payload, {
          emailId: accountId,
        });
      }

      // --- Async log to server ---
      $.ajax({
        type: "POST",
        url: `${ACC.config.encodedContextPath}/getPendoEvent`,
        data: data,
        success: (result) => {
          console.log("Pendo event logged from server response:", result);
          // --- Track with Pendo  ---
          if (
            typeof pendo !== "undefined" &&
            typeof pendo.track === "function"
          ) {
            pendo.track(eventType, pendoData);
            console.log("Pendo Trak Triggerd...");
          } else {
            console.warn("Pendo SDK not available.");
          }
        },
        error: (xhr, status, error) => {
          console.error("Pendo log failed:", status, error);
        },
      });
    } catch (err) {
      console.error("Error in captureEvent:", err);
    }
  },
};
